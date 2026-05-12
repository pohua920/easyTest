package com.sinosoft.claim.schema.service.spring;

/**
 * 报案信息补充说明接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistExtId;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;

public class PrpLregistExtServiceSpringImpl extends
		GenericDaoHibernate<PrpLregistExt, PrpLregistExtId> implements
		PrpLregistExtService {
	
	/**
	 * 保存报案信息补充说明信息
	 * @param prpLregistExt ：传入的报案信息补充说明
	 */
	@Override
	public void save(PrpLregistExt prpLregistExt) throws Exception {
		logger.info("保存报案信息补充说明信息");
		super.save(prpLregistExt);
	}
	
	/**
	 * 保存报案信息补充说明信息
	 * @param list:保存报案信息补充说明信息
	 */
	@Override
	public void save(List<PrpLregistExt> list) throws Exception {
		if(list!=null&&list.size()>0){
			super.saveAll(list);
		}
	}
	
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLregistExt> list)throws Exception{
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				this.saveOrUpdate(list.get(i));
			}
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLregistExt prpLregistExt)throws Exception{
		if(prpLregistExt!=null){
			super.getSession().saveOrUpdate(prpLregistExt);
		}
	}
	/**
	 * 删除报案信息补充说明信息
	 * @param prpLregistExtId ：传入的报案信息补充说明编号
	 */
	@Override
	public void delete(PrpLregistExtId prpLregistExtId) throws Exception{
		super.deleteByPK(PrpLregistExt.class, prpLregistExtId);
		logger.info("删除报案信息补充说明编号为" + prpLregistExtId + "的报案信息补充说明信息");
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		logger.info("删除报案信息补充说明编号为" + registNo + "的报案信息补充说明信息");
		String sql = "delete from PrpLregistExt where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}
	/**
	 * @description: 报案信息补充说明修改
	 * @param PrpLregistExt prpLregistExt
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLregistExt prpLregistExt){
		logger.info("修改报案信息补充说明信息开始");
		super.update(prpLregistExt);
		logger.info("修改报案信息补充说明信息结束");
	}
	
	/**
	 * 根据报案信息补充说明编号查询出报案信息补充说明信息
	 * @param prpLregistExtId ：传入的报案信息补充说明编号
	 * @return 返回报案信息补充说明
	 */
	@Override
	public PrpLregistExt findPrpLregistExt(PrpLregistExtId prpLregistExtId) throws Exception{
		logger.info("查询报案信息补充说明编号为" + prpLregistExtId + "的报案信息补充说明信息");
		return super.get(PrpLregistExt.class,prpLregistExtId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案信息补充说明页面信息
	 */
	@Override
	public Page findPrpLregistExt(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取报案信息补充说明列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLregistExt> findPrpLregistExt(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
}