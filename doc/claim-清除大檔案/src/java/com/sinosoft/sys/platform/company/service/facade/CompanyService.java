package com.sinosoft.sys.platform.company.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;
import java.util.Set;

import com.sinosoft.sys.platform.power.model.SaaCompany;

public interface CompanyService {

	public List<SaaCompany> listMatchesCompany(String comMatches);
	
	/**
	 * 由险种代码查询出险种名称（addBy raoguangpu）
	 */
	public String findComCNameByComCode(String comCode);

	
	public List<SaaCompany> getMatchesCompany(String comMatches);
	/**
	 * 得到当前归属
	 * 
	 * @param userCode
	 * @return
	 */
	public String getCompanyCode(String userCode);

	/**
	 * 得到当前机构的下属机构
	 * 
	 * @param userCode
	 * @return
	 */
	public String getSubCompanyCode(String comCode);
	
	/**
	 * 得到当前机构的下属机构
	 * @param comCode
	 * @return
	 * add by zoulijuan 20120427
	 */
	public List<SaaCompany> getSubCompanyCodeList(String comCode) ;

	/**
	 * 得到当前机构的下属机构但不包括本身的串
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCompanyCodeStr(String comCode);

	/**
	 * 得到当前机构及下属机构
	 * 
	 * @param userCode
	 * @return
	 */
	public String getCompanyCodeAndSubCompanyCode(String userCode);

	/**
	 * 得到有权限的机构(报表)
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return 字符串,格式类似：'11010800','11010801'
	 */
	public String getPermitCompany(String userCode, String taskCode);

	/**
	 * 得到有权限的机构(用'分隔)
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return 字符串,格式类似：'11010800','11010801'
	 */
	public String getPermitCompanys(String userCode, String taskCode);

	/**
	 * 得到有权限的机构
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return PrpDcompany列表
	 */
	public List<SaaCompany> listPermitCompany(String userCode, String taskCode);
	
	/**
	 * 得到岗位下的机构代码列表
	 * @param userCode
	 * @param query
	 * @return
	 */
	public List<SaaCompany> listGradePermitCompanyCodes(String userCode,String query) ;
	
	/**
	 * 得到岗位下的机构代码列表
	 * @param userCode
	 * @param query
	 * @param type
	 * @return
	 */
	public List<SaaCompany> listGradePermitCompanyCodes(String userCode,String query ,String type) ;
	
	public List<SaaCompany> listGradePermitCompanyCodes(String userCode, String query, String type, String gradeId);

	/**
	 * 根据机构以代码查询出一条对象
	 * 
	 * @param comCode
	 * @return
	 */
	public SaaCompany getPrpDcompanyByComCode(String comCode);

	/**
	 * 得到有权限的机构
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return 字符串,格式类似：11010800,11010801
	 */

	public String getPermitCompanyCode(String userCode, String taskCode);

	/**
	 * 得到当前机构的下属机构
	 * 
	 * @param prpDcompanys
	 * @return
	 */
	public List<SaaCompany> listSubPrpDcompany(List<SaaCompany> prpDcompanys);

	/**
	 * 省内机构
	 * 
	 * @param userComCode
	 * @param level
	 * @return
	 */
	public List<SaaCompany> listLocalCompany(String userComCode, int level);

	/**
	 * 省间机构
	 * 
	 * @param userComCode
	 * @param level
	 * @return
	 */
	public List<SaaCompany> listRemoteCompany(String userComCode, int level);

	public SaaCompany findPrpDcompanyByComCode(String comCode);

	public void addPrpDcompany(SaaCompany prpDcompany);

	public void updatePrpDcompany(SaaCompany prpDcompany);

	public void deletePrpDcompany(String comCode);

	public boolean getPrpDcompanyByUpperCode(String upperComCode);

	/**
	 * 返回市级机构代码，如果没有返回空串。
	 * 
	 * @param comCode
	 *            机构代码
	 * @return 市级机构代码，如果没有返回空串。
	 */
	public String getCityComCode(String comCode);

	/**
	 * 是否同一个市的直接下属机构代码
	 * 
	 * @param comCode
	 *            机构代码（变参）
	 * @return 是同一个市的则返回true，否则返回false
	 */
	public boolean isSameCityComCode(String... comCodes);

	public boolean isSameCityComCode(String comCodes);

