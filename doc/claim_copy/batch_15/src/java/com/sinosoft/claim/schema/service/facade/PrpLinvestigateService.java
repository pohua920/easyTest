package com.sinosoft.claim.schema.service.facade;
/**
 * 案情调查信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLinvestigate;
import com.sinosoft.claim.schema.model.PrpLinvestigateId;

public interface PrpLinvestigateService {
	
	/**
	 * 保存案情调查信息
	 * @param prpLinvestigate ：传入的案情调查信息
	 */
	public void save(PrpLinvestigate prpLinvestigate) throws Exception;
	
	/**
	 * 案情调查信息
	 * @param list  :传入的案情调查信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLinvestigate> list) throws Exception;
	
	/**
	 * 删除案情调查信息
	 * @param prpLinvestigateId ：传入的案情调查信息编号
	 */
	public void delete(PrpLinvestigateId prpLinvestigateId) throws Exception;

	/**
	 * 更新案情调查信息
	 * @param prpLinvestigate :传入需要更新的案情调查信息
	 */
	public void update(PrpLinvestigate prpLinvestigate) throws Exception;

	/**
	 * 根据案情调查信息编号查询出案情调查信息
	 * @param prpLinvestigateId ：传入的案情调查信息编号
	 * @return 返回案情调查信息
	 */
	public PrpLinvestigate findPrpLinvestigate(PrpLinvestigateId prpLinvestigateId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的案情调查信息页面信息
	 */
	public Page findPrpLinvestigate(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 案情调查信息页面信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的案情调查信息页面信息  的集合
	 */
	public List<PrpLinvestigate> findPrpLinvestigate(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据案情调查信息编号查询出案情调查信息
	 * @param certiNo ：传入的案情调查信息编号
	 * @return 返回案情调查信息
	 */
	public PrpLinvestigate findPrpLinvestigate(String certiNo) throws Exception;
}
