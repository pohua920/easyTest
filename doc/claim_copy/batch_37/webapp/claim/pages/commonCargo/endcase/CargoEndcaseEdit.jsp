<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
    <!--对title处理-->
    <title><s:text name="title.endcaseBeforeEdit.editEndcase"/></title> <%-- 结案登记 --%>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
  <!-- 标签页样式 -->
  <jsp:include page="/behaviors/MpcStyle.jsp" />
  <script src="${ctx}/pages/commonCargo/endcase/js/CargoEndcaseEdit.js"></script>
	<%@ include file="/common/meta_js.jsp"%>
  <script type="text/javascript">
  function showNotBackCount()
  {
    var NotBackCount = document.getElementsByName("prpNotBackCount");
    if(NotBackCount.length>0 && NotBackCount[0].value!="0"){
      alert("该案件还有"+NotBackCount[0].value+"份尚未收回的担保单证，请关注处理！");
    }
  }
  </script>
</head>
<s:if test="#parameters.editTypeOther[0]=='SHOWTASK'">
<body onload="showNotBackCount();initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';" >
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'">
<body onload="showNotBackCount();initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';" >
</s:elseif>
<s:else>
<body onload="showNotBackCount();initPage();initSet();oMPC.style.visibility='visible';" >
</s:else>
    <form name=fm action="${ctx}/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
		<DIV id="mainLayer" style="position:absolute;top:30px;left:2px;height:420px;z-index:1;">
				<mpc:container ID="oMPC" style="width:830px;height:420px;">
				 <!-- 1.1.报案基本信息页面 -->
					<mpc:page ID="tabMain" TABTITLE="基本信息" TABTEXT="基本信息">
				    <CENTER>
				      <DIV id ="page1" style="width:100%;height:420px;background-color:#F7F7F7;overflow:scroll;">
					      <!-- 1.结案主信息 -->
					      <%@include file="/pages/commonCargo/endcase/CargoEndcaseMainEdit.jsp" %>
					      <!-- 4.结案文本信息 -->
					      <%@include file="/pages/DAA/endcase/DAAEndcaseTextEdit.jsp"%>
					  </DIV>
					</CENTER>
					</mpc:page>
				</mpc:container>
		  </DIV>
		  <DIV id="buttonLayer" name="buttonLayer" style="position:absolute;top:450px;left:10px;z-index:1;">
			<TABLE class="common" align="center">
			  <TR>
			    <TD>
					<!-- 保存通用按钮 -->
					      <%@include file="/pages/DAA/endcase/DAAEndcaseSave.jsp"%>
			    </TD>
			  </TR>
			</TABLE>
			</DIV>
			<DIV id="buttonLayer" name="buttonLayer" align="right" style="position:absolute;top:2px;right:0px;z-index:1;">
			<TABLE cellpadding="0" cellspacing="0" border="0">
			  <TR>
			    <td>
			      <input type="button" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value'/>" class="bigbutton" onclick="openWinSave1();return false;"><%-- 赔案处理记录 --%>
			    </td>
                <td></td>
			  </TR>
			</TABLE>
		   </DIV>
     </form>
</body>
</html>