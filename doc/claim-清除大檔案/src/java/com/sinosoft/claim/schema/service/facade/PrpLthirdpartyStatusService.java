package com.sinosoft.claim.schema.service.facade;
/**
 * 与第三方企业信息交互信息状态接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLthirdpartyStatus;
import com.sinosoft.claim.schema.model.PrpLthirdpartyStatusId;

public interface PrpLthirdpartyStatusService {
	
	/**
	 * 保存与第三方企业信息交互信息状态信息
	 * @param prpLthirdpartyStatus ：传入的与第三方企业信息交互信息状态
	 */
	public void save(PrpLthirdpartyStatus prpLthirdpartyStatus) throws Exception;
	
	/**
	 * 与第三方企业信息交互信息状态信息
	 * @param list  :传入的与第三方企业信息交互信息状态信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLthirdpartyStatus> list) throws Exception;
	
	/**
	 * 删除与第三方企业信息交互信息状态信息
	 * @param prpLthirdpartyStatusId ：传入的与第三方企业信息交互信息状态编号
	 */
	public void delete(PrpLthirdpartyStatusId prpLthirdpartyStatusId) throws Exception;

	/**
	 * 更新与第三方企业信息交互信息状态信息
	 * @param prpLthirdpartyStatus :传入需要更新的与第三方企业信息交互信息状态
	 */
	public void update(PrpLthirdpartyStatus prpLthirdpartyStatus) throws Exception;

	/**
	 * 根据与第三方企业信息交互信息状态编号查询出与第三方企业信息交互信息状态信息
	 * @param prpLthirdpartyStatusId ：传入的与第三方企业信息交互信息状态编号
	 * @return 返回与第三方企业信息交互信息状态
	 */
	public PrpLthirdpartyStatus findPrpLthirdpartyStatus(PrpLthirdpartyStatusId prpLthirdpartyStatusId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的与第三方企业信息交互信息状态页面信息
	 */
	public Page findPrpLthirdpartyStatus(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取第三方企业信息交互信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的第三方企业信息交互信息  的集合
	 */
	public List<PrpLthirdpartyStatus> findPrpLthirdpartyStatus(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据与第三方企业信息交互信息状态编号查询出与第三方企业信息交互信息状态信息
	 * @param certiNo ：传入的与第三方企业信息交互信息状态编号
	 * @return 返回与第三方企业信息交互信息状态
	 */
	public PrpLthirdpartyStatus findPrpLthirdpartyStatus(String certiNo) throws Exception;
}
