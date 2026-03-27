
<%--
****************************************************************************
* DESC       ：通用代码查询处理页面,包含在隐藏的框架中,输入过程中要显示代码清单时调用此页面
*            ：此页面要处理所有异常，绝对不能有编译错误，不能抛出任何异常
* AUTHOR     ：理赔项目组
* CREATEDATE ：2002-12-23
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*                lixiang     2004-6-17
****************************************************************************
*转换的内容如下：
*             1。部门代码的转换
*             2。币别代码的转换
*             3。险别代码的转换
*             4。条款代码的转换
*             5。出险原因代码的转换
*             6。出险区域代码的转换
*             7。事故类型代码的转换
*             8。单证代码的转换
*             9。接案人 经办人
*             10。事故处理部门
*             11。承保公司
*             12。保单投保险别
*             13。费用代码
*             14。出险地址
*             15。调度对象
*             16。损失部件转换
*             17。所在地区转换
*             18。所在行业转换
*             19。保单投保险别 for 定损的财产定损
*             20。保单投保险别 for 定损的人员定损
*             21. 人员定损费用转换
*             22. 费用转换
*             23. 车辆受损部位的转换 CarPartCode
*             24. 客户资料集合转换（处理部门）  HandleUnit
*             25。案件性质
*             26. 巨灾代码
*             27. 查勘人员信息
*             28. 向外询价人员信息
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpDliabDto"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.resource.dtofactory.domain.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.ui.control.viewHelper.*"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpCinsuredNatureFacade"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpCitemKindFacade"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="com.sinosoft.sysframework.common.Constants"%>

<%
	String codeindex = StringUtils.rightTrim(request.getParameter("codeindex"));
	String codevalue = StringUtils.rightTrim(request.getParameter("codevalue"));
	String codetype = StringUtils.rightTrim(request.getParameter("codetype"));
	String relation = StringUtils.rightTrim(request.getParameter("relation"));
	String inputtype = StringUtils.rightTrim(request.getParameter("inputtype"));
	String querytype = StringUtils.rightTrim(request.getParameter("querytype"));
	String limit = StringUtils.rightTrim(request.getParameter("limit"));
	String method = StringUtils.rightTrim(request.getParameter("method"));
	String fieldsign = StringUtils.rightTrim(request.getParameter("fieldsign"));
	String other = StringUtils.rightTrim(request.getParameter("other"));
	String liabDetailCode = StringUtils.rightTrim(request.getParameter("LiabDetailCode"));
	UIPowerInterface uiPowerInterface = new UIPowerInterface(); //权限校验
	//codevalue = new String(codevalue.getBytes("ISO8859-1")); //转换 //delete by wuxiaodong 050907 reasion 代码选择输入汉字查询时会将汉字转换为乱码
	StringBuffer buffer = new StringBuffer(); // 装载代码查询结果
	String queryResult = "";
	String errorMessage = ""; // 错误信息
	int recordSize = -1; // 记录条数信息 -1--null, 0--等於0, 1--大於0
	int i = 0;
	int index = 0;
	String[] arrRelation;
	String[] arrCodeName = new String[0];
	//得到请求页面的方式,以确定是初次下载页面,还是通过页面查询代码
	String submitMethod = request.getMethod();
	//险种从画面中取，待修正
	String strRiskCode = StringUtils.rightTrim(request.getParameter("riskcode"));
	UICodeAction uicodeAction = new UICodeAction();
	String strRiskType = uicodeAction.translateRiskCodetoRiskType(strRiskCode);
	String strProvinceCode = StringUtils.rightTrim(request.getParameter("provinceCode"));
	if (strRiskCode.length() == 0) {
		strRiskCode = "0000";
	}
	//语种从画面中取，待修正
	String strLanguage = "C";

	//保单投保险种的读取，需要增加policy的传入，待修正

	String strPolicyNo = StringUtils.rightTrim(request.getParameter("policyno"));
	if (strPolicyNo.length() == 0) {
		strPolicyNo = "";
	}
%>

