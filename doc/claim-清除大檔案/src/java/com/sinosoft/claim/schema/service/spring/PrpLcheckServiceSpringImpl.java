package com.sinosoft.claim.schema.service.spring;
/**
 * rplcheck查勘/代查勘信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;

public class PrpLcheckServiceSpringImpl extends
GenericDaoHibernate<PrpLcheck, PrpLcheckId> implements PrpLcheckService{

	public void save(PrpLcheck prpLcheck) throws Exception {
		logger.info("保存查勘/代查勘信息信息");
		super.save(prpLcheck);
		
	}

	public void save(List<PrpLcheck> list) throws Exception {
		logger.info("保存查勘/代查勘信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	public void delete(PrpLcheckId prpLcheckId) throws Exception {
		logger.info("删除查勘/代查勘信息编号为" + prpLcheckId + "的查勘/代查勘信息");
		super.deleteByPK(PrpLcheck.class, prpLcheckId);
	}

	public PrpLcheck findPrpLcheck(PrpLcheckId prpLcheckId) throws Exception {
		logger.info("查询查勘/代查勘信息编号为" + prpLcheckId + "的查勘/代查勘信息");
		return super.get(PrpLcheck.class, prpLcheckId);
	}

	public Page findPrpLcheck(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取查勘/代查勘信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpLcheck> findPrpLcheck(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据查勘/代查勘信息编号查询出查勘/代查勘信息
	 * @param certiNo ：传入的查勘/代查勘信息编号
	 * @return 返回查勘/代查勘信息
	 */
	public PrpLcheck findPrpLcheck(String certiNo) throws Exception{
		PrpLcheck prpLcheck = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", certiNo);
		List<PrpLcheck> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcheck = resultList.get(0);
		}
		return prpLcheck;
	}
	/**
	 * 判断是否查勘
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String registNo)throws Exception{
		String hql = "from PrpLcheck where registNo=?";
		long count = super.getCount(hql, registNo);
		if(count<1){
			return false;
		}
		return true;
	}
}
