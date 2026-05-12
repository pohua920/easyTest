package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaCompany;

public interface SaaCompanyService {
	public List<SaaCompany> getComByValidstatus(String Validstatus);

	public Page findCompany(QueryRule queryRule, int pageNo, int pageSize);

	public String getComCname(String comCode) ;

}
