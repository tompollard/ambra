/*
 * Copyright (c) 2006-2014 by Public Library of Science
 *
 * http://plos.org
 * http://ambraproject.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ambraproject.service.article;

import org.w3c.dom.Document;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Alex Kudlick
 *         Date: 7/3/12
 */
public interface ArticleClassifier {

  /**
   * Classify an article from its xml.
   *
   * @param articleXml the article xml
   * @return a map of categories to which the article belongs. Each entry should use <code>/</code>s to
   *         delimit subject hierarchy.  Categories are returned in descending order of the
   *         strength of the match paired with the strength value
   */
  public Map<String, Integer> classifyArticle(Document articleXml) throws Exception;

  /**
   * Classify an article from its xml and output a lot of debugging information to the passed in stream
   * This does not actually store any information
   *
   * @param os the stream to write to
   * @param doi the doi of the article
   * @param thesaurus the thesaurus to use
   */
  public void testThesaurus(OutputStream os, String doi, String thesaurus) throws Exception;
}
