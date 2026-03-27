<%--
****************************************************************************
* DESC       ：理赔冲减保额的处理
* Author     : 中科软
* CREATEDATE ：2003-09-24
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>
<%-- 引入bean类部分 --%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.utility.string.Date"%>
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="com.sinosoft.prpall.blsvr.lp.*"%>
<%@page import="com.sinosoft.prpall.pubfun.PubTools"%>
<%@page import="com.sinosoft.prpall.blsvr.pg.BLEndorse"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.*"%>
<%@page import="com.sinosoft.prpall.schema.*"%>
<%@page import="com.sinosoft.prpall.pubfun.Bill"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>;
<%@page import="com.sinosoft.claim.dto.domain.PrpDuserDto"%>;
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>;
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/prpall/commonship/pub/UICommon.js"></script>
<body onload="loadForm()" class="interface">
	<form name="fm" method="post" action="/claim/endor.do">
		<input name="EndorType" type="hidden">
		<input type="hidden" name="editType" value="SAVEENDORSE">
		<table class=common align=center style="width: 80%">
			<tr>
				<td class="formtitle">
					<s:text name="endcase.offsetApproval" />
					<!--冲 减 保 额 批 文-->
				</td>
			</tr>
			<tr>
				<td class="input">
					<textarea class=inputtext wrap="hard" name="oldPtext" rows="15" cols="70"></textarea>
				</td>
			</tr>
		</table>
		<table class=common align=center style="width: 80%">
			<tr>
				<td class=button>
					<input type="button" name="buttonSave" value="<s:text name='button.save.value'/>" class="button" onclick="submit();">
					<!--保存-->
				</td>
			</tr>
		</table>
	</form>
</body>
<%
   //定义变量
   String strCompensateNo = request.getParameter("CompensateNo");
   String  strValidDate = new DateTime().current().addDay(1).toString();
   int i = 0;
   int icurr = 0;
   int iEndorsetimes = 0;
   String strItemKindNo = "";
   String strCurrency   = "";
   String strFlag       = "";
   double dblAmount     = 0;
   double dblSumAmount  = 0;
   double dblExchangeRate = 0;
   String strPtext      = "";
   String strPolicyCurrency = "";
   String strEndorType     = "14";
   UserDto   userDto     = (UserDto)session.getAttribute("user");
   String strOperatorCode  = userDto.getUserCode();
   String strMakeCom       = userDto.getComCode();
//System.out.println("strOperatorCode"+strOperatorCode);   
//System.out.println("strMakeCom"+strMakeCom);
   String strInputDate     = new ChgDate().getCurrentTime("yyyy/MM/dd");  
   BLPrpLendor          blPrpLendor = new BLPrpLendor();
   BLPolicy             blPolicy    = new BLPolicy();
   BLPolicy             blPolicyOld = new BLPolicy();
   BLEndorse            blEndorse   = new BLEndorse();
   PrpCitemKindSchema   prpCitemKindSchema = new PrpCitemKindSchema();
   PrpPheadSchema       prpPheadSchema     = new PrpPheadSchema();
   PubTools    pubTools = new PubTools();
   blPrpLendor.getData(strCompensateNo);
   blPolicy.getData(blPrpLendor.getArr(0).getPolicyNo());
   blPolicyOld.getData(blPrpLendor.getArr(0).getPolicyNo());
   strPolicyCurrency = blPolicy.getBLPrpCmain().getArr(0).getCurrency();
   //根据prplendor信息冲减prpcitemkind
   for (i=0;i<blPrpLendor.getSize();i++)
   {
      strItemKindNo = blPrpLendor.getArr(i).getItemKindNo();
      strCurrency   = blPrpLendor.getArr(i).getCurrency();
      icurr = blPolicy.getBLPrpCitemKind().search(strItemKindNo);
      if (icurr > -1)
      {
         prpCitemKindSchema = blPolicy.getBLPrpCitemKind().getArr(icurr);
         if (!prpCitemKindSchema.getCurrency().equals(strCurrency))
         {
           //System.out.println("赔款币别与投保币别不一致,请先换算成保单币别");
           continue;
         }
         //System.out.println("冲减前保额"+prpCitemKindSchema.getAmount());
         dblAmount = Double.parseDouble(prpCitemKindSchema.getAmount())+ 
                     Double.parseDouble(blPrpLendor.getArr(i).getEndorAmount());
         prpCitemKindSchema.setAmount(String.valueOf(dblAmount));
         //System.out.println("冲减後保额"+prpCitemKindSchema.getAmount());
         strFlag = prpCitemKindSchema.getFlag();
         if (strFlag.length()==0)
           {strFlag = "U";}
         else
           {
            strFlag = "U"+strFlag.substring(1,strFlag.length());
           }
         prpCitemKindSchema.setFlag(strFlag);
         blPolicy.getBLPrpCitemKind().setArr(icurr,prpCitemKindSchema);
         //汇总保额
         dblExchangeRate = PubTools.getExchangeRate(prpCitemKindSchema.getCurrency(),
                                                    strPolicyCurrency,
                                                    prpCitemKindSchema.getStartDate());
         dblSumAmount = dblSumAmount + dblAmount*dblExchangeRate;
      }
   }
    //System.out.println("--------冲减保额结束------"); 
   //更新cmain的保额
