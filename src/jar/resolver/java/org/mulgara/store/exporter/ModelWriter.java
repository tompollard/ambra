/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is the Kowari Metadata Store.
 *
 * The Initial Developer of the Original Code is Plugged In Software Pty
 * Ltd (http://www.pisoftware.com, mailto:info@pisoftware.com). Portions
 * created by Plugged In Software Pty Ltd are Copyright (C) 2001,2002
 * Plugged In Software Pty Ltd. All Rights Reserved.
 *
 * Contributor(s): N/A.
 *
 * [NOTE: The text of this Exhibit A may differ slightly from the text
 * of the notices in the Source Code files of the Original Code. You
 * should use the text of this Exhibit A rather than the text found in the
 * Original Code Source Code for Your Modifications.]
 *
 */
package org.mulgara.store.exporter;

// Java 2 standard packages
import java.io.*;

// Local packages
import org.jrdf.graph.GraphException;
import org.jrdf.graph.Graph;

/**
 * A Writer used to write a serialization for a Mulgara model.
 *
 * @created 2004-02-23
 *
 * @author <a href="mailto:pag@tucanatech.com">Paul Gearon</a>
 *
 * @version $Revision$
 *
 * @modified $Date: 2005/01/05 04:58:25 $
 *
 * @maintenanceAuthor $Author: newmana $
 *
 * @company <A href="mailto:info@PIsoftware.com">Plugged In Software</A>
 *
 * @copyright &copy;2001 <a href="http://www.pisoftware.com/">Plugged In
 *      Software Pty Ltd</a>
 *
 * @licence <a href="{@docRoot}/../../LICENCE">Mozilla Public License v1.1</a>
 */
public interface ModelWriter {

  /**
   * Writes the contents of the JRDFGraph to a PrintWriter in RDF/XML format.
   *
   * @param graph JRDF Graph containing the Statements to be written.
   * @param writer PrintWriter Where to write the statements.
   * @throws GraphException
   */
  public void write(Graph graph, PrintWriter writer) throws GraphException;

  /**
   * Writes the contents of the JRDFGraph to a PrintWriter in RDF/XML format
   * with the encoding specified in the opening XML tag.
   *
   * @param graph JRDF Graph
   * @param writer PrintWriter
   * @throws GraphException
   */
  public void write(Graph graph, OutputStreamWriter writer) throws GraphException;

}
