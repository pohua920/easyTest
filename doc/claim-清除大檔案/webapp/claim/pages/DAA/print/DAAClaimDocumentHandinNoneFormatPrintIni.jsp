<%--
****************************************************************************
* DESC       ：机动车辆保险索赔材料交接单
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@page import="java.util.ArrayList"%>

<%
	//变量声明部分
	String strRegistNo = request.getParameter("RegistNo"); //报案号
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人名称

	//add by coapeng start at 2005.12.2
	int index = 0; //循环变量
	int intSize = 0; //ArrayList的大小
	int inttemp = 0; //每项的材料数
	String[] strTypeCode; //材料类型
	String[] strTypeName; //材料名称
	String strCollectFlag = "";
	//add by caopeng end at 2005.12.2 

	//对象定义部分                                             
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	//add by coapeng start at 2005.12.2
	CertifyDto certifyDto = null;//CertifyDto对象
	UICertifyAction uiCertifyAction = new UICertifyAction();
	ArrayList arrPrpLcertifyDirectDto = null;
	PrpLcertifyCollectDto prpLcertifyCollectDto = null;
	//add by caopeng end at 2005.12.2

	//得到RegistDto对象
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	//add by coapeng start at 2005.12.2
	//得到CertifyDto对象
	certifyDto = uiCertifyAction.findByPrimaryKey(strRegistNo);
	//add by caopeng end at 2005.12.2

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();
	strPolicyNo = prpLregistDto.getPolicyNo();
	strInsuredName = prpLregistDto.getInsuredName();

	//add by coapeng start at 2005.12.2
	if (certifyDto != null) {
		arrPrpLcertifyDirectDto = certifyDto.getPrpLcertifyDirectDtoList();
		prpLcertifyCollectDto = certifyDto.getPrpLcertifyCollectDto();
		if (prpLcertifyCollectDto != null) {
			strCollectFlag = prpLcertifyCollectDto.getCollectFlag();
		}
		if (arrPrpLcertifyDirectDto != null) {
			intSize = arrPrpLcertifyDirectDto.size();
		}
	}
	UICodeAction uiCodeAction = new UICodeAction();
	if (intSize > 0) {
		strTypeCode = new String[intSize];
		strTypeName = new String[intSize];
		for (index = 0; index < intSize; index++) {
			PrpLcertifyDirectDto prpLcertifyDirectDto = (PrpLcertifyDirectDto) arrPrpLcertifyDirectDto.get(index);
			strTypeCode[index] = prpLcertifyDirectDto.getTypeCode();
			//strTypeName[index] = StringConvert.encode(prpLcertifyDirectDto.getTypeName());
			strTypeName[index] = uiCodeAction.translateCodeCode("ImageType", strTypeCode[index], true);
			if (strTypeName[index] == null) {
				strTypeName[index] = StringConvert.encode(prpLcertifyDirectDto.getTypeName());
			}
		}
	} else {
		strTypeCode = null;
		strTypeName = null;
	}
	//add by caopeng end at 2005.12.2
%>

