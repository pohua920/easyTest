<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String) session.getAttribute("deployCom");
%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
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
			src="${ctx}/pages/dictionary/prpDregulation/prpDregulationCheck.js"></script>
	</head>
	<body id="all_title" onload="initJsp()">
		<div id="wrapper">
			<div id="container">
				<div id="crash_menu">
					<h2 align="center">
						请输入查询条件
					</h2>
				</div>
				<s:form name="fm" action="queryPrpDTreatyReten"
					namespace="/dictionary" method="post">
					<s:hidden name="flag" id="flag"></s:hidden>
					<s:hidden id="editType"></s:hidden>
					<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
					<table class="fix_table">
						<tr>
								<!-- modify by duanfa 20110806 调整样式 span style="width: 23%" class="bgc_tt short">适用范围： -->
							<td class="bgc_tt short">
								适用范围：
							</td>
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt long" colspan="4">
								<select name="prpdRegulation.proviceCode" id="proviceCode"
									onchange="changeCitycode(this)" style="width: 150px;">
									${proinvceResult }
								</select>
								 <span
									id="cityCodeSlectSpanId"><select
										name="prpdRegulation.cityCode" id="cityCode"
										onchange="changeCountycode(this)" style="width: 150px;">
										<option value=''>
											请选择
										</option>
									</select> </span>
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
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt long" width="">
								<s:textfield name="prpdRegulation.validDate" id="validDate"
									cssClass="input_w w_15 dt-date dc-chk dt-nzhs Wdate Wdate"
									onfocus="WdatePicker()" maxlength="30" />
							</td>
							<td class="bgc_tt short"></td>
						</tr>
						<tr>
							<td class="bgc_tt short">
								文号：
							</td>
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt long">
								<input name="prpdRegulation.fileCode" id="uwYear"
									class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="4">
							</td>
							<td class="bgc_tt short">
								文件名称：
							</td>
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt long">
								<input name="prpdRegulation.fileName" id="uwYear"
									class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="4">
							</td>
							<td class="bgc_tt short">
								条例类型：
							</td>
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt long">
								<select id='regulationType' name="prpdRegulation.regulationType">
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
							</td>
							</td>
							<!-- modify by duanfa20110902 添加有效状态验证以及格式上的调整 -->
							<td class="bgc_tt ">
								有效状态：
							</td>
							<td class="bgc_tt long" style="">
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
							<td class="bgc_tt short"></td>
							<td class="bgc_tt short"></td>
							<td colspan="2" align="center" valign="baseline" nowrap
								class="table_bgc_tt">
								<button type="button"  value=""
									onclick='executeQuery(1, 10)'><span><em>查 询</em></span></button>
<!--								<input type="button" class="button_ty" value="查 询"-->
<!--									onclick='executeQuery(1, 10)'>-->
							</td>
							<td class="bgc_tt short"></td>
							<td class="bgc_tt short"></td>
							<!-- modify by duanfa 20110902 修改样式 -->
							<td class="bgc_tt short"></td>
							<td class="bgc_tt short"></td>
						</tr>
					</table>
				</s:form>
			</div>
			<form action="checkPassRegulation.do" method="post" >
				<div name="content_navigation" id="content_navigation" class="query"
					align="right"></div>
				<div id="content" class="sort"></div>
				<div name="content_navigation" id="content_navigation" class="query"
					align="right"></div>
					<br>
<br>
<br>
				<table width="60%" style=" margin-left:15%">
					<tr>
						<td valign="top">
							审核片语：
							<select id='commentSelect'  style="width: 86px;" onchange="changeComments(this,'comments')">
								<option value=''>
									请选择
								</option>
								<option value='通过'>
									通过
								</option>
								<option value='退回处理'>
									退回处理
								</option>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							审核意见：<textarea id="comments" name="comments" style="width: 186px;" ></textarea>
						</td>
						<td></td>
					</tr>
					<tr>
						<!-- modify by duanfa20110825 多条审批 -->
						<td >
						<button type="button" onclick="passAll(this.form,true)" value=""><span><em>审核通过</em></span></button>
<!--						<input type="button" onclick="passAll(this.form,true)" class="except button_ty" value="审核通过" />-->
						</td>
						<td></td>
						<td >
						<button type="button" onclick="rejectAll(this.form,true)" value=""><span><em>退回处理</em></span></button>
<!--						<input type="button" onclick="rejectAll(this.form,true)" class="except button_ty" value="退回处理" />-->
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>

