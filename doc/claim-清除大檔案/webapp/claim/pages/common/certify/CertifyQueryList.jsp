<%--
****************************************************************************
* DESC       ：单证结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-05
* MODIFYLIST ：   id  Sunhao     Date   2004-08-24         Reason/Contents
             1.在结果中增加案件状态
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<script language="javascript">
  <!--
  <%--案件状态标志处理--%>
  function submitForm() {
		if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0) || (fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("车险必须输入报案号、车牌号、被保险人其中一项精确查询！\n 非车险可以用报案号的前9位进行模糊查询！");
			return false;
		}
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {

			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}

		}

		fm.caseFlag.value = ref;
		fm.searchFlag.value = "true";
		fm.pageNo.value = "1";
		fm.submit(); //提交
	}
	//-->
	//按钮响应回车

	function document.onkeydown() {
		if (event.keyCode == 13) {
			document.getElementById("button").click();
			return false;
		}
	}
  </script>
<head>
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/common/js/showpage.js"> </script>
</head>
<body onload="initPage();document.onkeydown();">
	<form name="fm" action="/claim/certifyQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certify.queryDocumentInfo" />
				</td>
			</tr>
			<%--查询单证信息--%>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo" />:
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态:--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<!--<input type="checkbox" name="status" value="1">未处理-->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<%--没有此种案件状态 <input type="checkbox" name="status" value="3">已处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="nodeType" value="${param.nodeType }">
					<input type="hidden" name="editType2" value="SHOW">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=6 class="formtitle">
					<s:text name="certify.queryCertify" />
				</td>
			</tr>
			<%--查询单证收集信息--%>
			<tr>
				<td class="centertitle">
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态--%>
				<td class="centertitle">
					<s:text name="db.prpLcheckExt.registNo" />
				</td>
				<%--报案号码--%>
				<td class="centertitle">
					<s:text name="certify.beganCollectDate" />
				</td>
				<%--开始收集日期--%>
				<td class="centertitle">
					<s:text name="db.prpLlawsuit.operatorCode" />
				</td>
				<%--操作员--%>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.operatedate" />
				</td>
				<%--操作时间--%>
				<td class="centertitle">
					<s:text name="certify.collectFlag" />
				</td>
				<%--收集标志--%>
			</tr>
			<c:if test="${prpLcertifyCollect.certifyCollectList!=null}">
				<c:forEach var="prpLcertifyTemp" items="${prpLcertifyCollect.certifyCollectList}" varStatus="prpLcertify_status">
					<c:if test="${prpLcertify_status.index%2==0}">
						<tr class="listodd">
					</c:if>
					<c:if test="${prpLcertify_status.index%2!=0}">
						<tr class="listeven">
					</c:if>
					<td align="center">
						<c:choose>
							<c:when test="${prpLcertifyTemp.status=='1'}">
								<s:text name="common.status.untreated" />
								<%--未处理--%>
							</c:when>
							<c:when test="${prpLcertifyTemp.status=='2'}">
								<s:text name="common.status.intreating" />
								<%--正处理--%>
							</c:when>
							<c:when test="${prpLcertifyTemp.status=='3'}">
								<s:text name="common.status.treated" />
								<%--已处理--%>
							</c:when>
							<c:when test="${prpLcertifyTemp.status=='4'}">
								<s:text name="common.status.submited" />
								<%--已提交--%>
							</c:when>
							<c:when test="${prpLcertifyTemp.status=='5'}">
								<s:text name="common.status.revoked" />
								<%--已撤消--%>
							</c:when>
						</c:choose>
					</td>
					<td align="center">
						<a href="${ctx }/certifyFinishQueryList.do?prpLcertifyCertifyNo=${prpLcertifyTemp.id.businessNo}&nodeType=certi&editType=${prpLcertifyCollect.editType}&riskCode=${prpLcertifyTemp.riskCode}">${prpLcertifyTemp..id.businessNo}</a>
					</td>
					<td align="center">${prpLcertifyTemp.startDate}</td>
					<td align="center">${prpLcertifyTemp.operatorCode}</td>
					<td align="center">${prpLcertifyTemp.operateDate}</td>
					<td align="center">${prpLcertifyTemp.collectFlag}</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr class="listtail">
				<td colspan="6">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<!--分页信息
                  bean:define id="pageview" name="prpLcertifyCollectDto" property="turnPageDto"/>  
				  PrpLcertifyCollectDto prpLcertifyCollectDto = (PrpLcertifyCollectDto)request.getAttribute("prpLcertifyCollectDto"); 
				  int curPage = prpLcertifyCollectDto.getTurnPageDto().getPageNo(); 
                  < include file="/common/pub/TurnOverPage.jsp" >   
				 -->
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="editType" value="${param.editType }">
	</form>
</body>
</html>