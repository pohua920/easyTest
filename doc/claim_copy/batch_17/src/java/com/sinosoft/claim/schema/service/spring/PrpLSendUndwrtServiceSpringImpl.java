package com.sinosoft.claim.schema.service.spring;

/**
 * 送审审核菜单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLSendUndwrt;
import com.sinosoft.claim.schema.model.PrpLSendUndwrtId;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLSendUndwrtService;

public class PrpLSendUndwrtServiceSpringImpl extends GenericDaoHibernate<PrpLSendUndwrt, PrpLSendUndwrtId> implements PrpLSendUndwrtService {

	@Override
	public void save(PrpLSendUndwrt prpLSendUndwrt) throws Exception {
		logger.info("保存送审审核菜单信息");
		super.save(prpLSendUndwrt);

	}

	@Override
	public void save(List<PrpLSendUndwrt> list) throws Exception {
		logger.info("保存送审审核菜单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLSendUndwrtId prpLSendUndwrtId) throws Exception {
		logger.info("删除送审审核菜单信息编号为" + prpLSendUndwrtId + "的送审审核菜单信息");
		super.deleteByPK(PrpLSendUndwrt.class, prpLSendUndwrtId);
	}

	@Override
	public void update(PrpLSendUndwrt prpLSendUndwrt) {
		super.update(prpLSendUndwrt);
	}

	@Override
	public PrpLSendUndwrt findPrpLSendUndwrt(PrpLSendUndwrtId prpLSendUndwrtId) throws Exception {
		logger.info("查询送审审核菜单信息编号为" + prpLSendUndwrtId + "的送审审核菜单信息");
		return super.get(PrpLSendUndwrt.class, prpLSendUndwrtId);
	}

	@Override
	public Page findPrpLSendUndwrt(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取送审审核菜单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLSendUndwrt> findPrpLSendUndwrt(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据送审审核菜单编号查询出送审审核菜单信息
	 * @param certiNo ：传入的送审审核菜单编号
	 * @return 返回送审审核菜单
	 */
	public PrpLSendUndwrt findPrpLSendUndwrt(String certiNo) throws Exception {
		PrpLSendUndwrt prpLSendUndwrt = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLSendUndwrt> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLSendUndwrt = resultList.get(0);
		}
		return prpLSendUndwrt;
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception {
		int count = -1;
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM (SELECT * FROM PrpLSendUndwrt WHERE ");
		buffer.append(conditions);
		buffer.append(")");
		Session session = super.getSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
		return count;
	}

}
