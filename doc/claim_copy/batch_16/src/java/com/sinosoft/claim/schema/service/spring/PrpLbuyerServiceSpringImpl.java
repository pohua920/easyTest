package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLbuyer;
import com.sinosoft.claim.schema.model.PrpLbuyerId;
import com.sinosoft.claim.schema.service.facade.PrpLbuyerService;

public class PrpLbuyerServiceSpringImpl extends
GenericDaoHibernate<PrpLbuyer, PrpLbuyerId> implements PrpLbuyerService {

	/**
	 * 保存买受人信息
	 * @param prpLbuyer ：传入的买受人
	 */
	@Override
	public void save(PrpLbuyer prpLbuyer) throws Exception {
		logger.info("保存买受人信息");
		super.save(prpLbuyer);
	}
	
	/**
	 * 保存买受人信息
	 * @param list:保存买受人信息
	 */
	@Override
	public void save(List<PrpLbuyer> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存买受人信息
	 * @param list:保存买受人信息
	 */
	public void saveOrUpdate(List<PrpLbuyer> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存买受人信息
	 * @param prpLbuyer ：传入的买受人
	 */
	public void saveOrUpdate(PrpLbuyer prpLbuyer) throws Exception {
		logger.info("保存买受人信息");
		super.getSession().saveOrUpdate(prpLbuyer);
	}
	/**
	 * 删除买受人信息
	 * @param prpLbuyerId ：传入的买受人编号
	 */
	@Override
	public void delete(PrpLbuyerId prpLbuyerId) throws Exception{
		super.deleteByPK(prpLbuyerId);
		logger.info("删除买受人编号为" + prpLbuyerId + "的买受人信息");
	}
	
	/**
	 * @description: 买受人修改
	 * @param PrpLbuyer prpLbuyer
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLbuyer prpLbuyer){
		logger.info("修改买受人信息开始");
		super.update(prpLbuyer);
		logger.info("修改买受人信息结束");
	}
	
	/**
	 * 根据买受人编号查询出买受人信息
	 * @param prpLbuyerId ：传入的买受人编号
	 * @return 返回买受人
	 */
	@Override
	public PrpLbuyer findPrpLbuyer(PrpLbuyerId prpLbuyerId) throws Exception{
		logger.info("查询买受人编号为" + prpLbuyerId + "的买受人信息");
		return super.get(PrpLbuyer.class,prpLbuyerId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的买受人页面信息
	 */
	@Override
	public Page findPrpLbuyer(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取买受人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLbuyer> findPrpLbuyer(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询所有信息
	 */
	public List<PrpLbuyer> findByCompensateNo(String compensateNo)throws Exception{
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
		String sql = "delete from PrpLbuyer where compensateNo=?";
		super.getSession().createSQLQuery(sql).setString(0, compensateNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLbuyer> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

}
