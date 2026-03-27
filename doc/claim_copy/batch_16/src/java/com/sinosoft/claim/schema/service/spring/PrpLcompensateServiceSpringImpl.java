package com.sinosoft.claim.schema.service.spring;

/**
 * 赔款计算书信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.sysframework.common.util.DataUtils;

public class PrpLcompensateServiceSpringImpl extends GenericDaoHibernate<PrpLcompensate, String> implements PrpLcompensateService {

	@Override
	public void save(PrpLcompensate prpLcompensate) throws Exception {
		logger.info("保存赔款计算书信息");
		super.save(prpLcompensate);
	}

	@Override
	public void save(List<PrpLcompensate> list) throws Exception {
		logger.info("保存赔款计算书信息");
		super.saveAll(list);
	}

	@Override
	public void delete(String compensateNo) throws Exception {
		logger.info("删除赔款计算书信息编号为" + compensateNo + "的赔款计算书信息");
		super.deleteByPK(PrpLcompensate.class, compensateNo);
	}

	@Override
	public PrpLcompensate findPrpLcompensate(String compensateNo) throws Exception {
		logger.info("查询赔款计算书信息编号为" + compensateNo + "的赔款计算书信息");
		return super.get(PrpLcompensate.class, compensateNo);
	}

	@Override
	public Page findPrpLcompensate(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取赔款计算书信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<PrpLcompensate> findPrpLcompensate(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param claimNo
	 * @return
	 * @throws Exception 根立案号查询计算书
	 */
	public List<PrpLcompensate> findByClaimNo(String claimNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo);
		return super.find(queryRule);
	}

	@Override
	public long getCount(String conditions) {
		String sql = "SELECT count(*) FROM PrpLcompensate WHERE " + conditions;
		return HibernateUtils.getCountbyCountSql(super.getSession(), sql);
	}

	/**
	 * 复核实赔
	 */
	@Override
	public void approve(String compensateNo, String userCode, String underWriteFlag) throws Exception {
		String statement = " Update PrpLcompensate  set ApproverCode = '" + userCode + "',UnderWriteFlag = '" + underWriteFlag + "' where compensateno = '" + compensateNo + "'";
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public List<PrpLcompensate> findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = "Select DISTINCT a.ClaimNo," + "a.PolicyNo, a.CompensateNo, a.SumPaid, a.UnderWriteFlag, b.Status, b.RiskCode From " + "(select * from PrpLClaimStatus where NodeType='compe') b LEFT JOIN PrpLcompensate a "
				+ "ON a.CompensateNo = b.BusinessNo  where" + conditions;
		List<?> tempList = HibernateUtils.findbySql(super.getSession(), statement, pageNo, pageSize);
		List<PrpLcompensate> list = new ArrayList<PrpLcompensate>();
		if (tempList != null && !tempList.isEmpty()) {
			PrpLcompensate prpLcompensate = null;
			Object[] object = null;
			for (Iterator<?> it = tempList.iterator(); it.hasNext(); list.add(prpLcompensate)) {
				object = (Object[]) it.next();
				prpLcompensate = new PrpLcompensate();
				prpLcompensate.setClaimNo((String) object[0]);
				prpLcompensate.setPolicyNo((String) object[1]);
				prpLcompensate.setCompensateNo((String) object[2]);
				if(object[3]!=null){
					prpLcompensate.setSumPaid(((Number)object[3]).doubleValue());
				}
				prpLcompensate.setUnderWriteFlag((String) object[4]);
				prpLcompensate.setStatus((String) object[5]);
				prpLcompensate.setRiskCode((String) object[6]);
			}
		}
		return list;
	}

	@Override
	public String getClaimNoConditions(String conditions) {
		String claimNoList = "";
		String statement = "SELECT  ClaimNo from PrpLclaim , PrpLregist where PrpLregist.RegistNo=PrpLclaim.RegistNo " + conditions;
		List<?> resultSet = HibernateUtils.findbySql(super.getSession(), statement);
		for (Iterator<?> it = resultSet.iterator(); it.hasNext(); claimNoList += ",'" + it.next() + "'")
			;
		if (!"".equals(claimNoList)) {
			claimNoList = claimNoList.substring(1);
		}
		return claimNoList;
	}

	@Override
	public void saveOrUpdate(PrpLcompensate prpLcompensate) throws Exception {
		Session session = super.getSession();
		session.merge(prpLcompensate);
		//session.save(session.merge(prpLcompensate));
	}
	
	public void saveOrUpdate(List<PrpLcompensate> prpLcompensateList) throws Exception {
		for (int i = 0; i < prpLcompensateList.size(); i++) {
			PrpLcompensate prpLcompensate = prpLcompensateList.get(i);
			saveOrUpdate(prpLcompensate);
		}
	}
	
	public Page findByConditions(String conditions,int pageNo,int pageSize)throws Exception{
		String sql = "select * from  PrpLcompensate WHERE "+conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize,PrpLcompensate.class);
	}
	
	public Map<String,Double> getKindSumRealPayMap(String compensateNo) throws Exception{
    	Map<String,Double> kindSumRealPayMap = new HashMap<String,Double>();
    	StringBuffer sb = new StringBuffer();
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay");
    	sb.append(" From(");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplloss Where compensateno =? Group By kindcode");
    	sb.append(" Union ");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplpersonloss Where compensateno = ? Group By kindcode");
    	sb.append(" Union ");
    	sb.append("Select kindcode,Sum(sumrealpay) sumrealpay From prplcharge Where compensateno = ? Group By kindcode");
    	sb.append(")");
    	sb.append(" Group By kindcode");
    	List<?> list = super.getSession().createSQLQuery(sb.toString()).setString(1, compensateNo)
    	.setString(2, compensateNo).setString(3, compensateNo).list();
    	String kindCode;
    	double sumrealpay;
    	for(int i=0;i<list.size();i++){
    		Object[] objs = (Object[])list.get(i);
    		kindCode = (String)objs[0];
    		sumrealpay = ((Number)objs[1]).doubleValue();
    		kindSumRealPayMap.put(kindCode,new Double(sumrealpay));
    	}
    	return kindSumRealPayMap;
    }
	public List<PrpLcompensate> findByConditions(String conditions)throws Exception{
		String sql = "select * from  PrpLcompensate WHERE "+conditions;
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql, PrpLcompensate.class);
		List<PrpLcompensate> prpLcompensateList = new ArrayList<PrpLcompensate>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			PrpLcompensate prpLcompensate = (PrpLcompensate) iterator.next();
			prpLcompensateList.add(prpLcompensate);
		}
		return prpLcompensateList;
	}

	@Override
	public CompensateFeeDto findCompensateFeeByClaimNo(String claimNo) throws Exception {
//    	double sumPaid = 0d;
    	CompensateFeeDto compensateFeeDto = new CompensateFeeDto();
    	String statement = "select sum(sumpaid) sumpaid  from prplcompensate  where  claimno = '"+claimNo+"' and (UnderWriteFlag = 1 or UnderWriteFlag =3)";
//    	List<?> list = HibernateUtils.findbySql(super.getSession(),statement);
    	Number sum = (Number) super.getSession().createSQLQuery(statement).uniqueResult();
    	if(sum!=null){
    		compensateFeeDto.setSumPaid(sum.doubleValue());
		}else{
			compensateFeeDto.setSumPaid(0d);
		}
		return compensateFeeDto;
	}
	/**
	 * 获取保单历史（非本次）赔付
	 */
	@Override
	public List<Map<String,Double>> getPastCasePay(String policyNo, String claimNo) {
		String compensateSql = null;
		if(CommonUtils.isEmpty(claimNo)){
			compensateSql = "select compensateno,claimno from prplcompensate where compensateno like 'C%' and (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3') and policyno = '"+policyNo+"' ";
		}else{
			compensateSql = "select compensateno,claimno from prplcompensate where compensateno like 'C%' and (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3') and policyno = '"+policyNo+"' and claimno !='"+claimNo+"' ";
		}
		StringBuffer statement = new StringBuffer("");
		statement.append(" Select kindcode,Sum(sumrealpay) sumrealpay,count(*) times From (");
		statement.append("Select c1.claimno,t1.kindcode,Sum(t1.sumrealpay) sumrealpay From prplloss t1,("+compensateSql+") c1 ");
		statement.append(" Where t1.compensateno = c1.compensateno group by c1.claimno,t1.kindcode ");
		statement.append(" UNION ALL ");
		statement.append("Select c2.claimno,t2.kindcode,Sum(t2.sumrealpay) sumrealpay From prplpersonloss t2,("+compensateSql+") c2 ");
		statement.append(" Where t2.compensateno = c2.compensateno group by c2.claimno,t2.kindcode ");
		statement.append(") group by kindcode");
		List<Map<String,Double>> list = new ArrayList<Map<String,Double>>(2);
		Map<String,Double> pastKindPayAmount = new HashMap<String,Double>();//历史险别赔付金额
		Map<String,Double> pastKindPayTimes = new HashMap<String,Double>();//历史险别赔付次数
	   	List<?> result = super.getSession().createSQLQuery(statement.toString()).list();
	   	if(result!=null && !result.isEmpty()){
	   		Object[] objs = null;
	   		for(Object temp:result){
	   			objs = (Object[])temp;
	   			pastKindPayAmount.put(String.valueOf(objs[0]),((Number)objs[1]).doubleValue());
	   			pastKindPayTimes.put(String.valueOf(objs[0]),((Number)objs[2]).doubleValue());
	   		}
	   	}
   		list.add(pastKindPayAmount);
   		list.add(pastKindPayTimes);
		return list;
	}

	@Override
	public Map<String, Double> getPastPrpLlossPay(String claimNo) {
		Map<String,Double> pastPay = new HashMap<String,Double>();
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		String statement = "Select kindcode,Sum(sumrealpay) sumrealpay From prplloss Where compensateno in( select compensateno from prplcompensate where (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3') and claimno = ? ) " +
				" AND kindcode NOT IN ('E3','E5','E7') Group By kindcode";
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, claimNo).list();
	   	if(list!=null && !list.isEmpty()){
	   		Object[] objs = null;
	   		for(Object temp:list){
	   			objs = (Object[])temp;
	   			pastPay.put(String.valueOf(objs[0]),((Number)objs[1]).doubleValue());
	   		}
	   	}
		return pastPay;
	}
	
	/**
	 * mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
	 */
	@Override
	public Map<String, Double> getPastPrpLlossPayE(String claimNo) {
		Map<String,Double> pastPay = new HashMap<String,Double>();
		String statement = "Select kindcode,Sum(sumrealpay) sumrealpay From prplloss Where compensateno in( " +
				" select compensateno from prplcompensate where (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3') " +
				" and claimno in( " +
				"		SELECT CLAIMNO FROM PRPLCOMPENSATE WHERE POLICYNO in ( "+
				"				select POLICYNO from "+
				"				prplcompensate where "+
				"				claimno =? "+
				"		) "+
				" 	) " +
				" ) AND kindcode IN ('E3','E5','E7')  Group By kindcode ";
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, claimNo).list();
	   	if(list!=null && !list.isEmpty()){
	   		Object[] objs = null;
	   		for(Object temp:list){
	   			objs = (Object[])temp;
	   			pastPay.put(String.valueOf(objs[0]),((Number)objs[1]).doubleValue());
	   		}
	   	}
		return pastPay;
	}
	/***
	 * 获取本案已赔付人伤(根据受害人身份证号区分每人)
	 */
	@Override
	public Map<String, Double> getPastPrpLpersonLossPay(String claimNo) {
    	Map<String,Double> pastPay = new HashMap<String,Double>();
    	if(DataUtils.emptyToNull(claimNo)!=null){
    		StringBuffer statement = new StringBuffer("");
    		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
    		statement.append(" select t.identifynumber,t.kindcode,sum(t.sumrealpay) sumrealpay ");
    		statement.append(" from  prplpersonloss t where  compensateno in (");
    		statement.append(" select compensateno from prplcompensate where claimno = ? and (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3')");
    		statement.append(" )  AND t.kindcode NOT IN ('E3','E5','E7') group by t.identifynumber,t.kindcode");
    		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
    		List<?> result = super.getSession().createSQLQuery(statement.toString()).setString(0, claimNo).list();
    		Double tempDouble = null;
    		String kindCode = "";
    		double sumrealpay = 0d;
    		String identifynumber = "";
    		if(result!=null && !result.isEmpty()){
		   		Object[] objs = null;
		   		for(Object temp:result){
		   			objs = (Object[])temp;
		   			if(objs[0]!=null){
		   				identifynumber = String.valueOf(objs[0]);
		   				kindCode = String.valueOf(objs[1]);
		   				sumrealpay = ((Number)objs[2]).doubleValue();
		   				tempDouble = pastPay.get(kindCode);
		   				pastPay.put(identifynumber+"_"+kindCode,sumrealpay);
		   				pastPay.put(kindCode,tempDouble==null?sumrealpay:(sumrealpay+tempDouble.doubleValue()));
		   			}
		   		}
    		}
    	}
    	return pastPay;
	}


	/***
	 * mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
	 * 获取本案已赔付人伤(根据受害人身份证号区分每人)
	 */
	@Override
	public Map<String, Double> getPastPrpLpersonLossPayE(String claimNo) {
    	Map<String,Double> pastPay = new HashMap<String,Double>();
    	if(DataUtils.emptyToNull(claimNo)!=null){
    		StringBuffer statement = new StringBuffer("");
    		statement.append(" select t.identifynumber,t.kindcode,sum(t.sumrealpay) sumrealpay ");
    		statement.append(" from  prplpersonloss t where  compensateno in (");
    		statement.append(" select compensateno from prplcompensate where claimno in (" +
    				"						SELECT CLAIMNO FROM PRPLCOMPENSATE WHERE POLICYNO in ( "+
    				"									select POLICYNO from "+
    				"										prplcompensate where "+
    				"										claimno =? "+
    				"						) "+
    				"			) and (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3')");
    		statement.append(" )  AND t.kindcode IN ('E3','E5','E7') group by t.identifynumber,t.kindcode");
    		List<?> result = super.getSession().createSQLQuery(statement.toString()).setString(0, claimNo).list();
    		Double tempDouble = null;
    		String kindCode = "";
    		double sumrealpay = 0d;
    		String identifynumber = "";
    		if(result!=null && !result.isEmpty()){
		   		Object[] objs = null;
		   		for(Object temp:result){
		   			objs = (Object[])temp;
		   			if(objs[0]!=null){
		   				identifynumber = String.valueOf(objs[0]);
		   				kindCode = String.valueOf(objs[1]);
		   				sumrealpay = ((Number)objs[2]).doubleValue();
		   				tempDouble = pastPay.get(kindCode);
		   				pastPay.put(identifynumber+"_"+kindCode,sumrealpay);
		   				pastPay.put(kindCode,tempDouble==null?sumrealpay:(sumrealpay+tempDouble.doubleValue()));
		   			}
		   		}
    		}
    	}
    	return pastPay;
	}
	
	@Override
	public void update(List<PrpLcompensate> prpLcompensateList)
			throws Exception {
		for (Iterator<PrpLcompensate> iterator = prpLcompensateList.iterator(); iterator
				.hasNext();) {
			PrpLcompensate prpLcompensate = (PrpLcompensate) iterator.next();
			update(prpLcompensate);
		}
	}

	@Override
	public PrpLcompensate getReplevyPrpLcompensate(String claimNo) {
		String sql = " claimno = '"+claimNo+"' and compensateno ='R"+claimNo+"00' order by compensateno asc";
		List<PrpLcompensate> list = super.find(QueryRule.getInstance().addSql(sql));
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查询险别的损失
	 * @param compensateNo
	 * @param prpCitemKind
	 * @return
	 */
	public List<Double> findKindSumLoss(String compensateNo, PrpCitemKind prpCitemKind) {
		String sql = " SELECT (NVL((SELECT SUM(PRPLLOSS.sumLoss * PRPLLOSS.EXCHRATE) FROM PRPLLOSS  WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"), 0)+"
				+ " NVL((SELECT SUM(PRPLPERSONLOSS.sumDefPay * PRPLPERSONLOSS.EXCHRATE) FROM PRPLPERSONLOSS" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0)),"
				+ " (NVL((SELECT SUM(PRPLLOSS.SUMREALPAY * PRPLLOSS.EXCHRATE) FROM PRPLLOSS" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0) +"
				+ " NVL((SELECT SUM(PRPLPERSONLOSS.SUMREALPAY * PRPLPERSONLOSS.EXCHRATE) FROM PRPLPERSONLOSS" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0)" 
				+ " ) FROM DUAL";
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql);
		List<Double> sumLoss = new ArrayList<Double>();
		if(list.size()>0){
			Object[] num = (Object[]) list.get(0);
			sumLoss.add(((Number)num[0]).doubleValue());
			sumLoss.add(((Number)num[0]).doubleValue()-((Number)num[1]).doubleValue());
			sumLoss.add(((Number)num[1]).doubleValue());
		}
		sumLoss.add(prpCitemKind.getAmount());
		
		sql = " SELECT (NVL((SELECT SUM(PRPLLOSS.SUMREALPAY * PRPLLOSS.EXCHRATE) FROM PRPLLOSS" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0) +"
		+ " NVL((SELECT SUM(PRPLPERSONLOSS.SUMREALPAY * PRPLPERSONLOSS.EXCHRATE) FROM PRPLPERSONLOSS" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0))," 
		+ " NVL((SELECT SUM(PRPLCHARGE.chargeAmount * PRPLCHARGE.EXCHRATE)  FROM PRPLCHARGE" + " WHERE COMPENSATENO = '" + compensateNo + "' AND ITEMKINDNO= "+prpCitemKind.getId().getItemKindNo()+"),0) FROM DUAL";
		list = HibernateUtils.findbySql(super.getSession(), sql);
		if(list.size()>0){
			Object[] num = (Object[]) list.get(0);
			sumLoss.add(((Number)num[0]).doubleValue()+((Number)num[1]).doubleValue());
			sumLoss.add(((Number)num[1]).doubleValue());
		}
		return sumLoss;
	}
	
	/**
	 * 查詢核賠人員審核當月累積已核賠費用
	 * mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控
	 * @param uniformno
	 * @return
	 */
	@Override
	public Double getSumPayAmountThisMonth(String uniformno) {
		Double sumPayAmoun = 0d;
		String statement = "SELECT SUM(PAY.PAYAMOUNT)"+
				" FROM PRPLCOMPENSATE COM"+
				" INNER JOIN PRPLCHARGE FEE ON COM.COMPENSATENO = FEE.COMPENSATENO"+
				" INNER JOIN PRPLPAYOBJECTINFO PAY ON PAY.COMPENSATENO = FEE.COMPENSATENO AND PAY.KINDCODE IS NOT NULL AND FEE.SERIALNO = PAY.SERIALNO"+
				" WHERE COM.RISKCODE = 'A01' "+
				" AND COM.UNDERWRITEFLAG = '1'" +
				" AND CHARGECODE IN ('T','U','V') "+
				" AND TO_CHAR(UNDERWRITEENDDATE ,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM')"+
				" AND UNIFORMNO = '"+uniformno+"' GROUP BY PAY.UNIFORMNO";
	   	List<?> list = super.getSession().createSQLQuery(statement).list();
	   	if(list!=null && !list.isEmpty()){
	   		BigDecimal obj = null;
	   		for(Object temp:list){
	   			obj = (BigDecimal)temp;
	   			if(obj != null){
	   				sumPayAmoun = (obj).doubleValue();
	   			}
	   		}
	   	}
		return sumPayAmoun;
	}
}
