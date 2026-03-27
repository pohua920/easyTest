package com.sinosoft.claim.schema.service.spring;

/**
 * 保单基本信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.service.facade.PrpCopyMainService;

public class PrpCopyMainServiceSpringImpl extends GenericDaoHibernate<PrpCopyMain, String> implements PrpCopyMainService {

	/** 代码翻译Service */
	private CodeService codeService;

	@Override
	public void save(PrpCopyMain prpCopyMain) throws Exception {
		logger.info("保存保单基本信息");
		super.save(prpCopyMain);

	}

	@Override
	public void save(List<PrpCopyMain> list) throws Exception {
		logger.info("保存保单基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpCopyMain prpCopyMain) throws Exception {
		logger.info("保存保单基本信息");
		super.getSession().merge(prpCopyMain);

	}

	@Override
	public void saveOrUpdate(List<PrpCopyMain> list) throws Exception {
		logger.info("保存保单基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void delete(String endorseNo) throws Exception {
		logger.info("删除保单基本信息编号为" + endorseNo + "的保单基本信息");
		super.deleteByPK(PrpCopyMain.class, endorseNo);
	}

	@Override
	public PrpCopyMain findPrpCopyMain(String endorseNo) throws Exception {
		logger.info("查询保单基本信息编号为" + endorseNo + "的保单基本信息");
		return super.get(PrpCopyMain.class, endorseNo);
	}

	@Override
	public Page findPrpCopyMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保单基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<PrpCopyMain> findPrpCopyMain(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 根据sql语句查询page信息
	 */
	@Override
	public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String hql = "select * from PrpCopyMain where " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), hql, pageNo, pageSize, PrpCopyMain.class);
		return page;
	}

	/**
	 * 获得保单
	 * @param endorseNo 保单号
	 * @return 保单对象
	 * @throws Exception
	 */
	@Override
	public PrpCopyMain findPrpCopyMainByPrimaryKey(String endorseNo) throws Exception {
		PrpCopyMain prpCopyMain = findPrpCopyMain(endorseNo);
		if(prpCopyMain!=null) {
			String handler1Name = codeService.translateUserCode(prpCopyMain.getHandler1Code(),true);
			prpCopyMain.setHandler1Name(handler1Name);
			prpCopyMain.setZ2_handlerCode(prpCopyMain.getHandler1Code());
			prpCopyMain.setZ2_handlerName(handler1Name);
		}
		return prpCopyMain;
	}

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据(非车报案环节支持模糊查询)
	 * @param conditions String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	@Override
	public List<PrpCopyMain> findForRegistConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
//		QueryRule queryRule = QueryRule.getInstance();
		String sql ="select * from prpCopyMain where "+conditions;
//		queryRule.addSql(sql);
//		queryRule.addSql(conditions);
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql, PrpCopyMain.class);
		List<PrpCopyMain> prpCopyMainList = new ArrayList<PrpCopyMain>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			PrpCopyMain prpCopyMain = (PrpCopyMain) iterator.next();
			prpCopyMainList.add(prpCopyMain);
		}
		return prpCopyMainList;
