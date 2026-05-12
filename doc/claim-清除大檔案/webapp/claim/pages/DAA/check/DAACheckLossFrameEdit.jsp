<DIV id="mainLayer" style="position: absolute; top: 50px; left: 10px; height: 480px; z-index: 1;">
	<mpc:container ID="coMPC" style="width:830px;height:480px;">
		<mpc:page ID="tabMain" TABTITLE="涉案車輛" TABTEXT="涉案車輛">
			<CENTER>
				<DIV style="width: 830px; height: 450px; background-color: #F7F7F7; overflow: scroll;">
					<%-- 3.涉案车辆 --%>
					<%@include file="/DAA/claim/DAAClaimThirdPartyEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="駕駛員訊息" TABTEXT="駕駛員訊息">
			<CENTER>
				<DIV style="width: 830px; height: 450px; background-color: #F7F7F7; overflow: scroll;">
					<%--  财产损失部位信息 --%>
					<%@include file="/DAA/regist/DAARegistThirdPropEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="財產損失部位訊息" TABTEXT="財產損失部位訊息">
			<CENTER>
				<DIV style="width: 830px; height: 450px; background-color: #F7F7F7; overflow: scroll;">
					<%-- 4.1 人伤跟踪信息 --%>
					<%@ include file="/DAA/claim/DAAClaimPersonTraceEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="人傷跟蹤訊息" TABTEXT="人傷跟蹤訊息">
			<CENTER>
				<DIV style="width: 830px; height: 450px; background-color: #F7F7F7; overflow: scroll;">
					<%-- 5.驾驶员信息 --%>
					<%@include file="/DAA/claim/DAAClaimDriverEdit.jsp"%>
				</DIV>
			</CENTER>
		</mpc:page>
	</mpc:container>
</DIV>
