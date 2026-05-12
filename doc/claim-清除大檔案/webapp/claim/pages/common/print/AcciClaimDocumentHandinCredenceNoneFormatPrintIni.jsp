<%--
****************************************************************************
* DESC       ：人身险索赔文件交接凭证初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
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
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人名称
	String strAcciName = ""; //申请人姓名

	int index = 0; //循环变量
	int intSize = 0; //ArrayList的大小
	int inttemp = 0; //材料数
	String[] strTypeCode; //材料类型
	String[] strTypeName; //材料名称

	//对象定义部分                                             
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	CertifyDto certifyDto = null; //CertifyDto对象
	ClaimDto claimDto = null; //ClaimDto对象

	UICertifyAction uiCertifyAction = new UICertifyAction();
	ArrayList arrPrpLcertifyDirectDto = null;
	ArrayList arrPrpLacciPersonDto = null;

	//得到RegistDto对象
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	//得到CertifyDto对象
	UICodeAction uiCodeAction = new UICodeAction();
	strRegistNo = uiCodeAction.translateBusinessCode(strClaimNo, false);
	certifyDto = uiCertifyAction.findByPrimaryKey(strRegistNo);

	//得到prpLregistDto对象
	if (registDto != null) {
		prpLregistDto = registDto.getPrpLregistDto();
	}

	//得到claimDto对象
	claimDto = (ClaimDto) request.getAttribute("claimDto");

	if (prpLregistDto != null) {
		strPolicyNo = prpLregistDto.getPolicyNo();
		strInsuredName = prpLregistDto.getInsuredName();
	}

	if (claimDto != null) {
		arrPrpLacciPersonDto = claimDto.getPrpLacciPersonDtoList();
		if (arrPrpLacciPersonDto != null) {
			intSize = arrPrpLacciPersonDto.size();
			for (index = 0; index < intSize; index++) {
				PrpLacciPersonDto prpLacciPersonDto = (PrpLacciPersonDto) arrPrpLacciPersonDto.get(index);
				if (prpLacciPersonDto.getFlag() != null && prpLacciPersonDto.getFlag().equals("1")) {
					strAcciName = prpLacciPersonDto.getAcciName();

				}
			}
			intSize = 0;
		}

	}

	if (certifyDto != null) {
		arrPrpLcertifyDirectDto = certifyDto.getPrpLcertifyDirectDtoList();
		if (arrPrpLcertifyDirectDto != null) {
			intSize = arrPrpLcertifyDirectDto.size();
			//System.out.println("checkpoint"+intSize);
		}
	}

	if (intSize > 0) {
		strTypeCode = new String[intSize];
		strTypeName = new String[intSize];
		for (index = 0; index < intSize; index++) {
			PrpLcertifyDirectDto prpLcertifyDirectDto = (PrpLcertifyDirectDto) arrPrpLcertifyDirectDto.get(index);
			strTypeCode[index] = prpLcertifyDirectDto.getTypeCode();
			//strTypeName[index] = StringConvert.encode(prpLcertifyDirectDto.getTypeName());
			strTypeName[index] = uiCodeAction.translateCodeCode("ImageType", strTypeCode[index], true);
		}
	} else {
		strTypeCode = null;
		strTypeName = null;
	}
%>



<script language="javascript">

function loadForm() {

	spPolicyNo.innerHTML = '<%=strPolicyNo%>';

	spAcciName.innerHTML = '<%=strAcciName%>'; <%
	for (index = 0; index < 30; index++) {
		if (index < intSize && strTypeCode[index].substring(0, 1).equals("Y") && !strTypeCode[index].equals("Y24")) { %>
				tdTypeName <%= index + 1 %> .innerHTML = '&nbsp;' + '<%=index + 1%>' + '&nbsp;' + '<%=strTypeName[index]%>'; <%
		} else { %>
				tdTypeName <%= index + 1 %> .innerHTML = ''; <%
		}
	} %>
		spIntSize.innerHTML = '<%=intSize%>';

}
</script>