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
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@page import="java.util.Date" %>
<%@page import="com.sinosoft.sysframework.common.util.StringUtils" %>

<%
  //reportHtml为保单抄件正文
  String reportHtml=(String)request.getParameter("ta1" );
  //remark为保单抄件备注意见
  String remark = (String)request.getParameter("MainRemark" );
  String strPrintType = request.getParameter("PrintType");
  
  //将编辑按钮删除
	int editLocation = reportHtml.indexOf("<TD class=button style=\"WIDTH: 20%\" align=middle><INPUT class=button onclick=edit() type=button alt=编辑 value=编辑 name=edit> </TD>");
	if(editLocation>0)
  	reportHtml = reportHtml.substring(0,editLocation) + reportHtml.substring(editLocation+129);
  
  reportHtml = reportHtml.replaceAll("alt=编辑 value=编辑","alt=列印 value=列印").replaceAll("onclick=edit","onclick=printPage");
  reportHtml = reportHtml.replaceAll("divButton","divButtonX");
  	
  
  //保存前页面展示
  //out.print(reportHtml);
  //out.print(remark);

  
  //bizNo不从session中获取
  String bizNo = request.getParameter("BizNo");
  //out.print(bizNo);
  //businessType 0为投保单，1为保单
  //printType 0为保单抄件正本，1为保单抄件编辑备注意见
  String conditions = "businessno = '" + bizNo + "' and printType="+strPrintType;
  //System.out.println("delete conditions is " + conditions);
  DateTime today = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
  
  //删除此投保单号相关的所有数据，包括保单抄件正本数据和备注意见数据
  UtiPrintPageDeleteByConditionsCommand command3 = new UtiPrintPageDeleteByConditionsCommand(conditions);
  try {
    command3.execute();

  } catch (Exception e) {
		e.printStackTrace();
  }
  
  //拆分後存放保单抄件正本的数组
  String[] arrprintText = {};
  //拆分後存放保单抄件编辑备注意见的数组
  String[] arrRemark = {};
  
  //拆分内容到数组
  arrprintText = StringUtils.split(reportHtml,1000);
  arrRemark = StringUtils.split(remark,1000);
 
  //将拆分後的保单抄件正本循环插入数据库
  for(int i=0;i<arrprintText.length;i++){
  		UtiPrintPageDto utiPrintPageDto  = new UtiPrintPageDto();
  		utiPrintPageDto.setBusinessNo(bizNo);
  		utiPrintPageDto.setSeaialNo(i);
  		utiPrintPageDto.setBusinessType("3");
  		System.err.println("9999999999999999999999999999=="+strPrintType);
  		utiPrintPageDto.setPrintType(strPrintType);
  		utiPrintPageDto.setOperatorCode((String)session.getValue("UserCode"));
  		utiPrintPageDto.setOperatorName((String)session.getValue("UserName"));
  		utiPrintPageDto.setOperateTime(today.toString());
  		utiPrintPageDto.setPrintContext(arrprintText[i]);

  		//System.out.println("bizno is " + i + " aaaaaa " +  utiPrintPageDto.getBusinessNo());
  		//System.out.println("content is " + i + " aaaaaa " +  utiPrintPageDto.getPrintContext());
  		UtiPrintPageInsertCommand command = new UtiPrintPageInsertCommand(utiPrintPageDto);
			try {
				command.execute();

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
  //将拆分後的保单抄件编辑备注意见循环插入数据库
	  for(int i=0;i<arrRemark.length;i++){
  		UtiPrintPageDto utiPrintPageDto  = new UtiPrintPageDto();
  		utiPrintPageDto.setBusinessNo(bizNo);
  		utiPrintPageDto.setSeaialNo(i);
  		utiPrintPageDto.setBusinessType("3");
  		utiPrintPageDto.setPrintType(strPrintType);
  		utiPrintPageDto.setOperatorCode((String)session.getValue("UserCode"));
  		utiPrintPageDto.setOperatorName((String)session.getValue("UserName"));
  		utiPrintPageDto.setOperateTime(today.toString());
  		utiPrintPageDto.setPrintContext(arrRemark[i]);

  		//System.out.println("bizno is " + i + " aaaaaa " +  utiPrintPageDto.getBusinessNo());
  		//System.out.println("content is " + i + " aaaaaa " +  utiPrintPageDto.getPrintContext());
  		UtiPrintPageInsertCommand command = new UtiPrintPageInsertCommand(utiPrintPageDto);
			try {
				command.execute();

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	//保存後页面展示
/*
	UtiPrintPageDto utiPrintPageDto2  = new UtiPrintPageDto();
	UtiPrintPageFindByConditionsCommand command2 = new UtiPrintPageFindByConditionsCommand("PolicyNo = '" + bizNo + "' order by printno");
			try {
				ArrayList t = (ArrayList)command2.execute();
				for(int i=0;i<t.size();i++){
					utiPrintPageDto2 = (UtiPrintPageDto)t.get(i);
					out.print(utiPrintPageDto2.getPrintPage());
				}
				

			} catch (Exception e) {
				throw e;
			}
	*/

%>
<script>
window.close();
</script>

