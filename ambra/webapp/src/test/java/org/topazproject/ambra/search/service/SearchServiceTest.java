/* $HeadURL$
 * $Id$
 *
 * Copyright (c) 2006-2008 by Topaz, Inc.
 * http://topazproject.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.topazproject.ambra.search.service;

import org.apache.commons.configuration.Configuration;
import org.testng.annotations.Test;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

import org.topazproject.ambra.article.service.ArticleOtmService;
import org.topazproject.ambra.cache.CacheManager;
import org.topazproject.ambra.cache.AbstractObjectListener;
import org.topazproject.ambra.cache.MockCache;
import org.topazproject.ambra.configuration.ConfigurationStore;
import org.topazproject.ambra.journal.JournalService;
import org.topazproject.otm.Session;
import org.topazproject.otm.Query;
import org.topazproject.otm.query.Results;

import java.util.HashSet;

public class SearchServiceTest {
  @Test
  public void testQueryTransformation() throws Exception {
    runQueryTransformationTest("rna", "select art.id , art.eIssn , score0 , score1 , score2 from Article art where (((search(art.dublinCore.description, 'rna', score0))  or (search(art.dublinCore.title, 'rna', score1))  or (search(cast(art.representations, TextRepresentation).body, 'rna', score2)) )) ;");
    runQueryTransformationTest("+rna", "select art.id , art.eIssn , score0 , score1 , score2 from Article art where (((((search(art.dublinCore.description, 'rna', score0))  or (search(art.dublinCore.title, 'rna', score1))  or (search(cast(art.representations, TextRepresentation).body, 'rna', score2)) )) )) ;");
    runQueryTransformationTest("-rna", "select art.id , art.eIssn from Article art where gt('0'^^<xsd:int>, '1'^^<xsd:int>);");
    runQueryTransformationTest("(protein rna) AND (-genetic)", "select art.id , art.eIssn , score0 , score1 , score2 from Article art where (((((search(art.dublinCore.description, '(protein rna)', score0))  or (search(art.dublinCore.title, '(protein rna)', score1))  or (search(cast(art.representations, TextRepresentation).body, '(protein rna)', score2)) )) ) minus ((((search(art.dublinCore.description, 'genetic'))  or (search(art.dublinCore.title, 'genetic'))  or (search(cast(art.representations, TextRepresentation).body, 'genetic')) )) )) ;");
    runQueryTransformationTest("(protein rna) AND (-genetic) AND subject:(Biochemistry \"Cell Biology\")", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 from Article art where (((((search(art.dublinCore.description, '(protein rna)', score0))  or (search(art.dublinCore.title, '(protein rna)', score1))  or (search(cast(art.representations, TextRepresentation).body, '(protein rna)', score2)) ))  and (search(art.categories.mainCategory, '(biochemistry \"cell biology\")', score3) or search(art.categories.subCategory, '(biochemistry \"cell biology\")', score4)) ) minus ((((search(art.dublinCore.description, 'genetic'))  or (search(art.dublinCore.title, 'genetic'))  or (search(cast(art.representations, TextRepresentation).body, 'genetic')) )) )) ;");
    runQueryTransformationTest("(protein rna) AND genetic", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 from Article art where (((((search(art.dublinCore.description, '(protein rna)', score0))  or (search(art.dublinCore.title, '(protein rna)', score1))  or (search(cast(art.representations, TextRepresentation).body, '(protein rna)', score2)) ))  and (((search(art.dublinCore.description, 'genetic', score3))  or (search(art.dublinCore.title, 'genetic', score4))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score5)) )) )) ;");
    runQueryTransformationTest("protein +genetic rna", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 from Article art where (((((search(art.dublinCore.description, 'genetic', score0))  or (search(art.dublinCore.title, 'genetic', score1))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score2)) ))  and ((search(art.dublinCore.description, '(protein rna)', score3))  or (search(art.dublinCore.title, '(protein rna)', score4))  or (search(cast(art.representations, TextRepresentation).body, '(protein rna)', score5))  or lt('0'^^<xsd:int>, '1'^^<xsd:int>)))) ;");
    runQueryTransformationTest("protein +genetic +rna", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 , score6 , score7 , score8 from Article art where (((((search(art.dublinCore.description, 'genetic', score0))  or (search(art.dublinCore.title, 'genetic', score1))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score2)) ))  and (((search(art.dublinCore.description, 'rna', score3))  or (search(art.dublinCore.title, 'rna', score4))  or (search(cast(art.representations, TextRepresentation).body, 'rna', score5)) ))  and ((search(art.dublinCore.description, 'protein', score6))  or (search(art.dublinCore.title, 'protein', score7))  or (search(cast(art.representations, TextRepresentation).body, 'protein', score8))  or lt('0'^^<xsd:int>, '1'^^<xsd:int>)))) ;");
    runQueryTransformationTest("protein +genetic -rna", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 from Article art where (((((search(art.dublinCore.description, 'genetic', score0))  or (search(art.dublinCore.title, 'genetic', score1))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score2)) ))  and ((search(art.dublinCore.description, 'protein', score3))  or (search(art.dublinCore.title, 'protein', score4))  or (search(cast(art.representations, TextRepresentation).body, 'protein', score5))  or lt('0'^^<xsd:int>, '1'^^<xsd:int>))) minus ((((search(art.dublinCore.description, 'rna'))  or (search(art.dublinCore.title, 'rna'))  or (search(cast(art.representations, TextRepresentation).body, 'rna')) )) )) ;");
    runQueryTransformationTest("protein +genetic (-rna -foo)", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 from Article art where (((((search(art.dublinCore.description, 'genetic', score0))  or (search(art.dublinCore.title, 'genetic', score1))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score2)) ))  and ((search(art.dublinCore.description, 'protein', score3))  or (search(art.dublinCore.title, 'protein', score4))  or (search(cast(art.representations, TextRepresentation).body, 'protein', score5))  or lt('0'^^<xsd:int>, '1'^^<xsd:int>))) minus ((((search(art.dublinCore.description, '(rna foo)'))  or (search(art.dublinCore.title, '(rna foo)'))  or (search(cast(art.representations, TextRepresentation).body, '(rna foo)')) )) )) ;");
    runQueryTransformationTest("protein +genetic -rna -foo", "select art.id , art.eIssn , score0 , score1 , score2 , score3 , score4 , score5 from Article art where (((((search(art.dublinCore.description, 'genetic', score0))  or (search(art.dublinCore.title, 'genetic', score1))  or (search(cast(art.representations, TextRepresentation).body, 'genetic', score2)) ))  and ((search(art.dublinCore.description, 'protein', score3))  or (search(art.dublinCore.title, 'protein', score4))  or (search(cast(art.representations, TextRepresentation).body, 'protein', score5))  or lt('0'^^<xsd:int>, '1'^^<xsd:int>))) minus ((((search(art.dublinCore.description, 'rna'))  or (search(art.dublinCore.title, 'rna'))  or (search(cast(art.representations, TextRepresentation).body, 'rna')) ))  or (((search(art.dublinCore.description, 'foo'))  or (search(art.dublinCore.title, 'foo'))  or (search(cast(art.representations, TextRepresentation).body, 'foo')) )) )) ;");
  }

  private void runQueryTransformationTest(String luceneQuery, String oqlQuery) throws Exception {
    IMocksControl ctrl = EasyMock.createStrictControl();

    ArticleOtmService articleService = ctrl.createMock(ArticleOtmService.class);
    JournalService journalService = ctrl.createMock(JournalService.class);
    Configuration configuration = ctrl.createMock(Configuration.class);
    CacheManager cacheManager = ctrl.createMock(CacheManager.class);
    Session session = ctrl.createMock(Session.class);

    MockCache cache = new MockCache();
    cache.setCacheManager(cacheManager);
    cacheManager.registerListener(EasyMock.isA(AbstractObjectListener.class));
    EasyMock.expectLastCall().times(0,1);

    EasyMock.expect(session.listFilters()).andReturn(new HashSet<String>());
    EasyMock.expect(configuration.getStringArray("ambra.services.search.defaultFields")).
        andReturn(null);

    Query query = ctrl.createMock(Query.class);
    EasyMock.expect(session.createQuery(oqlQuery)).andReturn(query);

    Results results = ctrl.createMock(Results.class);
    EasyMock.expect(query.execute()).andReturn(results);

    EasyMock.expect(results.getWarnings()).andReturn(null);
    EasyMock.expect(results.getVariables()).andReturn(new String[0]);
    EasyMock.expect(results.next()).andReturn(false);

    ctrl.replay();

    ConfigurationStore.getInstance().loadDefaultConfiguration();

    SearchService service = new SearchService();
    service.setSearchCache(cache);
    service.setArticleOtmService(articleService);
    service.setJournalService(journalService);
    service.setAmbraConfiguration(configuration);
    service.setOtmSession(session);

    service.find(luceneQuery, new String[]{"test_journal_key"}, 0, 10, null);

    ctrl.verify();
  }
}
