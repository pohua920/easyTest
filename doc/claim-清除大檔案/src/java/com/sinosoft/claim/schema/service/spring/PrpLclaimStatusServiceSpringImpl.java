package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔节点状态信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;

public class PrpLclaimStatusServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimStatus, PrpLclaimStatusId> implements PrpLclaimStatusService{

	@Override
	public void save(PrpLclaimStatus prpLclaimStatus) throws Exception {
		logger.info("保存理赔节点状态信息");
		Session session = super.getSession();
		session.merge(prpLclaimStatus);
	}

	@Override
	public void save(List<PrpLclaimStatus> list) throws Exception {
		logger.info("保存理赔节点状态信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLclaimStatus> list)throws Exception{
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLclaimStatus prpLclaimStatus)throws Exception{
		if(prpLclaimStatus!=null){
			super.getSession().saveOrUpdate(prpLclaimStatus);
		}
	}
	
	/**保存对象，不同步到session中
	 * @param prpLclaimStatus
	 * @throws Exception
	 */
	public void saveOrMerge(PrpLclaimStatus prpLclaimStatus)throws Exception{
		if(prpLclaimStatus!=null){
//			super.getSession().clear();
			super.getSession().merge(prpLclaimStatus);
		}
	}

	@Override
	public void delete(PrpLclaimStatusId prpLcallCenterId) throws Exception {
		logger.info("删除理赔节点状态信息编号为" + prpLcallCenterId + "的理赔节点状态信息");
		super.deleteByPK(PrpLclaimStatus.class, prpLcallCenterId);
	}
	/**
	 * @param registNo
	 * @param nodeType
	 * @throws Exception
	 * 根据报案号和节点信息，删除所有的状态
	 */
	public void deleteByRegistNo(String registNo,String nodeType) throws Exception{
		String sql = null;
		if(nodeType==null){
			sql = "delete from PrpLclaimStatus where businessNo='"+registNo+"'";
		}else{
			sql ="delete from PrpLclaimStatus where businessNo='"+registNo+"' and nodeType='"+nodeType+"'";
		}
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	@Override
	public PrpLclaimStatus findPrpLclaimStatus(PrpLclaimStatusId prpLcallCenterId) throws Exception {
		logger.info("查询理赔节点状态信息编号为" + prpLcallCenterId + "的理赔节点状态信息");
		return super.get(PrpLclaimStatus.class, prpLcallCenterId);
	}

	@Override
	public Page findPrpLclaimStatus(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取理赔节点状态信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLclaimStatus> findPrpLclaimStatus(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
