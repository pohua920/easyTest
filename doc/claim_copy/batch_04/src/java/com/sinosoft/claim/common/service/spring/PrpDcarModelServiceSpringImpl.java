package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDcarModelService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDcarModel;

public class PrpDcarModelServiceSpringImpl extends GenericDaoHibernate<PrpDcarModel, String> implements PrpDcarModelService {

	/**
	 * 根据条件查询车型数据
	 * @author 中科软
	 * @param condition 查询条件
	 * @return
	 */
	@Override
	public List<PrpDcarModel> findByConditions(String conditions) {
		return super.find(QueryRule.getInstance().addSql(conditions));
	}
	/***
	 * 按条件查询厂商数据
	 * @author 中科软
 	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示条数
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findByConditionsFactory(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "Select distinct Factory From PrpDcarModel Where " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, rowsPerPage);
		List<PrpDcarModel> list = new ArrayList<PrpDcarModel>();
		List<?> result = page.getResult();
		if (result != null && !result.isEmpty()) {
			PrpDcarModel prpDcarModel = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				prpDcarModel = new PrpDcarModel();
				prpDcarModel.setFactory(DataUtils.dbNullToEmpty((String.valueOf(it.next()))));
				list.add(prpDcarModel);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), rowsPerPage, list);
	}
	/***
	 * 分页查询车型数据
	 * @author 中科软
	 * @date Mar 27, 2013 2:35:05 PM
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示条数
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findByPage(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDcarModel where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, rowsPerPage,PrpDcarModel.class);
	}
}
