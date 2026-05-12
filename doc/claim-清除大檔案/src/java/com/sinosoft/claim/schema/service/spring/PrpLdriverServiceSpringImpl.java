package com.sinosoft.claim.schema.service.spring;
/**
 *车险驾驶员信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLdriverId;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;

public class PrpLdriverServiceSpringImpl extends
GenericDaoHibernate<PrpLdriver, PrpLdriverId> implements PrpLdriverService{

	@Override
	public void save(PrpLdriver prpLcallCenter) throws Exception {
		logger.info("保存索赔单证信息");
		super.save(prpLcallCenter);
		
	}

	@Override
	public void save(List<PrpLdriver> list) throws Exception {
		logger.info("保存索赔单证信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改
	 */
	public void saveOrUpdate(PrpLdriver prpLcallCenter) throws Exception {
		logger.info("保存索赔单证信息");
		super.getSession().saveOrUpdate(prpLcallCenter);
		
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改
	 */
	public void saveOrUpdate(List<PrpLdriver> list) throws Exception {
		logger.info("保存索赔单证信息");
		for(int i=0;i<list.size();i++){
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
			PrpLdriver old = findPrpLdriver(list.get(i).getId());
			if(old != null){
				delete(old.getId()); 
			}
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
			this.saveOrUpdate(list.get(i));
		}
	}
	@Override
	public void delete(PrpLdriverId prpLcallCenterId) throws Exception {
		logger.info("删除索赔单证信息编号为" + prpLcallCenterId + "的索赔单证信息");
		super.deleteByPK(PrpLdriver.class, prpLcallCenterId);
	}

	@Override
	public PrpLdriver findPrpLdriver(PrpLdriverId prpLcallCenterId) throws Exception {
		logger.info("查询索赔单证信息编号为" + prpLcallCenterId + "的索赔单证信息");
		return super.get(PrpLdriver.class, prpLcallCenterId);
	}

	@Override
	public Page findPrpLdriver(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取索赔单证信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLdriver> findPrpLdriver(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from PrpLdriver where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}
}
