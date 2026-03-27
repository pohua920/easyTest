package com.sinosoft.claim.schema.service.facade;
/**
 * :理赔流转讨论留言接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLmessage;
import com.sinosoft.claim.schema.model.PrpLmessageId;

public interface PrpLmessageService {
	
	/**
	 * 保存:理赔流转讨论留言信息
	 * @param prpLmessage ：传入的:理赔流转讨论留言
	 */
	public void save(PrpLmessage prpLmessage) throws Exception;
	
	/**
	 * :理赔流转讨论留言信息
	 * @param list  :传入的:理赔流转讨论留言信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLmessage> list) throws Exception;
	
	/**
	 * 删除:理赔流转讨论留言信息
	 * @param prpLmessageId ：传入的:理赔流转讨论留言编号
	 */
	public void delete(PrpLmessageId prpLmessageId) throws Exception;

	/**
	 * 更新:理赔流转讨论留言信息
	 * @param prpLmessage :传入需要更新的:理赔流转讨论留言
	 */
	public void update(PrpLmessage prpLmessage) throws Exception;

	/**
	 * 根据:理赔流转讨论留言编号查询出:理赔流转讨论留言信息
	 * @param prpLmessageId ：传入的:理赔流转讨论留言编号
	 * @return 返回:理赔流转讨论留言
	 */
	public PrpLmessage findPrpLmessage(PrpLmessageId prpLmessageId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的:理赔流转讨论留言页面信息
	 */
	public Page findPrpLmessage(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取理赔流转讨论留言信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  理赔流转讨论留言信息的集合
	 */
	public List<PrpLmessage> findPrpLmessage(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据:理赔流转讨论留言编号查询出:理赔流转讨论留言信息
	 * @param certiNo ：传入的:理赔流转讨论留言编号
	 * @return 返回:理赔流转讨论留言
	 */
	public PrpLmessage findPrpLmessage(String certiNo) throws Exception;
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 获取留言信息
	 */
	public int findMaxNo(String registNo)throws Exception;
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询留言
	 */
	public List<PrpLmessage> findPrpLmessageByRegistNo(String registNo)throws Exception;
}
