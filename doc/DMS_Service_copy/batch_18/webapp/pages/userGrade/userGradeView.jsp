<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
</head>
<body>
	<div id="viewTab" class="yui-navset">   
	    <ul class="yui-nav">
	    <li class="selected"><a href="#tab1"><em>岗位功能信息</em></a></li>
	    <li><a href="#tab2" onclick="javascript:showPowerIframe();"><em>人员权限设置</em></a></li>
	    </ul>
		<div class="yui-content">
		<s:form name="fom" action="" target="companyTreeRight">
	        <div id="tab1">
	        <table width="100%" class="fix_table">
			<tr>
				<td class="bgc_tt short">岗位中文名</td>
				<td class="long"><s:textfield name="saaGrade.gradeCName"
					cssClass='input_w w_15' readonly="true" /></td>
				<td class="bgc_tt short">岗位繁体名</td>
				<td class="long""><s:textfield name="saaGrade.gradeTName"
					cssClass='input_w w_15' readonly="true" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">岗位英文名</td>
				<td class="long"><s:textfield name="saaGrade.gradeEName"
					cssClass='input_w w_15' readonly="true" /></td>
				<td class="bgc_tt short">有效标志</td>
				<s:if test="${saaGrade.validStatus=='1'}">
					<td class="long"><s:textfield value="有效" cssClass='input_w w_15' readonly="true"/></td>
				</s:if>
				<s:if test="${saaGrade.validStatus=='0'}">
					<td class="long"><s:textfield value="无效" cssClass='input_w w_15' readonly="true"/></td>
				</s:if>
				</tr>
	</table>
	<table border="0" width="100%" cellspacing="1">
			<br/>
			<div id="viewTabIframe" class="yui-navset">   
			    <ul class="yui-nav">
			     <c:set var="index" value="0" />
			    <s:iterator value="systemTasks" status="stuts">
			    <s:if test="${index+1==1}">
			    <li class="selected"><a href="#tabif${index+1 }"><em><s:property value="%{systemTasks[#stuts.index].taskCName}" /></em></a></li>
			        <c:set var="index" value="${index+1 }" />
			    </s:if>
			    <s:else>
			    <li><a href="#tabif${index+1 }" onclick="javascript:showGradeTaskIframe('tabtaskIframe${index+1 }','<s:property value="%{systemTasks[#stuts.index].taskCode}" />')";><em><s:property value="%{systemTasks[#stuts.index].taskCName}" /></em></a></li>
			        <c:set var="index" value="${index+1 }" />
			    </s:else>
			    </s:iterator> 
			    </ul>
				<div class="yui-content">
			        <c:set var="indexi" value="0" /> 
				     <s:iterator value="systemTasks" status="stuts">
				        <div id="tabif${indexi+1 }">
				        <s:if test="${indexi+1==1}">
				        	<iframe id="tabtaskIframe${indexi+1 }" src="${ctx}/saaUserGrade/preGradeTaskCodeBySys.do?rootTaskCode=<s:property value="%{systemTasks[#stuts.index].taskCode}" />&saaGradeID=${saaGrade.id}" frameborder="0" width="100%" height="580"></iframe>
				   		</s:if>
				   		<s:else>
				        <iframe id="tabtaskIframe${indexi+1 }" src="#" frameborder="0" width="100%" height="580"></iframe>
				   		</s:else>
				        	<s:property value="%{systemTasks[#stuts.index].taskCName}" />
				        </div>  
				        <c:set var="indexi" value="${indexi+1 }" /> 
				     </s:iterator>
			    </div>
			    </div>
	</table>
				
	        </div> 
	        </s:form>
	        <div id="tab2">
	        <iframe id="userGradePowerIframe" src="#" frameborder="0" width="100%" height="580"></iframe>
	        </div> 
	    </div>  
	</div>  
<%@include file="/common/meta_js.jsp"%>
</body>
</html>
<script language="javascript">
var tabView = new YAHOO.widget.TabView('viewTab');
var tabViewIframe = new YAHOO.widget.TabView('viewTabIframe');
var tabFlag = new Array();
tabFlag.push("tabtaskIframe1");
var powerIframeFlag="";
function showGradeTaskIframe(tab,rootCode){
	var tabNum=0;
	for(var i=0;i<tabFlag.length;i++){
		if(tabFlag[i]==tab){
			tabNum+=1;
		}
	}
	if(tabNum==0){
		document.getElementById(""+tab).src="${ctx}/saaUserGrade/preGradeTaskCodeBySys.do?rootTaskCode="+rootCode+"&saaGradeID=${saaGrade.id}";
		tabFlag.push(tab);
	}
} 
function showPowerIframe(){
	if(powerIframeFlag!="T"){
		document.getElementById("userGradePowerIframe").src="${ctx}/saaUserGrade/viewUserGradePower.do?userCode=${userCode}&saaGradeID=${saaGrade.id}";
		powerIframeFlag="T";
	}
}
</script>
