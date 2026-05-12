<%--
****************************************************************************
* DESC       ： 单证查看单证图片页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
**************************************************************************** 
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.certifyBeforeEdit.viewDocumentInfo" /></title>
<%-- 查看单证信息 --%>
<%-- 页面样式  --%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<SCRIPT LANGUAGE="JavaScript">
	function submitFormli(field) {
		var n = 0;
		if (fm.checkboxSelect.length == undefined) {
			if (fm.checkboxSelect.checked == true) {
				n = n + 1;
			}
		}
		for ( var i = 0; i < fm.checkboxSelect.length; i++) {
			if (fm.checkboxSelect[i].checked == true) {
				n = n + 1;
			}
		}
		if (n == 0) {
			alert("请先选择要删除的单证!");
			return false;
		} else {
			if (!confirm('确认要删除选择的单证吗？')) {
				return false;
			}
			fm.action = "${ctx}/processCertifyImg.do?actionType=delete";
			fm.submit();
			return true;
		}
	}

	function submite() {
		fm.action = "${ctx}/DAA/certify/DAAShowFile.jsp";
		fm.submit();
	}

	function hand(obj) {
		obj.style.cursor = "hand";
	}

	function buttonOnClick3(fieldObject) {
		var intIndex = parseInt(getElementOrder(fieldObject) - 1);
		var spanId = 'span_Engage_Context00';
		if (isNaN(fm.button_Engage_Open_Context00.length)) {
		} else { //多行
			spanId = 'span_Engage_Context00' + "[" + intIndex + "]";
		}
		showSubPage3(spanId);
	}

	//leftMove 默认值0，坐标左移leftMove

	function showSubPage3(spanID, leftMove) {
		var intLeftMove = (leftMove == null ? 0 : leftMove);
		var span = eval(spanID);
		var strTemp = span.id;

		var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
		var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

		ex = ex - 520;

		if (ex < 0) {
			ex = 0;
		}
		ex = ex - intLeftMove;

		span.style.left = ex;
		span.style.top = ey;
		span.style.display = '';
	}
