package com.sinosoft.claim.schema.service.spring;
/**
 * 担保函明细（船舶）信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLassureDetail;
import com.sinosoft.claim.schema.model.PrpLassureDetailId;
import com.sinosoft.claim.schema.service.facade.PrpLassureDetailService;

public class PrpLassureDetailServiceSpringImpl extends
GenericDaoHibernate<PrpLassureDetail, PrpLassureDetailId> implements PrpLassureDetailService{

	@Override
	public void save(PrpLassureDetail prpLassureDetail) throws Exception {
		logger.info("保存担保函明细（船舶）信息");
		super.save(prpLassureDetail);
		
	}

	@Override
	public void save(List<PrpLassureDetail> list) throws Exception {
		logger.info("保存担保函明细（船舶）信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLassureDetailId prpLassureDetailId) throws Exception {
		logger.info("删除担保函明细（船舶）信息编号为" + prpLassureDetailId + "的担保函明细（船舶）信息");
		super.deleteByPK(PrpLassureDetail.class, prpLassureDetailId);
	}

	@Override
	public PrpLassureDetail findPrpLassureDetail(PrpLassureDetailId prpLassureDetailId) throws Exception {
		logger.info("查询担保函明细（船舶）信息编号为" + prpLassureDetailId + "的担保函明细（船舶）信息");
		return super.get(PrpLassureDetail.class, prpLassureDetailId);
	}

	@Override
	public Page findPrpLassureDetail(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取担保函明细（船舶）信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLassureDetail> findPrpLassureDetail(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据担保函明细（船舶）编号查询出担保函明细（船舶）信息
	 * @param certiNo ：传入的担保函明细（船舶）编号
	 * @return 返回担保函明细（船舶）
	 */
	public PrpLassureDetail findPrpLassureDetail(String certiNo) throws Exception{
		PrpLassureDetail prpLassureDetail = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLassureDetail> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLassureDetail = resultList.get(0);
		}
		return prpLassureDetail;
	}

}
