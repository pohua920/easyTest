<%--
****************************************************************************
* DESC       ：报案注销处理页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLregistText"%>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%
	//得到案件注销标志
	String strCancelFlag = (String) request.getAttribute("strCancel");
%>
<head>
	
<html xmlns:mpc>
<!--立案注销/拒赔处理入口-->
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script language=javascript>
		function submitForm() {
			var context = fm.prpLregistTextContextInnerHTML.value;
			if (fm.prpLclaimRegistNo.value == null || (fm.prpLclaimRegistNo.value).length < 1) {
				alert(i18n.generalClaim.reportNumberCannotEmpty); //报案号不能为空!
				return false;
			}
			if (context.length < 1) {
				alert(i18n.generalClaim.cancelReasonsCannotEmpty); //注销原因不能为空!
				fm.prpLregistTextContextInnerHTML.focus();
				return false;
			}
			var $checked = $("input:enabled[name='txtcheckadd']").filter(":checked");//判断是否有选择的
			if($checked.length==0){
				alert(i18n.generalClaim.selectLeastPolicyCanceled) //请至少选择一项需要注销的保单！
				return;
			}else{
				$checked.each(function(){
		        	 var $selectToCancle = $(this).parents("tr").find("input[name='selectToCancle']");
		        	 if(this.checked){
		        		 $selectToCancle.val("1");
		        	 }else{
		        		 $selectToCancle.val("0");
		        	 }
				});
			}
			var $unchecked = $(":checkbox[name='txtcheckadd']").not(":checked");//取未选中的
			if($unchecked.length==0){//全部注销
				$("#txtCheckallCancel").attr("checked",true);
				$("#txtAllCancle").val("1");
			}else{//注销一部分
				$("#txtCheckallCancel").attr("checked",false);
				$("#txtAllCancle").val("0");
			}
			fm.submit();
			fm.buttonSave.disabled = true;
		}

		function initInfo() {
			var errorMessage = "";
			var prpLregistCancel = (fm.prpLregistCancel.value);
			if (prpLregistCancel == "1") {
				errorMessage = i18n.generalClaim.reportCanceled; //此报案已注销
				fm.buttonSave.disabled = true;
			}
			if (errorMessage.length > 0) {
				alert(errorMessage);
				return false;
			}
			return true;
		}

		function initRegistText() { <% PrpLregistText prpLregistTextText = (PrpLregistText) request.getAttribute("prpLregistText"); %>
				fm.prpLregistTextContextInnerHTML.value = '<%=prpLregistTextText.getContext()%>';
		}
    </script>
<script type="text/javascript">
			//mpc调整
			$(function(){
			     initWindowNoBtn();
		         $(window).resize(function(){
					initWindowNoBtn();
		         });
		         var $txtCheckallCancel = $("#txtCheckallCancel");
		         //
		         $("input:enabled[name='txtcheckadd']").click(function(){
		        	 var $unchecked = $(":checkbox[name='txtcheckadd']").not(":checked");
		        	 if($unchecked.length==0){
		        		 $txtCheckallCancel.attr("checked",true);
		        	 }else{
		        		 $txtCheckallCancel.attr("checked",false);
		        	 }
		         });
		         $txtCheckallCancel.click(function(){
		        	 if(this.checked){
		        		 $("input:enabled[name='txtcheckadd']").attr("checked",true);
		        	 }else{
		        		 $("input:enabled[name='txtcheckadd']").attr("checked",false);
		        	 }
		         });
			})
	   </script>
