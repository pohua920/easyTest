package com.sinosoft.claim.schema.service.facade;
/**
 * 重开赔案接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLrecaseId;

public interface PrpLrecaseService {
	
	/**
	 * 保存重开赔案信息
	 * @param prpLrecase ：传入的重开赔案
	 */
	public void save(PrpLrecase prpLrecase) throws Exception;
	
	/**
	 * 重开赔案信息
	 * @param list  :传入的重开赔案信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLrecase> list) throws Exception;
	
	/**
	 * 删除重开赔案信息
	 * @param prpLrecaseId ：传入的重开赔案编号
	 */
	public void delete(PrpLrecaseId prpLrecaseId) throws Exception;

	/**
	 * 更新重开赔案信息
	 * @param prpLrecase :传入需要更新的重开赔案
	 */
	public void update(PrpLrecase prpLrecase) throws Exception;

	/**
	 * 根据重开赔案编号查询出重开赔案信息
	 * @param prpLrecaseId ：传入的重开赔案编号
	 * @return 返回重开赔案
	 */
	public PrpLrecase findPrpLrecase(PrpLrecaseId prpLrecaseId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的重开赔案页面信息
	 */
	public Page findPrpLrecase(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取重开赔案信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  重开赔案信息的集合
	 */
	public List<PrpLrecase> findPrpLrecase(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据重开赔案编号查询出重开赔案信息
	 * @param certiNo ：传入的重开赔案编号
	 * @return 返回重开赔案信息
	 */
	public PrpLrecase findPrpLrecase(String certiNo) throws Exception;
	
	/**
	 * 根据赔案号查询出重开赔案信息的数量
	 * @param claimNo ：传入的赔案号
	 * @return 重开赔案信息的数量
	 */
	public int getCount(String claimNo);
	/**
	 * 根据立案号查找业务号,如果没有重开过赔案,正常的流程是用registNo流转,如果是重开的赔案,用claimNo+serialNo最为业务流转
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public String findJbpmBusinessNo(String claimNo,boolean isRecase)throws Exception;

}
