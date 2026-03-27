<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<html>
  <head>
    <title>test</title>
		<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
		<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
		<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
		<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
		<script language="javascript" src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
		<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
  </head>

  <body>
   		<table>
			<tr>
				<td class="bgc_tt short">动态查询</td>
				<td class="bgc_tt short">机构代码</td>
				<td class="long">
					<div id="comCodeAc" class="selectui-indiv">
						<div class="selectConfig">
							<div class="codeType">ComCode</div>
							<div class="type">inputLoad</div>
							<div class="forceSelection">1</div>
							<div class="inputHint">请选择机构代码</div>
						</div>
						<input type="text" class='selectui-input' value=""
							name="comCode" />
						<input type="hidden" name="prpDcompany.comCode"
							id="prpDcompany.comCode"
							value="${prpDcompany.comCode }" />
						<div id="comCode_Container" class="selectui-container"></div>
					</div>
				</td>
			</tr>
		</table>
  </body>
</html>

