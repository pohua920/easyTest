package com.sinosoft.claim.schema.service.spring;

/**
 * 计划信息处理率
 * @author 中科软
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.platform.bl.action.domain.BLPrpDagentAction;
import com.sinosoft.sysframework.reference.DBManager;

public class PrpDagentServiceSpringImpl extends GenericDaoHibernate<PrpDagent, String> implements PrpDagentService {

	 /**
     * 按主键删除一条数据
     * @param agentCode 代理人代码
     * @throws Exception
     */
	@Override
	public void delete(String agentCode) throws Exception {
		super.deleteByPK(agentCode);
	}

	/**
     * 按条件删除数据
     * @param condtions 删除条件
     * @throws Exception
     */
	@Override
	public void deleteByConditions(String conditions) throws Exception {
		String statement = "Delete From PrpDagent Where " + conditions;
		super.getSession().createSQLQuery(statement).executeUpdate();
	}

	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection 包含prpDagent的集合
     * @throws Exception
     */
	@Override
	public Page findPrpDagent(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDagent where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpDagent.class);
	}

	 /**
     * 插入一条数据
     * @param prpDagentDto prpDagentDto
     * @throws Exception
     */
	@Override
	public void save(PrpDagent prpDagent) throws Exception {
		super.save(prpDagent);
	}
	/**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpDagent prpDagent
     * @throws Exception
     */
	public void update(PrpDagent prpDagent){
		super.getSession().saveOrUpdate(prpDagent);
	}

	/**
     * 按主键查找一条数据
     * @param agentCode 代理人代码
     * @return prpDagent prpDagent
     * @throws Exception
     */
	@Override
	public PrpDagent findPrpDagent(String agentCode) throws Exception {
		return super.get(agentCode);
	}

	 /** 根据代理人代码得到代理人姓名
     * @param agentCode 代理人代码
     */
	@Override
	public String translateAgentName(String agentCode) throws SQLException, Exception {
		String agentName="";
		if(!CommonUtils.isEmpty(agentCode)){
			PrpDagent prpDagent=super.get(PrpDagent.class, agentCode);
			if(prpDagent!=null) {
				agentName = prpDagent.getAgenteName();
			}
		}
		return agentName;
	}

	/**
	 * 按条件查询数据
	 * @param conditons
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<PrpDagent> findByConditions(String conditions) throws Exception {
		  Collection<PrpDagent> collection = new ArrayList<PrpDagent>();

	        if(conditions.trim().length()==0){
	            conditions = "1=1";
	        }

	        DBManager dbManager = new DBManager();
	        BLPrpDagentAction blPrpDagentAction = new BLPrpDagentAction();
	        try{
	            dbManager.open("platformDataSource");
	            collection = blPrpDagentAction.findByConditions(dbManager,conditions);
	        }catch(Exception exception){
	            throw  exception;
	        }finally{
	            dbManager.close();
	        }
	        return collection;
	}
	/**
	 * 查询销管系统的讯息
	 * 台壽通路營業人員
	 * 台壽通路營業主管ID,电话
	 * @param sales 用户名称
	 * @param handlerCode
	 * @return
	 * @throws Exception
	 */
	public PrpDagent findSalesPrpDagent(String salesUser,String handlerCode )throws Exception{
		PrpDagent prpDagent = new PrpDagent();
		if(salesUser==null||"".equals(salesUser)){
			salesUser = "";
		}else{
			salesUser = salesUser+".";
		}
		String sql = "select agenTname,mobile from "+salesUser+"prpdagent where agentCode='"+handlerCode+"'  and channeltype = '12'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql);
		if(list.size()>0){
			Object[] objs = (Object[]) list.get(0);
			prpDagent.setAgentName(String.valueOf(objs[0]));
			prpDagent.setMobileNo(String.valueOf(objs[1]));
			String temp = "select mobile from "+salesUser+"prpdagent where agentcode in ("
				          +" select agentcode from "+salesUser+"prpdagentidv where certino in  (" 
				          +" select directorid from "+salesUser+"prpdaffiliatedcompany where unitscode in "
				          +" (select unitcode from "+salesUser+"prpdagent where agentcode='"+handlerCode+"' and channeltype = '12')))";
			list = HibernateUtils.findbySql(super.getSession(),temp);
			if(list.size()>0){
				prpDagent.setMobile(String.valueOf(list.get(0)));
			}
		}
		return prpDagent;
	}
}
