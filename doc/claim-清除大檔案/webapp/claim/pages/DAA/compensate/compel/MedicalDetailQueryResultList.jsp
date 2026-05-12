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
	<script type="text/javascript">
		var currentPageNo = ${requestScope.currentPageNo};
		var totalPageCount = ${requestScope.totalPageCount};
		var currentPageSize = ${requestScope.currentPageSize};
		$(function(){
			$("#cbxall").click(function(){
				$(":checkbox[name='cbx']").prop("checked",this.checked);
			});
			if(currentPageNo < totalPageCount){
				var loadhandler = function(){
					$.get(contextRootPath + "/compensate/getNextPageMedicalDetail.do" , { pageNo : currentPageNo + 1 , rowsPerPage : currentPageSize , queryType : "querycontinue" , returnType:"html"} , function(data){
						$("#tbresult").append(data);
						currentPageNo = currentPageNo + 1;
						if(currentPageNo < totalPageCount){
							$("#loadingdiv").one("click", loadhandler);
							$("#loadingdiv").find("em").text(currentPageNo + 1);
						} else {
							$("#loadingdiv").text("已加載至尾頁");
						}
					} , "html");
				};
				$("#loadingdiv").one("click", loadhandler);
				$("#loadingdiv").find("em").text(currentPageNo + 1);
			}
		});
	</script>
</head>
<body style="overflow: auto;">
	<form name="fm" id="fm" action="${ctx}/compensate/medicalDetailQuery.do" method="post">
		<input type="hidden" name="queryType" id="queryType" value="querycontinue">
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
					<input type="text" name="queryCompensateNo" class="query" value="${param.queryCompensateNo}" maxlength="44">
					<script type="text/javascript">$(":input[name='queryCompensateNoSign']").val("${param.queryCompensateNoSign}");</script>
				</td>
				<td class="title">立案號碼：</td>
				<td class="input">
					<select class="tag" name="queryClaimNoSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryClaimNo" class="query" value="${param.queryClaimNo}" maxlength="44">
					<script type="text/javascript">$(":input[name='queryClaimNoSign']").val("${param.queryClaimNoSign}");</script>
				</td>
			</tr>
			<tr>
				<td class="title">受害人名稱：</td>
				<td class="input">
					<select class="tag" name="queryPersonNameSign">
						<option value="=" selected="selected">=</option>
					</select>
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type="text" name="queryPersonName" class="query" value="${param.queryPersonName}" maxlength="100">
					<script type="text/javascript">$(":input[name='queryPersonNameSign']").val("${param.queryPersonNameSign}");</script>
				</td>
				<td class="title">受害人身分證號：</td>
				<td class="input">
					<select class="tag" name="queryIdentifyNumberSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryIdentifyNumber" class="query" value="${param.queryIdentifyNumber}" maxlength="40">
					<script type="text/javascript">$(":input[name='queryIdentifyNumberSign']").val("${param.queryIdentifyNumberSign}");</script>
				</td>
			</tr>
			<tr>
				<td class="title">保單號碼：</td>
				<td class="input">
					<select class="tag" name="queryPolicyNoSign">
						<option value="=" selected="selected">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="queryPolicyNo" class="query" value="${param.queryPolicyNo}" maxlength="44">
					<script type="text/javascript">$(":input[name='queryPolicyNoSign']").val("${param.queryPolicyNoSign}");</script>
				</td>
				<td class="title">結案日期：</td>
				<!-- 操作时间 -->
				<td class="input" colspan="3">
					<rc:rcDate name="queryEndCaseDateStart" style="width: 120px" value="${param.queryEndCaseDateStart}" wdatePicker="true"/>
					&nbsp;起&nbsp;
					<rc:rcDate name="queryEndCaseDateEnd" style="width: 120px" value="${param.queryEndCaseDateEnd}" wdatePicker="true"/>
					&nbsp;止&nbsp;
				</td>
			</tr>
			<tr>
				<td class="title">狀態：</td>
				<td class="input" colspan="5">
					<input type="radio" name="queryStatus" value="0" >待補錄
					<input type="radio" name="queryStatus" value="2" >暫存
					<input type="radio" name="queryStatus" value="4" >已校核
					<span style="color: red;">（待補錄：未錄入任何收據資料；暫存：未通過一致性校核；已校核：受害人各項醫療費用賠付與收據資料一致。）</span>
					<script type="text/javascript">$(":input[name='queryStatus'][value='${param.queryStatus}']").prop("checked",true);</script>
				</td>
			</tr>
			<tr>
				<td class="button" colspan="4">
					<input type="button" class="button" value="<s:text name='button.query.value' />" onclick="medicalDetailQuery();">
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table class="common" cellpadding="5" cellspacing="1">
						<thead>
							<tr>
								<td colspan="13" class="formtitle">強制險醫療給付費用處理</td>
							</tr>
							<tr>
								<td class="centertitle"><input type="checkbox" id="cbxall" >&nbsp;選擇</td>
								<td class="centertitle">保單號碼</td>
								<td class="centertitle">立案號碼</td>
								<td class="centertitle">計算書號碼</td>
								<td class="centertitle">受害人數別</td>
								<td class="centertitle">受害人名稱</td>
								<td class="centertitle">身分證號碼</td>
								<td class="centertitle">醫療費用合計</td>
								<td class="centertitle">健保點數</td>
								<td class="centertitle">健保金額</td>
								<td class="centertitle">結案日期</td>
								<td class="centertitle">狀態</td>
								<td class="centertitle">操作</td>
							</tr>
						</thead>
						<tbody id="tbresult">
							<c:choose>
								<c:when test="${not empty requestScope.resultList}">
									<%@include file="/pages/DAA/compensate/compel/MedicalDetailQueryResultData.jsp"%>
								</c:when>
								<c:otherwise>
									<tr><td align="center" colspan="13">未查詢到資料！</td></tr>
								</c:otherwise>
							</c:choose>
						</tbody>
						<tfoot>
							<c:if test="${requestScope.currentPageNo < requestScope.totalPageCount}">
								<tr class="listtail" id="loadingtr">
									<td colspan="13" align="center"><div id="loadingdiv" style="width: 100%;background-color: #F7F7F7;" align="center" >點擊加載第<em></em>頁到本頁</div></td>
								</tr>
							</c:if>
							<tr class="listtail">
								<td colspan="13" align="center">
									<%@include file="/pages/common/pub/TurnPage.jsp"%>
								</td>
							</tr>
						</tfoot>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<c:if test="${param.queryStatus == '2' || param.queryStatus == '4'}">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td align="center" class="button">
					<input type="button" class="button" value="選擇導出" onclick="exportMedicalDetail();">
				</td>
			</tr>
		</table>
		<form id="export" action="${ctx}/compensate/exportMedicalDetail.do" method="post" style="display: none"/>
	</c:if>
</body>
</html>