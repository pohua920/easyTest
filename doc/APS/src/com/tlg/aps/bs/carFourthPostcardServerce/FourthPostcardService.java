package com.tlg.aps.bs.carFourthPostcardServerce;

import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.entity.TiiTvmcq;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public interface FourthPostcardService {
	
	public void insertTiiTvmcq(TiiTvmcq tiiTvmcq) throws Exception;
	
	public void updateTiiTvmcq(TiiTvmcq tiiTvmcq) throws Exception;
	
	public void insertRenewalnotice(Renewalnotice renewalnotice) throws Exception;
}
