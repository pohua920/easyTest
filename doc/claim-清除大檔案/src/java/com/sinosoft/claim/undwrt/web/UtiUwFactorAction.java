package com.sinosoft.claim.undwrt.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.schema.model.UtiUwFactor;
import com.sinosoft.claim.schema.service.facade.UtiUwFactorService;
import com.sinosoft.platform.bl.facade.BLPrpDclassFacade;
import com.sinosoft.platform.bl.facade.BLUtiUwFactorFacade;
import com.sinosoft.platform.dto.domain.PrpDclassDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.UtiUwComboFactorDto;
import com.sinosoft.platform.dto.domain.UtiUwFactorDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <ÖÐ¿ÆÈí>
 * @Date <Feb 19, 2013>
 * @description
 */
public class UtiUwFactorAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private UtiUwFactorService utiUwFactorService;

	public String execute() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String actionType = request.getParameter("actionType");
		String forward = actionType;
		if (actionType == null || actionType.trim().equals("")) {
			forward = "invalid";
		} else if (actionType.equals("prepareQuery")) {
			prepareQuery(request, response);
		} else if (actionType.equals("query")) {
			query(request, response);
		} else if (actionType.equals("queryContinue")) {
			queryContinue(request, response);
		} else if (actionType.equals("prepareUpdate")) {
			prepareUpdate(request, response);
		} else if (actionType.equals("update")) {
			update(request, response);
		} else {
			Object parameters[] = { request, response };
			Method method = getClass().getMethod(actionType, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, parameters);
		}
		return forward;
	}

	private UtiUwFactorDto generateQueryDto(HttpServletRequest httpservletrequest) throws Exception {
		UtiUwFactorDto utiuwfactordto = new UtiUwFactorDto();
		utiuwfactordto.setUwType(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwTypeQuery")));
		utiuwfactordto.setUwTypeName(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwTypeNameQuery")));
		utiuwfactordto.setRiskCategoryCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryCodeQuery")));
		utiuwfactordto.setRiskCategoryName(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryNameQuery")));
		utiuwfactordto.setClassCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("classCodeQuery")));
		utiuwfactordto.setClassName(StringUtils.trimToEmpty(httpservletrequest.getParameter("classNameQuery")));
		utiuwfactordto.setFactorName(StringUtils.trimToEmpty(httpservletrequest.getParameter("factorNameQuery")));
		utiuwfactordto.setFactorCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("factorCodeQuery")));
		utiuwfactordto.setValidStatus(StringUtils.trimToEmpty(httpservletrequest.getParameter("validStatus")));
		return utiuwfactordto;
	}

	private String generateQueryConditions(UtiUwFactorDto utiuwfactordto) throws Exception {
		StringBuffer stringbuffer = new StringBuffer("1=1");
		if ("".equals(utiuwfactordto.getUwType()) && utiuwfactordto.getUwType().length() == 0)
			stringbuffer.append(" AND (UwType = 'C' OR UwType = 'Y') ");
		else
			stringbuffer.append(SqlUtils.convertString("UwType", utiuwfactordto.getUwType()));
		stringbuffer.append(getClassCodeByRiskCategoryCode(utiuwfactordto.getRiskCategoryCode()));
		stringbuffer.append(SqlUtils.convertString("ClassCode", utiuwfactordto.getClassCode()));
		stringbuffer.append(SqlUtils.convertString("FactorName", utiuwfactordto.getFactorName()));
		stringbuffer.append(SqlUtils.convertString("FactorCode", utiuwfactordto.getFactorCode()));
		stringbuffer.append(SqlUtils.convertString("ValidStatus", utiuwfactordto.getValidStatus()));
		stringbuffer.append(" And (UwType ='C' or UwType='Y') ");
		stringbuffer.append(" Order By ClassCode, FactorCode");
		return stringbuffer.toString();
	}

	private String getClassCodeByRiskCategoryCode(String s) throws Exception {
		Collection<?> collection = (new BLPrpDclassFacade()).findByConditions(" RiskCategory = '" + s + "'");
		if (collection != null && collection.size() > 0) {
			StringBuffer stringbuffer = new StringBuffer(" AND ( 1=0 ");
			PrpDclassDto prpdclassdto;
			for (Iterator<?> iterator = collection.iterator(); iterator.hasNext(); stringbuffer.append("ClassCode = '" + prpdclassdto.getClassCode() + "'")) {
				prpdclassdto = (PrpDclassDto) iterator.next();
				stringbuffer.append(" OR ");
			}

			stringbuffer.append(")");
			return stringbuffer.toString();
		} else {
			return "";
		}
	}

	public void prepareQuery(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwFactorDto utiuwfactordto = new UtiUwFactorDto();
		utiuwfactordto.setValidStatus("1");
		httpsession.setAttribute("FactorQueryDto", utiuwfactordto);
	}

	public void query(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		UtiUwFactorDto utiuwfactordto = generateQueryDto(httpservletrequest);
		String s = generateQueryConditions(utiuwfactordto);
		HttpSession httpsession = httpservletrequest.getSession();
		PrpDuserDto prpduserdto = new PrpDuserDto();
		int pageNo = 1;
		byte pageSize = 10;
		prpduserdto.setQueryCondition("UwFactor", pageNo, pageSize, s);
		BLUtiUwFactorFacade blutiuwfactorfacade = new BLUtiUwFactorFacade();
		httpsession.setAttribute("prpDuserDto", prpduserdto);
		PageRecord pageRecord = blutiuwfactorfacade.findByConditions(s, pageNo, pageSize);
		httpservletrequest.setAttribute("page", new Page((pageNo - 1) * pageSize, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		httpservletrequest.setAttribute("uwFactorOverview", pageRecord.getResult());
		httpsession.setAttribute("FactorQueryDto", utiuwfactordto);
	}

	public void queryContinue(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		ParamUtils paramutils = new ParamUtils(httpservletrequest);
		PrpDuserDto prpduserdto = (PrpDuserDto) httpservletrequest.getSession().getAttribute("prpDuserDto");
		com.sinosoft.platform.dto.domain.PrpDuserDto.QueryCondition querycondition = prpduserdto.getQueryCondition("UwFactor");
		int pageNo = 1;
		int pageSize = 10;
		String s = "1=1 And (UwType='C' OR UwType='Y')";
		if (querycondition != null) {
			pageNo = paramutils.getIntParameter("pageNo", querycondition.getPageNo());
			pageSize = paramutils.getIntParameter("rowsPerPage", querycondition.getRowsPerPage());
			s = querycondition.getConditions();
		}
		prpduserdto.setQueryCondition("UwFactor", pageNo, pageSize, s);
		BLUtiUwFactorFacade blutiuwfactorfacade = new BLUtiUwFactorFacade();
		PageRecord pageRecord = blutiuwfactorfacade.findByConditions(s, pageNo, pageSize);
		httpservletrequest.setAttribute("page", new Page((pageNo - 1) * pageSize, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		httpservletrequest.setAttribute("uwFactorOverview", pageRecord.getResult());
	}

	public void openTip(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		ParamUtils paramutils = new ParamUtils(httpservletrequest);
		String s = paramutils.getParameter("factorCode");
		Object obj = httpservletrequest.getSession().getAttribute("UtiUwConditionDto");
		String s1 = PropertyUtils.getProperty(obj, "uwType").toString();
		String s2 = PropertyUtils.getProperty(obj, "classCode").toString();
		UtiUwFactor utiuwfactor = utiUwFactorService.findByPrimaryKey(s1, s, s2);
		httpservletrequest.setAttribute("factorDto", utiuwfactor);
	}

	public void prepareUpdate(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		ParamUtils paramutils = new ParamUtils(httpservletrequest);
		String as[] = paramutils.getParameterValues("checkboxSelect");
		String as1[] = paramutils.getParameterValues("uwType");
		String as2[] = paramutils.getParameterValues("factorCode");
		String as3[] = paramutils.getParameterValues("classCode");
		String uwType = as1[Integer.parseInt(as[0])];
		String factorCode = as2[Integer.parseInt(as[0])];
		String classCode = as3[Integer.parseInt(as[0])];
		UtiUwFactor utiuwfactor = utiUwFactorService.findByPrimaryKey(uwType, factorCode, classCode);
		httpservletrequest.setAttribute("factorDto", utiuwfactor);
	}

	public void update(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("uwType"));
		String s1 = StringUtils.trimToEmpty(httpservletrequest.getParameter("classCode"));
		String s2 = StringUtils.trimToEmpty(httpservletrequest.getParameter("factorCode"));
		String s3 = StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryCode"));
		String s4 = StringUtils.trimToEmpty(httpservletrequest.getParameter("factorName"));
		String s5 = StringUtils.trimToEmpty(httpservletrequest.getParameter("factorAttr"));
		String s6 = StringUtils.trimToEmpty(httpservletrequest.getParameter("multiSelectFlag"));
		String s7 = StringUtils.trimToEmpty(httpservletrequest.getParameter("isCodeFlag"));
		String s8 = StringUtils.trimToEmpty(httpservletrequest.getParameter("exampleValue"));
		String s9 = StringUtils.trimToEmpty(httpservletrequest.getParameter("validStatus"));
		String s10 = StringUtils.trimToEmpty(httpservletrequest.getParameter("remark"));
		String s11 = StringUtils.trimToEmpty(httpservletrequest.getParameter("valueDesc"));
		String s12 = StringUtils.trimToEmpty(httpservletrequest.getParameter("operator"));
		UtiUwFactor utiuwfactor = new UtiUwFactor();
		utiuwfactor.getId().setUwType(s);
		utiuwfactor.getId().setClassCode(s1);
		utiuwfactor.getId().setFactorCode(s2);
		utiuwfactor.setRiskCategoryCode(s3);
		utiuwfactor.setFactorName(s4);
		utiuwfactor.setFactorAttr(s5);
		utiuwfactor.setMultiSelectFlag(s6);
		utiuwfactor.setIsCodeFlag(s7);
		utiuwfactor.setExampleValue(s8);
		utiuwfactor.setValidStatus(s9);
		utiuwfactor.setRemark(s10);
		utiuwfactor.setValueDesc(s11);
		utiuwfactor.setOperator(s12);
		if ("C".equals(utiuwfactor.getMultiSelectFlag())) {
			ArrayList<UtiUwComboFactorDto> arraylist = new ArrayList<UtiUwComboFactorDto>();
			String as[] = httpservletrequest.getParameterValues("serialNo");
			String as1[] = httpservletrequest.getParameterValues("codeType");
			String as2[] = httpservletrequest.getParameterValues("typeName");
			for (int i = 1; i < as.length; i++) {
				UtiUwComboFactorDto utiuwcombofactordto = new UtiUwComboFactorDto();
				utiuwcombofactordto.setUwType(s);
				utiuwcombofactordto.setClassCode(s1);
				utiuwcombofactordto.setFactorCode(s2);
				utiuwcombofactordto.setSerialNo(Integer.parseInt(as[i]));
				utiuwcombofactordto.setCodeType(as1[i]);
				utiuwcombofactordto.setTypeName(as2[i]);
				utiuwcombofactordto.setValidStatus(utiuwfactor.getValidStatus());
				utiuwcombofactordto.setFlag("0");
				arraylist.add(utiuwcombofactordto);
			}

			utiuwfactor.setUtiUwComboFactorList(arraylist);
		}
		utiUwFactorService.update(utiuwfactor);
	}

	public UtiUwFactorService getUtiUwFactorService() {
		return utiUwFactorService;
	}

	public void setUtiUwFactorService(UtiUwFactorService utiUwFactorService) {
		this.utiUwFactorService = utiUwFactorService;
	}
}