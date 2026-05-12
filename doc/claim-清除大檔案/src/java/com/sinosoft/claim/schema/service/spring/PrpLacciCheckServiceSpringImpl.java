package com.sinosoft.claim.schema.service.spring;

/**
 * 意健险调查信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;


import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLacciCheckServiceSpringImpl extends GenericDaoHibernate<PrpLacciCheck, String> implements PrpLacciCheckService {
	private PrpLregistService prpLregistService;
	@Override
	public void save(PrpLacciCheck prpLacciCheck) throws Exception {
		List<PrpLacciCheck> result = findPrpLacciCheckByRegistNo(prpLacciCheck.getRegistNo());
        int times = 0;
        if (result.size()>0) {
            times = result.get(0).getTimes();
        }

        prpLacciCheck.setTimes(++times);

        PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLacciCheck.getRegistNo());

        prpLacciCheck.setCheckNo(prpLacciCheck.getRegistNo() + "-"
                + StringUtils.newString("0", 3 - String.valueOf(prpLacciCheck.getTimes()).length())
                + prpLacciCheck.getTimes());
        prpLacciCheck.setRiskCode(prpLregist.getRiskCode());
        prpLacciCheck.setPolicyNo(prpLregist.getPolicyNo());
        prpLacciCheck.setApproverStatus("0");
		logger.info("保存意健险调查信息");
		Session session = super.getSession();
		session.merge(prpLacciCheck);//merge的作用相当于saveorupdate
	}

	@Override
	public void save(List<PrpLacciCheck> list) throws Exception {
		logger.info("保存意健险调查信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String checkNo) throws Exception {
		logger.info("删除意健险调查信息编号为" + checkNo + "的意健险调查信息");
		super.deleteByPK(PrpLacciCheck.class, checkNo);
	}

	@Override
	public PrpLacciCheck findPrpLacciCheck(String checkNo) throws Exception {
		logger.info("查询意健险调查信息编号为" + checkNo + "的意健险调查信息");
		PrpLacciCheck prpLacciCheck = super.get(checkNo);
		return prpLacciCheck;
		 
	}

	@Override
	public Page findPrpLacciCheck(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取意健险调查信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<PrpLacciCheck> findPrpLacciCheck(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 获得等於某个报案号的所有调查费用总和
	 */
	@Override
	public double getAcciCheckFeeByRegistNo(String registNo) throws Exception {
		String sql = "SELECT sum(checkFee) sumCheckFee FROM PrpLacciCheck WHERE RegistNo=?";
		List<?> list = super.findBySql(sql, registNo);
		if ( !list.isEmpty() && list.get(0) != null) {
			return ((BigDecimal) list.get(0)).doubleValue();
		}
		return 0;
	}
	public List<PrpLacciCheck> findPrpLacciCheckByRegistNo(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo",registNo);
		queryRule.addDescOrder("checkNo");
		return super.find(queryRule);
	}
		
	@Override
	public int findByRegistNoMaxTimes(String registNo) throws Exception {
		String sql = "select max(Times) from PrpLacciCheck where registNo ='"+registNo+"'";
//		PrpLacciCheck prpLacciCheck = (PrpLacciCheck) super.getSession().createQuery(sql).uniqueResult();
//		return prpLacciCheck.getTimes();
		BigDecimal bigDecimal = (BigDecimal)getSession().createSQLQuery(sql).uniqueResult();
		if(bigDecimal!=null){
			return bigDecimal.intValue();
		}else {
			return 0;
		}
		
	}
	
	@Override
	public List<PrpLacciCheck> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	 /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Page 查询的一页的结果
     * @throws Exception
     */
    public Page findByConditions(String conditions,int pageNo,int pageSize) throws Exception{
    	QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule, pageNo, pageSize);
    }
    
	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

}
