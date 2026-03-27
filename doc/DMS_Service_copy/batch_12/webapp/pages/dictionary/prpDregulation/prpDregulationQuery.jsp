<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<%
	String deployCom = (String) session.getAttribute("deployCom");
%>
<html>
	<head>
		<title>政策条例查询</title>
		<%@ include file="/common/i18njs.jsp"%>
		<%@ include file="/common/meta_css.jsp"%>
		<%@ include file="/common/meta_js.jsp"%>
		<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
		<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
		<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
		<script type="text/javascript"
			src="${ctx}/pages/dictionary/prpDregulation/prpDregulation.js"></script>
		<script type="text/javascript"
			src="${ctx}/pages/dictionary/prpDregulation/prpDregulationQuery.js"></script>
	</head>
	<body id="all_title" onload="initJsp()">
		<div id="wrapper">
			<div id="container">
				<div id="crash_menu">
					<h2>请输入查询条件</h2>
				</div>
				<s:form name="fm" action="queryPrpDTreatyReten"
					namespace="/dictionary" method="post">
					<s:hidden name="flag" id="flag"></s:hidden>
					<s:hidden id="editType"></s:hidden>
					<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
					<table class="fix_table">
						<tr>
							<td class="bgc_tt short">
								适用范围：
							</td>
							<td>
								<select name="prpdRegulation.proviceCode" id="proviceCode"
									onchange="changeCitycode(this)" >
									${proinvceResult }
								</select>
							</td>
							<td>
								<span
									id="cityCodeSlectSpanId"><select
										name="prpdRegulation.cityCode" id="cityCode"
										onchange="changeCountycode(this)" style="width: 150px;">
										<option value=''>
											请选择
										</option>
									</select> </span>
							</td>
							<td>
								<span id="countyCodeSlectSpanId"><select
										name="prpdRegulation.countyCode" id="countyCode"
										style="width: 150px;">
										<option value=''>
											请选择
										</option>
									</select> </span>
							</td>
							<td class="bgc_tt short">
								实施时间：
							</td>
							<td class="long">
								<s:textfield name="prpdRegulation.validDate" id="validDate"
									cssClass="input_w w_15 dt-date dc-chk dt-nzhs Wdate Wdate" onfocus="WdatePicker()" maxlength="30" />
							</td>
						<td class="bgc_tt short"></td>
						<td class="bgc_tt short"></td>
						</tr>
						<tr>
							<td class="bgc_tt short">
								文号：
							</td>
							<td class="">
							<!--modify by duanfa 20110803 长度控制  -->
								<input name="prpdRegulation.fileCode"
									class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="10">
							</td>
							<td class="bgc_tt short">
								文件名称：
							</td>
							<td class="">
							<!--modify by duanfa 20110803 长度控制  -->
								<input name="prpdRegulation.fileName" 
									class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="30">
							</td>
							<td class="bgc_tt short">
								条例类型：
							</td>
							<td class="long">
								<select id='regulationType' name="prpdRegulation.regulationType" style="width: 94px;">
									<option value=''>
										全部
									</option>
									<option value='I'>
										工伤条例
									</option>
									<option value='B'>
										基本医疗
									</option>
								</select>
							</td><td class="bgc_tt ">
								有效状态：
							</td>
							<td style="">
								<select id='regulationValidStatus' name="prpdRegulation.validStatus" style="width: 94px;">
									<option value=''>
										全部
									</option>
									<option value='1'>
										有效
									</option>
									<option value='0'>
										无效
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<!-- td class="bgc_tt short"></td -->
							<!-- td class="bgc_tt short"></td -->
							<td colspan="4" align="center" valign="baseline">
							<button type="button"  value=""
									onclick='executeQuery(1, 10)'><span><em>查 询</em></span></button>&nbsp;&nbsp;&nbsp;
<!--								<input type="button" class="button_ty" value="查 询"-->
<!--									onclick='executeQuery(1, 10)'>&nbsp;&nbsp;&nbsp;-->
									
								<%
									//delete by duanfa 20110728 start 取消增加权限判断
									//if (SyncConstants.ComCode_Head.equals(deployCom)) {
								%>
								
								<%
									//}
									//delete by duanfa 20110728 end 
								%>
							</td>
							<td colspan="4" align="center" valign="baseline">
							<button type="button"  value=""
									onclick="addNewRegulation()"><span><em>增 加</em></span></button>
<!--								<input type="button" class="button_ty" value="增 加"-->
<!--									onclick="addNewRegulation()">-->
							</td>
							<!-- td class="bgc_tt short"></td -->
							<!-- td class="bgc_tt short"></td -->
							<!-- td class="bgc_tt short"></td -->
						</tr>
					</table>
				</s:form>
			</div>
			<div name="content_navigation" id="content_navigation" class="query" align="right"></div>
			<div id="content" class="sort"></div>
			<div name="content_navigation" id="content_navigation" class="query" align="right"></div>
		</div>
	</body>
</html>

