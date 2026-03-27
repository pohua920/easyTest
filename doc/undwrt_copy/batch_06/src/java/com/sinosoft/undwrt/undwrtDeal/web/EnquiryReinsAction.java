package com.sinosoft.undwrt.undwrtDeal.web;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.reins.common.model.PrpCDangerCoins;
import com.sinosoft.reins.common.model.PrpPDangerCoins;
import com.sinosoft.reins.common.model.PrpTDangerCoins;
import com.sinosoft.reins.common.service.facade.BLEnquiryService;
import com.sinosoft.reins.common.service.facade.FacXLayerService;
import com.sinosoft.reins.common.vo.PrpCDangerCoinsVO;
import com.sinosoft.reins.common.vo.PrpPDangerCoinsVO;
import com.sinosoft.reins.common.vo.PrpTDangerCoinsVO;
import com.sinosoft.reins.in.facultative.verify.service.facade.VerifyService;
import com.sinosoft.reins.out.facultative.enquiry.vo.EnquiryVO;
import com.sinosoft.reins.out.facultative.enquiry.vo.FeoEnquiryVO;
import com.sinosoft.reins.out.facultative.enquiry.vo.FeoReinsReceiveVO;
import com.sinosoft.reins.out.facultative.enquiry.model.FeoReinsReceive;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.BLFacXLayerService;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.EnquiryService;
import com.sinosoft.reins.out.treaty.util.UIFormatAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

// TODO: Auto-generated Javadoc
/**
 * 再保處理類.
 */
public class EnquiryReinsAction extends Struts2Action {

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;

	/** 屬性詢價單號. */
	private String[] EnquiryNo;

	/** 屬性再保詢價單處理接口. */
	private EnquiryService enquiryService;

	/** 屬性臨分超賠接口. */
	private BLFacXLayerService blFacXLayerService;

	/** 屬性再保確認接口. */
	private VerifyService verifyService;

	/** 屬性臨分詢價單處理接口. */
	private BLEnquiryService blEnquiryService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;
	
	/** 屬性批單處理接口. */
	private EndorseService endorseService;
	
	private String riskCode;
	
	private String[] dangerNo;
	
	private String[] addDangerNo;
	
	private String dangerNos;
	
	/** 屬性業務號. */
	private String certiNo;

	/** 屬性業務類型. */
	private String certiType;
	
	/** 屬性險種代碼. */
	private String iRiskCode;
	
	private String whetherFacing;
	
