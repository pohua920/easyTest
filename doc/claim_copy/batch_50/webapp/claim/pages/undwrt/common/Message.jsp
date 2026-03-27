<%--
****************************************************************************
* DESC       ：操作成功提示页面
* AUTHOR     ：luyang
* CREATEDATE ：2004-12-27 11:56
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
</head>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<script language="javascript">
  function goNextTask(){
    var handType = fm.handType.value;
    var editType = fm.editType.value;
    var url = "";
    url = "${ctx }/hepeiTaskDeal.do?actionType=queryContinue";      
    fm.action=url;
    fm.method="post";
    fm.submit();
  }
 <c:if test="${reinisContent!=null&&reinisContent!=''}">
  alert('${reinisContent }');
  </c:if>
   <c:if test="${reinisContentFlag!=null&&reinisContentFlag!=''}">
  alert('${reinisContentFlag }');
  </c:if> 
</script>
<body>
	<form name="fm">
		<table align="center" class=common>
			<tr class=common>
				<td align="right" height="70px" width="40%" valign="middle" style="padding-right: 2px;border: 0">
					<img src="${ctx}/images/bgClaimFailure.gif" align="absmiddle">
				</td>
				<td align="left" height="70px" width="60%" valign="middle" style="padding-left: 2px;border: 0">
					<c:forEach items="${actionMessages}" var="msg">
						<c:out value="${pageScope.msg}"/><br>
					</c:forEach>
					<c:out value="${requestScope.content}"></c:out>
				</td>
			</tr>
		</table>
		<c:if test="${PolicyNo!=null&&PolicyNo!=''}">
			<table class=common>
				<tr>
					<td class="centertitle" align="center">
						<s:text name="undwrt.InsuranceNumber" />
						：${PolicyNo }
						<%-- 生成保单号 --%>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${EnquiryNo!=null&&EnquiryNo!=''}">
			<table class=common>
				<tr>
					<td class="centertitle" align="center">
						<s:text name="undwrt.RequestNumber" />
						：${EnquiryNo }
						<%-- 询价单号 --%>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${caseNo!=null&&caseNo!=''}">
			<table class=common>
				<tr>
					<td class="centertitle" align="center">
						<s:text name="prompt.undwrt.Success" />：${caseNo }
						<%-- 自动结案成功！生成赔案号 --%>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${undwrt_continuetask=='1'&&submitPage!=null&&submitPage=='1'}">
			<table class=common>
				<tr>
					<td class="centertitle" align="center">
						<input type="hidden" name="handType" value="${HandType }">
						<input type="hidden" name="editType" value="${EditType }">
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${dealBack!=null&&dealBack=='true'}">
			<table class=two>
				<tr>
					<td align="center">
						<Input name="buttonCancel" class="button" type="button" value="<s:text name='button.return.value'/>" alt="返回" src="${ctx }/pages/undwrt/common/images/butReturn.gif" onclick="return preWindow();">
						<%-- 返 回 --%>
					</td>
				</tr>
			</table>
		</c:if>
		<c:remove var="dealBack" scope="session" />
	</form>
</body>
</html>
