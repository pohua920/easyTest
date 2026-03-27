package com.sinosoft.claim.schema.service.facade;

/**
 * 实收实付记录转储表接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.PrpJPayRefRecHis;

public interface PrpJPayRefRecHisService {
	/**
	 * 根据单证分组编号、业务号码、交费计划序号、收付原因 查询出实收实付记录转储信息
	 * @param certiType ：传入的单证分组编号
	 * @param certiNo ：业务号码
	 * @param serialNo ：交费计划序号
	 * @param payRefReason ：收付原因
	 * @return 返回实收实付记录转储信息
	 */
	public PrpJPayRefRecHis findPrpJPayRefRecHis(String certiType, String certiNo, Integer serialNo, String payRefReason) throws Exception;

	/**
	 * 保存实收实付记录转储信息
	 * @param prpJPayRefRecHis ：传入的实收实付记录转储信息
	 */
	public void save(PrpJPayRefRecHis prpJPayRefRecHis) throws Exception;

	/**
	 * 删除实收实付记录转储信息
	 * @param payRefNo ：传入的收付确认号
	 */
	public void deleteByPayRefNo(String payRefNo) throws Exception;

	/**
	 * 删除实收实付记录转储信息
	 * @param certiType ：传入的单证分组编号
	 * @param certiNo ：业务号码
	 * @param serialNo ：交费计划序号
	 * @param payRefReason ：收付原因
	 * @param payRefTimes ：收付次数
	 */
	public void delete(String certiType, String certiNo, Integer serialNo, String payRefReason, Integer payRefTimes) throws Exception;

	/**
	 * 根据查询条件获取 实收实付记录转储信息的列表
	 * @param conditions 查询条件
	 * @return 包含的实收实付记录转储信息 的列表
	 */
	public List<PrpJPayRefRecHis> findPrpJPayRefRecHis(String conditions) throws Exception;

	/**
	 * 更新实收实付记录转储信息
	 * @param prpGroup :传入需要更新的实收实付记录转储信息
	 */
	public void update(PrpJPayRefRecHis prpJPayRefRecHis) throws Exception;

	/**
	 * 根据查询条件获取 实收实付记录转储信息的列表
	 * @param conditions 查询条件
	 * @return 包含的实收实付记录转储信息 的列表
	 */
	public List<?> findByQueryConditions(String conditions) throws Exception;
}
