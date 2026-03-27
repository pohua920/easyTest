<%--
****************************************************************************
* DESC       ：机动车辆保险索赔须知
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-06
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 引入bean类部分 --%>
<%@page import="jajava.text.*>
<%@page import="jajava.util.*>
<%@page import="cocom.sinosoft.claim.ui.control.action.*>
<%@page import="cocom.sinosoft.claim.dto.custom.*>
<%@page import="cocom.sinosoft.claim.dto.domain.*>
<%@page import="cocom.sinosoft.claim.util.*>
<%@page import="cocom.sinosoft.sysframework.common.util.*>
<%@page import="cocom.sinosoft.sysframework.common.datatype.DateTime>
<%@page import="cocom.sinosoft.sysframework.exceptionlog.*>
<%
	///变量声明部分
			String strRegistNo = request.getParameter("RegistNo"); //报案号
			String strPolicyNo = ""; //保单号
			String strInsuredName = ""; //被保险人名称

			int index = 0; //循环变量
			int intSize = 0; //ArrayList的大小
			int inttemp = 0; //每项的材料数
			String[] strTypeCode; //材料类型
			String[] strTypeName; //材料名称

			HashMap certifyMessage = new HashMap(); //用於查询单证

			//对象定义部分                                             
			PrpLregistDto prpLregistDto = null; //RegistDto对象

			CertifyDto certifyDto = null;//CertifyDto对象
			UICertifyAction uiCertifyAction = new UICertifyAction();
			ArrayList arrPrpLcertifyDirectDto = null;

			//得到RegistDto对象
			RegistDto registDto = (RegistDto) request.getAttribute("registDto");

			//得到CertifyDto对象
			certifyDto = uiCertifyAction.findByPrimaryKey(strRegistNo);

			//得到prpLregistDto对象
			prpLregistDto = registDto.getPrpLregistDto();
			strPolicyNo = prpLregistDto.getPolicyNo();
			strInsuredName = prpLregistDto.getInsuredName();

			if (certifyDto != null) {
				arrPrpLcertifyDirectDto = certifyDto.getPrpLcertifyDirectDtoList();
				if (arrPrpLcertifyDirectDto != null) {
					intSize = arrPrpLcertifyDirectDto.size();
				}
			}
			if (intSize > 0) {
				strTypeCode = new String[intSize];
				strTypeName = new String[intSize];
				for (index = 0; index < intSize; index++) {
					PrpLcertifyDirectDto prpLcertifyDirectDto = (PrpLcertifyDirectDto) arrPrpLcertifyDirectDto.get(index);
					strTypeCode[index] = prpLcertifyDirectDto.getTypeCode();
					strTypeName[index] = StringConvert.encode(prpLcertifyDirectDto.getTypeName());
					certifyMessage.put(strTypeCode[index], strTypeName[index]);
				}
			} else {
				strTypeCode = null;
				strTypeName = null;
			}