</SCRIPT>
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initPage();progressStop();">
	<form name=fm method="post"
		action="${ctx}/DAA/certify/DAACertifyViewFile.jsp?isCase=${isCase}&editType=${editType}&nodeTypeUpload=${nodeTypeUpload}&directType=${directType}&itemcode=${strLossItemName}&businessNo=${businessNo}">
		<input type="hidden" name="isCase" value="${isCase }" />
		<input type="hidden" name="editType" value="${editType}" />
		<input type="hidden" name="directType" value="${directType}" />
		<input type="hidden" name="display" value="${display}" />
		<input type="hidden" name="businessNo" value="${businessNo}" />
		<input type="hidden" name="itemcode" value="${strLossItemName}" />
		<input type="hidden" name="pageNo" value="${curPage+1}" />
		<input type="hidden" name="nodeTypeUpload" value="${nodeTypeUpload}" />
		<input type="hidden" name="BusinessNo" value="${businessNo}">
		<!--app:processBar-->
		<table border="0" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" width="100%">
			<tr>
				<!--<td class="subformtitle" style="width:20%" align="center">单证清单类型</td>-->
				<td class="subformtitle" style="width: 60%" align="center">
					<s:text name="certify.picture" />
				</td>
				<%-- 图片 --%>
				<td class="subformtitle" style="width: 20%" align="center">
					<s:text name="certify.instructe" />
				</td>
				<%-- 说明 --%>
				<!-- 
      // 由於display的数据，在翻页之後，边成="null" 这样的数据，所以需要增加判断条件"null".equals(display) 
      -->
				<c:if test="${display==null||''==display}">
					<td class="subformtitle" style="width: 10%" align="center">
						<s:text name="certify.delete" />
					</td>
					<%-- 删除 --%>
					<td class="subformtitle" style="width: 10%" align="center">
						<s:text name="certify.operate" />
					</td>
					<%-- 操作 --%>
				</c:if>
			</tr>
			<s:set var="cnti" value="0" scope="page" />
			<s:if test="#attr.page.result!=null">
				<s:set var="strFileName" value="" scope="page" />
				<s:iterator var="prpLcertifyImg" value="#attr.page.result" status="certify_status">
					<s:set var="checkboxReadOnly" value="" />
					<s:if test="#attr.nodeTypeUpload!=#prpLcertifyImg.uploadNodeFlag">
						<s:set var="checkboxReadOnly" value="disabled" />
					</s:if>
					<s:set var="imgFileName" value="%{'/claim/uiviewimg?BusinessNo='+#prpLcertifyImg.id.businessNo+'&SerialNo='+#prpLcertifyImg.id.serialNo}" scope="page" />
					<s:if test="#attr.display!=null&&#attr.display=='all'">
						<s:set var="strFileName" value="%{'/claim/uiviewimg?BusinessNo='+#prpLcertifyImg.id.businessNo+'&SerialNo=' + #prpLcertifyImg.id.serialNo}" scope="page" />
						<tr>
							<td class="prompt">
								<img src="${strFileName }">
							</td>
							<td class="prompt">${prpLcertifyImg.displayName }</td>
						</tr>
					</s:if>
					<s:else>
						<s:if test="#attr.directType=='undefined'">
							<s:if test="#prpLcertifyImg.typeCode!=null&&#prpLcertifyImg.typeCode.length()>1&&#prpLcertifyImg.typeCode.substring(0,2)=='99'">
								<s:set var="strFileName" value="%{'/claim/uiviewimg?BusinessNo='+#prpLcertifyImg.id.businessNo+'&SerialNo=' + #prpLcertifyImg.id.serialNo}" scope="page" />
								<tr>
									<td class="prompt">
										<s:set var="fileExt" value="" scope="page" />
										<s:if test="#prpLcertifyImg.imgFileName.indexOf('.')>-1">
											<s:set var="fileExt" value="#prpLcertifyImg.imgFileName.substring(#prpLcertifyImg.imgFileName.indexOf('.'))" scope="page" />
										</s:if>
										<s:if test="#attr.fileExt=='.doc'">
											<img src="/claim/images/word.gif">
											<a href="${strFileName }" target="_blank">${prpLcertifyImg.uploadFileName }</a>
										</s:if>
										<s:else>
											<a onclick="submite()" onmouseover="hand(this)" target="_blank"><img src="${strFileName }" width="130" height="110"> </a>
											<input type="hidden" name="FileName" value="${imgFileName }">
										</s:else>
									</td>
									<!-- modify by liyanjie add 20051103 begin -->
									<td class="prompt">${prpLcertifyImg.displayName}</td>
									<td class="prompt">
										<s:if test="#attr.userDto!=null">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&#attr.editType!='SHOW'">
												<input type=checkbox name=checkboxSelect value="${cnti }">
												<s:text name="certify.delete" />
												<%-- 删除 --%>
												<input type="hidden" name="certifyImgLossItemCode" value="${prpLcertifyImg.id.lossItemCode }">
												<input type="hidden" name="certifyImgSerialNo" value="${prpLcertifyImg.id.serialNo}">
												<s:set var="cnti" value="%{#attr.cnti+1}"></s:set>
											</s:if>
										</s:if>
									</td>
									<td class="prompt">
										<s:if test="#attr.userDto!=null">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&#attr.editType!='SHOW'">
												<input type="button" name="button_Engage_Open_Context00" class="button" value="<s:text name='button.writeNotes.value' />" onclick="buttonOnClick3(this);">
												<%-- 撰写备注 --%>
												<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
													<table class="common">
														<tr>
															<td class="prompttitle" colspan="6">
																<s:text name="db.prpLcomponent.remark" />
															</td>
															<%-- 备注 --%>
														</tr>
														<tr>
															<td class="prompt" colspan="6">
																<input name="prpLcertifyImgDisplayName" class="input" maxlength="100" value="${prpLcertifyImg.displayName }">
															</td>
														</tr>
														<tr>
															<td colspan=6 class="common">
																<input type=button class=button name='button_Engage_Close_Context00' value="<s:text name='button.confirm.value' />" ' ACCESSKEY="O"
																	onclick="updateRemark(this,'span_Engage_Context00','${businessNo }','${prpLcertifyImg.id.serialNo }')">
															</td>
															<%-- 确认 --%>
														</tr>
													</table>
												</span>
											</s:if>
										</s:if>
									</td>
								</tr>
							</s:if>
						</s:if>
						<s:elseif test="#attr.directType.length()<=1">
							<s:if
								test="(#prpLcertifyImg.typeCode!=null&&#prpLcertifyImg.typeCode.length()>1&&#prpLcertifyImg.typeCode.substring(1,2)==#attr.directType&&#attr.directType!='5'&&#attr.directType!='7'&&#attr.directType!='12')||(#prpLcertifyImg.typeCode!=null&&#prpLcertifyImg.typeCode.length()>1&&#prpLcertifyImg.typeCode.substring(1,2)==#attr.directType&&(#attr.directType=='5'||#attr.directType=='7'||#attr.directType=='12')&&prpLcertifyImg.id.lossItemCode==#attr.strLossItemName)">
								<tr>
									<td class="prompt">
										<s:set var="fileExt" value="" scope="page" />
										<s:if test="#prpLcertifyImg.imgFileName.indexOf('.')>-1">
											<s:set var="fileExt" value="#prpLcertifyImg.imgFileName.substring(#prpLcertifyImg.imgFileName.indexOf('.'))" scope="page" />
										</s:if>
										<s:if test="#attr.fileExt=='.doc'">
											<img src="${ctx }/images/word.gif">
											<a href="${strFileName }" target="_blank">${prpLcertifyImg.uploadFileName }</a>
										</s:if>
										<s:else>
											<a onclick="submite();" onmouseover="hand(this)" target="_blank"><img src="${strFileName }" width="130" height="110"> </a>
											<input type="hidden" name="FileName" value="${imgFileName }">
										</s:else>
									</td>
									<td class="prompt">${prpLcertifyImg.displayName }</td>
									<td class="prompt">
										<s:if test="#attr.userDto">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&#attr.editType!='SHOW'">
												<input type=checkbox name=checkboxSelect ${checkboxReadOnly } value="${cnti }">
												<s:text name="certify.delete" />
												<%-- 删除 --%>
												<input type="hidden" name="certifyImgLossItemCode" value="${prpLcertifyImg.id.lossItemCode}">
												<input type="hidden" name="certifyImgSerialNo" value="${prpLcertifyImg.id.serialNo}">
												<input type="hidden" name="certifyUploadNodeFlag" value="${prpLcertifyImg.uploadNodeFlag}">
												<input type="hidden" name="certifyUploadNodeName" value="${prpLcertifyImg.uploadFileName}">
												<s:set var="cnti" value="%{#attr.cnti+1}" scope="page" />
											</s:if>
										</s:if>
									</td>
									<td class="prompt">
										<s:if test="#attr.userDto!=null">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&editType!='SHOW'">
												<input type="button" name="button_Engage_Open_Context00" class="button" value="<s:text name='button.writeNotes.value' />" onclick="buttonOnClick3(this);">
												<%-- 撰写备注 --%>
												<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
													<table class="common">
														<tr>
															<td class="prompttitle" colspan="6">
																<s:text name="db.prpLcomponent.remark" />
																<%-- 备注 --%>
															</td>
														</tr>
														<tr>
															<td class="prompt" colspan="6">
																<input name="prpLcertifyImgDisplayName" class="input" maxlength="100" value="${prpLcertifyImg.displayName }">
															</td>
														</tr>
														<tr>
															<td colspan=6 class="common">
																<input type=button class=button name='button_Engage_Close_Context00' value="<s:text name='button.confirm.value' />" ACCESSKEY="O"
																	onclick="updateRemark(this,'span_Engage_Context00','${businessNo }','${prpLcertifyImg.id.serialNo}')">
															</td>
														</tr>
													</table>
												</span>
											</s:if>
										</s:if>
									</td>
								</tr>
							</s:if>
						</s:elseif>
						<s:else>
							<s:if
								test="(#prpLcertifyImg.typeCode!=null&&#prpLcertifyImg.typeCode.length()>1&&#prpLcertifyImg.typeCode.substring(1,2)==#attr.directType&&#attr.directType!='5'&&#attr.directType!='7'&&#attr.directType!='12')||(#prpLcertifyImg.typeCode!=null&&#prpLcertifyImg.typeCode.length()>1&&#prpLcertifyImg.typeCode.substring(1,2)==#attr.directType&&(#attr.directType=='5'||#attr.directType=='7'||#attr.directType=='12')&&prpLcertifyImg.id.lossItemCode==#attr.strLossItemName)">
								<tr>
									<td class="prompt">
										<s:set var="fileExt" value="" scope="page" />
										<s:if test="#prpLcertifyImg.imgFileName.indexOf('.')>-1">
											<s:set var="fileExt" value="#prpLcertifyImg.imgFileName.substring(#prpLcertifyImg.imgFileName.indexOf('.'))" scope="page" />
										</s:if>
										<s:if test="#attr.fileExt=='.doc'">
											<img src="/claim/images/word.gif">
											<a href="${strFileName }" target="_blank">${prpLcertifyImg.uploadFileName }</a>
										</s:if>
										<s:else>
											<a onclick="submite();" onmouseover="hand(this)" target="_blank"><img src="${strFileName }" width="130" height="110"></a>
											<input type="hidden" name="FileName" value="${imgFileName }">
										</s:else>
									</td>
									<!-- modify by liyanjie add 20051103 begin -->
									<td class="prompt">${prpLcertifyImg.displayName }</td>
									<td class="prompt">
										<s:if test="#attr.userDto!=null">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&#attr.editType!='SHOW'">
												<input type=checkbox name=checkboxSelect value="${cnti }">s:text name="certify.delete" /><%-- 删除 --%>
												<input type="hidden" name="certifyImgLossItemCode" value="${prpLcertifyImg.id.lossItemCode }">
												<input type="hidden" name="certifyImgSerialNo" value="${prpLcertifyImg.id.serialNo}">
												<s:set var="cnti" value="%{#attr.cnti+1}" scope="page" />
											</s:if>
										</s:if>
									</td>
									<td class="prompt">
										<s:if test="#attr.userDto!=null">
											<s:if test="#prpLcertifyImg.collectorName==#attr.userDto.userCode&&#attr.editType!='SHOW'">
												<input type="button" name="button_Engage_Open_Context00" class="button" value="<s:text name='button.writeNotes.value' />" onclick="buttonOnClick3(this);">
												<%-- 撰写备注 --%>
												<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
													<table class="common">
														<tr>
															<td class="prompttitle" colspan="6">
																<s:text name="db.prpLcomponent.remark" />
																<%-- 备注 --%>
															</td>
														</tr>
														<tr>
															<td class="prompt" colspan="6">
																<input name="prpLcertifyImgDisplayName" class="input" maxlength="100" value="${prpLcertifyImg.displayName }">
															</td>
														</tr>
														<tr>
															<td colspan=6 class="common">
																<input type=button class=button name='button_Engage_Close_Context00' value='确认' ACCESSKEY="O"
																	onclick="updateRemark(this,'span_Engage_Context00','${businessNo }','${prpLcertifyImg.id.serialNo}')">
															</td>
														</tr>
													</table>
												</span>
											</s:if>
										</s:if>
									</td>
								</tr>
							</s:if>
						</s:else>
					</s:else>
				</s:iterator>
			</s:if>
			<s:if test="#attr.display==null||''==#attr.display">
				<tr>
					<td colspan=4 class="centertitle">
						<input type="button" name="buttonSave" class="button" value="<s:text name='button.confirmDelete.value' />" onclick="return submitFormli(this)" <s:if test="#attr.isCase=='Yes'">disabled</s:if>>
						&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<%-- 确认删除 --%>
						<input type="button" name="buttonClose" class="button" value="<s:text name='button.close.value' />" onclick="javascript:window.close()">
					</td>
				</tr>
			</s:if>
		</table>
		<tr>
			<td colspan="7">
				<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
					<tr>
					</tr>
				</table>
			</td>
		</tr>
	</form>
	<script type="text/javascript">
		var xmlHttp;
		function updateRemark(field, tableName, businessNo, serialNo) {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			}

			var order = parseInt(getElementOrder(field));
			var displayName = document
					.getElementsByName("prpLcertifyImgDisplayName")[order - 1].value;
			var obj = document.getElementsByName(tableName)[order - 1];
			obj.style.display = 'none';
			var url = "/claim/updateCertifyRemark.do?businessNo=" + businessNo
					+ "&serialNo=" + serialNo + "&displayName="
					+ escape(escape(displayName));
			xmlHttp.open("GET", url);
			xmlHttp.onreadystatechange = callback;
			xmlHttp.send(null);
		}

		function callback() {
			if (xmlHttp.readyState == 4) {
				progressStop();
				if (xmlHttp.status == 200) {
					alert("更新备注成功！");
					location.replace(location);
				}
			} else {
				progressStart();
			}
		}
	</script>
</body>
</html>
