<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>菜单配置</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript" src="${ctx }/pages/ims/menu/menuEdit.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">菜单查看</h2>
</div>
<form name="fm" action="" target="menuTreeRight">
<s:hidden name="smcMenu.utiISvr.svrName" id="smcMenu.utiISvr.svrName" value="${smcMenu.utiISvr.svrName}"/>
<s:hidden name="smcMenu.menuLevel" id="smcMenu.menuLevel" value="${smcMenu.menuLevel}"/>
<s:hidden name="editType" id="editType" value="${editType}"/>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">菜单ID</td>			
			<td class="long"><input type="text" name="smcMenu.id"  class='input_w w_15' value="${smcMenu.id }" disabled></td>			
			<td class="bgc_tt short">上级菜单ID </td>
			<td class="long"><input type="text" name="smcMenu.upperId"  class='input_w w_15' id="smcMenu.upperId" value="${smcMenu.upperId }" disabled/></td>
		</tr>
	    <tr>
			<td class="bgc_tt short">系统代码</td>			
			<td class="long"><input type="text" name="smcMenu.utiISvr.svrCode"  class='input_w w_15' value="${smcMenu.utiISvr.svrCode}" disabled></td>			
			<td class="bgc_tt short">菜单中文名称</td>
			<td class="long"><input type="text" name="smcMenu.menuCName"  id="smcMenu.menuCName"  class='input_w w_15' value="${smcMenu.menuCName }" disabled>
			<img src="${ctx}/pages/image/imgMustInput.gif" /></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">菜单繁体名称</td>			
			<td class="long"><input type="text" name="smcMenu.menuTName"  id="smcMenu.menuTName"  class='input_w w_15' value="${smcMenu.menuTName}" disabled></td>			
			<td class="bgc_tt short">菜单英文名称</td>
			<td class="long"><input type="text" name="smcMenu.menuEName"  id="smcMenu.menuEName"  class='input_w w_15' value="${smcMenu.menuEName }" disabled></td>
		</tr>
		<tr>
			<td class="bgc_tt short">任务代码</td>			
			<td class="long"  colspan='3'><input name="smcMenu.taskCode"
				id="smcMenu.taskCode" class='input_w w_15'
				value="${smcMenu.taskCode}" disabled/>
				</td>
				
		</tr>
		<tr>
			<td class="bgc_tt short">执行URL</td>			
			<td class="long" colspan='3'>
			<s:textarea rows="4" cols="30" cssStyle="wwctrl" name="smcMenu.actionURL" value="${smcMenu.actionURL}" disabled="true"></s:textarea>		
			</td>			
		</tr>
		<tr>
			<td class="bgc_tt short">目标</td>			
			<td class="long"><input type="text" name="smcMenu.target"  id="smcMenu.target"  class='input_w w_15' value="${smcMenu.target}" disabled></td>			
			<td class="bgc_tt short">显示序号</td>
			<td class="long"><input type="text" name="smcMenu.displayNo"   id="smcMenu.displayNo"  class='input_w w_15' value="${smcMenu.displayNo }" disabled />
			<img src="${ctx}/pages/image/imgMustInput.gif" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">菜单标题图片名</td>			
			<td class="long"><input type="text" name="smcMenu.image"  class='input_w w_15' value="${smcMenu.image}" disabled></td>			
			<td class="bgc_tt short">展开菜单的图片名</td>
			<td class="long"><input type="text" name="smcMenu.imageExpand"  class='input_w w_15' value="${smcMenu.imageExpand }" disabled></td>
		</tr>
		<tr>
			<td class="bgc_tt short">合上菜单的图片名</td>			
			<td class="long"><input type="text" name="smcMenu.imageCollapse"  class='input_w w_15' value="${smcMenu.imageCollapse}" disabled></td>			
			<td class="bgc_tt short">展开菜单的图标</td>
			<td class="long"><input type="text" name="smcMenu.iconExpand"  class='input_w w_15' value="${smcMenu.iconExpand }" disabled></td>
		</tr>
		<tr>
			<td class="bgc_tt short">合上菜单的图标</td>			
			<td class="long"><input type="text" name="smcMenu.iconCollapse"  class='input_w w_15' value="${smcMenu.iconCollapse}" disabled></td>		
			<td class="bgc_tt short">有效标志</td>
			<td class="long"><ce:select name="smcMenu.validInd"
												value="${smcMenu.validInd}"
												list="#{'1':'有效','0':'无效'}" disabled="true"
											 /></td>
					
					
		</tr>
	   <tr>
			<td class="bgc_tt short">备注</td>			
			<td class="long" colspan='3'>
			<s:textarea rows="4" cols="30" cssStyle="wwctrl" name="smcMenu.remark" value="${smcMenu.remark}" disabled="true"></s:textarea>		
			</td>			
		</tr>
	</table>	
			
</form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
