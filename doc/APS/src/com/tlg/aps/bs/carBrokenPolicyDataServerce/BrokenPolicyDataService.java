package com.tlg.aps.bs.carBrokenPolicyDataServerce;

import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.entity.TiiTvbcm;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
public interface BrokenPolicyDataService {
	
	public void insertTiiTvbcm(TiiTvbcm tiiTvbcm) throws Exception;
	
	public void updateTiiTvbcm(TiiTvbcm tiiTvbcm) throws Exception;
	
	public void insertRenewalnotice(Renewalnotice renewalnotice) throws Exception;
}
