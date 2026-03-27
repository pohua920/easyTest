package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
import java.util.Date;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLuser;
//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
 */
public interface PrpLuserService {
	/**
	 * 保存邮件细节信息
	 * @param prpLemailDetail ：传入的邮件细节
	 */
	public void save(PrpLuser prpLemailConfig) throws Exception;
	
	/**
	 * 邮件细节信息
	 * @param list  :传入的邮件细节信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLuser> list) throws Exception;
	
	/**
	 * 删除邮件细节信息
	 * @param prpLemailDetailId ：传入的邮件细节编号
	 */
	public void delete(String id) throws Exception;

	/**
	 * 更新邮件细节信息
	 * @param prpLemailDetail :传入需要更新的邮件细节
	 */
	public void update(PrpLuser prpLuser) throws Exception;

	/**
	 * 根据邮件细节编号查询出邮件细节信息
	 * @param prpLemailDetailId ：传入的邮件细节编号
	 * @return 返回邮件细节
	 */
	public PrpLuser findPrpLuser(String id) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的邮件细节页面信息
	 */
	public Page findPrpLuser(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取邮件细节  的集合
	 * @param queryRule 查询对象
	 * @return 包含的邮件细节  的集合
	 */
	public List<PrpLuser> findPrpLuser(QueryRule queryRule) throws Exception;
	
	/**
	 * 
	 */
	public PrpLuser findPrpLuserByUserCode(String userCode) throws Exception;
	/**
	 * 
	 */
	public PrpLuser findPrpLuserById(String userCode) throws Exception;

	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護
	 * @param statements
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findPrpLuser(String statements, Object[] params, int pageNo,
			int pageSize);
	
	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	 */
	public Date getRecordDateByUserCode(String userCode) throws UserException, Exception ;
	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	 */
	public int countPrpLuserByUserCode(String userCode) throws Exception;
}
