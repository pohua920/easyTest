package com.sinosoft.claim.common.util;

/****************************************************************************
 * DESC       ：业务处理工具类
 * AUTHOR     ：中科软
 * CREATEDATE ： 2005-03-22
 * MODIFYLIST ：  Name       Date            Reason/Contents
 ****************************************************************************/

import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.claim.bl.facade.BLPrpLclaimFacade;
import com.sinosoft.claim.bl.facade.BLPrpLcompensateFacade;
import com.sinosoft.claim.bl.facade.BLPrpLregistFacade;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.dto.custom.PolicyDto;
import com.sinosoft.claim.dto.custom.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpLclaimDto;
import com.sinosoft.claim.dto.domain.PrpLcompensateDto;
import com.sinosoft.claim.dto.domain.PrpLregistDto;
import com.sinosoft.claim.dto.domain.UtiCodeTransferDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.ui.control.action.UIPrepayAction;
import com.sinosoft.claim.ui.control.action.UIUtiCodeTransferAction;
import com.sinosoft.claim.ui.control.viewHelper.EndorseViewHelper;
import com.sinosoft.platform.bl.action.domain.BLUtiGradeTaskAction;
import com.sinosoft.platform.bl.facade.BLPrpDcodeFacade;
import com.sinosoft.platform.bl.facade.BLUtiUserGradePowerFacade;
import com.sinosoft.platform.dto.domain.PrpDcodeDto;
import com.sinosoft.platform.dto.domain.UtiGradeTaskDto;
import com.sinosoft.platform.dto.domain.UtiUserGradePowerDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utiall.blsvr.BLPrpDrisk;

/**
 * 数据类型相关处理工具类
 */
public class BusinessRuleUtil {
	/** 日志信息 */
	private static Log log = LogFactory.getLog(BusinessRuleUtil.class);
	/**数据类型相关处理工具类*/
	private static BusinessRuleUtil businessRuleUtil = new BusinessRuleUtil();
	/**险种配置service*/
	private static UtiCodeTransferService utiCodeTransferService;

	public BusinessRuleUtil getInstance() {
		return businessRuleUtil;
	}

	/**
	 * 私有构造方法，禁止私自构造该类，请使用getInstance获得该实例
	 */
	private BusinessRuleUtil() {

	}

	/** 数据缓存*/
	private static Map<String, UtiCodeTransferDto> transferMap = null;
	/** 数据缓存*/
	private static Collection<UtiCodeTransferDto> transferList = null;

	/**
	 * 查询险种配置信息
	 * @param outerCode 配置信息
	 * @return
	 * @throws UserException
	 */
	private static UtiCodeTransferDto getUtiCodeTransferDto(String outerCode) throws UserException {
		if (transferList == null || transferMap == null) {
			initTransfer();
		}
		return transferMap.get(outerCode);
	}

	/**
	 * 清除缓存数据
	 * @throws UserException
	 */
	public static void clearTransfer() throws UserException {
		if (transferList != null || transferMap != null) {
			transferMap.clear();
			transferList = null;
		}
	}

