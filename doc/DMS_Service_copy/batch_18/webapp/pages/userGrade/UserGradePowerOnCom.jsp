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
</head>
<body id="all_title">

<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="userCode" id="userCode" value="${userCode}"></s:hidden>
<s:hidden name="saaGradeID" id="saaGradeID" value="${saaGradeID}"></s:hidden>
<s:hidden name="comCodes" ></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
        <tr>
					<td align="center">
						<input type="button" name="openCloseAll2" value="展开"
							onclick="openAndClose2();" class="button_ty">
					</td>
        </tr>
		
    </table>
<div id="comTrees" align="left"></div>
    <table width="100%" class="fix_table" border="0">
     
	  <tr>
		<td align=center>
			<input type="button" value="确定" class="button_ty"
			onclick="return updatePermitCom()">
		</td>
	  </tr>
    </table>
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
      if(fm.openCloseAll2.value=="展开"){
        fm.openCloseAll2.value = "合并";
        d2.openAll();
      }else{
        fm.openCloseAll2.value = "展开";
        d2.closeAll();
      }
    }  
 </script>
 <script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript">

function updatePermitCom() {
	var userCode = 	document.getElementById("userCode").value;
	var saaGradeID = document.getElementById("saaGradeID").value;
	Ims.checkExceptCom(userCode,saaGradeID,doAction);
}

function doAction(date){
	if(date == 'yes'){
		alert('已配置除外机构，不能同时配置允许机构！');
		return false;
	}else{
		fm.action = '${ctx }/saaUserGrade/updateUserPowerOnCom.do?';
		fm.submit();
	}
}
  
</script>