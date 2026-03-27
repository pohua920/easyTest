package com.sinosoft.claim.common.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDcurrency;

public class PrpDcurrencyServiceSpringImpl extends GenericDaoHibernate<PrpDcurrency, String> implements PrpDcurrencyService {

	/**
	 * 翻译代码
	 * @param currencyCode 币别代码
	 * @param isChinese 是否中午，英文
	 * @return 币别名称
	 */
	@Override
	public String translateCode(String currencyCode, boolean isChinese) {
		String codeName  = "" ;
		if(currencyCode==null||currencyCode.equals("")){
	        codeName = "" ;
	        return codeName;
		}
		PrpDcurrency prpDcurrency=super.get(PrpDcurrency.class, currencyCode);
		 if(isChinese){
			 return prpDcurrency.getCurrencyCName();
		 }else{
			 return prpDcurrency.getCurrencyEName();
		 }
		 
	}

	/**
	 * 分页币别代码表的数据
	 * @author 中科软
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 没有显示页数
	 * @return 分页数据
	 */
	@Override
	public Page findByPage(String conditions, int pageNo, int rowsPerPage) {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDcurrency where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, rowsPerPage,PrpDcurrency.class);
	}
	/**
	 * 分页币别代码表的数据
	 * @param queryRule  查询条件
	 * @return 所有数据
	 * @throws Exception
	 */
	@Override
	public List<PrpDcurrency> findPrpDcurrency(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 查询支付币别
	 * @return
	 */
	public List<PrpDcurrency> findPayCurrency() throws Exception {
		String sql = "select * from prpDcurrency where currencyCode='"+ConstantCodes.LOCAL_CURRENCY+"' "+
				"Union All select * from prpDcurrency where currencyCode !='"+ConstantCodes.LOCAL_CURRENCY+"'";
		return (List<PrpDcurrency>) HibernateUtils.findbySql(super.getSession(),sql,PrpDcurrency.class);
	}

}
