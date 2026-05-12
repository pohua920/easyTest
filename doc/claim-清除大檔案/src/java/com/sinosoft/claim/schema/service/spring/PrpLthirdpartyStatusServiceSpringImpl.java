package com.sinosoft.claim.schema.service.spring;
/**
 * 与第三方企业信息交互信息状态信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLthirdpartyStatus;
import com.sinosoft.claim.schema.model.PrpLthirdpartyStatusId;
import com.sinosoft.claim.schema.service.facade.PrpLthirdpartyStatusService;

public class PrpLthirdpartyStatusServiceSpringImpl extends
GenericDaoHibernate<PrpLthirdpartyStatus, PrpLthirdpartyStatusId> implements PrpLthirdpartyStatusService{

	@Override
	public void save(PrpLthirdpartyStatus prpLthirdpartyStatus) throws Exception {
		logger.info("保存与第三方企业信息互動信息状态信息");
		super.save(prpLthirdpartyStatus);
		
	}

	@Override
	public void save(List<PrpLthirdpartyStatus> list) throws Exception {
		logger.info("保存与第三方企业信息互動信息状态信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLthirdpartyStatusId prpLthirdpartyStatusId) throws Exception {
		logger.info("删除与第三方企业信息互動信息状态信息编号为" + prpLthirdpartyStatusId + "的与第三方企业信息互動信息状态信息");
		super.deleteByPK(PrpLthirdpartyStatus.class, prpLthirdpartyStatusId);
	}

	@Override
	public PrpLthirdpartyStatus findPrpLthirdpartyStatus(PrpLthirdpartyStatusId prpLthirdpartyStatusId) throws Exception {
		logger.info("查询与第三方企业信息互動信息状态信息编号为" + prpLthirdpartyStatusId + "的与第三方企业信息互動信息状态信息");
		return super.get(PrpLthirdpartyStatus.class, prpLthirdpartyStatusId);
	}

	@Override
	public Page findPrpLthirdpartyStatus(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取与第三方企业信息互動信息状态信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLthirdpartyStatus> findPrpLthirdpartyStatus(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据与第三方企业信息交互信息状态编号查询出与第三方企业信息交互信息状态信息
	 * @param certiNo ：传入的与第三方企业信息交互信息状态编号
	 * @return 返回与第三方企业信息交互信息状态
	 */
	public PrpLthirdpartyStatus findPrpLthirdpartyStatus(String certiNo) throws Exception{
		PrpLthirdpartyStatus prpLthirdpartyStatus = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLthirdpartyStatus> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLthirdpartyStatus = resultList.get(0);
		}
		return prpLthirdpartyStatus;
	}

}
