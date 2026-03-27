package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.common.schema.model.PrpCPengAge;
import com.sinosoft.common.schema.model.PrpCPfee;
import com.sinosoft.common.schema.model.PrpCPitemKind;
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCPplan;
import com.sinosoft.common.schema.model.PrpCfee;
import com.sinosoft.common.schema.model.PrpCitemKind;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpCplan;
import com.sinosoft.common.schema.model.PrpDkind;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpTengage;
import com.sinosoft.common.schema.model.PrpTexpense;
import com.sinosoft.common.schema.model.PrpTfee;
import com.sinosoft.common.schema.model.PrpTitemKind;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.common.schema.model.PrpTplan;
import com.sinosoft.prpall.dto.domain.PrpCPexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCPgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCcoinsDto;
import com.sinosoft.prpall.dto.domain.PrpCexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpLcompensateDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpTcoinsDto;
import com.sinosoft.prpall.dto.domain.PrpTgradeDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPexpense;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCcoins;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCexpense;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpGradeGroupDetail;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLcompensate;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpLprepay;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPcoins;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPheadCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTcoins;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTgrade;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.service.facade.PrpCpMainService;

import com.sinosoft.reins.common.service.facade.PrpDcodeService;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpLDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnitId;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnitId;
import com.sinosoft.reins.common.service.facade.PrpCDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpCDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpCDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpLDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpPDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpPDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpPDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpTDangerCoinsService;
import com.sinosoft.reins.common.service.facade.PrpTDangerPlanService;
import com.sinosoft.reins.common.service.facade.PrpTdangerUnitService;
import com.sinosoft.reins.common.vo.PrpCDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpLDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpPDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpTDangerUnitVO;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.reins.common.service.facade.PrpDriskService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoEnquiryService;
import com.sinosoft.undwrt.common.vo.CommonAmountAndPremiumVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.DangerUnitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.GetItemKindInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.GetPlanCurrencyTypeService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.vo.CommonDangerUnitSerialNoVo;

/**
 * 核保系統查詢實現類.
 */