//		return this.findBySql(sql);
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getCount1(String conditions) throws Exception {
		int count = -1;
		String statement = "Select count(*) from PrpCopyMain Where " + conditions;
		logger.info(statement);
		count = (int) HibernateUtils.getCountbyCountSql(getSession(), statement);
		logger.info("PrpCopyMainServiceBase.getCount1() success!");
		return count;
	}

	/**
	 * 二期查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getCount2(String conditions) throws Exception {
		int count = -1;
		String statement = "Select count(*) from PrpCopyMain LEFT JOIN PrpCitemcar b ON PrpCopyMain.PolicyNo = b.PolicyNo Where " + conditions;
		logger.info(statement);
		count = (int) HibernateUtils.getCountbyCountSql(getSession(), statement);
		logger.info("PrpCopyMainServiceBase.getCount1() success!");
		return count;
	}

	/**
	 * 更新一条数据(让表prpCopyMain中的字段claimstatus加1)
	 * @param
	 * @throws Exception
	 */
	@Override
	public void updateClaimTimesAdd1(String endorseNo) throws Exception {
		String statement = " update prpCopyMain set claimtimes=claimtimes+1" + " Where " + " PolicyNo = '" + endorseNo + "'";
		logger.info(statement);
		HibernateUtils.executeSql(getSession(), statement);
		logger.info("PrpCopyMainService.update() success!");
	}

	/**
	 * 更新一条数据(让表prpCopyMain中的字段claimstatus减1) 对於注销和拒赔案件，出现次数要减1 start
	 * @param
	 * @throws Exception
	 */
	@Override
	public void updateClaimTimesMinus1(String endorseNo) throws Exception {
		String statement = " update prpCopyMain set claimtimes=claimtimes-1" + " Where " + " PolicyNo = '" + endorseNo + "'";
		logger.info(statement);
		HibernateUtils.executeSql(getSession(), statement);
		logger.info("PrpCopyMainService.update() success!");
	}

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param pageSize int
	 * @throws Exception
	 * @return List
	 */
	@Override
	public List<PrpCopyMain> findForRegistConditions(String conditions, String operatDate, int pageNo, int pageSize) throws Exception {
		String policyNOConditions = this.getPolicyNoConditions(conditions);
		String policyPrpCopyMain = "";
		if (!" and 1=1 ".equals(policyNOConditions) && !" and 1=0 ".equals(policyNOConditions)) {
			policyPrpCopyMain = "prpCopyMain." + policyNOConditions.substring(5, policyNOConditions.length());
		} else {
			policyPrpCopyMain = policyNOConditions.substring(5, policyNOConditions.length());
		}
		// 当前保费实收，查询字段为 prpjplanfee表字段RealPayRefFee
		String statement = "SELECT D.*,T.PLANFEE2 " + "FROM (SELECT M.policyno,M.licenseno,M.insuredname,M.StartDate," + "M.EndDate,M.BrandName,M.OthFlag,M.RiskCode,M.ClassCode,M.CarKindCode,"
				+ "M.ComCode,M.FrameNo,M.VINNO,M.CarOwner,M.EngineNo,M.LicenseColorCode," + "M.ModelCode,M.PLANFEE1,M.PAYREFFEE1,N.PAYREFFEE2  " + "FROM (select x.policyno,x.licenseno,x.insuredname,x.StartDate,x.EndDate,"
				+ "x.BrandName,x.OthFlag,x.RiskCode,x.ClassCode,x.CarKindCode,x.ComCode," + "x.FrameNo,x.VINNO,x.CarOwner,x.EngineNo,x.LicenseColorCode,x.ModelCode,Y.PLANFEE1," + "y.PAYREFFEE1 "
				+ "from (Select prpCopyMain.PolicyNo,b.LicenseNo,prpCopyMain.InsuredName,prpCopyMain.StartDate,prpCopyMain.EndDate," + "b.BrandName,prpCopyMain.OthFlag,prpCopyMain.RiskCode,prpCopyMain.ClassCode,b.CarKindCode,prpCopyMain.ComCode,b.FrameNo,"
				+ "b.VINNO,b.CarOwner,b.EngineNo,b.LicenseColorCode,b.ModelCode   " + " From PrpCopyMain prpCopyMain   " + "LEFT JOIN PrpCitemcar b " + "ON prpCopyMain.PolicyNo = b.PolicyNo   " + "WHERE  "
				+ policyPrpCopyMain
				+ " and "
				+ conditions
				+ "   ) x  "
				+ "LEFT JOIN (SELECT PPF.POLICYNO,NVL(SUM(PPF.PLANFEE), 0) PLANFEE1,"
				+ "NVL(SUM(PPF.RealPayRefFee), 0) PAYREFFEE1 "
				+ " FROM PRPJPLANFEE PPF "
				+ "WHERE PPF.CERTITYPE IN ('P', 'E') "
				+ policyNOConditions
				+ "GROUP BY PPF.POLICYNO) y ON X.POLICYNO = Y.POLICYNO ) M  "
				+ "LEFT JOIN (select prf.policyno, NVL(SUM(prf.payreffee), 0) PAYREFFEE2 FROM PRPJPAYREFREC prf "
				+ "WHERE prf.Certitype IN ('P', 'E') AND PRF.OPERATEDATE < to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') "
				+ policyNOConditions
				+ "GROUP BY prf.Policyno) N ON M.POLICYNO = N.POLICYNO ) D  "
				+ "LEFT JOIN (SELECT PF.POLICYNO, NVL(SUM(PF.PLANFEE), 0) PLANFEE2 FROM PRPJPLANFEE PF "
				+ "WHERE PF.CERTITYPE IN ('P', 'E') AND PF.UNDERWRITEDATE < to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') "
				+ policyNOConditions
				+ "GROUP BY PF.POLICYNO) T " + "ON D.POLICYNO = T.POLICYNO";

		logger.info(statement);
		List<PrpCopyMain> resultList = new ArrayList<PrpCopyMain>();
		List<?> tempList = HibernateUtils.findbySql(getSession(), statement, pageNo, pageSize);
		resultList = this.getResutlList(tempList);
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		return resultList;
	}

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据（接口二期取得包括出险时和当前收费状态信息保单信息列表）
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param pageSize int
	 * @throws Exception
	 * @return List
	 */
	@Override
	public List<PrpCopyMain> findForCCRegistConditions(String conditions, String operatDate, int pageNo, int pageSize) throws Exception {

		String policyNOConditions = this.getPolicyNoConditions(conditions);
		String policyPrpCopyMain = "";
		if (!" and 1=1 ".equals(policyNOConditions) && !" and 1=0 ".equals(policyNOConditions)) {
			policyPrpCopyMain = "prpCopyMain." + policyNOConditions.substring(5, policyNOConditions.length());
		} else {
			policyPrpCopyMain = policyNOConditions.substring(5, policyNOConditions.length());
		}
		// 当前保费实收，查询字段为 prpjplanfee表字段RealPayRefFee
		String statement = "SELECT D.*,T.PLANFEE2 " + "FROM (SELECT M.policyno,M.licenseno,M.insuredname,M.StartDate," + "M.EndDate,M.BrandName,M.OthFlag,M.RiskCode,M.ClassCode,M.CarKindCode,"
				+ "M.ComCode,M.FrameNo,M.VINNO,M.CarOwner,M.EngineNo,M.LicenseColorCode," + "M.ModelCode,M.PLANFEE1,M.PAYREFFEE1,N.PAYREFFEE2  " + "FROM (select x.policyno,x.licenseno,x.insuredname,x.StartDate,x.EndDate,"
				+ "x.BrandName,x.OthFlag,x.RiskCode,x.ClassCode,x.CarKindCode,x.ComCode," + "x.FrameNo,x.VINNO,x.CarOwner,x.EngineNo,x.LicenseColorCode,x.ModelCode,Y.PLANFEE1," + "y.PAYREFFEE1 "
				+ "from (Select prpCopyMain.PolicyNo,b.LicenseNo,prpCopyMain.InsuredName,prpCopyMain.StartDate,prpCopyMain.EndDate," + "b.BrandName,prpCopyMain.OthFlag,prpCopyMain.RiskCode,prpCopyMain.ClassCode,b.CarKindCode,prpCopyMain.ComCode,b.FrameNo,"
				+ "b.VINNO,b.CarOwner,b.EngineNo,b.LicenseColorCode,b.ModelCode   " + " From PrpCopyMain prpCopyMain   " + "LEFT JOIN PrpCitemcar b " + "ON prpCopyMain.PolicyNo = b.PolicyNo   " + "WHERE  "
				+ policyPrpCopyMain
				+ " and "
				+ conditions
				+ "  and prpCopyMain.STARTDATE<= to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') and prpCopyMain.ENDDATE>=to_date('"
				+ operatDate
				+ "','yyyy-mm-dd')  ) x  "
				+ "LEFT JOIN (SELECT PPF.POLICYNO,NVL(SUM(PPF.PLANFEE), 0) PLANFEE1,"
				+ "NVL(SUM(PPF.RealPayRefFee), 0) PAYREFFEE1 "
				+ " FROM PRPJPLANFEE PPF "
				+ "WHERE PPF.CERTITYPE IN ('P', 'E') "
				+ policyNOConditions
				+ "GROUP BY PPF.POLICYNO) y ON X.POLICYNO = Y.POLICYNO ) M  "
				+ "LEFT JOIN (select prf.policyno, NVL(SUM(prf.payreffee), 0) PAYREFFEE2 FROM PRPJPAYREFREC prf "
				+ "WHERE prf.Certitype IN ('P', 'E') AND PRF.OPERATEDATE < to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') "
				+ policyNOConditions
				+ "GROUP BY prf.Policyno) N ON M.POLICYNO = N.POLICYNO ) D  "
				+ "LEFT JOIN (SELECT PF.POLICYNO, NVL(SUM(PF.PLANFEE), 0) PLANFEE2 FROM PRPJPLANFEE PF "
				+ "WHERE PF.CERTITYPE IN ('P', 'E') AND PF.UNDERWRITEDATE < to_date('" + operatDate + "','yyyy-mm-dd') " + policyNOConditions + "GROUP BY PF.POLICYNO) T " + "ON D.POLICYNO = T.POLICYNO";
		logger.info(statement);
		List<PrpCopyMain> resultList = new ArrayList<PrpCopyMain>();
		List<?> tempList = HibernateUtils.findbySql(getSession(), statement, pageNo, pageSize);
		resultList = this.getResutlList(tempList);
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		return resultList;
	}

	@Override
	public PrpCopyMain findForRegistConditions(String conditions, String operatDate) throws Exception {

		String policyNOConditions = this.getPolicyNoConditions(conditions);
		String policyPrpCopyMain = "";
		if (!" and 1=1 ".equals(policyNOConditions) && !" and 1=0 ".equals(policyNOConditions)) {
			policyPrpCopyMain = "prpCopyMain." + policyNOConditions.substring(5, policyNOConditions.length());
		} else {
			policyPrpCopyMain = policyNOConditions.substring(5, policyNOConditions.length());
		}
		String statement = "SELECT D.*,T.PLANFEE2 " + "FROM (SELECT M.policyno,M.licenseno,M.insuredname,M.StartDate," + "M.EndDate,M.BrandName,M.OthFlag,M.RiskCode,M.ClassCode,M.CarKindCode,"
				+ "M.ComCode,M.FrameNo,M.VINNO,M.CarOwner,M.EngineNo,M.LicenseColorCode," + "M.ModelCode,M.PLANFEE1,M.PAYREFFEE1,N.PAYREFFEE2  " + "FROM (select x.policyno,x.licenseno,x.insuredname,x.StartDate,x.EndDate,"
				+ "x.BrandName,x.OthFlag,x.RiskCode,x.ClassCode,x.CarKindCode,x.ComCode," + "x.FrameNo,x.VINNO,x.CarOwner,x.EngineNo,x.LicenseColorCode,x.ModelCode,Y.PLANFEE1," + "y.PAYREFFEE1 "
				+ "from (Select prpCopyMain.PolicyNo,b.LicenseNo,prpCopyMain.InsuredName,prpCopyMain.StartDate,prpCopyMain.EndDate," + "b.BrandName,prpCopyMain.OthFlag,prpCopyMain.RiskCode,prpCopyMain.ClassCode,b.CarKindCode,prpCopyMain.ComCode,b.FrameNo,"
				+ "b.VINNO,b.CarOwner,b.EngineNo,b.LicenseColorCode,b.ModelCode   " + " From PrpCopyMain prpCopyMain   " + "LEFT JOIN PrpCitemcar b " + "ON prpCopyMain.PolicyNo = b.PolicyNo   " + "WHERE  "
				+ policyPrpCopyMain
				+ " AND "
				+ conditions
				+ "  and prpCopyMain.STARTDATE<= to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') and prpCopyMain.ENDDATE>=to_date('"
				+ operatDate
				+ "','yyyy-mm-dd')  ) x  "
				+ "LEFT JOIN (SELECT PPF.POLICYNO,NVL(SUM(PPF.PLANFEE), 0) PLANFEE1,"
				+ "NVL(SUM(PPF.Payreffee), 0) PAYREFFEE1 "
				+ " FROM PRPJPLANFEE PPF "
				+ "WHERE PPF.CERTITYPE IN ('P', 'E') "
				+ policyNOConditions
				+ "GROUP BY PPF.POLICYNO) y ON X.POLICYNO = Y.POLICYNO ) M  "
				+ "LEFT JOIN (select prf.policyno, NVL(SUM(prf.payreffee), 0) PAYREFFEE2 FROM PRPJPAYREFREC prf "
				+ "WHERE prf.Certitype IN ('P', 'E') AND PRF.OPERATEDATE < to_date('"
				+ operatDate
				+ "','yyyy-mm-dd') "
				+ policyNOConditions
				+ "GROUP BY prf.Policyno) N ON M.POLICYNO = N.POLICYNO ) D  "
				+ "LEFT JOIN (SELECT PF.POLICYNO, NVL(SUM(PF.PLANFEE), 0) PLANFEE2 FROM PRPJPLANFEE PF "
				+ "WHERE PF.CERTITYPE IN ('P', 'E') AND PF.UNDERWRITEDATE < to_date('" + operatDate + "','yyyy-mm-dd') " + policyNOConditions + "GROUP BY PF.POLICYNO) T " + "ON D.POLICYNO = T.POLICYNO";
		logger.info(statement);
		PrpCopyMain prpCopyMain = null;
		List<?> tempList = HibernateUtils.findbySql(getSession(), statement, 0, 0);
		List<PrpCopyMain> resultList = this.getResutlList(tempList);
		if (!CommonUtils.isEmpty(resultList)) {
			prpCopyMain = resultList.get(0);
		}
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		return prpCopyMain;
	}

	/**
	 * 根据车架号，车牌号，发动机号查询保单号
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findPolicyNoForRegistConditions(String conditions) throws Exception {
		String statement = "Select PrpCopyMain.PolicyNo " + " From PrpCopyMain , PrpCitemcar b where " + " PrpCopyMain.endorseNo = b.endorseNo" + conditions;
		logger.info(statement);
		List<String> resultList = new ArrayList<String>();
		resultList = (List<String>) HibernateUtils.findbySql(getSession(), statement, 0, 0);
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		return resultList;
	}

	/**
	 * 95519二期组织符合条件的保单插叙信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String getPolicyNoConditions(String conditions) throws Exception {
		String endorseNoList = "";
		String statement = "Select prpCopyMain.PolicyNo  " + " From PrpCopyMain prpCopyMain   " + "LEFT JOIN PrpCitemcar b " + "ON prpCopyMain.PolicyNo = b.PolicyNo   " + "WHERE  " + conditions;
		logger.info(statement);
		List<String> resultList = (List<String>) HibernateUtils.findbySql(getSession(), statement, 0, 0);
		for (String str : resultList) {
			endorseNoList += "'" + str + "',";
		}
		if (endorseNoList.length() > 0) {
			endorseNoList = endorseNoList.substring(0, endorseNoList.length() - 1);
			endorseNoList = " and PolicyNo in (" + endorseNoList + ") ";
		} else {
			endorseNoList = " and 1=0 ";
		}
		return endorseNoList;
	}

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	@Override
	public List<PrpCopyMain> findForRegistConditions(String conditions) throws Exception {

		String statement = "SELECT D.*,'0' AS PLANFEE1, '0' AS  PAYREFFEE1,'0' AS PAYREFFEE2, '0' AS PLANFEE2 " + "FROM (Select prpCopyMain.PolicyNo,b.LicenseNo,prpCopyMain.InsuredName,prpCopyMain.StartDate,prpCopyMain.EndDate,"
				+ "b.BrandName,prpCopyMain.OthFlag,prpCopyMain.RiskCode,prpCopyMain.ClassCode,b.CarKindCode,prpCopyMain.ComCode,b.FrameNo," + "b.VINNO,b.CarOwner,b.EngineNo,b.LicenseColorCode,b.ModelCode   " + " From PrpCopyMain prpCopyMain   "
				+ "LEFT JOIN PrpCitemcar b " + "ON prpCopyMain.PolicyNo = b.PolicyNo   " + "WHERE  " + conditions + "   ) D  ";

		List<PrpCopyMain> resultList = new ArrayList<PrpCopyMain>();
		List<?> tempList = HibernateUtils.findbySql(getSession(), statement, 0, 0);
		resultList = this.getResutlList(tempList);
		logger.info("PrpCopyMainService.findForRegistConditions() success!");
		return resultList;
	}

	/***
	 * 从HibernateUtils 查询结果集中提取PrpCopyMain 信息
	 * @param tempList
	 * @return
	 */
	private List<PrpCopyMain> getResutlList(List<?> tempList) {
		List<PrpCopyMain> resultList = new ArrayList<PrpCopyMain>();
		PrpCopyMain prpCopyMain = null;
		for (int i = 0; i < tempList.size(); i++) {
			prpCopyMain = new PrpCopyMain();
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象 而是一个数组
			prpCopyMain = new PrpCopyMain();
			prpCopyMain.setPolicyNo((String) object[0]);
			prpCopyMain.setLicenseNo((String) object[1]);
			prpCopyMain.setInsuredName((String) object[2]);
			prpCopyMain.setStartDate(new Date(((Timestamp) object[3]).getTime()));
			prpCopyMain.setEndDate(new Date(((Timestamp) object[4]).getTime()));
			prpCopyMain.setBrandName((String) object[5]);
			// 添加保单是否已经注销标志
			prpCopyMain.setOthFlag((String) object[6]);
			prpCopyMain.setRiskCode((String) object[7]);
			prpCopyMain.setClassCode((String) object[8]);
			prpCopyMain.setCarKindCode((String) object[9]);
			prpCopyMain.setComCode((String) object[10]);
			prpCopyMain.setFrameNo((String) object[11]);
			prpCopyMain.setVINNo((String) object[12]);
			prpCopyMain.setCarOwne((String) object[13]);
			prpCopyMain.setEngineNo((String) object[14]);
			prpCopyMain.setLicenseColorCode((String) object[15]);
			// prpCopyMainDto.setLicenseColorName((String) object[0],17));
			prpCopyMain.setModelCode((String) object[16]);
			String z2_curJplanFee = "";
			String z2_regJplanFee = "";
			String numb18 = "";
			String numb19 = "";
			String numb20 = "";
			String numb21 = "";
			if (CommonUtils.isEmpty((String) object[17])) {
				numb18 = "0";
			} else {
				numb18 = (String) object[17];
			}
			if (CommonUtils.isEmpty((String) object[18])) {
				numb19 = "0";
			} else {
				numb19 = (String) object[18];
			}
			if (CommonUtils.isEmpty((String) object[19])) {
				numb20 = "0";
			} else {
				numb20 = (String) object[19];
			}
			if (CommonUtils.isEmpty((String) object[20])) {
				numb21 = "0";
			} else {
				numb21 = (String) object[20];
			}
			if (Double.parseDouble(numb19) <= 0) {
				z2_curJplanFee = "保费未缴";
			} else if (Double.parseDouble(numb18) - Double.parseDouble(numb19) > 0) {
				z2_curJplanFee = "保费未缴清";
			} else {
				z2_curJplanFee = "保费已缴清";
			}
			if (Double.parseDouble(numb20) <= 0) {
				z2_regJplanFee = "保费未缴";
			} else if (Double.parseDouble(numb21) - Double.parseDouble(numb20) > 0) {
				z2_regJplanFee = "保费未缴清";
			} else {
				z2_regJplanFee = "保费已缴清";
			}
			prpCopyMain.setZ2_curJplanFee(z2_curJplanFee);
			prpCopyMain.setZ2_regJplanFee(z2_regJplanFee);
			resultList.add(prpCopyMain);
		}
		return resultList;
	}
	@Override
	public PrpCopyMain findByPrimaryKey(String endorseNo) throws Exception {
		PrpCopyMain prpCopyMain = super.findUnique(QueryRule.getInstance().addEqual("endorseNo", endorseNo));
		return prpCopyMain;
	}
	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	/**
	 * 检查缴费情况
	 * 
	 * @param conditions
	 *            String
	 * @throws Exception
	 * @return Collection
	 */
