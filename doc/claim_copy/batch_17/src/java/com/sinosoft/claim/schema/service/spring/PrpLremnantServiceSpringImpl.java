package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLremnant;
import com.sinosoft.claim.schema.model.PrpLremnantId;
import com.sinosoft.claim.schema.service.facade.PrpLremnantService;

public class PrpLremnantServiceSpringImpl extends
GenericDaoHibernate<PrpLremnant, PrpLremnantId> implements PrpLremnantService {

	/**
	 * 保存残余物信息
	 * @param prpLremnant ：传入的残余物
	 */
	@Override
	public void save(PrpLremnant prpLremnant) throws Exception {
		logger.info("保存残余物信息");
		super.save(prpLremnant);
	}
	
	/**
	 * 保存残余物信息
	 * @param list:保存残余物信息
	 */
	@Override
	public void save(List<PrpLremnant> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存残余物信息
	 * @param list:保存残余物信息
	 */
	public void saveOrUpdate(List<PrpLremnant> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存残余物信息
	 * @param prpLremnant ：传入的残余物
	 */
	public void saveOrUpdate(PrpLremnant prpLremnant) throws Exception {
		logger.info("保存残余物信息");
		super.getSession().saveOrUpdate(prpLremnant);
	}
	/**
	 * 删除残余物信息
	 * @param prpLremnantId ：传入的残余物编号
	 */
	@Override
	public void delete(PrpLremnantId prpLremnantId) throws Exception{
		super.deleteByPK(prpLremnantId);
		logger.info("删除残余物编号为" + prpLremnantId + "的残余物信息");
	}
	
	/**
	 * @description: 残余物修改
	 * @param PrpLremnant prpLremnant
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLremnant prpLremnant){
		logger.info("修改残余物信息开始");
		super.update(prpLremnant);
		logger.info("修改残余物信息结束");
	}
	
	/**
	 * 根据残余物编号查询出残余物信息
	 * @param prpLremnantId ：传入的残余物编号
	 * @return 返回残余物
	 */
	@Override
	public PrpLremnant findPrpLremnant(PrpLremnantId prpLremnantId) throws Exception{
		logger.info("查询残余物编号为" + prpLremnantId + "的残余物信息");
		return super.get(PrpLremnant.class,prpLremnantId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的残余物页面信息
	 */
	@Override
	public Page findPrpLremnant(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取残余物列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLremnant> findPrpLremnant(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询所有信息
	 */
	public List<PrpLremnant> findByCompensateNo(String compensateNo)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", compensateNo);
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception{
		String sql = "delete from PrpLremnant where compensateNo=?";
		super.getSession().createSQLQuery(sql).setString(0, compensateNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLremnant> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
}
