package cn.com.sinosoft.ipconfig.service.facade;

import java.util.List;

import cn.com.sinosoft.ipconfig.vo.IpService;

public interface IpConfigService {

	public List<IpService> getIpConfigs(String environmenTypeCode);
}