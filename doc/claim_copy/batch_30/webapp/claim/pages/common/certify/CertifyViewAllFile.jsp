
<%--
****************************************************************************
* DESC       ： 单证查看单证图片页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="com.sinosoft.claim.ui.control.action.*" %>
<%@ page import="com.sinosoft.claim.bl.facade.BLClaimFacade" %>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.sysframework.common.util.StringUtils"%>
<%@ page import="com.sinosoft.claim.bl.facade.BLCertifyImgFacade"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.PageRecord" %>
<%@ page import="com.sinosoft.claim.ui.control.action.UIRegistAction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ include file="/common/taglibs.jsp"%>
<%!private boolean isImageFile(String fileName) {
		fileName = fileName.toLowerCase().trim();
		if (fileName.endsWith("jpg") || fileName.endsWith("jpeg") || fileName.endsWith("gif") || fileName.endsWith("bmp")) {
			return true;
		} else {
			return false;
		}
	}%>

<html locale="true">
<head>
  <title><s:text name="title.certifyBeforeEdit.viewDocumentInfo" /></title><%--查看单证信息--%>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script language="javascript">
    function downloadFile(fileURL){
      window.open(fileURL,"downloaFile",'width=10,height=10,top=500,left=380,toolbar=0,location=0,directories=0,menubar=0,scrollbars=0,resizable=0,status=0');
    }
  </script>
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initPage();">
	<form name=fm method="post" action="/claim/common/certify/CertifyViewAllFile.jsp">
		<%
 	String editType = request.getParameter("editType");

 	String directType = request.getParameter("directType");

 	String display = request.getParameter("display");
 	String businessNo = request.getParameter("businessNo");//可能为预赔号或实配号，需要变为报案号
 	String strLossItemName = request.getParameter("itemcode");
 	String pageNo = request.getParameter("pageNo");

 	String rowsPerPage = "20";
 	int curPage = 0;
 	UserDto userDto = (UserDto) session.getAttribute("user");

 	BLCertifyImgFacade blCertifyImgFacade = new BLCertifyImgFacade();
 	java.util.ArrayList prpLCertifyImgList = new java.util.ArrayList();

 	String strFileName = "";
 	String registNo = "";
 	String conditions = " (ClaimNo IN (SELECT ClaimNo FROM PrpLcompensate WHERE CompensateNo = '" + businessNo + "'  union all " + " SELECT ClaimNo FROM PrpLprepay WHERE PreCompensateNo = '" + businessNo + "') )";

 	BLClaimFacade bLClaimFacade = new BLClaimFacade();
 	Collection result = bLClaimFacade.findByConditions(conditions);

 	if (result.size() > 0) {
 		PrpLclaimDto prpLclaimDto = (PrpLclaimDto) result.iterator().next();
 		registNo = prpLclaimDto.getRegistNo();
 	}

 	PageRecord pageRecord = blCertifyImgFacade.findByQueryConditions("businessno='" + registNo.trim() + "' and ValidStatus ='1'  order by typecode", pageNo, rowsPerPage);
 	TurnPageDto turnPageDto = new TurnPageDto();
 	turnPageDto.setResultList((List) pageRecord.getResult());
 	turnPageDto.setPageNo(pageRecord.getPageNo());
 	turnPageDto.setRecordPerPage(20);
 	turnPageDto.setTotalCount(pageRecord.getCount());
 	turnPageDto.setCondition("businessno='" + registNo.trim() + "'");
 	turnPageDto.setTotalPage(pageRecord.getTotalPageCount());
 	request.setAttribute("turnPageDto", turnPageDto);
 	curPage = turnPageDto.getPageNo();
 	prpLCertifyImgList = (ArrayList) pageRecord.getResult();
 %>
		<input type="hidden" name="editType" value="<%=editType%>" />
		<input type="hidden" name="directType" value="<%=directType%>" />
		<input type="hidden" name="businessNo" value="<%=businessNo%>" />
		<input type="hidden" name="itemcode" value="<%=strLossItemName%>" />
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<input type="hidden" name="BusinessNo" value="<%=businessNo%>">
				<!--      <td class="subformtitle" style="width:20%">单证清单类型</td>-->
				<td class="subformtitle" style="width: 20%">
					<s:text name="certify.documentsType" />
				</td>
				<%--单证清单类型--%>
				<td class="subformtitle" style="width: 20%">
					<s:text name="certify.instructe" />
				</td>
				<%--说明--%>
				<td class="subformtitle" style="width: 60%">
					<s:text name="certify.picture" />
				</td>
				<%--图片--%>
			</tr>
			<%
				if (prpLCertifyImgList != null) {
					for (int i = 0; i < prpLCertifyImgList.size(); i++) {
						PrpLcertifyImgDto prpLcertifyImgDto = (PrpLcertifyImgDto) prpLCertifyImgList.get(i);
			%>
			<tr>
				<td class="prompt"><%=prpLcertifyImgDto.getPicName()%></td>
				<td class="prompt"><%=prpLcertifyImgDto.getDisplayName()%></td>
				<%
					strFileName = "/claim/uiviewimg?BusinessNo=" + prpLcertifyImgDto.getBusinessNo() + "&SerialNo=" + prpLcertifyImgDto.getSerialNo();

							if (isImageFile(prpLcertifyImgDto.getImgFileName())) {
				%>
				<td class="prompt">
					<!--      <img src="<%=AppConfig.get("sysconst.CertifyVirtualPath")%><%=prpLcertifyImgDto.getPicPath().substring(1)%>/<%=prpLcertifyImgDto.getImgFileName()%>">-->
					<a href="/claim/DAA/certify/DAAShowFile.jsp?FileName=<%=strFileName%>" target="_blank"><img src="<%=strFileName%>"></a>
				</td>
				<%
					} else {
				%>
				<td class="prompt">
					<!--<a href="<%=AppConfig.get("sysconst.CertifyVirtualPath")%><%=prpLcertifyImgDto.getPicPath().substring(1)%>/<%=prpLcertifyImgDto.getImgFileName()%>"><%=prpLcertifyImgDto.getImgFileName()%></a>-->
					<img src="/claim/images/word.gif"> <a href="<%=strFileName%>" target="_blank"><%=prpLcertifyImgDto.getUploadFileName()%></a>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
				}
			%>
			<tr>
				<td colspan=3 class="button">
					<input type="button" class=button name="buttonClose" value="<s:text name='button.close.value' />" onclick="javascript:window.close()">
				</td>
			</tr>
		</table>
		<tr>
			<td colspan="7">
				<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<%--
                  <bean:define id="pageview" name="turnPageDto"/>
               --%>
						<%@include file="/pages/common/pub/TurnOverPage.jsp"%>
					</tr>
				</table>
			</td>
		</tr>
	</form>
</body>
</html>