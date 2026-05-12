package com.sinosoft.claim.schema.service.spring;
/**
 * 设备险标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemDevice;
import com.sinosoft.claim.schema.model.PrpCitemDeviceId;
import com.sinosoft.claim.schema.service.facade.PrpCitemDeviceService;

public class PrpCitemDeviceServiceSpringImpl extends
GenericDaoHibernate<PrpCitemDevice, PrpCitemDeviceId> implements PrpCitemDeviceService{

	@Override
	public void save(PrpCitemDevice PrpCitemDevice) throws Exception {
		logger.info("保存设备险标的信息信息");
		super.save(PrpCitemDevice);
		
	}

	@Override
	public void save(List<PrpCitemDevice> list) throws Exception {
		logger.info("保存设备险标的信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemDeviceId PrpCitemDeviceId) throws Exception {
		logger.info("删除设备险标的信息编号为" + PrpCitemDeviceId + "的设备险标的信息");
		super.deleteByPK(PrpCitemDevice.class, PrpCitemDeviceId);
	}

	@Override
	public PrpCitemDevice findPrpCitemDevice(PrpCitemDeviceId PrpCitemDeviceId) throws Exception {
		logger.info("查询设备险标的信息编号为" + PrpCitemDeviceId + "的设备险标的信息");
		return super.get(PrpCitemDevice.class, PrpCitemDeviceId);
	}

	@Override
	public Page findPrpCitemDevice(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取设备险标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemDevice> findPrpCitemDevice(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出设备险标的信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCitemDevice findPrpCitemDevice(String certiNo) throws Exception{
		PrpCitemDevice PrpCitemDevice = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCitemDevice> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemDevice = resultList.get(0);
		}
		return PrpCitemDevice;
	}

}
