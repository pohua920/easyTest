package com.sinosoft.claim.schema.service.spring;
/**
 * 雇员医药费清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLmedicine;
import com.sinosoft.claim.schema.model.PrpLmedicineId;
import com.sinosoft.claim.schema.service.facade.PrpLmedicineService;

public class PrpLmedicineServiceSpringImpl extends
GenericDaoHibernate<PrpLmedicine, PrpLmedicineId> implements PrpLmedicineService{

	@Override
	public void save(PrpLmedicine prpLmedicine) throws Exception {
		logger.info("保存雇员医药费清单信息");
		super.save(prpLmedicine);
		
	}

	@Override
	public void save(List<PrpLmedicine> list) throws Exception {
		logger.info("保存雇员医药费清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLmedicineId prpLmedicineId) throws Exception {
		logger.info("删除雇员医药费清单信息编号为" + prpLmedicineId + "的雇员医药费清单信息");
		super.deleteByPK(PrpLmedicine.class, prpLmedicineId);
	}

	@Override
	public PrpLmedicine findPrpLmedicine(PrpLmedicineId prpLmedicineId) throws Exception {
		logger.info("查询雇员医药费清单信息编号为" + prpLmedicineId + "的雇员医药费清单信息");
		return super.get(PrpLmedicine.class, prpLmedicineId);
	}

	@Override
	public Page findPrpLmedicine(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取雇员医药费清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLmedicine> findPrpLmedicine(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据雇员医药费清单编号查询出雇员医药费清单信息
	 * @param certiNo ：传入的雇员医药费清单编号
	 * @return 返回雇员医药费清单
	 */
	public PrpLmedicine findPrpLmedicine(String certiNo) throws Exception{
		PrpLmedicine prpLmedicine = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLmedicine> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLmedicine = resultList.get(0);
		}
		return prpLmedicine;
	}

}
