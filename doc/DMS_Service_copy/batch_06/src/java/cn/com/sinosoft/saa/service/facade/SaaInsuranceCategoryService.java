package cn.com.sinosoft.saa.service.facade;

import java.util.List;

import cn.com.sinosoft.saa.model.SaaClass;

public interface SaaInsuranceCategoryService {
	public List<SaaClass> findSaaInsuranceCategoryList();
	public SaaClass findSaaClassByRiskCode(String riskCode);
}
