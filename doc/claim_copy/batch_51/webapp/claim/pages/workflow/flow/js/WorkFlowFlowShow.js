/*****************************************************************************
 * DESC       ：图形画工作流显示
 * AUTHOR     ：weishixin
 * CREATEDATE ： 2004-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
//---------------------------------------------------------------
//声明全局变量
//---------------------------------------------------------------
/**
 *@description 画节点
 *@param       节点名称
 *@param       节点X值
 *@param       节点Y值
 *@return      通过返回true,否则返回false
 */
function drawNode(nodeTitle, posX, posY, nodeStatus) {
	//var strElement="<v:rect title='"+nodeTitle+"' style='top:"+posX+";left:"+posY+"width:100;height:100'></v:rect>";
	//var posXA = parseInt(posX)+300;
	var strElement = "<v:rect style='position:relative;top:" + posY + ";left:" + posX + ";width:200;height:100;z-index:8;' fillcolor='yellow' strokeColor='blue'>" +
		"<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 10.2pt; LEFT: " + posX + "; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP: " + posY + "; HEIGHT: 25.687pt' inset = '2pt,5pt,2pt,5pt'>" + nodeTitle + "</v:TextBox>" +
		"</v:rect>";

	var newPoint = document.createElement(strElement);
	workflowGroup.insertBefore(newPoint);
	return true;
}


/**
 *@description 画路径
 *@param       路径起点
 *@param       路径终点
 *@return      通过返回true,否则返回false
 */
function drawPath(nodeTitle, posX, posY) {
	//var strElement="<v:rect title='"+nodeTitle+"' style='top:"+posX+";left:"+posY+"width:100;height:100'></v:rect>";
	//var newPoint = document.createElement(strElemnt);
	//workflowGroup.insertBefore(newPoint);
	return true;
}

/**
 *@description 弹出查看留言对话框
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQuery.do?registNo=" + fm.prpLregistRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}