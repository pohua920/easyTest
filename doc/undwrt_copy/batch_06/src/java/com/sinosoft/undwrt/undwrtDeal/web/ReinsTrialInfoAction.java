package com.sinosoft.undwrt.undwrtDeal.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.function.insutil.dto.domain.PrpUserGradeDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.reins.out.facultative.enquiry.model.Enquiry;
import com.sinosoft.reins.common.service.facade.BLReinsLTrialService;
import com.sinosoft.reins.common.service.facade.PrpTdangerUnitService;
import com.sinosoft.reins.base.model.FcoRepolicy;
import com.sinosoft.reins.base.service.facade.FcoRepolicyService;
import com.sinosoft.reins.out.facultative.enquiry.model.FeoEnquiry;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.EnquiryService;
import com.sinosoft.reins.out.facultative.enquiry.vo.EnquiryVO;
import com.sinosoft.reins.out.facultative.enquiry.vo.FeoEnquiryVO;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 再保信息處理類.
 */
public class ReinsTrialInfoAction extends Struts2Action {
	
	/** 屬性核保系統幫助服務接口. */
	private WfLogHelperService wfLogHelperService;
	
	/** 屬性危險單位信息服務接口. */
	private CommonDangerInfoService commonDangerInfoService;
	
	/** 屬性再保詢價單處理接口. */
	private EnquiryService enquiryService;
	
	/** 屬性 分保單主信息處理接口. */
	private FcoRepolicyService fcoRepolicyService;
	
	/** 屬性投保單的危險單位劃分接口. */
	private PrpTdangerUnitService prpTdangerUnitService;
	
	/** 屬性分攤試算處理接口. */
	private BLReinsLTrialService blReinsLTrialService;

	/**
	 * 獲取分保試算信息.
	 * 
	 * @return 頁面跳轉結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String getReinsTrialInfo() throws UserException, Exception {
		HttpServletRequest req = this.getRequest();
		PrpDuserDto prpDuserDto = (PrpDuserDto) req.getSession().getAttribute(
				"user");
		String businessNo = req.getParameter("CertiNo");
		String businessType = req.getParameter("CertiType");
		String classCode = req.getParameter("ClassCode");
		String hiRetCurrency = req.getParameter("hiRetCurrency");
		String hiRetentionValue = req.getParameter("hiRetentionValue");
		String exItemKind = req.getParameter("ExItemKind");
		String exItemFlag = req.getParameter("ExItemFlag");
		String ClaimNo = req.getParameter("ClaimNo");
		String UserCode = "";
		String forward = "";
		String dangerNo = "";
		String VerifyFlag = "";
		String UnderWriteFlag = "";
		PrpUserGradeDto prpUserGradeDto = null;
		String strContent; 
		try{ 
			strContent = wfLogHelperService.simulateRepolicyByDangerNo(
				businessNo, classCode, businessType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		// 处理核赔的分摊试算
		if (businessType.equals("C") || businessType.equals("Y")) {

			this.simulateRepayByDangerNo(businessNo, ClaimNo);
			// FIX0315 modify by liufengyao end

			// 返回分摊试算的结果
			commonDangerInfoService.getDangerUnit(businessNo, req);
			forward = "hepeiSuccess";
		} else {
			// 增加取相应用户代码权限的功能
			// modify by zhangTC begin 20060323 关闭中间成本时，同时将分保试算一起关闭
			Collection prpUserGradeDtoList = new ArrayList();
			prpUserGradeDto = new PrpUserGradeDto();
			HttpSession session = req.getSession(true);
			UserCode = (String) session.getAttribute("myUserCode");
			String utiUserGradeValue = "";
			if (UIPowerAction.checkPowerReturn(prpDuserDto,
					"prpall.policy.middlecost")) {
				utiUserGradeValue = "1";
			} else {
				utiUserGradeValue = "0";
			}
			req.setAttribute("prpUserGradeValue", utiUserGradeValue);
			// modify end by lihua 20060518 BUG编号 24591
			// modify by zhangTC end 20060323 关闭中间成本时，同时将分保试算一起关闭
			// System.out.println("分保试算数据已保存数据库");

			// add by zhangpanlai begin 2007-08-05 附加自留显示为建议临分

			QueryRule queryRule;
			queryRule=QueryRule.getInstance();
			queryRule.addEqual("id.proposalNo",businessNo);

            
			PrpTDangerUnit prpTdangerUnitDto = new PrpTDangerUnit();
			Collection dangerUnitDtoList = prpTdangerUnitService.findByConditions(queryRule);
			Iterator iteratorDanger = dangerUnitDtoList.iterator();
			while (iteratorDanger.hasNext()) {
				prpTdangerUnitDto = (PrpTDangerUnit) iteratorDanger.next();
				dangerNo = String.valueOf(prpTdangerUnitDto.getId().getDangerNo());
			}
			queryRule=QueryRule.getInstance();
			queryRule.addEqual("proposalNo",businessNo);
			if(""!=dangerNo)
			{
				queryRule.addEqual("dangerNo",Integer.parseInt(dangerNo));
			}
			Collection fcoRepolicyDtoList = new ArrayList();
			fcoRepolicyDtoList = fcoRepolicyService.findByConditions(queryRule);
			Iterator iteratorRepolicy = fcoRepolicyDtoList.iterator();
			while (iteratorRepolicy.hasNext()) {
				FcoRepolicy fcoRepolicyDto = null;
				fcoRepolicyDto = (FcoRepolicy) iteratorRepolicy.next();
				UnderWriteFlag = fcoRepolicyDto.getUnderWriteFlag();
			}
			
			queryRule=QueryRule.getInstance();
			queryRule.addEqual("proposalNo",businessNo);
			Collection enquiryDtoList = enquiryService
					.findByConditions(queryRule);
			Iterator iteratorEnquiry = enquiryDtoList.iterator();

			while (iteratorEnquiry.hasNext()) {
				EnquiryVO enquiryDto = null;
				enquiryDto = (EnquiryVO) iteratorEnquiry.next();
				FeoEnquiryVO feoEnquiryDto = null;
				feoEnquiryDto = enquiryDto.getFeoEnquiryVO();
				VerifyFlag = feoEnquiryDto.getVerifyFlag();
			}

			req.setAttribute("VerifyFlag", VerifyFlag);
			req.setAttribute("UnderWriteFlag", UnderWriteFlag);
			// add by zhangpanlai end 2007-08-05

			commonDangerInfoService.reinsTrialInfoToRequest(businessNo,
					businessType, req);
			forward = "success";
		}
		return forward;
	}
	
	
	/**
	 * Simulate repay by danger no.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param ClaimNo
	 *            立案號
	 * @throws Exception
	 *             異常
	 */
	public void simulateRepayByDangerNo(String businessNo,String ClaimNo)
	throws Exception{
		blReinsLTrialService.simulateRepayByDangerNo(businessNo, ClaimNo);
	}
	
	

