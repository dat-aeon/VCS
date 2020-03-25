/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.common.util;

import mm.com.dat.presto.main.transaction.TransactionBean;
import mm.com.dat.presto.main.transaction.TransactionBeanManager;

public final class TransactionUtilty {
    public static void rollBackTransaction() {
        TransactionBean transactionBean = TransactionBeanManager.get();
        String tranID = transactionBean.getTransactionId();
        if (tranID != null && !tranID.trim().equals("")) {
            transactionBean.setRollbackOnly(true);
        }
        TransactionBeanManager.set(transactionBean);
    }
}