//	public int checkPay(String conditions) throws Exception {
//		//modify by zhaohui start
//		//reason:修改保费是否缴清的判断条件，取得有效保单和所有有效批单，从prpjplanfee表中判断
////		conditions = "endorseno is null and (" + conditions + ")";
////		String statement = "SELECT sum(planfee),sum(delinquentfee) FROM prpcplan where "
////				+ conditions;
//		//modify by wangliguang begin
//		//reason:收付提供SQL，表示下列收付原因不应该在判断是否缴清保费之列 R72 --代收应缴车船税,R73 --代收补缴车船税,
//		//R74 --代收车船税滞纳金,R00 --小保单挂帳保费
//		//add by zhangruifeng 20081027 增加A02收付原因不参与是否实收的判断
//		conditions = " realpayrefflag = '0' and certitype In('P','E') AND  payrefreason Not In ('R72','R73','R74','R00','A01','A02') AND (" + conditions + ")";
//        String statement = "Select count(*) From prpjpayrefrec Where " + conditions;
//		//modify by wangliguang end
//		System.out.println("进行保单保费收费情况检查的SQL语句是:" + statement);
////		ResultSet resultSet = dbManager.executeQuery(statement);
//		int intRet = 1;
//        int intReturn = -1;
//        intReturn = ((Number)HibernateUtils.getCountbyCountSql(super.getSession(), statement)).intValue();
//		// -1,表示未缴全，1表示全交了
//		// modify by lixiang modify 20050421 start
//		// reason:默认为-1，根本没有交费,不能用0，因为0是初始化的值，以後会有问题的
//        if(intReturn > 0){
//            intRet = -2;
//        }else{
//            intRet = 1;
//        }
//		return intRet;
//	}
	

}
