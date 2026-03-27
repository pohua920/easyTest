/*
 * Created on 2005-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;


/**
 * 这是获取指定机构，险种，模板号和节点号的所有用户列表的类<br>
 * 创建者: LuoJing 时间： 20070904.
 */
public class GetUsersOfNode {
   
    /**
	 * 獲取屬性the sinosoft users of node.
	 * 
	 * @param wflogDto
	 *            the wflog dto
	 * @param dbManager
	 *            the db manager
	 * @return 屬性the sinosoft users of node的值
	 * @throws Exception
	 *             the exception
	 */
    public Collection getUsersOfNode(WfLog wflogDto,DBManager dbManager) throws Exception
    {
       
       String statementStr     = null;
       statementStr =
    	   "select  distinct usercode from uwgroup a, uwgrade b " + 
    	   "where a.groupno = b.groupno " +
    	   "and comcode = '" + wflogDto.getComCode() + "' " +
    	   "and riskcode = '" + wflogDto.getRiskCode() + "' " +
    	   "and modelno = '" + wflogDto.getModelNo() + "' " +
    	   "and nodeno=" + wflogDto.getNodeNo(); 
       ResultSet  resultSet  = dbManager.executeQuery(statementStr);
       Collection collection =  new ArrayList();
       while(resultSet.next())
       {
          
           try
           {   
                collection.add(dbManager.getString(resultSet,1));
              
            }catch(Exception e)
            {
              e.printStackTrace();
              throw new Exception("查询数据产生了错误");
            }
       }
      
 
       return collection;
    }
}
