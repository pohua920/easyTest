package com.sinosoft.prpins.policy.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.common.schema.model.PrpCPmain;
//mantis：EGN0105，處理人員：DP0713，需求單編號：新增批單共保會簽頁及共保收據保費顯示調整(含責任險) 
import com.sinosoft.common.schema.model.PrpCopyCoinsDetail;
import com.sinosoft.common.schema.model.PrpCopyMain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.util.IConstants;
import com.sinosoft.prpins.policy.service.facade.PrpCopyMainService;
import ins.framework.common.Page;
/**
 * 備份主服務實現
 * @author Sinosoft
 */
public class PrpCopyMainServiceSpringImpl extends GenericDaoHibernate<PrpCopyMain, String> implements PrpCopyMainService {
	
	/**
	 * 查詢備份表數據
	 * @param applyNo 申請號
	 * @return PrpCPmain PrpCPmain對象
	 */
	public PrpCPmain getPrpCopyMainByApplyNo1(String endorseNo) {
		PrpCPmain prpCopyMain = super.get(PrpCPmain.class, endorseNo);
		return prpCopyMain;
	}
	/**
	 * 查詢備份表數據
	 * @param applyNo 申請號
	 * @return PrpCPmain PrpCPmain對象
	 */
	public PrpCopyMain getPrpCopyMainByApplyNo(String endorseNo) {
		PrpCopyMain prpCopyMain = super.get(PrpCopyMain.class, endorseNo);
		return prpCopyMain;
	}
	/**
	 * 刪除PrpCopyMain信息
	 * @param applyNo 批單申請號
	 */
	public void deletePrpCopyMainByApplyNo(String applyNo) {
		if (applyNo != null && !"".equals(applyNo)) {
			super.deleteByPK(PrpCopyMain.class, applyNo);
		}
	}
	/**
	 * 查詢PrpCPmain對象集合
	 * @param conditions 條件
	 * @return List PrpCPmain對象集合
	 */
	@SuppressWarnings("unchecked")
	public List<PrpCPmain> getDataByConditions1(String conditions) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a from PrpCPmain a where underWriteFlag in ('1','3')");
		if (conditions != null && !"".equals(conditions)) {
			hql.append(" and ").append(conditions);
		}
		List<PrpCPmain> prpCopyMainList = this.findByHql(hql.toString());
		return prpCopyMainList;
	}
	/**
	 * 查詢PrpCopyMain對象集合
	 * @param conditions 條件
	 * @return List PrpCopyMain對象集合
	 */
	@SuppressWarnings("unchecked")
	public List<PrpCopyMain> getDataByConditions(String conditions) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a from PrpCopyMain a where underWriteFlag in ('1','3')");
		if (conditions != null && !"".equals(conditions)) {
			hql.append(" and ").append(conditions);
		}
		List<PrpCopyMain> prpCopyMainList = this.findByHql(hql.toString());
		return prpCopyMainList;
	}
	/**
	 * 查詢PrpCPmain對象
	 * @param applyNo 申請號
	 * @param policyNo 保單號
	 * @return PrpCPmain PrpCPmain對象
	 */
	@SuppressWarnings("unchecked")
	public PrpCPmain getPreviousPrpCopyMain1(String endorseNo, String policyNo) {
		//modify by wangyayun 
//		String hq="select a from PrpCopyMain a where a.policyNo = ? and a.endorseTimes = (select endorseTimes - 1 from PrpCopyMain where applyNo = ?) ";
		String hql = "select a from PrpCPmain a where a.policyNo = ? and a.endorseTimes=(select endorseTimes from PrpPhead where endorseNo = ?) ";
		List<PrpCPmain> prpCopyMainList = this.findByHql(hql, policyNo, endorseNo);
		PrpCPmain prpCopyMain = null;
		if (prpCopyMainList != null && !prpCopyMainList.isEmpty()) {
			prpCopyMain = prpCopyMainList.get(0);
		}
		return prpCopyMain;
	}
	/**
	 * 查詢PrpCopyMain對象
	 * @param applyNo 申請號
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	@SuppressWarnings("unchecked")
	public PrpCopyMain getPreviousPrpCopyMain(String endorseNo, String policyNo) {
		//modify by wangyayun 
		String hql="select a from PrpCopyMain a where a.policyNo = ? and a.endorseTimes = (select endorseTimes - 1 from PrpCopyMain where applyNo = ?) ";
		List<PrpCopyMain> prpCopyMainList = this.findByHql(hql, policyNo, endorseNo);
		PrpCopyMain prpCopyMain = null;
		if (prpCopyMainList != null && !prpCopyMainList.isEmpty()) {
			prpCopyMain = prpCopyMainList.get(0);
		}
		return prpCopyMain;
	}
	/**
	 * 查詢PrpCopyMain對象,查詢原始保單數據
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getFirstPrpCopyMain(String policyNo) {
		String hql = "select a from PrpCopyMain a where a.policyNo =? and a.endorseTimes ='0') ";
		List<PrpCopyMain> prpCopyMainList = this.findByHql(hql, policyNo);
		PrpCopyMain prpCopyMain = null;
		if (prpCopyMainList != null && !prpCopyMainList.isEmpty()) {
			prpCopyMain = prpCopyMainList.get(0);
		}
		return prpCopyMain;
	}
	/**
	 * 查詢PrpCopyMain對象集合
	 * @param policyNo 保單號
	 * @return List PrpCopyMain對象集合
	 */
	public List<PrpCopyMain> getPrpCopyMainByPolicyNo(String policyNo) {
		// mantis：FIR0542，處理人員：DP0714，住火_批單列印要被保險人取資料邏輯調整
		String hql = "select a from PrpCopyMain a where a.policyNo = ? order by a.endorseNo";
		List<PrpCopyMain> prpCopyMainList = this.findByHql(hql, policyNo);
		return prpCopyMainList;
	}
	
	/**
	 * 查詢同一批改类型的PrpCopyMain對象集合
	 * @param policyNo 保單號
	 * @return List PrpCopyMain對象集合
	 */
	public List<PrpCopyMain> getGPrpCopyMainByPolicyNo(String policyNo,String endorType) {
		String hql = "select a from PrpCopyMain a where a.policyNo = ? and endorseNo in " +
				"(select endorseNo from PrpPhead where policyNo = ? and endorType=? and underWriteFlag='1') and endorsetimes is not null order by endorsetimes desc";
		List<PrpCopyMain> prpCopyMainList = this.findByHql(hql, policyNo,policyNo,endorType);
		return prpCopyMainList;
	}
	
	/**
	 * 查詢PrpCopyMain對象
	 * @param certiNo 單證號
	 * @param endorseTimes 批改次數
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoAndEndorseTimes(String policyNo, Integer endorseTimes){
//		PrpCopyMain prpCopyMain = null;
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("policyNo", policyNo);
//		queryRule.addEqual("endorseTimes", endorseTimes);
//		List<PrpCopyMain> prpCopyMainList = this.find(queryRule);
//		if(prpCopyMainList!=null&&prpCopyMainList.size()>0){
//			prpCopyMain = prpCopyMainList.get(0);
//		}
//		return prpCopyMain;
		String hql = "";
		PrpCopyMain prpCopyMain = null;
		if (policyNo != null && endorseTimes >= 0) {
			hql = "from PrpCopyMain a where a.policyNo =? and a.endorseTimes = ? ";
			List<PrpCopyMain> prpCopyMains = this.findTopByHql(hql, 1, policyNo, endorseTimes);
			if (prpCopyMains.size() > 0) {
				prpCopyMain = prpCopyMains.get(0);
			}
		}
		return prpCopyMain;
	}
	/**
	 * 查詢PrpCopyMain對象
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoForCopyMain(String policyNo) {
		String hql = "";
		PrpCopyMain prpCopyMain = null;
		if (policyNo != null && !"".equals(policyNo)) {
//			hql = "select a from PrpCopyMain a where a.policyNo ='"
//					+ policyNo.trim() + "'";
			hql = "select a from PrpCopyMain a where a.policyNo =?";
		}
		List<PrpCopyMain> prpCopyMains = this.findByHql(hql,policyNo.trim());
		//List<PrpCopyMain> prpCopyMains = this.findByHql(hql);
		if (prpCopyMains.size() > 0) {
			prpCopyMain = prpCopyMains.get(0);
		}
		// this.getSession().evict(prpCmain);
		return prpCopyMain;
	}
	/**
	 * 查詢PrpCopyMain對象
	 * @param applyNo 申請號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoForCopyMainP(String applyNo){
		String hql = "";
		PrpCopyMain prpCopyMain = null;
		if (applyNo != null && !"".equals(applyNo)) {
			hql = "select a from PrpCopyMain a where a.applyNo = ? ";
		}
		List<PrpCopyMain> prpCopyMains = this.findByHql(hql,applyNo.trim());
		if (prpCopyMains.size() > 0) {
			prpCopyMain = prpCopyMains.get(0);
		}
		// this.getSession().evict(prpCmain);
		return prpCopyMain;

	}
	/**
	 * 更新PrpCopyMain對象
	 * @param prpCopyMain PrpCopyMain對象
	 */
	 public void updatePrpCopyMain(PrpCopyMain prpCopyMain) {
		 super.getHibernateTemplate().merge(prpCopyMain);
	 }
	 /**
	  * 查詢最大序列號
	  * @param applyNo 申請號
	  * @return Integer 最大序列號
	  * @throws Exception
	  */
	public Integer getMaxEngageSerial(String applyNo) throws Exception {
		Integer serialNo = 0;
		String hql = "select max(a.id.serialNo) from PrpCopyEngage a where a.id.applyNo =?";
		List<Integer> resultList = super.findByHql(hql, applyNo);
		if(resultList.get(0)!=null){
			serialNo = resultList.get(0);
		}
		return serialNo;
	}
	//add by bh054 mantis5719:新增查詢保單軌跡 20171106 start 
		/**
		 * 查詢PrpCopyMain對象集合(原始保單列印)
		 * @param policyNo 保單號
		 * @param endorseTimes 屬性批改次數
		 * @return List PrpCopyMain對象集合
		 */
		public Page getPrpCopyMainByPolicyNoAndEndorseTimes(String policyNo,String policyNoSign,String userCode,String riskcode, int ipageNo, int ipageSize) {
			// 获取QueryRule对象的Instance
			QueryRule queryRule = QueryRule.getInstance();
			Page page=null;
			int pageNo = ipageNo;
			int pageSize = ipageSize;

			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = 10;
			}
			StringBuilder sqlLike = new StringBuilder();
			if (StringUtils.isNotBlank(policyNo)) {
				if(policyNoSign.equals("*")){
					if(sqlLike.toString().length()>0){
						sqlLike.append(" and this_.policyNo like ");
						sqlLike.append("'"+policyNo.trim()+"%'");
					}else{
						sqlLike.append("this_.policyNo like ");
						sqlLike.append("'"+policyNo.trim()+"%'");
					}
				}else{
					queryRule.addEqual("policyNo", policyNo.trim());
				}			
			}
			if(StringUtils.isNotBlank(sqlLike.toString())){
				queryRule.addSql(sqlLike.toString());
			}
			queryRule.addDescOrder("signDate");
			queryRule.addEqual("riskCode", "MC");
			page = this.find(PrpCopyMain.class, queryRule, pageNo, pageSize);				
			
			//String hql = "select a from PrpCopyMain a where a.policyNo = ?  a.endorsetimes=?";
			//List<PrpCopyMain> prpCopyMainList = this.findByHql(hql, policyNo,endorseTimes);
			return page;
		}
		//add by bh054 mantis5719:新增查詢保單軌跡 20171106 end

		/**
		 * mantis：EGN0105，處理人員：DP0713，需求單編號：新增批單共保會簽頁及共保收據保費顯示調整(含責任險)
		 * 查詢PrpCopyCoinsDetail對象
		 */
		public List<PrpCopyCoinsDetail> getDataByPolicyNoAndEndorseNoForCopyCoinsDetail(String policyNo,String endorseNo){
			String hql = "";
			if (policyNo != null && !"".equals(policyNo) &&
					endorseNo != null && !"".equals(endorseNo)) {
				hql = "select a from PrpCopyCoinsDetail a where a.policyNo = ? and a.id.endorseNo = ? ";
				return this.findByHql(hql,policyNo.trim(),endorseNo.trim());
			}
			return null;
		}
}
