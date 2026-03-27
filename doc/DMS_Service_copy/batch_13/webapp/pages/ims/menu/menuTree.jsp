<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script type="text/javascript" src="${ctx }/pages/ims/menu/menuTree.js"></script>
</head>
<body id="all_title" style="overflow:auto;">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="menuTreeRight">
<s:hidden name="smcMenu.utiISvr.svrCode" id="svrCode" value="${smcMenu.utiISvr.svrCode}"/>

<s:hidden name="smcMenu.utiISvr.svrName" id="smcMenu.utiISvr.svrName" value="${smcMenu.utiISvr.svrName}"/>
<s:hidden name="menuId" id="menuId"/>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
	</table>
	<table align="center" class="fix_table">
		<tr>
			<td align="right"><input type='button' class="button_ty"
				name=buttonInsert value="增加菜单" onclick="return insertMethod()"></td>

			<td align="left"><input type='button' class="button_ty"
				name=buttonModify value="修改菜单" onclick="return modifyMethod()"></td>
		</tr>
		<tr>
			<td align="right"><input type='button' class="button_ty"
				name=buttonInsert value="查看菜单" onclick="return viewMethod()"></td>

			<td align="left"><input type='button' class="button_ty"
				name=buttonModify value="删除菜单" onclick="return delMethod()"></td>
		</tr>
	</table>
	<script language="javascript">
	
		d = new dTree('d');
		d.add('0','-1',document.getElementById("smcMenu.utiISvr.svrName").value,'','','','','','','true','0',true,'0');
	   <s:iterator value="smcMenuList" status="stuts">
  			d.add('<s:property value="%{smcMenuList[#stuts.index].menuID}" />','<s:property value="%{smcMenuList[#stuts.index].upperID}" />',
  			'<s:property value="%{smcMenuList[#stuts.index].menuCName}" />','','','menuTreeRight','','','','true','0',true,'0');	
  		</s:iterator>	
	   document.getElementById("gradeTrees").innerHTML = d; 
	 </script>
</s:form>
</div>
</div>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script language="javascript">
function delMethod(){
	var chooseflag = showRelateInfo();
	var svrCode = document.getElementById("svrCode").value;
	if(chooseflag){
	if(confirm("此操作将删除此菜单及下级菜单，确认要执行该操作？")){
		fm.action = '${ctx}/smcMenu/delMenu.do?menuID='+fm.menuId.value + '&svrCode=' + svrCode;
        fm.target="menuTreeRight";
        fm.submit();
        return true;
			}else{
				alert("操作已取消");
			}
    }
}


    
 </script>