	/**
	 * 初始化缓存数据
	 * @throws UserException
	 */
	@SuppressWarnings("unchecked")
	public static void initTransfer() throws UserException {
		transferList = null;
		transferMap = null;
		try {
			transferList = new UIUtiCodeTransferAction().findByConditions("validStatus=1");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "claim", "" + "請聯系系統管理員，進行險種在UICODETRANSFER表的初始化！");

		}
		if (transferList != null) {
			transferMap = new HashMap<String, UtiCodeTransferDto>();
			for (Iterator<UtiCodeTransferDto> iter = transferList.iterator(); iter.hasNext();) {
				UtiCodeTransferDto utiCodeTransferDto = (UtiCodeTransferDto) iter.next();
				transferMap.put(utiCodeTransferDto.getOuterCode(), utiCodeTransferDto);
			}
		}
	}

	/**
	 * 取得险种的代码
	 * @param policyNo 字段名
	 * @return riskCode 险种
	 */
	@SuppressWarnings("unchecked")
	public static String getRiskCode(String businessNo, String businessType) throws Exception {
		String riskCode = "";
		String condition = "";
		if (businessType.equals("PolicyNo")) {// -----------------------保单号
			EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
			PolicyDto policyDto = null;
			policyDto = endorseViewHelper.findForEndorBefore(businessNo);
			riskCode = policyDto.getPrpCmainDto().getRiskCode();
			return riskCode;
		} else if (businessType.equals("RegistNo")) {// -------------------报案号
			BLPrpLregistFacade blPrpLregistFacade = new BLPrpLregistFacade();
			PrpLregistDto prpLregistDto = new PrpLregistDto();
			prpLregistDto = blPrpLregistFacade.findByPrimaryKey(businessNo);
			riskCode = prpLregistDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("ClaimNo")) {// ------------------立案号
			BLPrpLclaimFacade blPrpLclaimFacade = new BLPrpLclaimFacade();
			PrpLclaimDto prpLclaimDto = new PrpLclaimDto();
			prpLclaimDto = blPrpLclaimFacade.findByPrimaryKey(businessNo);
			riskCode = prpLclaimDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("CompensateNo")) {// ----------------计算书号
			BLPrpLcompensateFacade blPrpLcompensateFacade = new BLPrpLcompensateFacade();
			PrpLcompensateDto prpLcompensateDto = new PrpLcompensateDto();
			ArrayList<PrpLcompensateDto> compensateList = new ArrayList<PrpLcompensateDto>();
			condition = "compensateno like '%" + businessNo + "%'";
			log.debug("----------------------------" + condition);
			compensateList = (ArrayList<PrpLcompensateDto>) blPrpLcompensateFacade.findByConditions(condition);
			log.debug("----------------------------" + compensateList.size());
			prpLcompensateDto = (PrpLcompensateDto) compensateList.get(0);
			riskCode = prpLcompensateDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("CaseNo")) {// -------------------结案号
			BLPrpLclaimFacade blPrpLclaimFacade = new BLPrpLclaimFacade();
			PrpLclaimDto prpLclaimDto = new PrpLclaimDto();
			ArrayList<PrpLclaimDto> claimList = new ArrayList<PrpLclaimDto>();
			condition = "caseno = '" + businessNo + "'";
			claimList = (ArrayList<PrpLclaimDto>) blPrpLclaimFacade.findByConditions(condition);
			prpLclaimDto = (PrpLclaimDto) claimList.get(0);
			riskCode = prpLclaimDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("ScheduleNo")) {// -----------------调度号
			// 目前，系统没有调度号，调度主表中没有调度号，报案号和调度序号为主键
			// 所以，调度号即为报案号
			BLPrpLregistFacade blPrpLregistFacade = new BLPrpLregistFacade();
			PrpLregistDto prpLregistDto = new PrpLregistDto();
			prpLregistDto = blPrpLregistFacade.findByPrimaryKey(businessNo);
			riskCode = prpLregistDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("CheckNo")) {// --------------------查勘号
			// 目前，系统没有查勘号，查勘主表中没有查勘号，主键为报案号和关联理赔车辆序号
			// 所以，查勘号即为报案号
			BLPrpLregistFacade blPrpLregistFacade = new BLPrpLregistFacade();
			PrpLregistDto prpLregistDto = new PrpLregistDto();
			prpLregistDto = blPrpLregistFacade.findByPrimaryKey(businessNo);
			riskCode = prpLregistDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("CertifyNo")) {// --------------------单证号
			// 目前系统没有单独的单证号，采用的是报案号
			BLPrpLregistFacade blPrpLregistFacade = new BLPrpLregistFacade();
			PrpLregistDto prpLregistDto = new PrpLregistDto();
			prpLregistDto = blPrpLregistFacade.findByPrimaryKey(businessNo);
			riskCode = prpLregistDto.getRiskCode();
			return riskCode;
		} else if (businessType.equals("PrepayNo")) {// --------------------预赔号
			// 目前系统没有预赔，估计，以後会有，这里预留接口
			UIPrepayAction uiPrepayAction = new UIPrepayAction();
			PrepayDto prepayDto = new PrepayDto();
			prepayDto = uiPrepayAction.findByPrimaryKey(businessNo);
			riskCode = prepayDto.getPrpLprepayDto().getRiskCode();
			return riskCode;
		}
		if (riskCode.equals("")) {
			throw new UserException(-6, -109, "getRiskCode", "獲取險種代碼失敗,請聯系系統管理員。");
		}
		return riskCode;
	}

	/**
	 * 取得UI的forward
	 * @param httpServletRequest httpServletRequest
	 * @param riskCode 险种
	 * @param nodeType 节点代码(regis,check等)
	 * @param editType 编辑类型(SHOW,EDIT,ADD,DELETE等)
	 * @param lossItemCode 标的号(0为人伤，－1为物损，车辆为1，2，3...等，不知道的时候填1)
	 * @return forward 向前
	 */
	public static String getForward(HttpServletRequest httpServletRequest, String riskCode, String nodeType, String editType, int lossItemCode) throws Exception {
		String forward = ""; // 向前
		// 用於区分是否从核心业务系统的调用
		if (httpServletRequest.getParameter("paramPrpallRegist") != null && httpServletRequest.getParameter("paramPrpallRegist").equals("DAA")) {
			riskCode = "DAA";
		} else if (httpServletRequest.getParameter("paramUndwrtCompe") != null && httpServletRequest.getParameter("paramUndwrtCompe").equals("DAA")) {
			riskCode = "DAA";
		} else {
			List<UtiCodeTransfer> utiCodeTransferList = getUtiCodeTransferService().findByConditions(" outercode='" + riskCode + "'");
			// 如果没有在转换表中查询到的险种，是不允许继续理算的，应该只能在增加了uitocodetransfer的表数据，再能进行操作。
			if (utiCodeTransferList == null || utiCodeTransferList.size() == 0) {
				throw new UserException(1, 3, "claim", "" + "請聯系系統管理員，進行險種'" + riskCode + "'在UICODETRANSFER表的初始化！");
			} else {
				UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
				riskCode = UtiCodeTransfer.getInnerCode();
			}
		}

		log.debug("---riskCode---getForward---" + riskCode);
		String riskCodeForward = "";// 从配置文件里面取得系统处理的险类的信息
		try {
			riskCodeForward = AppConfig.get("sysconst.RiskCodeForward");
			log.debug("***<@^@>***[riskCodeForward]==" + riskCodeForward);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (nodeType.equals("certa") || nodeType.equals("verip") || nodeType.equals("verpo") || nodeType.equals("verif")
				||"wound".equals(nodeType)||"veriw".equals(nodeType)||"propc".equals(nodeType)||"propv".equals(nodeType)) {
			if(lossItemCode>1){
				lossItemCode = 1;
			}
			if("certa".equals(nodeType)||"verif".equals(nodeType)){
				lossItemCode = 1;
			}else if("wound".equals(nodeType)||"veriw".equals(nodeType)){
				lossItemCode = 0;
			}else if("propc".equals(nodeType)||"propv".equals(nodeType)){
				lossItemCode = -1;
			}
			// 3。如果是SHOW类型的，目前和EDIT用同一个目的jsp所以
			if (editType.equals("SHOW") || editType.equals("DELETE") || editType.equals("TimeOut") || editType.equals("COPY")) {
				editType = "EDIT";
			}
			// 4。如果不是DAA类别的，目前都归到"Prop"类型里，主要是财产险
			if ((riskCode == null) || (riskCode.length() < 1)){
				riskCode = "DAA";
			}
			String subRiskCode = riskCode.substring(0, 1);
			if ((riskCodeForward.indexOf(subRiskCode)) >= 0) {
				forward = editType + lossItemCode + subRiskCode + "AA";
			} else {
				forward = editType + "Prop";
			}
		} else {
			// 3。如果是SHOW类型的，目前和EDIT用同一个目的jsp所以
			if (editType.equals("SHOW") || editType.equals("DELETE") || editType.equals("TimeOut") || editType.equals("COPY")) {
				editType = "EDIT";
			}
			// 4。如果不是DAA类别的，目前都归到"Prop"类型里，主要是财产险
			if ((riskCode == null) || (riskCode.length() < 1))
				riskCode = "DAA";
			String subRiskCode = riskCode.substring(0, 1);

			log.debug("--riskCodeForward---=" + riskCodeForward + "-subRiskCode--=" + subRiskCode + "--是否存在的标志，如果大於等於0则存在--=" + riskCodeForward.indexOf(subRiskCode));
			if ((riskCodeForward.indexOf(subRiskCode)) >= 0) {
				forward = editType + subRiskCode + "AA";
				log.debug("forword==" + forward);
			} else {
				forward = editType + "Prop";
				log.debug("forword==Prop");
			}
		}
		log.debug("-产生的-getForward----=" + forward);
		return forward;
	}

	/**
	 * 取得UI的forward
	 * @param nodeType 节点类型
	 * @return iTaskCode taskCode
	 */
	public static String transTaskCode(String nodeType) {
		String iTaskCode = "";
		if ("regis".equals(nodeType)) {
			iTaskCode = "lpba";
		} else if ("sched".equals(nodeType)) {
			iTaskCode = "lpdd";
		} else if ("check".equals(nodeType)) {
			iTaskCode = "lpck";
		} else if ("certa".equals(nodeType)) {
			iTaskCode = "lpds";
		} else if ("claim".equals(nodeType)) {
			iTaskCode = "lpla";
		} else if ("quote".equals(nodeType)) {
			iTaskCode = "lpbj";
		} else if ("verip".equals(nodeType) || "verpo".equals(nodeType)) {
			iTaskCode = "lphj";
		} else if ("verif".equals(nodeType)) {
			iTaskCode = "lphs";
		} else if ("wound".equals(nodeType)) {
			iTaskCode = "lprd";
		} else if ("veriw".equals(nodeType)) {
			iTaskCode = "lprh";
		} else if ("certi".equals(nodeType)) {
			iTaskCode = "lpdz";
		} else if ("prepa".equals(nodeType)) {
			iTaskCode = "lppa";
		} else if ("compe".equals(nodeType)) {
			iTaskCode = "lppa";
		} else if ("compp".equals(nodeType)) {
			iTaskCode = "lppa";
		} else if ("speci".equals(nodeType)) {
			iTaskCode = "lpts";
		} else if ("right".equals(nodeType)) {
			iTaskCode = "lpzc";
		} else if ("endca".equals(nodeType)) {
			iTaskCode = "lpja";
		} else if ("cance".equals(nodeType)) {
			iTaskCode = "lpzx";
		} else if ("veric".equals(nodeType)) {
			iTaskCode = "lphp";
		} else if ("veric".equals(nodeType)) {
			iTaskCode = "lphp";
		} else if ("propc".equals(nodeType)) {
			iTaskCode = "lpcd";
		} else if ("propv".equals(nodeType)) {
			iTaskCode = "lpch";
		} else if ("backc".equals(nodeType)) {
			iTaskCode = "lpxy";
		} else if ("backv".equals(nodeType)) {
			iTaskCode = "lphf";
		}
		return iTaskCode;
	}

	/**
	 * 根据ConfigCode从险种对照表取得外码
	 * @param configCode 字段名
	 * @return riskCode 险种
	 */
	public static String getOuterCode(HttpServletRequest httpServletRequest, String configCode) throws Exception {
		String riskCode = "";
		// 测试险种代码转换
		ArrayList<?> transferList = (ArrayList<?>) httpServletRequest.getSession().getAttribute("transferList");

		if (transferList == null) { // 考虑理赔系统被其他系统调用，需要初始化一些信息。
			UIUtiCodeTransferAction uiUtiCodeTransferAction = new UIUtiCodeTransferAction();
			transferList = (ArrayList<?>) uiUtiCodeTransferAction.findByConditions(" 1=1");
		}

		for (int i = 0; i < transferList.size(); i++) {
			UtiCodeTransferDto utiCodeTransferDto = (UtiCodeTransferDto) transferList.get(i);
			if (configCode.equals(utiCodeTransferDto.getConfigCode())) {
				riskCode = utiCodeTransferDto.getOuterCode();
				break;
			}
		}

		return riskCode;
	}

	/**
	 * 根据kindCode从判断是不是这个类别的险别
	 * @param kindType 险别类型 （可用参数 MainCarLoss:主标的车辆损失， ThirdLoss(包括人车物)，
	 *            ThirdCarLoss:三者车 ThirdPropLoss:三者物 ThirdPersonLoss:三者人）
	 * @param kindCode 险别
	 * @return boolean
	 */
	public static boolean checkKindType(String kindType, String kindCode) throws Exception {
		boolean blReturn = true;
		// 测试险种代码转换
		// MainCarLoss:主标的车辆损失，
		if ("MainCarLoss".equals(kindType)) {// :主标的车辆损失，
			return ConstantsCollection.MainCarLoss.contains(kindCode);
		} else 
		if ("ThirdLoss".equals(kindType)) {// :(包括人车物)，
			return ConstantsCollection.MainPersonLoss.contains(kindCode)||ConstantsCollection.MainPropLoss.contains(kindCode)||ConstantsCollection.InsAnddriver.contains(kindCode);
		} else 
		if ("ThirdCarLoss".equals(kindType)) {// :三者车
			return ConstantsCollection.ThirdCarLoss.contains(kindCode);
		} else 
		if ("ThirdPropLoss".equals(kindType)) {// :三者物，
			return ConstantsCollection.ThirdPropLoss.contains(kindCode);
		} else 
		if ("ThirdPersonLoss".equals(kindType)) {// :三者人，
			return ConstantsCollection.ThirdPersonLoss.contains(kindCode)||ConstantsCollection.InsAnddriver.contains(kindCode);
		} else 
		if ("MainPersonLoss".equals(kindType)) {// :(包括人车物)，
			return ConstantsCollection.MainPersonLoss.contains(kindCode)||ConstantsCollection.InsAnddriver.contains(kindCode);
		}
		return blReturn;
	}

	/**
	 * 根据kindType整理kindItem列表
	 * @param kindType 险别类型 （可用参数 MainCarLoss:主标的车辆损失， ThirdLoss(包括人车物)，
	 *            ThirdCarLoss:三者车 ThirdPropLoss:三者物 ThirdPersonLoss:三者人）
	 * @param kindCode 险别
	 * @return boolean
	 */
	public static List<PrpCitemKind> getReferItemKindListByKindType(String kindType, List<?> itemKindList) throws Exception {
		List<PrpCitemKind> itemKindListNew = new ArrayList<PrpCitemKind>();
		// 测试险种代码转换
		// MainCarLoss:主标的车辆损失，
		if (kindType == null)
			return itemKindListNew;
		if (itemKindList == null)
			return itemKindListNew;

		for (int i = 0; i < itemKindList.size(); i++) {
			PrpCitemKind prpCitemKind1 = new PrpCitemKind(); // 原来的写法出现：A-A-A-车损险
			// 这种情况
			PrpCitemKind prpCitemKind = (PrpCitemKind) itemKindList.get(i);
			prpCitemKind1.getId().setPolicyNo(prpCitemKind.getId().getPolicyNo());
			prpCitemKind1.setRiskCode(prpCitemKind.getRiskCode());
			prpCitemKind1.setKindCode(prpCitemKind.getKindCode());
			prpCitemKind1.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());

			if (checkKindType(kindType, prpCitemKind1.getKindCode())) {
				itemKindListNew.add(prpCitemKind1);
			}

		}
		return itemKindListNew;
	}

	/**
	 * 获取用户的权限
	 * @param userDto 用户对象
	 * @return
	 * @throws Exception
	 */
	public static String getConditions(UserDto userDto) throws Exception {
		String permitRiskCode = "";
		String conditions = "";
		try {
			Collection<UtiUserGradePowerDto> collectionTemp = new BLUtiUserGradePowerFacade().findByConditions(" usercode='" + userDto.getUserCode() + "'");
			if (collectionTemp != null && collectionTemp.size() > 0) {
				Iterator<UtiUserGradePowerDto> iterator = collectionTemp.iterator();
				if (iterator.hasNext()) {
					UtiUserGradePowerDto utiUserGradePowerDto = iterator.next();
					permitRiskCode = utiUserGradePowerDto.getPermitRiskCode();
				}
			}
			StringBuffer sb = new StringBuffer("(");
			if (!"*".equals(permitRiskCode) && !"".equals(permitRiskCode) && permitRiskCode.length() > 0) {
				String[] strs = permitRiskCode.split(",");
				for (String strRisk : strs) {
					sb.append("'" + strRisk + "',");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(")");
				conditions = sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conditions;
	}

	/**
	 * 是否是个险判断
	 * @param riskCode 险种
	 * @return
	 * @throws Exception
	 */
	public static boolean getGXFlag(String riskCode) throws Exception {
		boolean flag = false;
		try {
			PrpDcodeDto prpDcodeDto = new BLPrpDcodeFacade().findByPrimaryKey("SendUndwrtRisk", "GRBX");
			if (prpDcodeDto != null) {
				String riskCodes = prpDcodeDto.getCodeCName();
				if (DataUtils.emptyToNull(riskCodes) != null && riskCodes.indexOf(riskCode) > -1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据节点类型和当前用户判断能处理的险种权限
	 * @param userDto 用户
	 * @param nodeType 节点类型
	 * @return
	 * @throws Exception
	 */
	public static boolean getRiskFlag(UserDto userDto, String nodeType) throws Exception {
		DBManager dbManager = new DBManager();
		Collection<UtiGradeTaskDto> collection = null;
		boolean flag = false;
		String permitCode = "";
		StringBuffer sb = new StringBuffer("");
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			dbManager.beginTransaction();
			// 插入记录
			collection = new BLUtiGradeTaskAction().findByConditions(dbManager, "taskcode  like 'claim." + nodeType + "%.insert'");
			dbManager.commitTransaction();
		} catch (Exception exception) {
			dbManager.rollbackTransaction();
			throw exception;
		} finally {
			dbManager.close();
		}
		if (collection != null && collection.size() > 0) {
			Iterator<UtiGradeTaskDto> iterator = collection.iterator();
			while (iterator.hasNext()) {
				UtiGradeTaskDto utiGradeTaskDto = iterator.next();
				sb.append("'").append(utiGradeTaskDto.getGradeCode()).append("',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb.length() > 0) {
			Collection<UtiUserGradePowerDto> collection2 = new BLUtiUserGradePowerFacade().findByConditions(" usercode='" + userDto.getUserCode() + "' and gradecode in (" + sb.toString() + ")");
			if (collection2 != null && collection2.size() > 0) {
				Iterator<UtiUserGradePowerDto> iterator = collection2.iterator();
				if (iterator.hasNext()) {// 取一条就够了
					permitCode = iterator.next().getPermitRiskCode();
				}
			}

			if ("*".equals(permitCode)) {
				flag = true;
			} else if (permitCode != null && !"".equals(permitCode)) {
				String[] permitCodes = permitCode.split(",");
				if (permitCodes != null && permitCodes.length > 0) {
					for (String riskCode : permitCodes) {
						flag = BusinessRuleUtil.getGXFlag(riskCode);
						if (flag) {
							break;
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 根据节点类型和当前用户判断能处理的险种权限
	 * @param userDto 用户
	 * @param nodeType 节点类型
	 * @return
	 * @throws Exception
	 */
	public static String getPermitGXRisks(UserDto userDto, String nodeType) throws Exception {
		DBManager dbManager = new DBManager();
		Collection<UtiGradeTaskDto> collection = null;
		String permitGXRisks = "";
		String permitCode = "";
		StringBuffer sb = new StringBuffer("");
		StringBuffer buffer = new StringBuffer("");
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			dbManager.beginTransaction();
			// 插入记录
			collection = new BLUtiGradeTaskAction().findByConditions(dbManager, "taskcode  like 'claim." + nodeType + "%.insert'");
			dbManager.commitTransaction();
		} catch (Exception exception) {
			dbManager.rollbackTransaction();
			throw exception;
		} finally {
			dbManager.close();
		}
		if (collection != null && collection.size() > 0) {
			Iterator<UtiGradeTaskDto> iterator = collection.iterator();
			while (iterator.hasNext()) {
				UtiGradeTaskDto utiGradeTaskDto = iterator.next();
				sb.append("'").append(utiGradeTaskDto.getGradeCode()).append("',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb.length() > 0) {
			Collection<UtiUserGradePowerDto> collection2 = new BLUtiUserGradePowerFacade().findByConditions(" usercode='" + userDto.getUserCode() + "' and gradecode in (" + sb.toString() + ")");
			if (collection2 != null && collection2.size() > 0) {
				Iterator<UtiUserGradePowerDto> iterator = collection2.iterator();
				if (iterator.hasNext()) {// 取一条就够了
					permitCode = iterator.next().getPermitRiskCode();
				}
			}

			if ("*".equals(permitCode)) {
				BLPrpDrisk blPrpDrisk = new BLPrpDrisk();
				blPrpDrisk.query(" validstatus='1'");
				for (int i = 0; i < blPrpDrisk.getSize(); i++) {
					boolean flag = BusinessRuleUtil.getGXFlag(blPrpDrisk.getArr(i).getRiskCode());// 筛选出个险
					if (flag) {
						buffer.append(blPrpDrisk.getArr(i).getRiskCode()).append(",");
					}
				}
				if (buffer.length() > 0) {
					buffer.deleteCharAt(buffer.length() - 1);
				}
				permitGXRisks = buffer.toString();

			} else if (permitCode != null && !"".equals(permitCode)) {
				String[] permitCodes = permitCode.split(",");
				if (permitCodes != null && permitCodes.length > 0) {
					for (String riskCode : permitCodes) {
						boolean flag = BusinessRuleUtil.getGXFlag(riskCode);
						if (flag) {
							buffer.append("'").append(riskCode).append("',");
						}
					}
					if (buffer.length() > 0) {
						buffer.deleteCharAt(buffer.length() - 1);
					}
					permitGXRisks = buffer.toString();
				}
			}
		}
		return permitGXRisks;
	}

	/**
	 * 企业险险种范围
	 * @param userDto 用户
	 * @param nodeType 节点类型
	 * @return
	 * @throws Exception
	 */
	public static String getPermitQXRisks(UserDto userDto, String nodeType) throws Exception {
		DBManager dbManager = new DBManager();
		Collection<UtiGradeTaskDto> collection = null;
		String permitQXRisks = "";
		String permitCode = "";
		StringBuffer sb = new StringBuffer("");
		StringBuffer buffer = new StringBuffer("");
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			dbManager.beginTransaction();
			// 插入记录
			collection = new BLUtiGradeTaskAction().findByConditions(dbManager, "taskcode  like 'claim." + nodeType + "%.insert'");
			dbManager.commitTransaction();
		} catch (Exception exception) {
			dbManager.rollbackTransaction();
			throw exception;
		} finally {
			dbManager.close();
		}
		if (collection != null && collection.size() > 0) {
			Iterator<UtiGradeTaskDto> iterator = collection.iterator();
			while (iterator.hasNext()) {
				UtiGradeTaskDto utiGradeTaskDto = iterator.next();
				sb.append("'").append(utiGradeTaskDto.getGradeCode()).append("',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb.length() > 0) {
			Collection<UtiUserGradePowerDto> collection2 = new BLUtiUserGradePowerFacade().findByConditions(" usercode='" + userDto.getUserCode() + "' and gradecode in (" + sb.toString() + ")");
			if (collection2 != null && collection2.size() > 0) {
				Iterator<UtiUserGradePowerDto> iterator = collection2.iterator();
				if (iterator.hasNext()) {// 取一条就够了
					permitCode = iterator.next().getPermitRiskCode();
				}
			}

			if ("*".equals(permitCode)) {
				BLPrpDrisk blPrpDrisk = new BLPrpDrisk();
				blPrpDrisk.query(" validstatus='1'");
				for (int i = 0; i < blPrpDrisk.getSize(); i++) {
					boolean flag = BusinessRuleUtil.getGXFlag(blPrpDrisk.getArr(i).getRiskCode());// 筛选出企险
					if (!flag) {
						buffer.append(blPrpDrisk.getArr(i).getRiskCode()).append(",");
					}
				}
				if (buffer.length() > 0) {
					buffer.deleteCharAt(buffer.length() - 1);
				}
				permitQXRisks = buffer.toString();

			} else if (permitCode != null && !"".equals(permitCode)) {
				String[] permitCodes = permitCode.split(",");
				if (permitCodes != null && permitCodes.length > 0) {
					for (String riskCode : permitCodes) {
						boolean flag = BusinessRuleUtil.getGXFlag(riskCode);
						if (!flag) {
							buffer.append("'").append(riskCode).append("',");
						}
					}
					if (buffer.length() > 0) {
						buffer.deleteCharAt(buffer.length() - 1);
					}
					permitQXRisks = buffer.toString();
				}
			}
		}
		return permitQXRisks;
	}

	public static UtiCodeTransferService getUtiCodeTransferService() {
		if(utiCodeTransferService==null){
			utiCodeTransferService = (UtiCodeTransferService) ServiceFactory.getService("utiCodeTransferService");
		}
		return utiCodeTransferService;
	}

}