</head>
<body onload=" initInfo();initRegistText();oMPC.style.visibility='visible'">
	<DIV id="mainLayer" class="mainLayerNoBtn">
		<form name=fm action="/claim/registCancel.do" method="post" onsubmit="return validateForm(this);">
			<s:token ></s:token>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="<s:text name='regist.prpLregist.cancle'/>" TABTEXT="<s:text name='regist.prpLregist.cancle'/>">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 90%">
								<tr class=listtitle style='display: none'>
									<td colspan="4">
										<s:text name="regist.prpLregist.cancle" />
									</td>
								</tr>
								<%-- 报案注销处理 --%>
								<tr>
									<td class="title" style="width: 15%; valign: bottom">
										<s:text name="db.prpLclaim.registNo" />
										<%--备案号码--%>:
									</td>
									<td class="input" style="width: 35%; valign: bottom" colspan="3">
										<input type="text" name="prpLclaimRegistNo" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="${prpLregist.registNo}">
									</td>
								</tr>
								<%
											String selectToCancle = "0"; //是否可以选择用来进行注销的保单
											String haveCheck = ""; //默认是否选中
											String haveCheckdisable = ""; //是否允许进行选择
											String haveCancled = ""; //已经注销过了。
											int rowcount = 0;
								%>
								<c:forEach items="${registDto.prpLRegistRPolicyList}" var="relatePolicyList1" varStatus="prpCengageTempStatus">
									<%
										haveCheckdisable = ""; //默认不能选择check
										haveCheck = "checked"; //默认选择check
										rowcount = rowcount + 1; //记录保单的条数
										haveCancled = ""; //没有注销过
									%>
									<c:if test="${not empty relatePolicyList1.claimNo}">
										<%
											haveCheckdisable = "disabled";
											haveCheck = "";
										%>
									</c:if>
									<c:if test="${relatePolicyList1.validStatus=='0'}">
										<%
											haveCheckdisable = "disabled";
											haveCancled = "1";
										%>
									</c:if>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="db.prpLclaim.policyNo" />:
										</td>
										<td class="input" style="width: 35%">
											<input type="hidden" name="selectToCancle" value="">
											<input type="hidden" name="haveCancled" value="<%=haveCancled%>">
											<input type="checkbox" class="" name="txtcheckadd" style="width: 20px" value="ON"  <%=haveCheck%> <%=haveCheckdisable%>>
											<input type="text" name="prpLclaimPolicyNo" class="readonly" title="保單號碼" maxlength="22" readonly="true" value="${relatePolicyList1.id.policyNo}">
										</td>
										<td class="title" style="width: 15%">
											<s:text name="db.prpCmain.policyType" />
										</td>
										<%-- 保单类型 --%>
										<td class="input" style="width: 35%">
											<%-- 商业保单 --%>
											<c:if test="${relatePolicyList1.policyType=='1'}">
												<s:text name="regist.prpLregist.businessPolicy" />
											</c:if>
											<%-- 强制保单 --%>
											<c:if test="${relatePolicyList1.policyType=='3'}">
												<s:text name="regist.prpLregist.forcedPolicy" />
											</c:if>
										</td>
									</tr>
								</c:forEach>
								<%
											String allCancelChecked = "checked";
											String allCancel = "Style='display:none'";
											if (rowcount > 1) {
												allCancel = "";
												allCancelChecked = "";
												//考虑了一下，如果一个案件已经立案了，如果没有注销的情况下，也是不能进行全部报案注销的。
											}
								%>
								<tr <%=allCancel%>>
									<%-- 选择: --%>
									<td class="title" style="width: 15%; valign: bottom">
										<s:text name="regist.prpLregist.check" />
									</td>
									<%-- 选择全部保单 --%>
									<td class="input" style="width: 35%; valign: bottom" colspan="3">
										<input type="checkbox" class="" id="txtCheckallCancel" name="txtCheckallCancel" checked="true" style="width: 20px" value="ON" >
										<s:text name="regist.prpLregist.checkAll" />
									</td>
								</tr>
								<tr>
									<input type="hidden" name="txtPolicyCount" value="<%=rowcount%>">
									<input type="hidden" name="txtAllCancle" id="txtAllCancle" value="1">
									<%-- 注销原因： --%>
									<td class="title" colspan="4">
										<s:text name="regist.prpLregist.cancleReason" />
									</td>
								</tr>
								<tr>
									<td class="input" colspan="4" align="center">
										<textarea wrap="hard" rows=15 cols=80 style="width: 505px" name="prpLregistTextContextInnerHTML"></textarea>
									</td>
								</tr>
							</table>
							<input type="hidden" name="prpLregistCancel" value="<%=strCancelFlag%>">
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<TABLE id="btnCommon" class="common">
				<TR>
					<TD align="center">
						<input type=button name=buttonSave class='button' value="<s:text name='button.submit.value' />" onClick="submitForm();">
						<input type=button name=buttonCancel class='button' value="<s:text name='prompt.back'/>" onclick="history.back();">
					</td>
				</TR>
			</TABLE>
		</form>
	</DIV>
</body>
</html>