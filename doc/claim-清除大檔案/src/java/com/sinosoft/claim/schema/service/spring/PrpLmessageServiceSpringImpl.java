package com.sinosoft.claim.schema.service.spring;
/**
 * :理赔流转讨论留言信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLmessage;
import com.sinosoft.claim.schema.model.PrpLmessageId;
import com.sinosoft.claim.schema.service.facade.PrpLmessageService;

public class PrpLmessageServiceSpringImpl extends
GenericDaoHibernate<PrpLmessage, PrpLmessageId> implements PrpLmessageService{

	
	@Override
	public void save(PrpLmessage prpLmessage) throws Exception {
		logger.info("保存:理赔流转讨论留言信息");
		super.save(prpLmessage);
		
	}

	@Override
	public void save(List<PrpLmessage> list) throws Exception {
		logger.info("保存:理赔流转讨论留言信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLmessageId prpLmessageId) throws Exception {
		logger.info("删除:理赔流转讨论留言信息编号为" + prpLmessageId + "的:理赔流转讨论留言信息");
		super.deleteByPK(PrpLmessage.class, prpLmessageId);
	}

	@Override
	public PrpLmessage findPrpLmessage(PrpLmessageId prpLmessageId) throws Exception {
		logger.info("查询:理赔流转讨论留言信息编号为" + prpLmessageId + "的:理赔流转讨论留言信息");
		return super.get(PrpLmessage.class, prpLmessageId);
	}

	@Override
	public Page findPrpLmessage(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取:理赔流转讨论留言信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLmessage> findPrpLmessage(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询留言
	 */
	public List<PrpLmessage> findPrpLmessageByRegistNo(String registNo)
		throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		return super.find(queryRule);
	}
	
	/**
	 * 根据:理赔流转讨论留言编号查询出:理赔流转讨论留言信息
	 * @param certiNo ：传入的:理赔流转讨论留言编号
	 * @return 返回:理赔流转讨论留言
	 */
	public PrpLmessage findPrpLmessage(String certiNo) throws Exception{
		PrpLmessage prpLmessage = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLmessage> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLmessage = resultList.get(0);
		}
		return prpLmessage;
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 获取留言信息
	 */
	public int findMaxNo(String registNo)throws Exception{
		int messageID = 1;
		if(registNo!=null&&!"".equals(registNo)){
			String sql = "Select max(serialNo+1) from PrpLmessage Where RegistNo='" + registNo + "'";
			List<Object> list = super.getSession().createSQLQuery(sql).list();
			if (list == null || list.size() == 0||list.get(0)==null) {
				messageID = 1;
			} else {
				messageID = ((Number) list.get(0)).intValue();
			}
			logger.info("DBPrpLmessageBase.getCount() success!");
		}
		return messageID;
	}
	
}
