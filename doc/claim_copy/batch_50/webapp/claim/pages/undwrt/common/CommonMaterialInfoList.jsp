<!--***************************************************************************
* Description: 上传资料列表
* Author     : liuxueting
* CreateDate : 2005-05-29
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<title><s:text name="title.undwrtBeforeEdit.QueryResults" /></title>
<%-- 资料查询结果 --%>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<script language="javascript">
  //查看上传的文件
	function viewUploadFile(i){
	    var filePath = "";
	    var fileName = "";
	    var count = fm.filePath.length;
	    if (count > 1) {
	        filePath = fm.filePath[i].value;
	        fileName = fm.sysFileName[i].value;
	    } else {
	        filePath = fm.filePath.value;
	        fileName = fm.sysFileName.value;
	    }
	    filePath = trim(filePath);
	    var strURL = "/undwrt/upload/ViewUploadFile.jsp?filePath=" + filePath + "&sysFileName=" + fileName;
	    var newWindow = window.open(strURL, "Lendor", 'width=540,height=270,top=50,left=100,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

</script>
</head>
<body>
	<form name="fm" action="${ctx }/materialInfo.do" target="fraInterface" method="post">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class=listtitle>
				<td width=50%>
					<s:text name="db.utiPfield.fileName" />
				</td>
				<%-- 文件名称 --%>
				<td width=50%>
					<s:text name="certainLoss.view" />
				</td>
				<%-- 查看 --%>
			</tr>
			<c:if test="${MaterialList!=null}">
				<c:forEach items="${MaterialList}" var="materialDto" varStatus="materialDto_status">
					<tr class=common>
						<td align="left">${materialDto.fileName}</td>
						<td>
							<input type="button" class=button name=buttonSave value="<s:text name='button.view.value'/>" onclick="viewUploadFile(${materialDto_status.index });">
							<%-- 查 看 --%>
							<input type="hidden" name="filePath" value="${materialDto.filePath}">
							<input type="hidden" name="sysFileName" value="${materialDto.sysFileName}">
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<s:set value="#parameters.BusinessNo" var="BusinessNo" scope="page"></s:set>
			<s:set value="#parameters.BusinessNo.substring(0,1)" var="businessNo_starts" scope="page"></s:set>
			<c:if test="${MaterialList==null&&businessNo_starts=='2'}">
				<c:redirect url="${ctx }/common/certify/CertifyViewAllFile.jsp?businessNo=${BusinessNo }"></c:redirect>
			</c:if>
		</table>
	</form>
</body>
</html>