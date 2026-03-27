<%--
****************************************************************************
* DESC       : 理赔节点状态统计结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-28
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*"%>
<%
	String riskType = request.getParameter("riskType");
	//System.out.println("---在claimStatusStatList.jsp里面也-----"+riskType);
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<app:css />
<title>
	<!-- 根据节点的类型显示标题 --> <!-- 预赔节点 --> <logic:equal name="statStatusDto" property="nodeType" value="prepa">
		<s:text name="menu.preIndemnity.computTask" />
	</logic:equal> <!-- 报案节点 --> <logic:equal name="statStatusDto" property="nodeType" value="regis">
		<s:text name="menu.regist.computTask" />
	</logic:equal> <%
 	if (riskType.equals("acci")) {
 %> <logic:equal name="statStatusDto" property="nodeType" value="compe">
		<s:text name="claimstatus.reviewTask" />
		<%--审核任务状态统计--%>
	</logic:equal> <%
 	} else {
 %> <!-- 实赔节点 --> <logic:equal name="statStatusDto" property="nodeType" value="compe">
		<s:text name="menu.realIndemnity.computTask" />
	</logic:equal> <%
 	}
 %> <!-- 立案节点 --> <logic:equal name="statStatusDto" property="nodeType" value="claim">
		<s:text name="menu.claim.computTask" />
	</logic:equal> <!-- 调度节点 --> <logic:equal name="statStatusDto" property="nodeType" value="sched">
		<s:text name="menu.attemper.computTask" />
	</logic:equal> <!-- 查勘节点 --> <logic:equal name="statStatusDto" property="nodeType" value="check">
		<s:text name="menu.survey.computTask" />
	</logic:equal> <!-- 结案节点 --> <logic:equal name="statStatusDto" property="nodeType" value="endca">
		<s:text name="menu.finishCase.computTask" />
	</logic:equal> <!-- 单证收集节点 --> <logic:equal name="statStatusDto" property="nodeType" value="certi">
		<s:text name="menu.certify.computTask" />
	</logic:equal> <!-- 定损节点 --> <logic:equal name="statStatusDto" property="nodeType" value="certa">
		<s:text name="menu.determinant.computTask" />
	</logic:equal> <!-- 核损节点 --> <logic:equal name="statStatusDto" property="nodeType" value="verif">
		<s:text name="menu.checkClaim.computTask" />
	</logic:equal>
