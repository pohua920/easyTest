<%--
****************************************************************************
* DESC       ：机动车辆保险结案催告、注销通知书打印初始化
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
 


<%
 	//变量声明部分
 	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
 	String strPolicyNo = ""; //保单号
 	String strInsuredDate = ""; //保险期间
 	String strDamageStartDate = ""; //出险时间
 	String strInsureCarFlag = ""; //是否为本保单车辆
 	double dblSumAmount1 = 0; //保险金额    
 	double dblSumAmount2 = 0; //责任限额    
 	String strRiskCode = ""; //险种代码     
 	String strKindCode = ""; //险别代码     
 	String strCalculateFlag = ""; //是否记入保额
 	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode();
 	String strMessage = "";
 	String strCancelReason = ""; //拒赔注销String strCancelReason    = "";   //拒赔注销原因
 	String strTextType = "";
 	String strInsuredAddress = "";

 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志
 	String strComCode = "";
 	String strCompanyAdd = "";
 	String strPostCode = "";
 	PrpDcompanyDto prpDcompanyDto = null;
 	UICompanyAction uiCompanyAction = new UICompanyAction();

 	//对象定义部分                                             
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
 	PrpLregistDto prpLregistDto = null; //RegistDto对象
 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
 	PrpLltextDto prpLltextDto = null; //PrpLltextDto对象

 	int intClaimCount = 0; //ClaimDto对象的记录数
 	int intItemKindCount = 0; //ItemKindDto对象的记录数
 	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
 	int intLtextCount = 0; //PrpLltextDto对象的记录数
 	int intLtextCountTmp = 0;
 	int index = 0;

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();

 	//得到保单号  
 	strPolicyNo = prpLclaimDto.getPolicyNo();

 	strComCode = prpLclaimDto.getComCode();
 	prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);
 	if (prpDcompanyDto != null) {
 		strCompanyAdd = prpDcompanyDto.getAddressCName();
 		strPostCode = prpDcompanyDto.getPostCode();
 	}

 	//得到blPrpCitemKind对象的记录数
 	if (policyDto.getPrpCitemKindDtoList() != null) {
 		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
 	}

 	//得到blPrpLthirdParty对象的记录数
 	if (claimDto.getPrpLthirdPartyDtoList() != null) {
 		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
 	}

 	//得到PrpLltext对象的记录数
 	if (claimDto.getPrpLltextDtoList() != null) {
 		intLtextCount = claimDto.getPrpLltextDtoList().size();
 	}

 	//得到prpLregistDto对象
 	prpLregistDto = registDto.getPrpLregistDto();
 %>

<script language="javascript">
  function loadForm()
  {
    tdClaimNo.innerHTML = '赔案号：' + '<%=strClaimNo%>';
    
    //*****报案信息表PrpLregist*****
<%double douEstimateLoss = prpLregistDto.getEstimateLoss();
			if (douEstimateLoss == 0) {%>    
    //tdEstimateLoss.innerHTML = '无';
<%} else {%>    
    //tdEstimateLoss.innerHTML = '<%=new DecimalFormat("#,##0.00").format(prpLregistDto.getEstimateLoss())%>' + ' 元';
<%}
			//System.out.println("--------------"+prpLclaimDto.getPolicyNo());%>    
    //*****立案信息表PrpLregist*****
    tdPolicyNo.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getPolicyNo())%>';
    tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
    //tdInsuredName1.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>' + '（被保险人名称）：';
