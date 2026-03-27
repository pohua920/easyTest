package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.common.schema.model.PrpCPfee;
import com.sinosoft.common.schema.model.PrpCfee;
import com.sinosoft.common.schema.model.PrpCplan;
import com.sinosoft.common.schema.model.PrpPplan;
import com.sinosoft.common.schema.model.PrpTfee;
import com.sinosoft.common.schema.model.PrpTplan;
import com.sinosoft.prpall.dto.domain.PrpCPfeeDto;
import com.sinosoft.prpall.dto.domain.PrpCcoinsDto;
import com.sinosoft.prpall.dto.domain.PrpCfeeDto;
import com.sinosoft.prpall.dto.domain.PrpCplanDto;
import com.sinosoft.prpall.dto.domain.PrpPplanDto;
import com.sinosoft.prpall.dto.domain.PrpTcoinsDto;
import com.sinosoft.prpall.dto.domain.PrpTfeeDto;
import com.sinosoft.prpall.dto.domain.PrpTplanDto;
import com.sinosoft.reins.base.service.facade.BLFhTreatyService;
import com.sinosoft.reins.base.service.facade.FhTreatyService;
import com.sinosoft.reins.base.vo.FhTreatyVO;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.reins.common.vo.PrpCDangerCoinsVO;
import com.sinosoft.reins.common.vo.PrpCDangerItemVO;
import com.sinosoft.reins.common.vo.PrpCDangerPlanVO;
import com.sinosoft.reins.common.vo.PrpCDangerTotVO;
import com.sinosoft.reins.common.vo.PrpCDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpPDangerCoinsVO;
import com.sinosoft.reins.common.vo.PrpPDangerItemVO;
import com.sinosoft.reins.common.vo.PrpPDangerPlanVO;
import com.sinosoft.reins.common.vo.PrpPDangerTotVO;
import com.sinosoft.reins.common.vo.PrpPDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpTDangerCoinsVO;
import com.sinosoft.reins.common.vo.PrpTDangerItemVO;
import com.sinosoft.reins.common.vo.PrpTDangerPlanVO;
import com.sinosoft.reins.common.vo.PrpTDangerTotVO;
import com.sinosoft.reins.common.vo.PrpTDangerUnitVO;
import com.sinosoft.reins.common.service.facade.PrpLReinsTrialService;
import com.sinosoft.reins.common.model.PrpCDangerCoins;
import com.sinosoft.reins.common.model.PrpCDangerCoinsId;
import com.sinosoft.reins.common.model.PrpCDangerItem;
import com.sinosoft.reins.common.model.PrpCDangerItemId;
import com.sinosoft.reins.common.model.PrpCDangerPlan;
import com.sinosoft.reins.common.model.PrpCDangerPlanId;
import com.sinosoft.reins.common.model.PrpCDangerTot;
import com.sinosoft.reins.common.model.PrpCDangerTotId;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.reins.common.model.PrpLReinsTrial;
import com.sinosoft.reins.common.model.PrpPDangerCoins;
import com.sinosoft.reins.common.model.PrpPDangerCoinsId;
import com.sinosoft.reins.common.model.PrpPDangerItem;
import com.sinosoft.reins.common.model.PrpPDangerItemId;
import com.sinosoft.reins.common.model.PrpPDangerPlan;
import com.sinosoft.reins.common.model.PrpPDangerPlanId;
import com.sinosoft.reins.common.model.PrpPDangerTot;
import com.sinosoft.reins.common.model.PrpPDangerTotId;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnitId;
import com.sinosoft.reins.common.model.PrpTDangerCoins;
import com.sinosoft.reins.common.model.PrpTDangerCoinsId;
import com.sinosoft.reins.common.model.PrpTDangerItem;
import com.sinosoft.reins.common.model.PrpTDangerItemId;
import com.sinosoft.reins.common.model.PrpTDangerPlan;
import com.sinosoft.reins.common.model.PrpTDangerPlanId;
import com.sinosoft.reins.common.model.PrpTDangerTot;
import com.sinosoft.reins.common.model.PrpTDangerTotId;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnitId;
import com.sinosoft.reins.product.code.service.facade.BLReinsService;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ReinsService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.vo.CommonDangerUnitSerialNoVo;
import com.sinosoft.undwrt.undwrtDeal.vo.DangerExItemKindVo;
import com.sinosoft.undwrt.undwrtDeal.vo.ReinsTrialDangerInfoVo;

/**
 * 危險單位信息服務實現類.
 */
