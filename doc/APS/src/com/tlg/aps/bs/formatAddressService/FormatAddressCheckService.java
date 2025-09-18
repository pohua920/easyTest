package com.tlg.aps.bs.formatAddressService;

import java.util.Map;

/** mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化  **/
public interface FormatAddressCheckService {
	
	public Map<String,Object> formatAddressCheck(String address);

	
}
