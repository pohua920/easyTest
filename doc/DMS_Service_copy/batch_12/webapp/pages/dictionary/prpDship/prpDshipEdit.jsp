<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>船舶代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.shipCode.focus()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDship.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDship.flag" id="flag" value="${prpDship.flag}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
     <div id="crash_menu">
<h2 align="center">增加船舶</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改船舶</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看船舶</h2>
</div>
      </s:if>
    </tr>
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">船舶代码</td>
        <td class="long"><s:textfield name="prpDship.shipCode" 
          id="shipCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">速查码</td>
        <td class="long"><s:textfield name="prpDship.shortHandCode" 
          id="shortHandCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船名(中文)</td>
        <td class="long"><s:textfield name="prpDship.shipCName" 
          id="shipCName" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>       
        <td class="bgc_tt short">船名(英文)</td>
        <td class="long"><s:textfield name="prpDship.shipEName" 
          id="shipEName" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">曾用名</td>
        <td class="long"><s:textfield name="prpDship.oldShipName" 
          id="oldShipName" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>       
        <td class="bgc_tt short">船东</td>
        <td class="long"><s:textfield name="prpDship.shipOwner" 
          id="shipOwner" cssClass='input_w w_15' maxlength="100" readonly="true"/></td>  
      </tr>
      <tr>   
        <td class="bgc_tt short">原船东</td>
        <td class="long"><s:textfield name="prpDship.oldShipOwner" 
          id="oldShipOwner" cssClass='input_w w_15' maxlength="100" readonly="true"/></td>
        <td class="bgc_tt short">经营者</td>
        <td class="long"><s:textfield name="prpDship.conveyManager" 
          id="conveyManager" cssClass='input_w w_15' maxlength="40" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">建造年月</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeYearMonth" 
          id="makeYearMonth" cssClass='input_w w_15 dt-date Wdate' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDship.makeYearMonth" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>			
			</td>  
        <td class="bgc_tt short">国家代码</td>
        <td class="long">
<!--		
        <s:textfield name="prpDship.countryCode" 
          id="countryCode" cssClass='input_w w_15' maxlength="20" readonly="true"/>
-->
		<ct:select name="prpDship.countryCode" value ="${prpDship.countryCode}" id="countryCode" sysCode="IMS" codeType="CountryCode" cssClass="input_w w_15" disabled="true"></ct:select>
		</td>
      </tr>
      <tr>
        <td class="bgc_tt short">制造厂家</td>
        <td class="long"><s:textfield name="prpDship.makeFactory" 
          id="makeFactory" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">建造起始日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeStartDate" 
          id="makeStartDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDship.makeStartDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>			
			 </td>
      </tr>
      <tr>      
        <td class="bgc_tt short">建造终止日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeEndDate" 
          id="makeEndDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDship.makeEndDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn3" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer3" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">船级</td>
        <td class="long"><s:textfield name="prpDship.stepHull" 
          id="stepHull" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">原船级</td>
        <td class="long"><s:textfield name="prpDship.oldStepHull" 
          id="oldStepHull" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">船旗</td>
        <td class="long"><s:textfield name="prpDship.shipFlag" 
          id="shipFlag" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">船舶种类代码</td>
        <td class="long">
<!--
        <s:textfield name="prpDship.shipTypeCode" 
          id="shipTypeCode" cssClass='input_w w_15' maxlength="20" readonly="true"/>
-->
			<ct:select name="prpDship.shipTypeCode"  value="${prpDship.shipTypeCode}"
          id="shipTypeCode" cssClass='input_w w_15' sysCode="IMS" codeType="ShipTypeCode" disabled="true"></ct:select>
		</td>
        <td class="bgc_tt short">船舶使用性质代码</td>
        <td class="long"><s:textfield name="prpDship.useNatureCode" 
          id="useNatureCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">船舶用途</td>
        <td class="long">
<!--        
        <s:textfield name="prpDship.shipUsage" 
          id="shipUsage" cssClass='input_w w_15' maxlength="20" readonly="true"/>
-->
		<ct:select name="prpDship.shipUsage" value="${prpDship.shipUsage}"
          id="shipUsage" cssClass='input_w w_15' sysCode="IMS" codeType="ShipUsage" disabled="true"></ct:select>
		</td>
        <td class="bgc_tt short">船质结构代码</td>
        <td class="long">
