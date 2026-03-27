package com.sinosoft.claim.schema.service.facade;

/**
 * 保单隶属接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpCmainSubId;

public interface PrpCmainSubService {

	/**
	 * 保存保单隶属信息
	 * @param prpLcheck ：传入的保单隶属
	 */
	public void save(PrpCmainSub prpCmainSub) throws Exception;

	/**
	 * 保单隶属信息
	 * @param list :传入的保单隶属信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainSub> list) throws Exception;

	/**
	 * 删除保单隶属信息
	 * @param prpCmainSubId ：传入的保单隶属编号
	 */
	public void delete(PrpCmainSubId prpCmainSubId) throws Exception;

	/**
	 * 更新保单隶属信息
	 * @param prpCmainSub :传入需要更新的保单隶属
	 */
	public void update(PrpCmainSub prpCmainSub) throws Exception;

	/**
	 * 根据保单隶属编号查询出保单隶属信息
	 * @param prpCmainSubId ：传入的保单隶属编号
	 * @return 返回保单隶属
	 */
	public PrpCmainSub findPrpCmainSub(PrpCmainSubId prpCmainSubId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单隶属页面信息
	 */
	public Page findPrpCmainSub(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取保单隶属的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单隶属的列表
	 */
	public List<PrpCmainSub> findPrpCmainSub(QueryRule queryRule) throws Exception;
}
