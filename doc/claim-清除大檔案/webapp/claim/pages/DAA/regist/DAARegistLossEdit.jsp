<div id="con">
	<ul id="tags">
		<li class="selectTag"><a href="javascript:void(0)" onclick="selectTag('tagContent0',this)"><s:text name="certainLoss.thirdCarLoss.prpLcheckDamageCar" /></a></li>
		<li><a href="javascript:void(0)" onclick="selectTag('tagContent1',this)"><s:text name="info.driver" /></a></li>
		<li><a href="javascript:void(0)" onclick="selectTag('tagContent2',this)"><s:text name="info.propertyLoss" /></a></li>
		<li><a href="javascript:void(0)" onclick="selectTag('tagContent3',this)"><s:text name="info.personTrack" /></a></li>
	</ul>
	<div id="tagContent">
		<div id="tagContent0" class="tagContent selectTag" style="width: 100%; height: auto; background-color: #F7F7F7; overflow: hidden;">
			<%-- 1.2.2.涉案车辆 --%>
			<%@include file="/pages/DAA/claim/DAAClaimThirdPartyEdit.jsp"%>
		</div>
		<div id="tagContent1" class="tagContent" style="width: 100%; height: auto; background-color: #F7F7F7; overflow: hidden;">
			<%-- 1.2.3.驾驶员信息 --%>
			<%@include file="/pages/DAA/claim/DAAClaimDriverEdit.jsp"%>
		</div>
		<div id="tagContent2" class="tagContent" style="width: 100%; height: auto; background-color: #F7F7F7; overflow: hidden;">
			<%-- 1.2.4.财产损失部位信息 --%>
			<%@include file="/pages/DAA/regist/DAARegistThirdPropEdit.jsp"%>
		</div>
		<div id="tagContent3" class="tagContent" style="width: 100%; height: auto; background-color: #F7F7F7; overflow: hidden;">
			<%-- 1.2.5.人伤跟踪信息 --%>
			<%@include file="/pages/DAA/claim/DAAClaimPersonTraceEdit.jsp"%>
		</div>
	</div>
</div>