<%
	//如果是POST方式请求页面,说明是要通过页面查询代码,执行以下查询程序,否则直接返回默认页面
	if (submitMethod.equals("POST")) // 以POST方式请求
	{
		Collection collection = new ArrayList();
		String conditions = ""; //代码查询条件
		UICodeAction uiCodeAction = new UICodeAction();
		//根据不同的代码类型进行查询
		try {
			//部门代码
			if (codetype.equals("ComCode")) //1。部门代码的转换
			{
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " comcode like '%" + codevalue + "%'";
					} else {
						conditions = " comcname like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " comcode like '%" + codevalue + "%'";
					} else {
						conditions = " comcname like '%" + codevalue + "%'";
					}
				}
				collection = uiCodeAction.findByConditions(UICodeAction.COMCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcompanyDto.getComCName());
					buffer.append("'>");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("-");
					buffer.append(prpDcompanyDto.getComCName());
				}
			} else if (codetype.equals("ComCodeByProvinceCode")) {
				String baseCondition = " (";
				baseCondition += "(";
				baseCondition += "substr(comtype, 6, 1) is null or substr(comtype, 6, 1) = '0'";
				baseCondition += ")";
				baseCondition += " and ";
				baseCondition += " substr(comcode, 2, 2) = '" + strProvinceCode + "'";
				baseCondition += ")";

				baseCondition += " or ";

				baseCondition += "(";
				baseCondition += " substr(comtype, 6, 1) = '1' ";
				baseCondition += " and ";
				baseCondition += "substr(uppercomcode, 2, 2) = '" + strProvinceCode + "'";
				baseCondition += ")";

				conditions = " (" + baseCondition + ") and (";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions += " comcode like '%" + codevalue + "%'" + ")";
					} else {
						conditions += " comcname like '%" + codevalue + "%'" + ")";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions += " comcode like '%" + codevalue + "%'" + ")";
					} else {
						conditions += " comcname like '%" + codevalue + "%'" + ")";
					}
				}
				collection = uiCodeAction.findByConditions(UICodeAction.COMCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcompanyDto.getComCName());
					buffer.append("'>");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("-");
					buffer.append(prpDcompanyDto.getComCName());
				}
			} else if (codetype.equals("ComCodeByComLevel")) {
				String baseCondition = " ComCode like '%000000000' ";

				conditions = " (" + baseCondition + ") and (";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions += " comcode like '%" + codevalue + "%'" + ")";
					} else {
						conditions += " comcname like '%" + codevalue + "%'" + ")";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions += " comcode like '%" + codevalue + "%'" + ")";
					} else {
						conditions += " comcname like '%" + codevalue + "%'" + ")";
					}
				}
				collection = uiCodeAction.findByConditions(UICodeAction.COMCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcompanyDto.getComCName());
					buffer.append("'>");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("-");
					buffer.append(prpDcompanyDto.getComCName());
				}
			} else if (codetype.equals("Currency")) //2。币别代码的转换
			{
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " currencycode like '%" + codevalue + "%'";
					} else {
						conditions = " currencycname like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " currencycode like '%" + codevalue + "%'";
					} else {
						conditions = " currencycname like '%" + codevalue + "%'";
					}
				}
				collection = uiCodeAction.findByConditions(UICodeAction.CURRENCYCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcurrencyDto prpDcurrencyDto = (PrpDcurrencyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcurrencyDto.getCurrencyCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcurrencyDto.getCurrencyCName());
					buffer.append("'>");
					buffer.append(prpDcurrencyDto.getCurrencyCode());
					buffer.append("-");
					buffer.append(prpDcurrencyDto.getCurrencyCName());
				}
			} else if (codetype.equals("KindCode")) //3。险别代码的转换
			{
				conditions = "riskcode='" + strRiskCode + "' AND ValidStatus='1'";
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = conditions + " AND kindcode like '%" + codevalue + "%'";
					} else {
						conditions = conditions + " AND kindcname like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = conditions + " AND kindcode like '%" + codevalue + "%'";
					} else {
						conditions = conditions + " AND kindcname like '%" + codevalue + "%'";
					}
				}
				collection = uiCodeAction.findByConditions(UICodeAction.KINDCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDkindDto prpDkindDto = (PrpDkindDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDkindDto.getKindCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDkindDto.getKindCName());
					buffer.append("'>");
					buffer.append(prpDkindDto.getKindCode());
					buffer.append("-");
					buffer.append(prpDkindDto.getKindCName());
				}
			} else if (codetype.equals("ClauseCode")) //4。条款代码的转换
			{
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " ClauseCode like '%" + codevalue + "%'";
					} else {
						conditions = " ClauseName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " ClauseCode = '" + codevalue + "'";
					} else {
						conditions = " ClauseName = '" + codevalue + "'";
					}
				}
				conditions = conditions + " AND LineNo=1" + " AND ValidStatus='1'" + " AND ClauseCode IN" + "( SELECT CodeCode FROM PrpDcodeRisk " + " WHERE CodeType='ClauseCode'" + " AND ( RiskCode='" + strRiskCode + "' OR RiskCode='0000' )" + " )" + " ORDER BY ClauseCode";
				collection = uiCodeAction.findByConditions(UICodeAction.CLAUSECODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDclauseDto prpDclauseDto = (PrpDclauseDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDclauseDto.getClauseCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDclauseDto.getClauseName());
					buffer.append("'>");
					buffer.append(prpDclauseDto.getClauseCode());
					buffer.append("-");
					buffer.append(prpDclauseDto.getClauseName());
				}
			} else if (codetype.equals("DamageCode")) //5。出险原因代码的转换
			{
				////System.out.print("要开始测试代码dedamageCode啦----："+conditions);
				//根据出险原因大类进行查找
				String[] theMain = StringUtils.split(codevalue.trim(), "|");
				conditions = "";
				if (theMain[0].equals("9500")) {
					conditions = conditions + "codecode like '95%' and";
					//conditions = conditions + "codecode like '50%' and";
				} else if (theMain[0].equals("9600")) {
					conditions = conditions + "codecode like '96%' and";
					//conditions = conditions + "codecode like '60%' and";
				} else if (theMain[0].equals("9700")) {
					conditions = conditions + "codecode like '97%' and";
					//conditions = conditions + "codecode like '70%' and";
				} else {
					//查询所有
				}

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = conditions + " CodeCode like '%" + theMain[1] + "%'";
					} else {
						conditions = conditions + " CodeCName like '%" + theMain[1] + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = conditions + " CodeCode = '" + theMain[1] + "'";
					} else {
						conditions = conditions + " CodeCName = '" + theMain[1] + "'";
					}
				}

				conditions = " codeType ='" + codetype + "' and codecode in (select codecode from PrpDcodeRisk where riskcode='" + strRiskCode + "' and codetype='" + codetype + "') and " + conditions + " order by codecode";
				//conditions = " riskcode='"+strRiskCode+"' and codeType ='"+ codetype +"' and "+ conditions+" order by codecode";
				collection = uiCodeAction.findByConditions(UICodeAction.DAMAGECODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcodeDto.getCodeCName());
					buffer.append("'>");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("-");
					buffer.append(prpDcodeDto.getCodeCName());
				}

			} else if (codetype.equals("DamageAreaCode")) //6。出险区域代码的转换
			{

				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " CodeCode like '%" + codevalue + "%'";
					} else {
						conditions = " CodeCName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " CodeCode = '" + codevalue + "'";
					} else {
						conditions = " CodeCName = '" + codevalue + "'";
					}
				}

				conditions = " codeType ='" + codetype + "' and " + conditions + " order by codecode";

				collection = uiCodeAction.findByConditions(UICodeAction.DAMAGEAREACODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcodeDto.getCodeCName());
					buffer.append("'>");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("-");
					buffer.append(prpDcodeDto.getCodeCName());
				}

			} else if (codetype.equals("DamageTypeCode")) //7。事故类型代码的转换
			{
				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " CodeCode like '%" + codevalue + "%'";
					} else {
						conditions = " CodeCName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " CodeCode = '" + codevalue + "'";
					} else {
						conditions = " CodeCName = '" + codevalue + "'";
					}
				}

				//conditions = " codeType ='"+ codetype +"' and "+ conditions+" order by codecode";
				conditions = " codeType ='" + codetype + "' and codecode in (select codecode from PrpDcodeRisk where (riskcode='" + strRiskCode + "'  OR riskcode='0000') and codetype='" + codetype + "') and " + conditions + " order by codecode";
				collection = uiCodeAction.findByConditions(UICodeAction.DAMAGETYPECODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcodeDto.getCodeCName());
					buffer.append("'>");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("-");
					buffer.append(prpDcodeDto.getCodeCName());
				}

			} else if (codetype.equals("InjuryGrade")) //伤残等级、
			{

				conditions = "";
				String[] arrInjuryGrade = StringUtils.split(codevalue.trim(), "|");
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " CodeCode like '%" + arrInjuryGrade[1] + "%'";
					} else {
						conditions = " CodeCName like '%" + arrInjuryGrade[1] + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " CodeCode = '" + arrInjuryGrade[1] + "'";
					} else {
						conditions = " CodeCName = '" + arrInjuryGrade[1] + "'";
					}
				}

				//只有伤残类责任才能选择伤残等级
				if (arrInjuryGrade[0] == null || arrInjuryGrade[0].length() < 3 || arrInjuryGrade[0].substring(1, 2).equals("1")) {
					//conditions = " codeType ='"+ codetype +"' and "+ conditions+" order by codecode";
					conditions = " codeType ='" + codetype + "' and codecode in (select codecode from PrpDcodeRisk where (riskcode='" + strRiskCode + "'  OR riskcode='0000') and codetype='" + codetype + "') and " + conditions + " order by codecode";
				} else {
					conditions = "1=2";
				}
				collection = uiCodeAction.findByConditions(UICodeAction.INJURYGRADE, conditions, 0, 0);
				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcodeDto.getCodeCName());
					buffer.append("'>");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("-");
					buffer.append(prpDcodeDto.getCodeCName());
				}

			}

			else if (codetype.equals("DocCode")) //8。单证代码的转换
			{

				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " CodeCode like '%" + codevalue + "%'";
					} else {
						conditions = " CodeCName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " CodeCode = '" + codevalue + "'";
					} else {
						conditions = " CodeCName = '" + codevalue + "'";
					}
				}

				conditions = " codeType ='" + codetype + "' and " + conditions + " order by codecode";

				collection = uiCodeAction.findByConditions(UICodeAction.DOCCODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcodeDto.getCodeCName());
					buffer.append("'>");
					buffer.append(prpDcodeDto.getCodeCode());
					buffer.append("-");
					buffer.append(prpDcodeDto.getCodeCName());
				}

			} else if (codetype.equals("HanderCode")) //9。接案人 经办人
			{

				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " UserCode like '%" + codevalue + "%'";
					} else {
						conditions = " UserName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " UserCode = '" + codevalue + "'";
					} else {
						conditions = " UserCName = '" + codevalue + "'";
					}
				}

				UserDto userDto = (UserDto) session.getAttribute("user");
				String comCode = userDto.getComCode();

				conditions = "  ValidStatus='1' and comcode like '" + comCode.substring(0, 3) + "%' and " + conditions + " order by usercode";

				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDuserDto.getUserCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDuserDto.getUserName());
					buffer.append("'>");
					buffer.append(prpDuserDto.getUserCode());
					buffer.append("-");
					buffer.append(prpDuserDto.getUserName());
				}

			} else if (codetype.equals("HandleUnit")) //10。事故处理部门
			{
				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " ComCode like '%" + codevalue + "%'";
					} else {
						conditions = " ComCName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " ComCode = '" + codevalue + "'";
					} else {
						conditions = " ComCName = '" + codevalue + "'";
					}
				}

				conditions = " ComLevel ='5'  and " + conditions + " order by ComCode";

				collection = uiCodeAction.findByConditions(UICodeAction.HANDLEUNIT, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcompanyDto.getComCName());
					buffer.append("'>");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("-");
					buffer.append(prpDcompanyDto.getComCName());
				}

			} else if (codetype.equals("InsureComCode")) //11。承保公司
			{

				conditions = "";

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " ComCode like '%" + codevalue + "%'";
					} else {
						conditions = " ComCName like '%" + codevalue + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " ComCode = '" + codevalue + "'";
					} else {
						conditions = " ComCName = '" + codevalue + "'";
					}
				}

				conditions = " ValidStatus='1'  and " + conditions + " order by ComCode";

				collection = uiCodeAction.findByConditions(UICodeAction.INSURECOMCODE, conditions, 0, 0);

				recordSize = collection.size();

				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDcompanyDto.getComCName());
					buffer.append("'>");
					buffer.append(prpDcompanyDto.getComCode());
					buffer.append("-");
					buffer.append(prpDcompanyDto.getComCName());
				}

			} else if (codetype.equals("PolicyKindCodeOfPerson")) //查找某分户在保单上投保的险别
			{
				conditions = "";
				String strFlag = "";
				String[] arrPolicyKind = StringUtils.split(codevalue.trim(), "|");
				int familyNo = Integer.parseInt(arrPolicyKind[0]);
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " KindCode like '%" + arrPolicyKind[1] + "%'";
					} else {
						conditions = " KindName like '%" + arrPolicyKind[1] + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " KindCode = '" + arrPolicyKind[1] + "'";
					} else {
						conditions = " KindName = '" + arrPolicyKind[1] + "'";
					}
				}
				//conditions = " PolicyNo ='"+ strPolicyNo +"' and "
				//        + conditions+" order by Kindcode";
				conditions = " PolicyNo ='" + strPolicyNo + "' and FAMILYNO =" + familyNo + " and " + conditions + " order by Kindcode"; //增加查询条件 2005-09-20
				collection = uiCodeAction.findByConditions(UICodeAction.POLICYKINDCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
					if (prpCitemKindDto.getFamilyNo() != familyNo) {
						continue;
					}
					buffer.append("<option value='");
					buffer.append(prpCitemKindDto.getKindCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpCitemKindDto.getKindName());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpCitemKindDto.getItemKindNo());
					buffer.append("'>");
					buffer.append(prpCitemKindDto.getKindCode());
					buffer.append("-");
					buffer.append(prpCitemKindDto.getKindName());
				}

			} else if (codetype.equals("PrpDliab")) //按照险种和险别查找责任类别
			{
				conditions = "";
				String strFlag = "";
				String[] arrValue = StringUtils.split(codevalue.trim(), "|");
				String kindCode = arrValue[0];

				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						conditions = " LiabCode like '%" + arrValue[1] + "%'";
					} else {
						conditions = " LiabCName like '%" + arrValue[1] + "%'";
					}
				} else {
					if (inputtype.equals("code")) {
						conditions = " LiabCode = '" + arrValue[1] + "'";
					} else {
						conditions = " LiabCode = '" + arrValue[1] + "'";
					}
				}
				//modify by liping 20060109 start
				conditions = " riskCode ='" + strRiskCode + "' AND " + conditions + " order by Liabcode";
				//middify by liping 20060109 end
				collection = uiCodeAction.findByConditions(UICodeAction.LIABCODE, conditions, 0, 0);
				recordSize = collection.size();
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
					PrpDliabDto prpDliabDto = (PrpDliabDto) iterator.next();
					buffer.append("<option value='");
					buffer.append(prpDliabDto.getLiabCode());
					buffer.append("_FIELD_SEPARATOR_");
					buffer.append(prpDliabDto.getLiabCName());
					buffer.append("'>");
					buffer.append(prpDliabDto.getLiabCode());
					buffer.append("-");
					buffer.append(prpDliabDto.getLiabCName());
				}

			}

			//------------------------------------------------------------------------------
			else if (codetype.equals("ModelCode")) //车型代码
			{
				String[] arrModel = StringUtils.split(codevalue.trim(), "|");

				conditions = "validstatus='1' ";
				if (method.equals("select")) {
					if (inputtype.equals("code")) {
						if (!arrModel[3].trim().equals(""))
							conditions += " AND ModelCode like '%" + arrModel[3] + "%'";
						if (!arrModel[1].trim().equals(""))
							conditions += " AND CarStyle like '%" + arrModel[1] + "%'";
					} else {
						if (!arrModel[2].trim().equals(""))
							conditions += " AND ModelCode like '%" + arrModel[2] + "%'";
						if (!arrModel[3].trim().equals(""))
							conditions += " AND ModelName like '%" + arrModel[3] + "%'";
						if (!arrModel[1].trim().equals(""))
							conditions += " AND CarStyle like '%" + arrModel[1] + "%'";
					}
				} else {
					if (inputtype.equals("code"))
						conditions = conditions + " AND ModelCode = '" + arrModel[3] + "'";
					else
						conditions = conditions + " AND ModelName = '" + arrModel[3] + "'";
				}

				codevalue = arrModel[3];

				/* 
				  if(strRiskCode!=null && strRiskCode.equals("0502"))
				 {
				    conditions+=" AND carkind='摩托车类'";
				    if(inputtype.equals("select"))
				    {
				         conditions+=" or ModelCode ='9999999999'";
				    }
				 }
				 */

				conditions += " ORDER BY ModelCode";

				collection = uiCodeAction.findByConditions("ModelCode", conditions, 0, 0);
				recordSize = collection.size();

				if (recordSize > 300) {
%>
             <script language='javascript'> 
             alert('查询结果超过300条，请重新输入条件查询');
           
             </script>   
           <%
              	} else {
              					for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              						PrpDcarModelDto prpDcarModelDto = (PrpDcarModelDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(prpDcarModelDto.getModelCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDcarModelDto.getModelName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDcarModelDto.getSeatCount());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(new DecimalFormat("###0.00").format(prpDcarModelDto.getTonCount()));//车型代码
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDcarModelDto.getPurchasePrice());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(new DecimalFormat("###0.00").format(prpDcarModelDto.getPurchasePrice()));
              						buffer.append("'>");
              						buffer.append(prpDcarModelDto.getModelCode());
              						buffer.append("-");
              						buffer.append(prpDcarModelDto.getModelName());

              						if (!prpDcarModelDto.getCarSeriesName().trim().equals("")) {
              							buffer.append("-");
              							buffer.append(prpDcarModelDto.getCarSeriesName());
              						}
              					}
              				}
              			}

              			//-------------------------------------------------------------------------------

              			else if (codetype.equals("PolicyKindCode")) //12。保单投保险别 需要已保单参数作为转换条件
              			{
              				conditions = "";
              				String[] arrValue = StringUtils.split(codevalue.trim(), "|");
              				String PolicyBzNo = arrValue[2];
              				String startDate = arrValue[0];
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " KindCode like '%" + arrValue[1] + "%'";
              					} else {
              						conditions = " KindName like '%" + arrValue[1] + "%'";
              					}
              				} else { //System.out.println ( " ======[ method <> select ]====" );
              					if (inputtype.equals("code")) {
              						conditions = " KindCode = '" + arrValue[1] + "'";
              					} else {
              						conditions = " KindName = '" + arrValue[1] + "'";
              					}
              				}

              				conditions = " PolicyNo ='" + strPolicyNo + "'  and " + conditions + " order by Kindcode";
              				//System.out.println("======conditions========="+ conditions);

              				EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
              				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, startDate);
              				collection = policyDto.getPrpCitemKindDtoList();
              				//add by qinyongli 2006-06-06 begin 加入强制保单险别信息
              				if (PolicyBzNo != null && !PolicyBzNo.equals("")) {
              					policyDto = endorseViewHelper.findForEndorBefore(PolicyBzNo, startDate);
              					collection.addAll(policyDto.getPrpCitemKindDtoList());
              				}
              				//add by qinyongli 
              				recordSize = collection.size();
              				if (strRiskCode.trim().equals("QAA")) {
              					for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              						PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getKindCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getKindName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemKindNo());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getKindCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getKindName());
              					}
              				} else {
              					for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              						PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              						if (querytype.equals("selectb")) {
              							if (!prpCitemKindDto.getKindCode().trim().equals(ConstantCodes.KINDCODE_D_B) && !prpCitemKindDto.getKindCode().trim().equals(ConstantCodes.KINDCODE_D_BZ)) {

              								continue;
              							}
              						}
              						if (querytype.equals("selectnotb")) {
              							//System.out.println ( " ======[biaodi 222]===="  ); 
              							if (prpCitemKindDto.getKindCode().trim().equals(ConstantCodes.KINDCODE_D_B) || prpCitemKindDto.getKindCode().trim().equals(ConstantCodes.KINDCODE_D_BZ)) {
              								//System.out.println ( " ======[biaodi555 ]===="  );
              								continue;
              							}
              						}

              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getKindCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getKindName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemKindNo());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getKindCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getKindName());

              					}
              				}

              			}

              			else if (codetype.equals("PolicyItemCode")) //21。保单责任 需要已保单参数、险别作为转换条件
              			{
              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " ItemCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " ItemDetailName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " ItemCode = '" + codevalue + "'";
              					} else {
              						conditions = " ItemDetailName = '" + codevalue + "'";
              					}
              				}

              				conditions = " PolicyNo ='" + strPolicyNo + "' AND KindCode ='" + other + "' and " + conditions + " order by Itemcode";
              				collection = uiCodeAction.findByConditions(UICodeAction.POLICYITEMCODE, conditions, 0, 0);

              				recordSize = collection.size();
              				if (strRiskCode.trim().equals("QAA")) {
              					for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              						PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              					}
              				} else {
              					for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              						PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              					}
              				}

              			} else if (codetype.equals("PolicyKindCodeForProp")) //19。保单投保险别 for 定损 需要已保单参数作为转换条件
              			{

              				conditions = "";
              				String[] arrValue = StringUtils.split(codevalue.trim(), "|");
              				String PolicyBzNo = arrValue[0];
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " KindCode like '%" + arrValue[1] + "%'";
              					} else {
              						conditions = " KindName like '%" + arrValue[1] + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " KindCode = '" + arrValue[1] + "'";
              					} else {
              						conditions = " KindName = '" + arrValue[1] + "'";
              					}
              				}

              				conditions = "( PolicyNo ='" + strPolicyNo + "' or PolicyNo ='" + PolicyBzNo + "'  ) and "

              				+ conditions + " and (KindCode='" + ConstantCodes.KINDCODE_D_BZ + "' or KindCode='B' or KindCode='D2' or KindCode='H') order by Kindcode";
              				collection = uiCodeAction.findByConditions(UICodeAction.POLICYKINDCODE, conditions, 0, 0);
              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getKindName());
              					buffer.append("'>");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("-");
              					buffer.append(prpCitemKindDto.getKindName());
              				}

              			}

              			else if (codetype.equals("PolicyKindCodeForPerson")) //20。保单投保险别 for 定损 需要已保单参数作为转换条件
              			{

              				conditions = "";
              				String[] arrValue = StringUtils.split(codevalue.trim(), "|");
              				String PolicyBzNo = arrValue[0];
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " KindCode like '%" + arrValue[1] + "%'";
              					} else {
              						conditions = " KindName like '%" + arrValue[1] + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " KindCode = '" + arrValue[1] + "'";
              					} else {
              						conditions = " KindName = '" + arrValue[1] + "'";
              					}
              				}

              				conditions = "( PolicyNo ='" + strPolicyNo + "' or PolicyNo ='" + PolicyBzNo + "') and "

              				+ conditions + " and (KindCode='" + ConstantCodes.KINDCODE_D_BZ + "' or KindCode='B' or KindCode='D1' or KindCode='D3' or KindCode='D4') order by Kindcode";

              				//System.out.println(" ============" +conditions);
              				collection = uiCodeAction.findByConditions(UICodeAction.POLICYKINDCODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					//System.out.print("==========================="+prpCitemKindDto.getKindCode()+prpCitemKindDto.getKindName());
              					buffer.append("<option value='");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getKindName());
              					buffer.append("'>");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("-");
              					buffer.append(prpCitemKindDto.getKindName());
              				}

              			} else if (codetype.equals("PropertyFeeType")) //22. 财产费用转换
              			{
              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				conditions = " ValidStatus='1' and " + conditions + " order by codecode";

              				collection = uiCodeAction.findByConditions("PropertyFeeType", conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			}

              			else if (codetype.equals("PersonFeeType")) //21. 新的人伤费用类型转换
              			{
              				conditions = "riskcode='" + strRiskCode + "' AND ValidStatus='1'";
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " feecode like '%" + codevalue + "%'";
              					} else {
              						conditions = " name like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " code = '" + codevalue + "'";
              					} else {
              						conditions = " name = '" + codevalue + "'";
              					}
              				}

              				collection = UIPersonFeeAction.getInstance().findAllCodeList(strRiskCode);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDpersonFeeCodeRiskDto prpDpersonFeeCodeRiskDto = (PrpDpersonFeeCodeRiskDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDpersonFeeCodeRiskDto.getFeeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDpersonFeeCodeRiskDto.getFeeName());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDpersonFeeCodeRiskDto.getFeeCategory());
              					buffer.append("'>");
              					buffer.append(prpDpersonFeeCodeRiskDto.getFeeCode());
              					buffer.append("-");
              					buffer.append(prpDpersonFeeCodeRiskDto.getFeeName());
              				}

              			}

              			else if (codetype.equals("PersonFeeTypeCode")) //22. 人员费用转换(非车险调用）
              			{
              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				//add  start reason：理算中人员需显示的赔款项目列表
              				//if (strRiskCode.substring(0,2).equals("04")){
              				conditions = " ValidStatus='1' and  codeType='FeePersonCode'"; //and codeCode in ('10','11','24','15','14','34','21','20','99','01','02','03','16') ";
              				// }
              				//add end   
              				collection = uiCodeAction.findByConditions(UICodeAction.CHARGECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			} else if (codetype.equals("ChargeCode")) //13。费用代码
              			{

              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				//modify by weishixin add 20041026 begin
              				//reason:根据险种过滤不同代码
              				conditions = " ValidStatus='1' AND codeType ='" + codetype + "' AND " + conditions + " AND codecode in (SELECT codecode FROM prpdcoderisk WHERE codetype='" + codetype + "' AND (riskcode='" + strRiskCode + "' OR riskcode='0000') )";

              				//conditions = " codeType ='"+ codetype +"' and "+ conditions+" order by codecode";
              				//modify by weishixin add 20041026 end

              				collection = uiCodeAction.findByConditions(UICodeAction.CHARGECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			}

              			else if (codetype.equals("ChargeCode1")) //13。费用代码
              			{

              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				//modify by weishixin add 20041026 begin
              				//reason:根据险种过滤不同代码
              				conditions = " ValidStatus='1' AND codeType ='ChargeCode' AND " + conditions + " AND codecode in (SELECT codecode FROM prpdcoderisk WHERE codetype='ChargeCode' AND (riskcode='" + strRiskCode + "' OR riskcode='0000') )";

              				//conditions = " codeType ='ChargeCode' and "+ conditions+" order by codecode";
              				//modify by weishixin add 20041026 end

              				collection = uiCodeAction.findByConditions(UICodeAction.CHARGECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			}

              			else if (codetype.equals("DamageAddress")) //14。出险地址
              			{

              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				conditions = " codeType ='" + codetype + "' and " + conditions + " order by codecode";

              				collection = uiCodeAction.findByConditions(UICodeAction.CHARGECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			} else if (codetype.equals("ScheduleObject")) //15。调度对象
              			{

              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " ScheduleObjectID like '%" + codevalue + "%'";
              					} else {
              						conditions = " ScheduleObjectName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " ScheduleObjectID= '" + codevalue + "'";
              					} else {
              						conditions = "ScheduleObjectName = '" + codevalue + "'";
              					}
              				}

              				conditions = conditions + " order by ScheduleObjectID";

              				collection = uiCodeAction.findByConditions(UICodeAction.SCHEDULEOBJECT, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpLscheduleObjectDto prpLscheduleObjectDto = (PrpLscheduleObjectDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpLscheduleObjectDto.getScheduleObjectID());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpLscheduleObjectDto.getScheduleObjectName());
              					buffer.append("'>");
              					buffer.append(prpLscheduleObjectDto.getScheduleObjectID());
              					buffer.append("-");
              					buffer.append(prpLscheduleObjectDto.getScheduleObjectName());
              				}

              			} else if (codetype.equals("CompCode") || codetype.equals("BusinessSource") || codetype.equals("DamageDistrict") || codetype.equals("CarPartCode") || codetype.equals("AcciCheckChargeType"))
              			//16。损失部件转换
              			//17。所在地区转换
              			//18。所在行业转换
              			//23。车辆受损部位的转换
              			{

              				conditions = "";

              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				conditions = " codeType ='" + codetype + "' and " + conditions + " order by codecode";
              				collection = uiCodeAction.findByConditions(UICodeAction.CHARGECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			}

              			else if (codetype.equals("ClamCode")) {
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " claimcode like '%" + codevalue + "%'";
              					} else {
              						conditions = " longname like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " reinscode like '%" + codevalue + "%'";
              					} else {
              						conditions = " longname  like '%" + codevalue + "%'";
              					}
              				}
              				collection = uiCodeAction.findByConditions(UICodeAction.REINSCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//PrpDreinsDto prpDreinsDto = (PrpDreinsDto)iterator.next();
              					//buffer.append("<option value='");
              					//buffer.append(prpDreinsDto.getPrpDreinsReinsCode());
              					//buffer.append("_FIELD_SEPARATOR_");
              					//buffer.append(prpDreinsDto.getPrpDreinsLongName());
              					//buffer.append("'>");
              					//buffer.append(prpDreinsDto.getPrpDreinsReinsCode());
              					//buffer.append("-");
              					//buffer.append(prpDreinsDto.getPrpDreinsLongName());
              				}
              			}

              			else if (codetype.equals("KindCode4")) //BLPrpCitemKind 承保保单中的险别
              			{

              				String damageStartDate = request.getParameter("DamageStartDate");
              				EndorseViewHelper endorseViewHelper = new EndorseViewHelper();

              				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, damageStartDate);
              				collection = (ArrayList) policyDto.getPrpCitemKindDtoList();
              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getKindName());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getItemKindNo());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getItemDetailName());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getAmount()); //==itemValue
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getQuantity());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getUnitAmount());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getDeductible());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getDeductibleRate());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getAmount());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getFamilyName());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getItemCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getCurrency());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(uiCodeAction.translateCodeCode(strRiskCode, prpCitemKindDto.getItemCode(), true));
              					buffer.append("'>");
              					buffer.append(prpCitemKindDto.getKindCode());
              					buffer.append("-");
              					buffer.append(prpCitemKindDto.getKindName());

              				}

              			}

              			else if (codetype.equals("HandleUnitCar")) //事故处理部门(用於车险）
              			{

              				//原因：添加车险的事故处理部门查询代码，从prpDcode中查询。
              				conditions = " codetype = 'HandleUnit'";

              				collection = uiCodeAction.findByConditions(UICodeAction.HandleUnitCar, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcode = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcode.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcode.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcode.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcode.getCodeCName());
              				}
              			} else if (codetype.equals("CaseCode")) { //25.案件性质

              				//原因：添加意键险的案件性质
              				conditions = "";
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				conditions = " codeType ='" + codetype + "' and codecode in (select codecode from PrpDcodeRisk where riskcode='" + strRiskCode + "' and codetype='" + codetype + "') and " + conditions + " order by codecode";
              				//conditions = " riskcode='"+strRiskCode+"' and codeType ='"+ codetype +"' and "+ conditions+" order by codecode";

              				collection = uiCodeAction.findByConditions(UICodeAction.CASECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			} else if (codetype.equals("CatastropheCode")) { //26.巨灾代码

              				//原因：添加巨灾代码
              				conditions = "";
              				if (method.equals("select")) {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode like '%" + codevalue + "%'";
              					} else {
              						conditions = " CodeCName like '%" + codevalue + "%'";
              					}
              				} else {
              					if (inputtype.equals("code")) {
              						conditions = " CodeCode = '" + codevalue + "'";
              					} else {
              						conditions = " CodeCName = '" + codevalue + "'";
              					}
              				}

              				conditions = " codeType ='" + codetype + "' and codecode in (select codecode from PrpDcodeRisk where riskcode='0000' and codetype='" + codetype + "') and " + conditions + " order by codecode";
              				//conditions = " riskcode='"+strRiskCode+"' and codeType ='"+ codetype +"' and "+ conditions+" order by codecode";

              				collection = uiCodeAction.findByConditions(UICodeAction.CATASTROPHECODE, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpDcodeDto prpDcodeDto = (PrpDcodeDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpDcodeDto.getCodeCName());
              					buffer.append("'>");
              					buffer.append(prpDcodeDto.getCodeCode());
              					buffer.append("-");
              					buffer.append(prpDcodeDto.getCodeCName());
              				}

              			}
              			//增加查询标的信息，根据保单号和险别
              			else if (codetype.equals("PolicyItemKindCode")) {
              				String[] theKindCode = StringUtils.split(codevalue.trim(), "|");
              				PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
              				String startDate = theKindCode[2];
              				EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
              				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, startDate);

              				Collection itemKindList = policyDto.getPrpCitemKindDtoList();
              				recordSize = itemKindList.size();
              				for (Iterator iterator = itemKindList.iterator(); iterator.hasNext();) {
              					prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					if (prpCitemKindDto.getKindCode().equals(theKindCode[0])) {
              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemKindNo());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(NumberFormat.getInstance().format(prpCitemKindDto.getAmount()));
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getAmount());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getDeductibleRate());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              					}
              				}
              			} else if (codetype.equals("PolicyItemKindCode1")) { //增加查询标的信息，根据保单号和险别 2005-9-29
              				String[] theKindCode = StringUtils.split(codevalue.trim(), "|");
              				PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
              				String startDate = theKindCode[2];
              				EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
              				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, startDate);

              				Collection itemKindList = policyDto.getPrpCitemKindDtoList();
              				recordSize = itemKindList.size();
              				for (Iterator iterator = itemKindList.iterator(); iterator.hasNext();) {
              					prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					if (prpCitemKindDto.getKindCode().equals(theKindCode[0])) {
              						buffer.append("<option value='");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(NumberFormat.getInstance().format(prpCitemKindDto.getAmount()));
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCitemKindDto.getAmount());
              						buffer.append("'>");
              						buffer.append(prpCitemKindDto.getItemCode());
              						buffer.append("-");
              						buffer.append(prpCitemKindDto.getItemDetailName());
              					}
              				}
              			} //增加查询标的信息，根据保单号(和险别无关)
              			else if (codetype.equals("PolicyItemKindCodeNoRisk")) {
              				String[] theKindCode = StringUtils.split(codevalue.trim(), "|");
              				PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
              				String startDate = theKindCode[1];
              				EndorseViewHelper endorseViewHelper = new EndorseViewHelper();
              				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(strPolicyNo, startDate);

              				Collection itemKindList = policyDto.getPrpCitemKindDtoList();
              				recordSize = itemKindList.size();

              				//去除重复的标的
              				Map itemKindMap = new HashMap();
              				for (Iterator iterator = itemKindList.iterator(); iterator.hasNext();) {
              					prpCitemKindDto = (PrpCitemKindDto) iterator.next();
              					if (prpCitemKindDto.getItemCode() != null && prpCitemKindDto.getItemCode().length() != 0) {
              						itemKindMap.put(prpCitemKindDto.getItemCode(), prpCitemKindDto);
              					}
              				}
              				Set itemKindSet = itemKindMap.keySet();
              				for (Iterator iter = itemKindSet.iterator(); iter.hasNext();) {
              					String itemCode = (String) iter.next();
              					prpCitemKindDto = (PrpCitemKindDto) itemKindMap.get(itemCode);
              					//          for(Iterator iterator = itemKindList.iterator();iterator.hasNext();){
              					//              prpCitemKindDto =(PrpCitemKindDto)iterator.next(); 
              					//              if (prpCitemKindDto.getKindCode().equals(theKindCode[0])) {
              					buffer.append("<option value='");
              					buffer.append(prpCitemKindDto.getItemCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getItemDetailName());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getItemKindNo());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(NumberFormat.getInstance().format(prpCitemKindDto.getAmount()));
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getAmount());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpCitemKindDto.getDeductibleRate());
              					buffer.append("'>");
              					buffer.append(prpCitemKindDto.getItemCode());
              					buffer.append("-");
              					buffer.append(prpCitemKindDto.getItemDetailName());
              					//}
              					// }
              				}
              			} else if (codetype.equals("PayObject")) //30。支付对象代码
              			{

              				conditions = "";
              				//System.out.println(codevalue.trim());
              				String[] thePayObject = StringUtils.split(codevalue.trim(), "|");
              				String payObjectType = thePayObject[0];
              				String payObjectName = thePayObject[1];

              				if (method.equals("select")) {

              					conditions = " PayObjectName like '%" + payObjectName + "%'";
              				} else {
              					conditions = " PayObjectName = '" + payObjectName + "'";
              				}

              				conditions = " ValidStatus='1' AND PayObjectType='" + payObjectType + "' And " + conditions;
              				//System.out.println(conditions);

              				collection = uiCodeAction.findByConditions(UICodeAction.PAYOBJECT, conditions, 0, 0);

              				recordSize = collection.size();

              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpLpayObjectDto prpLpayObjectDto = (PrpLpayObjectDto) iterator.next();
              					buffer.append("<option value='");
              					buffer.append(prpLpayObjectDto.getPayObjectCode());
              					buffer.append("_FIELD_SEPARATOR_");
              					buffer.append(prpLpayObjectDto.getPayObjectName());
              					buffer.append("'>");
              					buffer.append(prpLpayObjectDto.getPayObjectCode());
              					buffer.append("-");
              					buffer.append(prpLpayObjectDto.getPayObjectName());
              				}

              			} else if (codetype.equals("prpCinsured")) {

              				//原因：添加意健险的查询代码，从PrpCinsured中查询。
              				UIPolicyAction uiPolicyAction = new UIPolicyAction();
              				PolicyDto policyDto = uiPolicyAction.findByPrimaryKey(strPolicyNo);
              				collection = policyDto.getPrpCinsuredDtoList();

              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					PrpCinsuredDto prpCinsuredDto = (PrpCinsuredDto) iterator.next();
              					if (prpCinsuredDto.getInsuredFlag().equals("1") || prpCinsuredDto.getInsuredFlag().equals("1")) {
              						//add by qinyongli start 2005-8-2 加入保险人详细信息
              						String policyNo = prpCinsuredDto.getPolicyNo();
              						int serialNo = prpCinsuredDto.getSerialNo();
              						BLPrpCinsuredNatureFacade blPrpCinsuredNatureFacade = new BLPrpCinsuredNatureFacade();
              						PrpCinsuredNatureDto prpCinsuredNatureDto = blPrpCinsuredNatureFacade.findByPrimaryKey(policyNo, serialNo);
              						//只显示连带被保险人和保险人
              						buffer.append("<option value='");
              						buffer.append(prpCinsuredDto.getInsuredCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCinsuredDto.getInsuredName());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCinsuredNatureDto.getSex());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCinsuredNatureDto.getAge());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpCinsuredDto.getIdentifyNumber());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(serialNo);
              						buffer.append("'>");
              						buffer.append(prpCinsuredDto.getInsuredCode());
              						buffer.append("-");
              						buffer.append(prpCinsuredDto.getInsuredName());
              					}
              				}
              			}
              			//添加查勘人员信息
              			else if (codetype.equals("CheckPerson")) //27.查勘人员信息
              			{
              				String checkCode = "";//校验代码
              				String taskCode = "";//得到任务代码
              				boolean checkPower = false; //是否通过校验
              				checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
              				taskCode = AppConfig.get("sysconst.TASKCODE_LP");

              				conditions = "";

              				//查询本机构下的所有人员prpduser表
              				conditions = "  ValidStatus='1' " + " AND comCode in ( select ComCode from prpdCompany Start With ComCode = '" + other + "' Connect By Prior comCode =  uppercomCode" + " and prior comcode != comcode and validstatus='1')";

              				if (!codevalue.trim().equals("")) {
              					if (inputtype.equals("code")) {
              						conditions = conditions + " and UserCode like '%" + codevalue + "%'";
              					} else {
              						conditions = conditions + " and UserName like '%" + codevalue + "%'";
              					}
              				}

              				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//对查询出的人员进行权限校验
              					//modify by zhaolu 20060815 start
              					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
              					com.sinosoft.platform.dto.domain.PrpDuserDto platformPrpDuserDto = new com.sinosoft.platform.dto.domain.PrpDuserDto();
              					platformPrpDuserDto.setLoginComCode(prpDuserDto.getComCode());
              					platformPrpDuserDto.setUserCode(prpDuserDto.getUserCode());
              					checkPower = UIPowerAction.checkPowerReturn(platformPrpDuserDto, Constants.TASK_CLAIM_CHECK);
              					//modify by zhaolu 20060815 start
              					if (checkPower) {
              						buffer.append("<option value='");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDuserDto.getUserName());
              						buffer.append("'>");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("-");
              						buffer.append(prpDuserDto.getUserName());
              					}
              				}
              			} else if (codetype.equals("CertaHanderCode")) //50.定损人员信息
              			{
              				String checkCode = "";//校验代码
              				String taskCode = "";//得到任务代码
              				boolean checkPower = false; //是否通过校验
              				checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
              				taskCode = "lpds";

              				conditions = "";

              				//查询本机构下的所有人员prpduser表
              				conditions = "  ValidStatus='1' " + " AND comCode in ( select ComCode from prpdCompany Start With ComCode = '" + other + "' Connect By Prior comCode =  uppercomCode" + " and prior comcode != comcode and validstatus='1')";

              				if (!codevalue.trim().equals("")) {
              					if (inputtype.equals("code")) {
              						conditions = conditions + " and UserCode like '%" + codevalue + "%'";
              					} else {
              						conditions = conditions + " and UserName like '%" + codevalue + "%'";
              					}
              				}
              				//System.out.println(conditions);
              				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//对查询出的人员进行权限校验
              					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
              					//modify bu zhaolu 20060815 start
              					com.sinosoft.platform.dto.domain.PrpDuserDto platformPrpDuserDto = new com.sinosoft.platform.dto.domain.PrpDuserDto();
              					platformPrpDuserDto.setLoginComCode(prpDuserDto.getComCode());
              					platformPrpDuserDto.setUserCode(prpDuserDto.getUserCode());
              					checkPower = UIPowerAction.checkPowerReturn(platformPrpDuserDto, Constants.TASK_CLAIM_CERTAINLOSS);
              					//modify by zhaolu 20060815 end
              					//checkPower=uiPowerInterface.checkPower(prpDuserDto.getUserCode(),taskCode,checkCode,"0501");
              					if (checkPower) {
              						buffer.append("<option value='");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDuserDto.getUserName());
              						buffer.append("'>");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("-");
              						buffer.append(prpDuserDto.getUserName());
              					}
              				}
              			} else if (codetype.equals("WoundHanderCode")) //50.人伤定损人员信息
              			{
              				String checkCode = "";//校验代码
              				String taskCode = "";//得到任务代码
              				boolean checkPower = false; //是否通过校验
              				checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
              				taskCode = "lprd";

              				conditions = "";

              				//查询本机构下的所有人员prpduser表
              				conditions = "  ValidStatus='1' " + " AND comCode in ( select ComCode from prpdCompany Start With ComCode = '" + other + "' Connect By Prior comCode =  uppercomCode" + " and prior comcode != comcode and validstatus='1')";

              				if (!codevalue.trim().equals("")) {
              					if (inputtype.equals("code")) {
              						conditions = conditions + " and UserCode like '%" + codevalue + "%'";
              					} else {
              						conditions = conditions + " and UserName like '%" + codevalue + "%'";
              					}
              				}
              				//System.out.println(conditions);
              				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//对查询出的人员进行权限校验
              					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
              					//add by zhaolu 20060815 start
              					com.sinosoft.platform.dto.domain.PrpDuserDto platformPrpDuserDto = new com.sinosoft.platform.dto.domain.PrpDuserDto();
              					platformPrpDuserDto.setLoginComCode(prpDuserDto.getComCode());
              					platformPrpDuserDto.setUserCode(prpDuserDto.getUserCode());
              					checkPower = UIPowerAction.checkPowerReturn(platformPrpDuserDto, Constants.TASK_CLAIM_CHECK);
              					//add by zhaolu 20060815 end
              					// checkPower=uiPowerInterface.checkPower(prpDuserDto.getUserCode(),taskCode,checkCode,"0501");
              					if (checkPower) {
              						buffer.append("<option value='");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDuserDto.getUserName());
              						buffer.append("'>");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("-");
              						buffer.append(prpDuserDto.getUserName());
              					}
              				}
              			} else if (codetype.equals("PropHanderCode")) //50.人伤定损人员信息
              			{
              				String checkCode = "";//校验代码
              				String taskCode = "";//得到任务代码
              				boolean checkPower = false; //是否通过校验
              				checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
              				taskCode = "lpcd";

              				conditions = "";

              				//查询本机构下的所有人员prpduser表
              				conditions = "  ValidStatus='1' " + " AND comCode in ( select ComCode from prpdCompany Start With ComCode = '" + other + "' Connect By Prior comCode =  uppercomCode" + " and prior comcode != comcode and validstatus='1')";

              				if (!codevalue.trim().equals("")) {
              					if (inputtype.equals("code")) {
              						conditions = conditions + " and UserCode like '%" + codevalue + "%'";
              					} else {
              						conditions = conditions + " and UserName like '%" + codevalue + "%'";
              					}
              				}
              				//System.out.println(conditions);
              				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//对查询出的人员进行权限校验
              					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
              					//add by zhaolu 20060815 start
              					com.sinosoft.platform.dto.domain.PrpDuserDto platformPrpDuserDto = new com.sinosoft.platform.dto.domain.PrpDuserDto();
              					platformPrpDuserDto.setLoginComCode(prpDuserDto.getComCode());
              					platformPrpDuserDto.setUserCode(prpDuserDto.getUserCode());
              					checkPower = UIPowerAction.checkPowerReturn(platformPrpDuserDto, Constants.TASK_CLAIM_CHECK);
              					//add by zhaolu 20060815 end

              					//checkPower=uiPowerInterface.checkPower(prpDuserDto.getUserCode(),taskCode,checkCode,"0501");
              					if (checkPower) {
              						buffer.append("<option value='");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDuserDto.getUserName());
              						buffer.append("'>");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("-");
              						buffer.append(prpDuserDto.getUserName());
              					}
              				}
              			}
              			//添加向外询价人员信息
              			else if (codetype.equals("VerifyPricePerson")) //28.向外询价人员信息
              			{
              				String checkCode = "";//校验代码
              				String taskCode = "";//得到任务代码
              				boolean checkPower = false; //是否通过校验
              				checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
              				taskCode = AppConfig.get("sysconst.TASKCODE_LPHJ");

              				conditions = "";

              				//查询本机构下的所有人员prpduser表
              				//  conditions = "  ValidStatus='1' "
              				//               + " AND comCode in ( select ComCode from prpdCompany Start With ComCode = '" + other
              				//             + "' Connect By Prior comCode =  uppercomCode"
              				//           + " and prior comcode != comcode and validstatus='1')";

              				conditions = "  ValidStatus='1' " + " AND comCode in ( select ComCode from prpdCompany where ComCode like '" + other.substring(0, 3) + "%' and validstatus='1')";

              				if (!codevalue.trim().equals("")) {
              					if (inputtype.equals("code")) {
              						conditions = conditions + " and UserCode like '%" + codevalue + "%'";
              					} else {
              						conditions = conditions + " and UserName like '%" + codevalue + "%'";
              					}
              				}

              				collection = uiCodeAction.findByConditions(UICodeAction.HANDERCODE, conditions, 0, 0);
              				recordSize = collection.size();
              				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              					//对查询出的人员进行权限校验
              					PrpDuserDto prpDuserDto = (PrpDuserDto) iterator.next();
              					//add by zhaolu 20060815 start
              					com.sinosoft.platform.dto.domain.PrpDuserDto platformPrpDuserDto = new com.sinosoft.platform.dto.domain.PrpDuserDto();
              					platformPrpDuserDto.setLoginComCode(prpDuserDto.getComCode());
              					platformPrpDuserDto.setUserCode(prpDuserDto.getUserCode());
              					checkPower = UIPowerAction.checkPowerReturn(platformPrpDuserDto, Constants.TASK_CLAIM_CHECK);
              					//add by zhaolu 20060815 end
              					//checkPower=uiPowerInterface.checkPower(prpDuserDto.getUserCode(),taskCode,checkCode,"0501");
              					if (checkPower) {
              						buffer.append("<option value='");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(prpDuserDto.getUserName());
              						buffer.append("'>");
              						buffer.append(prpDuserDto.getUserCode());
              						buffer.append("-");
              						buffer.append(prpDuserDto.getUserName());
              					}
              				}
              			} else if (codetype.equals("IdentifyType")) {
              				uiCodeAction = new UICodeAction();
              				conditions = "1=1";
              				conditions = conditions + " and codeType ='" + codetype + "' ";
              				collection = uiCodeAction.findByConditions("IdentifyType", conditions, 0, 0);
              				recordSize = collection.size();
              				if (collection != null && collection.size() > 0) {
              					Iterator iterator = collection.iterator();
              					while (iterator.hasNext()) {
              						PrpDcodeDto codeDto = (PrpDcodeDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(codeDto.getCodeCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(codeDto.getCodeCName());
              						buffer.append("'>");
              						buffer.append(codeDto.getCodeCode());
              						buffer.append("-");
              						buffer.append(codeDto.getCodeCName());
              					}
              				}
              			}
              			//对代码录入进行调整，将对prpdcode的查存统一放入else中进行处理
              			else {
              				uiCodeAction = new UICodeAction();
              				conditions = "1=1";
              				conditions = conditions + " and codeType ='" + codetype + "' ";
              				collection = uiCodeAction.findByConditions(null, conditions, 0, 0);
              				recordSize = collection.size();
              				if (collection != null && collection.size() > 0) {
              					Iterator iterator = collection.iterator();
              					while (iterator.hasNext()) {
              						PrpDcodeDto codeDto = (PrpDcodeDto) iterator.next();
              						buffer.append("<option value='");
              						buffer.append(codeDto.getCodeCode());
              						buffer.append("_FIELD_SEPARATOR_");
              						buffer.append(codeDto.getCodeCName());
              						buffer.append("'>");
              						buffer.append(codeDto.getCodeCode());
              						buffer.append("-");
              						buffer.append(codeDto.getCodeCName());
              					}
              				}
              			}
              		} catch (Exception exception) {
              			exception.printStackTrace();
              			errorMessage = "查询失败";
              		}
              		queryResult = buffer.toString();
              	}
              %>
