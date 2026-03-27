package com.sinosoft.claim.schema.service.facade;
/**
 * 报案信息补充说明接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistExtId;

public interface PrpLregistExtService {
	
	/**
	 * 保存报案信息补充说明信息
	 * @param prpLregistExt ：传入的报案信息补充说明
	 */
	public void save(PrpLregistExt prpLregistExt) throws Exception;
	
	/**
	 * 保存报案信息补充说明信息
	 * @param list:保存报案信息补充说明信息
	 */
	public void save(List<PrpLregistExt> list) throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLregistExt> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLregistExt prpLregistExt)throws Exception;
	
	/**
	 * 删除报案信息补充说明信息
	 * @param prpLregistExtId ：传入的报案信息补充说明编号
	 */
	public void delete(PrpLregistExtId prpLregistExtId) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;

	/**
	 * 更新报案信息补充说明信息
	 * @param prpLregistExt :传入需要更新的报案信息补充说明
	 */
	public void update(PrpLregistExt prpLregistExt) throws Exception;

	/**
	 * 根据报案信息补充说明编号查询出报案信息补充说明信息
	 * @param prpLregistExtId ：传入的报案信息补充说明编号
	 * @return 返回报案信息补充说明
	 */
	public PrpLregistExt findPrpLregistExt(PrpLregistExtId prpLregistExtId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案信息补充说明页面信息
	 */
	public Page findPrpLregistExt(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取报案信息补充说明信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  报案信息补充说明信息的集合
	 */
	public List<PrpLregistExt> findPrpLregistExt(QueryRule queryRule) throws Exception;
}
