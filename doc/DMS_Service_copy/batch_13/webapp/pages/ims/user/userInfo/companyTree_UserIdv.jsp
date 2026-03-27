<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="companyTreeRight">
<table>
	<tr align="left"><td align="left"><input type="button" name="openCloseAll" value="展开" onclick="openAndClose();" class="button_ty"></td>
	</tr>
</table>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		<script language="javascript">
		d = new dTree('d');
		d.add('0','-1','组织机构列表','','组织机构列表','','','','',false,false,true);
		<s:iterator value="comCodList" status="stuts">	
		 //每个节点的机构代码
		 var strComCode = '<s:property value="%{comCodList[#stuts.index].comCode}" />';
		 //每个节点的URL
		 var url = '/ims/utiIUser/prepareQueryUserIdv.do?comCode='+strComCode;
		 
		 //alert("strComCode:"+strComCode);
		 //alert("contextRootPath:"+contextRootPath);
		//alert("url::"+url);
	  	 d.add('<s:property value="%{comCodList[#stuts.index].comCode}" />','<s:property value="%{comCodList[#stuts.index].upperComCode}" />',
	  		  	 '<s:property value="%{comCodList[#stuts.index].comCName}" />',url,'','companyTreeRight','','','','','0',true,'0');
	    </s:iterator>
	   
	document.getElementById("gradeTrees").innerHTML = d;  
    function openAndClose(){
      if(fm.openCloseAll.value=="展开"){
    	  fm.openCloseAll.value = "合并";
          d.openAll();
      }else{
    	  fm.openCloseAll.value = "展开";
          d.closeAll();
      }
    }
	 </script>
	
</s:form>
</div>
</div>
</body>
</html>