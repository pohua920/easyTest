<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>

<script type="text/javascript">
		
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>

<table border="0" width="100%" cellspacing="1">
			<br/>
			<div id="viewTabIframe" class="yui-navset">   
			    <ul class="yui-nav">
			    <li class="selected"><a href="#tabif1"><em>允许机构</em></a></li>
			    <li><a href="#tabif2" onclick="javascript:showGradeTaskIframe('tabtaskIframe2')";><em>除外机构</em></a></li>
			    <li><a href="#tabif3" onclick="javascript:showGradeTaskIframeR('tabtaskIframe3')";><em>允许产品</em></a></li>
			    </ul>
				<div class="yui-content">
			         <div id="tabif1">
				        
				       <iframe id="tabtaskIframe1" src="/ims/saaUserGrade/prepareModifyPowerOnCom.do?userCode=<%=request.getParameter("userCode")%>&saaGradeID=<%=request.getParameter("saaGradeID")%>" frameborder="0" width="100%" height="580"></iframe>
				   		允许机构
				      </div>  
				         
				     
				     <div id="tabif2">
				        <iframe id="tabtaskIframe2" src="#" frameborder="0" width="100%" height="580"></iframe>
				   		
				        	除外机构
				        </div>  
				        <div id="tabif3">
				        <iframe id="tabtaskIframe3" src="#" frameborder="0" width="100%" height="580"></iframe>
				   		  允许产品
				        </div>  
				</div>
			    </div>
	</table>


</s:form>
</div>
</div>
</body>
</html>
<script language="javascript">

var tabViewIframe = new YAHOO.widget.TabView('viewTabIframe');
var tabFlag = new Array();
tabFlag.push("tabtaskIframe1");
var powerIframeFlag="";
function showGradeTaskIframe(tab){
	var tabNum=0;
	for(var i=0;i<tabFlag.length;i++){
		if(tabFlag[i]==tab){
			tabNum+=1;
		}
	}
	if(tabNum==0){
		document.getElementById(""+tab).src="${ctx}/saaUserGrade/prepareModifyPowerOnExceptCom.do?userCode=<%=request.getParameter("userCode")%>&saaGradeID=<%=request.getParameter("saaGradeID")%>";
		tabFlag.push(tab);
	}
} 
function showGradeTaskIframeR(tab){
	var tabNum=0;
	for(var i=0;i<tabFlag.length;i++){
		if(tabFlag[i]==tab){
			tabNum+=1;
		}
	}
	if(tabNum==0){
		document.getElementById(""+tab).src="${ctx}/saaUserGrade/prepareModifyPowerOnRisk.do?userCode=<%=request.getParameter("userCode")%>&saaGradeID=<%=request.getParameter("saaGradeID")%>";
		tabFlag.push(tab);
	}
} 
</script>
