package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDkind;
import com.sinosoft.claim.schema.model.PrpDkindId;

public interface PrpDkindService {
	/**
	 * 根据子险种代码，险种得到子险种名称
	 * @param userCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCode(String riskCode, String kindCode, boolean isChinese);

	/**
	 * PrpDkind表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PrpDkind findPrpDkindById(PrpDkindId id) throws Exception;
	
	public List<PrpDkind> findByConditions(String conditions) throws Exception ;
}
