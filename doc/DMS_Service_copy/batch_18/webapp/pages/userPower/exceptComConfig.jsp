<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<%
String UserCode=(String)session.getAttribute("UserCode");
String ComCode=(String)session.getAttribute("ComCode");
if(UserCode.equals("nomole")||ComCode.equals("nomole")||UserCode==null||ComCode==null){
	request.getRequestDispatcher("/login.jsp").forward(request, response);
    return; 
}
%>
<head>
	    <%@include file="/common/meta_css.jsp"%>
		<%@include file="/common/i18njs.jsp"%>
        <%@include file="/common/meta_js.jsp"%>
		<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
		<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
		<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
		<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
</head>
<body id="all_title">
<s:form name="fm" action="/saaUserPower/exceptComPowerGrant.do?userCode=${userCode}">
	<table align="center" class="fix_table">
		<tr>
					<td align="center">
					<button type="button" name="openCloseAll2" value=""
							onclick="openAndClose2();"><span><em>展开</em></span></button>
<!--						<input type="button" name="openCloseAll2" value="展开"-->
<!--							onclick="openAndClose2();" class="button_ty">-->
					</td>
        </tr>
	</table>
    <div id="comTrees" align="left"></div>
    
</s:form>
</body>
</html>
<script language="javascript">
		d2 = new dTree('d2');
		d2.add('0','-1','机构选择(机构后面的选择框表示是否包含所有下级机构)(按住SHIFT可同时选择下级)','','机构选择','','','','',false,false,true);
		<s:iterator value="utiIUserVOList" status="stuts">
 	  		d2.add('<s:property value="%{utiIUserVOList[#stuts.index].comCode}" />','<s:property value="%{utiIUserVOList[#stuts.index].upperComCode}" />','<s:property value="%{utiIUserVOList[#stuts.index].comCName}" />','','','','','','',true,'<s:property value="%{utiIUserVOList[#stuts.index].checked}" />',true,'',true,'<s:property value="%{utiIUserVOList[#stuts.index].incluSubChecked}" />');
  		</s:iterator>
	document.getElementById("comTrees").innerHTML = d2;
	function openAndClose2(){
      if(fm.openCloseAll2.value=="<em>展开</em>"){
        fm.openCloseAll2.value = "<em>合并</em>";
        d2.openAll();
      }else{
        fm.openCloseAll2.value = "<em>展开</em>";
        d2.closeAll();
      }
    }  
 </script>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>

