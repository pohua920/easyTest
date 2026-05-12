package com.sinosoft.claim.schema.service.facade;
/**
 * 涉诉赔案律师信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.Prpllawyer;
import com.sinosoft.claim.schema.model.PrpllawyerId;

public interface PrpllawyerService {
	
	/**
	 * 保存涉诉赔案律师信息
	 * @param prpllawyer ：传入的涉诉赔案律师信息
	 */
	public void save(Prpllawyer prpllawyer) throws Exception;
	
	/**
	 * 涉诉赔案律师信息
	 * @param list  :传入的涉诉赔案律师信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<Prpllawyer> list) throws Exception;
	
	/**
	 * 删除涉诉赔案律师信息
	 * @param prpllawyerId ：传入的涉诉赔案律师信息编号
	 */
	public void delete(PrpllawyerId prpllawyerId) throws Exception;

	/**
	 * 更新涉诉赔案律师信息
	 * @param prpllawyer :传入需要更新的涉诉赔案律师信息
	 */
	public void update(Prpllawyer prpllawyer) throws Exception;

	/**
	 * 根据涉诉赔案律师信息编号查询出涉诉赔案律师信息
	 * @param prpllawyerId ：传入的涉诉赔案律师信息编号
	 * @return 返回涉诉赔案律师信息
	 */
	public Prpllawyer findPrpllawyer(PrpllawyerId prpllawyerId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的涉诉赔案律师信息页面信息
	 */
	public Page findPrpllawyer(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  涉诉赔案律师信息页面信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的 涉诉赔案律师信息页面信息 的集合
	 */
	public List<Prpllawyer> findPrpllawyer(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据涉诉赔案律师信息编号查询出涉诉赔案律师信息
	 * @param certiNo ：传入的涉诉赔案律师信息编号
	 * @return 返回涉诉赔案律师信息
	 */
	public Prpllawyer findPrpllawyer(String certiNo) throws Exception;
}
