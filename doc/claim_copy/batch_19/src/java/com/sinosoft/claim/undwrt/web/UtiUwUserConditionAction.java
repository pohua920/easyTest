package com.sinosoft.claim.undwrt.web;

import ins.framework.web.Struts2Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.service.facade.UtiUwUserConditionService;
import com.sinosoft.platform.bl.facade.BLSwfNodeFacade;
import com.sinosoft.platform.bl.facade.BLUtiUwUserConditionFacade;
import com.sinosoft.platform.dto.domain.UtiUwLevelDto;
import com.sinosoft.platform.dto.domain.UtiUwUserConditionDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <ÖÐ¿ÆÈí>
 * @Date <Feb 19, 2013>
 * @description
 */
public class UtiUwUserConditionAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private UtiUwUserConditionService utiUwUserConditionService;
	private PrpDriskService prpDriskService;

	public String execute() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String actionType = request.getParameter("actionType");
		String forward = actionType;
		try {
			if (actionType == null || actionType.trim().equals("")) {
				forward = "invalid";
			} else if (actionType.equals("prepareUpdate")) {
				prepareUpdate(request, response);
			} else {
				Object parameters[] = { request, response };
				Method method = getClass().getMethod(actionType, HttpServletRequest.class, HttpServletResponse.class);
				method.invoke(this, parameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	public void prepareUpdate(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		try {
			HttpSession httpsession = httpservletrequest.getSession();
			UtiUwLevelDto utiuwleveldto = (UtiUwLevelDto) httpsession.getAttribute("UtiUwLevelDto");
			int i = Integer.parseInt(httpservletrequest.getParameter("index"));
			String userCode = httpservletrequest.getParameter("userCode");
			String userName = httpservletrequest.getParameter("userName");
			int nodeNo = Integer.parseInt(httpservletrequest.getParameter("nodeNo"));
			int modelNo = (new Long(utiuwleveldto.getModelNo())).intValue();
			String nodeName = (new BLSwfNodeFacade()).findByPrimaryKey(modelNo, nodeNo).getNodeName();
			String riskCode = httpservletrequest.getParameterValues("riskCode")[i];
			utiuwleveldto.setRiskCode(riskCode);
			utiuwleveldto.setUserCode(userCode);
			utiuwleveldto.setUserName(userName);
			utiuwleveldto.setNodeNo(nodeNo);
			utiuwleveldto.setNodeName(nodeName);
			httpservletrequest.setAttribute("conditionDto", utiuwleveldto);
			BLUtiUwUserConditionFacade blutiuwuserconditionfacade = new BLUtiUwUserConditionFacade();
			List<?> singleFactorList = blutiuwuserconditionfacade.getSimpleFactors(utiuwleveldto, 1);
			httpservletrequest.setAttribute("singleFactorList", singleFactorList);
			List<?> enumFactorList = blutiuwuserconditionfacade.getEnumFactors(utiuwleveldto, 1);
			httpservletrequest.setAttribute("enumFactorList", enumFactorList);
			List<?> comboFactorList = blutiuwuserconditionfacade.getComboFactors(utiuwleveldto, 1);
			httpservletrequest.setAttribute("comboFactorList", comboFactorList);
			String s4 = singleFactorList.size() <= 0 ? "0" : "1";
			String s5 = enumFactorList.size() <= 0 ? "0" : "1";
			String s6 = comboFactorList.size() <= 0 ? "0" : "1";
			httpservletrequest.setAttribute("simpleCount", s4);
			httpservletrequest.setAttribute("enumCount", s5);
			httpservletrequest.setAttribute("comboCount", s6);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void beforeUpdate(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		try {
			UtiUwLevelDto utiuwleveldto = (UtiUwLevelDto) httpservletrequest.getSession().getAttribute("UtiUwLevelDto");
			int nodeNo = Integer.parseInt(httpservletrequest.getParameter("nodeNo"));
			String underComCode = httpservletrequest.getParameter("comCode");
			String userCode = httpservletrequest.getParameter("userCode");
			UtiUwUserConditionDto utiuwuserconditiondto = new UtiUwUserConditionDto();
			utiuwuserconditiondto.setUserCode(userCode);
			utiuwuserconditiondto.setUserName(httpservletrequest.getParameter("userName"));
			utiuwuserconditiondto.setComCode(underComCode);
			utiuwuserconditiondto.setComName(httpservletrequest.getParameter("comName"));
			utiuwuserconditiondto.setNodeNo(nodeNo);
			ArrayList<UtiUwLevelDto> arraylist = new ArrayList<UtiUwLevelDto>();
			String riskCode[] = httpservletrequest.getParameter("riskCode").split(",");
			PrpDrisk prpDrisk = null;
			for (int k = 0; k < riskCode.length; k++) {
				StringBuffer stringbuffer = new StringBuffer();
				stringbuffer.append("RiskCode = '" + riskCode[k] + "' ");
				stringbuffer.append("AND ComCode = '" + underComCode + "' ");
				stringbuffer.append("AND UserCode = '" + userCode + "' ");
				stringbuffer.append("AND ModelNo = '" + utiuwleveldto.getModelNo() + "' ");
				stringbuffer.append("AND NodeNo = '" + nodeNo + "' ");
				stringbuffer.append("AND UwType = '" + utiuwleveldto.getUwType() + "' ");
				stringbuffer.append("AND ValidStatus = '1'");
				Collection<?> collection = utiUwUserConditionService.findByConditions(stringbuffer.toString());
				UtiUwLevelDto utiuwleveldto1 = new UtiUwLevelDto();
				if (collection.size() > 0)
					utiuwleveldto1.setFlag("1");
				else
					utiuwleveldto1.setFlag("0");
				utiuwleveldto1.setComCode(underComCode);
				utiuwleveldto1.setComName(httpservletrequest.getParameter("comName"));
				utiuwleveldto1.setRiskCode(riskCode[k]);
				prpDrisk = prpDriskService.findPrpDrisk(riskCode[k]);
				if (prpDrisk != null) {
					utiuwleveldto1.setRiskName(prpDrisk.getRiskCName());
				}
				arraylist.add(utiuwleveldto1);
			}

			httpservletrequest.setAttribute("UtiUwUserConditionDto", utiuwuserconditiondto);
			httpservletrequest.setAttribute("riskCodeList", arraylist);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public UtiUwUserConditionService getUtiUwUserConditionService() {
		return utiUwUserConditionService;
	}

	public void setUtiUwUserConditionService(UtiUwUserConditionService utiUwUserConditionService) {
		this.utiUwUserConditionService = utiUwUserConditionService;
	}

//	private static final String UTIUWLEVELDTO = "UtiUwLevelDto";

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}
}
