package com.sinosoft.undwrt.common.service.facade;

import com.sinosoft.undwrt.common.model.PrpDExpenseControl;

// TODO: Auto-generated Javadoc
/**
 * 費用聯動控制策略接口類.
 */
public interface PrpDExpenseControlService {

    /**
     * 根據業務歸屬機構從費用聯動控制策略表PRPDEXPENSECONTROL中遞歸向上查找控制策略數據.
     *
     * @param iComCode 業務歸屬機構
     * @return 符合條件的記錄
     * @throws Exception 異常
     */
    public PrpDExpenseControl getExpenseControl(String iComCode)throws Exception;
}
