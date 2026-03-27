<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">用户类型</h2>
</div>
<s:form name="fm" action="">
	<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	<table width="100%" class="fix_table">

		<tr>
			<td class="bgc_tt short" colspan="3">用户类型</td>
			<td class="long">
                     <div id="userTypeDiv" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="0" />
					        <ce:select name="userType" id="userType" cssClass="selectui-input input_w w_15	"  value="${checked}" list="userTypeMap" />
					    </div>
                    </td>

		</tr>

	</table>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td><input type="button" value="下一步" class="button_ty"
				onclick="return nextMethod()"></td>

		</tr>
	</table>
</s:form></div>
</div>
</body>
</html>
<script language="javascript">
	var tabView = new YAHOO.widget.TabView('tabdemo');
	var tabFlag = new Array();
	tabFlag.push("taskIframe1");

	function nextMethod(){
    	var usertype = document.getElementById("userType").value;
    	//liyu add start 20090917 业务员用户、合作伙伴用户、企业客户、个人客户分别暂时分别从工具库、中获取
    	if(usertype == '00'){
			alert("请选择用户类型！");
			return false;
        }
    	if(usertype == '<%=IConstants.USERTYPE_SALES%>' || usertype=='<%=IConstants.USERTYPE_PARTNERS%>' || usertype=='<%=IConstants.USERTYPE_ENTERPRISE%>'|| usertype=='<%=IConstants.USERTYPE_PERSONAL%>'){
            alert("只能添加临时用户、虚拟用户、其它用户、员工用户！");	
            return false;
            //liyu add end
          }else if(usertype == '<%=IConstants.USERTYPE_STUFF%>'){
      			fm.action = "${ctx}/utiIUser/prepareHrUser.do";        	
          }else if(usertype=='<%=IConstants.USERTYPE_VIRTUAL%>' || usertype=='<%=IConstants.USERTYPE_PARTNERS%>' 
             || usertype=='<%=IConstants.USERTYPE_TEMPORARY%>' || usertype=='<%=IConstants.USERTYPE_OTHER%>'){
            
        	fm.action="${ctx}/utiIUser/prepareSelectUserSort.do";
        	
        }else if(usertype=='<%=IConstants.USERTYPE_SALES%>' || usertype=='<%=IConstants.USERTYPE_PERSONAL%>'){
        	fm.action="${ctx}/utiIUser/prepareInsertUser.do?userSort=<%=IConstants.USERSORT_PERSONAL%>";
        }else if(usertype=='<%=IConstants.USERTYPE_ENTERPRISE%>'){
        	fm.action="${ctx}/utiIUser/prepareInsertUser.do?userSort=<%=IConstants.USERSORT_Enterprise%>";
        }
			fm.submit();
			return true;
	}
</script>