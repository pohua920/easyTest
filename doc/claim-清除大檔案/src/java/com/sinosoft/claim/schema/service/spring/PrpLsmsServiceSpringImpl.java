package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔调派处理记录信息接口实现类
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLsms;
import com.sinosoft.claim.schema.service.facade.PrpLsmsService;
import com.sinosoft.sysframework.reference.DBManager;

public class PrpLsmsServiceSpringImpl extends GenericDaoHibernate<PrpLsms, String> implements PrpLsmsService{
	/**
	 * 保存简讯
	 * @param prpLsms ：简讯讯息
	 */
	public void save(PrpLsms prpLsms) throws Exception {
		super.save(prpLsms);
		
	}
	/**
	 * 保存简讯
	 * @param list  :简讯讯息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLsms> list) throws Exception {
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * 保存简讯信息
	 * @param list
	 * @throws Exception
	 */
	public String[] saveSms(List<PrpLsms> list) throws Exception{
		this.save(list);
		return this.saveSMSRequest(list);
	}

	/**
	 * 保存SMSRequest信息
	 * @param prpLsms
	 * @param dbManager
	 * @throws Exception
	 */
	public String[] saveSMSRequest(List<PrpLsms> list) throws Exception {
		String[] success = {"1",""};
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("smsDataSource");
			dbManager.beginTransaction();
			PrpLsms prpLsms = null;
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SMSRequest (");
			sql.append("Serial,Target,Corp_Id,Submit_Date,Dr_Flag,Language,Message");
			sql.append(")");
			sql.append(" values(?,?,?,?,?,?,?)");
			for(int i=0;i<list.size();i++){
				prpLsms = list.get(i);
				if("1".equals(prpLsms.getSmsFlag())){
					dbManager.prepareStatement(sql.toString());
					dbManager.setString(1, prpLsms.getSerial());
					dbManager.setString(2, prpLsms.getTarget());
					dbManager.setString(3, prpLsms.getCorp_Id());
					dbManager.setString(4, prpLsms.getSubmit_Date());
					dbManager.setString(5, prpLsms.getDr_Flag());
					dbManager.setString(6, prpLsms.getLanguage());
					dbManager.setString(7, prpLsms.getMessage());
					dbManager.executePreparedUpdate();
				}
			}
			dbManager.commitTransaction();
		}catch (Exception e) {
			success[0] = "0";
			success[1] = e.getLocalizedMessage();
			dbManager.rollbackTransaction();
		}finally{
			dbManager.close();
		}
		return success;
	}
	/**
	 * 保存SMSRequest信息
	 * @param prpLsms
	 * @param dbManager
	 * @throws Exception
	 */
	public String[] saveSMSRequest(PrpLsms prpLsms) throws Exception {
		String[] success = {"1",""};
		DBManager dbManager = new DBManager();
		try {
			dbManager.open("smsDataSource");
			dbManager.beginTransaction();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SMSRequest (");
			sql.append("Serial,Target,Corp_Id,Submit_Date,Dr_Flag,Language,Message");
			sql.append(")");
			sql.append(" values(?,?,?,?,?,?,?)");
			if("1".equals(prpLsms.getSmsFlag())){
				dbManager.prepareStatement(sql.toString());
				dbManager.setString(1, prpLsms.getSerial());
				dbManager.setString(2, prpLsms.getTarget());
				dbManager.setString(3, prpLsms.getCorp_Id());
				dbManager.setString(4, prpLsms.getSubmit_Date());
				dbManager.setString(5, prpLsms.getDr_Flag());
				dbManager.setString(6, prpLsms.getLanguage());
				dbManager.setString(7, prpLsms.getMessage());
				dbManager.executePreparedUpdate();
			}
			dbManager.commitTransaction();
		}catch (Exception e) {
			success[0] = "0";
			success[1] = e.getLocalizedMessage();
			dbManager.rollbackTransaction();
		}finally{
			dbManager.close();
		}
		return success;
	}
 	/**
	 * 删除简讯讯息
	 * @param prpLsmsId ：传入简讯讯息主键
	 */
	public void delete(String id) throws Exception {
		super.deleteByPK(PrpLsms.class, id);
	}
	/**
	 * 简讯讯息
	 * @param prpLsmsId ：简讯讯息主键
	 * @return 返回简讯讯息
	 */
	public PrpLsms findPrpLsms(String id) throws Exception {
		return super.get(PrpLsms.class, id);
	}
	/**
	 * 根据查询对象获取 简讯讯息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  简讯讯息的集合
	 */
	public List<PrpLsms> findPrpLsms(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
