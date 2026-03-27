package com.sinosoft.claim.schema.service.spring;

/**
 * 损失部位接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdCarLossId;
import com.sinosoft.claim.schema.service.facade.PrpLthirdCarLossService;

public class PrpLthirdCarLossServiceSpringImpl extends
		GenericDaoHibernate<PrpLthirdCarLoss, PrpLthirdCarLossId> implements
		PrpLthirdCarLossService {
	
	/**
	 * 保存损失部位信息
	 * @param prpLthirdCarLoss ：传入的损失部位
	 */
	@Override
	public void save(PrpLthirdCarLoss prpLthirdCarLoss) throws Exception {
		logger.info("保存损失部位信息");
		super.save(prpLthirdCarLoss);
	}
	
	/**
	 * 保存损失部位信息
	 * @param list:保存损失部位信息
	 */
	@Override
	public void save(List<PrpLthirdCarLoss> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存损失部位信息
	 * @param list:保存损失部位信息
	 */
	public void saveOrUpdate(List<PrpLthirdCarLoss> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存损失部位信息
	 * @param prpLthirdCarLoss ：传入的损失部位
	 */
	public void saveOrUpdate(PrpLthirdCarLoss prpLthirdCarLoss) throws Exception {
		logger.info("保存损失部位信息");
		super.getSession().saveOrUpdate(prpLthirdCarLoss);
	}
	/**
	 * 删除损失部位信息
	 * @param prpLthirdCarLossId ：传入的损失部位编号
	 */
	@Override
	public void delete(PrpLthirdCarLossId prpLthirdCarLossId) throws Exception{
		super.deleteByPK(prpLthirdCarLossId);
		logger.info("删除损失部位编号为" + prpLthirdCarLossId + "的损失部位信息");
	}
	
	/**
	 * @description: 损失部位修改
	 * @param PrpLthirdCarLoss prpLthirdCarLoss
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLthirdCarLoss prpLthirdCarLoss){
		logger.info("修改损失部位信息开始");
		super.update(prpLthirdCarLoss);
		logger.info("修改损失部位信息结束");
	}
	
	/**
	 * 根据损失部位编号查询出损失部位信息
	 * @param prpLthirdCarLossId ：传入的损失部位编号
	 * @return 返回损失部位
	 */
	@Override
	public PrpLthirdCarLoss findPrpLthirdCarLoss(PrpLthirdCarLossId prpLthirdCarLossId) throws Exception{
		logger.info("查询损失部位编号为" + prpLthirdCarLossId + "的损失部位信息");
		return super.get(PrpLthirdCarLoss.class,prpLthirdCarLossId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的损失部位页面信息
	 */
	@Override
	public Page findPrpLthirdCarLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取损失部位列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLthirdCarLoss> findPrpLthirdCarLoss(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询所有信息
	 */
	public List<PrpLthirdCarLoss> findByRegistNo(String registNo)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLthirdCarLoss where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLthirdCarLoss> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
}