/*
 * Created on 2005-6-14
 *
 */
package com.sinosoft.undwrt.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.PrpCurrencyTypeVo;
import com.sinosoft.prpall.dto.domain.PrpPmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmain;

/**
 * The Class GetPlanCurrencyType.
 */
public class GetPlanCurrencyType {

    /**
	 * 得到收费计划中的币种类型信息.
	 * 
	 * @param businessNo
	 *            the business no
	 * @param businessType
	 *            the business type
	 * @param dbManager
	 *            the db manager
	 * @return 屬性the sinosoft plan currency type的值
	 * @throws SQLException
	 *             the sQL exception
	 * @throws Exception
	 *             the exception
	 */
    public Collection getPlanCurrencyType(String businessNo,
            String businessType, DBManager dbManager) throws SQLException,
            Exception {
        Collection currencyType = null;
        String statementStr = null;
        PrpCurrencyTypeVo prpCurrencyType = null;
        if (businessType.equals("T")) {
            statementStr = "SELECT DISTINCT currency1 FROM prptfee a where proposalno = '"
                    + businessNo + "'";
        }
        else if (businessType.equals("P")) {
            statementStr = "SELECT DISTINCT currency1 FROM prpcfee a where policyno = '"
                    + businessNo + "'";
        }

        else if (businessType.equals("E")) {
            PrpPmainDto prpPmainDto = new PrpPmainDto();
            DBPrpPmain dbPrpPmain = new DBPrpPmain(dbManager);
            prpPmainDto = dbPrpPmain.findByPrimaryKey(businessNo);
            statementStr = "SELECT DISTINCT currency1 FROM prpcpfee a where policyNo = '"
                    + prpPmainDto.getPolicyNo() + "'";
        }

        ResultSet result = dbManager.executeQuery(statementStr);
        String tempType = "";
        while (result.next()) {
            try {
                currencyType = new ArrayList();
                tempType = dbManager.getString(result, 1);
                prpCurrencyType = new PrpCurrencyTypeVo();
                prpCurrencyType.setCurrencyType(tempType);
                currencyType.add(prpCurrencyType);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (currencyType == null) {
            throw new Exception("交费计划信息，请在业务系统中设置相应的数据");
        }
        return currencyType;
    }
}