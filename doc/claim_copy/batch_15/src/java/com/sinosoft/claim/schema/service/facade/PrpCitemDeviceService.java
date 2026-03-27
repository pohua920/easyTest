package com.sinosoft.claim.schema.service.facade;
/**
 * 设备险标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemDevice;
import com.sinosoft.claim.schema.model.PrpCitemDeviceId;

public interface PrpCitemDeviceService {
	
	/**
	 * 保存设备险标的信息信息
	 * @param PrpCitemDevice ：传入的设备险标的信息
	 */
	public void save(PrpCitemDevice PrpCitemDevice) throws Exception;
	
	/**
	 * 设备险标的信息信息
	 * @param list  :传入的设备险标的信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemDevice> list) throws Exception;
	
	/**
	 * 删除设备险标的信息信息
	 * @param PrpCitemDeviceId ：传入的设备险标的信息编号
	 */
	public void delete(PrpCitemDeviceId PrpCitemDeviceId) throws Exception;

	/**
	 * 更新设备险标的信息信息
	 * @param PrpCitemDevice :传入需要更新的设备险标的信息
	 */
	public void update(PrpCitemDevice PrpCitemDevice) throws Exception;

	/**
	 * 根据设备险标的信息编号查询出设备险标的信息信息
	 * @param PrpCitemDeviceId ：传入的设备险标的信息编号
	 * @return 返回设备险标的信息
	 */
	public PrpCitemDevice findPrpCitemDevice(PrpCitemDeviceId PrpCitemDeviceId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的设备险标的信息页面信息
	 */
	public Page findPrpCitemDevice(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  设备险标的列表
	 * @param queryRule 查询对象
	 * @return 包含的 设备险标 的列表
	 */
	public List<PrpCitemDevice> findPrpCitemDevice(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据设备险标的信息编号查询出设备险标的信息信息
	 * @param certiNo ：传入的设备险标的信息编号
	 * @return 返回设备险标的信息
	 */
	public PrpCitemDevice findPrpCitemDevice(String certiNo) throws Exception;
}
