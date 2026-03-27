<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">

<s:form name="fm" action="">
	<s:hidden name="applicantCode" value="${auditTask.utiIUserByApplicantcode.userCode}"> </s:hidden>
	<s:hidden name="userCode" value="${auditTask.userCode}"></s:hidden>
	<s:hidden name="auditTask.taskName" value="${auditTask.taskName}"></s:hidden>
	<s:hidden name="auditTask.userName" value="${auditTask.userName}"></s:hidden>
	<s:hidden name="taskInstanceId" value="${auditTask.taskinstanceCode}"></s:hidden>
	<s:hidden name="auditTask.serialNo" value="${auditTask.serialNo}"></s:hidden>
	<table width="100%" class="fix_table">
		<tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              	新添用户审核
            </h2>
            </div>	
		</tr>				

			<tr>
				<td class="bgc_tt short">申请人</td>
				<td class="long"><input type="text" name="auditTask.applicantName" value="${auditTask.applicantName}" readonly class='input_w w_15'/></td>
				<td class="bgc_tt short">申请机构</td>
				<td class="long"><input type="text" name="auditTask.userComName" value="${auditTask.userComName}" class='input_w w_30'
					  readonly/></td>				
				
			</tr>
			<tr>
				<td class="bgc_tt short">新增用户</td>
				<td class="long">
					<input type="text" name="userName" value="${auditTask.userName}" class='input_w w_15'
					 readonly/><a href="#" onclick="viewUserInfo('${auditTask.userCode}')">查看用户信息</a></td>
				<td class="bgc_tt short">申请时间</td>			
				<td class="long"><s:textfield name="auditTask.applyDate" value="${auditTask.applyDate}" cssClass='input_w w_30' maxlength="10"
					readonly="true">
						<s:param name="value"><s:date name="auditTask.applyDate" format="yyyy-MM-dd"/></s:param> 
					</s:textfield></td>
			</tr>
			<tr>
				<td class="bgc_tt short">审核意见<font color="red">*</font></td>
				<td class="long" colspan="3">
				<div id="wwgrp_smcMenu_actionURL" class="wwgrp">
				<div id="wwctrl_smcMenu_actionURL" class="wwctrl">
				<textarea name="auditTask.verifyOpinion" cols="45" rows="4" id="opinion" style="wwctrl dc-chk"></textarea>
				</div> </div>
				</td>	
			</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td><input type="button" value="同意" class="button_ty" onclick="verifyApprove()">
            <input type="button" value="不同意" class="button_ty" onclick="verifyDisapprove()">
			<input type="button" value="驳回" class="button_ty" onclick="verifyReject()">
			<input type="button" value="查看审核意见" class="button_ty" onclick="viewOpinions(${auditTask.serialNo})"></td>
    
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript">

	function verifyApprove(){
		var opinion = document.getElementById("opinion").innerHTML;
		if(opinion == "" || opinion == null){
			alert("请输入审核意见！");
			return false;
		}else{
			var i = 0;
			for(var j=0;j<opinion.length;j++){
				 if(opinion.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+2;
				 }	
			}
			if(i > 255){
				alert("审核意见过长，请检查！");
				return false;
			}else {
				fm.action="${ctx}/audit/verify.do?result=approve";
			    fm.submit();
			}
		}
	}
	
	function verifyDisapprove(){
		var opinion = document.getElementById("opinion").innerHTML;
		if(opinion == "" || opinion == null){
			alert("请输入审核意见！");
			return false;
		}else{
			var i = 0;
			for(var j=0;j<opinion.length;j++){
				 if(opinion.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+2;
				 }	
			}
			if(i > 255){
				alert("审核意见过长，请检查！");
				return false;
			}else {
				fm.action="${ctx}/audit/verify.do?result=disapprove";
			    fm.submit();
			}
		}
	
	}	

	function verifyReject(){
		var opinion = document.getElementById("opinion").innerHTML;
		if(opinion == "" || opinion == null){
			alert("请输入审核意见！");
			return false;
		}else{
			var i = 0;
			for(var j=0;j<opinion.length;j++){
				 if(opinion.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+2;
				 }	
			}
			if(i > 255){
				alert("审核意见过长，请检查！");
				return false;
			}else {
				fm.action="${ctx}/audit/verify.do?result=reject";
			    fm.submit();
			}
		}
		
	}

	function viewOpinions(taskId){
		vURL='${ctx}/audit/prpViewOpinions.do?taskId=' + taskId;
		window.open(vURL,"","width=600,height=400,top=200,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
	}
	function viewUserInfo(userCode){
		vURL = '${ctx}/audit/findUserByCode.do?userCode=' + userCode;
		window.open(vURL,"","width=600,height=600,top=50,left=200,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
	}

	
</script>
