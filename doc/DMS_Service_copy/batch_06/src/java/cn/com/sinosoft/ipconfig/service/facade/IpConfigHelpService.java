package cn.com.sinosoft.ipconfig.service.facade;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import cn.com.sinosoft.ipconfig.model.IpServiceConfig;

public interface IpConfigHelpService {
	
	public Properties readProp() throws IOException;
	
	public HashMap<String, HashMap<String, IpServiceConfig>> getClientMap();
	
	public List<IpServiceConfig> findIpServiceConfigByEnvironmenTypeCode(String environmenTypeCode);

}
