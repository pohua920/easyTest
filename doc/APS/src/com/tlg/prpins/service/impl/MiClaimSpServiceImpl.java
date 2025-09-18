package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.MiClaimSpDao;
import com.tlg.prpins.service.MiClaimSpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MiClaimSpServiceImpl implements MiClaimSpService{

	private MiClaimSpDao miClaimSpDao;

	@Override
	public Map<String, Object> runSpFetmispTonewclaim(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		miClaimSpDao.runSpFetmispTonewclaim(params);
		
		Map<String, Object> resultMap = new HashMap<>();
		if(params.containsKey("outResult")) {
			resultMap.put("outResult", (Integer)params.get("outResult"));
		}
		if(params.containsKey("outErrmsg")) {
			resultMap.put("outErrmsg", params.get("outErrmsg"));
		}
		if(params.containsKey("outSpmemo")) {
			resultMap.put("outSpmemo", params.get("outSpmemo"));
		}
		return resultMap;
	}

	public MiClaimSpDao getMiClaimSpDao() {
		return miClaimSpDao;
	}

	public void setMiClaimSpDao(MiClaimSpDao miClaimSpDao) {
		this.miClaimSpDao = miClaimSpDao;
	}
}
