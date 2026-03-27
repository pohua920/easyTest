<%--
****************************************************************************
* DESC       ：委托公估公司处理赔案评估表
* AUTHOR     : liuwei
* CREATEDATE ：2011-05-21
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/meta_js.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<html>
<head>
<title><s:text name="title.pubBeforeEdit.appointCompanyHandleTable" /></title>
<%--委托公估公司处理赔案评估表--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<script type="text/javascript">
	function check(field, startvalue, endvalue, name) {
		var value = field.value;
		var totalScore = 0;
		var rate = 0.1;
		for ( var i = 0; i < 3; i++) {
			if (value != "") {
				document.getElementsByName(name)[i].value = ""
			}
		}
		field.value = value;
		if ((value > startvalue) && (value <= endvalue) || value == "") {
			for ( var i = 0; i < 3; i++) {
				for ( var j = 1; j <= 7; j++) {
					if (j == 3 || j == 5 || j == 6) {
						rate = 0.2;
					} else {
						rate = 0.1;
					}
					if (document.getElementsByName("score" + j)[i].value != "") {
						totalScore = totalScore
								+ document.getElementsByName("score" + j)[i].value
								* rate;
					}
				}
			}
			document.getElementById("totalScore").value = round(totalScore, 1);
			total.innerHTML = round(totalScore, 1);
			return true;
		} else {
			alert("系统错误提示：\r\n得分取值范围为" + endvalue + "~" + startvalue);
			field.focus();
			return false;
		}
	}
	function submitForm() {
		fm.buttonSave.disabled = "true";
		if (fm.commitDate.value == "") {
			alert("请輸入委托时间！");
			fm.buttonSave.disabled = "";
			return false;
		}
		var index = 0;
		for ( var i = 0; i < 3; i++) {
			for ( var j = 1; j <= 7; j++) {
				if (document.getElementsByName("score" + j)[i].value != "") {
					index = index + 1;
				}
			}
		}
		if (index != 7) {
			alert("系统错误提示：\r\n还有评估指标没有进行评估！");
			fm.buttonSave.disabled = "";
			return false;
		}
		if (fm.remark.vaue != "") {
			if (fm.remark.value.length > 250) {
				alert("系统错误提示：\r\n说明不能超过250个字符！");
				fm.buttonSave.disabled = "";
				return false;
			}
		}
		if (fm.company.vaue != "") {
			if (fm.company.value.length > 50) {
				alert("系统错误提示：\r\n评估单位不能超过50个字符！");
				fm.buttonSave.disabled = "";
				return false;
			}
		}
		fm.submit();
	}
