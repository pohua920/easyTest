package com.sinosoft.claim.schema.service.facade;
/**
 * 担保函（船舶）接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLassure;

public interface PrpLassureService {
	
	/**
	 * 担保函（船舶）信息
	 * @param PrpLassure ：传入的担保函（船舶）
	 */
	public void save(PrpLassure prpLassure) throws Exception;
	
	/**
	 * 保存担保函（船舶）
	 * @param list  :传入的担保函（船舶）集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLassure> list) throws Exception;
	
	/**
	 * 删除担保函（船舶）
	 * @param policyNo ：传入的担保函（船舶）
	 */
	public void delete(String assureNo) throws Exception;

	/**
	 * 更新担保函（船舶）信息
	 * @param PrpLassure :传入需要更新的担保函（船舶）
	 */
	public void update(PrpLassure prpLassure) throws Exception;

	/**
	 * 根据担保函（船舶）编号查询出保单担保函（船舶）
	 * @param policyNo ：传入的担保函（船舶）编号
	 * @return 返回担保函（船舶）
	 */
	public PrpLassure findPrpLassure(String assureNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的担保函（船舶）信息
	 */
	public Page findPrpLassure(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取担保函（船舶）的列表
	 * @param queryRule 查询对象
	 * @return 包含的担保函（船舶）的列表
	 */
	public List<PrpLassure> findPrpLassure(QueryRule queryRule) throws Exception;
}
