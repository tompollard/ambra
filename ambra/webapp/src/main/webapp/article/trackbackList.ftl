<#--
  $HeadURL::                                                                            $
  $Id$
  
  Copyright (c) 2007-2008 by Topaz, Inc.
  http://topazproject.org
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<div id="content">
  <h1>Trackbacks</h1>
  <@s.url namespace="/article" includeParams="none" id="articleURL" action="fetchArticle" articleURI="${Request.trackbackId}"/>
  <div class="source">
    <span>Original Article</span><a href="${articleURL}" title="Back to original article" class="article icon">${articleObj.dublinCore.title}</a>
  </div>
  <@s.url namespace="/" includeParams="none" id="trackbackURL" action="trackback" trackbackId="${Request.trackbackId}"/>
  <#assign trackbackLink = Request[freemarker_config.journalContextAttributeKey].baseHostUrl + trackbackURL>
  <p id="trackbackURL">To trackback to this article, please use the following trackback URL: <a href="${trackbackLink}" title="Trackback URL">${trackbackLink}</a></p>

<#list trackbackList as t>
  <div class="trackback">
    <#if t.title?exists && !(t.title = "")>
      <#assign title = t.title>
    <#else>
      <#assign title = t.url>
    </#if>
    <p class="header">
    <#if t.blog_name?exists>
    <span class="blog">${t.blog_name}</span>
    <#else>
    An unknown source
    </#if>
     referenced this article in "<a href="${t.url}" title="${t.title?replace('"',"")!""}" class="post">${title}</a>" <span class="timestamp">on <strong>${t.created?string("dd MMM yyyy '</strong>at<strong>' HH:mm zzz")}</strong></span></p>
    <#if t.excerpt?exists>
    <p class="excerpt">"${t.excerpt}"</p>
    </#if>
  </div>
</#list>
</div>
