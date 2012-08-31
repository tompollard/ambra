/*
 * $HeadURL::                                                                            $
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
dojo.provide("ambra.widget.LoadingCycle");

dojo.require("dijit.Dialog");

dojo.declare(
  "ambra.widget.LoadingCycle", 
  [dijit.Dialog],
{
  templateString:"<div class=\"dijitDialog\"><div dojoAttachPoint=\"containerNode\" class=\"dijitDialogPaneContent\"></div></div>",
  duration: 250,
  refocus: false,
  _getFocusItems: function(arg){},
  _onKey:function(e){}
});