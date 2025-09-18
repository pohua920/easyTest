package com.tlg.aps.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.xchg.entity.Rfrcode;

public interface PremiumCalService {

	public Map<String, BigDecimal> calSugAmt(String sumfloors, String postCode, String structure, String wallMaterial, String buildArea, String startDate,Map<String,Rfrcode> mapRfrCode) throws SystemException, Exception;

}