//System.out.println("冲减前保单保额"+blPolicy.getBLPrpCmain().getArr(0).getSumAmount());
   blPolicy.getBLPrpCmain().getArr(0).setSumAmount(String.valueOf(dblSumAmount));
//System.out.println("冲减後保单保额"+blPolicy.getBLPrpCmain().getArr(0).getSumAmount());
   //生成phead记录
   Bill bill = new Bill();
   String  strBizNo = bill.getNo(SysConfig.getProperty("ENDORSRE_TABLE"),blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
//System.out.println("-----获得批单号-----"+strBizNo);  
   prpPheadSchema.setEndorseNo(strBizNo);
   prpPheadSchema.setMakeCom(strMakeCom);
   prpPheadSchema.setEndorDate(strInputDate);
   prpPheadSchema.setValidDate(strValidDate);
   prpPheadSchema.setValidHour("0");
   prpPheadSchema.setOperatorCode(strOperatorCode);
   prpPheadSchema.setInputDate(strInputDate);
   prpPheadSchema.setInputHour("0");
   prpPheadSchema.setEndorType(strEndorType);
   blEndorse.getBLPrpPhead().setArr(prpPheadSchema);
   
   //根据最新保单记录生成批单记录
   //System.out.println("----生成批单记录开始---");
   blEndorse.evaluateFromPolicyToEndor(blPolicyOld,blPolicy);
   //System.out.println("----生成批单记录结束---");
   //更新cmain,pmain
   //System.out.println("----批单计算开始---");
   blEndorse.webAfterCal(blPolicy); 
   //System.out.println("----批单计算结束---");
   //更新批改生效日期、批改类型
   //默认生效日期为冲减保额日期的第2天
   prpPheadSchema = blEndorse.getBLPrpPhead().getArr(0);
   prpPheadSchema.setCompensateNo(strCompensateNo);
   blEndorse.getBLPrpPhead().initArr();
   blEndorse.getBLPrpPhead().setArr(prpPheadSchema);
   iEndorsetimes = Integer.parseInt(blPolicy.getBLPrpCmain().getArr(0).getEndorseTimes());
   iEndorsetimes = iEndorsetimes + 1;
   blPolicy.getBLPrpCmain().getArr(0).setEndorseTimes(""+iEndorsetimes);
   
   //生成冲减保额批文
   //System.out.println("----生成批文开始-"+blPolicy.getBLPrpCmain().getArr(0).getRiskCode());
   blEndorse.generatePtext(blPolicy);
   //显示批文
   //System.out.println("----生成批文结束---");
   strPtext=blEndorse.getBLPrpPtext().getEndorseText();
   session.putValue("Policy",blPolicy);
   session.putValue("Endorse",blEndorse);
   //System.out.println("over");
%>
<script language=javascript>
  //装载窗口
  function loadForm()
  {
    fm.oldPtext.value = '<%=Str.encode(strPtext)%>';
    fm.EndorType.value = '<%=strEndorType%>';
	}
</script>
