/*
 * Created on 2005-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.CommonAmountAndPremiumVo;
import com.sinosoft.prpall.dto.domain.PrpPmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmain;

/**
 * The Class GetAmountAndPremium.
 * 
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class GetAmountAndPremium {

    /**
	 * 獲取屬性the sinosoft amount and premium.
	 * 
	 * @param businessNo
	 *            the business no
	 * @param businessType
	 *            the business type
	 * @param dbManager
	 *            the db manager
	 * @return 屬性the sinosoft amount and premium的值
	 * @throws Exception
	 *             the exception
	 */
    public CommonAmountAndPremiumVo getAmountAndPremium(String businessNo,String businessType,DBManager dbManager) throws Exception
    {
       
       String statementStr     = null;
       CommonAmountAndPremiumVo commonAmountAndPremiumDto = null;
       
       if(businessType.equals("T"))
       {
         statementStr = "select sum(amount2) amount,sum(premium2) premium "+
         "from prptfee where proposalno='"+businessNo+"'";
       }
       if(businessType.equals("P"))
       {
           statementStr = "select sum(amount2) amount,sum(premium2) premium "+
           "from prpcfee where policyno='"+businessNo+"'";                
       }
       if(businessType.equals("E"))
       {   PrpPmainDto prpPmainDto = new PrpPmainDto();
           DBPrpPmain  dbPrpPmain = new DBPrpPmain(dbManager);
           prpPmainDto = dbPrpPmain.findByPrimaryKey(businessNo);
           statementStr = "select sum(amount2) amount,sum(premium2) premium "+
           "from prpcpfee where policyNo='" + prpPmainDto.getPolicyNo() + "'";
                        
       }
       //System.out.println(statementStr);
       ResultSet  resultSet  = dbManager.executeQuery(statementStr);
       Collection collection =  new ArrayList();
       while(resultSet.next())
       {
           try
           {   
               commonAmountAndPremiumDto = new CommonAmountAndPremiumVo();
               commonAmountAndPremiumDto.setBussinessNo(businessNo);
               commonAmountAndPremiumDto.setBussinessType(businessType);
               commonAmountAndPremiumDto.setAmount(dbManager.getDouble(resultSet,1));
               commonAmountAndPremiumDto.setPremium(dbManager.getDouble(resultSet,2)); 
              
            }catch(Exception e)
            {
              e.printStackTrace();
              throw new Exception("查询数据产生了错误");
            }
       }
       /*
       if(commonAmountAndPremiumDto == null)
       {
         throw new Exception("没有相关的危险单位标的信息或兑换率信息");  
       }
        */
       return commonAmountAndPremiumDto;
    }
}
