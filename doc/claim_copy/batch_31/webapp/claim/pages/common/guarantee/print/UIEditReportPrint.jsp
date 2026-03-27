<%--
****************************************************************************
* DESC       ：报表修改後保存並显示
* Author     : 国寿项目组
* CREATEDATE ：2007-10-08
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sinosoft.prpall.dto.domain.UtiPrintPageDto"%>
<%@page import="com.sinosoft.prpall.ui.model.*"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.BLPrpCmain"%>
<%@page import="com.sinosoft.prpall.schema.PrpCmainSchema"%>
<%@page import="java.util.ArrayList" %>
<jsp:directive.page import="com.sinosoft.claim.bl.facade.BLPrplguaranteeFacade"/>
<jsp:directive.page import="com.sinosoft.claim.dto.domain.PrplguaranteeDto"/>
<%
  //bizNo is 业务号，根据bizType来区分业务号类型
  String bizNo=(String)request.getParameter("BizNo" );
  //bizType定义传入的业务号，0为投保单号，非0为保单号
  String bizType = (String)request.getParameter("BizType" );
  //editType定义页面是否可以再编辑，空表示不能再编辑只能浏览；非空表示可以再次编辑，需要替换打印按钮为编辑按钮
  String editType = (String)request.getParameter("EditType" );
  //printType定义保存内容的类型，0为保单抄件内容，1为保单抄件意见备注
  String printType = (String)request.getParameter("PrintType" );
  String showOnly = request.getParameter("showOnly");
  
  //缺省为保单抄件内容
  if (printType == null || printType.trim() == "")
  	printType = "0";
  	
  BLPrpCmain blPrpCmain = new BLPrpCmain();
  PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
  String ProposalNo = null;
  String UnderWriteName = null;
  
	UtiPrintPageDto utiPrintPageDto2  = new UtiPrintPageDto();
	System.err.println("BusinessNo = '" + bizNo + "' and printtype = '" + printType + "' order by SeaialNo");
	UtiPrintPageFindByConditionsCommand command3 = null;
	UtiPrintPageFindByConditionsCommand command2 = new UtiPrintPageFindByConditionsCommand("BusinessNo = '" + bizNo + "' and printtype = '" + printType + "' order by SeaialNo");
			try {
				ArrayList t = (ArrayList)command2.execute();
				if(t.size()<1){
				  command3 = new UtiPrintPageFindByConditionsCommand("BusinessNo = 0 and printtype = '" + printType + "' order by SeaialNo");
				  t = (ArrayList)command3.execute();
				}
				StringBuffer buffer = new StringBuffer();
				for(int i=0;i<t.size();i++){
					utiPrintPageDto2 = (UtiPrintPageDto)t.get(i);
					buffer.append(utiPrintPageDto2.getPrintContext());
					//out.print(utiPrintPageDto2.getPrintContext().replaceAll("PolicyNo",bizNo).replaceAll("UnderWriteName",UnderWriteName));
				}
				String pageContent = null;
				if(editType !=null && !editType.trim().equals(""))
					pageContent = buffer.toString().replaceAll("PolicyNo",bizNo).replaceAll("UnderWriteName",UnderWriteName).replaceAll("alt=列印 value=列印","alt=编辑 value=编辑").replaceAll("onclick=printPage","onclick=edit").replaceAll("TDclass","TD class");
				else
					pageContent = buffer.toString().replaceAll("PolicyNo",bizNo).replaceAll("UnderWriteName",UnderWriteName).replaceAll("TDclass","TD class");
					
				out.print(pageContent);
				if(t.size() ==0)
					out.print("没有编辑後的数据！");

			} catch (Exception e) {
				throw e;
			}
			if(showOnly!=null&&!"".equals(showOnly)){
			  out.print("<font color='red'>                     该页面仅供查看，不能作为列印凭证！</font>");
			}
%>
      <jsp:include page="/common/guarantee/print/UIEditPrintButton.jsp">
        <jsp:param name="BizNo" value="<%=bizNo%>"/>
        <jsp:param name="PrintType" value="<%=printType%>"/>
        <jsp:param name="NotEdit" value="print"/>
        <jsp:param name="showOnly" value="<%=showOnly %>"/>
      </jsp:include>


