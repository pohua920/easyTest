package com.sinosoft.claim.schema.service.spring;
/**
 * 投保机动车新增设备信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcarDevice;
import com.sinosoft.claim.schema.model.PrpCcarDeviceId;
import com.sinosoft.claim.schema.service.facade.PrpCcarDeviceService;

public class PrpCcarDeviceServiceSpringImpl extends
GenericDaoHibernate<PrpCcarDevice, PrpCcarDeviceId> implements PrpCcarDeviceService{

	@Override
	public void save(PrpCcarDevice PrpCcarDevice) throws Exception {
		logger.info("保存投保机动车新增设备信息信息");
		super.save(PrpCcarDevice);
		
	}

	@Override
	public void save(List<PrpCcarDevice> list) throws Exception {
		logger.info("保存投保机动车新增设备信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCcarDeviceId PrpCcarDeviceId) throws Exception {
		logger.info("删除投保机动车新增设备信息编号为" + PrpCcarDeviceId + "的投保机动车新增设备信息");
		super.deleteByPK(PrpCcarDevice.class, PrpCcarDeviceId);
	}

	@Override
	public PrpCcarDevice findPrpCcarDevice(PrpCcarDeviceId PrpCcarDeviceId) throws Exception {
		logger.info("查询投保机动车新增设备信息编号为" + PrpCcarDeviceId + "的投保机动车新增设备信息");
		return super.get(PrpCcarDevice.class, PrpCcarDeviceId);
	}

	@Override
	public Page findPrpCcarDevice(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取投保机动车新增设备信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCcarDevice> findPrpCcarDevice(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出投保机动车新增设备信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCcarDevice findPrpCcarDevice(String certiNo) throws Exception{
		PrpCcarDevice PrpCcarDevice = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCcarDevice> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCcarDevice = resultList.get(0);
		}
		return PrpCcarDevice;
	}

}