public class CommonDangerInfoServiceSpringImpl extends GenericDaoHibernate
		implements CommonDangerInfoService {

	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;

	/** 屬性再保服務接口. */
	private ReinsService reinsService;

	/** 屬性合約主信息處理接口. */
	private BLFhTreatyService blFhTreatyService;

	/** 屬性賠案的分攤試算信息接口. */
	private PrpLReinsTrialService prpLReinsTrialService;

	/** 屬性再保接口業務處理接口. */
	private BLReinsService blReinsService;

	/**
	 * 取除外標的訊息.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return the danger ex item kind
	 * @throws Exception
	 *             異常
	 */
	@Override
	public Collection getDangerExItemKind(String riskCode) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statementStr = null;
		DangerExItemKindVo dangerExItemKindDto = null;
		List list = null;
		// modify by liuahqi begin 20061031 删除了order by codecode
		// 之desc,风险评估中的除外责任和申报业务要求还正序排序
		statementStr = "select codecode, codecname from prpdcode "
				+ "where  codecode in "
				+ "(select codecode from prpdcoderisk where codetype='ItemKind' "
				+ "and riskcode='" + riskCode + "') and codetype='ItemKind'"
				+ " Order by codecode";
		// modify by liuahqi end 20061031 删除了order by codecode 之desc
		list = super.getSession().createSQLQuery(statementStr).list();
		Iterator it = list.iterator();
		Collection collection = new ArrayList();
		while (it.hasNext()) {
			try {
				Object[] obj = (Object[]) it.next();
				dangerExItemKindDto = new DangerExItemKindVo();
				dangerExItemKindDto.setItemCode((String) obj[0]);
				dangerExItemKindDto.setItemName((String) obj[1]);
				dangerExItemKindDto.setAllItemKind((String) obj[1]);
				collection.add(dangerExItemKindDto);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(
						internal.getText("undwrt.action.commonDangerRisk.queryDataError"));
			}
		}
		return collection;
	}

	/**
	 * 取自留額訊息.
	 * 
	 * @param strConditon
	 *            查詢條件
	 * @return 自留額訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getRetenValue(java.lang.String)
	 */
	@Override
	public Collection getRetenValue(String strConditon) throws Exception {

		return blReinsService.getFhRetenValue(strConditon);

	}

	/**
	 * 獲取危險單位訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 危險單位訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerUnit(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Collection getDangerUnit(HttpServletRequest req) throws Exception {
		Collection dangerUnitDtoList = new ArrayList();
		ParamUtils paramUtils = new ParamUtils(req);
		String businessNo = paramUtils.getParameter("businessNo");
		String businessType = paramUtils.getParameter("hiBusinessType");
		String policyNo = paramUtils.getParameter("policyNo");
		String dangerNo = paramUtils.getParameter("hiDangerNo");
		String dangerDesc[] = paramUtils.getParameterValues("dangerDesc");
		String dangerAddress[] = paramUtils.getParameterValues("dangerAddress"); //
		String dangerItemKind[] = paramUtils.getParameterValues("itemKind");//
		String dangerItemFlag[] = paramUtils
				.getParameterValues("hiDangerItemFlag"); //
		String riskLevel[] = paramUtils.getParameterValues("riskLevel");
		String riskLevelDesc[] = paramUtils.getParameterValues("riskLevelDesc");
		String retCurrency[] = paramUtils.getParameterValues("retCurrency"); //
		String retentionValue[] = paramUtils
				.getParameterValues("retentionValue"); //
		String currency[] = paramUtils.getParameterValues("currency");
		String amount[] = paramUtils.getParameterValues("amount");
		String chgAmount[] = paramUtils.getParameterValues("chgAmount");
		String chgPremium[] = paramUtils.getParameterValues("chgPremium");
		String premium[] = paramUtils.getParameterValues("premium");
		String dangerShare[] = paramUtils.getParameterValues("dangerShare"); //
		String riskCode[] = paramUtils.getParameterValues("riskCode");
		String classCode = paramUtils.getParameter("classCode");
		String speValue[] = paramUtils.getParameterValues("speValue");
		String speCurrency[] = paramUtils.getParameterValues("speCurrency");
		String dangerCoinsFlag[] = paramUtils
				.getParameterValues("hiDangerCoinsFlag");
		String dangerShareHolderFlag[] = req
				.getParameterValues("hiDangerShareHolderFlag");
		String dangerBusinessFlag[] = req
				.getParameterValues("hiDangerBusinessFlag");
		// modify by subeite reason:rebuild sap 20080928 start
		String businessNature[] = req
				.getParameterValues("hiDangerBusinessNature");
		String channelType[] = paramUtils
				.getParameterValues("hiDangerChannelType");
		String cartypeCode[] = paramUtils
				.getParameterValues("hiDangerCartypeCode");
		String exchRateCNY[] = paramUtils
				.getParameterValues("hiDangerExchRateCNY");
		// modify by subeite reason:rebuild sap 20080928 start
		// modify begin 2008-03-12 by lihua 风险类别赋值
		String riskClass[] = paramUtils.getParameterValues("riskClass");
		String riskClassDesc[] = paramUtils.getParameterValues("riskClassDesc");
		String sameRiskNo[] = paramUtils.getParameterValues("sameRiskNo");
		// modify end 2008-03-12 by lihua 风险类别赋值
		if (businessType.equals("T")) {
			PrpTDangerUnitVO prpTdangerUnitDto = new PrpTDangerUnitVO();
			prpTdangerUnitDto.setProposalNo(businessNo);
			prpTdangerUnitDto.setDangerNo(Integer.parseInt(dangerNo));
			for(int i=1;i<sameRiskNo.length;i++)
			{
				if(!"".equals(sameRiskNo[i]))
				{
					prpTdangerUnitDto.setSameRiskNo(sameRiskNo[i]);
					break;
				}
			}
			
			prpTdangerUnitDto.setRiskCode(riskCode[1]);
			prpTdangerUnitDto.setDangerDesc(dangerDesc[1]);
			prpTdangerUnitDto.setAddressName(dangerAddress[1]);
			if (dangerItemKind == null || dangerItemKind.equals("")) {
				prpTdangerUnitDto.setItemKind("");
			} else if (dangerItemKind.length > 1) {
				prpTdangerUnitDto.setItemKind(dangerItemKind[1]);
			} else {
				prpTdangerUnitDto.setItemKind(dangerItemKind[0]);
			}
			prpTdangerUnitDto.setRiskLevel(riskLevel[1]);
			prpTdangerUnitDto.setRiskLevelDesc(riskLevelDesc[1]);
			prpTdangerUnitDto.setRiskClass(riskClass[1]);
			prpTdangerUnitDto.setRiskClassDesc(riskClassDesc[1]);
			prpTdangerUnitDto.setRetCurrency(retCurrency[1]);
			if (retentionValue[1] != null && !retentionValue[1].equals("")) {
				prpTdangerUnitDto.setRetentionValue(Double
						.parseDouble(retentionValue[1].replaceAll(",", "")));
			}
			prpTdangerUnitDto.setCurrency(currency[1]);
			prpTdangerUnitDto.setAmount(Double.parseDouble(amount[1]
					.replaceAll(",", "")));
			prpTdangerUnitDto.setPremium(Double.parseDouble(premium[1]
					.replaceAll(",", "")));
			prpTdangerUnitDto.setDisFee(0.00);
			prpTdangerUnitDto.setDangerShare(Double.parseDouble(dangerShare[1]
					.replaceAll(",", "")));
			prpTdangerUnitDto.setCoinsFlag(dangerCoinsFlag[1]);
			prpTdangerUnitDto.setShareHolderFlag(dangerShareHolderFlag[1]);
			prpTdangerUnitDto.setBusinessFlag(dangerBusinessFlag[1]);
			prpTdangerUnitDto.setBusinessNature(businessNature[1]);
			prpTdangerUnitDto.setChannelType(channelType[1]);
			prpTdangerUnitDto.setCartypeCode(cartypeCode[1]);
			prpTdangerUnitDto.setExchRateCNY(Double.parseDouble(exchRateCNY[1]
					.replaceAll(",", "")));

			if (classCode != null
					&& (classCode.equals("27") || classCode.equals("15"))
					|| riskCode[1].equals("1107") || riskCode[1].equals("1001")
					|| riskCode[1].equals("2201") || riskCode[1].equals("0109")
					|| riskCode[1].equals("0907") || riskCode[1].equals("2729")) {
				prpTdangerUnitDto.setSpeCurrency(speCurrency[1]);
				if (speValue[1] == null || speValue[1].equals("")) {
					prpTdangerUnitDto.setSpeValue(0.0);
				} else {
					System.out.println(speValue[1].replaceAll(",", ""));
					prpTdangerUnitDto.setSpeValue(Double
							.parseDouble(speValue[1].replaceAll(",", "")));
				}
			}
			prpTdangerUnitDto.setFlag(dangerItemFlag[1]);

			dangerUnitDtoList.add(prpTdangerUnitDto);
		} else if (businessType.equals("P")) {
			PrpCDangerUnitVO prpCdangerUnitDto = new PrpCDangerUnitVO();
			prpCdangerUnitDto.setPolicyNo(businessNo);
			prpCdangerUnitDto.setDangerNo(Integer.parseInt(dangerNo));
			prpCdangerUnitDto.setRiskCode(riskCode[1]);
			prpCdangerUnitDto.setDangerDesc(dangerDesc[1]);
			prpCdangerUnitDto.setAddressName(dangerAddress[1]);

			if (dangerItemKind == null || dangerItemKind.equals("")) {
				prpCdangerUnitDto.setItemKind("");
			} else if (dangerItemKind.length > 1) {
				prpCdangerUnitDto.setItemKind(dangerItemKind[1]);
			} else {
				prpCdangerUnitDto.setItemKind(dangerItemKind[0]);
			}
			prpCdangerUnitDto.setRiskLevel(riskLevel[1]);
			prpCdangerUnitDto.setRiskLevelDesc(riskLevelDesc[1]);
			prpCdangerUnitDto.setRiskClass(riskClass[1]);
			prpCdangerUnitDto.setRiskClassDesc(riskClassDesc[1]);
			prpCdangerUnitDto.setRetCurrency(retCurrency[1]);
			if (retentionValue[1] != null && !retentionValue[1].equals("")) {
				prpCdangerUnitDto.setRetentionValue(Double
						.parseDouble(retentionValue[1].replaceAll(",", "")));
			}
			prpCdangerUnitDto.setCurrency(currency[1]);
			prpCdangerUnitDto.setAmount(Double.parseDouble(amount[1]
					.replaceAll(",", "")));
			prpCdangerUnitDto.setPremium(Double.parseDouble(premium[1]
					.replaceAll(",", "")));
			prpCdangerUnitDto.setDisFee(0.00);
			prpCdangerUnitDto.setDangerShare(Double.parseDouble(dangerShare[1]
					.replaceAll(",", "")));
			prpCdangerUnitDto.setCoinsFlag(dangerCoinsFlag[1]);
			prpCdangerUnitDto.setShareHolderFlag(dangerShareHolderFlag[1]);
			prpCdangerUnitDto.setBusinessFlag(dangerBusinessFlag[1]);

			if (classCode != null
					&& (classCode.equals("27") || classCode.equals("15"))
					|| riskCode[1].equals("1107") || riskCode[1].equals("1001")
					|| riskCode[1].equals("2201") || riskCode[1].equals("0907")
					|| riskCode[1].equals("0109") || riskCode[1].equals("2729")) {
				prpCdangerUnitDto.setSpeCurrency(speCurrency[1]);
				if (speValue[1] == null || speValue[1].equals("")) {
					prpCdangerUnitDto.setSpeValue(0.0);
				} else {
					prpCdangerUnitDto.setSpeValue(Double
							.parseDouble(speValue[1].replaceAll(",", "")));
				}
			}
			prpCdangerUnitDto.setReinsureFlag("0");
			prpCdangerUnitDto.setFlag(dangerItemFlag[1]);

			dangerUnitDtoList.add(prpCdangerUnitDto);
		} else if (businessType.equals("E")) {
			PrpPDangerUnitVO prpPdangerUnitDto = new PrpPDangerUnitVO();
			prpPdangerUnitDto.setEndorseNo(businessNo);
			prpPdangerUnitDto.setDangerNo(Integer.parseInt(dangerNo));
			prpPdangerUnitDto.setPolicyNo(policyNo);
			prpPdangerUnitDto.setSameRiskNo(sameRiskNo[1]);
			prpPdangerUnitDto.setRiskCode(riskCode[1]);
			prpPdangerUnitDto.setDangerDesc(dangerDesc[1]);
			prpPdangerUnitDto.setAddressName(dangerAddress[1]);
			// modify by subeite, reason:rebuild sap, start 20081005
			prpPdangerUnitDto.setBusinessNature(businessNature[1]);
			prpPdangerUnitDto.setChannelType(channelType[1]);
			prpPdangerUnitDto.setCartypeCode(cartypeCode[1]);
			prpPdangerUnitDto
					.setExchRateCNY(Double.parseDouble(exchRateCNY[1]));
			// modify by subeite, reason:rebuild sap, end 20081005

			if (dangerItemKind == null || dangerItemKind.equals("")) {
				prpPdangerUnitDto.setItemKind("");
			} else if (dangerItemKind.length > 1) {
				prpPdangerUnitDto.setItemKind(dangerItemKind[1]);
			} else {
				prpPdangerUnitDto.setItemKind(dangerItemKind[0]);
			}
			prpPdangerUnitDto.setRiskLevel(riskLevel[1]);
			prpPdangerUnitDto.setRiskLevelDesc(riskLevelDesc[1]);
			prpPdangerUnitDto.setRiskClass(riskClass[1]);
			prpPdangerUnitDto.setRiskClassDesc(riskClassDesc[1]);
			prpPdangerUnitDto.setRetCurrency(retCurrency[1]);
			if (retentionValue[1] != null && !retentionValue[1].equals("")) {
				prpPdangerUnitDto.setRetentionValue(Double
						.parseDouble(retentionValue[1].replaceAll(",", "")));
			}

			prpPdangerUnitDto.setCurrency(currency[1]);

			prpPdangerUnitDto.setAmount(Double.parseDouble(amount[1]
					.replaceAll(",", "")));

			prpPdangerUnitDto.setChgAmount(Double.parseDouble(chgAmount[1]
					.replaceAll(",", "")));

			prpPdangerUnitDto.setPremium(Double.parseDouble(premium[1]
					.replaceAll(",", "")));

			prpPdangerUnitDto.setChgPremium(Double.parseDouble(chgPremium[1]
					.replaceAll(",", "")));

			prpPdangerUnitDto.setDisFee(0.00);
			prpPdangerUnitDto.setChgDisFee(0.00);
			prpPdangerUnitDto
					.setDangerShare(Double.parseDouble(dangerShare[1]));
			prpPdangerUnitDto.setCoinsFlag(dangerCoinsFlag[1]);
			prpPdangerUnitDto.setShareHolderFlag("0");
			if (classCode != null
					&& (classCode.equals("27") || classCode.equals("15"))
					|| riskCode[1].equals("1107") || riskCode[1].equals("1001")
					|| riskCode[1].equals("2201") || riskCode[1].equals("0109")
					|| riskCode[1].equals("0907") || riskCode[1].equals("2729")) {
				prpPdangerUnitDto.setSpeCurrency(speCurrency[1]);
				if (speValue[1] == null || speValue[1].equals("")) {
					prpPdangerUnitDto.setSpeValue(0.0);
				} else {
					prpPdangerUnitDto.setSpeValue(Double
							.parseDouble(speValue[1].replaceAll(",", "")));
				}
			}

			prpPdangerUnitDto.setReinsureFlag("0");
			prpPdangerUnitDto.setFlag(dangerItemFlag[1]);

			dangerUnitDtoList.add(prpPdangerUnitDto);
		}

		return dangerUnitDtoList;
	}

	/**
	 * 獲取險別訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 滿足條件的險別訊息
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerItemList(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ArrayList getDangerItemList(HttpServletRequest req) throws Exception {
		ArrayList dangerItemList = new ArrayList();
		ParamUtils paramUtils = new ParamUtils(req);
		String businessType = paramUtils.getParameter("hiBusinessType");
		String businessNo = paramUtils.getParameter("businessNo");
		String classCode = paramUtils.getParameter("classCode");
		String[] riskCode = paramUtils.getParameterValues("riskCode");
		String[] dangerNo = paramUtils.getParameterValues("dangerNo");
		String[] serialNo = paramUtils.getParameterValues("itemKindNo"); //
		String[] kindCode = paramUtils.getParameterValues("ItemKindCode"); //
		String[] kindName = paramUtils.getParameterValues("ItemKindName"); //
		String[] itemCode = paramUtils.getParameterValues("ItemCode"); //
		String[] addressName = paramUtils.getParameterValues("ItemAddressName"); //
		String[] itemDetailName = paramUtils
				.getParameterValues("ItemDetailName"); //
		String[] currency = paramUtils.getParameterValues("ItemCurrency");
		String[] amount = paramUtils.getParameterValues("ItemAmount");
		String[] premium = paramUtils.getParameterValues("ItemPremium");
		String[] itemCalculateFlag = req
				.getParameterValues("hiItemcalculateFlag"); //
		String[] postCode = paramUtils.getParameterValues("ItemPost"); //
		String[] dangerFlag = paramUtils.getParameterValues("dangerFlag");//
		String[] isFacultative = paramUtils.getParameterValues("isFacultative");
		String[] riskLevel = paramUtils.getParameterValues("riskLevel");
		String[] riskLevelDesc = paramUtils.getParameterValues("riskLevelDesc");
		String[] riskClass = paramUtils.getParameterValues("riskClass");
		String[] riskClassDesc = paramUtils.getParameterValues("riskClassDesc");
		String[] retCurrency = paramUtils.getParameterValues("retCurrency");
		String[] sameRiskNo = paramUtils.getParameterValues("sameRiskNo");
		String[] retentionValue = paramUtils
				.getParameterValues("retentionValue");
		if (businessType.equals("T")) {
			for (int i = 1; i < serialNo.length; i++) {
				if (!dangerFlag[i].equals("0")) {
					PrpTDangerItemVO prpTdangerItemDto = new PrpTDangerItemVO();
					prpTdangerItemDto.setProposalNo(businessNo);
					prpTdangerItemDto
							.setDangerNo(Integer.parseInt(dangerNo[1]));
					prpTdangerItemDto.setIsFacultative(isFacultative[i-1]);
					prpTdangerItemDto
							.setSerialNo(Integer.parseInt(serialNo[i]));
					prpTdangerItemDto.setRiskCode(riskCode[1]);
					prpTdangerItemDto.setRiskCode(riskCode[1]);
					prpTdangerItemDto.setKindFlag("0"); //
					prpTdangerItemDto.setKindCode(kindCode[i]); //
					prpTdangerItemDto.setKindName(kindName[i]);
					prpTdangerItemDto.setItemCode(itemCode[i]); //
					prpTdangerItemDto.setItemDetailName(itemDetailName[i]); //
					prpTdangerItemDto.setPostCode(postCode[i]); //
					prpTdangerItemDto.setAddressName(addressName[i]); //
					prpTdangerItemDto.setCurrency(currency[i]);
					prpTdangerItemDto.setSameRiskNo(sameRiskNo[i]);
					prpTdangerItemDto.setAmount(Double.parseDouble(amount[i]
							.replaceAll(",", "")));
					prpTdangerItemDto.setPremium(Double.parseDouble(premium[i]
							.replaceAll(",", "")));
					prpTdangerItemDto.setCalculateFlag(itemCalculateFlag[i]); //
					if("A01".equals(riskCode[1]) || "B01".equals(riskCode[1])){
						prpTdangerItemDto.setRiskLevel(riskLevel[i]);
						prpTdangerItemDto.setRiskLevelDesc(riskLevelDesc[i]);
						prpTdangerItemDto.setRiskClass(riskClass[i]);
						prpTdangerItemDto.setRiskClassDesc(riskClassDesc[i]);
						prpTdangerItemDto.setReTCurrency(retCurrency[i]);
						prpTdangerItemDto.setRetentionValue(Double.parseDouble(retentionValue[i].replaceAll(",", "")));
					}
					dangerItemList.add(prpTdangerItemDto);
				}
			}
		} else if (businessType.equals("P")) {
			for (int i = 1; i < serialNo.length; i++) {
				if (!dangerFlag[i].equals("0")) {
					PrpCDangerItemVO prpCdangerItemDto = new PrpCDangerItemVO();
					prpCdangerItemDto.setPolicyNo(businessNo);
					prpCdangerItemDto
							.setDangerNo(Integer.parseInt(dangerNo[1]));
					prpCdangerItemDto.setIsFacultative(isFacultative[i-1]);
					prpCdangerItemDto
							.setSerialNo(Integer.parseInt(serialNo[i]));
					prpCdangerItemDto.setRiskCode(riskCode[1]);
					prpCdangerItemDto.setRiskCode(riskCode[1]);
					prpCdangerItemDto.setKindFlag("0"); //
					prpCdangerItemDto.setKindCode(kindCode[i]); //
					prpCdangerItemDto.setKindName(kindName[i]);
					prpCdangerItemDto.setItemCode(itemCode[i]); //
					prpCdangerItemDto.setItemDetailName(itemDetailName[i]); //
					prpCdangerItemDto.setPostCode(postCode[i]); //
					prpCdangerItemDto.setAddressName(addressName[i]); //
					prpCdangerItemDto.setCurrency(currency[i]);
					prpCdangerItemDto.setAmount(Double.parseDouble(amount[i]
							.replaceAll(",", "")));
					prpCdangerItemDto.setPremium(Double.parseDouble(premium[i]
							.replaceAll(",", "")));
					prpCdangerItemDto.setCalculateFlag(itemCalculateFlag[i]); //
					prpCdangerItemDto.setRiskLevel(riskLevel[i]);
					prpCdangerItemDto.setRiskLevelDesc(riskLevelDesc[i]);
					prpCdangerItemDto.setRiskClass(riskClass[i]);
					prpCdangerItemDto.setRiskClassDesc(riskClassDesc[i]);
					prpCdangerItemDto.setReTCurrency(retCurrency[i]);
					prpCdangerItemDto
							.setRetentionValue(Double
									.parseDouble(retentionValue[i].replaceAll(
											",", "")));

					dangerItemList.add(prpCdangerItemDto);
				}
			}
		} else if (businessType.equals("E")) {

			String[] chgAmount = paramUtils.getParameterValues("ItemchgAmount");
			String[] chgPremium = paramUtils
					.getParameterValues("ItemchgPremium");
			String[] pdangerItemFlag = req
					.getParameterValues("hiPdangerItemFlag");

			for (int i = 1; i < serialNo.length; i++) {

				if (!dangerFlag[i].equals("0")) {
					PrpPDangerItemVO prpPdangerItemDto = new PrpPDangerItemVO();
					prpPdangerItemDto.setEndorseNo(businessNo);
					prpPdangerItemDto.setDangerNo(Integer.parseInt(dangerNo[1]));
					if("A".equals(classCode) || "B".equals(classCode)){
						prpPdangerItemDto.setIsFacultative(isFacultative[i-1]);
					}
					prpPdangerItemDto.setSerialNo(Integer.parseInt(serialNo[i]));
					prpPdangerItemDto.setRiskCode(riskCode[1]);
					prpPdangerItemDto.setRiskCode(riskCode[1]);
					prpPdangerItemDto.setKindFlag("0"); //
					prpPdangerItemDto.setKindCode(kindCode[i]); //
					prpPdangerItemDto.setKindName(kindName[i]);
					prpPdangerItemDto.setItemCode(itemCode[i]); //
					prpPdangerItemDto.setItemDetailName(itemDetailName[i]); //
					prpPdangerItemDto.setPostCode(postCode[i]); //
					prpPdangerItemDto.setAddressName(addressName[i]); //
					prpPdangerItemDto.setCurrency(currency[i]);
					prpPdangerItemDto.setSameRiskNo(sameRiskNo[i]);
					prpPdangerItemDto.setAmount(Double.parseDouble(amount[i]
							.replaceAll(",", "")));
					prpPdangerItemDto.setChgAmount(Double
							.parseDouble(chgAmount[i].replaceAll(",", "")));
					prpPdangerItemDto.setPremium(Double.parseDouble(premium[i]
							.replaceAll(",", "")));
					prpPdangerItemDto.setChgPremium(Double
							.parseDouble(chgPremium[i].replaceAll(",", "")));
					prpPdangerItemDto.setCalculateFlag(itemCalculateFlag[i]); //
					prpPdangerItemDto.setFlag(pdangerItemFlag[i]);
					if("A01".equals(riskCode[1]) || "B01".equals(riskCode[1])){
						prpPdangerItemDto.setRiskLevel(riskLevel[i]);
						prpPdangerItemDto.setRiskLevelDesc(riskLevelDesc[i]);
						prpPdangerItemDto.setRiskClass(riskClass[i]);
						prpPdangerItemDto.setRiskClassDesc(riskClassDesc[i]);
						prpPdangerItemDto.setReTCurrency(retCurrency[i]);
						prpPdangerItemDto.setRetentionValue(Double.parseDouble(retentionValue[i].replaceAll(",", "")));
					}
					dangerItemList.add(prpPdangerItemDto);
				}
			}
		}
		return dangerItemList;
	}

	/**
	 * 獲取投保單危險單位金額合計資訊.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 投保單危險單位金額類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerTotList(java.lang.String,
	 *      java.lang.String, java.util.Collection)
	 */
	@Override
	public Collection getDangerTotList(String businessType, String businessNo,
			Collection prpDangerUnitDtoList) throws Exception {
		DecimalFormat idecimalFormat = new DecimalFormat("0.00");
		Collection prpDangerTotDtoList = new ArrayList();
		Iterator iterator = null;
		Iterator itFee = null;
		double exchRate = 0d;

		Collection prpFeeDtoList = (Collection) prpallService.getExchangeRate(
				businessType, businessNo);
		if (businessType.equals("T")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerUnitVO prpTdangerUnitDto = (PrpTDangerUnitVO) iterator
						.next();

				PrpTDangerTotVO prpTdangerTotDto = new PrpTDangerTotVO();
				prpTdangerTotDto.setDangerNo(prpTdangerUnitDto.getDangerNo());
				prpTdangerTotDto.setProposalNo(prpTdangerUnitDto
						.getProposalNo());
				prpTdangerTotDto.settCurrency(prpTdangerUnitDto.getCurrency());
				prpTdangerTotDto.setAmountEx(prpTdangerUnitDto.getAmount());
				prpTdangerTotDto.setPremiumEx(prpTdangerUnitDto.getPremium());

				//
				itFee = prpFeeDtoList.iterator();
				while (itFee.hasNext()) {
					PrpTfee prpTfee = (PrpTfee) itFee.next();
					if (prpTdangerTotDto.gettCurrency().equals(
							prpTfee.getCurrency2())) {
						exchRate = prpTfee.getExchangeRate2().doubleValue();
						prpTdangerTotDto.setExchRate(exchRate);
						prpTdangerTotDto.setsCurrency(prpTfee.getId()
								.getCurrency());
						prpTdangerTotDto.setAmount(Double
								.parseDouble(idecimalFormat
										.format(prpTdangerTotDto.getAmountEx()
												/ exchRate)));
						prpTdangerTotDto.setPremium(Double
								.parseDouble(idecimalFormat
										.format(prpTdangerTotDto.getPremiumEx()
												/ exchRate)));
						break;
					}
				}
				prpDangerTotDtoList.add(prpTdangerTotDto);
			}
		} else if (businessType.equals("P")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnitVO prpCdangerUnitDto = (PrpCDangerUnitVO) iterator
						.next();
				PrpCDangerTotVO prpCdangerTotDto = new PrpCDangerTotVO();
				prpCdangerTotDto.setDangerNo(prpCdangerUnitDto.getDangerNo());
				prpCdangerTotDto.setPolicyNo(prpCdangerUnitDto.getPolicyNo());
				prpCdangerTotDto.settCurrency(prpCdangerUnitDto.getCurrency());
				prpCdangerTotDto.setAmountEx(prpCdangerUnitDto.getAmount());
				prpCdangerTotDto.setPremiumEx(prpCdangerUnitDto.getPremium());

				//
				itFee = prpFeeDtoList.iterator();
				while (itFee.hasNext()) {
					PrpCfee prpCfee = (PrpCfee) itFee.next();
					if (prpCdangerTotDto.gettCurrency().equals(
							prpCfee.getCurrency2())) {
						exchRate = prpCfee.getExchangeRate2().doubleValue();
						prpCdangerTotDto.setExchRate(exchRate);
						prpCdangerTotDto.setsCurrency(prpCfee.getId()
								.getCurrency());
						prpCdangerTotDto.setAmount(Double
								.parseDouble(idecimalFormat
										.format(prpCdangerTotDto.getAmountEx()
												/ exchRate)));
						prpCdangerTotDto.setPremium(Double
								.parseDouble(idecimalFormat
										.format(prpCdangerTotDto.getPremiumEx()
												/ exchRate)));
						break;
					}
				}
				prpDangerTotDtoList.add(prpCdangerTotDto);
			}
		} else if (businessType.equals("E")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnitVO prpPdangerUnitDto = (PrpPDangerUnitVO) iterator
						.next();
				PrpPDangerTotVO prpPdangerTotDto = new PrpPDangerTotVO();
				prpPdangerTotDto.setDangerNo(prpPdangerUnitDto.getDangerNo());
				prpPdangerTotDto.setEndorseNo(prpPdangerUnitDto.getEndorseNo());
				prpPdangerTotDto.settCurrency(prpPdangerUnitDto.getCurrency());
				prpPdangerTotDto.setAmountEx(prpPdangerUnitDto.getAmount());
				prpPdangerTotDto.setPremiumEx(prpPdangerUnitDto.getPremium());
				prpPdangerTotDto.setChgAmountEx(prpPdangerUnitDto
						.getChgAmount());
				prpPdangerTotDto.setChgPremiumEx(prpPdangerUnitDto
						.getChgPremium());

				//
				itFee = prpFeeDtoList.iterator();
				while (itFee.hasNext()) {
					PrpCPfee prpCPfee = (PrpCPfee) itFee.next();
					if (prpPdangerTotDto.gettCurrency().equals(
							prpCPfee.getCurrency2())) {
						exchRate = prpCPfee.getExchangeRate2().doubleValue();
						prpPdangerTotDto.setExchRate(exchRate);
						prpPdangerTotDto.setsCurrency(prpCPfee.getId()
								.getCurrency());
						prpPdangerTotDto.setAmount(Double
								.parseDouble(idecimalFormat
										.format(prpPdangerTotDto.getAmountEx()
												/ exchRate)));
						prpPdangerTotDto.setPremium(Double
								.parseDouble(idecimalFormat
										.format(prpPdangerTotDto.getPremiumEx()
												/ exchRate)));
						prpPdangerTotDto.setChgAmount(Double
								.parseDouble(idecimalFormat
										.format(prpPdangerTotDto
												.getChgAmountEx() / exchRate)));
						prpPdangerTotDto
								.setChgPremium(Double
										.parseDouble(idecimalFormat
												.format(prpPdangerTotDto
														.getChgPremiumEx()
														/ exchRate)));
						break;
					}
				}
				prpDangerTotDtoList.add(prpPdangerTotDto);
			}
		}

		return prpDangerTotDtoList;
	}

	/**
	 * 獲取投保單危險單位交費計畫.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 投保單危險單位交費計畫類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerPlanList(java.lang.String,
	 *      java.lang.String, java.util.Collection)
	 */
	@Override
	public Collection getDangerPlanList(String businessType, String businessNo,
			Collection prpDangerUnitDtoList) throws Exception {
		DecimalFormat idecimalFormat = new DecimalFormat("0.00");
		Collection prpPlanList = prpallService.getPrpPlan(businessNo,
				businessType);
		Collection prpDangerPlanList = new ArrayList();
		Iterator iterator = null;
		Iterator itPlan = null;
		double planFee = 0d;
		double sumPlanFee = 0d;

		if (businessType.equals("T")) {
			itPlan = prpPlanList.iterator();
			while (itPlan.hasNext()) {
				PrpTplan prpTplan = (PrpTplan) itPlan.next();
				sumPlanFee = sumPlanFee + prpTplan.getPlanFee().doubleValue();
			}
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerUnitVO prpTdangerUnitDto = (PrpTDangerUnitVO) iterator
						.next();
				itPlan = prpPlanList.iterator();
				while (itPlan.hasNext()) {
					PrpTplan prpTplan = (PrpTplan) itPlan.next();
					PrpTDangerPlanVO prpTdangerPlanDto = new PrpTDangerPlanVO();
					prpTdangerPlanDto.setDangerNo(prpTdangerUnitDto
							.getDangerNo());
					prpTdangerPlanDto.setProposalNo(businessNo);
					prpTdangerPlanDto.setSerialNo((int) prpTplan.getId()
							.getSerialNo());
					prpTdangerPlanDto.setPayNo((int) prpTplan.getPayNo());
					prpTdangerPlanDto.setPlanDate(new DateTime(prpTplan
							.getPlanStartDate()));

					prpTdangerPlanDto.setCurrency(prpTplan.getCurrency2());
					prpTdangerPlanDto.setPlanFee(prpTplan.getPlanFee2()
							.doubleValue());
					if (sumPlanFee == 0) {
						planFee = prpTdangerUnitDto.getPremium();
					} else {
						planFee = Double.parseDouble(idecimalFormat
								.format(prpTdangerUnitDto.getPremium()
										* prpTplan.getPlanFee().doubleValue()
										/ sumPlanFee));
					}
					prpTdangerPlanDto.setPlanFee(planFee);
					prpDangerPlanList.add(prpTdangerPlanDto);
				}
			}
		} else if (businessType.equals("P")) {
			itPlan = prpPlanList.iterator();
			while (itPlan.hasNext()) {
				PrpCplan prpCplan = (PrpCplan) itPlan.next();
				sumPlanFee = sumPlanFee + prpCplan.getPlanFee().doubleValue();
			}
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnitVO prpCdangerUnitDto = (PrpCDangerUnitVO) iterator
						.next();
				itPlan = prpPlanList.iterator();
				while (itPlan.hasNext()) {
					PrpCplan prpCplan = (PrpCplan) itPlan.next();
					PrpCDangerPlanVO prpCdangerPlanDto = new PrpCDangerPlanVO();
					prpCdangerPlanDto.setDangerNo(prpCdangerUnitDto
							.getDangerNo());
					prpCdangerPlanDto.setPolicyNo(businessNo);
					prpCdangerPlanDto.setSerialNo((int) prpCplan.getId()
							.getSerialNo());
					prpCdangerPlanDto.setPayNo((int) prpCplan.getPayNo());
					prpCdangerPlanDto.setPlanDate(new DateTime(prpCplan
							.getPlanStartDate()));
					prpCdangerPlanDto.setCurrency(prpCplan.getCurrency());
					if (sumPlanFee == 0) {
						planFee = prpCdangerUnitDto.getPremium();
					} else {
						planFee = Double.parseDouble(idecimalFormat
								.format(prpCdangerUnitDto.getPremium()
										* prpCdangerPlanDto.getPlanFee()
										/ sumPlanFee));
					}
					prpCdangerPlanDto.setPlanFee(planFee);
					prpDangerPlanList.add(prpCdangerPlanDto);
				}
			}
		} else if (businessType.equals("E")) {
			itPlan = prpPlanList.iterator();
			while (itPlan.hasNext()) {
				PrpPplan prpPplan = (PrpPplan) itPlan.next();
				sumPlanFee = sumPlanFee + prpPplan.getPlanFee().doubleValue();
			}
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnitVO prpPdangerUnitDto = (PrpPDangerUnitVO) iterator
						.next();
				itPlan = prpPlanList.iterator();
				while (itPlan.hasNext()) {
					PrpPplan prpPplan = (PrpPplan) itPlan.next();
					PrpPDangerPlanVO prpPdangerPlanDto = new PrpPDangerPlanVO();
					prpPdangerPlanDto.setDangerNo(prpPdangerUnitDto
							.getDangerNo());
					prpPdangerPlanDto.setEndorseNo(businessNo);
					prpPdangerPlanDto.setSerialNo((int) prpPplan.getId()
							.getSerialNo());
					prpPdangerPlanDto.setPayNo((int) prpPplan.getPayNo());
					prpPdangerPlanDto.setPlanDate(new DateTime(prpPplan
							.getPlanDate()));
					prpPdangerPlanDto.setCurrency(prpPplan.getCurrency2());
					if (sumPlanFee == 0) {
						planFee = prpPdangerUnitDto.getChgPremium();
					} else {
						planFee = Double.parseDouble(idecimalFormat
								.format(prpPdangerUnitDto.getChgPremium()
										* prpPplan.getPlanFee().doubleValue()
										/ sumPlanFee));
					}
					prpPdangerPlanDto.setPlanFee(planFee);
					prpDangerPlanList.add(prpPdangerPlanDto);
				}
			}
		}
		return prpDangerPlanList;
	}

	/**
	 * 獲取投保單危險單位共保資訊.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param policyNo
	 *            保單號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 保單危險單位共保資訊類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerCoinsList(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.Collection)
	 */
	@Override
	public Collection getDangerCoinsList(String businessType,
			String businessNo, String policyNo, Collection prpDangerUnitDtoList)
			throws Exception {
		DecimalFormat idecimalFormat = new DecimalFormat("0.00");
		Collection prpDangerCoinsList = new ArrayList();
		Collection prpCoinsDtoList = new ArrayList();
		Iterator iterator = null;
		Iterator itCoins = null;

		prpCoinsDtoList = (Collection) prpallService.getPrpCoinsList(
				businessNo, businessType);

		if (businessType.equals("T")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerUnitVO prpTdangerUnitDto = (PrpTDangerUnitVO) iterator
						.next();
				// modify begin 20071119 by lihua 业务系统的分入公司信息作为联共保
				// 信息录入到系统中，但是分入没有联共保的业务，因此增加判断：分入业务不保存联共保信息
				if (prpTdangerUnitDto.getBusinessFlag().equals("1")) {
					return prpDangerCoinsList;
				}
				// modify end 20071119 by lihua
				itCoins = prpCoinsDtoList.iterator();
				while (itCoins.hasNext()) {
					PrpTcoinsDto prpTcoinsDto = (PrpTcoinsDto) itCoins.next();
					PrpTDangerCoinsVO prpTdangerCoinsDto = new PrpTDangerCoinsVO();
					prpTdangerCoinsDto.setDangerNo(prpTdangerUnitDto
							.getDangerNo());
					prpTdangerCoinsDto.setProposalNo(businessNo);
					prpTdangerCoinsDto.setSerialNo(prpTcoinsDto.getSerialNo());
					prpTdangerCoinsDto.setMainProposalNo(businessNo);
					prpTdangerCoinsDto
							.setCoinsCode(prpTcoinsDto.getCoinsCode());
					prpTdangerCoinsDto
							.setCoinsName(prpTcoinsDto.getCoinsName());
					prpTdangerCoinsDto
							.setCoinsType(prpTcoinsDto.getCoinsType());
					prpTdangerCoinsDto
							.setCoinsRate(prpTcoinsDto.getCoinsRate());
					prpTdangerCoinsDto
							.setChiefFlag(prpTcoinsDto.getChiefFlag());
					prpTdangerCoinsDto.setProportionFlag(prpTcoinsDto
							.getProportionFlag());
					prpTdangerCoinsDto.setCurrency(prpTdangerUnitDto
							.getCurrency());
					prpTdangerCoinsDto
							.setCoinsAmount(Double.parseDouble(idecimalFormat
									.format(prpTdangerUnitDto.getAmount()
											* prpTcoinsDto.getCoinsRate() / 100)));
					prpTdangerCoinsDto
							.setCoinsPremium(Double.parseDouble(idecimalFormat
									.format(prpTdangerUnitDto.getPremium()
											* prpTcoinsDto.getCoinsRate() / 100)));
					prpTdangerCoinsDto.setMiddleCostFee(0.00);
					prpTdangerCoinsDto.setFlag("");

					prpDangerCoinsList.add(prpTdangerCoinsDto);
				}
			}
		} else if (businessType.equals("P")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnitVO prpCdangerUnitDto = (PrpCDangerUnitVO) iterator
						.next();
				// modify begin 20071119 by lihua 业务系统的分入公司信息作为联共保
				// 信息录入到系统中，但是分入没有联共保的业务，因此增加判断：分入业务不保存联共保信息
				if (prpCdangerUnitDto.getBusinessFlag().equals("1")) {
					return prpDangerCoinsList;
				}
				// modify end 20071119 by lihua
				itCoins = prpCoinsDtoList.iterator();
				while (itCoins.hasNext()) {
					PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) itCoins.next();
					PrpCDangerCoinsVO prpCdangerCoinsDto = new PrpCDangerCoinsVO();
					prpCdangerCoinsDto.setDangerNo(prpCdangerUnitDto
							.getDangerNo());
					prpCdangerCoinsDto.setPolicyNo(businessNo);
					prpCdangerCoinsDto.setSerialNo(prpCcoinsDto.getSerialNo());
					prpCdangerCoinsDto.setMainPolicyNo(businessNo);
					prpCdangerCoinsDto
							.setCoinsCode(prpCcoinsDto.getCoinsCode());
					prpCdangerCoinsDto
							.setCoinsName(prpCcoinsDto.getCoinsName());
					prpCdangerCoinsDto
							.setCoinsType(prpCcoinsDto.getCoinsType());
					prpCdangerCoinsDto
							.setCoinsRate(prpCcoinsDto.getCoinsRate());
					prpCdangerCoinsDto
							.setChiefFlag(prpCcoinsDto.getChiefFlag());
					prpCdangerCoinsDto.setProportionFlag(prpCcoinsDto
							.getProportionFlag());
					prpCdangerCoinsDto.setCurrency(prpCdangerUnitDto
							.getCurrency());
					prpCdangerCoinsDto
							.setCoinsAmount(Double.parseDouble(idecimalFormat
									.format(prpCdangerUnitDto.getAmount()
											* prpCcoinsDto.getCoinsRate() / 100)));
					prpCdangerCoinsDto
							.setCoinsPremium(Double.parseDouble(idecimalFormat
									.format(prpCdangerUnitDto.getPremium()
											* prpCcoinsDto.getCoinsRate() / 100)));
					prpCdangerCoinsDto.setMiddleCostFee(0.00);
					prpCdangerCoinsDto.setFlag("");

					prpDangerCoinsList.add(prpCdangerCoinsDto);
				}
			}
		} else if (businessType.equals("E")) {
			prpCoinsDtoList = (Collection) prpallService.getPrpCoinsList(
					policyNo, "P");
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnitVO prpPdangerUnitDto = (PrpPDangerUnitVO) iterator
						.next();
				// modify begin 20071119 by lihua 业务系统的分入公司信息作为联共保
				// 信息录入到系统中，但是分入没有联共保的业务，因此增加判断：分入业务不保存联共保信息
				if (prpPdangerUnitDto.getBusinessFlag().equals("1")) {
					return prpDangerCoinsList;
				}
				// modify end 20071119 by lihua
				itCoins = prpCoinsDtoList.iterator();
				while (itCoins.hasNext()) {
					PrpCcoinsDto prpCcoinsDto = (PrpCcoinsDto) itCoins.next();
					PrpPDangerCoinsVO prpPdangerCoinsDto = new PrpPDangerCoinsVO();
					prpPdangerCoinsDto.setDangerNo(prpPdangerUnitDto
							.getDangerNo());
					prpPdangerCoinsDto.setEndorseNo(businessNo);
					prpPdangerCoinsDto.setSerialNo(prpCcoinsDto.getSerialNo());
					prpPdangerCoinsDto.setMainEndorseNo(businessNo);
					prpPdangerCoinsDto
							.setCoinsCode(prpCcoinsDto.getCoinsCode());
					prpPdangerCoinsDto
							.setCoinsName(prpCcoinsDto.getCoinsName());
					prpPdangerCoinsDto
							.setCoinsType(prpCcoinsDto.getCoinsType());
					prpPdangerCoinsDto
							.setCoinsRate(prpCcoinsDto.getCoinsRate());
					prpPdangerCoinsDto
							.setChiefFlag(prpCcoinsDto.getChiefFlag());
					prpPdangerCoinsDto.setProportionFlag(prpCcoinsDto
							.getProportionFlag());
					prpPdangerCoinsDto.setCurrency(prpPdangerUnitDto
							.getCurrency());
					prpPdangerCoinsDto
							.setCoinsAmount(Double.parseDouble(idecimalFormat
									.format(prpPdangerUnitDto.getAmount()
											* prpCcoinsDto.getCoinsRate() / 100)));
					prpPdangerCoinsDto
							.setCoinsPremium(Double.parseDouble(idecimalFormat
									.format(prpPdangerUnitDto.getPremium()
											* prpCcoinsDto.getCoinsRate() / 100)));
					prpPdangerCoinsDto
							.setChgCoinsAmount(Double
									.parseDouble(idecimalFormat
											.format(prpPdangerUnitDto
													.getChgAmount()
													* prpCcoinsDto
															.getCoinsRate()
													/ 100)));
					prpPdangerCoinsDto
							.setChgCoinsPremium(Double
									.parseDouble(idecimalFormat
											.format(prpPdangerUnitDto
													.getChgPremium()
													* prpCcoinsDto
															.getCoinsRate()
													/ 100)));
					prpPdangerCoinsDto.setMiddleCostFee(0.00);
					prpPdangerCoinsDto.setChgMiddleCostFee(0.00);
					prpPdangerCoinsDto.setFlag("");

					prpDangerCoinsList.add(prpPdangerCoinsDto);
				}
			}
		}

		return prpDangerCoinsList;
	}

	/**
	 * 更新危險單位共保等保額訊息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param sumAmount
	 *            總保額
	 * @param chgAmount
	 *            變化保額
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @param prpDangerTotDtoList
	 *            投保單危險單位金額合計資訊類集合
	 * @param prpDangerCoinsDtoList
	 *            投保單危險單位共保資訊類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#updateAmountFor1903(java.lang.String,
	 *      double, double, java.util.Collection, java.util.Collection,
	 *      java.util.Collection)
	 */
	@Override
	public void updateAmountFor1903(String businessType, double sumAmount,
			double chgAmount, Collection prpDangerUnitDtoList,
			Collection prpDangerTotDtoList, Collection prpDangerCoinsDtoList)
			throws Exception {
		DecimalFormat idecimalFormat = new DecimalFormat("0.00");
		Iterator iterator = null;

		if (businessType.equals("T")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerUnitVO prpTdangerUnitDto = (PrpTDangerUnitVO) iterator
						.next();
				prpTdangerUnitDto.setAmount(sumAmount);
			}
			iterator = prpDangerTotDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerTot prpTdangerTotDto = (PrpTDangerTot) iterator
						.next();
				prpTdangerTotDto.setAmount(sumAmount);
				prpTdangerTotDto.setAmountEx(sumAmount);
			}
			iterator = prpDangerCoinsDtoList.iterator();
			while (iterator.hasNext()) {
				PrpTDangerCoins prpTdangerCoinsDto = (PrpTDangerCoins) iterator
						.next();
				prpTdangerCoinsDto.setCoinsAmount(Double
						.parseDouble(idecimalFormat.format(sumAmount
								* prpTdangerCoinsDto.getCoinsRate() / 100)));
			}
		} else if (businessType.equals("P")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnit prpCdangerUnitDto = (PrpCDangerUnit) iterator
						.next();
				prpCdangerUnitDto.setAmount(sumAmount);
			}
			iterator = prpDangerTotDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerTot prpCdangerTotDto = (PrpCDangerTot) iterator
						.next();
				prpCdangerTotDto.setAmount(sumAmount);
				prpCdangerTotDto.setAmountEx(sumAmount);
			}
			iterator = prpDangerCoinsDtoList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerCoins prpCdangerCoinsDto = (PrpCDangerCoins) iterator
						.next();
				prpCdangerCoinsDto.setCoinsAmount(Double
						.parseDouble(idecimalFormat.format(sumAmount
								* prpCdangerCoinsDto.getCoinsRate() / 100)));
			}
		} else if (businessType.equals("E")) {
			iterator = prpDangerUnitDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnit prpPdangerUnitDto = (PrpPDangerUnit) iterator
						.next();
				prpPdangerUnitDto.setAmount(sumAmount);
			}
			iterator = prpDangerTotDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerTot prpPdangerTotDto = (PrpPDangerTot) iterator
						.next();
				prpPdangerTotDto.setAmount(sumAmount);
				prpPdangerTotDto.setAmountEx(sumAmount);
			}
			iterator = prpDangerCoinsDtoList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerCoins prpPdangerCoinsDto = (PrpPDangerCoins) iterator
						.next();
				prpPdangerCoinsDto.setCoinsAmount(Double
						.parseDouble(idecimalFormat.format(sumAmount
								* prpPdangerCoinsDto.getCoinsRate() / 100)));
				prpPdangerCoinsDto.setChgCoinsAmount(Double
						.parseDouble(idecimalFormat.format(chgAmount
								* prpPdangerCoinsDto.getCoinsRate() / 100)));
			}
		}
	}

	/**
	 * 獲取危險單位訊息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param req
	 *            請求對象
	 * @return 危險單位訊息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#getDangerUnit(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void getDangerUnit(String certiNo, HttpServletRequest req)
			throws Exception {
		InternationalizationUtil intenal = new InternationalizationUtil();
		String condition = "certiNo = '" + certiNo + "'";
		Collection dangerTrial = null;
		PrpLReinsTrial prpLreinsTrialDto = new PrpLReinsTrial();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		dangerTrial = prpLReinsTrialService.findByConditions(queryRule);
		Iterator dangerList = dangerTrial.iterator();
		double sumShare = 0.0D;
		double sumAmount = 0.0D;
		double sumPaid = 0.0D;
		double sumFee = 0.0D;
		String treatyRef;
		for (; dangerList.hasNext(); prpLreinsTrialDto.setDangerDesc(treatyRef)) {
			prpLreinsTrialDto = (PrpLReinsTrial) dangerList.next();
			sumShare += prpLreinsTrialDto.getShareRate();
			sumAmount += prpLreinsTrialDto.getSumClaim();
			sumPaid += prpLreinsTrialDto.getSumPaid();
			sumFee += prpLreinsTrialDto.getSumFee();
			String treatyNo = prpLreinsTrialDto.getTreatyNo();
			treatyRef = this.findByPrimaryKey(treatyNo);
		}

		PrpLReinsTrial sum = new PrpLReinsTrial();
		sum.setDangerDesc(intenal
				.getText("undwrt.service.commonDangerInfo.total"));
		sum.setShareRate(sumShare);
		sum.setSumClaim(sumAmount);
		sum.setSumPaid(sumPaid);
		// add begin 20091225 by yilijun 显示的时候增加费用一列
		sum.setSumFee(sumFee);
		// add end 20091225 by yilijun 显示的时候增加费用一列
		dangerTrial.add(sum);
		if (dangerTrial != null && dangerTrial.size() > 0)
			req.setAttribute("reinsTrial", "reinsTrial");
		req.setAttribute("dangerTrial", dangerTrial);
	}

	/**
	 * 根據合約號查詢合約主信息.
	 * 
	 * @param treatyNo
	 *            合約號
	 * @return 合約簡稱
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public String findByPrimaryKey(String treatyNo) throws Exception {
		FhTreatyVO fhTreatyDto = new FhTreatyVO();
		fhTreatyDto = blFhTreatyService.findByPrimaryKey(treatyNo);
		return fhTreatyDto.getRefNo();
	}

	/**
	 * 分保訊息轉成請求對象.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService#reinsTrialInfoToRequest(java.lang.String,
	 *      java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reinsTrialInfoToRequest(String businessNo, String businessType,
			HttpServletRequest req) throws Exception {
		double treatyShareRate = 0.0D;
		double treatyAmount = 0.0D;
		double treatyPremium = 0.0D;
		double treatyComm = 0.0D;
		double agreementShareRate = 0.0D;
		double agreementAmount = 0.0D;
		double agreementPremium = 0.0D;
		double agreementComm = 0.0D;
		double facAmount = 0.0D;
		double facPremium = 0.0D;
		double facShareRate = 0.0D;
		double facComm = 0.0D;
		int count = 0;
		String dangerNo = "";
		ArrayList prpTreinstrialViewInofoList = new ArrayList();
		Collection dangerUnitSerialNoInfo = null;
		ReinsTrialDangerInfoVo reinsTrialDangerInfoDto = null;
		dangerUnitSerialNoInfo = (Collection) prpallService
				.getDangerUnitSerialNoInfo(businessNo, businessType);
		Collection result = new ArrayList();
		for (Iterator iterator = dangerUnitSerialNoInfo.iterator(); iterator
				.hasNext(); req.setAttribute("ReinsTrialInfo", result)) {
			CommonDangerUnitSerialNoVo commonDangerUnitSerialNoDto = (CommonDangerUnitSerialNoVo) iterator
					.next();
			dangerNo = commonDangerUnitSerialNoDto.getDangerNo();
			Collection reinsTrialInfo = (Collection) (reinsService
					.getReinsTrialInfo(businessNo, dangerNo, businessType));
			reinsTrialDangerInfoDto = new ReinsTrialDangerInfoVo();
			reinsTrialDangerInfoDto.setDangerNo(Integer.parseInt(dangerNo));
			reinsTrialDangerInfoDto.setCollection(reinsTrialInfo);
			result.add(reinsTrialDangerInfoDto);
		}

	}

	/**
	 * 獲取屬性核保系統查詢接口.
	 * 
	 * @return 屬性核保系統查詢接口的值
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置屬性核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口的值
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取屬性再保服務接口.
	 * 
	 * @return 屬性再保服務接口的值
	 */
	public ReinsService getReinsService() {
		return reinsService;
	}

	/**
	 * 設置屬性再保服務接口.
	 * 
	 * @param reinsService
	 *            待設置的再保服務接口的值
	 */
	public void setReinsService(ReinsService reinsService) {
		this.reinsService = reinsService;
	}

	/**
	 * 獲取屬性合約主信息處理接口.
	 * 
	 * @return 屬性合約主信息處理接口的值
	 */

	public BLFhTreatyService getBlFhTreatyService() {
		return blFhTreatyService;
	}
	
	/**
	 * 設置屬性合約主信息處理接口.
	 * 
	 * @param fhTreatyService
	 *            待設置的合約主信息處理接口的值
	 */
	
	public void setBlFhTreatyService(BLFhTreatyService blFhTreatyService) {
		this.blFhTreatyService = blFhTreatyService;
	}

	/**
	 * 獲取屬性賠案的分攤試算信息接口.
	 * 
	 * @return 屬性賠案的分攤試算信息接口的值
	 */
	public PrpLReinsTrialService getPrpLReinsTrialService() {
		return prpLReinsTrialService;
	}

	/**
	 * 設置屬性賠案的分攤試算信息接口.
	 * 
	 * @param prpLReinsTrialService
	 *            待設置的賠案的分攤試算信息接口的值
	 */
	public void setPrpLReinsTrialService(
			PrpLReinsTrialService prpLReinsTrialService) {
		this.prpLReinsTrialService = prpLReinsTrialService;
	}

	/**
	 * 獲取屬性再保接口業務處理接口.
	 * 
	 * @return 屬性再保接口業務處理接口的值
	 */
	public BLReinsService getBlReinsService() {
		return blReinsService;
	}

	/**
	 * 設置屬性再保接口業務處理接口.
	 * 
	 * @param blReinsService
	 *            待設置的再保接口業務處理接口的值
	 */
	public void setBlReinsService(BLReinsService blReinsService) {
		this.blReinsService = blReinsService;
	}

}
