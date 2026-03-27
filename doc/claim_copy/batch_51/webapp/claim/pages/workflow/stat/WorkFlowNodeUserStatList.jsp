<%--
****************************************************************************
* DESC       : 理赔节点状态统计结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-28
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="java.util.*" %>

<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
    <app:css />


<title><s:text name="title.workflow.nodeStateInformationCount" />
	<%--节点状态信息统计 --%></title>

  <STYLE>
v\:* {
	BEHAVIOR: url(#default#VML)
}
  </STYLE>
  <script src="/claim/common/js/showpage.js"> </script>
  <html:base/>
</head>


<body  >
<%
  //从数据库中得到的统计数据
  StatStatusDto statStatusDto = (StatStatusDto)request.getAttribute("statStatusDto");
  List list = (ArrayList)statStatusDto.getStatStatusList();
  int nodeStatusSize = list.size();

%>
<script language="JavaScript">
<!--
  //案件总数
  var dSumData = 0;
  //临时变量
  var dCs = 0;
  //最大值
  var MaxData = 0;
  //数据列表
  var dataarray = new Array(2);
  //状态名称
  var temarray0 = new Array(<%= nodeStatusSize %>);
  //每个状态数量
  var temarray1 = new Array(<%= nodeStatusSize %>);
  //每个状态颜色
 var tempColer = new Array( <%= nodeStatusSize %> ); <% %>

  //temarray0 = new Array('未<br>分<br>配','未处理','正处理','已处理','已提交','已回退','已撤消','回退处理');
  //temarray1 = new Array(0,0,0,0,0,0,0,0);
  //var tempColer = new Array("<%= AppConfig.get("sysconst.ClaimStatus0") %>","<%= AppConfig.get("sysconst.ClaimStatus1") %>","<%= AppConfig.get("sysconst.ClaimStatus2") %>","<%= AppConfig.get("sysconst.ClaimStatus3") %>","<%= AppConfig.get("sysconst.ClaimStatus4") %>","<%= AppConfig.get("sysconst.ClaimStatus5") %>","<%= AppConfig.get("sysconst.ClaimStatus6") %>","<%= AppConfig.get("sysconst.ClaimStatus7") %>");
 <% String oldName1 = "";
  String nowName1="";
  String displayName="";
  int statusRow=0; //状态代码的指针

  int realCol=0;   //真正的长度
  String nodeStatusColor =""; //获取颜色

  for(int i=0 ;i<list.size();i++){
    StatStatusDto statStatusDto1 = (StatStatusDto)list.get(i);
    nowName1 = statStatusDto1.getNodeTypeName() ;

    displayName =nowName1;
     if (nowName1.equals(oldName1)) {
         displayName = "";
     } else {
    //首先判断是否是状态已经结束了是不是最後一个？


         //新增加一行做为空格使用的%>
    temarray0[<%= realCol %>] = "";
    temarray1[<%=realCol %>] = -100;
         tempColer[ <%= realCol %> ] = ""; <% //数组增加
    realCol++;

    }
    oldName1= nowName1;
    //判断状态指针的位置是否等於当前指针，如果不等於，则判断状态指针的最大值
   //获取当前的颜色
     nodeStatusColor = "sysconst.ClaimStatus" + statStatusDto1.getStatus(); %>
    temarray0[<%= realCol %>] = '<%=displayName %>';
    temarray1[<%=realCol %>] = <%= statStatusDto1.getCount() %>;
     tempColer[ <%= realCol %> ] = '<%=AppConfig.get(nodeStatusColor)%>'; <% realCol++;

 } %>
  //X坐标轴上的汉字
  dataarray[0] = temarray0;
  //直方图上面的数字。
  dataarray[1] = temarray1;

  /**
   *@description 画横坐标
   *@param       m 表示切在最大值上的切线的长度
   *@return      通过返回true,否则返回false
  */
 function drawLinesX(m) {
    count=0 //画横坐标
    var textPoint=1
    var dcs = 1
     for (var i = 0; i < dataarray[1].length; i++) {
      if(MaxData < dataarray[1][i]) MaxData = dataarray[1][i];
      dSumData = dSumData + dataarray[1][i];
    }
     for (var i = 10; i >= 1; i--) {
      if(MaxData % i == 0){
        dcs = i;
        break;
      }
    }

    for(var i=1;i<= dcs;i++){
      textPoint = i * (MaxData/dcs)
      var py=2400-i*(2400/dcs) + 300
      var strTo=m+" "+py
      var newLine = document.createElement("<v:line from='150 "+py+"' to='"+strTo+"' style='position:absolute;z-index:8'></v:line>")
      group2.insertBefore(newLine)
      var newStroke = document.createElement("<v:stroke color='#c0c0c0'>")
      newLine.insertBefore(newStroke)
      var newShape= document.createElement("<v:shape style='position:absolute;left:0;top:"+(py-50)+";WIDTH:1000px;HEIGHT:200px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
      group2.insertBefore(newShape)
      var newText = document.createElement("<v:textbox id='tx"+textPoint+"' inset='3pt,0pt,0pt,0pt' style='font-size:10pt;v-text-anchor:bottom-right-baseline'></v:textbox>")
      newShape.insertBefore(newText)
      newText.innerText = textPoint
        if(count % 2 == 0){
          var newStroke = document.createElement("<v:stroke dashstyle='dot' color='black'/>")
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
    var textPoint= "";
   //在X轴上的坐标 宽度
    var fColWidth = 4200 / (dataarray[0].length + 1);

    var iCols = dataarray[0].length + 1;
    for(var i=1;i<=iCols;i++){
      if(i != iCols){
        //横坐标上的中文名称，如 提交什么的
        textPoint = dataarray[0][i-1];
      } else {
        textPoint = "";
      }

      if (textPoint=="") continue;
      var py=200+ i * fColWidth ;
      var newLine = document.createElement("<v:line from='"+py+" 2700' to='"+py+" 2750' style='position:absolute;z-index:8'></v:line>");
      group2.insertBefore(newLine);
      var newStroke = document.createElement("<v:stroke color='black'>");
      newLine.insertBefore(newStroke);
      var newShape= document.createElement("<v:shape style='position:absolute;left:"+(py-50)+";top:2750;WIDTH:400px;HEIGHT:150px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
      group2.insertBefore(newShape);
      var newText = document.createElement("<v:textbox id='ty"+textPoint+"' inset='-8pt,-2pt,10pt,0pt' style='font-size:11pt;width:6.8pt;top:280.4pt;position:absolute' ></v:textbox>");
      //生成文本筐
      newShape.insertBefore(newText);
      newText.innerHTML= textPoint;
    }
  }

  /**
   *@description 画柱装图
   *@param       无
   *@return      通过返回true,否则返回false
  */
  function drawBar(v,t,color){
    var textPoint= "";
    //柱状图的宽度，如果缩小的话，变窄
    var fColWidth = 4200 / (dataarray[0].length + 1)


    var iCols = dataarray[0].length + 1
    if(dSumData == 0) dSumData = 1
    if(MaxData == 0) MaxData = 1
    dCs = 2400 / MaxData

    for(var i = 0; i < iCols - 1; i ++){
      var textPoint = dataarray[1][i]
      var h = dCs * dataarray[1][i]
      var px=2700 - h;
      var py=170 + i * fColWidth + fColWidth / 4 * 3;
      var color1=tempColer[i];
      //判断如果是不同的案件节点，要将中间空出一些地方来
      var realWidth=fColWidth;
      //插空格用的
         if (dataarray[1][i] == -100) {
          //插入竖线

          //插入空格图
          continue;

      }


      var newShape= document.createElement("<v:rect style='position:absolute;left:"+py+";top:"+px+";WIDTH:" + realWidth+ "px;HEIGHT:"+h+"px;z-index:9;border-width:0' fillcolor='" + color1 + "' title = '本项数值为：" + dataarray[1][i] + " 总和为：" + dSumData + " 百分比：" + Math.round(100/(dSumData/dataarray[1][i])) + "%'></v:rect>")
      var tempTextbox=document.createElement("<v:TextBox inset='8pt,-15pt,0pt,0pt' style='font-size:10.2pt;'></v:TextBox>");
      newShape.appendChild(tempTextbox);
      //用来在柱状图上标出数字的内容
      var temptext=document.createTextNode(dataarray[1][i]);
      tempTextbox.appendChild(temptext);
      group2.insertBefore(newShape);
    }
  }
  /**
   *@description 画整个图形
   *@param       无
   *@return      通过返回true,否则返回false
  */
  function drawBars(){
    drawLinesX(4500);
    //画横线
    drawLinesY();
    //画X轴的刻度
    drawBar(2.5,1,"blue");
  }
//-->
</script>

  <table border="0" align="center" cellpadding="5" cellspacing="0" class="common">
    <tr>
			<td align="left">
				<font color="#FF0080">&nbsp;<s:text name="workflow.countDate" />
					<%--统计日期 --%>:<bean:write name="statStatusDto" property="startDate" /> - <bean:write name="statStatusDto" property="endDate" />&nbsp;&nbsp; <!--<input type=button name=bgetNew class='button' value="刷新" style="align:center" onclick="cmdOK_onclick();">-->

      </td>
    </tr>
  </table>
  <table  class="common" cellpadding="5" cellspacing="1" >
     <tr>
			<td colspan=4 class="formtitle">
				<s:text name="workflow.nodeStateCount" />
				<%--节点状态统计 --%>
			<td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="db.prpGnode.nodeName" />
				<%--节点名称 --%>
			</td>
			<td class="centertitle">
				<s:text name="guarantee.operateName" />
				<%--操作员名称 --%>
			</td>
			<td class="centertitle">
				<s:text name="claimstatus.stateName" />
				<%--状态名称 --%>
			</td>
			<td class="centertitle">
				<s:text name="db.prpCmain_cargo.remark" />
				<%--数量 --%>
			</td>
     </tr>
		<%
			int index = 0;
		%>
		<%
			int rowindex = 0;
		%>
		<%
			int nodecount = 0; //判断有多少个节点
		%>
		<%
			String nowName = "";
		%>
		<%
			String oldName = "";
		%>
     <logic:notEmpty  name="statStatusDto"  property="statStatusList">
     <logic:iterate id="swfLogNode"  name="statStatusDto"  property="statStatusList">

<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");

%>
				<%
					StatStatusDto statStatusDto1 = (StatStatusDto) list.get(rowindex);
           nowName=statStatusDto1.getNodeTypeName();
         %>
				<%
					if (nowName.equals(oldName)) {
            %>
				<tr class=common>
					<td align="center"></td>
					<%
						} else {

            //新起一行
            nodecount++;
            out.print("</tr>");
            if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");

            %>

         <td align="center">
         <bean:write name="swfLogNode" property="nodeTypeName"/>
         </td>
					<%
						}
					%>
        <%
        oldName=nowName;
        %>
					<td align="center">
						<bean:write name="swfLogNode" property="userName" />
					</td>
					<td align="center">
						<bean:write name="swfLogNode" property="statusName" />
					</td>
					<td align="center">
						<bean:write name="swfLogNode" property="count" />
					</td>

      </tr>
				<%
					index++;
									rowindex++;
				%>
      </logic:iterate>
      </logic:notEmpty>
      <tr class="listtail">
	      <td colspan="5">
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=nodecount--%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录 --%>
	      </td>
      </tr>
    </table>
    </tr>
  </table>



</body>
<html>
