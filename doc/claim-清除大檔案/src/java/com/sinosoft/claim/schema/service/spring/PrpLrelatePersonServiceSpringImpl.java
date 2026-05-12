package com.sinosoft.claim.schema.service.spring;

/**
 * 联系人接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLrelatePerson;
import com.sinosoft.claim.schema.model.PrpLrelatePersonId;
import com.sinosoft.claim.schema.service.facade.PrpLrelatePersonService;

public class PrpLrelatePersonServiceSpringImpl extends
		GenericDaoHibernate<PrpLrelatePerson, PrpLrelatePersonId> implements
		PrpLrelatePersonService {
	
	/**
	 * 保存联系人信息
	 * @param prpLrelatePerson ：传入的联系人
	 */
	@Override
	public void save(PrpLrelatePerson prpLrelatePerson) throws Exception {
		logger.info("保存联系人信息");
		super.save(prpLrelatePerson);
	}
	
	/**
	 * 保存联系人信息
	 * @param list:保存联系人信息
	 */
	@Override
	public void save(List<PrpLrelatePerson> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存联系人信息
	 * @param list:保存联系人信息
	 */
	public void saveOrUpdate(List<PrpLrelatePerson> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存联系人信息
	 * @param prpLrelatePerson ：传入的联系人
	 */
	public void saveOrUpdate(PrpLrelatePerson prpLrelatePerson) throws Exception {
		logger.info("保存联系人信息");
		super.getSession().saveOrUpdate(prpLrelatePerson);
	}
	/**
	 * 删除联系人信息
	 * @param prpLrelatePersonId ：传入的联系人编号
	 */
	@Override
	public void delete(PrpLrelatePersonId prpLrelatePersonId) throws Exception{
		super.deleteByPK(prpLrelatePersonId);
		logger.info("删除联系人编号为" + prpLrelatePersonId + "的联系人信息");
	}
	
	/**
	 * @description: 联系人修改
	 * @param PrpLrelatePerson prpLrelatePerson
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLrelatePerson prpLrelatePerson){
		logger.info("修改联系人信息开始");
		super.update(prpLrelatePerson);
		logger.info("修改联系人信息结束");
	}
	
	/**
	 * 根据联系人编号查询出联系人信息
	 * @param prpLrelatePersonId ：传入的联系人编号
	 * @return 返回联系人
	 */
	@Override
	public PrpLrelatePerson findPrpLrelatePerson(PrpLrelatePersonId prpLrelatePersonId) throws Exception{
		logger.info("查询联系人编号为" + prpLrelatePersonId + "的联系人信息");
		return super.get(PrpLrelatePerson.class,prpLrelatePersonId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的联系人页面信息
	 */
	@Override
	public Page findPrpLrelatePerson(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取联系人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLrelatePerson> findPrpLrelatePerson(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLrelatePerson where registNo='"+registNo+"'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
}