	/**
	 * 獲取屬性核保系統幫助服務接口.
	 * 
	 * @return 屬性核保系統幫助服務接口的值
	 */
	public WfLogHelperService getWfLogHelperService() {
		return wfLogHelperService;
	}

	/**
	 * 設置屬性核保系統幫助服務接口.
	 * 
	 * @param wfLogHelperService
	 *            待設置的核保系統幫助服務接口的值
	 */
	public void setWfLogHelperService(WfLogHelperService wfLogHelperService) {
		this.wfLogHelperService = wfLogHelperService;
	}


	/**
	 * 獲取屬性危險單位信息服務接口.
	 * 
	 * @return 屬性危險單位信息服務接口的值
	 */
	public CommonDangerInfoService getCommonDangerInfoService() {
		return commonDangerInfoService;
	}


	/**
	 * 設置屬性危險單位信息服務接口.
	 * 
	 * @param commonDangerInfoService
	 *            待設置的危險單位信息服務接口的值
	 */
	public void setCommonDangerInfoService(
			CommonDangerInfoService commonDangerInfoService) {
		this.commonDangerInfoService = commonDangerInfoService;
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
	 * 獲取屬性 分保單主信息處理接口.
	 * 
	 * @return 屬性 分保單主信息處理接口的值
	 */
	public FcoRepolicyService getFcoRepolicyService() {
		return fcoRepolicyService;
	}


	/**
	 * 設置屬性 分保單主信息處理接口.
	 * 
	 * @param fcoRepolicyService
	 *            待設置的 分保單主信息處理接口的值
	 */
	public void setFcoRepolicyService(FcoRepolicyService fcoRepolicyService) {
		this.fcoRepolicyService = fcoRepolicyService;
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
	public void setPrpTdangerUnitService(PrpTdangerUnitService prpTdangerUnitService) {
		this.prpTdangerUnitService = prpTdangerUnitService;
	}


	/**
	 * 獲取屬性分攤試算處理接口.
	 * 
	 * @return 屬性分攤試算處理接口的值
	 */
	public BLReinsLTrialService getBlReinsLTrialService() {
		return blReinsLTrialService;
	}


	/**
	 * 設置屬性分攤試算處理接口.
	 * 
	 * @param blReinsLTrialService
	 *            待設置的分攤試算處理接口的值
	 */
	public void setBlReinsLTrialService(BLReinsLTrialService blReinsLTrialService) {
		this.blReinsLTrialService = blReinsLTrialService;
	} 
	
}
