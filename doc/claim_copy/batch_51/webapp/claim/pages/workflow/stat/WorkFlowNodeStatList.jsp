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

   //得到节点状态列表
		ArrayList claimStatusList = (ArrayList) request
				.getAttribute("claimStatusList");
  //节点状态数量
  int claimStatusSize = claimStatusList.size();

%>
<script language="JavaScript">
<!--
  //案件总数
  var dSumData = 0;
  //临时变量
  var dCs = 0;
  //最大值
  var MaxData = 0;
  //每个group填充数据列表
  var dataarray = new Array(2);

  //所有数据的列表
  var statarray = new Array(2);

  //新groupName的编号
  var newGroupID=0;

  //状态名称
  var temarray0 = new Array(<%= nodeStatusSize %>);
  //每个状态数量
  var temarray1 = new Array(<%= nodeStatusSize %>);
  //节点名称
  var temarray3 = new Array(<%= nodeStatusSize %>)
  //每个状态颜色
  var tempColer = new Array(<%= nodeStatusSize %>);


  //最後整理数据使用

  var statusArray=new Array(<%= claimStatusSize %>);
  //状态从code中的颜色
  var statusColerArray=new Array(<%= claimStatusSize %>);

  <%for (int n = 0; n < claimStatusSize; n++) {
    PrpDcodeDto prpDcodeDto = (PrpDcodeDto)claimStatusList.get(n);
				String nodeStatusColor = "sysconst.ClaimStatus"
						+ prpDcodeDto.getCodeCode();%>
    statusArray[<%= prpDcodeDto.getCodeCode() %>] = '<%= prpDcodeDto.getCodeCName() %>';
    statusColerArray[<%= n %>] = '<%= AppConfig.get(nodeStatusColor) %>';
<%}%>

  //temarray0 = new Array('未<br>分<br>配','未处理','正处理','已处理','已提交','已回退','已撤消','回退处理');
  //temarray1 = new Array(0,0,0,0,0,0,0,0);
  //var tempColer = new Array("<%= AppConfig.get("sysconst.ClaimStatus0") %>","<%= AppConfig.get("sysconst.ClaimStatus1") %>","<%= AppConfig.get("sysconst.ClaimStatus2") %>","<%= AppConfig.get("sysconst.ClaimStatus3") %>","<%= AppConfig.get("sysconst.ClaimStatus4") %>","<%= AppConfig.get("sysconst.ClaimStatus5") %>","<%= AppConfig.get("sysconst.ClaimStatus6") %>","<%= AppConfig.get("sysconst.ClaimStatus7") %>");
