/*
 * Created on 2005-7-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.util;
import java.sql.*;
import java.util.*;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.CommonDangerUnitSerialNoVo;

/**
 * The Class GetDangerUnitSerialNo.
 */
public class GetDangerUnitSerialNo {

    /**
	 * 獲取屬性the sinosoft danger unit now serial no.
	 * 
	 * @param businessNo
	 *            the business no
	 * @param businessType
	 *            the business type
	 * @param dbManager
	 *            the db manager
	 * @return 屬性the sinosoft danger unit now serial no的值
	 * @throws Exception
	 *             the exception
	 */
    public Collection getDangerUnitNowSerialNo(String businessNo,String businessType,DBManager dbManager) throws Exception
    {

       String statementStr     = null;
       CommonDangerUnitSerialNoVo commonDangerUnitSerialNoDto = null;
       if(businessType.equals("T"))
       {
         statementStr = "select proposalno, dangerno " +
         		        "from prptdangerunit where proposalno='" + businessNo +"'";
       }
       else if(businessType.equals("P"))
       {
         statementStr =  "select policyno, dangerno " +
	        "from prpcdangerunit where policyno='" + businessNo +"'";
       }
       else if(businessType.equals("E")) {
           statementStr = "select endorseNo, dangerNo " +
             "from prppdangerUnit where endorseNo ='" + businessNo +"'";
       }

       //System.out.println(statementStr);
       ResultSet  resultSet  = dbManager.executeQuery(statementStr);
       Collection collection =  new ArrayList();
       while(resultSet.next())
       {

           try
           {
               commonDangerUnitSerialNoDto = new CommonDangerUnitSerialNoVo();
               commonDangerUnitSerialNoDto.setBusinessNo(dbManager.getString(resultSet,1));
               commonDangerUnitSerialNoDto.setDangerNo(dbManager.getString(resultSet,2));
               collection.add(commonDangerUnitSerialNoDto);
            }catch(Exception e)
           {
              e.printStackTrace();
              throw new Exception("查询数据产生了错误");
           }
       }

        //System.out.println("collection.size()= " + collection.size());
       return collection;
    }



}
