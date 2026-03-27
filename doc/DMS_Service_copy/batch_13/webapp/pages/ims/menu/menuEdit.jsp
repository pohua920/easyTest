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
<h2 align="center">菜单配置</h2>
</div>
<form name="fm" action="" target="menuTreeRight">
<s:hidden name="smcMenu.utiISvr.svrName" id="smcMenu.utiISvr.svrName" value="${smcMenu.utiISvr.svrName}"/>
<s:hidden name="smcMenu.menuLevel" id="smcMenu.menuLevel" value="${smcMenu.menuLevel}"/>
<s:hidden name="editType" id="editType" value="${editType}"/>
<s:hidden name="taskCodes" ></s:hidden>
<s:hidden name="smcMenu.id" value="${smcMenu.id}"></s:hidden>
<s:hidden name="smcMenu.upperId" value="${smcMenu.upperId}" ></s:hidden>
	<table class="fix_table">	
	    <tr>
			<td class="bgc_tt short">系统代码</td>			
			<td class="long"><input type="text" name="smcMenu.utiISvr.svrCode"  class='input_w w_30' value="${smcMenu.utiISvr.svrCode}" readonly />
			
			</td>			
			<td class="bgc_tt short">菜单中文名称<font color="RED">*</font></td>
			<td class="long"><input type="text" name="smcMenu.menuCName"  id="smcMenu.menuCName"  maxlength="255"  class='input_w w_30 dc-chk' value="${smcMenu.menuCName }" />
			</td>
		</tr>		
		<tr>
			<td class="bgc_tt short">菜单繁体名称</td>			
			<td class="long"><input type="text" name="smcMenu.menuTName"  id="smcMenu.menuTName"  maxlength="255"  class='input_w w_30' value="${smcMenu.menuTName}" /></td>			
			<td class="bgc_tt short">菜单英文名称</td>
			<td class="long"><input type="text" name="smcMenu.menuEName"  id="smcMenu.menuEName"  maxlength="255"  class='input_w w_30' value="${smcMenu.menuEName }" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">任务代码<font color="RED">*</font></td>			
			<td class="long"  colspan='3'>
				${treeScript}
			</td>
				<s:hidden name="taskName" id="taskName"/>	
		</tr>
		<tr>
			<td class="bgc_tt short">执行URL</td>			
			<td class="long" colspan='3'>
			<s:textarea rows="4" cols="30" cssStyle="wwctrl dc-chk" id="url" name="smcMenu.actionURL" value="${smcMenu.actionURL}"></s:textarea>		
			</td>			
		</tr>
		<tr>
			<td class="bgc_tt short">目标</td>			
			<td class="long"><input type="text" name="smcMenu.target"  id="smcMenu.target"  maxlength="255"  class='input_w w_30' value="${smcMenu.target}"></td>			
			<td class="bgc_tt short">显示序号<font color="RED">*</font></td>
			<td class="long"><input type="text" name="smcMenu.displayNo"   maxlength="19"  id="smcMenu.displayNo"  class='input_w w_30 dc-chk dt-num' value="${smcMenu.displayNo }">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">菜单标题图片名</td>			
			<td class="long"><input type="text" name="smcMenu.image"  maxlength="255"  class='input_w w_30' value="${smcMenu.image}"></td>			
			<td class="bgc_tt short">展开菜单的图片名</td>
			<td class="long"><input type="text" name="smcMenu.imageExpand"  maxlength="255"  class='input_w w_30' value="${smcMenu.imageExpand }"></td>
		</tr>
		<tr>
			<td class="bgc_tt short">合上菜单的图片名</td>			
			<td class="long"><input type="text" name="smcMenu.imageCollapse"  maxlength="255"  class='input_w w_30' value="${smcMenu.imageCollapse}"></td>			
			<td class="bgc_tt short">展开菜单的图标</td>
			<td class="long"><input type="text" name="smcMenu.iconExpand"  maxlength="255"  class='input_w w_30' value="${smcMenu.iconExpand }"></td>
		</tr>
		<tr>
			<td class="bgc_tt short">合上菜单的图标</td>			
			<td class="long"><input type="text" name="smcMenu.iconCollapse"  maxlength="255"  class='input_w w_30' value="${smcMenu.iconCollapse}" ></td>		
			<td class="bgc_tt short">有效标志</td>
			<td class="long"><ce:select name="smcMenu.validInd"
												value="${smcMenu.validInd}"
												list="#{'1':'有效','0':'无效'}"
											 /></td>
					
					
		</tr>
	   <tr>
			<td class="bgc_tt short">备注</td>			
			<td class="long" colspan='3'>
			<s:textarea rows="4" cols="30" id="bz" cssStyle="wwctrl dc-chk" name="smcMenu.remark" value="${smcMenu.remark}"></s:textarea>		
			</td>			
		</tr>
		
			
		<tr align="center">
            <c:if test="${editType=='insert' }">
				<td align="center"  colspan="4"><input type="button" value="保存" class="button_ty"
				onclick="return insertMenu()"></td>
            </c:if>
            <c:if test="${editType=='update' }">
                <td align="center"  colspan="4"><input type="button" value="保存" class="button_ty"
				onclick="return updateMenu()"></td>
            </c:if>
			
            
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
<script language="javascript">
function insertMenu() {
	if(YAHOO.quote.data.datacheck('fm') ){
		if(checkLen()){
			var taskCode = getCheckValue();
			if(taskCode == null){
				alert('请选择一个任务代码！');
				return false;
			}else{
				var bz = document.getElementById("bz").innerHTML;
				var url = document.getElementById("url").innerHTML;
				var i = 0;
				for(var j=0;j<bz.length;j++){
					 if(bz.charAt(j)<='\255') {   
						 i++;
					  }else {   
						  i=i+2;
					 }	
				}
				var k = 0;
				for(var m=0;m<url.length;m++){
					 if(url.charAt(m)<='\255') {   
						 k++;
					  }else {   
						  k=k+2;
					 }	
				}
				if(k > 255){
					alert('URL过长，请检查！');
					return false;	
				}else if(i > 255){
					alert('备注过长，请检查！');
					return false;
				}else{
					fm.action = '${ctx }/smcMenu/insertMenu.do';
					fm.taskCodes.value = taskCode;
					fm.target = "menuTreeRight";
  		    		fm.submit();
				}
			}
		}
	}else{
		alert(" 数据有误，请核实！");
	}
}

function updateMenu() {
	if(YAHOO.quote.data.datacheck('fm') ){
		if(checkLen()){
			var taskCode = getCheckValue();
			if(taskCode == null){
				alert('请选择一个任务代码！');
				return false;
			}else {
				fm.action = '${ctx }/smcMenu/updateMenu.do';
				fm.taskCodes.value = taskCode;
				fm.target = "menuTreeRight";
				fm.submit();
			}
		}
	}else{
		alert(" 这里是必输项，必须填写信息！");
	}
}
  



</script>