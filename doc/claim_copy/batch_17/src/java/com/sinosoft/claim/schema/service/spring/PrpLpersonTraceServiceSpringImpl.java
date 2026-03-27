package com.sinosoft.claim.schema.service.spring;

/**
 * 人伤跟踪接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLpersonTraceId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;

public class PrpLpersonTraceServiceSpringImpl extends
		GenericDaoHibernate<PrpLpersonTrace, PrpLpersonTraceId> implements
		PrpLpersonTraceService {
	
	/**
	 * 保存人伤跟踪信息
	 * @param prpLpersonTrace ：传入的人伤跟踪
	 */
	@Override
	public void save(PrpLpersonTrace prpLpersonTrace) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLpersonTrace);
	}
	
	/**
	 * 保存人伤跟踪信息
	 * @param list:保存人伤跟踪信息
	 */
	@Override
	public void save(List<PrpLpersonTrace> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存人伤跟踪信息
	 * @param list:保存人伤跟踪信息
	 */
	public void saveOrUpdate(List<PrpLpersonTrace> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
			PrpLpersonTrace old = findPrpLpersonTrace(list.get(i).getId());
			if(old != null){
				delete(old.getId()); 
			}
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 保存人伤跟踪信息
	 * @param prpLpersonTrace ：传入的人伤跟踪
	 */
	public void saveOrUpdate(PrpLpersonTrace prpLpersonTrace) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.getSession().saveOrUpdate(prpLpersonTrace);
	}
	/**
	 * 删除人伤跟踪信息
	 * @param prpLpersonTraceId ：传入的人伤跟踪编号
	 */
	@Override
	public void delete(PrpLpersonTraceId prpLpersonTraceId) throws Exception{
		super.deleteByPK(prpLpersonTraceId);
		logger.info("删除人伤跟踪编号为" + prpLpersonTraceId + "的人伤跟踪信息");
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		logger.info("删除人伤跟踪编号为" + registNo + "的人伤跟踪信息");
		String sql = "delete from PrpLpersonTrace where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}
	/**
	 * @description: 人伤跟踪修改
	 * @param PrpLpersonTrace prpLpersonTrace
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLpersonTrace prpLpersonTrace){
		logger.info("修改人伤跟踪信息开始");
		super.update(prpLpersonTrace);
		logger.info("修改人伤跟踪信息结束");
	}
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param prpLpersonTraceId ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	@Override
	public PrpLpersonTrace findPrpLpersonTrace(PrpLpersonTraceId prpLpersonTraceId) throws Exception{
		logger.info("查询人伤跟踪编号为" + prpLpersonTraceId + "的人伤跟踪信息");
		return super.get(PrpLpersonTrace.class,prpLpersonTraceId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人伤跟踪页面信息
	 */
	@Override
	public Page findPrpLpersonTrace(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取人伤跟踪列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLpersonTrace> findPrpLpersonTrace(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void insertAll(List<PrpLpersonTrace> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
}