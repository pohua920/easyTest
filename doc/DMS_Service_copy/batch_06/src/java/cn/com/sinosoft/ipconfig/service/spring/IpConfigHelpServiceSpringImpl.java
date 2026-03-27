package cn.com.sinosoft.ipconfig.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import cn.com.sinosoft.ipconfig.model.IpEnvironmen;
import cn.com.sinosoft.ipconfig.model.IpServiceConfig;
import cn.com.sinosoft.ipconfig.service.facade.IpConfigHelpService;


public class IpConfigHelpServiceSpringImpl extends GenericDaoHibernate implements IpConfigHelpService {

	public Properties readProp() throws IOException {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		properties.load(IpConfigHelpServiceSpringImpl.class.getResourceAsStream("/config/ipconfig.properties"));
		return properties;
	}

	public HashMap<String, HashMap<String, IpServiceConfig>> getClientMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<IpServiceConfig> findIpServiceConfigByEnvironmenTypeCode(String environmenTypeCode){
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("environmentTypeCode", environmenTypeCode);
		queryRule.addEqual("validStatus", "1");
		List<IpEnvironmen> ipEnviromenList=super.find(IpEnvironmen.class, queryRule);
		
		if (ipEnviromenList.size()>0) {
			return ipEnviromenList.get(0).getIpServiceConfigs();
		}else{
			List<IpServiceConfig> nullIP=new ArrayList();
			return nullIP;
		}
		
	}


}
