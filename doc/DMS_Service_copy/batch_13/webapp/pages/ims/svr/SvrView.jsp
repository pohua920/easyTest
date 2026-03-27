<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<title>服务查看</title>
<%@ include file="/common/meta_css.jsp"%>
<%
	String log = (String)request.getAttribute("loginMethods");
%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
	<h2 align="center">查看服务</h2>
</div>
<s:form name="fm" action="" method="post">
	<s:hidden name="flag" id="flag"></s:hidden>
	<s:hidden name="position" id="position" value="${position}"></s:hidden>
	<s:hidden name="type" id="type" value="${type}"></s:hidden>
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">服务代码</td>
			<td class="long"><input id="utiISvr.svrCode" name="utiISvr.svrCode" value="${utiISvr.svrCode}"
				class='input_w w_15' readonly="true"></td>

			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input id="utiISvr.svrName" name="utiISvr.svrName" value="${utiISvr.svrName}"
				class='input_w w_15' readonly="true"></td>
		</tr>

		<s:if test="${position=='2' }">
		<tr>
			<td class="bgc_tt short">省集中名称</td>
			<td class="long"><input  name="companyName" value="${companyName}"
					id=""companyName"" class="input_w w_15" maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">省集中服务代码</td>
			<td class="long"><input type="text"  readonly="true" name="utiISvr.svrCodeInCompany" 
					id="utiISvr.svrCodeInCompany" value="${utiISvr.svrCodeInCompany }" class='input_w w_15' maxlength="20" />
			</td>
		</tr>
		</s:if>
		<tr>
			<td class="bgc_tt short">服务IP</td>
			<td class="long"><input  name="utiISvr.svrIp" value="${utiISvr.svrIp }"
					id="utiISvr.svrIp" class="input_w w_15" maxlength="20" readonly="true"/></td>
			<td class="bgc_tt short">服务端口</td>
			<td class="long"><input type="text"  readonly="true" name="utiISvr.svrPort" 
					id="utiISvr.svrPort" value="${utiISvr.svrPort }" class='input_w w_15' maxlength="20" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务分类</td>
			<td class="long"><ce:select name="utiISvr.svrType" id="utiISvr.svrType" value="${utiISvr.svrType}" 
								list="#{'1':'数据库','2':'应用服务器','3':'应用系统'}" disabled="true"/></td>
			<td class="bgc_tt short">有效终止时间</td>
			<td class="long"><input type="text" name="utiISvr.validEndDate" id="utiISvr.validEndDate" value="${utiISvr.validEndDate}" 
								class='input_w w_15' readonly="readonly"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效状态</td>
			<td class="long"><ce:select id="utiISvr.validStatus" name="utiISvr.validStatus" value="${utiISvr.validStatus}" 
								list="#{'1':'有效','0':'无效'}" disabled="true"/></td>

			<td class="bgc_tt short">服务认证方式</td>
			<td class="long"><input type="text" id="utiISvr.svrLoginMethod" name="utiISvr.svrLoginMethod" value="<%=log %>"
								class='input_w w_15' readonly="true"/></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务创建人</td>
			<td class="long"><input type="text" id="creatorName" name="creatorName" value="${creatorName}" 
				class='input_w w_15' readonly="true"></td>

			<td class="bgc_tt short">服务创建时间</td>
			<td class="long"><input type="text" id="utiISvr.createDate" name="utiISvr.createDate" value="${utiISvr.createDate}" 
				class='input_w w_15' readonly="true"></td>
		</tr>
		<s:if test="${type=='3'}">
		<tr>
			<td class="bgc_tt short">最新更新修改人</td>
			<td class="long"><input type="text" id="updaterName" name="updaterName" value="${updaterName}" 
				class='input_w w_15' readonly="true"></td>

			<td class="bgc_tt short">最新更新修改时间</td>
			<td class="long"><input type="text" id="utiISvr.updateDate" name="utiISvr.updateDate" value="${utiISvr.updateDate}" 
				class='input_w w_15' readonly="true"></td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否允许平台管理权限</td>
			<td class="long">
				<ce:select name="utiISvr.manageRightStatus" value="${utiISvr.manageRightStatus }" list="#{'1':'允许','0':'不允许'}" disabled="true"></ce:select>
			</td>
			<td class="bgc_tt short">是否允许平台管理菜单</td>
			<td class="long">
				<ce:select name="utiISvr.manageMenuStatus" value="${utiISvr.manageMenuStatus }" list="#{'1':'允许','0':'不允许'}" disabled="true"></ce:select>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否允许平台管理账户</td>
			<td class="long">
				<ce:select name="utiISvr.manageLoginStatus" value="${utiISvr.manageLoginStatus }" list="#{'1':'允许','0':'不允许'}" disabled="true"></ce:select>
			</td>
			<td class="bgc_tt short">是否使用平台管理登录</td>
			<td class="long">
				<ce:select name="utiISvr.manageAccStatus" value="${utiISvr.manageAccStatus }" list="#{'1':'使用','0':'不使用'}" disabled="true"></ce:select>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否与账户同步</td>
			<td class="long">
				<ce:select name="utiISvr.accSyncStatus" value="${utiISvr.accSyncStatus }" list="#{'1':'同步','0':'不同步'}" disabled="true"></ce:select>
			</td>
			<td class="bgc_tt short">是否与账户信息同步</td>
			<td class="long">
				<ce:select name="utiISvr.accMsgSyncStatus" value="${utiISvr.accMsgSyncStatus }" list="#{'1':'同步','0':'不同步'}" disabled="true"></ce:select>
			</td>
		</tr>
		<tr>	
			<td class="bgc_tt short">是否使用账户登录</td>
			<td class="long" colspan="3">
				<ce:select name="utiISvr.accLoginStatus" value="${utiISvr.accLoginStatus }" list="#{'1':'使用','0':'不使用'}" disabled="true"></ce:select>
			</td>
		</tr>
        <tr>
       	<td class="bgc_tt short">系统对应工具库</td>
			<td class="long"  colspan="3">
				<c:set var="checked" value="${utiISvr.utilitySvrCode}" />
              
				<ce:select name="utiISvr.utilitySvrCode" id="utilitySvrCode" cssClass="input_y w_p30 dc-chk" value="${checked}" list="svrCodeMap" disabled="true" />
                                                                 （显示【请选择...】项表示没有系统对应工具库）</td>

         </tr>
		</s:if>
		<tr>
			<td colspan="4">
				<center>
					<input type="button" name="OK" class="button_ty" value="确定" onclick="javascript:window.location.href='${ctx}/utiISvr/prepareQuerySvr.do'"/>
				</center>
  			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" >
</script>