<%strInsuredDate = "自" + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日" + prpLclaimDto.getStartHour() + "时起"

			+ "至" + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日" + prpLclaimDto.getEndHour() + "时止";
			//拒赔注销原因
			if (claimDto.getPrpLltextDtoList() != null) {
				for (index = 0; index < intLtextCount; index++) {
					prpLltextDto = (PrpLltextDto) claimDto.getPrpLltextDtoList().get(index);
					strTextType = StringConvert.encode(prpLltextDto.getTextType());
					if (strTextType != null && strTextType.equals("10")) {
						strCancelReason = strCancelReason + StringConvert.encode(prpLltextDto.getContext());
					}
				}
			}

			intLtextCountTmp = 0; //text的行数

			for (index = 0; index < strCancelReason.length(); index++) {
				if (strCancelReason.substring(index, index + 1).equals("\\")) {
					if (!(strCancelReason.substring(index).length() < 4)) {
						if (strCancelReason.substring(index, index + 4).equals("\\r\\n")) {
							intLtextCountTmp++; //只要有回车换行，intLtextCountTmp+1
						}
					}
				}
			}

			int x = 0;
			int y = 0;
			if (!(strCancelReason.length() < 4)) //如果strCancelReason.length()>=4，判断strCancelReason结尾是文字，还是回车换行
			{
				x = strCancelReason.length() - 4;
				y = strCancelReason.length();
				if (!strCancelReason.substring(x, y).equals("\\r\\n")) {
					intLtextCountTmp += 1;
				}
			} else
				//如果strCancelReason不足1行，intLtextCountTmp = 1; 
				intLtextCountTmp = 1;

			/*if( intLtextCountTmp>20 )
			{      
			   strCancelReason = "<br>";        
			}*/
			while (strCancelReason.indexOf("\\r\\n") != -1) {
				strCancelReason = strCancelReason.substring(0, strCancelReason.indexOf("\\r\\n")) + "<br>" + strCancelReason.substring(strCancelReason.indexOf("\\r\\n") + "\\r\\n".length());
			}
			%>                    
    tdInsuredDate.innerHTML = '<%=strInsuredDate%>';
    tdRegistNo.innerHTML = '报案号：'+'<%=StringConvert.encode(prpLclaimDto.getRegistNo())%>';
    //tdCaseNo.innerHTML = '归档号'+'<%=StringConvert.encode(prpLclaimDto.getCaseNo())%>';
    tdCancelReason.innerHTML = '註銷拒赔原因：<br>&nbsp;&nbsp;&nbsp;&nbsp;'+'<%=StringConvert.encode(strCancelReason)%>';
    tdDamageCause.innerHTML = '出险原因：<br>&nbsp;&nbsp;&nbsp;&nbsp;'+'<%=StringConvert.encode(prpLclaimDto.getDamageName())%>';
<%//出险险种
			strCode = "";
			strName = "";
			strCode = StringConvert.encode(prpLclaimDto.getRiskCode());
			strName = uiCodeAction.translateRiskCode(strCode, isChinese);%>  
    //tdRiskName.innerHTML = '<%=strName%>';
    tdRiskNameAndEstimateLoss.innerHTML = '<%=strName + "/" + new DecimalFormat("#,##0.00").format(prpLregistDto.getEstimateLoss()) + "元"%>';
<%strDamageStartDate = "<u>&nbsp;" + prpLclaimDto.getDamageStartDate().getYear() + "&nbsp;</u>" + "年" + "<u>&nbsp;" + prpLclaimDto.getDamageStartDate().getMonth() + "&nbsp;</u>" + "月" + "<u>&nbsp;" + prpLclaimDto.getDamageStartDate().getDate() + "&nbsp;</u>" + "日" + "<u>&nbsp;" + prpLclaimDto.getDamageStartHour().substring(0, 2) + "&nbsp;</u>" + "时";%>                    
    tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
    //tdDamageAddress.innerHTML   = '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';
    
    //*****理赔车辆信息PrpLthirdParty*****
<%if (claimDto.getPrpLthirdPartyDtoList() != null) {
				for (index = 0; index < intThirdPartyCount; index++) {
					prpLthirdPartyDto = (PrpLthirdPartyDto) claimDto.getPrpLthirdPartyDtoList().get(index);
					strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();

					if (strInsureCarFlag.equals("1")) {%>        
        tdLicenseNo.innerHTML = '<%=prpLthirdPartyDto.getLicenseNo()%>';
        //tdBrandName.innerHTML = '<%=prpLthirdPartyDto.getBrandName()%>';
<%}
				}
			}

			//*****标的子险信息PrpTitemKind*****
			dblSumAmount1 = 0;
			dblSumAmount2 = 0;

			if (policyDto.getPrpCitemKindDtoList() != null) {
				for (index = 0; index < intItemKindCount; index++) {
					prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
					strCalculateFlag = prpItemKindDto.getCalculateFlag();
					if (strCalculateFlag.equals("Y")) {
						dblSumAmount1 += prpItemKindDto.getAmount();
					}
					if (strCalculateFlag.equals("N")) {
						dblSumAmount2 += prpItemKindDto.getAmount();
					}
				}
			}
			if (strCancelReason.length() < 63) {
				boolean flag = true;
				int length = strCancelReason.length() + 1;
				for (int i = 63; i >= length; length++) {
					if (flag) {
						strCancelReason = "&nbsp;" + strCancelReason;
					} else {
						strCancelReason += "&nbsp;";
					}
					flag = !flag;
				}
			}

			//System.out.println() ;
			//System.out.println(" --------------------> " + strCancelReason.length()) ;
			//System.out.println() ;

			strInsuredAddress = registDto.getPrpLregistDto().getInsuredAddress();%>
    //tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount1)%>';
    //tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';    
  }
</script>