	/**
	 * 保存再保信息.
	 * 
	 * @return 頁面跳轉結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String saveReinsInfo() throws UserException, Exception {
		String forward = "";
		EnquiryVO enquiryVo = null;
		ArrayList<EnquiryVO> enquiryVoList = null;
		ArrayList itemKindList = new ArrayList();
		ArrayList engageList = new ArrayList();
		ArrayList exchRateList = new ArrayList();
		ArrayList dangerCoinsList = new ArrayList();
		ArrayList planList = new ArrayList();

		HttpServletRequest req = this.getRequest();

		String certiNo = req.getParameter("certiNo");
		String certiType = req.getParameter("certiType");
		String type = req.getParameter("type");
		QueryRule queryRule;

		String policyNo = "";
		// add by dongyanqi
		EnquiryVO tempEnquiryDto = null;
		String tempEnquiryNo = "";
		Collection feoEquiryList = new ArrayList();
		if (certiType != null && !certiType.equals("")) {
			if (certiType.equals("T")) {
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("proposalNo", certiNo);
				feoEquiryList = enquiryService.findByConditions(queryRule);
			} else if (certiType.equals("P")) {
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("policyNo", certiNo);
				feoEquiryList = enquiryService.findByConditions(queryRule);
			} else if (certiType.equals("E")) {
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("endorseNo", certiNo);
				feoEquiryList = enquiryService.findByConditions(queryRule);
			}
		}
		try {
			if (type.equals("verifyEnquiry")) {
				HttpSession session = req.getSession();
				PrpDuserDto user = (PrpDuserDto) session.getAttribute("user");
				String operatorCode = user.getUserCode(); // 用户代码
				List<String> enquiryNos = Arrays.asList(EnquiryNo);
				blEnquiryService.verifyEnquiry(enquiryNos, operatorCode);

				content = getText("undwrt.action.enquiryReins.submitReinsureSuccess");// ??是否需要url的设置
				forward = "verifyEnquirySuccess";
			}
			// modify begin 20071012 by lihua 提交分入确认
			if (type.equals("reinsVerify")) {
				String proposalNo = req.getParameter("proposalNo");
				String riskCode = req.getParameter("riskCode");
				String classCode = req.getParameter("hiClassCode");
				String endorNo = req.getParameter("endorNo");
				policyNo = req.getParameter("policyNo");
				String CertiType = null;
				if (policyNo == null || policyNo.equals("")) {
					policyNo = proposalNo;
				}
				if (!(proposalNo == null || proposalNo.equals(""))) {
					certiNo = proposalNo;
					CertiType = "T";
				} else {
					certiNo = endorNo;
					CertiType = "E";
				}
				verifyService.ReinsVerify(CertiType, certiNo, policyNo,
						classCode, riskCode);
				content = getText("undwrt.action.enquiryReins.submitReinsureSuccess");
				forward = "verifyEnquirySuccess";
			}
			// modify end 20071012 by lihua
			if (type.equals("saveReinsReceive"))
			{
				// 根据临分意向页面修改内容更新enquiryDto中feoEnquiry及feoReinsReceive
				if("F01".equals(riskCode))
				{
					enquiryVoList = (ArrayList<EnquiryVO>) this.getReinsReceiveList(req, (ArrayList)feoEquiryList);
				}
				else
				{
					if(feoEquiryList.size()>0)
					{	
						Iterator iter = feoEquiryList.iterator();
						while (iter.hasNext()) {
							tempEnquiryDto = (EnquiryVO) iter.next();
							tempEnquiryNo = tempEnquiryDto.getFeoEnquiryVO().getEnquiryNo();
							break;
						}
					}
				}
					// 根据投保单数据更新enquiryDto中feoItem、feoTot、FeoEngage
					if (certiType.equals("T"))
					{
						itemKindList = (ArrayList) prpallService.getPrpTitemKindList(certiNo);
						engageList = (ArrayList) prpallService.getPrpTengageList(certiNo);
						exchRateList = (ArrayList) prpallService.getExchangeRate(certiType, certiNo);
						// add begin by zhaijq 20051228 临分询价处理联共保信息
						dangerCoinsList = (ArrayList) prpallService.getPrpDangerCoinsList(certiNo, certiType);
					// add by yangxintao 2011-6-24
						planList = (ArrayList) prpallService.getPrpDangerPlanList(certiNo, certiType);
					}
					else if (certiType.equals("P"))
					{
						itemKindList = (ArrayList) prpallService.getPrpCitemKindList(certiNo);
						engageList = (ArrayList) prpallService.getPrpTengageList(certiNo);
						exchRateList = (ArrayList) prpallService.getExchangeRate(certiType, certiNo);
						// add begin by zhaijq 20051228 临分询价处理联共保信息
						dangerCoinsList = (ArrayList) prpallService.getPrpDangerCoinsList(certiNo, certiType);
						// add end by zhaijq 20051228
					}
					else if (certiType.equals("E"))
					{
					policyNo = req.getParameter("policyNo");
					itemKindList = (ArrayList) prpallService.getPrpCPitemKindList(policyNo);
					engageList = (ArrayList) prpallService.getPrpCPengageList(policyNo);
					exchRateList = (ArrayList) prpallService.getExchangeRate(certiType, certiNo);
					// add begin by zhaijq 20051228 临分询价处理联共保信息
					dangerCoinsList = (ArrayList) prpallService.getPrpDangerCoinsList(certiNo, certiType);
					// add end by zhaijq 20051228
					}
					Collection dangerCoinsVOList = prpDangerCoinsToVOList(dangerCoinsList, certiType);
			if("F01".equals(riskCode))
			{
					// modify begin by zhaijq 20051228 临分询价处理联共保信息
					blEnquiryService.UpdateEnquiryDto(enquiryVoList,certiType, itemKindList, engageList, exchRateList,
							dangerCoinsVOList, planList);
					// modify end by zhaijq 20051228
				try
				{
					blEnquiryService.save(enquiryVoList, certiType);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// modify begin by zhaijq 20100527 临分询价在双核保存后，再展示原来的临分超赔安排信息
//				Collection feoXFacDtoList = blFacXLayerService.findFeoXFac(
//						enquiryNo, "undwrt");
//				req.setAttribute("feoXFacDtoList", feoXFacDtoList);
				// modify end by zhaijq 20100527 临分询价在双核保存后，再展示原来的临分超赔安排信息
				blEnquiryService.reinsReceiveToRequest(req, enquiryVoList);
				req.setAttribute("content",
						getText("undwrt.action.enquiryReins.inforSaveSuccess"));// ??是否需要url的设置
				forward = "success";
			}
			else
			{
				if (!tempEnquiryNo.equals(""))
				{ // 如果有询价单号说明是对原有信息的更新，需要先获取现有询价单数据
					enquiryVo = enquiryService.findByPK(tempEnquiryNo);
				}
				else
				{ 
					enquiryVo = new EnquiryVO();
				}
				enquiryVo = this.getReinsReceiveVo(req, enquiryVo);
				enquiryVo = blEnquiryService.UpdateEnquiryDto(enquiryVo,
						certiType, itemKindList, engageList, exchRateList,
						dangerCoinsVOList, planList);
				// modify end by zhaijq 20051228
				try {
					EnquiryNo = new String[1];
					EnquiryNo[0] = blEnquiryService.save(enquiryVo, certiType);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("---------");
				// modify begin by zhaijq 20100527 临分询价在双核保存后，再展示原来的临分超赔安排信息
				Collection feoXFacDtoList = blFacXLayerService.findFeoXFac(
						EnquiryNo[0], "undwrt");
				req.setAttribute("feoXFacDtoList", feoXFacDtoList);
				// modify end by zhaijq 20100527 临分询价在双核保存后，再展示原来的临分超赔安排信息
				blEnquiryService.reinsReceiveToRequest(req, enquiryVo);
				req.setAttribute("content",
						getText("undwrt.action.enquiryReins.inforSaveSuccess"));// ??是否需要url的设置
				req.setAttribute("EnquiryNo", EnquiryNo[0]);
				forward = "success";
			}
			}
		}
		catch (UserException usee) {
			throw usee;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
		return forward;
	}

	/**
	 * 獲取屬性商火詢價單分保接受人訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @param enquiryDto
	 *            臨分詢價單訊息
	 * @return 屬性詢價單分保接受人訊息的值
	 * @throws Exception
	 *             異常
	 */
	public Collection<EnquiryVO> getReinsReceiveList(HttpServletRequest req,
			ArrayList feoEquiryList) throws Exception {
		HttpSession session = req.getSession();
		ArrayList paramList = new ArrayList();
		EnquiryVO enquiryVO = new EnquiryVO();
		FeoEnquiryVO feoEnquiryDto = new FeoEnquiryVO();
		String specialFacFlag[] = req
				.getParameterValues("CheckBoxSpecialFacShareHidden");
		String[] facFlag0 = req.getParameterValues("CheckBoxFacShareHidden");
		DateTime createDate = new DateTime(DateTime.current(), 13);
		String optType = (String) session.getAttribute("OptType");
		PrpDuserDto user = (PrpDuserDto) session.getAttribute("user");
		String operatorCode = user.getUserCode();
		String businessNo = req.getParameter("certiNo");
		String certiType = req.getParameter("certiType");
		String policyNo = req.getParameter("policyNo");
		String proposalNo = req.getParameter("proposalNo");
		String riskCode = req.getParameter("RiskCode");
		String remarks[] = req.getParameterValues("Remarks");
		String specialFacShare[] = req.getParameterValues("SpecialFacShare");
		String facShare[] = req.getParameterValues("FacShare");
		String feoEnquiryDtoVerifyFlag = req
				.getParameter("feoEnquiryDtoVerifyFlag");
		String dangerNo = req.getParameter("dangerNo");
		if (dangerNo == null)
			dangerNo = "1";
		String feoReinsReceiveDiffFlag[] = req
				.getParameterValues("feoReinsReceiveDiffFlag");
		String feoReinsReceiveCurrency[] = req
				.getParameterValues("feoReinsReceiveCurrency");
		String feoReinsReceiveAmount[] = req
				.getParameterValues("feoReinsReceiveAmount");
		String feoReinsReceiveRate[] = req
				.getParameterValues("feoReinsReceiveRate");
		String feoReinsReceivePremium[] = req
				.getParameterValues("feoReinsReceivePremium");
		String feoReinsReceiveDeductible[] = req
				.getParameterValues("feoReinsReceiveDeductible");
		String feoReinsReceiveDeductibleRate[] = req
				.getParameterValues("feoReinsReceiveDeductibleRate");
		String feoReinsReceiveRemarks[] = req
				.getParameterValues("feoReinsReceiveRemarks");
		String feoReinsReceiveSpecialProvisions[] = req
				.getParameterValues("feoReinsReceiveSpecialProvisions");
		String reinsCode[] = req.getParameterValues("ReinsCode");
		String reinsName[] = req.getParameterValues("ReinsName");
		String finalReinsCode[] = req.getParameterValues("FinalReinsCode");
		String finalReinsName[] = req.getParameterValues("FinalReinsName");
		String shareRate[] = req.getParameterValues("ShareRate");
		String commRate[] = req.getParameterValues("CommRate");
		String writtenLine[] = req.getParameterValues("WrittenLine");
		String writtenComm[] = req.getParameterValues("WrittenComm");
		String offeredLine[] = req.getParameterValues("OfferedLine");
		String offeredComm[] = req.getParameterValues("OfferedComm");
		String taxRate[] = req.getParameterValues("TaxRate");
		String othRate[] = req.getParameterValues("OthRate");
		String reinsType[] = req.getParameterValues("feoReinsReceiveReinsType");
		String facFlag[] = req.getParameterValues("feoReinsReceiveFacFlag");
		String currencyFlag[] = req
				.getParameterValues("feoReinsReceiveCurrencyFlag");
		String assessLevel[] = req.getParameterValues("assessLevel");
		String assessLevel2[] = req.getParameterValues("assessLevel2");
		String assessLevel3[] = req.getParameterValues("assessLevel3");
		String assessLevel4[] = req.getParameterValues("assessLevel4");
		String assessLevel5[] = req.getParameterValues("assessLevel5");
		String feoReinsReceiveChgAmount[] = (String[]) null;
		String feoReinsReceiveChgPremium[] = (String[]) null;
		 Date startDate = null;
		 Date endDate = null;
		if (certiType.equals("E"))
		{
			feoReinsReceiveChgAmount = req
					.getParameterValues("feoReinsReceiveChgAmount");
			feoReinsReceiveChgPremium = req
					.getParameterValues("feoReinsReceiveChgPremium");
		}
		for(int i=0;i<this.dangerNo.length;i++)
		{	
			enquiryVO = new EnquiryVO();
			if(i<feoEquiryList.size())
			{
				feoEnquiryDto = ((EnquiryVO)feoEquiryList.get(i)).getFeoEnquiryVO();
			}
			else
			{
				feoEnquiryDto = new FeoEnquiryVO();
			}
			feoEnquiryDto.setDangerNo(Integer.parseInt(this.dangerNo[i]));
			if (certiType.equals("T"))
				feoEnquiryDto.setProposalNo(businessNo);
			else if (certiType.equals("P"))
				feoEnquiryDto.setPolicyNo(businessNo);
			else if (certiType.equals("E"))
			{
				feoEnquiryDto.setEndorseNo(businessNo);
				feoEnquiryDto.setPolicyNo(policyNo);
				feoEnquiryDto.setProposalNo(proposalNo);
			}
			feoEnquiryDto.setRiskCode(riskCode);
			feoEnquiryDto.setRemarks(remarks[i]);
			feoEnquiryDto.setSpecialFacFlag(specialFacFlag[i]);
			feoEnquiryDto.setFacFlag(facFlag0[i]);
			feoEnquiryDto.setVerifyFlag(feoEnquiryDtoVerifyFlag);
			feoEnquiryDto.setReinsured(AppConfig.get("sysconst.COM_CNAME_LONG"));
			if (specialFacShare[i].length() == 0)
				specialFacShare[i] = "0";
			if (facShare[i].length() == 0)
				facShare[i] = "0";
			feoEnquiryDto.setSpecialFacShare(Double.parseDouble(specialFacShare[i]));
			feoEnquiryDto.setFacShare(Double.parseDouble(facShare[i]));
			feoEnquiryDto.setFlag("0000000000");
			if (optType.equals("update"))
			{
				feoEnquiryDto.setModifierCode(operatorCode);
				feoEnquiryDto.setModifyDate(createDate);
			} 
			else
			{
				feoEnquiryDto.setOperatorCode(operatorCode);
				feoEnquiryDto.setCreateDate(createDate);
			}
			enquiryVO.setFeoEnquiryVO(feoEnquiryDto);
			paramList.add(enquiryVO);
		}
		ArrayList feoReinsReceiveList = new ArrayList();
		for (int i = 1; i < reinsCode.length; i++)
		{
			if(null!=reinsCode[i] && !"".equals(reinsCode[i]))
			{
			FeoReinsReceiveVO feoReinsReceiveDto = new FeoReinsReceiveVO();
			if (certiType.equals("T"))
			{   startDate=policyService.getPrpTmainByProposalNo(businessNo).getStartDate();
				endDate=policyService.getPrpTmainByProposalNo(businessNo).getEndDate();
				feoReinsReceiveDto.setStartDate(new DateTime(startDate));
				feoReinsReceiveDto.setEndDate(new DateTime(endDate));
				feoReinsReceiveDto.setProposalNo(businessNo);
			}
			else if (certiType.equals("P"))
			{
				feoReinsReceiveDto.setPolicyNo(businessNo);
			}
			else if (certiType.equals("E"))
			{
				startDate=endorseService.getPrpPheadByEndorseNo(businessNo).getValidDate();
				endDate = endorseService.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0).getEndDate();
				feoReinsReceiveDto.setStartDate(new DateTime(startDate));
				feoReinsReceiveDto.setEndDate(new DateTime(endDate));
				feoReinsReceiveDto.setEndorseNo(businessNo);
				feoReinsReceiveDto.setPolicyNo(policyNo);
				feoReinsReceiveDto.setProposalNo(proposalNo);
			}
			feoReinsReceiveDto.setRiskCode(riskCode);
			feoReinsReceiveDto.setSerialNo(i);
			feoReinsReceiveDto.setReinsCode(reinsCode[i]);
			feoReinsReceiveDto.setReinsType(reinsType[i]);
			feoReinsReceiveDto.setReinsName(reinsName[i]);
			feoReinsReceiveDto.setFinalReinsCode(finalReinsCode[i]);
			feoReinsReceiveDto.setFinalReinsName(finalReinsName[i]);
			feoReinsReceiveDto.setPayCode(reinsCode[i]);
			feoReinsReceiveDto.setPayName(reinsName[i]);
			feoReinsReceiveDto.setAssessLevel(assessLevel[i]);
			feoReinsReceiveDto.setAssessLevel2(assessLevel2[i]);
			feoReinsReceiveDto.setAssessLevel3(assessLevel3[i]);
			feoReinsReceiveDto.setAssessLevel4(assessLevel4[i]);
			feoReinsReceiveDto.setAssessLevel5(assessLevel5[i]);
			feoReinsReceiveDto.setDangerNo(Integer.valueOf(addDangerNo[i]));
			feoReinsReceiveDto
					.setUpdateDate(new DateTime((new SimpleDateFormat(
							"yyyy-MM-dd")).format(new Date()), 13));
			if (shareRate[i].length() == 0)
				shareRate[i] = "0";
			if (commRate[i].length() == 0)
				commRate[i] = "0";
			if (taxRate[i].length() == 0)
				taxRate[i] = "0";
			if (othRate[i].length() == 0)
				othRate[i] = "0";
			feoReinsReceiveDto.setSignedLine(Double.parseDouble(shareRate[i]
					.equals("") ? "0" : shareRate[i]));
			feoReinsReceiveDto.setSignedComm(Double.parseDouble(commRate[i]
					.equals("") ? "0" : commRate[i]));
			feoReinsReceiveDto.setWrittenLine(Double.parseDouble(writtenLine[i]
					.equals("") ? "0" : writtenLine[i]));
			feoReinsReceiveDto.setWrittenComm(Double.parseDouble(writtenComm[i]
					.equals("") ? "0" : writtenComm[i]));
			feoReinsReceiveDto.setOfferedLine(Double.parseDouble(offeredLine[i]
					.equals("") ? "0" : offeredLine[i]));
			feoReinsReceiveDto.setOfferedComm(Double.parseDouble(offeredComm[i]
					.equals("") ? "0" : offeredComm[i]));
			feoReinsReceiveDto.setTaxRate(Double.parseDouble(taxRate[i]
					.equals("") ? "0" : taxRate[i]));
			feoReinsReceiveDto.setOthRate(Double.parseDouble(othRate[i]
					.equals("") ? "0" : othRate[i]));
			feoReinsReceiveDto.setCurrencyFlag(currencyFlag[i]);
			feoReinsReceiveDto.setFacFlag(facFlag[i]);
			feoReinsReceiveDto.setCurrency(feoReinsReceiveCurrency[i]);
			feoReinsReceiveDto.setAmount(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveAmount[i])));
			feoReinsReceiveDto.setRate(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveRate[i])));
			feoReinsReceiveDto.setPremium(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceivePremium[i])));
			feoReinsReceiveDto.setDeductible(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveDeductible[i])));
			feoReinsReceiveDto
					.setDeductibleRate(Double.parseDouble(UIFormatAction
							.formatNumberToString(feoReinsReceiveDeductibleRate[i])));
			feoReinsReceiveDto
					.setSpecialProvisions(feoReinsReceiveSpecialProvisions[i]);
			String feoReinsReceiveRemark = "";
			if (feoReinsReceiveRemarks != null
					&& feoReinsReceiveRemarks[i] != null)
				feoReinsReceiveRemark = feoReinsReceiveRemarks[i];
			feoReinsReceiveDto.setRemark(feoReinsReceiveRemark);
			feoReinsReceiveDto.setFlag(feoReinsReceiveDiffFlag[i]);
			if (certiType.equals("E"))
			{
				feoReinsReceiveDto
						.setChgAmount(Double.parseDouble(UIFormatAction
								.formatNumberToString(feoReinsReceiveChgAmount[i])));
				feoReinsReceiveDto
						.setChgPremium(Double.parseDouble(UIFormatAction
								.formatNumberToString(feoReinsReceiveChgPremium[i])));
			}
			if (feoReinsReceiveDiffFlag[i].substring(0, 3).indexOf("1") >= 0)
				feoEnquiryDto.setFlag("1000000000");
			feoReinsReceiveList.add(feoReinsReceiveDto);
		}
		}
		ArrayList<EnquiryVO> dataList = new ArrayList<EnquiryVO>();
		ArrayList<FeoReinsReceiveVO> receiveList = null;
		for(int i=0;i<paramList.size();i++)
		{
			EnquiryVO tempEnquiryVO = new EnquiryVO();
			tempEnquiryVO = (EnquiryVO) paramList.get(i);
			receiveList = new ArrayList<FeoReinsReceiveVO>();
			for(int j=0;j<feoReinsReceiveList.size();j++)
			{	
				FeoReinsReceiveVO tempReceiveVO = (FeoReinsReceiveVO) feoReinsReceiveList.get(j);
				if(tempEnquiryVO.getFeoEnquiryVO().getDangerNo()==tempReceiveVO.getDangerNo())
				{
					receiveList.add(tempReceiveVO);
				}
			}
			tempEnquiryVO.setFeoReinsReceiveVOList(receiveList);
			dataList.add(tempEnquiryVO);
		}
		return dataList;
	}
	/**
	 * 獲取屬性詢價單分保接受人訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @param enquiryDto
	 *            臨分詢價單訊息
	 * @return 屬性詢價單分保接受人訊息的值
	 * @throws Exception
	 *             異常
	 */
	public EnquiryVO getReinsReceiveVo(HttpServletRequest req,
			EnquiryVO enquiryDto) throws Exception {
		HttpSession session = req.getSession();
		FeoEnquiryVO feoEnquiryDto = new FeoEnquiryVO();
		String specialFacFlag = req
				.getParameter("CheckBoxSpecialFacShareHidden");
		String facFlag0 = req.getParameter("CheckBoxFacShareHidden");
		DateTime createDate = new DateTime(DateTime.current(), 13);
		String optType = (String) session.getAttribute("OptType");
		PrpDuserDto user = (PrpDuserDto) session.getAttribute("user");
		String operatorCode = user.getUserCode();
		String businessNo = req.getParameter("certiNo");
		String certiType = req.getParameter("certiType");
		String policyNo = req.getParameter("policyNo");
		String proposalNo = req.getParameter("proposalNo");
		String riskCode = req.getParameter("RiskCode");
		String remarks = req.getParameter("Remarks");
		String specialFacShare = req.getParameter("SpecialFacShare");
		String facShare = req.getParameter("FacShare");
		String feoEnquiryDtoVerifyFlag = req
				.getParameter("feoEnquiryDtoVerifyFlag");
		String dangerNo = req.getParameter("dangerNo");
		if (dangerNo == null)
			dangerNo = "1";
		String feoReinsReceiveDiffFlag[] = req
				.getParameterValues("feoReinsReceiveDiffFlag");
		String feoReinsReceiveCurrency[] = req
				.getParameterValues("feoReinsReceiveCurrency");
		String feoReinsReceiveAmount[] = req
				.getParameterValues("feoReinsReceiveAmount");
		String feoReinsReceiveRate[] = req
				.getParameterValues("feoReinsReceiveRate");
		String feoReinsReceivePremium[] = req
				.getParameterValues("feoReinsReceivePremium");
		String feoReinsReceiveDeductible[] = req
				.getParameterValues("feoReinsReceiveDeductible");
		String feoReinsReceiveDeductibleRate[] = req
				.getParameterValues("feoReinsReceiveDeductibleRate");
		String feoReinsReceiveRemarks[] = req
				.getParameterValues("feoReinsReceiveRemarks");
		String feoReinsReceiveSpecialProvisions[] = req
				.getParameterValues("feoReinsReceiveSpecialProvisions");
		String reinsCode[] = req.getParameterValues("ReinsCode");
		String reinsName[] = req.getParameterValues("ReinsName");
		String finalReinsCode[] = req.getParameterValues("FinalReinsCode");
		String finalReinsName[] = req.getParameterValues("FinalReinsName");
		String shareRate[] = req.getParameterValues("ShareRate");
		String commRate[] = req.getParameterValues("CommRate");
		String writtenLine[] = req.getParameterValues("WrittenLine");
		String writtenComm[] = req.getParameterValues("WrittenComm");
		String offeredLine[] = req.getParameterValues("OfferedLine");
		String offeredComm[] = req.getParameterValues("OfferedComm");
		String taxRate[] = req.getParameterValues("TaxRate");
		String othRate[] = req.getParameterValues("OthRate");
		String reinsType[] = req.getParameterValues("feoReinsReceiveReinsType");
		String facFlag[] = req.getParameterValues("feoReinsReceiveFacFlag");
		String currencyFlag[] = req
				.getParameterValues("feoReinsReceiveCurrencyFlag");
		String assessLevel[] = req.getParameterValues("assessLevel");
		String assessLevel2[] = req.getParameterValues("assessLevel2");
		String assessLevel3[] = req.getParameterValues("assessLevel3");
		String assessLevel4[] = req.getParameterValues("assessLevel4");
		String assessLevel5[] = req.getParameterValues("assessLevel5");
		String feoReinsReceiveChgAmount[] = (String[]) null;
		String feoReinsReceiveChgPremium[] = (String[]) null;
		 Date startDate = null;
		 Date endDate = null;
		if (certiType.equals("E")) {
			feoReinsReceiveChgAmount = req
					.getParameterValues("feoReinsReceiveChgAmount");
			feoReinsReceiveChgPremium = req
					.getParameterValues("feoReinsReceiveChgPremium");
		}
		feoEnquiryDto = enquiryDto.getFeoEnquiryVO();
		feoEnquiryDto.setDangerNo(Integer.parseInt(dangerNo));
		if (certiType.equals("T"))
			feoEnquiryDto.setProposalNo(businessNo);
		else if (certiType.equals("P"))
			feoEnquiryDto.setPolicyNo(businessNo);
		else if (certiType.equals("E")) {
			feoEnquiryDto.setEndorseNo(businessNo);
			feoEnquiryDto.setPolicyNo(policyNo);
			feoEnquiryDto.setProposalNo(proposalNo);
		}
		feoEnquiryDto.setRiskCode(riskCode);
		feoEnquiryDto.setRemarks(remarks);
		feoEnquiryDto.setSpecialFacFlag(specialFacFlag);
		feoEnquiryDto.setFacFlag(facFlag0);
		feoEnquiryDto.setVerifyFlag(feoEnquiryDtoVerifyFlag);
		feoEnquiryDto.setReinsured(AppConfig.get("sysconst.COM_CNAME_LONG"));
		if (specialFacShare.length() == 0)
			specialFacShare = "0";
		if (facShare.length() == 0)
			facShare = "0";
		feoEnquiryDto.setSpecialFacShare(Double.parseDouble(specialFacShare));
		feoEnquiryDto.setFacShare(Double.parseDouble(facShare));
		feoEnquiryDto.setFlag("0000000000");
		if (optType.equals("update")) {
			feoEnquiryDto.setModifierCode(operatorCode);
			feoEnquiryDto.setModifyDate(createDate);
		} else {
			feoEnquiryDto.setOperatorCode(operatorCode);
			feoEnquiryDto.setCreateDate(createDate);
		}
		ArrayList feoReinsReceiveList = new ArrayList();
		for (int i = 1; i < reinsCode.length; i++) {
			FeoReinsReceiveVO feoReinsReceiveDto = new FeoReinsReceiveVO();
			if (certiType.equals("T"))
			{   startDate=policyService.getPrpTmainByProposalNo(businessNo).getStartDate();
				endDate=policyService.getPrpTmainByProposalNo(businessNo).getEndDate();
				feoReinsReceiveDto.setStartDate(new DateTime(startDate));
				feoReinsReceiveDto.setEndDate(new DateTime(endDate));
				feoReinsReceiveDto.setProposalNo(businessNo);
			}
			else if (certiType.equals("P"))
			{
				feoReinsReceiveDto.setPolicyNo(businessNo);
			}
			else if (certiType.equals("E")) {
				startDate=endorseService.getPrpPheadByEndorseNo(businessNo).getValidDate();
				endDate = endorseService.getPrpPheadByEndorseNo(businessNo).getPrpPmains().get(0).getEndDate();
				feoReinsReceiveDto.setStartDate(new DateTime(startDate));
				feoReinsReceiveDto.setEndDate(new DateTime(endDate));
				feoReinsReceiveDto.setEndorseNo(businessNo);
				feoReinsReceiveDto.setPolicyNo(policyNo);
				feoReinsReceiveDto.setProposalNo(proposalNo);
			}
			feoReinsReceiveDto.setRiskCode(riskCode);
			feoReinsReceiveDto.setSerialNo(i);
			feoReinsReceiveDto.setReinsCode(reinsCode[i]);
			feoReinsReceiveDto.setReinsType(reinsType[i]);
			feoReinsReceiveDto.setReinsName(reinsName[i]);
			feoReinsReceiveDto.setFinalReinsCode(finalReinsCode[i]);
			feoReinsReceiveDto.setFinalReinsName(finalReinsName[i]);
			feoReinsReceiveDto.setPayCode(reinsCode[i]);
			feoReinsReceiveDto.setPayName(reinsName[i]);
			feoReinsReceiveDto.setAssessLevel(assessLevel[i]);
			feoReinsReceiveDto.setAssessLevel2(assessLevel2[i]);
			feoReinsReceiveDto.setAssessLevel3(assessLevel3[i]);
			feoReinsReceiveDto.setAssessLevel4(assessLevel4[i]);
			feoReinsReceiveDto.setAssessLevel5(assessLevel5[i]);
			feoReinsReceiveDto
					.setUpdateDate(new DateTime((new SimpleDateFormat(
							"yyyy-MM-dd")).format(new Date()), 13));
			if (shareRate[i].length() == 0)
				shareRate[i] = "0";
			if (commRate[i].length() == 0)
				commRate[i] = "0";
			if (taxRate[i].length() == 0)
				taxRate[i] = "0";
			if (othRate[i].length() == 0)
				othRate[i] = "0";
			feoReinsReceiveDto.setSignedLine(Double.parseDouble(shareRate[i]
					.equals("") ? "0" : shareRate[i]));
			feoReinsReceiveDto.setSignedComm(Double.parseDouble(commRate[i]
					.equals("") ? "0" : commRate[i]));
			feoReinsReceiveDto.setWrittenLine(Double.parseDouble(writtenLine[i]
					.equals("") ? "0" : writtenLine[i]));
			feoReinsReceiveDto.setWrittenComm(Double.parseDouble(writtenComm[i]
					.equals("") ? "0" : writtenComm[i]));
			feoReinsReceiveDto.setOfferedLine(Double.parseDouble(offeredLine[i]
					.equals("") ? "0" : offeredLine[i]));
			feoReinsReceiveDto.setOfferedComm(Double.parseDouble(offeredComm[i]
					.equals("") ? "0" : offeredComm[i]));
			feoReinsReceiveDto.setTaxRate(Double.parseDouble(taxRate[i]
					.equals("") ? "0" : taxRate[i]));
			feoReinsReceiveDto.setOthRate(Double.parseDouble(othRate[i]
					.equals("") ? "0" : othRate[i]));
			feoReinsReceiveDto.setCurrencyFlag(currencyFlag[i]);
			feoReinsReceiveDto.setFacFlag(facFlag[i]);
			feoReinsReceiveDto.setCurrency(feoReinsReceiveCurrency[i]);
			feoReinsReceiveDto.setAmount(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveAmount[i])));
			feoReinsReceiveDto.setRate(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveRate[i])));
			feoReinsReceiveDto.setPremium(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceivePremium[i])));
			feoReinsReceiveDto.setDeductible(Double.parseDouble(UIFormatAction
					.formatNumberToString(feoReinsReceiveDeductible[i])));
			feoReinsReceiveDto
					.setDeductibleRate(Double.parseDouble(UIFormatAction
							.formatNumberToString(feoReinsReceiveDeductibleRate[i])));
			feoReinsReceiveDto
					.setSpecialProvisions(feoReinsReceiveSpecialProvisions[i]);
			String feoReinsReceiveRemark = "";
			if (feoReinsReceiveRemarks != null
					&& feoReinsReceiveRemarks[i] != null)
				feoReinsReceiveRemark = feoReinsReceiveRemarks[i];
			feoReinsReceiveDto.setRemark(feoReinsReceiveRemark);
			feoReinsReceiveDto.setFlag(feoReinsReceiveDiffFlag[i]);
			if (certiType.equals("E")) {
				feoReinsReceiveDto
						.setChgAmount(Double.parseDouble(UIFormatAction
								.formatNumberToString(feoReinsReceiveChgAmount[i])));
				feoReinsReceiveDto
						.setChgPremium(Double.parseDouble(UIFormatAction
								.formatNumberToString(feoReinsReceiveChgPremium[i])));
			}
			if (feoReinsReceiveDiffFlag[i].substring(0, 3).indexOf("1") >= 0)
				feoEnquiryDto.setFlag("1000000000");
			feoReinsReceiveList.add(feoReinsReceiveDto);
		}

		enquiryDto.setFeoEnquiryVO(feoEnquiryDto);
		enquiryDto.setFeoReinsReceiveVOList(feoReinsReceiveList);
		return enquiryDto;
	}
	/**
	 * 投保單危險單位共保資訊轉成VO集合.
	 * 
	 * @param prpDangerCoinsList
	 *            危險單位共保資訊集合
	 * @param certiType
	 *            業務類型
	 * @return 危險單位共保資訊集合
	 */
	public Collection prpDangerCoinsToVOList(Collection prpDangerCoinsList,
			String certiType) {
		Collection list = new ArrayList();

		if (certiType.equals("T")) {
			for (Iterator<PrpTDangerCoins> it = prpDangerCoinsList.iterator(); it
					.hasNext();) {
				list.add(new PrpTDangerCoinsVO(it.next()));
			}
		} else if (certiType.equals("P")) {
			for (Iterator<PrpCDangerCoins> it = prpDangerCoinsList.iterator(); it
					.hasNext();) {
				list.add(new PrpCDangerCoinsVO(it.next()));
			}
		} else if (certiType.equals("E")) {
			for (Iterator<PrpPDangerCoins> it = prpDangerCoinsList.iterator(); it
					.hasNext();) {
				list.add(new PrpPDangerCoinsVO(it.next()));
			}
		}

		return list;
	}

	public String[] getEnquiryNo() {
		return EnquiryNo;
	}

	public void setEnquiryNo(String[] enquiryNo) {
		EnquiryNo = enquiryNo;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * 獲取屬性再保詢價單處理接口.
	 * 
	 * @return 屬性再保詢價單處理接口的值
	 */
	public EnquiryService getEnquiryService() {
		return enquiryService;
	}

	/**
	 * 設置屬性再保詢價單處理接口.
	 * 
	 * @param enquiryService
	 *            待設置的再保詢價單處理接口的值
	 */
	public void setEnquiryService(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}

	/**
	 * 獲取屬性臨分超賠接口.
	 * 
	 * @return 屬性臨分超賠接口的值
	 */
	public BLFacXLayerService getBlFacXLayerService() {
		return blFacXLayerService;
	}

	/**
	 * 設置屬性臨分超賠接口.
	 * 
	 * @param blFacXLayerService
	 *            臨分超賠接口
	 */
	public void setBlFacXLayerService(BLFacXLayerService blFacXLayerService) {
		this.blFacXLayerService = blFacXLayerService;
	}

	/**
	 * 獲取屬性再保確認接口.
	 * 
	 * @return 屬性再保確認接口的值
	 */
	public VerifyService getVerifyService() {
		return verifyService;
	}

	/**
	 * 設置屬性再保確認接口.
	 * 
	 * @param verifyService
	 *            待設置的再保確認接口的值
	 */
	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}

	/**
	 * 獲取屬性臨分詢價單處理接口.
	 * 
	 * @return 屬性臨分詢價單處理接口的值
	 */
	public BLEnquiryService getBlEnquiryService() {
		return blEnquiryService;
	}

	/**
	 * 設置屬性臨分詢價單處理接口.
	 * 
	 * @param blEnquiryService
	 *            待設置的臨分詢價單處理接口的值
	 */
	public void setBlEnquiryService(BLEnquiryService blEnquiryService) {
		this.blEnquiryService = blEnquiryService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @return 要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置要保書處理接口的值.
	 * 
	 * @param policyService
	 *            待設置設置要保書處理接口的值
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
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public String[] getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(String[] dangerNo) {
		this.dangerNo = dangerNo;
	}

	public String[] getAddDangerNo() {
		return addDangerNo;
	}

	public void setAddDangerNo(String[] addDangerNo) {
		this.addDangerNo = addDangerNo;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getCertiNo() {
		return certiNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param certiNo
	 *            待設置的業務號的值
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getCertiType() {
		return certiType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param certiType
	 *            待設置的業務類型的值
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	public String getDangerNos() {
		return dangerNos;
	}

	public void setDangerNos(String dangerNos) {
		this.dangerNos = dangerNos;
	}
	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	public String getiRiskCode() {
		return iRiskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param iRiskCode
	 *            待設置的險種代碼的值
	 */
	public void setiRiskCode(String iRiskCode) {
		this.iRiskCode = iRiskCode;
	}

	public String getWhetherFacing() {
		return whetherFacing;
	}

	public void setWhetherFacing(String whetherFacing) {
		this.whetherFacing = whetherFacing;
	}
	
}
