package com.sinosoft.claim.schema.service.spring;

/**
 * 支付对象接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLpayObject;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectService;

public class PrpLpayObjectServiceSpringImpl extends GenericDaoHibernate<PrpLpayObject, String> implements PrpLpayObjectService {

	/**
	 * 保存支付对象信息
	 * @param prpLpayObject ：传入的支付对象
	 */
	@Override
	public void save(PrpLpayObject prpLpayObject) throws Exception {
		logger.info("保存支付对象信息");
		super.save(prpLpayObject);
	}

	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	@Override
	public void save(List<PrpLpayObject> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void saveOrUpdate(List<PrpLpayObject> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 保存支付对象信息
	 * @param prpLpayObject ：传入的支付对象
	 */
	public void saveOrUpdate(PrpLpayObject prpLpayObject) throws Exception {
		logger.info("保存支付对象信息");
		super.getSession().saveOrUpdate(prpLpayObject);
	}

	/**
	 * 删除支付对象信息
	 * @param payObjectCode ：传入的支付对象编号
	 */
	@Override
	public void delete(String payObjectCode) throws Exception {
		super.deleteByPK(payObjectCode);
		logger.info("删除支付对象编号为" + payObjectCode + "的支付对象信息");
	}

	/**
	 * @description: 支付对象修改
	 * @param PrpLpayObject prpLpayObject
	 * @throws Exception
	 */
	@Override
	public void update(PrpLpayObject prpLpayObject) {
		logger.info("修改支付对象信息开始");
		super.update(prpLpayObject);
		logger.info("修改支付对象信息结束");
	}

	/**
	 * 根据支付对象编号查询出支付对象信息
	 * @param payObjectCode ：传入的支付对象编号
	 * @return 返回支付对象
	 */
	@Override
	public PrpLpayObject findPrpLpayObject(String payObjectCode) throws Exception {
		logger.info("查询支付对象编号为" + payObjectCode + "的支付对象信息");
		return super.get(PrpLpayObject.class, payObjectCode);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的支付对象页面信息
	 */
	@Override
	public Page findPrpLpayObject(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取支付对象列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLpayObject> findPrpLpayObject(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void insertAll(List<PrpLpayObject> list) {
		if (list != null && list.size() > 0) {
			Session session = super.getSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpLpayObject where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLpayObject.class);
	}
}