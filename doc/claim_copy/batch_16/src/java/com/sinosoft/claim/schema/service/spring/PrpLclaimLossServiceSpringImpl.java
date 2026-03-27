package com.sinosoft.claim.schema.service.spring;

/**
 * 立案险别估损金额信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimLossId;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarExtService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCitemCarExt;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.prpall.schema.PrpCitemCarExtSchema;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utility.database.DbPool;
import com.sinosoft.utility.string.ChgDate;

public class PrpLclaimLossServiceSpringImpl extends GenericDaoHibernate<PrpLclaimLoss, PrpLclaimLossId> implements PrpLclaimLossService {
	private PrpLclaimService prpLclaimService;
	private ReinsServiceManager reinsServiceManager;
	private UtiCodeTransferService utiCodeTransferService;
	private PrpCmainService prpCmainService;
	private PrpLclaimLossService prpLclaimLossService;
	private PrpCitemCarExtService prpCitemCarExtService;
	
	@Override
	public void save(PrpLclaimLoss prpLclaimLoss) throws Exception {
		logger.info("保存立案险别估损金额信息");
		super.save(prpLclaimLoss);

	}

	/**
	 * @param list
	 * @throws Exception 修改或者保存的方法
	 */
	public void saveOrUpdate(PrpLclaimLoss prpLclaimLoss) throws Exception {
		logger.info("保存立案险别估损金额信息");
		super.getSession().saveOrUpdate(prpLclaimLoss);

	}

	@Override
	public void save(List<PrpLclaimLoss> list) throws Exception {
		logger.info("保存立案险别估损金额信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @param list
	 * @throws Exception 修改或者保存的方法
	 */
	public void saveOrUpdate(List<PrpLclaimLoss> list) throws Exception {
		logger.info("保存立案险别估损金额信息");
		for (int i = 0; i < list.size(); i++) {
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void delete(PrpLclaimLossId prpLclaimLossId) throws Exception {
		logger.info("删除立案险别估损金额信息编号为" + prpLclaimLossId + "的立案险别估损金额信息");
		super.deleteByPK(PrpLclaimLoss.class, prpLclaimLossId);
	}

	/**
	 * @param claimNo
	 * @throws Exception 根据立案号删除车损信息
	 */
	public void deleteByClaimNo(String claimNo) throws Exception {
		logger.info("删除立案险别估损金额信息编号为" + claimNo + "的立案险别估损金额信息");
		String sql = "delete from PrpLclaimLoss where claimNo=?";
		super.getSession().createSQLQuery(sql).setString(0, claimNo).executeUpdate();
	}

	@Override
	public PrpLclaimLoss findPrpLclaimLoss(PrpLclaimLossId prpLclaimLossId) throws Exception {
		logger.info("查询立案险别估损金额信息编号为" + prpLclaimLossId + "的立案险别估损金额信息");
		return super.get(PrpLclaimLoss.class, prpLclaimLossId);
	}
	/**
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * 根据立案号，查询多条估损信息
	 */
	public List<PrpLclaimLoss> findPrpLclaimLoss(String claimNo) throws Exception {
		List<PrpLclaimLoss> prpLclaimLossList = null;
		if(claimNo!=null&&!"".equals(claimNo)){
			QueryRule queryRule = QueryRule.getInstance().addEqual("id.claimNo", claimNo).addAscOrder("id.serialNo");
			prpLclaimLossList = this.findPrpLclaimLoss(queryRule);
		}else{
			prpLclaimLossList = new ArrayList<PrpLclaimLoss>(0);
		}
	     return prpLclaimLossList;
	}

	/**
	 * @param claimNo
	 * @return
	 * @throws Exception 获取赔款金额
	 */
	public PrpLclaimLoss getClaimLoss(String claimNo) throws Exception {
		String sql = "select sum(SumClaim) from prpLclaimLoss where claimNo = '" + claimNo + "'";
		Number resule = (Number) super.getSession().createSQLQuery(sql).uniqueResult();
		PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
		prpLclaimLoss.getId().setClaimNo(claimNo);
		double sumClaim = 0.0D;
		if (resule != null) {
			sumClaim = resule.doubleValue();
		}
		prpLclaimLoss.setSumClaim(sumClaim);
		return prpLclaimLoss;
	}

	@Override
	public Page findPrpLclaimLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取立案险别估损金额信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLclaimLoss> findPrpLclaimLoss(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(String conditions) 
        throws Exception{
        int count = -1;
        StringBuffer buffer = new StringBuffer(100);
        buffer.append("SELECT count(1) FROM (SELECT * FROM PrpLclaimLoss WHERE ");
        buffer.append(conditions);
        buffer.append(")");
        count = (int) HibernateUtils.getCountbyCountSql(getSession(), buffer.toString());
        return count;
    }

    
    public List<PrpLclaimLoss> getClaimLossList(String claimNo){
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM prplclaimloss t ");
    	sb.append("WHERE claimNo = ? and lossFeeType = 'P' ");
    	List<PrpLclaimLoss> list = getSession().createSQLQuery(sb.toString()).addEntity(entityClass).setString(0, claimNo).list();
    	return list;
    }
    
	/**
	 * 修改数据
	 * @param condition
	 * @param claimLossList
	 */
	public void updateClaimLoss(String condition,List<PrpLclaimLoss> claimLossList) throws Exception {
		// PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
		String claimNo = "";
		double sumClaimLoss = 0;
		// 将修改估损金额的存贮规则改为增量存储
		// int oldCount = this.getCount(condition);
		// 插入记录多条数据
//		Iterator<PrpLclaimLoss> lossList = claimLossList.iterator();
		// List<PrpLclaimLoss> prpLclaimLossList = new
		// ArrayList<PrpLclaimLoss>();
		// int mm = 0;
		for (PrpLclaimLoss prpLclaimLoss : claimLossList) {
			// mm++;
			// prpLclaimLoss = (PrpLclaimLoss) lossList.next();
			claimNo = prpLclaimLoss.getId().getClaimNo();
			sumClaimLoss = sumClaimLoss + prpLclaimLoss.getSumClaim();
			// prpLclaimLossList.add(prpLclaimLoss);
			// if (mm < oldCount) {
			// mm++;
			// continue;
			// }
			// this.save(prpLclaimLoss);
		}
		/*需求變更#83，歷史估损调整記錄不做删除，不需要重新组织数据插入，因为需要保留每次估損調整的時間點*/
//		prpLclaimLossService.deleteByClaimNo(claimNo);
		prpLclaimLossService.save(claimLossList);
		// 更新立案表中的险别估损金额
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		prpLclaim.setSumClaim(sumClaimLoss);
		this.update(prpLclaim);
		// 提交送再保分赔
		ClaimDto claimDto = new ClaimDto();
		claimDto.setPrpLclaim(prpLclaim);
		claimDto.setPrpLclaimLossList(claimLossList);
		WorkFlowDto workFlowDto = null;
		// 国寿财公司调整，由於再保不处理车险信息，车险理赔不需要与再保进行交互
		// String riskCode = claimDto.getPrpLclaim().getRiskCode();
		// String codeName = "";
		// List<?> utiCodeTransferList = null;
		// try {
		// utiCodeTransferList = (ArrayList<?>)
		// utiCodeTransferService.findByConditions(" outercode='" + riskCode +
		// "'");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
		// UtiCodeTransfer utiCodeTransfer = (UtiCodeTransfer)
		// utiCodeTransferList.get(0);
		// codeName = utiCodeTransfer.getRiskType();
		// }

		// if (!"D".equals(codeName)) {
		ReinsClaimMain reinsClaimMain = ReinsTranslateViewHelper.getClaimMainCollection(claimDto, workFlowDto);// 待调整
		PrpCmain prpCmain = prpCmainService.findPrpCmain(claimDto.getPrpLclaim().getPolicyNo());
		String businessNature = prpCmain.getBusinessNature();// 业务渠道
		String channelType = prpCmain.getChannelType();// 渠道类型
		ChgDate thisDte = new ChgDate();
		double exchangeRate = PubTools.getExchangeRate(prpCmain.getCurrency(),ConstantCodes.LOCAL_CURRENCY, thisDte.getCurrentTime("yyyy-MM-dd"));
		reinsClaimMain.setBusinessNature(businessNature);
		reinsClaimMain.setExchangeRate(exchangeRate);
		reinsClaimMain.setChannelType(channelType);
		// 获取业务类型、渠道、车型、兑换率
		reinsServiceManager.getReinsService().repayCal(reinsClaimMain);
	}

	/**
	 * 修改数据
	 * @param condition 查询条件
	 * @param claimLossList
	 */
	public void updateDAAClaimLoss(String claimNo,List<PrpLclaimLoss> claimLossList) throws Exception {
		// DBManager dbManager = new DBManager();
		// PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
		double sumClaimLoss = 0;
		// try {
		// dbManager.open(AppConfig.get("sysconst.DBJNDI"));
		// dbManager.beginTransaction();
		// 删除记录
		/*需求變更#83，歷史估损调整記錄不做删除，不需要重新组织数据插入，因为需要保留每次估損調整的時間點*/
//		this.deleteByClaimNo(claimNo);
		// 插入记录多条数据
		// Iterator<PrpLclaimLoss> lossList = claimLossList.iterator();
		// List<PrpLclaimLoss> prpLclaimLossList = new
		// ArrayList<PrpLclaimLoss>();
		// int mm = 0;
		for (PrpLclaimLoss prpLclaimLoss : claimLossList) {
			// prpLclaimLoss = (PrpLclaimLoss) lossList.next();
			claimNo = prpLclaimLoss.getId().getClaimNo();
			sumClaimLoss = sumClaimLoss + prpLclaimLoss.getSumClaim();
			// prpLclaimLossList.add(prpLclaimLoss);
			saveOrUpdate(prpLclaimLoss);
		}
		// 更新立案表中的险别估损金额
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		prpLclaim.setSumClaim(sumClaimLoss);
		prpLclaimService.update(prpLclaim);
		// 提交送再保分赔
		ClaimDto claimDto = new ClaimDto();
		claimDto.setPrpLclaim(prpLclaim);
		claimDto.setPrpLclaimLossList(claimLossList);

		WorkFlowDto workFlowDto = null;
		// 国寿财公司调整，由於再保不处理车险信息，车险理赔不需要与再保进行交互
		// String riskCode = claimDto.getPrpLclaim().getRiskCode();
		// String codeName = "";
		// List<UtiCodeTransfer> utiCodeTransferList = null;
		// try {
		// utiCodeTransferList = (ArrayList<UtiCodeTransfer>)
		// utiCodeTransferService.findByConditions(" outercode='" + riskCode +
		// "'");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
		// UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer)
		// utiCodeTransferList.get(0);
		// codeName = UtiCodeTransfer.getRiskType();
		// }

		// if (!"D".equals(codeName)) {
		ReinsClaimMain reinsClaimMain = ReinsTranslateViewHelper.getClaimMainCollection(claimDto, workFlowDto);
		// 获取业务类型、渠道、车型、兑换率begin
		// DbPool dbPool = new DbPool();
		// dbPool.setDBManager(dbManager);
		PrpCmain prpCmain = prpCmainService.findPrpCmain(claimDto.getPrpLclaim().getPolicyNo());
		List<PrpCitemCarExt> prpCitemCarExtList = prpCitemCarExtService.findByPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		// Vector<?> prpCitemCarExtDto = new
		// DBPrpCitemCarExt().findByPolicyNo(dbPool,
		// claimDto.getPrpLclaim().getPolicyNo());
		String businessNature = prpCmain.getBusinessNature();// 业务渠道
		String channelType = prpCmain.getChannelType();// 渠道类型
		String cartypeCode = "";
		if (null != prpCitemCarExtList && prpCitemCarExtList.size() > 0) {
			cartypeCode = prpCitemCarExtList.get(0).getCartypeCode();// 车型
		}
		ChgDate thisDte = new ChgDate();
		double exchangeRate = PubTools.getExchangeRate(prpCmain.getCurrency(),ConstantCodes.LOCAL_CURRENCY, thisDte.getCurrentTime("yyyy-MM-dd"));
		reinsClaimMain.setBusinessNature(businessNature);
		reinsClaimMain.setExchangeRate(exchangeRate);
		reinsClaimMain.setChannelType(channelType);
		reinsClaimMain.setCartypeCode(cartypeCode);
		// 获取业务类型、渠道、车型、兑换率end
		reinsServiceManager.getReinsService().repayCal(reinsClaimMain);
		// dbManager.commitTransaction();
		// } catch (Exception exception) {
		// dbManager.rollbackTransaction();
		// throw exception;
		// } finally {
		// dbManager.close();
		// }
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(
			UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public PrpCitemCarExtService getPrpCitemCarExtService() {
		return prpCitemCarExtService;
	}

	public void setPrpCitemCarExtService(PrpCitemCarExtService prpCitemCarExtService) {
		this.prpCitemCarExtService = prpCitemCarExtService;
	}

}
