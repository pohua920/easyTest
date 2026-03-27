<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:tab>
	<head>
		<title></title>
		<link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
		<jsp:include page="/pages/platform/behaviors/HtcStyle.jsp" />
	</head>
	<body>
		<div id="mainLayer" style="position: absolute; width: 792px; height: 500px; z-index: 1; left: 5px; top: 5px;">
			<table>
				<tr>
					<td>
						<tab:tabBox class="tabstrip" style="display:none">
							<tab:Tabs selectedIndex="0" width="792px" height="500px">
								<tab:Button Text="&nbsp;<s:text name='button.Staff.value'/>&nbsp;"><%-- 人员权限 --%>							
									<iframe style="width: 785; height: 100%" src="${ctx}/processUwLevel.do?actionType=prepareQuery"></iframe>
								</tab:Button>
							</tab:Tabs>
						</tab:tabBox>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>