</title>
<STYLE>
v\:* {
	BEHAVIOR: url(#default#VML)
}
</STYLE>
<script src="/claim/common/js/showpage.js"> </script>
<html:base />
</head>
<body>
	<%
		//得到节点状态列表
		ArrayList claimStatusList = (ArrayList) request
				.getAttribute("claimStatusList");
		//节点状态数量
		int claimStatusSize = claimStatusList.size();
		//System.out.println("-------size-------------jsp:::::" + claimStatusSize);
	%>
	<script language="JavaScript">  //案件总数
  var dSumData = 0;
  //临时变量
  var dCs = 0;
  //最大值
  var MaxData = 0;
  //数据列表
  var dataarray = new Array(2);
  //状态名称
  var temarray0 = new Array(<%=claimStatusSize%>);
  //每个状态数量
  var temarray1 = new Array(<%=claimStatusSize%>);
  //每个状态颜色
  var tempColer = new Array(<%=claimStatusSize%>);
<%for (int n = 0; n < claimStatusSize; n++) {
				PrpDcodeDto prpDcodeDto = (PrpDcodeDto) claimStatusList.get(n);
				String nodeStatusColor = "sysconst.ClaimStatus"
						+ prpDcodeDto.getCodeCode();%>
    temarray0[<%=prpDcodeDto.getCodeCode()%>] = '<%=prpDcodeDto.getCodeCName()%>';
    temarray1[<%=n%>] = 0;
    tempColer[<%=n%>] = '<%=AppConfig.get(nodeStatusColor)%>';
<%}%>
  //temarray0 = new Array('未分配','未处理','正处理','已处理','已提交','已回退','已撤消','回退处理');
  //temarray1 = new Array(0,0,0,0,0,0,0,0);
  //var tempColer = new Array("<%=AppConfig.get("sysconst.ClaimStatus0")%>","<%=AppConfig.get("sysconst.ClaimStatus1")%>","<%=AppConfig.get("sysconst.ClaimStatus2")%>","<%=AppConfig.get("sysconst.ClaimStatus3")%>","<%=AppConfig.get("sysconst.ClaimStatus4")%>","<%=AppConfig.get("sysconst.ClaimStatus5")%>","<%=AppConfig.get("sysconst.ClaimStatus6")%>","<%=AppConfig.get("sysconst.ClaimStatus7")%>
		");
	<%StatStatusDto statStatusDto = (StatStatusDto) request
					.getAttribute("statStatusDto");
			List list = (ArrayList) statStatusDto.getStatStatusList();
			for (int i = 0; i < list.size(); i++) {
				StatStatusDto statStatusDto1 = (StatStatusDto) list.get(i);%>
		temarray1[
	<%=statStatusDto1.getStatus()%>
		] =
	<%=statStatusDto1.getCount()%>
		;
	<%}%>
		dataarray[0] = temarray0;
		dataarray[1] = temarray1;

		/**
		 *@description 画横坐标
		 *@param       无
		 *@return      通过返回true,否则返回false
		 */
		function drawLinesX(m) {
			count = 0 //画横坐标
			var textPoint = 1
			var dcs = 1
			for ( var i = 0; i < dataarray[1].length; i++) {
				if (MaxData < dataarray[1][i])
					MaxData = dataarray[1][i];
				dSumData = dSumData + dataarray[1][i];
				//alert(":::1:" +dSumData);
			}
			for ( var i = 10; i >= 1; i--) {
				if (MaxData % i == 0) {
					dcs = i;
					break;
				}
			}

			for ( var i = 1; i <= dcs; i++) {
				textPoint = i * (MaxData / dcs)
				var py = 2400 - i * (2400 / dcs) + 300
				var strTo = m + " " + py
				var newLine = document
						.createElement("<v:line from='150 "+py+"' to='"+strTo+"' style='position:absolute;z-index:8'></v:line>")
				group2.insertBefore(newLine)
				var newStroke = document
						.createElement("<v:stroke color='#c0c0c0'>")
				newLine.insertBefore(newStroke)
				var newShape = document
						.createElement("<v:shape style='position:absolute;left:0;top:"
								+ (py - 50)
								+ ";WIDTH:1000px;HEIGHT:200px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
				group2.insertBefore(newShape)
				var newText = document
						.createElement("<v:textbox id='tx"+textPoint+"' inset='3pt,0pt,0pt,0pt' style='font-size:10pt;v-text-anchor:bottom-right-baseline'></v:textbox>")
				newShape.insertBefore(newText)
				newText.innerText = textPoint
				if (count % 2 == 0) {
					var newStroke = document
							.createElement("<v:stroke dashstyle='dot' color='black'/>")
					newLine.insertBefore(newStroke)
				}
				count++
			}
		}

		/**
		 *@description 画纵坐标
		 *@param       无
		 *@return      通过返回true,否则返回false
		 */
		function drawLinesY() {
			//画Y轴上的刻度
			var textPoint = "";
			var fColWidth = 4200 / (dataarray[0].length + 1);
			var iCols = dataarray[0].length + 1;
			for ( var i = 1; i <= iCols; i++) {
				if (i != iCols) {
					textPoint = dataarray[0][i - 1];
					//alert(textPoint);
				} else {
					textPoint = "";
				}
				var py = 170 + i * fColWidth;
				var newLine = document
						.createElement("<v:line from='"+py+" 2700' to='"+py+" 2750' style='position:absolute;z-index:8'></v:line>");
				group2.insertBefore(newLine);
				var newStroke = document
						.createElement("<v:stroke color='black'>");
				newLine.insertBefore(newStroke);
				var newShape = document
						.createElement("<v:shape style='position:absolute;left:"
								+ (py - 50)
								+ ";top:2750;WIDTH:400px;HEIGHT:150px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
				group2.insertBefore(newShape);
				var newText = document
						.createElement("<v:textbox id='ty"+textPoint+"' inset='-8pt,-2pt,0pt,0pt' style='font-size:11pt;v-text-anchor:top-right-baseline'></v:textbox>");
				//生成文本筐
				newShape.insertBefore(newText);
				newText.innerHTML = textPoint;
			}
		}

		/**
		 *@description 画柱装图
		 *@param       无
		 *@return      通过返回true,否则返回false
		 */
		function drawBar(v, t, color) {
			var textPoint = "";
			var fColWidth = 4200 / (dataarray[0].length + 1)
			var iCols = dataarray[0].length + 1
			if (dSumData == 0)
				dSumData = 1
			if (MaxData == 0)
				MaxData = 1
			dCs = 2400 / MaxData
			for ( var i = 0; i < iCols - 1; i++) {
				var textPoint = dataarray[1][i]
				var h = dCs * dataarray[1][i]
				var px = 2700 - h;
				var py = 170 + i * fColWidth + fColWidth / 4 * 3;
				var color1 = tempColer[i];
				var newShape = document
						.createElement("<v:rect style='position:absolute;left:"
								+ py
								+ ";top:"
								+ px
								+ ";WIDTH:"
								+ fColWidth
								/ 2
								+ "px;HEIGHT:"
								+ h
								+ "px;z-index:9;border-width:0' fillcolor='"
								+ color1
								+ "' title = '本项数值为："
								+ dataarray[1][i]
								+ " 总和为："
								+ dSumData
								+ " 百分比："
								+ Math
										.round(100 / (dSumData / dataarray[1][i]))
								+ "%'></v:rect>")
				var tempTextbox = document
						.createElement("<v:TextBox inset='8pt,-15pt,0pt,0pt' style='font-size:10.2pt;'></v:TextBox>");
				newShape.appendChild(tempTextbox);
				var temptext = document.createTextNode(dataarray[1][i]);
				tempTextbox.appendChild(temptext);
				group2.insertBefore(newShape);
			}
		}
		/**
		 *@description 画整个图形
		 *@param       无
		 *@return      通过返回true,否则返回false
		 */
		function drawBars() {
			drawLinesX(4500);
			//画横线
			drawLinesY();
			//画X轴的刻度
			drawBar(2.5, 1, "blue");
		}
	</script>
	<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<!-- 根据节点的类型显示内容 -->
				<!-- 预赔节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="prepa">
					<s:text name="menu.preIndemnity.computTask" />
				</logic:equal>
				<!-- 报案节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="regis">
					<s:text name="menu.regist.computTask" />
				</logic:equal>
				<!-- 实赔节点 -->
				<%
					if (riskType.equals("acci")) {
				%>
				<logic:equal name="statStatusDto" property="nodeType" value="compe">
					<s:text name="claimstatus.reviewTask" />
					<%--审核任务状态统计--%>
				</logic:equal>
				<%
					} else {
				%>
				<logic:equal name="statStatusDto" property="nodeType" value="compe">
					<s:text name="menu.realIndemnity.computTask" />
				</logic:equal>
				<%
					}
				%>
				<!-- 立案节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="claim">
					<s:text name="menu.claim.computTask" />
				</logic:equal>
				<!-- 调度节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="sched">
					<s:text name="menu.attemper.computTask" />
				</logic:equal>
				<!-- 查勘节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="check">
					<s:text name="menu.survey.computTask" />
				</logic:equal>
				<!-- 结案节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="endca">
					<s:text name="menu.finishCase.computTask" />
				</logic:equal>
				<!-- 单证收集节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="certi">
					<s:text name="menu.certify.computTask" />
				</logic:equal>
				<!-- 定损节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="certa">
					<s:text name="menu.determinant.computTask" />
				</logic:equal>
				<!-- 核损节点 -->
				<logic:equal name="statStatusDto" property="nodeType" value="verif">
					<s:text name="menu.checkClaim.computTask" />
				</logic:equal>
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="cdb.prpLCitemKind.quantity" />
			</td>
			<%--数量--%>
			<td class="centertitle">
				<s:text name="claimstatus.stateName" />
			</td>
			<%--状态名称--%>
			<td class="centertitle">
				<s:text name="db.prpLregist.operatorCode" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpGnode.nodeName" />
			</td>
			<%--节点名称--%>
		</tr>
		<%
			int index = 0;
		%>
		<logic:notEmpty name="statStatusDto" property="statStatusList">
			<logic:iterate id="prpLclaimStatus1" name="statStatusDto" property="statStatusList">
				<%
					if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
				%>
				<td align="center">
					<a
						href="/claim/wfLogQuery.do?nodeType=<bean:write name='prpLclaimStatus1' property='nodeType'/>&status=<bean:write name='prpLclaimStatus1' property='status'/>&statStartDate=<%=request.getParameter("statStartDate")%>&statEndDate=<%=request.getParameter("statEndDate")%>&type=<%=request.getParameter("type")%>">
						<bean:write name="prpLclaimStatus1" property="count" />
					</a>
				</td>
				<td align="center">
					<bean:write name="prpLclaimStatus1" property="statusName" />
					<input type=hidden value="<bean:write name='prpLclaimStatus1' property='status'/>">
				</td>
				<td align="center">
					<bean:write name="prpLclaimStatus1" property="userName" />
				</td>
				<td align="center">
					<bean:write name="prpLclaimStatus1" property="nodeTypeName" />
				</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
		<tr class="listtail">
			<td colspan="4">
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=index%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录--%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td>
				<div class="memo" style="width: 700; line-height: 23px">
					<br>
					<v:group ID="group2" style="WIDTH:700px;HEIGHT:400px" coordsize="4900,3500">
						<v:line from="200,100" to="200,2700" style="Z-INDEX:8;POSITION:absolute" strokeweight="1pt">
							<v:stroke StartArrow="classic" />
						</v:line>
						<v:line from="200,2700" to="4500,2700" style="Z-INDEX:8;POSITION:absolute" strokeweight="1pt">
							<v:stroke EndArrow="classic" />
						</v:line>
						<v:rect style="WIDTH:4600px;HEIGHT:2900px" coordsize="21600,21600" fillcolor="white" strokecolor="black">
							<v:shadow on="t" type="single" color="silver" offset="4pt,3pt"></v:shadow>
						</v:rect>
						<v:shape style="position:absolute;left:50;top:2650;WIDTH:1000px;HEIGHT:200px;z-index:8" coordsize="21600,21600" fillcolor="white">
							<v:textbox id="text1" inset="3pt,0pt,3pt,0pt" align="center" style="font-size:10pt;v-text-anchor:bottom-center-baseline">0</v:textbox>
						</v:shape>
					</v:group>
				</div>
				<br>
			</td>
		</tr>
	</table>
	<script language="javascript">		drawBars();
	</script>
</body>
<html>