<!--
        <s:textfield name="prpDship.shipStruct" 
          id="shipStruct" cssClass='input_w w_15' maxlength="20" readonly="true"/>
 -->
		<ct:select name="prpDship.shipStruct" value="${prpDship.shipStruct}"
          id="shipStruct" cssClass='input_w w_15' sysCode="IMS" codeType="ShipStruct" disabled="true"></ct:select>
		</td>
      </tr>
      <tr>       
        <td class="bgc_tt short">注册地点</td>
        <td class="long"><s:textfield name="prpDship.registrySite" 
          id="registrySite" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">总吨位</td>
        <td class="long"><s:textfield name="prpDship.tonCount" 
          id="tonCount" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">净吨位</td>
        <td class="long"><s:textfield name="prpDship.netTonCount" 
          id="netTonCount" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">功率</td>
        <td class="long"><s:textfield name="prpDship.horsePower" 
          id="horsePower" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">功率单位</td>
        <td class="long"><s:textfield name="prpDship.powerUnit" 
          id="powerUnit" cssClass='input_w w_15' maxlength="4" readonly="true"/></td>
        <td class="bgc_tt short">客位</td>
        <td class="long"><s:textfield name="prpDship.seatCount" 
          id="seatCount" cssClass='input_w w_15' maxlength="5" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">载重吨</td>
        <td class="long"><s:textfield name="prpDship.loadTon" 
          id="loadTon" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">总长</td>
        <td class="long"><s:textfield name="prpDship.shipLength" 
          id="shipLength" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">型宽</td>
        <td class="long"><s:textfield name="prpDship.shipWidth" 
          id="shipWidth" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">型深</td>
        <td class="long"><s:textfield name="prpDship.shipDepth" 
          id="shipDepth" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船籍港</td>
        <td class="long"><s:textfield name="prpDship.shipPort" 
          id="shipPort" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">航行方式</td>
        <td class="long">
