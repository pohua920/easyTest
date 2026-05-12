<%--
****************************************************************************
* DESC       ：零件代码页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2005-04-06
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%-- 代码名称页面 --%>
<title><s:text name="title.registBeforeEdit.codeName" /></title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/regist/js/DAARegistEdit.js"></script>
<script src="${ctx }/pages/DAA/regist/js/DAAThirdCarLossEdit.js"></script>
<script language="javascript">
//根据损失部位的不同，显示不同的零件
  function loadInfo() {
  	var partCode = "${partCode}";
  	if (partCode == 1) {
  		spanQianBu.style.display = "";
  	} else if (partCode == 4) {
  		spanHouBu.style.display = "";
  	} else if (partCode == 2 || partCode == 3) {
  		spanZhongBu.style.display = "";
  	} else if (partCode == 5) {
  		spanLossItem.style.display = "";
  	}
  	return true;
  }
  </script>
</head>
<body onload="loadInfo();">
	<form name="fm">
		<table border="0" align="center" cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td class="formtitle">
					<s:text name="title.registBeforeEdit.codeList" />
				</td>
			</tr>
			<%-- 代码项目列表 --%>
			<tr>
				<td id="spanQianBu" style="display: none">
					<UL>
						<LI><IMG src="${ctx }/images/treeFoderclassOpen.gif"></IMG>
						<s:text name="regist.prpLregist.front" />
							<%-- 前部 --%>
							<UL>
								<LI><A href="javascript:getCompCode('1001-前保險杠');"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front1" /> </A>
								<%-- 前保险杠 --%>
								<LI><A href="javascript:getCompCode('1002-前杠骨架')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front2" /> </A>
								<%-- 前杠骨架 --%>
								<LI><A href="javascript:getCompCode('1003-前機蓋')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front3" /> </A>
								<%-- 前机盖 --%>
								<LI><A href="javascript:getCompCode('1004-前面板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front4" /> </A>
								<%-- 前面板 --%>
								<LI><A href="javascript:getCompCode('1005-前下橫梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front5" /> </A>
								<%-- 前下横梁 --%>
								<LI><A href="javascript:getCompCode('1006-左前葉子板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore1" /> </A>
								<%-- 左前叶子板 --%>
								<LI><A href="javascript:getCompCode('1007-右前葉子板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBefore1" /> </A>
								<%-- 右前叶子板 --%>
								<LI><A href="javascript:getCompCode('1008-左前內旋')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore2" /> </A>
								<%-- 左前内旋 --%>
								<LI><A href="javascript:getCompCode('1009-右前內旋')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBefore2" /> </A>
								<%-- 右前内旋 --%>
								<LI><A href="javascript:getCompCode('1010-左前縱梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore3" /> </A>
								<%-- 左前纵梁 --%>
								<LI><A href="javascript:getCompCode('1011-右前縱梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBefore3" /> </A>
								<%-- 右前纵梁 --%>
								<LI><A href="javascript:getCompCode('1012-前防火牆')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front6" /> </A>
								<%-- 前防火墙 --%>
								<LI><A href="javascript:getCompCode('1013-左前窗立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore4" /> </A>
								<%-- 左前窗立柱 --%>
								<LI><A href="javascript:getCompCode('1014-右前窗立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBeforet4" /> </A>
								<%-- 右前窗立柱 --%>
								<LI><A href="javascript:getCompCode('1015-左前門立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore5" /> </A>
								<%-- 左前门立柱 --%>
								<LI><A href="javascript:getCompCode('1016-右前門立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBefore5" /> </A>
								<%-- 右前门立柱 --%>
								<LI><A href="javascript:getCompCode('1017-左前門')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBefore6" /> </A>
								<%-- 左前门 --%>
								<LI><A href="javascript:getCompCode('1018-右前門')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBefore6" /> </A>
								<%-- 右前门 --%>
								<LI><A href="javascript:getCompCode('1019-前龍門架')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.front7" /> </A>
								<%-- 前龙门架 --%> <%--增加玻璃选项--%>
								<LI><A href="javascript:getCompCode('1020-玻璃')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.frontGlass" /> </A>
								<%-- 玻璃 --%>
							</UL>
					</UL>
				</td>
			</tr>
			<tr>
				<td id="spanZhongBu" style="display: none">
					<UL>
						<LI><IMG src="${ctx }/images/treeFoderclassOpen.gif"></IMG>
						<s:text name="regist.prpLregist.middle" />
							<%-- 中部 --%>
							<UL>
								<LI><A href="javascript:getCompCode('2001-中網')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle1" /> </A>
								<%-- 中网 --%>
								<LI><A href="javascript:getCompCode('2002-水箱框架')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle2" /> </A>
								<%-- 水箱框架 --%>
								<LI><A href="javascript:getCompCode('2003-車頂')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle3" /> </A>
								<%-- 车顶 --%>
								<LI><A href="javascript:getCompCode('2004-左中門立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle4" /> </A>
								<%-- 左中门立柱 --%>
								<LI><A href="javascript:getCompCode('2005-右中門立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle5" /> </A>
								<%-- 右中门立柱 --%>
								<LI><A href="javascript:getCompCode('2006-中門')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middle6" /></A>
								<%-- 中门 --%> <%--增加玻璃选项--%>
								<LI><A href="javascript:getCompCode('2007-玻璃')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.middleGlass" /> </A>
								<%-- 玻璃  --%>
							</UL>
					</UL>
				</td>
			</tr>
			<tr>
				<td id="spanHouBu" style="display: none">
					<UL>
						<LI><IMG src="${ctx }/images/treeFoderclassOpen.gif"></IMG>
						<s:text name="regist.prpLregist.back" />
							<%-- 後部 --%>
							<UL>
								<LI><A href="javascript:getCompCode('4001-左側下大邊')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack1" /> </A>
								<%-- 左侧下大边 --%>
								<LI><A href="javascript:getCompCode('4002-右側下大邊')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack1" /> </A>
								<%-- 右侧下大边 --%>
								<LI><A href="javascript:getCompCode('4003-車身底板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back1" /> </A>
								<%-- 车身底板 --%>
								<LI><A href="javascript:getCompCode('4004-左後门')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack2" /> </A>
								<%-- 左後门 --%>
								<LI><A href="javascript:getCompCode('4005-右後门')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack2" /> </A>
								<%-- 右後门 --%>
								<LI><A href="javascript:getCompCode('4006-左側車身')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack3" /> </A>
								<%-- 左侧车身 --%>
								<LI><A href="javascript:getCompCode('4007-右側車身')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack3" /> </A>
								<%-- 右侧车身 --%>
								<LI><A href="javascript:getCompCode('4008-左後葉子板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack4" /> </A>
								<%-- 左後叶子板 --%>
								<LI><A href="javascript:getCompCode('4009-右後葉子板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack4" /> </A>
								<%-- 右後叶子板 --%>
								<LI><A href="javascript:getCompCode('4010-左後立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack5" /> </A>
								<%-- 左後立柱 --%>
								<LI><A href="javascript:getCompCode('4011-右後立柱')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack5" /> </A>
								<%-- 右後立柱 --%>
								<LI><A href="javascript:getCompCode('4012-後圍板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back2" /> </A>
								<%-- 後围板 --%>
								<LI><A href="javascript:getCompCode('4013-後備板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back3" /> </A>
								<%-- 後备板 --%>
								<LI><A href="javascript:getCompCode('4014-後備箱蓋')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back4" /> </A>
								<%-- 後备箱盖 --%>
								<LI><A href="javascript:getCompCode('4015-後底板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back5" /> </A>
								<%-- 後底板 --%>
								<LI><A href="javascript:getCompCode('4016-左後縱梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack6" /> </A>
								<%-- 左後纵梁 --%>
								<LI><A href="javascript:getCompCode('4017-右後縱梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack6" /> </A>
								<%-- 右後纵梁 --%>
								<LI><A href="javascript:getCompCode('4018-車身大梁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back6" /> </A>
								<%-- 车身大梁 --%>
								<LI><A href="javascript:getCompCode('4019-後尾門')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back7" /> </A>
								<%-- 後尾门 --%>
								<LI><A href="javascript:getCompCode('4020-後保險杠')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back8" /> </A>
								<%-- 後保险杠 --%>
								<LI><A href="javascript:getCompCode('4021-後杠骨架')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back9" /> </A>
								<%-- 後杠骨架 --%>
								<LI><A href="javascript:getCompCode('4022-左大廂板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.leftBack7" /> </A>
								<%-- 左大厢板 --%>
								<LI><A href="javascript:getCompCode('4023-右大廂板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.rightBack7" /></A>
								<%-- 右大厢板 --%>
								<LI><A href="javascript:getCompCode('4024-大廂板')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.back10" /> </A>
								<%-- 大厢板 --%> <%--增加玻璃选项--%>
								<LI><A href="javascript:getCompCode('4025-玻璃')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.backGlass" /> </A>
								<%-- 玻璃 --%>
							</UL>
					</UL>
				</td>
			</tr>
			<tr>
				<td id="spanLossItem" style="display: none">
					<UL>
						<LI><IMG src="${ctx }/images/treeFoderclassOpen.gif"></IMG>
						<s:text name="regist.prpLregist.others" />
							<%-- 其它损失部位 --%>
							<UL>
								<LI><A href="javascript:getLossItemCode('5001-護欄')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other1" /> </A>
								<%-- 护栏 --%>
								<LI><A href="javascript:getLossItemCode('5002-電線杆')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other2" /> </A>
								<%-- 电线杆 --%>
								<LI><A href="javascript:getLossItemCode('5003-綠化帶')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other3" /> </A>
								<%-- 绿化带 --%>
								<LI><A href="javascript:getLossItemCode('5004-車上貨物')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other4" /> </A>
								<%-- 车上货物 --%>
								<LI><A href="javascript:getLossItemCode('5005-鐵門')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other5" /> </A>
								<%-- 铁门 --%>
								<LI><A href="javascript:getLossItemCode('5006-牆壁')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other6" /> </A>
								<%-- 墙壁 --%>
								<LI><A href="javascript:getLossItemCode('5007-房屋')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.other7" /> </A>
								<%-- 房屋 --%> <%--增加玻璃选项--%>
								<LI><A href="javascript:getLossItemCode('5008-玻璃')"><IMG border=0 src="${ctx }/images/treeNode.gif"></IMG> <s:text name="regist.prpLregist.otherGlass" /> </A>
								<%-- 玻璃 --%>
							</UL>
					</UL>
				</td>
			</tr>
			<input type="hidden" name="txtIndex" value="${strIndex}">
			<input type="hidden" name="txtPreviousFlag" value="${previousFlag }">
			<tr>
				<td align="center">
					<P>
						<inut type="button" onclick=window.close(); class=button value="关 闭">
					</P>
				</td>
			</tr>
		</table>
	</form>
</BODY>
</html>
