package com.sinosoft.common.service.spring;


import ins.framework.dao.GenericDaoHibernate;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.common.schema.model.PrpDcompany;
import com.sinosoft.common.service.facade.PrpDcompanyService;
import com.sinosoft.prpins.policy.schema.vo.UtiIUserVO;

/**
 * 機構服務實現
 * @author Sinosoft
 */
public class PrpDcompanyServiceSpringImpl extends
GenericDaoHibernate<PrpDcompany, String> implements PrpDcompanyService{

	/**
	 * 查詢機構對象
	 * @param ComCode 機構代碼
	 * @return PrpDcompany 機構對象
	 * @throws Exception
	 */
	public PrpDcompany findByComCode(String ComCode) throws Exception{
			return super.get(ComCode);
	}

	/**
	 * 校驗用戶允許機構權限
	 * @param powerSQL SQL查詢條件
	 * @param comCode 機構代碼
	 * @return boolean true/false
	 * @throws Exception
	 */
	public boolean findByWhereStrAndComCode(String powerSQL, String comCode) throws Exception{
		String hql = "from PrpDcompany where comCode= ? and "+ powerSQL;
		List<PrpDcompany> prpDcompanyList = super.findTopByHql(hql,1,comCode);
		if(prpDcompanyList!=null && prpDcompanyList.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 查詢機構對象集合
	 * @param conditions SQL查詢條件
	 * @return List 機構對象集合
	 * @throws Exception
	 */
	public List<PrpDcompany> getDatasByConditions(String conditions) throws Exception{
		
		List<PrpDcompany> prpDcompanys = new ArrayList<PrpDcompany>(0);
		String hql = "select a from PrpDcompany a";
		if(conditions != null && !"".equals(conditions)){
			hql = hql + " where " + conditions;
			
			prpDcompanys = super.findByHql(hql);
		}
		return prpDcompanys;
	}
	/**
	 * 查詢當前機構所屬省的機構代碼
	 * @param comCode 機構代碼
	 * @return String 機構代碼
	 * @throws Exception
	 */
	public String findProvinceComCode(String comCode) {
		String upperpath = null;
		String []upperpaths = null;
		String provinceComCode = null;
		if(comCode !=null && !"".equals(comCode)){
//			String sql="select c.upperpath from prpdcompany  c where c.comcode  =?";
//			List  list  = this.findBySql(sql, comCode);
		    List  list  = null;
			if(list !=null && !list.isEmpty()){
				upperpath=(String) list.get(0);
				if(upperpath != null && !"".equals(upperpath)){
					upperpaths = upperpath.split(",");
					if(upperpaths.length >= 2){
						provinceComCode = upperpaths[1];
					}else if(upperpaths.length == 1){
						provinceComCode = upperpaths[0];
					}
				}
			}
		}
		return provinceComCode ;
	}
	/**
	 * 查詢機構對象集合
	 * @param parentID 父級標識
	 * @return List 機構對象集合
	 * @throws Exception
	 */
	public List<UtiIUserVO>  getSubSystemListByParentId(String parentID){
		List<UtiIUserVO> utiIUserVOList = new ArrayList<UtiIUserVO>(0);
		UtiIUserVO utiIUserVO = null;
		List<PrpDcompany> prpdCompanyList = null;
		String hql = " select prpdCompany from PrpDcompany prpdCompany where prpdCompany.upperComCode = ? and prpdCompany.comCode<> ? and prpdCompany.validStatus = ? order by prpdCompany.comCName";
		prpdCompanyList = super.findByHql(hql, parentID, parentID,"1");
		PrpDcompany company = null;
		for (int i = 0; i < prpdCompanyList.size(); i++) {
			company = prpdCompanyList.get(i);
			utiIUserVO = new UtiIUserVO();
			utiIUserVO.setComCode(company.getComCode());
			utiIUserVO.setComCName(company.getComCName());
			utiIUserVO.setUpperComCode(company.getUpperComCode());
			utiIUserVOList.add(utiIUserVO);
		}
		return utiIUserVOList;
	}
	/**
	 * 查詢當前機構的上級機構
	 * @param comCode 機構代碼
	 * @return String 上級機構
	 * @throws Exception
	 */
	public String findProvinceCom(String comCode) {
		String upperpath = null;
		String []upperpaths = null;
		String provinceComCode = null;
		if(comCode !=null && !"".equals(comCode)){
			String sql="select c.upperpath from prpdcompany  c where c.comcode  =?";
			List  list  = this.findBySql(sql, comCode);
			if(list !=null && !list.isEmpty()){
				upperpath=(String) list.get(0);
				if(upperpath != null && !"".equals(upperpath)){
					provinceComCode = upperpath;
				}
			}
		}
		return provinceComCode ;
	}
}

