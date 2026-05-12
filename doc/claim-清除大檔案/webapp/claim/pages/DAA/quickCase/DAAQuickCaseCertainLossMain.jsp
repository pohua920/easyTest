<%--
****************************************************************************
* DESC       ：简易赔案定损损失信息框架页面
* AUTHOR     ：zhaohui
* CREATEDATE ：2007-6-12
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<div id="con">
	<ul id="tags">
		<li class="selectTag"><a href="javascript:void(0)" onclick="selectTag('tagContent0',this)" title="涉案車輛損失情況"><s:text name="title.quickCase.involvedVehicleDamage" /></a></li>
		<!-- 涉案车辆损失情况 -->
		<li><a href="javascript:void(0)" onclick="selectTag('tagContent1',this)" title="車輛外的財產損失情況"><s:text name="title.quickCase.propertyDamageOutside" /></a></li>
		<!-- 车辆外的财产损失情况 -->
	</ul>
	<div id="tagContent">
		<div id="tagContent0" class="tagContent selectTag" style="width: 100%; height: 515px; background-color: #F7F7F7; overflow: scroll;">
			<%-- 1.2.1.涉案车辆损失情况 --%>
			<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossCar.jsp"%>
		</div>
		<div id="tagContent1" class="tagContent" style="width: 100%; height: 515px; background-color: #F7F7F7; overflow: scroll;">
			<%-- 1.2.2.车辆外的财产损失情况 --%>
			<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossProp.jsp"%>
		</div>
	</div>
</div>