<%String oldName1 = "";
  String nowName1="";
  String displayName="";
  int statusRow=0; //状态代码的指针

  int realCol=0;   //真正的长度
  String nodeStatusColor =""; //获取颜色
  String nodeStatusName=""; //节点上的名称
  for(int i=0 ;i<list.size();i++){
    StatStatusDto statStatusDto1 = (StatStatusDto)list.get(i);
    nowName1 = statStatusDto1.getNodeTypeName() ;
    //nowStatus =Integer.parseInt(statStatusDto1.getStatus());
    nodeStatusName = statStatusDto1.getStatusName();
    displayName =nowName1;

				if (nowName1.equals(oldName1)) {
					displayName = "";
				} else {
    //首先判断是否是状态已经结束了是不是最後一个？


					//新增加一行做为空格使用的%>
    temarray0[<%= realCol %>] = '<%=statStatusDto1.getStatus()%>';
    temarray1[<%=realCol %>] = -100;
    tempColer[<%= realCol %>] = "";
    temarray3[<%= realCol %>] ='<%=nowName1%>';
    <%//数组增加
    realCol++;

    }
    oldName1= nowName1;
    //判断状态指针的位置是否等於当前指针，如果不等於，则判断状态指针的最大值
   //获取当前的颜色
				nodeStatusColor = "sysconst.ClaimStatus"
						+ statStatusDto1.getStatus();%>
    temarray0[<%= realCol %>] =  <%=statStatusDto1.getStatus()%>;
    temarray1[<%=realCol %>] = <%= statStatusDto1.getCount() %>;
    tempColer[<%= realCol %>] = '<%= AppConfig.get(nodeStatusColor) %>';
    temarray3[<%= realCol %>] ="";

<%realCol++;
			}%>
  //赋值


  //X坐标轴上的汉字
  //dataarray[0] = temarray0;
  statarray[0]= temarray0;
  //直方图上面的数字。
  //dataarray[1] = temarray1;
  statarray[1]= temarray1;


  /**
   *@description 画横坐标
   *@param       m 表示切在最大值上的切线的长度
   *@param       groupName 要画坐标的groupName的名称
   *@return      通过返回true,否则返回false
  */
  function drawLinesX(m, groupName) {
     count=0 //画横坐标
    var textPoint=1
    var dcs = 1
	    for (var i = 0; i < dataarray[1].length; i++) {
      if(MaxData < dataarray[1][i]) MaxData = dataarray[1][i];
      dSumData = dSumData + dataarray[1][i];
      //alert(":::1:" +dSumData);
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
      eval(groupName).insertBefore(newLine)
      var newStroke = document.createElement("<v:stroke color='#c0c0c0'>")
      newLine.insertBefore(newStroke)
      var newShape= document.createElement("<v:shape style='position:absolute;left:-200;top:"+(py-50)+";WIDTH:1000px;HEIGHT:200px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
      eval(groupName).insertBefore(newShape)
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
   *@param       groupName 要画坐标的groupName的名称
   *@return      通过返回true,否则返回false
  */
	function drawLinesY(groupName) {
   //画Y轴上的刻度
    var textPoint= "";
    var fColWidth = 4200 / (dataarray[0].length + 1);
    var iCols = dataarray[0].length + 1;
    for(var i=1;i<=iCols;i++){
      if(i != iCols){
        textPoint = dataarray[0][i-1];
        //alert(textPoint);
      } else {
        textPoint = "";
      }
      var py=170+ i * fColWidth ;
      var newLine = document.createElement("<v:line from='"+py+" 2700' to='"+py+" 2750' style='position:absolute;z-index:8'></v:line>");
      eval(groupName).insertBefore(newLine);
      var newStroke = document.createElement("<v:stroke color='black'>");
      newLine.insertBefore(newStroke);
      var newShape= document.createElement("<v:shape style='position:absolute;left:"+(py-250)+";top:2750;WIDTH:800px;HEIGHT:150px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>")
      eval(groupName).insertBefore(newShape);
      var newText = document.createElement("<v:textbox id='ty"+textPoint+"' inset='-8pt,-2pt,0pt,0pt' style='font-size:11pt;v-text-anchor:top-right-baseline'></v:textbox>");
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
  function drawBar(v,t,color,groupName){
     var textPoint= "";
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
      //var color1=tempColer[i];

      var color1=statusColerArray[i];

      var newShape= document.createElement("<v:rect style='position:absolute;left:"+py+";top:"+px+";WIDTH:" + fColWidth / 2 + "px;HEIGHT:"+h+"px;z-index:9;border-width:0' fillcolor='" + color1 + "' title = '本项数值为：" + dataarray[1][i] + " 总和为：" + dSumData + " 百分比：" + Math.round(100/(dSumData/dataarray[1][i])) + "%'></v:rect>")
      var tempTextbox=document.createElement("<v:TextBox inset='3pt,-15pt,3pt,0pt' style='font-size:10.2pt'></v:TextBox>");
      newShape.appendChild(tempTextbox);
      var temptext=document.createTextNode(dataarray[1][i]);
      tempTextbox.appendChild(temptext);
      eval(groupName).insertBefore(newShape);
    }

    MaxData=0;
    dSumData=0;
  }

  /**
   *@description 画整个内容图形
   *@param       groupName
   *@return      通过返回true,否则返回false
  */
  function drawBars(groupName){

    drawLinesX(4500,groupName);
    //画横线
    drawLinesY(groupName);
    //画X轴的刻度
    drawBar(2.5,1,"blue",groupName);
  }

    /**
   *@description 获取新增加的group的名称
   *@param       无
   *@return      返回group的名称
  */
  function getNewGroupName(){

    newGroupID=newGroupID+1;
    var newGroupName ="group"+newGroupID;
    return newGroupName;
  }

    /**注意，画group的过程里面必须没有" "这样的空格
   *@description 画group
   *@param       groupName
   *@return
  */
  function drawGroups(groupName,nodeName){
  document.writeln(nodeName);
  document.writeln("<br>");

  document.writeln("<v:group ID='"+groupName+"' style='WIDTH:500px;HEIGHT:300px' coordsize='4900,3500'></v:group>");
  var newLine = document.createElement("<v:line from='200,100' to='200,2700' style='Z-INDEX:8;POSITION:absolute' strokeweight='1pt'></v:line>")
  eval(groupName).insertBefore(newLine);
  var newStroke = document.createElement("<v:stroke StartArrow='classic'/>");
  newLine.insertBefore(newStroke );
  var newLine1 = document.createElement("<v:line from='200,2700' to='4500,2700' style='Z-INDEX:8;POSITION:absolute' strokeweight='1pt'></v:line>");
  eval(groupName).insertBefore(newLine1);
  var newStroke1 = document.createElement("<v:stroke EndArrow='classic'/>")
  newLine1.insertBefore(newStroke1);

  var newRect=document.createElement("<v:rect style='WIDTH:4600px;HEIGHT:2900px' coordsize='21600,21600' fillcolor='white' strokecolor='black'></v:rect>");
  eval(groupName).insertBefore(newRect)
  var newShadow=  document.createElement("<v:shadow on='t' type='single' color='silver' offset='4pt,3pt'></v:shadow>");
  newRect.insertBefore(newShadow);
  var newShape=document.createElement("<v:shape style='position:absolute;left:-200;top:2650;WIDTH:1000px;HEIGHT:200px;z-index:8' coordsize='21600,21600' fillcolor='white'></v:shape>");
  eval(groupName).insertBefore(newShape)
  var newText= document.createElement("<v:textbox id='text1' inset='3pt,0pt,3pt,0pt' align='center' style='font-size:10pt;v-text-anchor:bottom-center-baseline'></v:textbox>");
  newShape.insertBefore(newText);
  newText.innerHTML= '0';


  document.writeln("<br>");

  document.writeln("<br>");
  }


   /**
   *@description 画整个group和条形图的图形
   *@param       无
   *@return      通过返回true,否则返回false
  */
  function drawGraphs(){

   //根据整理好的数组内容画group的图形

   //重新给数组赋值

    var iCols = statarray[0].length
    var groupName="66666";
    var nodeName="";
   var j=0;
   var relCol=0;
   var temarrayNew0= new Array(<%= claimStatusSize %>);
   var temarrayNew1= new Array(<%= claimStatusSize %>);

   temarrayNew0 = statusArray;


    for(var i = 0; i < iCols; i ++){


	        if (statarray[1][i] == -100) {
         //插入新的group
         groupName = getNewGroupName();
         nodeName=temarray3[i]+"节点状况";
         drawGroups(groupName,nodeName);
         //计算距离下个-100的距离,並把中间的数值组织成一个新的数据
         relCol=0;

	            for (j > i; j < iCols; j++) {
          relCol++;
	                if (statarray[1][i + relCol] == -100)
	                    break;
	                if ((i + relCol) == iCols)
	                    break;

         }
         relCol =relCol -1;



         //初试化
         //最後的整理
	            for (var n = 0; n < temarrayNew1.length; n++) {
         temarrayNew1[n]=0;
         }

	            for (j = 0; j < relCol; j++) {
             //设置数值
              temarrayNew1[statarray[0][i+j+1]]=statarray[1][i+j+1];
         }


         dataarray[0]=temarrayNew0;
         dataarray[1]=temarrayNew1;

         drawBars(groupName);

      }
    }

  }




//-->

	enter" cellpadding="0" cellspacing="0" class="common">
    <tr>
			<td align="left">
				<font color="#FF0080">&nbsp;<s:text name="workflow.countDate" />
					<%--统计日期 --%>:<bean:write name="statStatusDto" property="startDate" /> - <bean:write name="statStatusDto" property="endDate" />&nbsp;&nbsp; <!--<input type=button name=bgetNew class='button' value="刷新" style="align:center" onclick="cmdOK_onclick();">-->

      </td>
    </tr>
  </table>
  <table  class="common" cellpadding="5" cellspacing="1" >
		<tr>
			<td colspan=3 class="formtitle">
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
				<s:text name="claimstatus.stateName" />
				<%--状态名称 --%>
			</td>
			<td class="centertitle">
				<s:text name="db.prpCmain_cargo.remark" />
				<%--数量 --%>
			</td>
     </tr>
     <%int index=0;%>
     <%int rowindex=0;%>
     <%int nodecount=0; //判断有多少个节点%>
     <%String nowName="";%>
     <%String oldName="";%>

     <logic:notEmpty  name="statStatusDto"  property="statStatusList">
     <logic:iterate id="swfLogNode"  name="statStatusDto"  property="statStatusList">

				<%if (index % 2 == 0)
               out.print("<tr class=listodd>");
          else
				out.print("<tr class=listeven>");%>

        <%  StatStatusDto statStatusDto1 = (StatStatusDto)list.get(rowindex);
			nowName = statStatusDto1.getNodeTypeName();%>
				<%if (nowName.equals(oldName)) {%>
				<tr class=common>
					<td align="center"></td>
            <%}else{

            //新起一行
            nodecount++;
            out.print("</tr>");
            if(index %2== 0)
               out.print("<tr class=listodd>");
          else
					out.print("<tr class=listeven>");%>


         <td align="center">
         <bean:write name="swfLogNode" property="nodeTypeName"/>
         </td>

          <%}%>

					<%oldName = nowName;%>
					<td align="center">
						<bean:write name="swfLogNode" property="statusName" />
					</td>
					<td align="center">
						<bean:write name="swfLogNode" property="count" />
					</td>

      </tr>
<%        index++;
          rowindex++;%>
      </logic:iterate>
      </logic:notEmpty>
      <tr class="listtail">
	      <td colspan="4">
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=nodecount--%><s:text name="compensate.common2" />
				<%--条满足条件的记录 --%>
	      </td>
      </tr>
    </table>
    </tr>
  </table>
<br>
<table align="center" class=common>
  <tr>
    <td align="center">

      <script language = "javascript">
         <!--
          drawGraphs();
         //-->
     </script>

    </td>
  </tr>
</table>

</body>
<html>
