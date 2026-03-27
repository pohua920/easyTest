<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<!--在不同状态下，    按钮的数量是不同的，-->
<%-- (1)ADD,EDIT     "暂存" "提交/下一步" "取消"
      (2)SHOW 已提交  "返回"
 --%>

<script language='javascript'>
function printWindow(registNo, strWindowName) {
	strUrl = "/claim/print/claimPrint.do?printType=Regist&RegistNo=" + registNo;
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;

	if (pageWidth < 100)
		pageWidth = 100;

	if (pageHeight < 100)
		pageHeight = 100;

	var newWindow = window.open(
					strUrl,
					strWindowName,
					'width='+ pageWidth+ ',height='+ pageHeight+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}
</script>
<%@ include file="/common/taglibs.jsp"%>
<table id="buttonArea">
	<tr>
		<c:if test="${prpLregist.status!='1'}">
			<td>
				<%@include file="/pages/common/pub/MessageButton.jsp"%>
			</td>
		</c:if>
		<td class="button" align="center">
			<input type="hidden" name="buttonSaveType" value="1">
			<s:select name="nextNodeNoList" style="width:0px;height:0px" list="#request.userSelectList" listKey="endNodeNo" listValue="endNodeName" multiple="true" value="#request.selectNodeList_int"></s:select>
			<c:if test="${editType != 'ADD'}">
				<input type="hidden" name="print" class="bigbutton"  value="<s:text name="button.printInsClaimInfo.value" />" onclick="printWindow('${prpLregist.registNo}', '列印1');">
			</c:if>
			<c:choose>
				<c:when test="${editType == 'SHOW'}">
					<c:choose>
						<c:when test="${ifclose=='true'}">
							<input type="button" name="buttonClose" class="button" value="<s:text name='button.close.value' />" onclick="return window.close();">
						</c:when>
						<c:otherwise>
							<input type="button" name="buttonBack" class="button" value="<s:text name='button.return.value' />" onclick="return history.back();">
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${prpLregist.status=='4'}">
							<c:if test="${editType == 'PERFECT'}">
								<input type="button" name="buttonSaveFinishSubmit" class="button" value="<s:text name='button.submit.value' />" onclick="return beforeSaveForm(this,'4');">
							</c:if>
							<c:choose>
								<c:when test="${ifclose=='true'}">
									<input type="button" name="buttonClose" class="button" value="<s:text name='button.close.value' />" onclick="return window.close();">
								</c:when>
								<c:otherwise>
									<input type="button" name="buttonBack" class="button" value="<s:text name='button.return.value' />" onclick="return history.back();">
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<input type="button" name="buttonSave" class="button" value="<s:text name='button.save.value'/>" onclick="return beforeSaveForm(this,'2');">
							<input type="button" name="buttonSaveFinishSubmit" class="button" value="<s:text name='button.submit.value'/>" onclick="return beforeSaveForm(this,'4');">
							<input type="button" name="buttonCancel" class="button" value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>