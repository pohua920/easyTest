<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<HTML xmlns:mpc>
<HEAD>
<TITLE></TITLE>
	<jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
	<link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
	<jsp:include page="/pages/platform/behaviors/MpcStyle.jsp"/>
</HEAD>
<BODY BGCOLOR="#D7E1F6" ONLOAD="oMPC.style.visibility='visible'" style="scroll: no; overflow: hidden;">
	<form name="fm" action="" method="POST">
		<div id="Layer2" style="position:absolute; width:620px; height:450px; z-index:1; left:5px; top:30px;">
		  <mpc:container ID="oMPC" STYLE="width:620px; height:445px; visibility:hidden;">
				<mpc:page ID="tab2" TABTITLE="" TABTEXT="<s:text name='button.Staff.value'/>">
					<center>
						<div style="width:610px; height:445px; overflow:auto;">
							<jsp:include page="/pages/platform/uwusercondition/BeforeUpdateInclud.jsp"/>
						</div>
					</center>
				</mpc:page>
		  </mpc:container>
		</div>
		<app:claimPlatFromCodeInput/>
	</form>
	<script language="javascript">
		function prepareUpdate(actionType, nodeNo){
			fm.action = "/claim/processUwLevel.do?actionType=" + actionType +"&nodeNo=" + nodeNo;
			fm.submit();
		}
		 function doInsert(){
			if(checkUtiUwLevel() == false){
				return;
			}
			if(confirm("¥_åç“™É¶¥ÊÜ·£ø")){
				fm.action = "/claim/processUwLevel.do?actionType=insertUwLevel2";
				fm.submit();
			}
		 }
	</script>
</BODY>
</HTML>