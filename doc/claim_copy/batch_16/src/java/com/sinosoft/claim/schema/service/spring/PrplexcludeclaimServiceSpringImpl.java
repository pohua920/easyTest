package com.sinosoft.claim.schema.service.spring;

/**
 * 立案除外信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Date;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplexcludeclaim;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplexcludeclaimService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrplexcludeclaimServiceSpringImpl extends GenericDaoHibernate<Prplexcludeclaim, String> implements PrplexcludeclaimService {
	private PrpLregistService prpLregistService = null;

	@Override
	public void save(Prplexcludeclaim prplexcludeclaim) throws Exception {
		logger.info("保存立案除外信息");
		super.save(prplexcludeclaim);

	}

	@Override
	public void save(List<Prplexcludeclaim> list) throws Exception {
		logger.info("保存立案除外信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String registno) throws Exception {
		logger.info("删除立案除外信息编号为" + registno + "的立案除外信息");
		super.deleteByPK(Prplexcludeclaim.class, registno);
	}

	@Override
	public Prplexcludeclaim findPrplexcludeclaim(String registno) throws Exception {
		logger.info("查询立案除外信息编号为" + registno + "的立案除外信息");
		return super.get(Prplexcludeclaim.class, registno);
	}

	@Override
	public Page findPrplexcludeclaim(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取立案除外信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<Prplexcludeclaim> findPrplexcludeclaim(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 判断是否有例外的案件
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public boolean isExcluded(String registNo) throws Exception {
		String hql = " select count(*) from Prplexcludeclaim where registNo = '"+registNo+"'";
		long count = HibernateUtils.getCountbyCountSql(super.getSession(), hql);
		if (count < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 立案除外提交
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
	public void save(String registNo, String excludeReason, UserDto userDto) throws Exception {
		// 1.报案数据
		PrpLregist prpLregist = new PrpLregist();
		prpLregist = prpLregistService.findPrpLregist(registNo);
		Prplexcludeclaim prplexcludeclaim = new Prplexcludeclaim();
		prplexcludeclaim.setRegistNo(registNo);
		prplexcludeclaim.setPolicyNo(prpLregist.getPolicyNo());
		prplexcludeclaim.setRiskCode(prpLregist.getRiskCode());
		prplexcludeclaim.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
		prplexcludeclaim.setOperatorCode(userDto.getUserCode());
		prplexcludeclaim.setOperatorname(userDto.getUserName());
		prplexcludeclaim.setComCode(prpLregist.getComCode());
		prplexcludeclaim.setComname("");
		prplexcludeclaim.setExcludereason(excludeReason);
		prplexcludeclaim.setFlag("");
		this.save(prplexcludeclaim);
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

}