	/**
	 * 是否同一个省的机构代码
	 * 
	 * @param comCode
	 *            机构代码（变参）
	 */
	public boolean isSameProvinceComCode(String comCodes);

	/**
	 * 得到当前机构列表的所有下属机构(外部接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	public String getSubCompanyCodes(String comCode);

	/**
	 * 得到当前机构列表的所有下属机构(权限专用接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	public List<String> getSubCompanyCodeList(List<String> comCodes);

	/**
	 * 得到当前机构列表的所有下属机构
	 * 
	 * @param comCode
	 * @return
	 */
	public List<String> getSubAllCompanyCode(String comCode);

	/**
	 * 得到当前机构列表的所有上级机构
	 * 
	 * @param comCode
	 * @return
	 */
	public List<String> getAllUpperCompanyCode(String comCode);

	/**
	 * 得到下级的市级公司
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCityCompanyCodes(String comCode);

	/**
	 * 得到有权的市级公司
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return
	 */
	public List<String> getPermitCityCompanyCodes(String userCode,
			String taskCode);

	/**
	 * 根据当前机构获取省级机构代码
	 * 
	 * @param comCode
	 * @return
	 */
	public String getProvinceCode(String comCode);

	/**
	 * 省间通赔省内机构查询
	 * 
	 * @param matches
	 * @return
	 */
	public Page listRemoteCompany(String matches);

	/**
	 * 得到当前机构列表的所有下属机构
	 * 
	 * @param comCode
	 * @return
	 */
	public Page getAllSubComCode(String matches, String comCode);

	/**
	 * 获得省间机构的省级机构，用於下拉框
	 * 
	 * @param matches
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page listProvinceCompany(String matches, String listProvinceCompany);

	/**
	 * 得到当前机构列表的所有下属机构,逗号隔开
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCompanyCodeString(String comCode);

	/**
	 * 得到当前机构的分公司机构
	 * 
	 * @param comCode
	 * @return
	 */
	public String getBranchComCode(String comCode);

	/**
	 * 判断upperComCode 是否是comCode的父节点
	 * 
	 * @param comCode
	 * @param upperComCode
	 * @return
	 */
	public boolean isUpperComCode(String comCode, String upperComCode);

	/**
	 * 得到当前机构列表的所有上级机构序列
	 * 
	 * @param comCode
	 * @return
	 */
	public String getUpperCompanyCode(String comCode);

	/**
	 * 
	 * linsiming-wb 2011-7-15
	 * 得到当前机构名称
	 * @param comCname
	 * @return
	 */
	public String getComCname(String comCode);
	/**
	 * 通过状态查询机构
	 */
	public List<SaaCompany> getComByValidstatus(String Validstatus);
	
	
	public List<SaaCompany> getCompany(QueryRule rule);
	
	public Page findCompany(QueryRule queryRule, int pageNo, int pageSize);

    public void synchroPrpDcompany(SaaCompany prpDcompany);
    
    public void synReverseSaaCompany(SaaCompany saaCompany);
    
    public Page findCompanyByRule(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
   
    public Page findCompanyByRule(String userCode,String taskCode,QueryRule queryRule, int pageNo, int pageSize) throws Exception;

    
    /**
	 * 得到当前机构列表的所有下属机构,逗号隔开(需要进行判断是否是虚拟机构，如果是，则使用该虚拟机构的上级机构)
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCompanyCodeByComCode(String comCode);
	
	/**
	 * 查询总公司部门
	 * @param comCode
	 * @return
	 */
	public List<SaaCompany> getSubDepartCodeByComCode(String comCode);

	/**
	 * 查询三级机构
	 * @param comLevel 
	 * @return
	 */
	public List<SaaCompany> findSubCompany(String comCode, String comLevel);

	/**
	 * 把List转换成Page
	 * @param saaCompanyList
	 * @return
	 */
	public Page transferListToPage(List<SaaCompany> saaCompanyList, int pageNo, int pageSize) throws Exception;
	
	public List<String> findComCodeByVirtual(String upperComCode) throws Exception;
	
	public boolean isExistComCode(String comCode) throws Exception;

	public void insertVirtualCompany(SaaCompany saaCompany);

	public void updateVirtualCompany(SaaCompany saaCompany);
	//查询指定机构下的指定级别机构数量(限二级)
	public int getCompanyAmount(String comCode, int i);
	/**查找不在集合中的二级机构*/
	public List<SaaCompany> findNotInCompany(Set<String> comCodeSet);
}
