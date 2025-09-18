package com.tlg.aps.bs.firDifficultWordImportServerce.impl;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firDifficultWordImportServerce.RewNoshowwordDataService;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.util.Result;

/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入*/
@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class RewNoshowwordDataServiceImpl implements RewNoshowwordDataService {
	
	private FirCtbcRewNoshowwordService firCtbcRewNoshowwordService;

	@Override
	public void removeFirCtbcRewNoshowword(BigDecimal oid) throws Exception {
		firCtbcRewNoshowwordService.removeFirCtbcRewNoshowword(oid);
	}
	
	@Override
	public Result insertFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws Exception {
		return firCtbcRewNoshowwordService.insertFirCtbcRewNoshowword(firCtbcRewNoshowword);
	}

	public FirCtbcRewNoshowwordService getFirCtbcRewNoshowwordService() {
		return firCtbcRewNoshowwordService;
	}

	public void setFirCtbcRewNoshowwordService(FirCtbcRewNoshowwordService firCtbcRewNoshowwordService) {
		this.firCtbcRewNoshowwordService = firCtbcRewNoshowwordService;
	}
	
}
