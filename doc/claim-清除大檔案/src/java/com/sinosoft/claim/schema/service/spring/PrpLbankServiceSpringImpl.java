package com.sinosoft.claim.schema.service.spring;
/**
 * 银行信息表接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLbank;
import com.sinosoft.claim.schema.model.PrpLbankId;
import com.sinosoft.claim.schema.service.facade.PrpLbankService;

public class PrpLbankServiceSpringImpl extends GenericDaoHibernate<PrpLbank, PrpLbankId> implements PrpLbankService {

	@Override
	public void delete(PrpLbankId prpLbankId) throws Exception {
		super.deleteByPK(PrpLbank.class, prpLbankId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrpLbank> findBank(String bankCode, String bankCName,String bankLevel,String upperBankCode,String upperBankName, int pageNo,
			int pageSize) throws Exception {
		if("2".equals(bankLevel)&&(upperBankCode==null||"".equals(upperBankCode)||upperBankName==null||"".equals(upperBankName))){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.bankcode bankCode,a.bankcname bankCName ,b.bankcode upperBankCode,b.bankCName upperBankCName,b.bankShortName bankShortName FROM PrpLbank a,PrpLbank b where 1=1 and a.validstatus='1' ");
			sql.append(" AND a.banklevel='2' AND a.upperbankcode=b.bankcode ");
			if(null!=bankCode&&!"".equals(bankCode)){
				sql.append(" and a.bankCode LIKE '"+bankCode+"%'");
			}
			if(null!=bankCName&&!"".equals(bankCName)){
				sql.append(" and a.bankCName LIKE '"+bankCName+"%'");
			}
			sql.append(" order by a.bankcode ");
			List<Object[]> list =  (List<Object[]>) HibernateUtils.findbySql(super.getSession(), sql.toString(),pageNo,pageSize);
			List<PrpLbank> prpLbankList = new ArrayList<PrpLbank>();
			for(int i=0; i < list.size(); i ++){
				PrpLbank prpLbank = new PrpLbank();
				prpLbank.getId().setBankCode((String) list.get(i)[0]);
				prpLbank.getId().setUpperBankCode((String) list.get(i)[2]);
				prpLbank.setBankCName((String) list.get(i)[1]);
				prpLbank.setUpperBankCName((String) list.get(i)[3]);
				prpLbank.setBankShortName((String) list.get(i)[4]);
				prpLbankList.add(prpLbank);
			}
			return prpLbankList;
		}else{
			QueryRule queryRule = QueryRule.getInstance();
			if(bankCode!=null&&!"".equals(bankCode)){
				queryRule.addLike("id.bankCode", bankCode+"%");
			}
			if(bankCName!=null&&!"".equals(bankCName)){
				queryRule.addLike("bankCName", bankCName+"%");
			}
			if(bankLevel!=null&&!"".equals(bankLevel)){
				queryRule.addEqual("bankLevel", bankLevel);
			}
			if(upperBankCode!=null&&!"".equals(upperBankCode)){
				queryRule.addEqual("id.upperBankCode", upperBankCode);
			}
			queryRule.addEqual("validstatus", "1");
			queryRule.addAscOrder("id.bankCode");
			return super.find(queryRule, pageNo, pageSize).getResult();
		}
	}

	@Override
	public long findCount(String bankCode, String bankCName, String bankLevel,String upperBankCode,String upperBankName) throws Exception {
		StringBuffer hql = new StringBuffer("select count(1) from PrpLbank where 1=1 and validstatus='1' ");
		if(bankCode!=null&&!"".equals(bankCode)){
			hql.append(" and bankCode like '"+bankCode+"%'");
		}
		if(bankCName!=null&&!"".equals(bankCName)){
			hql.append(" and bankCName like '"+bankCName+"%'");
		}
		if(bankLevel!=null&&!"".equals(bankLevel)){
			hql.append(" and bankLevel = '"+bankLevel+"'");
		}
		if(upperBankCode!=null&&!"".equals(upperBankCode)){
			hql.append(" and upperBankCode = '"+upperBankCode+"'");
		}
		return super.getCount(hql.toString());
	}

	@Override
	public Page findPrpLbank(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLbank> findPrpLbank(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public PrpLbank findPrpLbank(String bankCode , String upperBankCode) throws Exception {
		return super.get(PrpLbank.class, new PrpLbankId(bankCode , upperBankCode));
	}
	
	@Override
	public PrpLbank findPrpLbank(String bankCode) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.bankCode", bankCode);
		List<PrpLbank> list = super.find(queryRule);
		if(list!= null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public long getCount(String bankCode, String bankCName,String bankLevel) throws Exception {
		StringBuffer hql = new StringBuffer("select count(1) from PrpLbank where 1=1");
		if(bankCode!=null&&!"".equals(bankCode)){
			hql.append(" and bankCode='"+bankCode+"'");
		}
		if(bankCName!=null&&!"".equals(bankCName)){
			hql.append(" and bankCName='"+bankCName+"'");
		}
		if(bankLevel!=null&&!"".equals(bankLevel)){
			hql.append(" and bankLevel='"+bankLevel+"'");
		}
		return super.getCount(hql.toString());
	}

	@Override
	public void save(PrpLbank prpLbank) throws Exception {
		super.save(prpLbank);
	}

	@Override
	public void save(List<PrpLbank> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void updatePrpLbank(PrpLbank prpLbank) throws Exception{
		String sql = " update PrpLbank set bankCode = ? , upperBankCode = ? , bankCName = ? , upperBankCName = ? , bankShortName = ? , validstatus = ? where bankCode = ? ";
		HibernateUtils.executeSql(super.getSession(), sql, prpLbank.getId().getBankCode() , prpLbank.getId().getUpperBankCode(), prpLbank.getBankCName() , prpLbank.getUpperBankCName() , prpLbank.getBankShortName() , prpLbank.getValidstatus() , prpLbank.getOrigBankCode());
		if("1".equals(prpLbank.getBankLevel())){//修改總行
			sql = "update PrpLbank set upperBankCode = ? , upperBankCName = ? where upperBankCode = ? ";
			HibernateUtils.executeSql(super.getSession(), sql, prpLbank.getId().getBankCode() , prpLbank.getBankCName() , prpLbank.getOrigBankCode());
		}
	}
	
	public void updatePrpLbank(String bankCode , String validstatus) throws Exception {
		String sql = " update PrpLbank set validstatus = ?  where bankCode = ? ";
		HibernateUtils.executeSql(super.getSession(), sql , validstatus , bankCode);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page findPrpLbank(String statements, Object[] params, int pageNo, int pageSize) {
		Session session = super.getSession();
		long count = 0L;
		Query query;
		List<?> resultList;
		if (pageNo > 0 && pageSize > 0) {
			// 統計數量
			String countSQL = "select count(0) from PrpLbank where " + statements ;
			query = session.createSQLQuery(countSQL);
			if (!CommonUtils.isEmpty(params)) {
				for (int i = 0, l = params.length; i < l; i++) {
					query.setParameter(i, params[i]);
				}
			}
			resultList = query.list();
			if (resultList.size() > 0) {
				BigDecimal object = (BigDecimal) resultList.get(0);
				count = object.longValue();
			}
			if (count == 0) {
				return new Page((pageNo - 1) * pageSize, 0L, pageSize, new ArrayList());
			}
		}
		query = session.createSQLQuery("select * from PrpLbank where " + statements ).addEntity(PrpLbank.class) ;
		if (!CommonUtils.isEmpty(params)) {
			for (int i = 0, l = params.length; i < l; i++) {
				query.setParameter(i, params[i]);
			}
		}
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return new Page((pageNo - 1) * pageSize, count, pageSize, resultList);
	}
	
}
