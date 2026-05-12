package com.sinosoft.claim.schema.service.facade;
/**
 * 担保函明细（船舶）接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLassureDetail;
import com.sinosoft.claim.schema.model.PrpLassureDetailId;

public interface PrpLassureDetailService {
	
	/**
	 * 保存担保函明细（船舶）信息
	 * @param prpLassureDetail ：传入的担保函明细（船舶）
	 */
	public void save(PrpLassureDetail prpLassureDetail) throws Exception;
	
	/**
	 * 担保函明细（船舶）信息
	 * @param list  :传入的担保函明细（船舶）信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLassureDetail> list) throws Exception;
	
	/**
	 * 删除担保函明细（船舶）信息
	 * @param prpLassureDetailId ：传入的担保函明细（船舶）编号
	 */
	public void delete(PrpLassureDetailId prpLassureDetailId) throws Exception;

	/**
	 * 更新担保函明细（船舶）信息
	 * @param prpLassureDetail :传入需要更新的担保函明细（船舶）
	 */
	public void update(PrpLassureDetail prpLassureDetail) throws Exception;

	/**
	 * 根据担保函明细（船舶）编号查询出担保函明细（船舶）信息
	 * @param prpLassureDetailId ：传入的担保函明细（船舶）编号
	 * @return 返回担保函明细（船舶）
	 */
	public PrpLassureDetail findPrpLassureDetail(PrpLassureDetailId prpLassureDetailId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的担保函明细（船舶）页面信息
	 */
	public Page findPrpLassureDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取担保函明细（船舶）  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 担保函明细（船舶） 的列表
	 */
	public List<PrpLassureDetail> findPrpLassureDetail(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据担保函明细（船舶）编号查询出担保函明细（船舶）信息
	 * @param certiNo ：传入的担保函明细（船舶）编号
	 * @return 返回担保函明细（船舶）
	 */
	public PrpLassureDetail findPrpLassureDetail(String certiNo) throws Exception;
}
