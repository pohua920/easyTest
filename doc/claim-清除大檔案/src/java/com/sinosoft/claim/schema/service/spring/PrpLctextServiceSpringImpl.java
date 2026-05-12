package com.sinosoft.claim.schema.service.spring;

/**
 * 赔款计算文字信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLctextId;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLctextServiceSpringImpl extends GenericDaoHibernate<PrpLctext, PrpLctextId> implements PrpLctextService {

	@Override
	public void save(PrpLctext prpLctext) throws Exception {
		logger.info("保存赔款计算文字信息");
		super.save(prpLctext);

	}

	public void save(List<PrpLctext> list) throws Exception {
		logger.info("保存赔款计算文字信息");
		super.saveAll(list);
	}

	@Override
	public void delete(PrpLctextId prpLctextId) throws Exception {
		logger.info("删除赔款计算文字信息编号为" + prpLctextId + "的赔款计算文字信息");
		super.deleteByPK(PrpLctext.class, prpLctextId);
	}

	@Override
	public PrpLctext findPrpLctext(PrpLctextId prpLctextId) throws Exception {
		logger.info("查询赔款计算文字信息编号为" + prpLctextId + "的赔款计算文字信息");
		return super.get(PrpLctext.class, prpLctextId);
	}

	@Override
	public Page findPrpLctext(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取赔款计算文字信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLctext> findPrpLctext(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据赔款计算文字编号查询出赔款计算文字信息
	 * @param certiNo ：传入的赔款计算文字编号
	 * @return 返回赔款计算文字
	 */
	public PrpLctext findPrpLctext(String certiNo) throws Exception {
		PrpLctext prpLctext = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLctext> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLctext = resultList.get(0);
		}
		return prpLctext;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLctext Where " + " compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
