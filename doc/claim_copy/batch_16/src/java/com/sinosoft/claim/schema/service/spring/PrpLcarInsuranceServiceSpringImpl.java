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

import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcarInsuranceId;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;

public class PrpLcarInsuranceServiceSpringImpl extends
GenericDaoHibernate<PrpLcarInsurance, PrpLcarInsuranceId> implements PrpLcarInsuranceService {


	/**
	 * 保存车体险讯息信息
	 * @param PrpLcarInsurance ：传入的车体险讯息
	 */
	@Override
	public void save(PrpLcarInsurance prpLcarInsurance) throws Exception {
		logger.info("保存车体险讯息信息");
		super.save(prpLcarInsurance);
	}
	
	/**
	 * 保存车体险讯息信息
	 * @param list:保存车体险讯息信息
	 */
	@Override
	public void save(List<PrpLcarInsurance> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除车体险讯息信息
	 * @param PrpLcarInsuranceId ：传入的车体险讯息编号
	 */
	@Override
	public void delete(PrpLcarInsuranceId prpLcarInsuranceId) throws Exception{
		super.deleteByPK(prpLcarInsuranceId);
		logger.info("删除车体险讯息编号为" + prpLcarInsuranceId + "的车体险讯息信息");
	}
	
	/**
	 * @description: 车体险讯息修改
	 * @param PrpLcarInsurance PrpLcarInsurance
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLcarInsurance prpLcarInsurance){
		logger.info("修改车体险讯息信息开始");
		super.update(prpLcarInsurance);
		logger.info("修改车体险讯息信息结束");
	}
	
	/**
	 * 根据车体险讯息编号查询出车体险讯息信息
	 * @param PrpLcarInsuranceId ：传入的车体险讯息编号
	 * @return 返回车体险讯息
	 */
	@Override
	public PrpLcarInsurance findPrpLcarInsurance(PrpLcarInsuranceId prpLcarInsuranceId) throws Exception{
		logger.info("查询车体险讯息编号为" + prpLcarInsuranceId + "的车体险讯息信息");
		return super.get(PrpLcarInsurance.class,prpLcarInsuranceId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车体险讯息页面信息
	 */
	@Override
	public Page findPrpLcarInsurance(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取车体险讯息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcarInsurance> findPrpLcarInsurance(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}


	@Override
	public void insertAll(List<PrpLcarInsurance> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = "delete from PrpLcarInsurance where compensateNo=?";
		super.getSession().createSQLQuery(sql).setString(0, compensateNo).executeUpdate();
	}

	@Override
	public void saveOrUpdate(List<PrpLcarInsurance> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpLcarInsurance prpLcarInsurance)
			throws Exception {
		logger.info("保存车体险讯息信息");
		super.getSession().saveOrUpdate(prpLcarInsurance);
	}

}
