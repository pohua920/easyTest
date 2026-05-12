package com.sinosoft.claim.schema.service.facade;

/**
 * 车辆驾驶员关系接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCopyCarDriver;
import com.sinosoft.claim.schema.model.PrpCopyCarDriverId;

public interface PrpCopyCarDriverService {

	/**
	 * 保存车辆驾驶员关系信息
	 * @param prpLcheck ：传入的车辆驾驶员关系
	 */
	public void save(PrpCopyCarDriver prpCopyCarDriver) throws Exception;

	/**
	 * 车辆驾驶员关系信息
	 * @param list :传入的车辆驾驶员关系信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyCarDriver> list) throws Exception;

	/**
	 * 删除车辆驾驶员关系信息
	 * @param prpCopyCarDriverId ：传入的车辆驾驶员关系编号
	 */
	public void delete(PrpCopyCarDriverId prpCopyCarDriverId) throws Exception;

	/**
	 * 更新车辆驾驶员关系信息
	 * @param prpCopyCarDriver :传入需要更新的车辆驾驶员关系
	 */
	public void update(PrpCopyCarDriver prpCopyCarDriver) throws Exception;

	/**
	 * 根据车辆驾驶员关系编号查询出车辆驾驶员关系信息
	 * @param prpCopyCarDriverId ：传入的车辆驾驶员关系编号
	 * @return 返回车辆驾驶员关系
	 */
	public PrpCopyCarDriver findPrpCopyCarDriver(PrpCopyCarDriverId prpCopyCarDriverId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车辆驾驶员关系页面信息
	 */
	public Page findPrpCopyCarDriver(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取车辆驾驶员关系的列表
	 * @param queryRule 查询对象
	 * @return 包含的车辆驾驶员关系的列表
	 */
	public List<PrpCopyCarDriver> findPrpCopyCarDriver(QueryRule queryRule) throws Exception;
}
