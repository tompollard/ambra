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

package org.mulgara.resolver;

// Java 2 enterprise packages
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

/**
 * Generate a presentation form for a transaction {@link Status}.
 *
 * @created 2004-11-26
 * @author <a href="http://www.pisoftware.com/raboczi">Simon Raboczi</a>
 * @version $Revision$
 * @modified $Date: 2005/01/05 04:58:24 $ 
 * @maintenanceAuthor $Author: newmana $
 * @company <a href="mailto:info@PIsoftware.com">Plugged In Software</a>
 * @copyright &copy;2004 <a href="http://www.pisoftware.com/">Plugged In
 *      Software Pty Ltd</a>
 * @licence <a href="{@docRoot}/../../LICENCE">Mozilla Public License v1.1</a>
 */
abstract class StatusFormat
{
  /**
   * Utility to generate a presentation form for a transaction {@link Status}.
   *
   * @param status  a transaction {@link Status}
   * @return presentation form of the <var>status</var>
   */
  public static String formatStatus(int status) {
    switch (status) {
      case Status.STATUS_ACTIVE:
        return "ACTIVE";
      case Status.STATUS_COMMITTED:
        return "COMMITTED";
      case Status.STATUS_COMMITTING:
        return "COMMITTING";
      case Status.STATUS_MARKED_ROLLBACK:
        return "MARKED_ROLLBACK";
      case Status.STATUS_NO_TRANSACTION:
        return "NO_TRANSACTION";
      case Status.STATUS_PREPARED:
        return "PREPARED";
      case Status.STATUS_PREPARING:
        return "PREPARING";
      case Status.STATUS_ROLLEDBACK:
        return "ROLLEDBACK";
      case Status.STATUS_ROLLING_BACK:
        return "ROLLING_BACK";
      case Status.STATUS_UNKNOWN:
        return "UNKNOWN";
      default:
        return "NOT_A_STATUS_" + status;
    }
  }

  /**
   * Generate a presentation form for the status of a transaction manager.
   *
   * This method will return the message of the exception if an exception
   * results from querying the transaction manager's status, rather than
   * throwing the exception.
   *
   * @param transactionManager  the transaction manager
   */
  public static String formatStatus(TransactionManager transactionManager)
  {
    try {
      return formatStatus(transactionManager.getStatus());
    }
    catch (SystemException e) {
      return e.getMessage();
    }
  }
}
