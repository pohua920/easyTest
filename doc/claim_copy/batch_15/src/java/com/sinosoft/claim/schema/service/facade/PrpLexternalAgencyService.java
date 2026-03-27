package com.sinosoft.claim.schema.service.facade;
/**
 * 外部机构信息表，包括银行帐号信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLexternalAgencyId;

public interface PrpLexternalAgencyService {
	
	/**
	 * 保存外部机构信息表，包括银行帐号信息
	 * @param prpLexternalAgency ：传入的外部机构信息表，包括银行帐号信息
	 */
	public void save(PrpLexternalAgency prpLexternalAgency) throws Exception;
	
	/**
	 * 外部机构信息表，包括银行帐号信息
	 * @param list  :传入的外部机构信息表，包括银行帐号信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLexternalAgency> list) throws Exception;
	
	/**
	 * 删除外部机构信息表，包括银行帐号信息
	 * @param prpLexternalAgencyId ：传入的外部机构信息表，包括银行帐号信息编号
	 */
	public void delete(PrpLexternalAgencyId prpLexternalAgencyId) throws Exception;

	/**
	 * 更新外部机构信息表，包括银行帐号信息
	 * @param prpLexternalAgency :传入需要更新的外部机构信息表，包括银行帐号信息
	 */
	public void update(PrpLexternalAgency prpLexternalAgency) throws Exception;

	/**
	 * 根据外部机构信息表，包括银行帐号信息编号查询出外部机构信息表，包括银行帐号信息
	 * @param prpLexternalAgencyId ：传入的外部机构信息表，包括银行帐号信息编号
	 * @return 返回外部机构信息表，包括银行帐号信息
	 */
	public PrpLexternalAgency findPrpLexternalAgency(PrpLexternalAgencyId prpLexternalAgencyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的外部机构信息表，包括银行帐号信息页面信息
	 * @deprecated 
	 */
	public Page findPrpLexternalAgency(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取外部机构信息表，包括银行帐号信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 外部机构信息表，包括银行帐号信息 的集合
	 */
	public List<PrpLexternalAgency> findPrpLexternalAgency(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据外部机构信息表，包括银行帐号信息编号查询出外部机构信息表，包括银行帐号信息
	 * @param certiNo ：传入的外部机构信息表，包括银行帐号信息编号
	 * @return 返回外部机构信息表，包括银行帐号信息
	 */
	public PrpLexternalAgency findPrpLexternalAgency(String certiNo) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的外部机构信息表，包括银行帐号信息页面信息
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception;
	    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含prplexternalagencyDto的集合
     * @throws Exception
     */
    public List<?> findByConditions(String conditions)throws Exception;

}
