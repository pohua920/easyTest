package com.sinosoft.claim.schema.service.facade;
/**
 * 投保机动车新增设备信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcarDevice;
import com.sinosoft.claim.schema.model.PrpCcarDeviceId;

public interface PrpCcarDeviceService {
	
	/**
	 * 保存投保机动车新增设备信息
	 * @param PrpCcarDevice ：传入的投保机动车新增设备信息
	 */
	public void save(PrpCcarDevice PrpCcarDevice) throws Exception;
	
	/**
	 * 投保机动车新增设备信息信息
	 * @param list  :传入的投保机动车新增设备信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCcarDevice> list) throws Exception;
	
	/**
	 * 删除投保机动车新增设备信息信息
	 * @param PrpCcarDeviceId ：传入的投保机动车新增设备信息编号
	 */
	public void delete(PrpCcarDeviceId PrpCcarDeviceId) throws Exception;

	/**
	 * 更新投保机动车新增设备信息信息
	 * @param PrpCcarDevice :传入需要更新的投保机动车新增设备信息
	 */
	public void update(PrpCcarDevice PrpCcarDevice) throws Exception;

	/**
	 * 根据投保机动车新增设备信息编号查询出投保机动车新增设备信息信息
	 * @param PrpCcarDeviceId ：传入的投保机动车新增设备信息编号
	 * @return 返回投保机动车新增设备信息
	 */
	public PrpCcarDevice findPrpCcarDevice(PrpCcarDeviceId PrpCcarDeviceId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的投保机动车新增设备信息页面信息
	 */
	public Page findPrpCcarDevice(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**
	 * 根据查询对象获取机动车新增设备信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的机动车新增设备信息列表
	 */
	public List<PrpCcarDevice> findPrpCcarDevice(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据投保机动车新增设备信息编号查询出投保机动车新增设备信息信息
	 * @param certiNo ：传入的投保机动车新增设备信息编号
	 * @return 返回投保机动车新增设备信息
	 */
	public PrpCcarDevice findPrpCcarDevice(String certiNo) throws Exception;
}