public class PrpallServiceSpringImpl extends GenericDaoHibernate implements
		PrpallService {

	/** 屬性查詢危險單位信息接口. */
	private GetItemKindInfoService getItemKindInfoService;

	/** 屬性險種代碼接口. */
	private PrpDriskService prpDriskReinsService;

	/** 屬性獲取危險單位主信息接口. */
	private DangerUnitService dangerUnitService;

	/** 屬性交費計劃中的幣種信息接口. */
	private GetPlanCurrencyTypeService getPlanCurrencyTypeService;

	/** 屬性通用代碼類型接口. */
	private PrpDcodeService prpDcodeReinsService;

	/** 屬性保單危險單位共保資訊接口. */
	private PrpCDangerCoinsService prpCDangerCoinsService;

	/** 屬性保單危險單位交費計畫接口. */
	private PrpCDangerPlanService prpCDangerPlanService;

	/** 屬性批單危險單位共保資訊接口. */
	private PrpPDangerCoinsService prpPDangerCoinsService;

	/** 屬性要保書危險單位共保資訊接口. */
	private PrpTDangerCoinsService prpTDangerCoinsService;

	/** 屬性批單危險單位交付計畫接口. */
	private PrpPDangerPlanService prpPDangerPlanService;

	/** 屬性要保書危險單位交費計畫. */
	private PrpTDangerPlanService prpTDangerPlanService;

	/** 屬性保單危險單位臨分接口. */
	private PrpCDangerUnitService prpCDangerUnitService;

	/** 屬性理賠的危險單位劃分接口. */
	private PrpLDangerUnitService prpLDangerUnitService;

	/** 屬性批單的危險單位劃分接口. */
	private PrpPDangerUnitService prpPDangerUnitService;

	/** 屬性投保單的危險單位劃分接口. */
	private PrpTdangerUnitService prpTdangerUnitService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性保單訊息接口. */
	private PrpCpMainService prpCpMainService;
	
	/** 屬性詢價單信息接口. */
	private FeoEnquiryService feoEnquiryService;

	/**
	 * 獲取屬性交費計劃中的幣種信息.
	 * 
	 * @return 屬性交費計劃中的幣種信息的值
	 */
	public GetPlanCurrencyTypeService getGetPlanCurrencyTypeService() {
		return getPlanCurrencyTypeService;
	}

	/**
	 * 設置屬性交費計劃中的幣種信息.
	 * 
	 * @param getPlanCurrencyTypeService
	 *            待設置的交費計劃中的幣種信息的值
	 */
	public void setGetPlanCurrencyTypeService(
			GetPlanCurrencyTypeService getPlanCurrencyTypeService) {
		this.getPlanCurrencyTypeService = getPlanCurrencyTypeService;
	}

	/**
	 * 獲取屬性獲取危險單位主信息接口.
	 * 
	 * @return 屬性獲取危險單位主信息接口的值
	 */
	public DangerUnitService getDangerUnitService() {
		return dangerUnitService;
	}

	/**
	 * 設置屬性獲取危險單位主信息接口.
	 * 
	 * @param dangerUnitService
	 *            待設置的獲取危險單位主信息接口的值
	 */
	public void setDangerUnitService(DangerUnitService dangerUnitService) {
		this.dangerUnitService = dangerUnitService;
	}
	@Override
	public void delete(Object obj) {
		super.delete(obj);
	}

	/**
	 * 獲取危險單位序號.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 危險單位序號
	 * @throws Exception
	 *             異常
	 */
	@Override
	public Collection getDangerUnitSerialNoInfo(String businessNo,
			String businessType) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statementStr = null;
		CommonDangerUnitSerialNoVo commonDangerUnitSerialNoDto = null;
		if (businessType.equals("T")) {
			statementStr = "select proposalno, dangerno "
					+ "from prptdangerunit where proposalno='" + businessNo
					+ "'";
		} else if (businessType.equals("P")) {
			statementStr = "select policyno, dangerno "
					+ "from prpcdangerunit where policyno='" + businessNo + "'";
		} else if (businessType.equals("E")) {
			statementStr = "select endorseNo, dangerNo "
					+ "from prppdangerUnit where endorseNo ='" + businessNo
					+ "'";
		}
		List list = super.getSession().createSQLQuery(statementStr).list();
		Iterator it = list.iterator();
		Collection collection = new ArrayList();
		while (it.hasNext()) {

			try {
				Object[] obj = (Object[]) it.next();
				commonDangerUnitSerialNoDto = new CommonDangerUnitSerialNoVo();
				commonDangerUnitSerialNoDto.setBusinessNo((String) obj[0]);
				commonDangerUnitSerialNoDto.setDangerNo(String.valueOf(obj[1]));
				collection.add(commonDangerUnitSerialNoDto);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(
						internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
			}
		}
		return collection;
	}

	/**
	 * 根據不同業務號獲取要保書訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書訊息
	 * @throws Exception
	 *             異常
	 */
	@Override
	public PrpTmain getPrpTmain(String businessNo, String businessType)
			throws Exception {
		PrpTmain prpTmain = null;
		try {
			if (businessType.equals("T")) {
				prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			}
			if (businessType.equals("P")) {
				String proposalno = policyService.getPrpCmainByPolicyNo(
						businessNo).getProposalNo();
				prpTmain = policyService.getPrpTmainByProposalNo(proposalno);
			}
			if (businessType.equals("E")) {
				String proposalno = endorseService
						.getPrpPheadByEndorseNo(businessNo).getPrpPmains()
						.get(0).getProposalNo();
				prpTmain = policyService.getPrpTmainByProposalNo(proposalno);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpTmain;
	}

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	@Override
	public PrpTgradeDto getPrpTgrade(String businessNo, String businessType)
			throws Exception {
		PrpTgradeDto prpTgradeDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			if (businessType.equals("T")) {
				prpTgradeDto = new DBPrpTgrade(dbManager)
						.findByPrimaryKey(businessNo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpTgradeDto;
	}

	/**
	 * 根據業務號獲取定級業務的關聯業務.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級業務的關聯業務訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpGradeGroupDetailByBusinessNo(String businessNo)
			throws Exception {
		Collection collection = new ArrayList();
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			String sql = " businessNo='" + businessNo + "'";
			collection = (Collection) new DBPrpGradeGroupDetail(dbManager)
					.findByConditions(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return collection;
	}

	/**
	 * 根據不同業務號獲取要保書訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書訊息
	 * @throws Exception
	 *             異常
	 */
	public PrpTexpense getPrpTexpense(String businessNo, String businessType)
			throws Exception {
		PrpTexpense prpTexpense = null;
		try {
			if (businessType.equals("T")) {
				String str = "select * from PrpTexpense where ProposalNo='"
						+ businessNo + "'";
				List list = super.getSession().createSQLQuery(str)
						.addEntity(PrpTexpense.class).list();
				if (list != null && list.size() > 0) {
					prpTexpense = (PrpTexpense) list.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpTexpense;
	}

	/**
	 * 獲取保單保險證明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單保險證明
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpCmainCovernote(java.lang.String)
	 */
	public PrpCmainCovernoteDto getPrpCmainCovernote(String businessNo)
			throws Exception {
		PrpCmainCovernoteDto prpCmainCovernoteDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(
					dbManager);
			prpCmainCovernoteDto = dbPrpCmainCovernote
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCmainCovernoteDto;
	}

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCgradeDto getPrpCgrade(String businessNo) throws Exception {
		PrpCgradeDto prpCgradeDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			prpCgradeDto = new DBPrpCgrade(dbManager)
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCgradeDto;
	}

	/**
	 * 查找保單主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCmain getPrpCmain(String businessNo) throws Exception {
		PrpCmain prpCmain = null;
		try {
			prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpCmain;
	}
	
	public void updateTmain(PrpTmain prpTmain)
	{
		this.update(prpTmain);
	}

	public void updateCmain(PrpCmain prpCmain)
	{
		this.update(prpCmain);
	}
	
	public void updateQmain(PrpQmain prpQmain)
	{
		this.update(prpQmain);
	}
	public void RecoveryStatus(String policyNo)
	{
		this.getSession().createSQLQuery("delete  FROM PRPCOPYMAIN WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYITEMKIND  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYPLAN WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYFEE WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYITEMCAREXT WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYCOMMISSION  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYCOMMISSIONDETAIL WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYITEMCAR WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYINSURED WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYINSUREDARTIF WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYINSUREDNATURE WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYLIMIT WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYMAINSUB  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYCARDEVICE  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCOPYCARDRIVER  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		//以下為刪除收付和介接數據  modefied by zhangruofei 20141201
		this.getSession().createSQLQuery("delete  FROM INTFPRPJPAYREFKIND  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM INTFPRPJPAYREFREC  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCJPLAN  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCJPLANKIND  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJFTIME  WHERE POLICYNO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPAYREFKIND  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPAYREFREC  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPLANFEE  WHERE CERTINO = '"+ policyNo+"'").executeUpdate();
	};
	public void RecoveryStatusQta(String businessNo)
	{
		this.getSession().createSQLQuery("delete  FROM INTFPRPJPAYREFKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM INTFPRPJPAYREFREC  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCJPLAN  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPCJPLANKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJFTIME  WHERE POLICYNO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPAYREFKIND  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPAYREFREC  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
		this.getSession().createSQLQuery("delete  FROM PRPJPLANFEE  WHERE CERTINO = '"+ businessNo+"'").executeUpdate();
	};
	/**
	 * 根據不同業務號獲取保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 保單信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCexpenseDto getPrpCexpense(String businessNo, String businessType)
			throws Exception {
		PrpCexpenseDto prpCexpenseDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			if (businessType.equals("P")) {
				prpCexpenseDto = new DBPrpCexpense(dbManager)
						.findByPrimaryKey(businessNo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCexpenseDto;
	}

	/**
	 * 預約保險批單.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險批單訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpPmainCovernote(java.lang.String)
	 */
	public PrpPmainCovernoteDto getPrpPmainCovernote(String businessNo)
			throws Exception {
		PrpPmainCovernoteDto prpPmainCovernoteDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			DBPrpPmainCovernote dbPrpPmainCovernote = new DBPrpPmainCovernote(
					dbManager);
			prpPmainCovernoteDto = dbPrpPmainCovernote
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpPmainCovernoteDto;
	}

	/**
	 * 獲取預約保險主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險主信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPmainCovernoteDto getPrpCPmainCovernote(String businessNo)
			throws Exception {
		PrpCPmainCovernoteDto prpCPmainCovernoteDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			DBPrpCPmainCovernote dbPrpCPmainCovernote = new DBPrpCPmainCovernote(
					dbManager);
			prpCPmainCovernoteDto = dbPrpCPmainCovernote
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCPmainCovernoteDto;
	}

	/**
	 * 根據業務號返回定級信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 定級信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPgradeDto getPrpCPgrade(String businessNo) throws Exception {
		PrpCPgradeDto prpCPgradeDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			prpCPgradeDto = new DBPrpCPgrade(dbManager)
					.findByPrimaryKey(businessNo);

		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCPgradeDto;
	}

	/**
	 * 獲取預約保險批改訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 預約保險批改訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpPheadCovernote(java.lang.String)
	 */
	public PrpPheadCovernoteDto getPrpPheadCovernote(String businessNo)
			throws Exception {
		PrpPheadCovernoteDto prpPheadDtoCovernote = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			DBPrpPheadCovernote dbPrpPheadCovernote = new DBPrpPheadCovernote(
					dbManager);
			prpPheadDtoCovernote = dbPrpPheadCovernote
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpPheadDtoCovernote;
	}

	/**
	 * 根據業務號返回批單訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 批單訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpPmain(java.lang.String)
	 */
	public PrpPmain getPrpPmain(String businessNo) throws Exception {
		PrpPmain prpPmain = null;
		try {
			prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo)
					.getPrpPmains().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpPmain;
	}

	/**
	 * 根據業務號返回保單訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpCPmain(java.lang.String)
	 */
	public PrpCPmain getPrpCPmain(String businessNo) throws Exception {
		PrpCPmain prpCPmain = null;
		try {
			prpCPmain = prpCpMainService.getPrpCpMainByPolicyNo(businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpCPmain;
	}

	/**
	 * 根據不同業務號獲取批單費用信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 批單費用信息
	 * @throws Exception
	 *             異常
	 */
	public PrpCPexpenseDto getPrpCPexpense(String businessNo,
			String businessType) throws Exception {
		PrpCPexpenseDto prpCPexpenseDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			if (businessType.equals("E")) {
				prpCPexpenseDto = new DBPrpCPexpense(dbManager)
						.findByPrimaryKey(businessNo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpCPexpenseDto;
	}

	/**
	 * 查找計算書主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 計算書主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpLcompensateDto getPrpLcompensate(String businessNo)
			throws Exception {
		PrpLcompensateDto prpLcompensateDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			prpLcompensateDto = new DBPrpLcompensate(dbManager)
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpLcompensateDto;
	}

	/**
	 * 查找計算書主表信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 計算書主表信息
	 * @throws Exception
	 *             異常
	 */
	public PrpLprepayDto getPrpLprepay(String businessNo) throws Exception {
		PrpLprepayDto prpLprepayDto = null;
		// 创建数据库管理对象
		DBManager dbManager = new DBManager();
		// 连接数据库
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		try {
			prpLprepayDto = new DBPrpLprepay(dbManager)
					.findByPrimaryKey(businessNo);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return prpLprepayDto;
	}

	/**
	 * 獲取要保書，保單的折幣總保額總保費(拆分危險單位時調用).
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 總保額總保費
	 * @throws Exception
	 *             異常
	 */
	public CommonAmountAndPremiumVo getAmountAndPremium(String businessNo,
			String businessType) throws Exception {
		InternationalizationUtil interal = new InternationalizationUtil();
		CommonAmountAndPremiumVo commonAmountAndPremiumDto = null;
		// 连接数据库
		String statementStr = null;
		if (businessType.equals("T")) {
			statementStr = "select sum(amount2) amount,sum(premium2) premium "
					+ "from prptfee where proposalno='" + businessNo + "'";
		}
		if (businessType.equals("P")) {
			statementStr = "select sum(amount2) amount,sum(premium2) premium "
					+ "from prpcfee where policyno='" + businessNo + "'";
		}
		if (businessType.equals("E")) {
			PrpPmain prpPmain = new PrpPmain();
			prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo)
					.getPrpPmains().get(0);
			statementStr = "select sum(amount2) amount,sum(premium2) premium "
					+ "from prpcpfee where policyNo='" + prpPmain.getPolicyNo()
					+ "'";

		}
		// System.out.println(statementStr);
		List list = super.getSession().createSQLQuery(statementStr).list();
		Iterator it = list.iterator();
		Collection collection = new ArrayList();
		while (it.hasNext()) {
			try {
				Object[] obj = (Object[]) it.next();
				commonAmountAndPremiumDto = new CommonAmountAndPremiumVo();
				commonAmountAndPremiumDto.setBussinessNo(businessNo);
				commonAmountAndPremiumDto.setBussinessType(businessType);
				if (((BigDecimal) obj[0] != null)) {
					commonAmountAndPremiumDto.setAmount(((BigDecimal) obj[0])
							.doubleValue());
				}
				if (((BigDecimal) obj[1] != null)) {
					commonAmountAndPremiumDto.setPremium(((BigDecimal) obj[1])
							.doubleValue());
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(
						interal.getText("undwrt.action.commonDangerRisk.queryDataError"));
			}
		}
		return commonAmountAndPremiumDto;
	}

	/**
	 * 根據不同業務號壹次獲取對應的所有危險單位主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 所有危險單位主信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerDetailList(String businessNo, String businessType)
			throws Exception {
		// System.out.println("-----正在获取业务类型为" + businessType +
		// "的危险单位主信息------");
		List dangerDetailList = null;
		QueryRule queryRule;
		String riskCode = "";
		String itemKind = "";
		List dangerList = new ArrayList();
		if (businessType.equals("T"))
		{
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.proposalNo", businessNo);
			queryRule.addAscOrder("id.dangerNo");
			dangerDetailList = prpTdangerUnitService.findByConditions(queryRule);
			if (dangerDetailList != null)
			{
				Iterator dangerDetail = dangerDetailList.iterator();
				while (dangerDetail.hasNext())
				{
					PrpTDangerUnit prpTdangerUnit = (PrpTDangerUnit) dangerDetail.next();
					PrpTDangerUnitVO prpTdangerUnitVo = new PrpTDangerUnitVO(prpTdangerUnit);
					riskCode = prpTdangerUnitVo.getRiskCode();
					if (!(riskCode == null || riskCode.equals("")))
					{
						PrpDrisk prpdrisk = this.findByConditions(riskCode);
						prpTdangerUnitVo.setRiskName(prpdrisk.getRiskCName());
					}
					itemKind = prpTdangerUnitVo.getItemKind();
					if (!(itemKind == null || itemKind.equals("")))
					{
						PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",itemKind);
						if (prpDcode != null)
						{
							prpTdangerUnitVo.setItemKindDesc(prpDcode.getId()
									.getCodeCName());
						}
					}
					int dangerNo =prpTdangerUnitVo.getDangerNo();
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("proposalNo", businessNo);
					queryRule.addEqual("dangerNo",dangerNo);
					List list = feoEnquiryService.findByConditions(queryRule);
					if(null!=list && list.size()>0)
					{
						prpTdangerUnitVo.setHasEnquiry(true);
					}
					dangerList.add(prpTdangerUnitVo);
				}
			}

		}
		else if (businessType.equals("P"))
		{
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", businessNo);
			queryRule.addAscOrder("id.dangerNo");
			dangerDetailList = prpCDangerUnitService.findByConditions(queryRule);
			if (dangerDetailList != null)
			{
				Iterator dangerDetail = dangerDetailList.iterator();
				while (dangerDetail.hasNext())
				{
					PrpCDangerUnit prpCdangerUnit = (PrpCDangerUnit) dangerDetail.next();
					PrpCDangerUnitVO prpCdangerUnitVo = new PrpCDangerUnitVO(prpCdangerUnit);
					riskCode = prpCdangerUnitVo.getRiskCode();
					if (!(riskCode == null || riskCode.equals("")))
					{
						PrpDrisk prpdrisk = this.findByConditions(riskCode);
						prpCdangerUnitVo.setRiskName(prpdrisk.getRiskCName());
					}
					itemKind = prpCdangerUnitVo.getItemKind();
					if (!(itemKind == null || itemKind.equals("")))
					{
						PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",itemKind);
						if (prpDcode != null)
						{
							prpCdangerUnitVo.setItemKindDesc(prpDcode.getId()
									.getCodeCName());
						}
					}
					int dangerNo =prpCdangerUnitVo.getDangerNo();
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("proposalNo", businessNo);
					queryRule.addEqual("dangerNo",dangerNo);
					List list = feoEnquiryService.findByConditions(queryRule);
					/*if(null!=list && list.size()>0)
					{
						prpCdangerUnitVo.setHasEnquiry(true);
					}
					dangerList.add(prpCdangerUnitVo);*/
				}
			}
		}
		else if (businessType.equals("E"))
		{
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.endorseNo", businessNo);
			queryRule.addAscOrder("id.dangerNo");
			dangerDetailList = prpPDangerUnitService.findByConditions(queryRule);
			if (dangerDetailList != null)
			{
				Iterator dangerDetail = dangerDetailList.iterator();
				while (dangerDetail.hasNext())
				{
					PrpPDangerUnit prpPdangerUnit = (PrpPDangerUnit) dangerDetail.next();
					PrpPDangerUnitVO prpPdangerUnitVo = new PrpPDangerUnitVO(prpPdangerUnit);
					riskCode = prpPdangerUnitVo.getRiskCode();
					if (!(riskCode == null || riskCode.equals("")))
					{
						PrpDrisk prpdrisk = this.findByConditions(riskCode);
						prpPdangerUnitVo.setRiskName(prpdrisk.getRiskCName());
					}
					itemKind = prpPdangerUnitVo.getItemKind();
					if (!(itemKind == null || itemKind.equals("")))
					{
						PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",itemKind);
						if (prpDcode != null)
						{
							prpPdangerUnitVo.setItemKindDesc(prpDcode.getId().getCodeCName());
						}
					}
					int dangerNo =prpPdangerUnitVo.getDangerNo();
					queryRule = QueryRule.getInstance();
					queryRule.addEqual("proposalNo", businessNo);
					queryRule.addEqual("dangerNo",dangerNo);
					List list = feoEnquiryService.findByConditions(queryRule);
					if(null!=list && list.size()>0)
					{
						prpPdangerUnitVo.setHasEnquiry(true);
					}
					dangerList.add(prpPdangerUnitVo);
				}
			}
		} 
		return dangerList;
	}

	/**
	 * 獲取標的信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @return 標的信息
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getCustomItemKindList(String businessType,
			String businessNo, String riskCode) throws Exception {
		ArrayList itemKindList = new ArrayList();
		// 标的信息
		if (businessType.equals("T")) {
			itemKindList = (ArrayList) this.getPrpCustomTitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("P")) {
			itemKindList = (ArrayList) this.getPrpCustomCitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("E")) {
			itemKindList = (ArrayList) this.getPrpCustomPitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("C")) {
			itemKindList = (ArrayList) this.getPrpCustomCitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("Y")) {
			itemKindList = (ArrayList) this.getPrpCustomCitemKindList(
					businessNo, riskCode);
		}
		return itemKindList;
	}

	/**
	 * 自定義獲取標的信息.
	 * 
	 * @param proposalNo
	 *            要保書號
	 * @param riskCode
	 *            險種代碼
	 * @return Collection
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCustomTitemKindList(String proposalNo,
			String riskCode) throws SQLException, Exception {
		Collection collection = new ArrayList();
		try {
			collection = getItemKindInfoService.getItemInfoMain(proposalNo,
					riskCode, "T");
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return collection;
	}

	/**
	 * 自定義獲取標的信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @param riskCode
	 *            險種代碼
	 * @return Collection
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCustomCitemKindList(String policyNo, String riskCode)
			throws SQLException, Exception {
		Collection collection = new ArrayList();
		try {
			collection = getItemKindInfoService.getItemInfoMain(policyNo,
					riskCode, "P");
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return collection;
	}

	/**
	 * 自定義獲取標的信息.
	 * 
	 * @param endorseNo
	 *            批單號
	 * @param riskCode
	 *            險種代碼
	 * @return Collection
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCustomPitemKindList(String endorseNo,
			String riskCode) throws SQLException, Exception {
		Collection collection = new ArrayList();
		try {
			collection = getItemKindInfoService.getItemInfo(endorseNo, riskCode, "E");
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return collection;
	}

	/**
	 * 自定義獲取標的信息.
	 * 
	 * @param proposalNo
	 *            要保書號
	 * @param riskCode
	 *            險種代碼
	 * @return Collection
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getQtaCustomTitemKindList(String proposalNo,
			String riskCode) throws SQLException, Exception {
		Collection collection = new ArrayList();
		try {
			collection = getItemKindInfoService.getItemInfo(proposalNo,
					riskCode, "T");
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return collection;
	}

	/**
	 * 獲得保單號.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單號
	 * @throws Exception
	 *             異常
	 */
	public String getMessageId(String businessNo) throws Exception {
		String messageId = "";
		try {
			// 暂时不再使用此表20130802
			// DBPrpLcompensate dbPrpLcompensate = new
			// DBPrpLcompensate(dbManager);
			// DBPrpLprepay dbPrpLprepay = new DBPrpLprepay(dbManager);
			if (businessNo.substring(0, 1).equals("T")
					|| businessNo.substring(0, 1).equals("1")
					|| businessNo.substring(0, 1).equals("9")
					|| businessNo.substring(0, 1).equals("7")) {
				messageId = businessNo;
			}
			if (businessNo.substring(0, 1).equals("1")
					&& businessNo.length() == 25) {
				PrpPhead prpPhead = new PrpPhead();
				prpPhead = endorseService.getPrpPheadByEndorseNo(businessNo);
				messageId = prpPhead.getPolicyNo();
			}
			if (businessNo.substring(0, 1).equals("2")
					&& businessNo.length() == 25) {
				PrpLcompensateDto prpLcompensateDto = new PrpLcompensateDto();
				// prpLcompensateDto = dbPrpLcompensate
				// .findByPrimaryKey(businessNo);
				messageId = prpLcompensateDto.getPolicyNo();
			}
			if (businessNo.substring(0, 1).equals("Y")) {
				PrpLprepayDto prpLprepayDto = new PrpLprepayDto();
				// prpLprepayDto = dbPrpLprepay.findByPrimaryKey(businessNo);
				messageId = prpLprepayDto.getPolicyNo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return messageId;
	}

	/**
	 * 獲取到指定危險單位序號的投保單(批單)的危險單位主信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 拆分危險單位程序
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerDetail(String businessType, String businessNo,
			String iDangerNo) throws SQLException, Exception {
		Collection dangerDetail = new ArrayList();
		String itemKind = "";
		int dangerNo = Integer.parseInt(iDangerNo);
		double baseRate = 0d;
		String riskCode = "";

		baseRate = this.getBaseRate(businessType, businessNo);
		if (businessType.equals("T")) {
			PrpTDangerUnitId id = new PrpTDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setProposalNo(businessNo);
			PrpTDangerUnit prpTdangerUnit = prpTdangerUnitService
					.findByConditions(id);
			if (prpTdangerUnit != null) {
				PrpTDangerUnitVO prpTdangerUnitDto = new PrpTDangerUnitVO(
						prpTdangerUnit);
				prpTdangerUnitDto.setBaseRate(baseRate);
				itemKind = prpTdangerUnitDto.getItemKind();
				if (!(itemKind == null || itemKind.equals(""))) {
					PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",
							itemKind);
					if (prpDcode != null) {
						prpTdangerUnitDto.setItemKindDesc(prpDcode.getId()
								.getCodeCName());
					}
				}
				riskCode = prpTdangerUnitDto.getRiskCode();
				if (!(riskCode == null || riskCode.equals(""))) {
					PrpDrisk prpdrisk = this.findByConditions(riskCode);
					prpTdangerUnitDto.setRiskName(prpdrisk.getRiskCName());
				}
				dangerDetail.add(prpTdangerUnitDto);
			}
		}
		if (businessType.equals("P")) {

			PrpCDangerUnitId id = new PrpCDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setPolicyNo(businessNo);
			PrpCDangerUnit prpCdangerUnit = prpCDangerUnitService
					.findByConditions(id);

			if (prpCdangerUnit != null) {
				PrpCDangerUnitVO prpCdangerUnitDto = new PrpCDangerUnitVO(
						prpCdangerUnit);
				prpCdangerUnitDto.setBaseRate(baseRate);
				itemKind = prpCdangerUnitDto.getItemKind();
				if (!(itemKind == null || itemKind.equals(""))) {
					PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",
							prpCdangerUnitDto.getItemKind());
					if (prpDcode != null) {
						prpCdangerUnitDto.setItemKindDesc(prpDcode.getId()
								.getCodeCName());
					}
				}
				riskCode = prpCdangerUnitDto.getRiskCode();
				if (!(riskCode == null || riskCode.equals(""))) {
					PrpDrisk prpdrisk = this.findByConditions(riskCode);
					prpCdangerUnitDto.setRiskName(prpdrisk.getRiskCName());
				}
				dangerDetail.add(prpCdangerUnitDto);
			}
		}
		if (businessType.equals("E")) {
			PrpPDangerUnitId id = new PrpPDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setEndorseNo(businessNo);
			PrpPDangerUnit prpPdangerUnit = prpPDangerUnitService
					.findByPrimaryKey(id);
			if (prpPdangerUnit != null) {
				PrpPDangerUnitVO prpPdangerUnitDto = new PrpPDangerUnitVO(
						prpPdangerUnit);
				prpPdangerUnitDto.setBaseRate(baseRate);
				itemKind = prpPdangerUnitDto.getItemKind();
				if (!(itemKind == null || itemKind.equals(""))) {
					PrpDcode prpDcode = this.findByPrimaryKey("ItemKind",
							itemKind);
					if (prpDcode != null) {
						prpPdangerUnitDto.setItemKindDesc(prpDcode.getId()
								.getCodeCName());
					}
				}
				riskCode = prpPdangerUnitDto.getRiskCode();
				if (!(riskCode == null || riskCode.equals(""))) {
					PrpDrisk prpdrisk = this.findByConditions(riskCode);
					prpPdangerUnitDto.setRiskName(prpdrisk.getRiskCName());
				}
				dangerDetail.add(prpPdangerUnitDto);
			}
		}
		return dangerDetail;
	}

	/**
	 * 獲取我司比例.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @return baseRate
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public double getBaseRate(String businessType, String businessNo)
			throws SQLException, Exception {
		double baseRate = 100;
		Collection coinsDtoList = new ArrayList();
		// 暂时不再使用此表20130802
		// coinsDtoList = this.getPrpCoinsList(businessNo, businessType,
		// dbManager);
		if (businessType.equals("T")) {
			if (coinsDtoList != null && coinsDtoList.size() > 0) {
				baseRate = 0;
				Iterator iterator = coinsDtoList.iterator();
				while (iterator.hasNext()) {
					PrpTcoinsDto prpTcoinsDto = (PrpTcoinsDto) iterator.next();
					if (prpTcoinsDto.getCoinsType().equals("1")
							|| prpTcoinsDto.getCoinsType().equals("2")) {
						baseRate = baseRate + prpTcoinsDto.getCoinsRate();
					}
				}
			}
		} else if (businessType.equals("P") || businessType.equals("E")) {
			if (businessType.equals("E")) {
				PrpPhead prpPhead = endorseService
						.getPrpPheadByEndorseNo(businessNo);
				// 暂时不再使用此表数据20130802
				// coinsDtoList = this.getPrpCoinsList(prpPhead.getPolicyNo(),
				// "P", dbManager);
			}
			if (coinsDtoList != null && coinsDtoList.size() > 0) {
				baseRate = 0;
				Iterator iterator = coinsDtoList.iterator();
				while (iterator.hasNext()) {
					PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) iterator.next();
					if (prpCcoinsDto.getCoinsType().equals("1")
							|| prpCcoinsDto.getCoinsType().equals("2")) {
						baseRate = baseRate + prpCcoinsDto.getCoinsRate();
					}
				}
			}
		}

		return baseRate;
	}

	/**
	 * 取當前數據庫聯共保信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 共保信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCoinsList(String businessNo, String businessType)
			throws Exception {
		DBManager dbManager = new DBManager();
		Collection collection = null;

		try {
			// 连接数据库
			dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
			collection = this.getPrpCoinsList(businessNo, businessType,
					dbManager);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭数据库连接
			dbManager.close();
		}
		return collection;
	}

	/**
	 * 取當前數據庫聯共保信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 共保信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCoinsList(String businessNo, String businessType,
			DBManager dbManager) throws Exception {
		Collection collection = null;
		String conditions = "";
		if (businessType.equals("T")) {
			conditions = " ProposalNo = '" + businessNo + "'";
			collection = new DBPrpTcoins(dbManager)
					.findByConditions(conditions);
		} else if (businessType.equals("P")) {
			conditions = " PolicyNo = '" + businessNo + "'";
			collection = new DBPrpCcoins(dbManager)
					.findByConditions(conditions);
		} else if (businessType.equals("E")) {
			conditions = " EndorseNo = '" + businessNo + "'";
			collection = new DBPrpPcoins(dbManager)
					.findByConditions(conditions);
		}
		return collection;
	}

	/**
	 * 獲取危險單位的所有子信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 子信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerItemList(String businessNo, String dangerNo,
			String businessType) throws Exception {
		Collection dangerItemList = new ArrayList();
		try {
			dangerItemList = dangerUnitService.getDangerUnitItemInfo(
					businessNo, dangerNo, businessType);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dangerItemList;
	}

	/**
	 * 获取到收费计划中的币种类别.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 收费计划中的币种类别
	 * @throws Exception
	 *             異常
	 */
	public Collection getPlanCurrencyType(String businessNo, String businessType)
			throws Exception {
		Collection CurrencyKind = null;
		try {
			CurrencyKind = getPlanCurrencyTypeService.getPlanCurrencyType(
					businessNo, businessType);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return CurrencyKind;
	}

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpTDangerUnit getDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception {
		PrpTDangerUnit prpTdangerUnitDto = null;
		try {
			if (businessType.equals("T")) {
				PrpTDangerUnitId id = new PrpTDangerUnitId();
				id.setDangerNo(Integer.parseInt(dangerNo));
				id.setProposalNo(businessNo);
				prpTdangerUnitDto = prpTdangerUnitService.findByConditions(id);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpTdangerUnitDto;
	}

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpCDangerUnit getCDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception {
		PrpCDangerUnit prpCdangerUnitDto = null;
		try {
			if (businessType.equals("P")) {
				PrpCDangerUnitId id = new PrpCDangerUnitId();
				id.setDangerNo(Integer.parseInt(dangerNo));
				id.setPolicyNo(businessNo);
				prpCdangerUnitDto = prpCDangerUnitService.findByConditions(id);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpCdangerUnitDto;
	}

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @return 風險等級
	 * @throws Exception
	 *             異常
	 */
	public PrpPDangerUnit getPDangerRiskLevel(String businessType,
			String businessNo, String dangerNo) throws Exception {
		PrpPDangerUnit prpPdangerUnitDto = null;
		try {
			if (businessType.equals("E")) {
				PrpPDangerUnitId id = new PrpPDangerUnitId();
				id.setDangerNo(Integer.parseInt(dangerNo));
				id.setEndorseNo(businessNo);
				prpPdangerUnitDto = prpPDangerUnitService.findByPrimaryKey(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prpPdangerUnitDto;
	}

	/**
	 * 保存投保單的所有危險單位主訊息.
	 * 
	 * @param dangerList
	 *            the danger list
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void savePrpTdangerUnit(ArrayList dangerList) throws SQLException,
			Exception {
		PrpTDangerUnit prpTdangerUnitDto = null;
		PrpTDangerUnit dbPrpTdangerUnitDto = null;

		for (int i = 0; i < dangerList.size(); i++) {
			prpTdangerUnitDto = (PrpTDangerUnit) dangerList.get(i);
			String proposalNo = prpTdangerUnitDto.getId().getProposalNo();
			int dangerNo = prpTdangerUnitDto.getId().getDangerNo();

			PrpTDangerUnitId id = new PrpTDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setProposalNo(proposalNo);
			dbPrpTdangerUnitDto = prpTdangerUnitService.findByConditions(id);

			if (dbPrpTdangerUnitDto != null) // 主表中有数据，则只能更新
			{
				prpTdangerUnitService.update(prpTdangerUnitDto);
			} else {
				prpTdangerUnitService.save(prpTdangerUnitDto);
			}
		}
	}

	/**
	 * 保存保單的危險單位拆分信息.
	 * 
	 * @param dangerList
	 *            危險單位信息集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void savePrpCdangerUnit(ArrayList dangerList) throws SQLException,
			Exception {
		PrpCDangerUnit prpCdangerUnitDto = null;
		PrpCDangerUnit dbPrpCdangerUnitDto = null;
		for (int i = 0; i < dangerList.size(); i++) {
			prpCdangerUnitDto = (PrpCDangerUnit) dangerList.get(i);
			String policyNo = prpCdangerUnitDto.getId().getPolicyNo();
			int dangerNo = prpCdangerUnitDto.getId().getDangerNo();
			PrpCDangerUnitId id = new PrpCDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setPolicyNo(policyNo);
			dbPrpCdangerUnitDto = prpCDangerUnitService.findByConditions(id);
			if (dbPrpCdangerUnitDto != null) // 主表中有数据，则只能更新
			{
				prpCDangerUnitService.update(prpCdangerUnitDto);
			} else {
				prpCDangerUnitService.save(prpCdangerUnitDto);
			}
		}
	}

	/**
	 * 保存批單的危險單位主信息.
	 * 
	 * @param dangerList
	 *            危險單位主信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#savePrpPdangerUnit(java.util.ArrayList)
	 */
	public void savePrpPdangerUnit(ArrayList dangerList) throws SQLException,
			Exception {
		PrpPDangerUnit prpPdangerUnitDto = null;
		PrpPDangerUnit dbPrpPdangerUnitDto = null;

		for (int i = 0; i < dangerList.size(); i++) {
			prpPdangerUnitDto = (PrpPDangerUnit) dangerList.get(i);

			String endorseNo = prpPdangerUnitDto.getId().getEndorseNo();
			int dangerNo = prpPdangerUnitDto.getId().getDangerNo();
			PrpPDangerUnitId id = new PrpPDangerUnitId();
			id.setDangerNo(dangerNo);
			id.setEndorseNo(endorseNo);
			dbPrpPdangerUnitDto = prpPDangerUnitService.findByPrimaryKey(id);
			if (dbPrpPdangerUnitDto != null) // 主表中有数据，则只能更新
			{
				// System.out.println("--进行的是批单的危险单位主信息的更新操作--");
				prpPDangerUnitService.update(prpPdangerUnitDto);
				// System.out.println("批单的危险单位主信息更新数据成功");

			} else {
				// System.out.println("--进行的是批单的危险单位主信息的插入操作---");
				prpPDangerUnitService.save(prpPdangerUnitDto);
			}
		}
	}

	/**
	 * 獲取保單訊息.
	 * 
	 * @param sql
	 *            查詢條件
	 * @return 保單訊息集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getPrpCmainList(String sql) throws SQLException, Exception {
		ArrayList cmainList = new ArrayList();
		try {
			cmainList = (ArrayList) super.getSession().createSQLQuery(sql)
					.addEntity(PrpCmain.class).list();
		} catch (Exception e) {
			throw e;
		}
		return cmainList;
	}

	/**
	 * 獲取報價單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 報價單信息
	 * @throws Exception
	 *             異常
	 */
	@Override
	public PrpQmain getPrpTmainQta(String businessNo)
			throws Exception {
		PrpQmain prpQmain = null;
		
		prpQmain =  policyService.getPrpQmainByProposalNo(businessNo,
				"quotation");;

		return prpQmain;
	}

	/**
	 * 保存危險單位拆分主信息(不保存子信息).
	 * 
	 * @param dangerList
	 *            危險單位主信息集合
	 * @param businessType
	 *            業務類型
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void saveDangerUnit(ArrayList dangerList, String businessType)
			throws SQLException, Exception {
		// 连接数据库
		try {
			if (businessType.equals("T")) {
				this.savePrpTdangerUnit(dangerList);
			}
			if (businessType.equals("P")) {
				this.savePrpCdangerUnit(dangerList);
			}
			if (businessType.equals("E")) {
				this.savePrpPdangerUnit(dangerList);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 獲取到指定的收費計劃信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 指定的收費計劃信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpPlan(String businessNo, String businessType)
			throws Exception {
		Collection result = null;
		if (businessType.equals("T")) {
			Collection prpTplanList = new ArrayList();
			PrpTplan prpTplan = null;
			String sql = "SELECT * FROM PRPTPLAN WHERE ProposalNo = '"
					+ businessNo + "'";
			prpTplanList = super.getSession().createSQLQuery(sql)
					.addEntity(PrpTplan.class).list();
			result = prpTplanList;
		}

		else if (businessType.equals("P")) {
			Collection prpCplanList = new ArrayList();
			PrpCplan prpCplan = null;
			String sql = "SELECT * FROM PRPTPLAN WHERE policyNo = '"
					+ businessNo + "'";
			prpCplanList = super.getSession().createSQLQuery(sql)
					.addEntity(PrpCplan.class).list();
			result = prpCplanList;
		}

		else if (businessType.equals("E")) {// 查CP表数据时用对应的PolicyNo去查
			Collection prpCPplanList = new ArrayList();
			PrpCPplan prpCPplan = null;
			String sql = "SELECT * FROM PrpCPplan WHERE policyNo  ='"
					+ businessNo + "'";
			prpCPplanList = super.getSession().createSQLQuery(sql)
					.addEntity(PrpCPplan.class).list();
			result = prpCPplanList;
		}

		return result;
	}

	/**
	 * 獲取業務的支付幣別相關信息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @return 支付幣別相關信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getExchangeRate(String businessType, String businessNo)
			throws Exception {
		String conditions = "";
		ArrayList prpTfeeList = null;
		ArrayList prpCfeeList = null;
		ArrayList prpPfeeList = null;

		if (businessType.equals("T")) {

			conditions = "select * from PrpTfee where proposalNo = '"
					+ businessNo + "'";
			prpTfeeList = (ArrayList) super.getSession()
					.createSQLQuery(conditions).addEntity(PrpTfee.class).list();
			return prpTfeeList;
		}
		if (businessType.equals("C")) {

			conditions = "select * from PrpCfee where policyNo = '"
					+ businessNo + "'";
			prpCfeeList = (ArrayList) super.getSession()
					.createSQLQuery(conditions).addEntity(PrpCfee.class).list();
			return prpCfeeList;
		} else if (businessType.equals("E")) {

			PrpPmain prpPmain = new PrpPmain();
			prpPmain = endorseService.getPrpPheadByEndorseNo(businessNo)
					.getPrpPmains().get(0);
			conditions = "select * from PrpCPfee where  policyNo = '"
					+ prpPmain.getPolicyNo() + "'";
			prpPfeeList = (ArrayList) super.getSession()
					.createSQLQuery(conditions).addEntity(PrpCPfee.class)
					.list();
			return prpPfeeList;
		}
		return null;
	}

	/**
	 * 獲取投保標的信息.
	 * 
	 * @param proposalNo
	 *            業務號
	 * @return 投保標的信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpTitemKindList(String proposalNo)
			throws SQLException, Exception {
		Collection collection = new ArrayList();
		String sql = "select * from PrpTitemKind where  proposalNo='"
				+ proposalNo + "'";
		collection = super.getSession().createSQLQuery(sql)
				.addEntity(PrpTitemKind.class).list();
		return collection;
	}

	/**
	 * 獲取投保單特別約定信息.
	 * 
	 * @param proposalNo
	 *            業務號
	 * @return 投保單特別約定信息類集合.
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpTengageList(String proposalNo) throws Exception {
		Collection collection = new ArrayList();
		String sql = "select * from PrpTengage where  proposalNo='"
				+ proposalNo + "'";
		collection = (ArrayList) super.getSession().createSQLQuery(sql)
				.addEntity(PrpTengage.class).list();
		return collection;
	}

	/**
	 * 取當前業務危險單位聯共保信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 危險單位聯共保信息類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpDangerCoinsList(String businessNo,
			String businessType) throws Exception {
		Collection collection = null;
		String conditions = "";
		QueryRule queryRule;
		if (businessType.equals("T")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.proposalNo", businessNo);
			collection = prpTDangerCoinsService.findByConditions(queryRule);
		} else if (businessType.equals("P")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", businessNo);
			collection = prpCDangerCoinsService.findByConditions(queryRule);
		} else if (businessType.equals("E")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.endorseNo", businessNo);
			collection = prpPDangerCoinsService.findByConditions(queryRule);
		}
		return collection;
	}

	/**
	 * 獲取要保書危險單位交費計畫訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 要保書危險單位交費計畫訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService#getPrpDangerPlanList(java.lang.String,
	 *      java.lang.String)
	 */
	public Collection getPrpDangerPlanList(String businessNo,
			String businessType) throws Exception {
		Collection collection = null;
		String conditions = "";
		QueryRule queryRule;
		if (businessType.equals("T")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.proposalNo", businessNo);
			collection = prpTDangerPlanService.findByConditions(queryRule);
		} else if (businessType.equals("P")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", businessNo);
			collection = prpCDangerPlanService.findByConditions(queryRule);
		} else if (businessType.equals("E")) {
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.endorseNo", businessNo);
			collection = prpPDangerPlanService.findByConditions(queryRule);
		}
		return collection;
	}

	/**
	 * 獲取保單標的信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 保單標的信息類集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCitemKindList(String policyNo) throws Exception {
		Collection collection = new ArrayList();
		String sql = " select * from PrpCitemKind where policyNo='" + policyNo
				+ "'";
		collection = super.getSession().createSQLQuery(sql)
				.addEntity(PrpCitemKind.class).list();
		return collection;
	}

	/**
	 * 獲取是否計入總保額的標誌位.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param kindCode
	 *            險別代碼
	 * @return the string
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public String findKindFlag(String riskCode, String kindCode)
			throws SQLException, Exception {
		String kindFlag = "0";
		String sql = "select * from PrpDkind where RiskCode='" + riskCode
				+ "' and KindCode='" + kindCode + "'";
		String calculateFlag = "";
		// System.out.println("riskCode: " + riskCode + "kindCode :" +
		// kindCode);
		try {
			PrpDkind prpDkind = (PrpDkind) super.getSession()
					.createSQLQuery(sql).addEntity(PrpDkind.class).list()
					.get(0);
			calculateFlag = prpDkind.getCalculateFlag();
		} catch (Exception ee) {
			// System.out.println("PrpDkind中没有匹配的信息");
		}

		if (calculateFlag.length() >= 5)
			kindFlag = calculateFlag.substring(4, 5);
		return kindFlag;
	}

	/**
	 * 獲取批單標的信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 批單標的信息的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCPitemKindList(String policyNo) throws Exception {
		Collection collection = new ArrayList();
		String sql = "select * from PrpCPitemKind where  policyNo='" + policyNo
				+ "'";
		collection = super.getSession().createSQLQuery(sql)
				.addEntity(PrpCPitemKind.class).list();
		return collection;
	}

	/**
	 * 獲取批單特別約定信息.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 批單特別約定信息集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getPrpCPengageList(String policyNo) throws Exception {
		Collection collection = new ArrayList();
		String sql = "select * from PrpCPengAge where  policyNo='" + policyNo
				+ "'";
		collection = (ArrayList) super.getSession().createSQLQuery(sql)
				.addEntity(PrpCPengAge.class).list();
		return collection;
	}

	/**
	 * 判斷是否爲關聯單.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param businessno
	 *            業務號
	 * @return 是關聯單返回true,不是返回false
	 */
	public boolean isAssociation(String riskCode, String businessno) {
		PrpTmainSub prpTmainSub = getPrpTmainSub(riskCode, businessno);
		if (null != prpTmainSub) {
			return true;
		}
		return false;
	}

	/**
	 * 判斷某個單號是否在給定的單號數組裡面.
	 * 
	 * @param businessno
	 *            業務號
	 * @param businessnos
	 *            業務號數組
	 * @return 存在返回true,不存在返回false
	 */
	public boolean isInArray(String businessno, String businessnos[]) {
		for (int i = 0; i < businessnos.length; i++) {
			if (businessno.equals(businessnos[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 獲取要保書子訊息.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param businessno
	 *            業務號
	 * @return 要保書子訊息
	 */
	public PrpTmainSub getPrpTmainSub(String riskCode, String businessno) {
		String sql = null;
		PrpTmainSub prpTmainSub = null;
		if ("B01".equals(riskCode))
		{
			sql = "select * from prptmainsub where MAINPOLICYNO='" + businessno
					+ "'";
		} 
		else
		{
			sql = "select * from prptmainsub where proposalno='" + businessno
					+ "'";
		}
 		List list = super.getSession().createSQLQuery(sql)
				.addEntity(PrpTmainSub.class).list();
		if (null != list && list.size() > 0) {
			prpTmainSub = (PrpTmainSub) list.get(0);
		}
		return prpTmainSub;
	}

	/**
	 * 獲取屬性查詢危險單位信息接口.
	 * 
	 * @return 屬性查詢危險單位信息接口的值
	 */
	public GetItemKindInfoService getGetItemKindInfoService() {
		return getItemKindInfoService;
	}

	/**
	 * 設置屬性查詢危險單位信息接口.
	 * 
	 * @param getItemKindInfoService
	 *            待設置的查詢危險單位信息接口的值
	 */
	public void setGetItemKindInfoService(
			GetItemKindInfoService getItemKindInfoService) {
		this.getItemKindInfoService = getItemKindInfoService;
	}

	/**
	 * 獲取屬性險種代碼接口.
	 * 
	 * @return 屬性險種代碼接口的值
	 */
	public PrpDriskService getPrpDriskReinsService() {
		return prpDriskReinsService;
	}

	/**
	 * 設置屬性險種代碼接口.
	 * 
	 * @param prpDriskReinsService
	 *            待設置的險種代碼接口的值
	 */
	public void setPrpDriskReinsService(PrpDriskService prpDriskReinsService) {
		this.prpDriskReinsService = prpDriskReinsService;
	}

	/**
	 * 獲取屬性通用代碼類型接口.
	 * 
	 * @return 屬性通用代碼類型接口的值
	 */
	public PrpDcodeService getPrpDcodeReinsService() {
		return prpDcodeReinsService;
	}

	/**
	 * 設置屬性通用代碼類型接口.
	 * 
	 * @param prpDcodeReinsService
	 *            待設置的通用代碼類型接口的值
	 */
	public void setPrpDcodeReinsService(PrpDcodeService prpDcodeReinsService) {
		this.prpDcodeReinsService = prpDcodeReinsService;
	}

	/**
	 * 獲取屬性保單危險單位共保資訊接口.
	 * 
	 * @return 屬性保單危險單位共保資訊接口的值
	 */
	public PrpCDangerCoinsService getPrpCDangerCoinsService() {
		return prpCDangerCoinsService;
	}

	/**
	 * 設置屬性保單危險單位共保資訊接口.
	 * 
	 * @param prpCDangerCoinsService
	 *            待設置的保單危險單位共保資訊接口的值
	 */
	public void setPrpCDangerCoinsService(
			PrpCDangerCoinsService prpCDangerCoinsService) {
		this.prpCDangerCoinsService = prpCDangerCoinsService;
	}

	/**
	 * 獲取屬性保單危險單位交費計畫接口.
	 * 
	 * @return 屬性保單危險單位交費計畫接口的值
	 */
	public PrpCDangerPlanService getPrpCDangerPlanService() {
		return prpCDangerPlanService;
	}

	/**
	 * 設置屬性保單危險單位交費計畫接口.
	 * 
	 * @param prpCDangerPlanService
	 *            待設置的保單危險單位交費計畫接口的值
	 */
	public void setPrpCDangerPlanService(
			PrpCDangerPlanService prpCDangerPlanService) {
		this.prpCDangerPlanService = prpCDangerPlanService;
	}

	/**
	 * 獲取屬性批單危險單位共保資訊接口.
	 * 
	 * @return 屬性批單危險單位共保資訊接口的值
	 */
	public PrpPDangerCoinsService getPrpPDangerCoinsService() {
		return prpPDangerCoinsService;
	}

	/**
	 * 設置屬性批單危險單位共保資訊接口.
	 * 
	 * @param prpPDangerCoinsService
	 *            待設置的批單危險單位共保資訊接口的值
	 */
	public void setPrpPDangerCoinsService(
			PrpPDangerCoinsService prpPDangerCoinsService) {
		this.prpPDangerCoinsService = prpPDangerCoinsService;
	}

	/**
	 * 獲取屬性要保書危險單位共保資訊接口.
	 * 
	 * @return 屬性要保書危險單位共保資訊接口的值
	 */
	public PrpTDangerCoinsService getPrpTDangerCoinsService() {
		return prpTDangerCoinsService;
	}

	/**
	 * 設置屬性要保書危險單位共保資訊接口.
	 * 
	 * @param prpTDangerCoinsService
	 *            待設置的要保書危險單位共保資訊接口的值
	 */
	public void setPrpTDangerCoinsService(
			PrpTDangerCoinsService prpTDangerCoinsService) {
		this.prpTDangerCoinsService = prpTDangerCoinsService;
	}

	/**
	 * 獲取屬性批單危險單位交付計畫接口.
	 * 
	 * @return 屬性批單危險單位交付計畫接口的值
	 */
	public PrpPDangerPlanService getPrpPDangerPlanService() {
		return prpPDangerPlanService;
	}

	/**
	 * 設置屬性批單危險單位交付計畫接口.
	 * 
	 * @param prpPDangerPlanService
	 *            待設置的批單危險單位交付計畫接口的值
	 */
	public void setPrpPDangerPlanService(
			PrpPDangerPlanService prpPDangerPlanService) {
		this.prpPDangerPlanService = prpPDangerPlanService;
	}

	/**
	 * 獲取屬性要保書危險單位交費計畫.
	 * 
	 * @return 屬性要保書危險單位交費計畫的值
	 */
	public PrpTDangerPlanService getPrpTDangerPlanService() {
		return prpTDangerPlanService;
	}

	/**
	 * 設置屬性要保書危險單位交費計畫.
	 * 
	 * @param prpTDangerPlanService
	 *            待設置的要保書危險單位交費計畫的值
	 */
	public void setPrpTDangerPlanService(
			PrpTDangerPlanService prpTDangerPlanService) {
		this.prpTDangerPlanService = prpTDangerPlanService;
	}

	/**
	 * 獲取屬性保單危險單位臨分接口.
	 * 
	 * @return 屬性保單危險單位臨分接口的值
	 */
	public PrpCDangerUnitService getPrpCDangerUnitService() {
		return prpCDangerUnitService;
	}

	/**
	 * 設置屬性保單危險單位臨分接口.
	 * 
	 * @param prpCDangerUnitService
	 *            待設置的保單危險單位臨分接口的值
	 */
	public void setPrpCDangerUnitService(
			PrpCDangerUnitService prpCDangerUnitService) {
		this.prpCDangerUnitService = prpCDangerUnitService;
	}

	/**
	 * 獲取屬性理賠的危險單位劃分接口.
	 * 
	 * @return 屬性理賠的危險單位劃分接口的值
	 */
	public PrpLDangerUnitService getPrpLDangerUnitService() {
		return prpLDangerUnitService;
	}

	/**
	 * 設置屬性理賠的危險單位劃分接口.
	 * 
	 * @param prpLDangerUnitService
	 *            待設置的理賠的危險單位劃分接口的值
	 */
	public void setPrpLDangerUnitService(
			PrpLDangerUnitService prpLDangerUnitService) {
		this.prpLDangerUnitService = prpLDangerUnitService;
	}

	/**
	 * 獲取屬性批單的危險單位劃分接口.
	 * 
	 * @return 屬性批單的危險單位劃分接口的值
	 */
	public PrpPDangerUnitService getPrpPDangerUnitService() {
		return prpPDangerUnitService;
	}

	/**
	 * 設置屬性批單的危險單位劃分接口.
	 * 
	 * @param prpPDangerUnitService
	 *            待設置的批單的危險單位劃分接口的值
	 */
	public void setPrpPDangerUnitService(
			PrpPDangerUnitService prpPDangerUnitService) {
		this.prpPDangerUnitService = prpPDangerUnitService;
	}

	/**
	 * 獲取屬性投保單的危險單位劃分接口.
	 * 
	 * @return 屬性投保單的危險單位劃分接口的值
	 */
	public PrpTdangerUnitService getPrpTdangerUnitService() {
		return prpTdangerUnitService;
	}

	/**
	 * 設置屬性投保單的危險單位劃分接口.
	 * 
	 * @param prpTdangerUnitService
	 *            待設置的投保單的危險單位劃分接口的值
	 */
	public void setPrpTdangerUnitService(
			PrpTdangerUnitService prpTdangerUnitService) {
		this.prpTdangerUnitService = prpTdangerUnitService;
	}

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 獲取屬性保單訊息接口.
	 * 
	 * @return 屬性保單訊息接口的值
	 */
	public PrpCpMainService getPrpCpMainService() {
		return prpCpMainService;
	}

	/**
	 * 設置屬性保單訊息接口.
	 * 
	 * @param prpCpMainService
	 *            待設置的保單訊息接口的值
	 */
	public void setPrpCpMainService(PrpCpMainService prpCpMainService) {
		this.prpCpMainService = prpCpMainService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}
	
	/**
	 * 獲取屬性詢價單信息接口.
	 * 
	 * @return 屬性詢價單信息接口的值
	 */
	public FeoEnquiryService getFeoEnquiryService() {
		return feoEnquiryService;
	}

	/**
	 * 設置屬性詢價單信息接口.
	 * 
	 * @param feoEnquiryService
	 *            待設置的詢價單信息接口的值
	 */
	public void setFeoEnquiryService(FeoEnquiryService feoEnquiryService) {
		this.feoEnquiryService = feoEnquiryService;
	}

	// 为了解决jar包的问题临时加的方法
	/**
	 * 根據險種代碼條件獲取險種信息.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return 險種信息類
	 */
	private PrpDrisk findByConditions(String riskCode) {
		String sql = "select * from PrpDrisk where riskcode = '" + riskCode
				+ "'";
		List<PrpDrisk> list = this.getSession().createSQLQuery(sql)
				.addEntity(PrpDrisk.class).list();
		return list.get(0);
	}

	// 为了解决jar包的问题临时加的方法
	/**
	 * 根據業務代碼類型和業務代碼獲取通用代碼類信息.
	 * 
	 * @param codeType
	 *            通用代碼類型
	 * @param codeCode
	 *            業務代碼
	 * @return 通用代碼類信息
	 * @throws Exception
	 *             異常
	 */
	public PrpDcode findByPrimaryKey(String codeType, String codeCode)
			throws Exception {
		PrpDcode prpDcode = null;
		String sql = "select * from PrpDcode where codeType='" + codeType
				+ "' and codeCode='" + codeCode + "'";
		List<PrpDcode> list = this.getSession().createSQLQuery(sql)
				.addEntity(PrpDcode.class).list();
		if (list.size() > 0) {
			prpDcode = list.get(0);
		}
		return prpDcode;
	}
}
