package com.sinosoft.claim.common.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
/***
 * 理赔系统公用代码异步获取公共类
 * @author 中科软
 */
public class ClaimCodeAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 险类 service */
	private PrpDclassService prpDclassService;
	/** 险别 service */
	private PrpDriskService prpDriskService;
	/** 批单 service */
	private PrpPheadService prpPheadService;
	/** 备案 service */
	private PrpLregistService prpLregistService;
	/** 批单ViewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 共保信息接口 */
	private PrpCcoinsService prpCcoinsService;
	/** 立案接口 */
	private ClaimService claimService;
	/** 报案业务处理服务 */
	private RegistService registService;
	/** 报案数据收集信息 */
	private DAARegistViewHelper daaRegistViewHelper;

	private CodeService codeService;
	//mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員
	private CommonService commonService;
	/***
	 * 获取系统险类
	 * @return
	 * @throws IOException
	 */
	public String getClassCode() {
		try {
			String conditions = " ValidStatus='1' AND classCode!='99' order by ClassCode asc ";
			Collection<PrpDclass> list = prpDclassService.findByConditions(conditions);
			if (list != null && !list.isEmpty()) {
				StringBuffer htmlStr = new StringBuffer("<option value=\"\"></option>");
				for (PrpDclass p : list) {
					htmlStr.append("<option value=\"");
					htmlStr.append(p.getClassCode());
					htmlStr.append("\">");
					htmlStr.append(p.getClassCode()).append("-").append(p.getClassName());
					htmlStr.append("</option>");
				}
				HttpServletResponse response = super.getResponse();
				response.setContentType("text/html;charset=GBK");
				response.getWriter().write(htmlStr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/***
	 * 获取系统险类下的险别
	 * @return
	 * @throws IOException
	 */
	public String getRiskCode() throws IOException {
		HttpServletRequest request = super.getRequest();
		try {
			String classCode = request.getParameter("classCode");
			if (DataUtils.emptyToNull(classCode) != null) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				PrpDclass pClass = this.prpDclassService.findPrpDclass(classCode);
				if (pClass != null) {
					jsonMap.put("riskCategory", pClass.getRiskCategory());// 设置险类别
				}
				String conditions = " ValidStatus='1' AND classCode ='" + classCode + "' order by riskCode asc ";
				Collection<PrpDrisk> list = this.prpDriskService.findByConditions(conditions);
				StringBuffer htmlStr = new StringBuffer("<option value=\"\"></option>");
				if (list != null && !list.isEmpty()) {
					for (PrpDrisk p : list) {
						htmlStr.append("<option value=\"");
						htmlStr.append(p.getRiskCode());
						htmlStr.append("\">");
						htmlStr.append(p.getRiskCode()).append("-").append(p.getRiskCName());
						htmlStr.append("</option>");
					}
				}
				jsonMap.put("htmlStr", htmlStr.toString());
				HttpServletResponse response = super.getResponse();
				response.setContentType("text/html;charset=GBK");
				response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/***
	 * 校验出险日期
	 * @return
	 * @throws IOException
	 */
	public String checkDamageDate() {
		HttpServletRequest request = super.getRequest();
		try {
			HttpServletResponse response = this.getResponse();
			HttpSession session = request.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			response.setCharacterEncoding("GBK");
			response.setContentType("text/HTML");
			// 用viewHelper整理界面输入
			String beforeDamageDate = request.getParameter("beforeDamageDate");
			String afterDamageDate = request.getParameter("afterDamageDate");
			String policyNo = request.getParameter("policyNo");
			String registNo = request.getParameter("registNo");
			String conditions = null;
			Date afterDamageDate2 = CommonUtils.toYearToDayDate(afterDamageDate);
			Date beforeDamageDate2 = CommonUtils.toYearToDayDate(beforeDamageDate);
			if (afterDamageDate2.getTime() > beforeDamageDate2.getTime()) {
				conditions = " policyno='" + policyNo + "' and validdate between to_date('" + beforeDamageDate + "','yyyy/MM/dd') and to_date('" + afterDamageDate + "','yyyy/MM/dd') ";
			} else {
				conditions = " policyno='" + policyNo + "' and validdate between to_date('" + afterDamageDate + "','yyyy/MM/dd') and to_date('" + beforeDamageDate + "','yyyy/MM/dd') ";
			}
			long count = prpPheadService.getCount(conditions);
			if (count > 0) {
				response.getWriter().print("true");
			} else {
				response.getWriter().print("false");
				registService.updateDamageDate(registNo, afterDamageDate,user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/***
	 * mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員,校验是否服務或營業人員
	 * @return
	 * @throws IOException
	 */
	public String checkIsPolicyHandlers() {
		HttpServletRequest request = super.getRequest();
		try {
			HttpServletResponse response = this.getResponse();
			HttpSession session = request.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			response.setCharacterEncoding("GBK");
			response.setContentType("text/HTML");
			String userCode = user.getUserCode() ;
			String userName = user.getUserName() ;
			String policyNo = request.getParameter("policyNo") ;
			String statements = " select a.agentname,m.handler1code from prpcmain m"
				+" left join prpdagent a " 
				+" on m.handleridentifynumber = a.identifynumber "  
				+" where m.policyno = '"+policyNo+"' and a.validstatus='1' "  
				+" and (a.agentname='"+userName+"' or m.handler1code='"+userCode+"')" ;
			List<?> list = commonService.findByStatements(statements);
			if (list!=null&&list.size()>0) {
				response.getWriter().print("true");
			} else {
				response.getWriter().print("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/***
	 * 刷新批改信息
	 * 保險金額、貨物名稱、船名、開行日期、航程,規則：只有貨物運輸險
	 * @return
	 * @throws IOException
	 */
	public String refreshEndorseInfo() {
		HttpServletRequest request = super.getRequest();
		List<PrpLclaim> resultList = new ArrayList<PrpLclaim>();
		try {
			HttpServletResponse response = this.getResponse();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/HTML");
			String policyNo = request.getParameter("policyNo");
			String endorseNo = request.getParameter("endorseNo");
			PrpLclaim prpLclaim = claimService.generateCargoInfo(policyNo, endorseNo);
			resultList.add(prpLclaim);
			this.writeJSONData(resultList, new String[] {"claimNo","sumAmount","cargoName","cargoNo","sailStartDate","importType","shipCName","endSitePort","startSitePort","areaCode"});
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
		}finally {
		}
		return NONE;
	}
	
	public String getPrpDcode(){
		HttpServletRequest request = super.getRequest();
		try {
			String codeType = request.getParameter("codeType");
			String codeLevel = request.getParameter("codeLevel");
			String upperCode = request.getParameter("upperCode");
			if (DataUtils.emptyToNull(codeType) != null) {
				StringBuilder condition = new StringBuilder();
				condition.append(" validStatus = '1' and codeType = '").append(codeType).append("' ");
				if(!CommonUtils.isEmpty(codeLevel)){
					condition.append(" and codeLevel = '").append(codeLevel).append("' ");
				}
				if(!CommonUtils.isEmpty(upperCode)){
					condition.append(" and upperCode = '").append(upperCode).append("' ");
				}
				condition.append(" order by codeCode ");
				List<PrpDcode> list = this.codeService.findPrpDcodeByConditions(condition.toString());
				StringBuffer htmlStr = new StringBuffer("<option value=\"\"></option>");
				if (list != null && !list.isEmpty()) {
					for (PrpDcode p : list) {
						htmlStr.append("<option value=\"");
						htmlStr.append(p.getId().getCodeCode());
						htmlStr.append("\">");
						htmlStr.append(p.getId().getCodeCode()).append("-").append(p.getCodeCName());
						htmlStr.append("</option>");
					}
				}
				HttpServletResponse response = super.getResponse();
				response.setContentType("text/html;charset=GBK");
				response.getWriter().write(htmlStr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}
	
	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}
	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
	//mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員,校验是否服務或營業人員 start
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	//mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員,校验是否服務或營業人員 end
	
}
