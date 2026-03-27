package com.sinosoft.claim.schema.service.spring;
/**
 * 支付信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfoId;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;

public class PrpLpayObjectInfoServiceSpringImpl extends
GenericDaoHibernate<PrpLpayObjectInfo, PrpLpayObjectInfoId> implements PrpLpayObjectInfoService {


	/**
	 * 保存支付对象信息
	 * @param PrpLpayObjectInfo ：传入的支付对象
	 */
	@Override
	public void save(PrpLpayObjectInfo PrpLpayObjectInfo) throws Exception {
		logger.info("保存支付对象信息");
		super.save(PrpLpayObjectInfo);
	}
	
	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	@Override
	public void save(List<PrpLpayObjectInfo> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除支付对象信息
	 * @param PrpLpayObjectInfoId ：传入的支付对象编号
	 */
	@Override
	public void delete(PrpLpayObjectInfoId PrpLpayObjectInfoId) throws Exception{
		super.deleteByPK(PrpLpayObjectInfoId);
		logger.info("删除支付对象编号为" + PrpLpayObjectInfoId + "的支付对象信息");
	}
	
	/**
	 * @description: 支付对象修改
	 * @param PrpLpayObjectInfo PrpLpayObjectInfo
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLpayObjectInfo PrpLpayObjectInfo){
		logger.info("修改支付对象信息开始");
		super.update(PrpLpayObjectInfo);
		logger.info("修改支付对象信息结束");
	}
	
	/**
	 * 根据支付对象编号查询出支付对象信息
	 * @param PrpLpayObjectInfoId ：传入的支付对象编号
	 * @return 返回支付对象
	 */
	@Override
	public PrpLpayObjectInfo findPrpLpayObjectInfo(PrpLpayObjectInfoId PrpLpayObjectInfoId) throws Exception{
		logger.info("查询支付对象编号为" + PrpLpayObjectInfoId + "的支付对象信息");
		return super.get(PrpLpayObjectInfo.class,PrpLpayObjectInfoId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的支付对象页面信息
	 */
	@Override
	public Page findPrpLpayObjectInfo(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取支付对象列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLpayObjectInfo> findPrpLpayObjectInfo(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}


	@Override
	public void insertAll(List<PrpLpayObjectInfo> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = "delete from PrpLpayObjectInfo where compensateNo=?";
		super.getSession().createSQLQuery(sql).setString(0, compensateNo).executeUpdate();
	}

	@Override
	public void saveOrUpdate(List<PrpLpayObjectInfo> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpLpayObjectInfo prpLpayObjectInfo)
			throws Exception {
		logger.info("保存支付对象信息");
		super.getSession().saveOrUpdate(prpLpayObjectInfo);
	}

}
