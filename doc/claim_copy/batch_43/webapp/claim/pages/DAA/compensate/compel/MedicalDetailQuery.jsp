<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title>強制險醫療給付費用處理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
	<script language="javascript" src="${ctx}/pages/DAA/compensate/compel/js/MedicalDetailQuery.js"></script>
</head>
<body>
	<form name="fm" id="fm" action="${ctx}/compensate/medicalDetailQuery.do" method="post" autocomplete="off" >
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">查詢條件</td>
			</tr>
			<tr>
				<td class="title">計算書號碼：</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<select class="tag" name="queryCompensateNoSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryCompensateNo" class="query" maxlength="44">
				</td>
				<td class="title">立案號碼：</td>
				<td class="input">
					<select class="tag" name="queryClaimNoSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryClaimNo" class="query" maxlength="44">
				</td>
			</tr>
			<tr>
				<td class="title">受害人名稱：</td>
				<td class="input">
					<select class="tag" name="queryPersonNameSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type="text" name="queryPersonName" class="query" maxlength="100">
				</td>
				<td class="title">受害人身分證號：</td>
				<td class="input">
					<select class="tag" name="queryIdentifyNumberSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryIdentifyNumber" class="query" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title">保單號碼：</td>
				<td class="input">
					<select class="tag" name="queryPolicyNoSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryPolicyNo" class="query" maxlength="44">
				</td>
				<td class="title">結案日期：</td>
				<!-- 操作时间 -->
				<td class="input" colspan="3">
					<rc:rcDate name="queryEndCaseDateStart" style="width: 120px" wdatePicker="true" />&nbsp;起&nbsp;
					<rc:rcDate name="queryEndCaseDateEnd" style="width: 120px" wdatePicker="true" />&nbsp;止&nbsp;
				</td>
			</tr>
			<tr>
				<td class="title">狀態：</td>
				<td class="input" colspan="5">
					<input type="radio" name="queryStatus" value="0" >待補錄
					<input type="radio" name="queryStatus" value="2" >暫存
					<input type="radio" name="queryStatus" value="4" checked="checked">已校核
					<span style="color: red;">（待補錄：未錄入任何收據資料；暫存：未通過一致性校核；已校核：受害人各項醫療費用賠付與收據資料一致。）</span>
				</td>
			</tr>
			<tr>
				<td class="button" colspan="4">
					<input type="button" class="button" value="<s:text name='button.query.value' />" onclick="medicalDetailQuery();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>