package com.sinosoft.claim.schema.service.spring;

/**
 * 收费计划接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;

public class PrpDclassServiceSpringImpl extends GenericDaoHibernate<PrpDclass, String> implements PrpDclassService {

	/**
	 * 保存优惠信息
	 * @param prpLcheck ：传入的优惠信息
	 */
	public void save(PrpDclass prpDclass) throws Exception {
		logger.info("收费计划信息");
		super.save(prpDclass);
	}

	/**
	 * 保存优惠信息集合
	 * @param list :传入的优惠信息集合
	 * @throws Exception
	 */
	public void save(List<PrpDclass> list) throws Exception {
		logger.info("收费计划信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除优惠信息
	 * @param prpDclassId ：传入的优惠信息编号
	 */
	public void delete(String classCode) throws Exception {
		logger.info("删除收费计划编号为" + classCode + "的收费计划");
		super.deleteByPK(PrpDclass.class, classCode);
	}

	/**
	 * 根据优惠信息编号查询出优惠信息
	 * @param prpDclassId ：传入的优惠信息编号
	 * @return 返回优惠信息
	 */
	public PrpDclass findPrpDclass(String classCode) throws Exception {
		logger.info("查询收费计划编号为" + classCode + "的收费计划");
		return super.get(PrpDclass.class, classCode);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的优惠信息页面信息
	 */
	public Page findPrpDclass(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取收费计划列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取  Page的列表
	 * @param queryRule
	 * @throws Exception
	 */
	public List<PrpDclass> findPrpDclass(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含prpDclassDto的集合
     * @throws Exception
     */
	@Override
	public Collection<PrpDclass> findByConditions(String conditions) throws Exception {
//        Collection<PrpDclass> collection = new ArrayList<PrpDclass>();

        if(conditions.trim().length()==0){
            conditions = " 1=1 ";
        }

        QueryRule queryRule = QueryRule.getInstance();
        queryRule.addSql(conditions);
        return super.find(queryRule);
    }
}