<html>
<head>
<title>代码查询</title>
<app:css />
<script language="Javascript" src="/claim/common/js/Common.js"></script>
<script language="Javascript" src="/claim/common/js/InputCode.js"></script>
<script language="Javascript" src="/claim/common/js/PrivateInputCode.js"></script>
<script language='javascript'>
	var fmInput;
	var fraInterface;
	try {
		//fraInterface= parent.fraMenu.fraInterface;
		//fmInput =fraInterface.fm;
		fmInput = parent.fraInterface.fm;

	} catch (E) {
	}
</script>
</head>
<body class="interface">
	<%--查询结束後，将查询结果放在input域传回到客户端，並且重画页面，使其恢复到初始状态--%>
	<form name='fm' action="/claim/common/pub/InputCode.jsp" method='POST'>
		<input type="hidden" name=codeindex value='<%=codeindex%>'>
		<input type="hidden" name=codevalue value='<%=codevalue%>'>
		<input type="hidden" name=codetype value='<%=codetype%>'>
		<input type="hidden" name=relation value='<%=relation%>'>
		<input type="hidden" name=inputtype value='<%=inputtype%>'>
		<input type="hidden" name=querytype value='<%=querytype%>'>
		<input type="hidden" name=limit value='<%=limit%>'>
		<input type="hidden" name=method value='<%=method%>'>
		<input type="hidden" name=fieldsign value='<%=fieldsign%>'>
		<input type="hidden" name=other value='<%=other%>'>
		<input type="hidden" name=riskcode value='<%=strRiskCode%>'>
		<input type="hidden" name="provinceCode" value='<%=strProvinceCode%>'>
		<input type="hidden" name=policyno value='<%=strPolicyNo%>'>
		<%
			try {
				if (submitMethod.equals("POST")) {//如果是POST则处理
					out.println("<script language='javascript'>            ");
					if (!errorMessage.equals("")) {//查询代码失败
						out.println("    //fm.elements[" + codeindex + "].focus();");
						out.println("    window.status='查询代码失败\t';");
						out.println("    parent.fraInterface.setInquery(false);");
						out.println("    parent.fraInterface.setQueryError(true);");
					} else if (method.equals("change") && recordSize > 0) {//查询代码成功且method为change
						queryResult = queryResult.substring(queryResult.indexOf("<option value='") + ("<option value='").length(), queryResult.indexOf("'>"));
						arrCodeName = StringUtils.split(queryResult, "_FIELD_SEPARATOR_");
						out.println("    window.status='查询代码<" + codevalue + ">成功';");
						out.println("    if(parent.fraInterface.getSign()==fm.fieldsign.value){");
						if (inputtype.equals("name")) {
							arrRelation = StringUtils.split("0," + relation, ",");
							arrRelation[0] = arrRelation[1];
							arrRelation[1] = "0";
							for (i = 0; i < arrRelation.length; i++) {
								index = Integer.parseInt(arrRelation[i]) + Integer.parseInt(DataUtils.nullToZero(codeindex));
								if (querytype.equals("need") && i != 0) {
									out.println("        if(isEmpty(fmInput.elements[" + index + "])){");
								}
								if (i > arrCodeName.length - 1) {
									out.println("            fmInput.elements[" + index + "].value = '" + arrCodeName[arrCodeName.length - 1] + "';");
									out.println("            fmInput.elements[" + index + "].tag = '" + arrCodeName[arrCodeName.length - 1] + "';");
								} else {
									out.println("            fmInput.elements[" + index + "].value = '" + arrCodeName[i] + "';");
									out.println("            fmInput.elements[" + index + "].tag = '" + arrCodeName[i] + "';");
								}
								out.println("            try");
								out.println("            {");
								out.println("                if(fmInput.elements[" + index + "].onchange!=null){");
								out.println("                    fmInput.elements[" + index + "].onchange();");
								out.println("                }");
								out.println("            }catch(Ex1){");
								out.println("            }");
								if (querytype.equals("need") && i != 0) {
									out.println("        }");
								}
							}
						} else { //inputtype is 
							arrRelation = StringUtils.split("0," + relation, ",");
							for (i = 0; i < arrRelation.length; i++) {
								index = Integer.parseInt(arrRelation[i]) + Integer.parseInt(DataUtils.nullToZero(codeindex));
								if (querytype.equals("need") && i != 0) {
									out.println("        if(isEmpty(fmInput.elements[" + index + "])){");
								}
								if (i > arrCodeName.length - 1) {
									out.println("            fmInput.elements[" + index + "].value = '" + arrCodeName[arrCodeName.length - 1] + "';");
									out.println("            fmInput.elements[" + index + "].tag = '" + arrCodeName[arrCodeName.length - 1] + "';");
								} else {
									out.println("            fmInput.elements[" + index + "].value = '" + arrCodeName[i] + "';");
									out.println("            fmInput.elements[" + index + "].tag = '" + arrCodeName[i] + "';");
								}
								out.println("            try");
								out.println("            {");
								out.println("                if(fmInput.elements[" + index + "].onchange!=null){");
								out.println("                    fmInput.elements[" + index + "].onchange();");
								out.println("                }");
								out.println("            }catch(Ex1){");
								out.println("            }");
								if (querytype.equals("need") && i != 0) {
									out.println("        }");
								}
							}
						}

						out.println("    }");
						out.println("    parent.fraInterface.setInquery(false);");
						out.println("    parent.fraInterface.setQueryError(false);");
					} else if (recordSize == 0) {//没有查到符合条件的数据
						arrRelation = StringUtils.split("0," + relation, ",");
						if (limit.equals("must") && !codevalue.equals("")) {
							out.println("    if(parent.fraInterface.getSign() == fm.fieldsign.value){");
							out.println("        fmInput.elements[" + codeindex + "].focus();");
							out.println("        window.status='代码<" + codevalue + ">不存在，请重新输入';");
							out.println("    }");
						} else {
							if (limit.equals("clear") || (limit.equals("must") && codevalue.equals(""))) {
								out.println("    window.status='代码<" + codevalue + ">不存在，清空相关欄位';");
								out.println("    if(parent.fraInterface.getSign() == fm.fieldsign.value){");

								for (i = 0; i < arrRelation.length; i++) {
									index = Integer.parseInt(arrRelation[i]) + Integer.parseInt(DataUtils.nullToZero(codeindex));

									out.println("        fmInput.elements[" + index + "].value = '';");
									out.println("        fmInput.elements[" + index + "].oldvalue = '';");
								}
								out.println("    }");
							} else if (limit.equals("none")) {
								out.println("    window.status='代码<" + codevalue + ">不存在';");
							}
						}
						out.println("    parent.fraInterface.setInquery(false);");
						out.println("    parent.fraInterface.setQueryError(true);");
					}

					out.println("</script>");
					if (errorMessage.equals("") && method.equals("select") && recordSize > 0) { //method为select且查询出结果
		%>
		<table class="common" style="width: 100%" align="center">
			<tr>
				<td style="width: 30%"></td>
				<td style="width: 20%" align="center">
					<%
						// 确定按钮
					%>
					<input type=button name=SelectIt class="button" value="<s:text name='button.save.value' />" onclick='setFieldValue()'></input>
				</td>
				<td style="width: 20%" align="center">
					<input type=button name=CancelIt class="button" value="<s:text name='button.cancel.value' />" onclick='cancelFieldValue()'></input>
				</td>
				<td style="width: 30%"></td>
			</tr>
			<tr>
				<td colspan=4 align="center" style="width: 100%">
					<%
						if (codetype.equals("ItemCode1") && (codevalue.equals("*") || codevalue.length() <= 4) || (codetype.equals("ItemCode2") && (codevalue.equals("*") || codevalue.length() <= 4))) {
					%>
					<select name=codeselect class="code" style='width: 320px' size=8 onkeypress=fieldOnKeyPress() ondblclick="continueQueryItemCode()">
						<%=buffer.toString()%>
					</select>
					<%
						} else {
					%>
					<select name=codeselect class="code" style='width: 320px' size=8 onkeypress=fieldOnKeyPress() ondblclick="setFieldValue()">
						<%=buffer.toString()%>
					</select>
					<%
						}
					%>
				</td>
			</tr>
		</table>
		<script language='javascript'>
          window.status='查询代码<<%=codevalue%>>成功';
          parent.fraRight.rows = "50%,0%,*";
          fm.codeselect.options.selectedIndex=0;
          fm.codeselect.focus();

          function continueQueryItemCode()
          {

             fm.codevalue.value = fm.codeselect.options[fm.codeselect.options.selectedIndex].value + "*";
             arrCodeValue = fm.codevalue.value.split(FIELD_SEPARATOR);
             fm.codevalue.value = arrCodeValue[0] + "*";
             fm.submit();
          }
        </script>
		<%
			}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		%>
	</form>
</body>
</html>
