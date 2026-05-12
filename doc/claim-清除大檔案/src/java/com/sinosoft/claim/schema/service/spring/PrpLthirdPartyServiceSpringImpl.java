package com.sinosoft.claim.schema.service.spring;

/**
 * 理赔车辆接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdPartyId;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;

public class PrpLthirdPartyServiceSpringImpl extends
		GenericDaoHibernate<PrpLthirdParty, PrpLthirdPartyId> implements
		PrpLthirdPartyService {
	
	/**
	 * 保存理赔车辆信息
	 * @param prpLthirdParty ：传入的理赔车辆
	 */
	@Override
	public void save(PrpLthirdParty prpLthirdParty) throws Exception {
		logger.info("保存理赔车辆信息");
		super.save(prpLthirdParty);
	}
	
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	@Override
	public void save(List<PrpLthirdParty> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	public void saveOrUpdate(List<PrpLthirdParty> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	public void saveOrUpdate(PrpLthirdParty prpLthirdParty) throws Exception {
		logger.info("保存理赔车辆信息");
		Session session = super.getSession();
		session.saveOrUpdate(session.merge(prpLthirdParty));
	}
	/**
	 * 删除理赔车辆信息
	 * @param prpLthirdPartyId ：传入的理赔车辆编号
	 */
	@Override
	public void delete(PrpLthirdPartyId prpLthirdPartyId) throws Exception{
		super.deleteByPK(prpLthirdPartyId);
		logger.info("删除理赔车辆编号为" + prpLthirdPartyId + "的理赔车辆信息");
	}
	
	/**
	 * @description: 理赔车辆修改
	 * @param PrpLthirdParty prpLthirdParty
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLthirdParty prpLthirdParty){
		logger.info("修改理赔车辆信息开始");
		super.update(prpLthirdParty);
		logger.info("修改理赔车辆信息结束");
	}
	
	/**
	 * 根据理赔车辆编号查询出理赔车辆信息
	 * @param prpLthirdPartyId ：传入的理赔车辆编号
	 * @return 返回理赔车辆
	 */
	@Override
	public PrpLthirdParty findPrpLthirdParty(PrpLthirdPartyId prpLthirdPartyId) throws Exception{
		logger.info("查询理赔车辆编号为" + prpLthirdPartyId + "的理赔车辆信息");
		return super.get(prpLthirdPartyId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔车辆页面信息
	 */
	@Override
	public Page findPrpLthirdParty(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取理赔车辆列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLthirdParty> findPrpLthirdParty(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from PrpLthirdParty where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLthirdParty> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
}