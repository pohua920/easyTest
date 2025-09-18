package com.tlg.aps.bs.firDifficultWordImportServerce;

import java.math.BigDecimal;

import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.util.Result;

/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入*/
public interface RewNoshowwordDataService {

	public void removeFirCtbcRewNoshowword(BigDecimal oid) throws Exception;
	
	public Result insertFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws Exception;
	
}
