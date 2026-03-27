package com.sinosoft.claim.schema.service.spring;

/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLacciPersonId;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;

public class PrpLacciPersonServiceSpringImpl extends GenericDaoHibernate<PrpLacciPerson, PrpLacciPersonId> implements PrpLacciPersonService {

	@Override
	public void save(PrpLacciPerson prpLacciPerson) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLacciPerson);

	}

	@Override
	public void save(List<PrpLacciPerson> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void saveOrUpdate(PrpLacciPerson prpLacciPerson) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.getSession().saveOrUpdate(prpLacciPerson);

	}

	public void saveOrUpdate(List<PrpLacciPerson> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void delete(PrpLacciPersonId prpLacciPersonId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLacciPersonId + "的人伤跟踪信息");
		super.deleteByPK(PrpLacciPerson.class, prpLacciPersonId);
	}

	@Override
	public PrpLacciPerson findPrpLacciPerson(PrpLacciPersonId prpLacciPersonId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLacciPersonId + "的人伤跟踪信息");
		return super.get(PrpLacciPerson.class, prpLacciPersonId);
	}

	@Override
	public Page findPrpLacciPerson(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLacciPerson> findPrpLacciPerson(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLacciPerson findPrpLacciPerson(String certiNo) throws Exception {
		PrpLacciPerson prpLacciPerson = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLacciPerson> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLacciPerson = resultList.get(0);
		}
		return prpLacciPerson;
	}

	/**
	 * @param registNo
	 * @throws Exception 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo, String flag) throws Exception {
		String sql = "delete from PrpLacciPerson where certiNo='" + registNo + "'";
		if (flag != null && !"".equals(flag)) {
			sql += " and flag='" + flag + "'";
		}
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void updateFlag(PrpLacciPerson prpLacciPerson) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", prpLacciPerson.getId().getCertiNo());
		queryRule.addEqual("id.certiType", prpLacciPerson.getId().getCertiType());
		queryRule.addEqual("id.serialNo", prpLacciPerson.getId().getSerialNo());
		queryRule.addEqual("flag", prpLacciPerson.getFlag());
		List<PrpLacciPerson> list = this.find(queryRule);
		for (PrpLacciPerson p : list) {
			p.setPolicyNo(prpLacciPerson.getPolicyNo());
			p.setFamilyNo(prpLacciPerson.getFamilyNo());
			p.setAcciCode(prpLacciPerson.getAcciCode());
			p.setAcciName(prpLacciPerson.getAcciName());
			p.setSex(prpLacciPerson.getSex());
			p.setAge(prpLacciPerson.getAge());
			p.setIdentifyType(prpLacciPerson.getIdentifyType());
			p.setIdentifyNumber(prpLacciPerson.getIdentifyNumber());
			p.setRemark(prpLacciPerson.getRemark());
			p.setPhone(prpLacciPerson.getPhone());
			p.setAddress(prpLacciPerson.getAddress());
			p.setRelationCode(prpLacciPerson.getRelationCode());
			p.setRelationName(prpLacciPerson.getRelationName());
			this.saveOrUpdate(p);
		}
	}

	public int findBySeriaNo(String condition) throws Exception {
		int seriaNo = 0;
		String sql = "select max(SerialNo) SerialNo from PrpLacciPerson where " + condition;
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql);
		if (list.size() > 0 && list.get(0) != null) {
			BigDecimal b = (BigDecimal)list.get(0);
			seriaNo = b.intValue();
		}
		return seriaNo;
	}

}