<!--       
        <s:textfield name="prpDship.sailModeCode" 
          id="sailModeCode" cssClass='input_w w_15' maxlength="20" readonly="true"/>
 -->
		<ct:select name="prpDship.sailModeCode" value="${prpDship.sailModeCode}"
          id="sailModeCode" cssClass='input_w w_15' sysCode="IMS" codeType="SailModeCode" disabled="true"></ct:select>
		</td>
      </tr>
      <tr>     
        <td class="bgc_tt short">船舶价值</td>
        <td class="long"><s:textfield name="prpDship.shipValue" 
          id="shipValue" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">币别</td>
        <td class="long"><s:textfield name="prpDship.currency" 
          id="currency" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">抵押权人</td>
        <td class="long"><s:textfield name="prpDship.mortgageName" 
          id="mortgageName" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">操作员代码</td>
        <td class="long"><s:textfield name="prpDship.operatorCode" 
          id="operatorCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">操作时间</td>
          <td class="long" nowrap="nowrap"><s:textfield name="prpDship.operateDTime" 
          id="operateDTime" cssClass='input_w w_15 dt-date Wdate' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDship.operateDTime" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn4" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer4" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">新的船舶代码</td>
        <td class="long"><s:textfield name="prpDship.newShipCode" 
          id="newShipCode" cssClass='input_w w_15  dc-chk dt-nzhs' maxlength="20" readonly="true"/></td>   
      </tr>
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDship.validDate" value="${prpDship.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDship.invalidDate" value="${prpDship.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
      <tr>    
       <td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDship.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
-->
		<ct:select name="prpDship.validStatus" value="${prpDship.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>  
        <td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDship.remark" 
          id="remark" cssClass='input_w w_15' maxlength="50" readonly="true"/></td>    
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
       <tr>
        <td class="bgc_tt short">船舶代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDship.shipCode" 
          id="shipCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="10" readonly="true"/></td>
        <td class="bgc_tt short">速查码</td>
        <td class="long"><s:textfield name="prpDship.shortHandCode" 
          id="shortHandCode" cssClass='input_w w_15' maxlength="20"/></td>    
      </tr>
      <tr>   
        <td class="bgc_tt short">船名(中文)</td>
        <td class="long"><s:textfield name="prpDship.shipCName" 
          id="shipCName" cssClass='input_w w_15' maxlength="120" /></td>       
        <td class="bgc_tt short">船名(英文)</td>
        <td class="long"><s:textfield name="prpDship.shipEName" 
          id="shipEName" cssClass='input_w w_15' maxlength="120" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">曾用名</td>
        <td class="long"><s:textfield name="prpDship.oldShipName" 
          id="oldShipName" cssClass='input_w w_15' maxlength="120" /></td>       
        <td class="bgc_tt short">船东</td>
        <td class="long"><s:textfield name="prpDship.shipOwner" 
          id="shipOwner" cssClass='input_w w_15' maxlength="100" /></td>  
      </tr>
      <tr>   
        <td class="bgc_tt short">原船东</td>
        <td class="long"><s:textfield name="prpDship.oldShipOwner" 
          id="oldShipOwner" cssClass='input_w w_15' maxlength="100" /></td>
        <td class="bgc_tt short">经营者</td>
        <td class="long"><s:textfield name="prpDship.conveyManager" 
          id="conveyManager" cssClass='input_w w_15' maxlength="40" /></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">建造年月</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeYearMonth" 
          id="makeYearMonth" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeYearMonth" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">国家代码</td>
        <td class="long">
<!--
        <s:textfield name="prpDship.countryCode" 
          id="countryCode" cssClass='input_w w_15' maxlength="3" />
-->
		<ct:select name="prpDship.countryCode" value="${prpDship.countryCode}" id="countryCode" sysCode="IMS" codeType="CountryCode" cssClass="input_w w_15"></ct:select>
		</td>
      </tr>
      <tr>
        <td class="bgc_tt short">制造厂家</td>
        <td class="long"><s:textfield name="prpDship.makeFactory" 
          id="makeFactory" cssClass='input_w w_15' maxlength="40" /></td>       
        <td class="bgc_tt short">建造起始日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeStartDate" 
          id="makeStartDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeStartDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span> 
--%>			
			</td>
      </tr>
      <tr>
        <td class="bgc_tt short">建造终止日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeEndDate" 
          id="makeEndDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeEndDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn3" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer3" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">船级</td>
        <td class="long"><s:textfield name="prpDship.stepHull" 
          id="stepHull" cssClass='input_w w_15' maxlength="20" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">原船级</td>
        <td class="long"><s:textfield name="prpDship.oldStepHull" 
          id="oldStepHull" cssClass='input_w w_15' maxlength="20" /></td>       
        <td class="bgc_tt short">船旗</td>
        <td class="long"><s:textfield name="prpDship.shipFlag" 
          id="shipFlag" cssClass='input_w w_15' maxlength="15" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">船舶种类代码</td>
        <td class="long">
<!--
        <s:textfield name="prpDship.shipTypeCode" 
          id="shipTypeCode" cssClass='input_w w_15' maxlength="3" />
-->
		<ct:select name="prpDship.shipTypeCode"  value="${prpDship.shipTypeCode}"
          id="shipTypeCode" cssClass='input_w w_15' sysCode="IMS" codeType="ShipTypeCode"></ct:select>
		</td>       
        <td class="bgc_tt short">船舶使用性质代码</td>
        <td class="long"><s:textfield name="prpDship.useNatureCode" 
          id="useNatureCode" cssClass='input_w w_15' maxlength="2" /></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">船舶用途</td>
        <td class="long">
<!--       
        <s:textfield name="prpDship.shipUsage" 
          id="shipUsage" cssClass='input_w w_15' maxlength="20" />
 -->
		<ct:select name="prpDship.shipUsage" value="${prpDship.shipUsage}"
          id="shipUsage" cssClass='input_w w_15' sysCode="IMS" codeType="ShipUsage"></ct:select>
		</td>
        <td class="bgc_tt short">船质结构代码</td>
        <td class="long">
<!--
        <s:textfield name="prpDship.shipStruct" 
          id="shipStruct" cssClass='input_w w_15' maxlength="2" />
-->
		<ct:select name="prpDship.shipStruct" value="${prpDship.shipStruct}"
          id="shipStruct" cssClass='input_w w_15' sysCode="IMS" codeType="ShipStruct"></ct:select>
		</td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">注册地点</td>
        <td class="long"><s:textfield name="prpDship.registrySite" 
          id="registrySite" cssClass='input_w w_15' maxlength="30" /></td>       
        <td class="bgc_tt short">总吨位</td>
        <td class="long"><s:textfield name="prpDship.tonCount" 
          id="tonCount" cssClass='input_w w_15 dt-num' 
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">净吨位</td>
        <td class="long"><s:textfield name="prpDship.netTonCount" 
          id="netTonCount" cssClass='input_w w_15 dt-num'
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');"/></td>       
        <td class="bgc_tt short">功率</td>
        <td class="long"><s:textfield name="prpDship.horsePower" 
          id="horsePower" cssClass='input_w w_15 dt-num' maxlength="7" /></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">功率单位</td>
        <td class="long"><s:textfield name="prpDship.powerUnit" 
          id="powerUnit" cssClass='input_w w_15' maxlength="4" /></td>
        <td class="bgc_tt short">客位</td>
        <td class="long"><s:textfield name="prpDship.seatCount" 
          id="seatCount" cssClass='input_w w_15 dt-num' maxlength="5" /></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">载重吨</td>
        <td class="long"><s:textfield name="prpDship.loadTon" 
          id="loadTon" cssClass='input_w w_15 dt-num'
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');"/></td>       
        <td class="bgc_tt short">总长</td>
        <td class="long"><s:textfield name="prpDship.shipLength" 
          id="shipLength" cssClass='input_w w_15 dt-num'
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">型宽</td>
        <td class="long"><s:textfield name="prpDship.shipWidth" 
          id="shipWidth" cssClass='input_w w_15 dt-num' 
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td>       
        <td class="bgc_tt short">型深</td>
        <td class="long"><s:textfield name="prpDship.shipDepth" 
          id="shipDepth" cssClass='input_w w_15 dt-num'
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船籍港</td>
        <td class="long"><s:textfield name="prpDship.shipPort" 
          id="shipPort" cssClass='input_w w_15' maxlength="20" /></td>
        <td class="bgc_tt short">航行方式</td>
        <td class="long">
<!--       
        <s:textfield name="prpDship.sailModeCode" 
          id="sailModeCode" cssClass='input_w w_15' maxlength="3" />
 -->
		<ct:select name="prpDship.sailModeCode" value="${prpDship.sailModeCode}"
          id="sailModeCode" cssClass='input_w w_15' sysCode="IMS" codeType="SailModeCode"></ct:select>
		</td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船舶价值</td>
        <td class="long"><s:textfield name="prpDship.shipValue" 
          id="shipValue" cssClass='input_w w_15 dt-num' maxlength="14" /></td>       
        <td class="bgc_tt short">币别</td>

        <td class="long">
<!--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDship.currency" id="currency" cssClass="selectui-input-up input_y w_p60" value="${prpDship.currency}" list="currencyMap" />
			 </div>

        <s:textfield name="prpDship.currency" 
          id="currency" cssClass='input_w w_15' maxlength="3" />
-->
		<ct:select name="prpDship.currency"  sysCode="IMS" codeType="Currency" cssClass="selectui-input-up input_y w_p60" value="${prpDship.currency}"></ct:select>
          </td>
      </tr>
      <tr>
        <td class="bgc_tt short">抵押权人</td>
        <td class="long"><s:textfield name="prpDship.mortgageName" 
          id="mortgageName" cssClass='input_w w_15' maxlength="40" /></td>       
        <td class="bgc_tt short">操作员代码</td>
        <td class="long"><s:textfield name="prpDship.operatorCode" 
          id="operatorCode" cssClass='input_w w_15' maxlength="10" /></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">操作时间</td>
        <td class="long" nowrap="nowrap">
			<s:textfield name="prpDship.operateDTime" 
          id="operateDTime" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.operateDTime" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn4" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer4" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">新的船舶代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDship.newShipCode" 
          id="newShipCode" cssClass='input_w w_15  dc-chk dt-nzhs' maxlength="10" /></td> 
      </tr>
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDship.validDate" value="${prpDship.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDship.invalidDate" value="${prpDship.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
      <tr>
       <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDship.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" />
-->
			<ct:select name="prpDship.validStatus" value="${prpDship.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
			<s:hidden name="prpDship.validStatus" id="validStatus" value="${prpDship.validStatus}"></s:hidden>
		</td>  
        <td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDship.remark" 
          id="remark" cssClass='input_w w_15' maxlength="40" /></td> 
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
      <tr>
        <td class="bgc_tt short">船舶代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDship.shipCode" 
          id="shipCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="10"/></td>
        <td class="bgc_tt short">速查码</td>
        <td class="long"><s:textfield name="prpDship.shortHandCode" 
          id="shortHandCode" cssClass='input_w w_15' maxlength="20"/></td>    
      </tr>
      <tr>   
        <td class="bgc_tt short">船名(中文)</td>
        <td class="long"><s:textfield name="prpDship.shipCName" 
          id="shipCName" cssClass='input_w w_15' maxlength="120" /></td>       
        <td class="bgc_tt short">船名(英文)</td>
        <td class="long"><s:textfield name="prpDship.shipEName" 
          id="shipEName" cssClass='input_w w_15' maxlength="120" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">曾用名</td>
        <td class="long"><s:textfield name="prpDship.oldShipName" 
          id="oldShipName" cssClass='input_w w_15' maxlength="120" /></td>       
        <td class="bgc_tt short">船东</td>
        <td class="long"><s:textfield name="prpDship.shipOwner" 
          id="shipOwner" cssClass='input_w w_15' maxlength="100" /></td>  
      </tr>
      <tr>   
        <td class="bgc_tt short">原船东</td>
        <td class="long"><s:textfield name="prpDship.oldShipOwner" 
          id="oldShipOwner" cssClass='input_w w_15' maxlength="100" /></td>
        <td class="bgc_tt short">经营者</td>
        <td class="long"><s:textfield name="prpDship.conveyManager" 
          id="conveyManager" cssClass='input_w w_15' maxlength="40" /></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">建造年月</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeYearMonth" 
          id="makeYearMonth" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeYearMonth" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">国家代码</td>

        <td class="long">
<!--
	        <s:textfield name="prpDship.countryCode" 
	          id="countryCode" cssClass='input_w w_15' maxlength="3" />
-->
			<ct:select name="prpDship.countryCode" value="prpDship.countryCode" id="countryCode" sysCode="IMS" codeType="CountryCode" cssClass="input_w w_15"></ct:select>
		</td>

      </tr>
      <tr>
        <td class="bgc_tt short">制造厂家</td>
        <td class="long"><s:textfield name="prpDship.makeFactory" 
          id="makeFactory" cssClass='input_w w_15' maxlength="40" /></td>       
        <td class="bgc_tt short">建造起始日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeStartDate" 
          id="makeStartDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeStartDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield> 
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span> 
--%>			
			</td>
      </tr>
      <tr>
        <td class="bgc_tt short">建造终止日期</td>
        <td class="long" nowrap="nowrap"><s:textfield name="prpDship.makeEndDate" 
          id="makeEndDate" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.makeEndDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn3" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer3" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">船级</td>
        <td class="long"><s:textfield name="prpDship.stepHull" 
          id="stepHull" cssClass='input_w w_15' maxlength="20" /></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">原船级</td>
        <td class="long"><s:textfield name="prpDship.oldStepHull" 
          id="oldStepHull" cssClass='input_w w_15' maxlength="20" /></td>       
        <td class="bgc_tt short">船旗</td>
        <td class="long"><s:textfield name="prpDship.shipFlag" 
          id="shipFlag" cssClass='input_w w_15' maxlength="15" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">船舶种类代码</td>
        <td class="long">
<!--       
        <s:textfield name="prpDship.shipTypeCode" 
          id="shipTypeCode" cssClass='input_w w_15' maxlength="3" />
 -->
		<ct:select name="prpDship.shipTypeCode"  value="${prpDship.shipTypeCode}"
          id="shipTypeCode" cssClass='input_w w_15' sysCode="IMS" codeType="ShipTypeCode"></ct:select>
		</td>
        <td class="bgc_tt short">船舶使用性质代码</td>
        <td class="long"><s:textfield name="prpDship.useNatureCode" 
          id="useNatureCode" cssClass='input_w w_15' maxlength="2" /></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">船舶用途</td>
        <td class="long">
<!--        
        <s:textfield name="prpDship.shipUsage" 
          id="shipUsage" cssClass='input_w w_15' maxlength="20" />
-->
		<ct:select name="prpDship.shipUsage" value="${prpDship.shipUsage}"
          id="shipUsage" cssClass='input_w w_15' sysCode="IMS" codeType="ShipUsage"></ct:select>
		</td>
        <td class="bgc_tt short">船质结构代码</td>
        <td class="long">
<!--       
        <s:textfield name="prpDship.shipStruct" 
          id="shipStruct" cssClass='input_w w_15' maxlength="2" />
 -->
		<ct:select name="prpDship.shipStruct" 
          id="shipStruct" cssClass='input_w w_15' sysCode="IMS" codeType="ShipStruct"></ct:select>
		</td> 
      </tr>
      <tr>
        <td class="bgc_tt short">注册地点</td>
        <td class="long"><s:textfield name="prpDship.registrySite" 
          id="registrySite" cssClass='input_w w_15' maxlength="30" /></td>       
        <td class="bgc_tt short">总吨位</td>
        <td class="long"><s:textfield name="prpDship.tonCount" 
          id="tonCount" cssClass='input_w w_15 dt-num' 
          	onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">净吨位</td>
        <td class="long"><s:textfield name="prpDship.netTonCount" 
          id="netTonCount" cssClass='input_w w_15 dt-num'
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');" /></td>       
        <td class="bgc_tt short">功率</td>
        <td class="long"><s:textfield name="prpDship.horsePower" 
          id="horsePower" cssClass='input_w w_15 dt-num' maxlength="7" /></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">功率单位</td>
        <td class="long"><s:textfield name="prpDship.powerUnit" 
          id="powerUnit" cssClass='input_w w_15' maxlength="4" /></td>
        <td class="bgc_tt short">客位</td>
        <td class="long"><s:textfield name="prpDship.seatCount" 
          id="seatCount" cssClass='input_w w_15 dt-num' maxlength="5" /></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">载重吨</td>
        <td class="long"><s:textfield name="prpDship.loadTon" 
          id="loadTon" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,9,2,'','');"/></td>       
        <td class="bgc_tt short">总长</td>
        <td class="long"><s:textfield name="prpDship.shipLength" 
          id="shipLength" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">型宽</td>
        <td class="long"><s:textfield name="prpDship.shipWidth" 
          id="shipWidth" cssClass='input_w w_15 dt-num' 
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td>       
        <td class="bgc_tt short">型深</td>
        <td class="long"><s:textfield name="prpDship.shipDepth" 
          id="shipDepth" cssClass='input_w w_15 dt-num' 
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,7,2,'','');"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船籍港</td>
        <td class="long"><s:textfield name="prpDship.shipPort" 
          id="shipPort" cssClass='input_w w_15' maxlength="20" /></td>
        <td class="bgc_tt short">航行方式</td>
        <td class="long">
<!--        
        <s:textfield name="prpDship.sailModeCode" 
          id="sailModeCode" cssClass='input_w w_15' maxlength="3" />
-->
		<ct:select name="prpDship.sailModeCode" value="${prpDship.sailModeCode}"
          id="sailModeCode" cssClass='input_w w_15' sysCode="IMS" codeType="SailModeCode"></ct:select>
		</td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">船舶价值</td>
        <td class="long"><s:textfield name="prpDship.shipValue" 
          id="shipValue" cssClass='input_w w_15 dt-num' maxlength="14" /></td>       
        <td class="bgc_tt short">币别</td>
		<td class="long">
<!--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDship.currency" id="currency" cssClass="selectui-input-up input_y w_p60" value="${prpDship.currency}" list="currencyMap" />
			 </div>
-->
			<ct:select name="prpDship.currency" sysCode="IMS" codeType="Currency" cssClass="selectui-input-up input_y w_p60" value="${prpDship.currency}"></ct:select>
		</td>
 
<!--
        <td class="long"><s:textfield name="prpDship.currency" 
          id="currency" cssClass='input_w w_15' maxlength="3" /></td>
-->

      </tr>
      <tr>
        <td class="bgc_tt short">抵押权人</td>
        <td class="long"><s:textfield name="prpDship.mortgageName" 
          id="mortgageName" cssClass='input_w w_15' maxlength="40" /></td>       
        <td class="bgc_tt short">操作员代码</td>
        <td class="long"><s:textfield name="prpDship.operatorCode" 
          id="operatorCode" cssClass='input_w w_15' maxlength="10" /></td> 
      </tr>
      <tr>
        <td class="bgc_tt short">操作时间</td>
        <td class="long" nowrap="nowrap">
        <s:textfield name="prpDship.operateDTime" 
          id="operateDTime" cssClass='input_w w_15 dt-date Wdate' maxlength="20" onfocus="WdatePicker()">
          	<s:param name="value"><s:date name="prpDship.operateDTime" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
<%--
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn4" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer4" style="position: absolute;"></div>
			</span>
--%>
		</td>
        <td class="bgc_tt short">新的船舶代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDship.newShipCode"
          id="newShipCode" cssClass='input_w w_15  dc-chk dt-nzhs' maxlength="10" /></td> 
      </tr>
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDship.validDate" value="${prpDship.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDship.invalidDate" value="${prpDship.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
      <tr>
       <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDship.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" />
-->
			<ct:select name="prpDship.validStatus"  id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1" disabled="true"></ct:select>
			<s:hidden name="prpDship.validStatus" id="validStatus" value="1"></s:hidden>
		</td>  
        <td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDship.remark" 
          id="remark" cssClass='input_w w_15' maxlength="40" /></td> 
      </tr>
    </s:elseif>
    
  </table>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
 		<c:if test="${editType=='view' }">
        <td>
        <button type="button" value=""
        onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--        <input type="button" value="修改" class="button_ty"-->
<!--        onclick="prepareUpdate()">-->
        </td>
      </c:if>

      <c:if test="${editType=='insert' }">
        <td>
        <button type="button" value="" 
        onclick="return addMethod()"><span><em>保存</em></span></button>
<!--        <input type="button" value="保存" class="button_ty"-->
<!--        onclick="return addMethod()">-->
        </td>
      </c:if>
      <c:if test="${editType=='update' }">
        <td>
        <button type="button" value=""
          onclick="updateMethod()"><span><em>保存</em></span></button>
<!--        <input type="button" value="保存" class="button_ty"-->
<!--          onclick="updateMethod()">-->
          </td>
      </c:if>
    </tr>
  </table>
</s:form>
		</div>
		</div>
</body>
</html>

<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript"
	src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
function updateMethod(){
    if(checkForm()){
	    if(checkLen()){
		    fm.action="${ctx}/dictionary/updatePrpDship.do";
		    //fm.target="＿self";
		    fm.submit();
	    }
    }
}

function addMethod(){
	if(checkForm()){
		 if(checkLen()){
			hasSameKey();
		}
	}
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
	var key1 = document.getElementById("shipCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDship.do?shipCode="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("shipCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDship&values=shipCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该船舶代码已存在！");
			return false;
		}else{
			fm.action="${ctx}/dictionary/insertPrpDship.do";
			fm.submit();
		}
	};
	var handleFailure = function(o){
		if(o.responseText !== undefined){
			var msg = i18n.errors.updatefail+"!\n"+ o.status +" " + o.statusText;
			alert(msg);
			return true;
		}
	};	
	var callback =
	{
	  success:handleSuccess,
	  failure:handleFailure
	};
	var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
}
function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
             event.keyCode = 0; 
       		 event.cancelBubble = true; 
             return false; 
            }
        }
//function init(){
//	initAllSelectUi();
/*****时间控件******/
//init_calendar("calContainer1","imgBtn1","makeYearMonth","");
//init_calendar("calContainer2","imgBtn2","makeStartDate","");
//init_calendar("calContainer3","imgBtn3","makeEndDate","");
//init_calendar("calContainer4","imgBtn4","operateDTime","");
//}
//YAHOO.util.Event.addListener(window,'load',init);
</script>



