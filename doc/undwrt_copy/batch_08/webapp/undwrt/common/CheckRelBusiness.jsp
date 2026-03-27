<%--
****************************************************************************
* DESC       ：批量下发关联单校验页面
* Author     : 项目组
* CREATEDATE ：2009-09-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*
****************************************************************************
--%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%> 
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.BLPrpCmainSub"%>
<%@page import="com.sinosoft.prpall.schema.*"%>

<%@page contentType="text/xml;charset=GBK"%>
<%
  String strBusinessNo = request.getParameter("BusinessNo");
  String strBusinessType = request.getParameter("BusinessType");
  String strWhere = "";
  String strReturn = "";
  String xmlText = "";
  if(strBusinessType.equals("T"))
  {
    strWhere = "mainpolicyno = '" + strBusinessNo + "'";
    BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
    blPrpTmainSub.query(strWhere);
    if(blPrpTmainSub.getSize()>0&&"111".equals(blPrpTmainSub.getArr(0).getFlag()))
    {
      strReturn = blPrpTmainSub.getArr(0).getProposalNo();;
    }
    else
    	{
    	  strReturn = "";
    	}
  }else if(strBusinessType.equals("P"))
  {
   
  }
  else if(strBusinessType.equals("E"))
  {
  }
  System.out.println("strReturn==="+strReturn);
  xmlText = "<?xml version=\"1.0\" encoding=\"GBK\"?>" //字符编码GBK
  				+	"<root>"
  				+ strReturn
  				+ "</root>";
	out.print(xmlText);  
  
%>