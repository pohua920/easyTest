package com.sinosoft.claim.schema.service.spring;

/**
 * PRPCITEMKIND信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.claim.schema.model.PrpCopyItemKindId;
import com.sinosoft.claim.schema.service.facade.PrpCopyItemKindService;

public class PrpCopyItemKindServiceSpringImpl extends GenericDaoHibernate<PrpCopyItemKind, PrpCopyItemKindId> implements PrpCopyItemKindService {

	@Override
	public void save(PrpCopyItemKind prpCopyItemKind) throws Exception {
		logger.info("保存PRPCopyItemKind信息");
		super.save(prpCopyItemKind);

	}

	@Override
	public void save(List<PrpCopyItemKind> list) throws Exception {
		logger.info("保存PRPCopyItemKind信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCopyItemKindId prpCopyItemKindId) throws Exception {
		logger.info("删除PRPCopyItemKind信息编号为" + prpCopyItemKindId + "的PRPCopyItemKind信息");
		super.deleteByPK(PrpCopyItemKind.class, prpCopyItemKindId);
	}

	@Override
	public PrpCopyItemKind findPrpCopyItemKind(PrpCopyItemKindId prpCopyItemKindId) throws Exception {
		logger.info("查询PRPCopyItemKind信息编号为" + prpCopyItemKindId + "的PRPCopyItemKind信息");
		return super.get(PrpCopyItemKind.class, prpCopyItemKindId);
	}

	/**
	 * 不建议使用
	 */
	@Override
	public Page findPrpCopyItemKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取PRPCopyItemKind信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCopyItemKind> findPrpCopyItemKind(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据PRPCopyItemKind编号查询出PRPCopyItemKind信息
	 * @param certiNo ：传入的PRPCopyItemKind编号
	 * @return 返回PRPCopyItemKind
	 */
	public PrpCopyItemKind findPrpCopyItemKind(String certiNo) throws Exception {
		PrpCopyItemKind prpCopyItemKind = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", certiNo);
		List<PrpCopyItemKind> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCopyItemKind = resultList.get(0);
		}
		return prpCopyItemKind;
	}

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpCopyItemKind> findByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (conditions.length() <= 0) {
			conditions = "1 = 1";
		}
		String statement = "SELECT DISTINCT(familyno),familyName, kindCode,kindName,amount,itemCode,unitAmount,flag,itemKindNo,itemDetailName" + " FROM PRPCopyItemKind WHERE " + conditions;
		List<PrpCopyItemKind> resultList = new ArrayList<PrpCopyItemKind>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, rowsPerPage);
		PrpCopyItemKind prpCopyItemKind = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			prpCopyItemKind = new PrpCopyItemKind();
			prpCopyItemKind.setFamilyNo(new Integer(String.valueOf(object[0])));
			prpCopyItemKind.setFamilyName(String.valueOf(object[1]));
			prpCopyItemKind.setKindCode(String.valueOf(object[2]));
			prpCopyItemKind.setKindName(String.valueOf(object[3]));
			prpCopyItemKind.setAmount(new Double(String.valueOf(object[4])));
			prpCopyItemKind.setItemCode(String.valueOf(object[5]));
			prpCopyItemKind.setUnitAmount(new Double(String.valueOf(object[6])));
			prpCopyItemKind.setFlag(String.valueOf(object[7]));
			prpCopyItemKind.getId().setItemKindNo(new Integer(String.valueOf(object[8])));
			prpCopyItemKind.setItemDetailName(String.valueOf(object[9]));
			resultList.add(prpCopyItemKind);
		}
		return resultList;
	}

	@Override
	public Page findKindCodeAndNameByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = "1 = 1";
		}
		String statement = "SELECT DISTINCT(KINDCODE),KINDNAME,familyno,ITEMKINDNO FROM PRPCopyItemKind WHERE " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, rowsPerPage);
		List<?> result = page.getResult();
		List<PrpCopyItemKind> resultList = new ArrayList<PrpCopyItemKind>();
		if (result != null && !result.isEmpty()) {
			Object[] object = null;
			PrpCopyItemKind prpCopyItemKind = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				object = (Object[]) it.next();
				prpCopyItemKind = new PrpCopyItemKind();
				prpCopyItemKind.setKindCode((String) object[0]);
				prpCopyItemKind.setKindName((String) object[1]);
				prpCopyItemKind.setFamilyNo(DataUtils.getInteger(object[2]));
				prpCopyItemKind.getId().setItemKindNo(DataUtils.getInteger(object[3]));
				resultList.add(prpCopyItemKind);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpCopyItemKind where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, rowsPerPage, PrpCopyItemKind.class);
	}

	@Override
	public List<PrpCopyItemKind> findByConditions(String conditions) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

}
