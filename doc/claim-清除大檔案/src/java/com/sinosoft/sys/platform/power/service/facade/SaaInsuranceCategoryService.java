package com.sinosoft.sys.platform.power.service.facade;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaClass;


public interface SaaInsuranceCategoryService {
	public List<SaaClass> findSaaInsuranceCategoryList();
	public SaaClass findSaaClassByRiskCode(String riskCode);
}
