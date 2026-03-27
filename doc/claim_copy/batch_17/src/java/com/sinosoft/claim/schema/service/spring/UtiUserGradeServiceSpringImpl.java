package com.sinosoft.claim.schema.service.spring;
/**
 * 用户岗位接口实现类
 * @author 中科软
 *
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.model.UtiUserGradeId;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;

public class UtiUserGradeServiceSpringImpl extends
GenericDaoHibernate<UtiUserGrade, UtiUserGradeId> implements UtiUserGradeService {

	@Override
	public void delete(UtiUserGradeId utiUserGradeId) throws Exception {
		super.deleteByPK(utiUserGradeId);
		logger.info("删除用户岗位定义表编号为" + utiUserGradeId + "的用户岗位定义表信息");
	}

	@Override
	public UtiUserGrade findUtiUserGrade(UtiUserGradeId utiUserGradeId) throws Exception {
		logger.info("查询用户岗位定义表编号为" + utiUserGradeId + "的用户岗位定义表信息");
		return super.get(UtiUserGrade.class,utiUserGradeId);
	}

	@Override
	public Page findUtiUserGrade(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取用户岗位定义表列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<UtiUserGrade> findUtiUserGrade(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void insertAll(List<UtiUserGrade> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public void save(UtiUserGrade utiUserGrade) throws Exception {
		logger.info("保存用户岗位定义表信息");
		super.save(utiUserGrade);
	}

	@Override
	public void save(List<UtiUserGrade> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(List<UtiUserGrade> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(UtiUserGrade utiUserGrade) throws Exception {
		logger.info("保存用户岗位定义表信息");
		super.getSession().saveOrUpdate(utiUserGrade);
	}

	@Override
	public void update(UtiUserGrade utiUserGrade) {
		logger.info("修改用户岗位定义表信息开始");
		super.update(utiUserGrade);
		logger.info("修改用户岗位定义表信息结束");
	}

	@Override
	public Collection<UtiUserGrade> findByConditions(String conditions)
			throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return find(queryRule);
	}
	/**
	 * 查询用户的角色
	 * @param userCode
	 * @return
	 */
	public List<String> findGradeCodeByUserCode(String userCode){
		String sql = " select distinct gradecode from UtiUserGrade where  usercode='" + userCode + "' and gradeCode in (select gradeCode from utiGradeTask where taskcode='claim')  order by usercode";
		List<?> temp = HibernateUtils.findbySql(super.getSession(), sql);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<temp.size();i++){
			list.add(String.valueOf(temp.get(i)));
		}
		return list;
	}
}
