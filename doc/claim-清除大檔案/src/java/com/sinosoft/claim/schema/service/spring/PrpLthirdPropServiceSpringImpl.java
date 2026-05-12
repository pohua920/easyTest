package com.sinosoft.claim.schema.service.spring;

/**
 * 财产损失部位接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLthirdPropId;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;

public class PrpLthirdPropServiceSpringImpl extends
		GenericDaoHibernate<PrpLthirdProp, PrpLthirdPropId> implements
		PrpLthirdPropService {
	
	/**
	 * 保存财产损失部位信息
	 * @param prpLthirdProp ：传入的财产损失部位
	 */
	@Override
	public void save(PrpLthirdProp prpLthirdProp) throws Exception {
		logger.info("保存财产损失部位信息");
		super.save(prpLthirdProp);
	}
	
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息
	 */
	@Override
	public void save(List<PrpLthirdProp> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存财产损失部位信息
	 * @param prpLthirdProp ：传入的财产损失部位
	 */
	public void saveOrUpdate(PrpLthirdProp prpLthirdProp) throws Exception {
		logger.info("保存财产损失部位信息");
		super.getSession().saveOrUpdate(prpLthirdProp);
	}
	
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息
	 */
	public void saveOrUpdate(List<PrpLthirdProp> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 删除财产损失部位信息
	 * @param prpLthirdPropId ：传入的财产损失部位编号
	 */
	@Override
	public void delete(PrpLthirdPropId prpLthirdPropId) throws Exception{
		super.deleteByPK(prpLthirdPropId);
		logger.info("删除财产损失部位编号为" + prpLthirdPropId + "的财产损失部位信息");
	}
	
	/**
	 * @description: 财产损失部位修改
	 * @param PrpLthirdProp prpLthirdProp
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLthirdProp prpLthirdProp){
		logger.info("修改财产损失部位信息开始");
		super.update(prpLthirdProp);
		logger.info("修改财产损失部位信息结束");
	}
	
	/**
	 * 根据财产损失部位编号查询出财产损失部位信息
	 * @param prpLthirdPropId ：传入的财产损失部位编号
	 * @return 返回财产损失部位
	 */
	@Override
	public PrpLthirdProp findPrpLthirdProp(PrpLthirdPropId prpLthirdPropId) throws Exception{
		logger.info("查询财产损失部位编号为" + prpLthirdPropId + "的财产损失部位信息");
		return super.get(PrpLthirdProp.class,prpLthirdPropId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的财产损失部位页面信息
	 */
	@Override
	public Page findPrpLthirdProp(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取财产损失部位列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLthirdProp> findPrpLthirdProp(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLthirdProp where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLthirdProp> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
}