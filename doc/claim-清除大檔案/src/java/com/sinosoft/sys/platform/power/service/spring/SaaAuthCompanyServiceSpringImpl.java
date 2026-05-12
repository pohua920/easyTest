/**
 * 
 */
package com.sinosoft.sys.platform.power.service.spring;

import java.util.ArrayList;
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaAuthCompany;
import com.sinosoft.sys.platform.power.service.facade.SaaAuthCompanyService;
import com.sinosoft.sys.platform.power.vo.CompanyVO;

/**
 * @author 中科软
 */
public class SaaAuthCompanyServiceSpringImpl extends GenericDaoHibernate<SaaAuthCompany,Long>
implements SaaAuthCompanyService{
	private CompanyService companyService;
	/* 
	 * linsiming-wb 
	 * 2011-8-16
	 */
	@Override
	public Page findSaaAuthCompany(QueryRule queryRule, int pageNo, int pageSize) {
		Page page = super.find(queryRule, pageNo, pageSize);
		List<SaaAuthCompany> saaAuthCompanyList = page.getResult();
		List<CompanyVO> companyVOList = new ArrayList<CompanyVO>(0);
		if (saaAuthCompanyList.size() != 0) {
			for (SaaAuthCompany itemCompany : saaAuthCompanyList) {
				CompanyVO companyVO = new CompanyVO();
				
				companyVO.setComCode(itemCompany.getComCode());
				if ("".equals(itemCompany.getComCode())) {
					break;
				}
				SaaCompany prpDcompany  = companyService.findPrpDcompanyByComCode(itemCompany.getComCode());
				if (prpDcompany==null) {
					System.out.println("now the result is null");
					break;
				}
				companyVO.setComName(prpDcompany.getComCName());
				companyVOList.add(companyVO);
				super.evict(companyVO);
			}
		}
		return new Page(0, page.getTotalCount(), pageSize, companyVOList);
	}
	
	/*--------------------注入方法--------------------------*/
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
