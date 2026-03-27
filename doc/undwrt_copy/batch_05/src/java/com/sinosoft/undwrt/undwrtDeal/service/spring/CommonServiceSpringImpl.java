package com.sinosoft.undwrt.undwrtDeal.service.spring;

//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整
import java.sql.ResultSet;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonService;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 
 * 為避免影響原有程式，因此獨立拉出這支
 */
public class CommonServiceSpringImpl extends GenericDaoHibernate implements CommonService {

	/**
	 * 
	 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
	 * 
	 * 更新地址比對狀態
	 * 
	 * @param businessNo
	 * @throws Exception
	 */
	public void updateNomastatus(String type, String businessNo) throws Exception {
		DBManager dbManager = new DBManager();
		
		try {
			if("".equals(businessNo) || businessNo == null){
				return;
			}
			
			
			String sql = "";
			if("T".equals(type)){
				 sql = "update prpTmain set normastatus ='7' where proposalno='"+businessNo+"' ";
			}
			if("B".equals(type)){
				 sql = "update prpQmain set normastatus ='7' where proposalno='"+businessNo+"' ";
			}
			if("".equals(sql)){
				throw new Exception("無法取得操作類型(報價或是要保？)");
			}
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager.beginTransaction();
			int i = dbManager.executeUpdate(sql);
			dbManager.commitTransaction();

		} catch (Exception e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Error e) {
			dbManager.rollbackTransaction();
			throw new RuntimeException(e.getMessage());// 抛出外层可以捕获的Exception异常。
		} finally {
			dbManager.close();
		}
	}
	
	/**
	 * 
	 * mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整
	 * 
	 * 支票收費註記
	 * 
	 * @param businessNo
	 * @throws Exception
	 */
	public String queryCheckPay( String businessNo) throws Exception {
		DBManager dbManager = new DBManager();
		String type = null;
		ResultSet rs= null;
		String stopByReason = "";
		boolean _contiune = true;
		try {
			if("".equals(businessNo) || businessNo == null){
				return "";
			}
			String sql = "";
			if(businessNo.substring(1, 2).equals("B")){
				 sql = " Select * from prptmainsub where MAINPOLICYNO ='"+businessNo+"' ";		
			}else{
				_contiune = false;
				stopByReason = "second word isn't B";
			}
			
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager.beginTransaction();
			rs= dbManager.executeQuery(sql);
			while (rs.next()) {
				stopByReason = "query1 have data";
				_contiune = false;
			}
			dbManager.commitTransaction();

		} catch (Exception e) {
//			dbManager.rollbackTransaction();
//			e.printStackTrace();
			//throw new RuntimeException(e.getMessage());
		} catch (Error e) {
//			dbManager.rollbackTransaction();
			//throw new RuntimeException(e.getMessage());// 抛出外层可以捕获的Exception异常。
		} finally {
			try{
				rs.close();
				dbManager.close();
			} catch (Exception e) {
			
			}
		}
		

		DBManager dbManager2 = new DBManager();
		ResultSet rs2= null;
		try {
			if(!_contiune){
				return stopByReason;
			}
			String sql2 = "";
			sql2 =" SELECT * FROM prptmain " +
				 " WHERE EXISTS(SELECT * FROM ccicdms.prpdnewcode WHERE prptmain.BusinessNature= ccicdms.prpdnewcode.CODECODE " +
				 " AND ccicdms.prpdnewcode.CODETYPE='CheckBusinessNature' " +
				 " AND ccicdms.prpdnewcode.VALIDSTATUS='1') " +
				 " AND prptmain.PROPOSALNO ='"+businessNo+"' ";		
			
			dbManager2.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			dbManager2.beginTransaction();
			rs2= dbManager2.executeQuery(sql2);
			stopByReason = "query2 haven't data";
			while (rs2.next()) {
				//查詢出來有值的話，則不需要輸入支票號碼與開票日期，允許直接儲存繳費資料。
				stopByReason = "";
			}
			dbManager2.commitTransaction();

		} catch (Exception e) {
//			dbManager2.rollbackTransaction();
//			e.printStackTrace();
			//throw new RuntimeException(e.getMessage());
		} catch (Error e) {
//			dbManager2.rollbackTransaction();
			//throw new RuntimeException(e.getMessage());// 抛出外层可以捕获的Exception异常。
		} finally {
			try {
				rs2.close();
				dbManager2.close();
			} catch (Exception e) {
				
			}
		}
		return stopByReason;
	}
}


