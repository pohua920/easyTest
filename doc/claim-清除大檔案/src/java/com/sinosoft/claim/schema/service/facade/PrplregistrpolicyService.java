package com.sinosoft.claim.schema.service.facade;
/**
 * 赔案保单关联接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrplregistrpolicyId;

public interface PrplregistrpolicyService {
	
	/**
	 * 保存赔案保单关联信息
	 * @param prplregistrpolicy ：传入的赔案保单关联
	 */
	public void save(Prplregistrpolicy prplregistrpolicy) throws Exception;
	
	/**
	 * 保存赔案保单关联信息
	 * @param list:保存赔案保单关联信息
	 */
	public void save(List<Prplregistrpolicy> list) throws Exception;
	
	/**
	 * 删除赔案保单关联信息
	 * @param prplregistrpolicyId ：传入的赔案保单关联编号
	 */
	public void delete(PrplregistrpolicyId prplregistrpolicyId) throws Exception;

	/**
	 * 更新赔案保单关联信息
	 * @param prplregistrpolicy :传入需要更新的赔案保单关联
	 */
	public void update(Prplregistrpolicy prplregistrpolicy) throws Exception;

	/**
	 * 根据赔案保单关联编号查询出赔案保单关联信息
	 * @param prplregistrpolicyId ：传入的赔案保单关联编号
	 * @return 返回赔案保单关联
	 */
	public Prplregistrpolicy findPrplregistrpolicy(PrplregistrpolicyId prplregistrpolicyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔案保单关联页面信息
	 */
	public Page findPrplregistrpolicy(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取赔案保单关联信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  赔案保单关联信息的集合
	 */
	public List<Prplregistrpolicy> findPrplregistrpolicy(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 更具报案号查询关联信息
	 */
	public List<Prplregistrpolicy> findByRegistNo(String registNo)throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * @param prpLregist
	 * @throws Exception
	 * 保存或修改，
	 */
	public void saveOrUpdate(List<Prplregistrpolicy> list)throws Exception;
	/**
	 * @param prpLregist
	 * @throws Exception
	 * 保存或修改，
	 */
	public void saveOrUpdate(Prplregistrpolicy prplregistrpolicy)throws Exception;
	/**
	 * 根据报案号判断是否关联报案
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public boolean isCompelFlag(String registNo)throws Exception;
	
    /**
     * 以保单号组织查询到的报案号信息
     * @param conditions
     * @return
     * @throws Exception
     */
    public List<String> getRegistNoByPolicyNo(String policyNo,String policyNoSign) throws Exception;
    
    /**
     * 以赔案（立案）号组织查询到的报案号信息
     * @param conditions
     * @return
     * @throws Exception
     */
    public List<String> getRegistNoByClaimNo(String claimNo,String claimNoSign) throws Exception;
    
    
    /**
     * 以强制保险证号 组织查询到的报案号信息
     * @param conditions
     * @return
     * @throws Exception
     */
    public List<String> getRegistNoByPrintNo(String printNo,String printNoSign) throws Exception;
    
    /**
     * 根据被保险人ID查询保单
     * @author 中科软
     * @param identifyNumber
     * @return
     * @throws Exception
     */
    public String getPolicyNoByInsuredIdentifyNumber(String identifyNumber) throws Exception;

    /**
     * 根据三者车车牌号查询报案号信息
     * @author 中科软
     * @param thirdLicenseNo
     * @param parameter
     * @return
     */
	public List<String> getRegistNoByThirdLicenseNo(String thirdLicenseNo, String thirdLicenseNoSign);
	/**
     * 根据受害人身份证查询报案号信息
     * @author 中科软
     * @param thirdLicenseNo
     * @param parameter
     * @return
     */
	public List<String> getRegistNoByPersonIdentifyNumber(String identifyNumber, String identifyNumberSign);
	
	/**
	 * 根据保单号获取其初次标记同业共摊的备案号
	 * @author 中科软
	 * @param policyNo
	 * @param tempPrpLregist
	 * @return
	 */
	public String getSharingRegistNo(String policyNo,PrpLregist tempPrpLregist);
	/**
	 * 根据报案号查询流程id
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public String findSwfLogId (String registNo) throws Exception;
	/**
	 * 根据任意保險卡號查询保单
	 */
	public List<String> getPolicyNoByVisaCodeBI(String visaCodeBI,String visaCodeBISign) throws Exception;
	/**
	 * webservice 根據身份證號碼，險种查詢保單號
	 */
	public String findPolicyNoByIdentifyNumberCode(String identifyNumber,String code) throws Exception;

}
