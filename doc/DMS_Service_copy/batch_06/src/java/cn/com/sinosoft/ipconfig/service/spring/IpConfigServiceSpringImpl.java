package cn.com.sinosoft.ipconfig.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.ipconfig.model.IpServiceConfig;
import cn.com.sinosoft.ipconfig.service.facade.IpConfigHelpService;
import cn.com.sinosoft.ipconfig.service.facade.IpConfigService;
import cn.com.sinosoft.ipconfig.vo.IpService;

public class IpConfigServiceSpringImpl extends GenericDaoHibernate implements IpConfigService {
	private IpConfigHelpService ipConfigHelpService;

	public List<IpService> getIpConfigs(String environmenTypeCode) {
		// TODO Auto-generated method stub
//		HashMap<String, IpServiceConfig> ipConfigMap=new HashMap<String, IpServiceConfig>(0);
		
		List<IpServiceConfig> ipServiceConfigs= ipConfigHelpService.findIpServiceConfigByEnvironmenTypeCode(environmenTypeCode);
		List<IpService> ipServiceList=new ArrayList<IpService>(0);
		if (ipServiceConfigs.size()>0) {
			
			for (int i = 0; i < ipServiceConfigs.size(); i++) {
				IpService ipService=new IpService();
				if ("1".equals(ipServiceConfigs.get(i).getValidStatus())) {
					ipService.setApplicationName(ipServiceConfigs.get(i).getApplicationName());
					ipService.setProteclType(ipServiceConfigs.get(i).getProtecltype());
					if (null!=ipServiceConfigs.get(i).getAppPassword()) {
						ipService.setAppPassword(ipServiceConfigs.get(i).getAppPassword());
					}
					if (null!=ipServiceConfigs.get(i).getAppUserName()) {
						ipService.setAppUserName(ipServiceConfigs.get(i).getAppUserName());
					}
					if (null!=ipServiceConfigs.get(i).getAreaCode()) {
						ipService.setAreaCode(ipServiceConfigs.get(i).getAreaCode());
					}
					ipService.setEnvironmentTypeCode(ipServiceConfigs.get(i).getIpEnvironmen().getEnvironmentTypeCode());
					if (null!=ipServiceConfigs.get(i).getMethods()) {
						ipService.setMethods(ipServiceConfigs.get(i).getMethods());
					}
					ipService.setServerIp(ipServiceConfigs.get(i).getServerIp());
					if (null!=ipServiceConfigs.get(i).getServerPort()) {
						ipService.setServerPort(ipServiceConfigs.get(i).getServerPort());
					}
					ipService.setServerType(ipServiceConfigs.get(i).getId().getServerType());
					ipServiceList.add(ipService);
				}
			}
		}
		return ipServiceList;
	}

	public IpConfigHelpService getIpConfigHelpService() {
		return ipConfigHelpService;
	}

	public void setIpConfigHelpService(IpConfigHelpService ipConfigHelpService) {
		this.ipConfigHelpService = ipConfigHelpService;
	}

}
