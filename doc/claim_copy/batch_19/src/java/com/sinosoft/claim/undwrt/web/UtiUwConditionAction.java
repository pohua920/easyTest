package com.sinosoft.claim.undwrt.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.service.facade.UtiUwConditionService;
import com.sinosoft.platform.bl.facade.BLPrpDclassFacade;
import com.sinosoft.platform.bl.facade.BLSwfNodeFacade;
import com.sinosoft.platform.bl.facade.BLUtiUwConditionFacade;
import com.sinosoft.platform.dto.domain.PrpDclassDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.UtiUwConditionDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <中科软>
 * @Date <Feb 19, 2013>
 * @description
 */
public class UtiUwConditionAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	private UtiUwConditionService utiUwConditionService;

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
		} else if (actionType.equals("prepareInsert")) {
			prepareInsert(request, response);
		} else if (actionType.equals("prepareUpdate")) {
			prepareUpdate(request, response);
		} else if (actionType.equals("update")) {
			update(request, response);
		} else if (actionType.equals("delete")) {
			delete(request, response);
		} else {
			try {
				Object parameters[] = { request, response };
				Method method = getClass().getMethod(actionType, HttpServletRequest.class, HttpServletResponse.class);
				method.invoke(this, parameters);
			} catch (InvocationTargetException e) {
				if (e.getTargetException() instanceof UserException) {// 反射方法的调用，会先抛
					throw (UserException) e.getTargetException();
				}
				throw e;
			}
		}
		return forward;
	}

	private UtiUwCondition generateConditionDto(HttpServletRequest httpservletrequest) throws Exception {
		String as[] = httpservletrequest.getParameterValues("checkboxSelect");
		int i = Integer.parseInt(as[0]);
		UtiUwCondition utiuwcondition = new UtiUwCondition();
		utiuwcondition.getId().setUwType(httpservletrequest.getParameterValues("uwType")[i]);
		utiuwcondition.setUwTypeName(httpservletrequest.getParameterValues("uwTypeName")[i]);
		utiuwcondition.getId().setClassCode(httpservletrequest.getParameterValues("classCode")[i]);
		utiuwcondition.setClassName(httpservletrequest.getParameterValues("className")[i]);
		utiuwcondition.getId().setComCode(httpservletrequest.getParameterValues("comCode")[i]);
		utiuwcondition.setComName(httpservletrequest.getParameterValues("comName")[i]);
		utiuwcondition.getId().setRiskCode(httpservletrequest.getParameterValues("riskCode")[i]);
		utiuwcondition.getId().setModelNo(Integer.parseInt(httpservletrequest.getParameterValues("modelNo")[i]));
		utiuwcondition.setModelName(httpservletrequest.getParameterValues("modelName")[i]);
		utiuwcondition.setRemark(httpservletrequest.getParameterValues("remark")[i]);
		utiuwcondition.setCreateTime(httpservletrequest.getParameterValues("createTime")[i]);
		utiuwcondition.setValidStatus(httpservletrequest.getParameterValues("validStatus")[i]);
		return utiuwcondition;
	}

	private UtiUwConditionDto generateQueryDto(HttpServletRequest httpservletrequest) throws Exception {
		UtiUwConditionDto utiuwconditiondto = new UtiUwConditionDto();
		utiuwconditiondto.setUwType(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwTypeQuery")));
		utiuwconditiondto.setUwTypeName(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwTypeNameQuery")));
		utiuwconditiondto.setComCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("comCodeQuery")));
		utiuwconditiondto.setComName(StringUtils.trimToEmpty(httpservletrequest.getParameter("comNameQuery")));
		utiuwconditiondto.setRiskCategoryCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryCodeQuery")));
		utiuwconditiondto.setRiskCategoryName(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryNameQuery")));
		utiuwconditiondto.setClassCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("classCodeQuery")));
		utiuwconditiondto.setClassName(StringUtils.trimToEmpty(httpservletrequest.getParameter("classNameQuery")));
		utiuwconditiondto.setRiskCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCodeQuery")));
		utiuwconditiondto.setRiskName(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskNameQuery")));
		return utiuwconditiondto;
	}

	private String generateQueryConditions(UtiUwConditionDto utiuwconditiondto) throws Exception {
		StringBuffer stringbuffer = new StringBuffer("1=1");
		if ("".equals(utiuwconditiondto.getUwType()) && utiuwconditiondto.getUwType().length() == 0)
			stringbuffer.append(" AND (UwType = 'C' OR UwType = 'Y') ");
		else
			stringbuffer.append(SqlUtils.convertString("UwType", utiuwconditiondto.getUwType()));
		stringbuffer.append(SqlUtils.convertString("ComCode", utiuwconditiondto.getComCode()));
		stringbuffer.append(getClassCodeByRiskCategoryCode(utiuwconditiondto.getRiskCategoryCode()));
		stringbuffer.append(SqlUtils.convertString("ClassCode", utiuwconditiondto.getClassCode()));
		stringbuffer.append(SqlUtils.convertString("RiskCode", utiuwconditiondto.getRiskCode()));
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

	public void main(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
	}

	public void prepareQuery(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwConditionDto utiuwconditiondto = new UtiUwConditionDto();
		httpsession.setAttribute("ConditionQueryDto", utiuwconditiondto);
	}

	public void query(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		UtiUwConditionDto utiuwconditiondto = generateQueryDto(httpservletrequest);
		String s = generateQueryConditions(utiuwconditiondto);
		HttpSession httpsession = httpservletrequest.getSession();
		PrpDuserDto prpduserdto = new PrpDuserDto();
		int pageNo = 1;
		byte pageSize = 10;
		prpduserdto.setQueryCondition("UwCondition", pageNo, pageSize, s);
		httpsession.setAttribute("prpDuserDto", prpduserdto);
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		PageRecord pageRecord = blutiuwconditionfacade.findOverviewByConditions(s, pageNo, pageSize);
		httpservletrequest.setAttribute("page", new Page((pageNo - 1) * pageSize, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		httpservletrequest.setAttribute("conditionList", pageRecord.getResult());
		httpsession.setAttribute("ConditionQueryDto", utiuwconditiondto);
	}

	public void queryContinue(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		ParamUtils paramutils = new ParamUtils(httpservletrequest);
		PrpDuserDto prpduserdto = (PrpDuserDto) httpservletrequest.getSession().getAttribute("prpDuserDto");
		com.sinosoft.platform.dto.domain.PrpDuserDto.QueryCondition querycondition = prpduserdto.getQueryCondition("UwCondition");
		int pageNo = 1;
		int pageSize = 10;
		String s = "1=1 And (UwType='C' OR UwType='Y')";
		if (querycondition != null) {
			pageNo = paramutils.getIntParameter("pageNo", querycondition.getPageNo());
			pageSize = paramutils.getIntParameter("rowsPerPage", querycondition.getRowsPerPage());
			s = querycondition.getConditions();
		}
		prpduserdto.setQueryCondition("UwCondition", pageNo, pageSize, s);
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		PageRecord pageRecord = blutiuwconditionfacade.findOverviewByConditions(s, pageNo, pageSize);
		httpservletrequest.setAttribute("page", new Page((pageNo - 1) * pageSize, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		httpservletrequest.setAttribute("conditionList", pageRecord.getResult());
	}

	// 看到第一个新增页面。
	public void prepareInsert(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
	}

	// 点击下一步後。
	public void prepareInsert2(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		UtiUwCondition utiuwcondition = new UtiUwCondition();
		utiuwcondition.getId().setUwType(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwType")));
		utiuwcondition.setUwTypeName(StringUtils.trimToEmpty(httpservletrequest.getParameter("uwTypeName")));
		utiuwcondition.getId().setComCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("comCode")));
		utiuwcondition.setComName(StringUtils.trimToEmpty(httpservletrequest.getParameter("comName")));
		utiuwcondition.setRiskCategoryCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryCode")));
		utiuwcondition.setRiskCategoryName(StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCategoryName")));
		utiuwcondition.getId().setModelNo(Integer.parseInt(httpservletrequest.getParameter("modelNo")));
		utiuwcondition.setModelName(StringUtils.trimToEmpty(httpservletrequest.getParameter("modelName")));
		utiuwcondition.getId().setClassCode(StringUtils.trimToEmpty(httpservletrequest.getParameter("classCode")));
		utiuwcondition.setClassName(StringUtils.trimToEmpty(httpservletrequest.getParameter("className")));
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCode"));
		utiuwcondition.getId().setRiskCode(s);
		String s1 = (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS")).format(new Date());
		utiuwcondition.setCreateTime(s1);
		utiuwcondition.setValidStatus("1");
		HttpSession httpsession = httpservletrequest.getSession();
		httpsession.setAttribute("UtiUwConditionDto", utiuwcondition);
		StringBuffer stringbuffer = new StringBuffer("1=1");
		stringbuffer.append(SqlUtils.convertString("ModelNo", "" + utiuwcondition.getId().getModelNo()));
		stringbuffer.append(" AND EndFlag!=1 Order By NodeNo");
		BLSwfNodeFacade blswfnodefacade = new BLSwfNodeFacade();
		List<?> list = (List<?>) blswfnodefacade.findByConditions(stringbuffer.toString());
		httpservletrequest.setAttribute("swfNodeList", list);
		List<?> list1 = utiUwConditionService.getSimpleFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("singleFactorList", list1);
		List<?> list2 = utiUwConditionService.getComboFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("comboFactorList", list2);
		String s2 = list1.size() <= 0 ? "0" : "1";
		String s3 = list2.size() <= 0 ? "0" : "1";
		String s4 = "0";
		if ("0".equals(s2) && "0".equals(s3) && "0".equals(s4)) {
			String message = "沒有爲審核類型：" + utiuwcondition.getId().getUwType() + "，審核部門：" + utiuwcondition.getId().getComCode() + "，險類：" + utiuwcondition.getId().getClassCode() + " 請在雙核因子配置欄裏先配置風險條件。";
			throw new UserException(0, -1, "核賠權限", message);
		} else {
			httpservletrequest.setAttribute("simpleCount", s2);
			httpservletrequest.setAttribute("enumCount", s4);
			httpservletrequest.setAttribute("comboCount", s3);
			httpservletrequest.setAttribute("conditionDto", utiuwcondition);
			return;
		}
	}

	public void insertUtiUwCondition2(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwCondition utiuwcondition = (UtiUwCondition) httpsession.getAttribute("UtiUwConditionDto");
		StringBuffer stringbuffer = new StringBuffer("1=1");
		stringbuffer.append(SqlUtils.convertString("ModelNo", "" + utiuwcondition.getId().getModelNo()));
		stringbuffer.append(" AND EndFlag!=1 Order By NodeNo");
		BLSwfNodeFacade blswfnodefacade = new BLSwfNodeFacade();
		List<?> list = (List<?>) blswfnodefacade.findByConditions(stringbuffer.toString());
		httpservletrequest.setAttribute("swfNodeList", list);
		List<?> list1 = utiUwConditionService.getSimpleFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("singleFactorList", list1);
		List<?> list2 = utiUwConditionService.getComboFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("comboFactorList", list2);
		String s = list1.size() <= 0 ? "0" : "1";
		String s1 = list2.size() <= 0 ? "0" : "1";
		String s2 = "0";
		httpservletrequest.setAttribute("simpleCount", s);
		httpservletrequest.setAttribute("enumCount", s2);
		httpservletrequest.setAttribute("comboCount", s1);
		httpservletrequest.setAttribute("conditionDto", utiuwcondition);
	}

	// 新增某个级别的人员。
	public void prepareInsertUtiUwLevel(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		int i = Integer.parseInt(StringUtils.trimToEmpty(httpservletrequest.getParameter("nodeNo")));
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwConditionDto utiuwconditiondto = (UtiUwConditionDto) httpsession.getAttribute("UtiUwConditionDto");
		utiuwconditiondto.setRiskCode(utiuwconditiondto.getRiskCode().replaceAll(" ", ","));
		utiuwconditiondto.setNodeNo(i);
		int j = utiuwconditiondto.getModelNo();
		String s = (new BLSwfNodeFacade()).findByPrimaryKey(j, i).getNodeName();
		utiuwconditiondto.setNodeName(s);
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		List<?> list = blutiuwconditionfacade.getUtiUwLevel(utiuwconditiondto);
		httpservletrequest.setAttribute("utiUwLevelUserList", list);
		httpservletrequest.setAttribute("conditionDto", utiuwconditiondto);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String s1 = simpledateformat.format(calendar.getTime());
		int k = calendar.get(1);
		calendar.set(1, k + 1);
		String s2 = simpledateformat.format(calendar.getTime());
		httpservletrequest.setAttribute("utiuwlevelStartDate", s1);
		httpservletrequest.setAttribute("utiuwlevelEndDate", s2);
	}

	// 保存修改。
	public void insertUtiUwCondition(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwConditionDto utiuwconditiondto = (UtiUwConditionDto) httpsession.getAttribute("UtiUwConditionDto");
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("actionType"));
		String as[] = httpservletrequest.getParameterValues("nodeNo");
		String as1[] = httpservletrequest.getParameterValues("simFactorCode");
		String as2[] = httpservletrequest.getParameterValues("simpleFactorValue");
		String as3[] = httpservletrequest.getParameterValues("enumfactorFactorCode");
		String as4[] = httpservletrequest.getParameterValues("enumfactorCheckbox");
		String as5[] = httpservletrequest.getParameterValues("comboNodeNo");
		String as6[] = httpservletrequest.getParameterValues("utiUwConditionFactorCodeValue");
		String as7[] = httpservletrequest.getParameterValues("combofactorFactorCode");
		String as8[] = httpservletrequest.getParameterValues("combofactorFactorCols");
		String as9[] = httpservletrequest.getParameterValues("combofactorCodeType");
		String as10[] = httpservletrequest.getParameterValues("combofactorCodeCode");
		String as11[] = httpservletrequest.getParameterValues("combofactorFactorValue");
		String as12[] = httpservletrequest.getParameterValues("comboFactorDefaultValue");
		String as13[] = httpservletrequest.getParameterValues("typeName");
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		blutiuwconditionfacade.updateUtiUwCondition(utiuwconditiondto, as1, as2, as3, as4, as7, as8, as9, as10, as11, as12, 0, s, as13, as6, as, as5);
	}

	// 在结果列表选中某条记录。
	public void prepareUpdate(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		UtiUwCondition utiuwcondition = generateConditionDto(httpservletrequest);
		HttpSession httpsession = httpservletrequest.getSession();
		String s = utiuwcondition.getId().getRiskCode().replaceAll(" ", ",");
		utiuwcondition.getId().setRiskCode(s);
		httpsession.setAttribute("UtiUwConditionDto", utiuwcondition);
		httpservletrequest.setAttribute("conditionDto", utiuwcondition);
	}

	public void update(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwConditionDto utiuwconditiondto = (UtiUwConditionDto) httpsession.getAttribute("UtiUwConditionDto");
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCode"));
		UtiUwConditionDto utiuwconditiondto1 = utiuwconditiondto;
		utiuwconditiondto1.setRiskCode(s);
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		String s1 = blutiuwconditionfacade.update(utiuwconditiondto1, null);
		utiuwconditiondto.setCreateTime(s1);
		httpsession.setAttribute("UtiUwConditionDto", utiuwconditiondto);
	}

	public void prepareUpdate2(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("oldRiskCode"));
		String s1 = StringUtils.trimToEmpty(httpservletrequest.getParameter("riskCode"));
		if (!s.equals(s1))
			update(httpservletrequest, null);
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwCondition utiuwcondition = (UtiUwCondition) httpsession.getAttribute("UtiUwConditionDto");
		StringBuffer stringbuffer = new StringBuffer("1=1");
		stringbuffer.append(SqlUtils.convertString("ModelNo", "" + utiuwcondition.getId().getModelNo()));
		stringbuffer.append(" AND EndFlag!=1 Order By NodeNo");
		BLSwfNodeFacade blswfnodefacade = new BLSwfNodeFacade();
		try {
			List<?> list = (List<?>) blswfnodefacade.findByConditions(stringbuffer.toString());
			httpservletrequest.setAttribute("swfNodeList", list);
			List<?> list1 = utiUwConditionService.getSimpleFactors(utiuwcondition, 0);
			httpservletrequest.setAttribute("singleFactorList", list1);
			List<?> list2 = utiUwConditionService.getComboFactors(utiuwcondition, 0);
			httpservletrequest.setAttribute("comboFactorList", list2);
			String s2 = list1.size() <= 0 ? "0" : "1";
			String s3 = list2.size() <= 0 ? "0" : "1";
			String s4 = "0";
			httpservletrequest.setAttribute("simpleCount", s2);
			httpservletrequest.setAttribute("enumCount", s4);
			httpservletrequest.setAttribute("comboCount", s3);
		} catch (Exception e) {
			String message = e.getMessage();
			Exception ex = new UserException(0, -1, "機構權限設定", message);
			throw ex;
		}
		httpservletrequest.setAttribute("conditionDto", utiuwcondition);
	}

	// 修改某个级别的因子值。
	public void prepareUpdateUtiUwCondition(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwCondition utiuwcondition = (UtiUwCondition) httpsession.getAttribute("UtiUwConditionDto");
		int i = utiuwcondition.getId().getModelNo();
		int j = Integer.parseInt(httpservletrequest.getParameter("nodeNo"));
		String s = (new BLSwfNodeFacade()).findByPrimaryKey(i, j).getNodeName();
		String s1 = utiuwcondition.getId().getRiskCode().replaceAll(" ", ",");
		utiuwcondition.getId().setRiskCode(s1);
		utiuwcondition.getId().setNodeNo(j);
		utiuwcondition.setNodeName(s);
		List<?> list = utiUwConditionService.getSimpleFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("singleFactorList", list);
		List<?> list1 = utiUwConditionService.getEnumFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("enumFactorList", list1);
		List<?> list2 = utiUwConditionService.getComboFactors(utiuwcondition, 0);
		httpservletrequest.setAttribute("comboFactorList", list2);
		String s2 = list.size() <= 0 ? "0" : "1";
		String s3 = list1.size() <= 0 ? "0" : "1";
		String s4 = list2.size() <= 0 ? "0" : "1";
		httpservletrequest.setAttribute("simpleCount", s2);
		httpservletrequest.setAttribute("enumCount", s3);
		httpservletrequest.setAttribute("comboCount", s4);
		httpservletrequest.setAttribute("conditionDto", utiuwcondition);
	}

	// 修改某个级别的人员。
	public void prepareUpdateUtiUwLevel(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		HttpSession httpsession = httpservletrequest.getSession();
		UtiUwConditionDto utiuwconditiondto = (UtiUwConditionDto) httpsession.getAttribute("UtiUwConditionDto");
		int i = utiuwconditiondto.getModelNo();
		int j = Integer.parseInt(httpservletrequest.getParameter("nodeNo"));
		String s = (new BLSwfNodeFacade()).findByPrimaryKey(i, j).getNodeName();
		String s1 = utiuwconditiondto.getRiskCode().replaceAll(" ", ",");
		utiuwconditiondto.setRiskCode(s1);
		utiuwconditiondto.setNodeNo(j);
		utiuwconditiondto.setNodeName(s);
		BLUtiUwConditionFacade blutiuwconditionfacade = new BLUtiUwConditionFacade();
		List<?> list = blutiuwconditionfacade.getUtiUwLevel(utiuwconditiondto);
		httpservletrequest.setAttribute("utiUwLevelUserList", list);
		httpservletrequest.setAttribute("conditionDto", utiuwconditiondto);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String s2 = simpledateformat.format(calendar.getTime());
		int k = calendar.get(1);
		calendar.set(1, k + 1);
		String s3 = simpledateformat.format(calendar.getTime());
		httpservletrequest.setAttribute("utiuwlevelStartDate", s2);
		httpservletrequest.setAttribute("utiuwlevelEndDate", s3);
	}

	public void delete(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception {
		String s = StringUtils.trimToEmpty(httpservletrequest.getParameter("actionType"));
		UtiUwCondition utiuwcondition = generateConditionDto(httpservletrequest);
		utiuwcondition.getId().setRiskCode(utiuwcondition.getId().getRiskCode().replaceAll(" ", ","));
		utiUwConditionService.delete(utiuwcondition, s);
	}

	public UtiUwConditionService getUtiUwConditionService() {
		return utiUwConditionService;
	}

	public void setUtiUwConditionService(UtiUwConditionService utiUwConditionService) {
		this.utiUwConditionService = utiUwConditionService;
	}

//	private static final String UTIUWCONDITIONDTO = "UtiUwConditionDto";

}