</script>
</head>
<body bgcolor="#FFFFFF">
	<form name="fm" method="post" action="/claim/AssessorScore.do" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="insertSave" />
		<!-- 标题部分 -->
		<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height=30>
				<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>
						<center>
							<span><s:text name="pub.appointCompanyHandleTable" /></span>
							<%--委托公估公司处理赔案评估表--%>
							<center>
								<B>
				</td>
			</tr>
		</table>
		<table border=1 width="100%" align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td width="50%" valign="top">
					<table width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
						<tr height="25">
							<td width="15%">
								<s:text name="pub.assessmentCompanyName" />
								：
							</td>
							<%--公估公司名称--%>
							<td colspan="7">
								<input type="hidden" name="comCode1" value="${prpLAssessorScoreDto.id.comCode1}" />
								${prpLAssessorScoreDto.comCName2}
							</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="pub.assessmentName" />
								：
							</td>
							<%--公估师姓名--%>
							<td colspan="3" width="35%">
								<input type="hidden" name="comCode" value="${prpLAssessorScoreDto.id.comCode}" />
								${prpLAssessorScoreDto.comCName1}
							</td>
							<td width="15%">
								<s:text name="certify.contactPhoneNo" />
								：
							</td>
							<%--联络电话--%>
							<td colspan="3" width="35%">${prpLAssessorScoreDto.telePhone}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="specialCase.ClaimsNumbers" />
								：
							</td>
							<%--赔案号码--%>
							<td colspan="3" width="35%">
								<input type="hidden" name="claimNo" value="${prpLclaimDto.claimNo}" />
								${prpLclaimDto.claimNo}
							</td>
							<td width="15%">
								<s:text name="db.view_larrearage.insuredname" />
								：
							</td>
							<%--被保险人名称--%>
							<td colspan="3" width="35%">${prpLclaimDto.insuredName}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="pub.commissionTime" />
								：
							</td>
							<%--委托时间--%>
							<td colspan="3" width="35%">
								<input type="text" name="commitDate" class="Wdate" onClick="WdatePicker()" readonly="readonly" style="width: 85">
							</td>
							<td width="15%">
								<s:text name="regist.prpLregist.damageTime" />
								：
							</td>
							<%--出险时间--%>
							<td colspan="3" width="35%">${prpLclaimDto.damageStartDate}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCase" />
							</td>
							<%--事故原因：--%>
							<td colspan="7">${prpLclaimDto.damageName}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
							</td>
							<%--事故地点：--%>
							<td colspan="7">${prpLclaimDto.damageAddress}</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<b><s:text name="pub.evaluationIndicator" /></b>
								<%--评估指标--%>
							</td>
							<td width="7%">
								<b><s:text name="dangerUnit.Share" /></b>
								<%--占比--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score100" /></b>
								<%--得分(100~80)--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score80" /></b>
								<%--得分(80~60)--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score60" /></b>
								<%--得分(60~30)--%>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.cooperatDegree" />
							</td>
							<%--配合度--%>
							<td width="7%">10%</td>
							<td width="13%">
								<s:text name="pub.fast" />
							</td>
							<%--快--%>
							<td width="13%">
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score1" onblur="return check(this,80,100,'score1')">
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score1" onblur="return check(this,60,80,'score1')">
							</td>
							<td width="13%">
								<s:text name="pub.slow" />
							</td>
							<%--慢--%>
							<td width="13%">
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score1" onblur="return check(this,30,60,'score1')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.negotiatSkill" />
							</td>
							<%--谈判技巧--%>
							<td>10%</td>
							<td>
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score2" onblur="return check(this,80,100,'score2')">
							</td>
							<td>
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score2" onblur="return check(this,60,80,'score2')">
							</td>
							<td>
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score2" onblur="return check(this,30,60,'score2')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.professionalLevel" />
							</td>
							<%--专业水平--%>
							<td>20%</td>
							<td>
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score3" onblur="return check(this,80,100,'score3')">
							</td>
							<td>
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score3" onblur="return check(this,60,80,'score3')">
							</td>
							<td>
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score3" onblur="return check(this,30,60,'score3')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.progressReturnManner" />
							</td>
							<%--处理进度回报是否及时--%>
							<td>10%</td>
							<td>
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score4" onblur="return check(this,80,100,'score4')">
							</td>
							<td>
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score4" onblur="return check(this,60,80,'score4')">
							</td>
							<td>
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score4" onblur="return check(this,30,60,'score4')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.qualityAssessReport" />
							</td>
							<%--公估报告质量--%>
							<td>20%</td>
							<td>
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score5" onblur="return check(this,80,100,'score5')">
							</td>
							<td>
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score5" onblur="return check(this,60,80,'score5')">
							</td>
							<td>
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score5" onblur="return check(this,30,60,'score5')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.professionalEthics" />
							</td>
							<%--职业道德操守--%>
							<td>20%</td>
							<td>
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score6" onblur="return check(this,80,100,'score6')">
							</td>
							<td>
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score6" onblur="return check(this,60,80,'score6')">
							</td>
							<td>
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score6" onblur="return check(this,30,60,'score6')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.price" />
							</td>
							<%--收费价格--%>
							<td>10%</td>
							<td>
								<s:text name="pub.reasonable" />
							</td>
							<%--合理--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score7" onblur="return check(this,80,100,'score7')">
							</td>
							<td>
								<s:text name="pub.highSide" />
							</td>
							<%--偏高--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score7" onblur="return check(this,60,80,'score7')">
							</td>
							<td>
								<s:text name="pub.tooHigh" />
							</td>
							<%--过高--%>
							<td>
								<input type="text" class="input" onkeyup="value=value.replace(/[^\d]/g,'')" name="score7" onblur="return check(this,30,60,'score7')">
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.totalScore" />
							</td>
							<%--总得分--%>
							<input type="hidden" id="totalScore" name="totalScore">
							<td colspan="7" id="total"></td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.instructions" />
							</td>
							<%--说明--%>
							<td colspan="7">
								<textarea rows="10" cols="10" style="width: 100%;" name="remark" class="input"></textarea>
							</td>
						</tr>
						<tr align="center" height="25">
							<td>
								<s:text name="pub.assessmentUnit" />
							</td>
							<%--评估单位--%>
							<td colspan="7">
								<input type="text" class="input" name="company" value="" style="width: 100%; height: 100%" maxlength="100">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div align="center" id="divButton" style="display:">
			<p>
			<table cellpadding="0" cellspacing="0" width="100%" style="display:">
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="buttonSave" value="<s:text name='button.save.value' />" onclick="return submitForm()">
					</td>
					<td class=button style="width: 33%" align="center">
						<input class="button" type="button" name="buttonPrint" value="<s:text name='button.print.value' />" onclick="vbscript:printPage()" disabled="disabled">
					</td>
					<td class=button align="center">
						<input class="button" type="button" name="buttonBack" value="<s:text name='button.return.value' />" onclick="return history.back();">
					</td>
				</tr>
			</table>
			</p>
		</div>
	</form>
</body>
</html>
