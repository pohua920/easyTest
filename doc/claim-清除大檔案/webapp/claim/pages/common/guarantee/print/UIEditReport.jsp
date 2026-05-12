<%--
****************************************************************************
* DESC       ：报表编辑页
* Author     : 国寿项目组
* CREATEDATE ：2007-10-08
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sinosoft.prpall.ui.model.*"%>
<jsp:directive.page import="com.sinosoft.prpall.dto.domain.UtiPrintPageDto;"/>
<%
  String riskName=(String)session.getValue("RiskName");
  String ProposalNo=request.getParameter("BizNo");
  System.err.println("UIEditReport.jsp=="+ProposalNo);
  String PrintType = request.getParameter("PrintType");
  String pageContent = "";
  String showOnly =request.getParameter("showOnly");
	UtiPrintPageDto utiPrintPageDto2  = new UtiPrintPageDto();
	UtiPrintPageFindByConditionsCommand command2 = new UtiPrintPageFindByConditionsCommand("BusinessNo = '" + ProposalNo + "' and printtype = '1' order by SeaialNo");
			try {
				ArrayList t = (ArrayList)command2.execute();
				StringBuffer buffer = new StringBuffer();
				for(int i=0;i<t.size();i++){
					utiPrintPageDto2 = (UtiPrintPageDto)t.get(i);
					buffer.append(utiPrintPageDto2.getPrintContext());
					//out.print(utiPrintPageDto2.getPrintContext().replaceAll("PolicyNo",bizNo).replaceAll("UnderWriteName",UnderWriteName));
				}
				pageContent = buffer.toString();
				if(t.size() ==0)
					pageContent="";

			} catch (Exception e) {
				throw e;
			}


%>


<html>
<head>
<title>担保列印编辑器</title>
<script src="/prpall/commonship/pub/UICommon.js"></script>
<link rel="STYLESHEET" type="text/css" href="edit.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
form {  margin-top: 0px; margin-right: 1px; margin-bottom: 0px; margin-left: 1px; font-family: "宋体", "黑体", "仿宋_GB2312", "System", "Fixedsys"; font-size: 12px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px}
div {  font-family: "宋体", "幼圆", "黑体", "仿宋_GB2312", "System", "Fixedsys"; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px}
h6 {  font-size: 9pt; font-weight: bolder; font-style: normal; text-decoration: blink; line-height: normal}
h5 {  font-size: 14px; font-weight: bolder; line-height: normal}
h4 {  font-size: 15px; font-weight: bolder; line-height: normal}
h3 {  font-size: 16px; font-weight: bolder; line-height: normal}
h2 {  font-size: 18px; font-weight: bolder; letter-spacing: -1px; line-height: normal}
h1 {  font-size: 20px; font-weight: bolder; letter-spacing: -1px}
pre {  font-size: 9pt; color: #999999; line-height: normal; font-family: "宋体", "幼圆", "黑体", 
"仿宋_GB2312", "System", "Fixedsys"}
body, td, p, li, input, select{
        font-family: "宋体", "黑体", "仿宋_GB2312", "System", "Fixedsys";
        color: #000044;
        font-size: 12px;
        margin-top: 1px;
        margin-right: 1px;
        margin-bottom: 1px;
        margin-left: 1px;
        list-style-type: square;
        line-height: 130%;

}
body {background-color: #fafaff}
.font_大字显示全高 {line-height: 130%}
.TABLE_真1边框 {BORDER-COLLAPSE: collapse;LETTER-SPACING: 2px }
</style>
</head>

<body bgcolor="menu" STYLE="margin:0pt;padding:0pt">
<form name="form1" method="post">

<table width="90%" height="3" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td></td>
  </tr>
</table>


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="100%">

      &nbsp; 
      <img src="image/qt.gif" alt="导入" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=load()  lANGUAGE="javascript"> 
      <select name="selectFont" onChange="format('fontname', selectFont.options[selectFont.selectedIndex].value);selectFont.options[0].selected = true;"  style="font-family: 宋体; font-size: 9pt" onMouseOver="window.status='选择选定文字的字体。';return true;" onMouseOut="window.status='';return true;">
        <option selected>选择字体</option>
        <option value="宋体">宋体</option>
        <option value="楷体_GB2312">楷体</option>
        <option value="新宋体">新宋体</option>
        <option value="黑体">黑体</option>
        <option value="隶书">隶书</option>
        <option value="幼圆">幼圆</option>
        <OPTION value="Andale Mono">Andale Mono</OPTION>
        <OPTION value=Arial>Arial</OPTION>
        <OPTION value="Arial Black">Arial Black</OPTION>
        <OPTION value="Book Antiqua">Book Antiqua</OPTION>
        <OPTION value="Century Gothic">Century Gothic</OPTION>
        <OPTION value="Comic Sans MS">Comic Sans MS</OPTION>
        <OPTION value="Courier New">Courier New</OPTION>
        <OPTION value=Georgia>Georgia</OPTION>
        <OPTION value=Impact>Impact</OPTION>
        <OPTION value=Tahoma>Tahoma</OPTION>
        <OPTION value="Times New Roman" >Times New Roman</OPTION>
        <OPTION value="Trebuchet MS">Trebuchet MS</OPTION>
        <OPTION value="Script MT Bold">Script MT Bold</OPTION>
        <OPTION value=Stencil>Stencil</OPTION>
        <OPTION value=Verdana>Verdana</OPTION>
        <OPTION value="Lucida Console">Lucida Console</OPTION>
      </select>
      <select language="javascript"  id="select2" title="字號大小" onChange="format('fontsize',this[this.selectedIndex].value);this[0].selected = true;" name="select" onMouseOver="window.status='选择选定文字的字号大小。';return true;" onMouseOut="window.status='';return true;">
        <option class="heading" selected>字号 
        <option value="7">一号 
        <option value="6">二号 
        <option value="5">三号 
        <option value="4">四号 
        <option value="3">五号 
        <option value="2">六号 
        <option value="1">七号</option>
      </select>
      <!--<img  src="image/fgcolor.gif" align="absmiddle" WIDTH="16" HEIGHT="16"  onclick="foreColor()" TITLE="字體顏色">&nbsp;&nbsp;<img  src="image/bgcolor.gif" align="absmiddle" WIDTH="16" HEIGHT="16"  onclick="BackColor()" TITLE="字體背景顏色">&nbsp; -->
      <img src="image/bold.gif" align="absmiddle" alt="粗体" onClick="format('bold', '')" style="cursor: hand;"> 
      <img src="image/italic.gif" align="absmiddle" alt="斜体" onClick="format('italic', '')" style="cursor: hand;"> 
      <img src="image/underline.gif" align="absmiddle" alt="下划线" onClick="format('underline', '')" style="cursor: hand;"> 
      <img src="image/sup.gif" align="absmiddle" border="0" alt="上标" onClick="format('superscript')" style="cursor: hand;"> 
      <img src="image/sub.gif" align="absmiddle" border="0" alt="下标" onClick="format('subscript')" style="cursor: hand;"> 
      &nbsp;&nbsp;<img src="image/clear.gif" align="absmiddle" border="0" alt="删除文字格式" onClick="format('RemoveFormat')" style="cursor: hand;"> 
      &nbsp; 
      <select name="select2" id="specialtype"  onchange="specialtype(this[this.selectedIndex].value);this.selectedIndex=0">
        <option selected>特殊格式</option>
        <option VALUE="SUP">上标</option>
        <option VALUE="SUB">下标</option>
        <option VALUE="DEL">删除线</option>
        <option VALUE="BLINK">闪烁</option>
        <option VALUE="BIG">增大字体</option>
        <option VALUE="SMALL">减小字体</option>
      </select>

      &nbsp; <img src="image/selectall.gif" alt="全部选择" width="18" height="18" border="0" align="absmiddle" style="cursor: hand;" onClick="format('selectall')"> 
      <img src="image/cut.gif"  align="absmiddle" onClick="format('cut')" style="cursor: hand;" alt="剪切"> 
      <img src="image/copy.gif"  align="absmiddle" onClick="format('copy')" style="cursor: hand;" alt="复制"> 
      <img src="image/paste.gif"  align="absmiddle" onClick="format('paste')" style="cursor: hand;" alt="粘贴"> 
      <img src="image/del.gif" align="absmiddle" border="0" alt="删除" onClick="format('DELETE')" style="cursor: hand;"> 
      <img src="image/undo.gif" align="absmiddle" border="0" alt="撤消" onClick="format('undo')" style="cursor: hand;"> 
      <img src="image/redo.gif" align="absmiddle" border="0" alt="恢复" onClick="format('redo')" style="cursor: hand;"> 
      <!--<img src="image/fly.gif" alt="飞行文字" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=fly()  lANGUAGE="javascript"> 
      <img src="image/move.gif" alt="移动文字" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=move()  lANGUAGE="javascript"> 
      <img src="image/glow.gif" alt="发光文字" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=glow()  lANGUAGE="javascript"> 
      <img src="image/shadow.gif" alt="阴影文字" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=shadow()  lANGUAGE="javascript"> 
      &nbsp; <font color="#339966">支持手工加入的UBB代码</font> -->

      <!--&nbsp; <img src="image/url.gif" align="absmiddle" border="0" alt="超级链接" onClick="format('createLink')" style="cursor: hand;"> 
      <img src="image/nourl.gif" align="absmiddle" border="0" alt="取消超级链接" onClick="format('unLink')" style="cursor: hand;"> 
      &nbsp; <img src="image/fieldset.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入栏目框" LANGUAGE="javascript" onclick="FIELDSET()"> 
      <img src="image/htm.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入网页" LANGUAGE="javascript" onclick="iframe()"> 
      <img src="image/table.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入表格" LANGUAGE="javascript" onclick="InsertTable()"> 
      --><img src="image/line.gif" align="absmiddle" alt="普通水平线" border="0" onClick="format('InsertHorizontalRule', '')"  style="cursor: hand;"> 
      <!--<img src="image/sline.gif" align="absmiddle" alt="特殊水平线" border="0" onClick="hr()"  style="cursor: hand;"> -->
      &nbsp;<img src="image/Aleft.gif" align="absmiddle" onClick="format('Justifyleft', '')" style="cursor: hand;" alt="左对齐"> 
      <img src="image/Acenter.gif" align="absmiddle" border="0" alt="居中" onClick="format('JustifyCenter', '')" style="cursor: hand;"> 
      <img src="image/Aright.gif" align="absmiddle" onClick="format('JustifyRight', '')" style="cursor: hand;" alt="右对齐"> 
      &nbsp;<img src="image/list.gif" align="absmiddle" border="0" alt="项目符号" onClick="format('InsertUnorderedList', '')" style="cursor: hand;"> 
      <img src="image/num.gif" align="absmiddle" alt="编号" border="0" onClick="format('insertorderedlist', '')" style="cursor: hand;"> 
      <img src="image/outdent.gif" align="absmiddle" onClick="format('Outdent', '')" style="cursor: hand;" alt="回退"> 
      <img src="image/indent.gif" align="absmiddle" border="0" alt="缩进" onClick="format('indent', '')" style="cursor: hand;">&nbsp;&nbsp; 
      <select name="select3" ID="formatSelect"  onchange="format('FormatBlock',this[this.selectedIndex].value);this.selectedIndex=0">
        <option selected>段落格式</option>
        <option VALUE="&lt;P&gt;">普通</option>
        <option VALUE="&lt;PRE&gt;">已编排格式</option>
        <option VALUE="&lt;H1&gt;">标题一</option>
        <option VALUE="&lt;H2&gt;">标题二</option>
        <option VALUE="&lt;H3&gt;">标题三</option>
        <option VALUE="&lt;H4&gt;">标题四</option>
        <option VALUE="&lt;H5&gt;">标题五</option>
        <option VALUE="&lt;H6&gt;">标题六</option>
        <option VALUE="&lt;H7&gt;">标题七</option>
      </select>
      &nbsp;&nbsp;<img  src="image/save.gif" alt="儲存-回写並儲存到資料庫" WIDTH="16" HEIGHT="16" align="absmiddle" style="cursor:hand;" onclick="save()"> 
      <!--暂存-将编辑区数据转入临时区，並未真正存盘-->
      <!--<input name="checkbox" type="checkbox" id="EditMode" title="查看HTML源代碼" onclick="setMode(this.checked)" >
      <font color="#FF6633">查看HTML源代码</font> </font> -->
			

      <!--&nbsp; 
      <select name="music" onChange="insertsmilie(this.options[this.selectedIndex].value)">
        <option value="">背景音乐</option>
        <option value="midi/md02.mid">命运</option>
        <option value="midi/md03.mid">太傻</option>
        <option value="midi/md04.mid">上海滩</option>
        <option value="midi/md05.mid">天鹅湖</option>
        <option value="midi/md06.mid">乱世佳人</option>
        <option value="midi/md07.mid">蓝色多瑙河</option>
        <option value="midi/md08.mid">鬥牛士之歌</option>
        <option value="midi/md09.mid">多爱你一天</option>
        <option value="midi/md10.mid">友谊地久天长</option>
        <option value="midi/md11.mid">威廉泰尔序曲</option>
        <option value="midi/md12.mid">月亮代表我的心</option>
        <option value="midi/md13.mid">让我们荡起双桨</option>
        <option value="midi/md14.mid">风中有朵雨做的云</option>
      </select>
      &nbsp; <img src="image/flash.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入FLASH" LANGUAGE="javascript" onclick="swf()"> 
      <img src="image/wmv.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入视频文件，支持格式为：avi、wmv、asf" LANGUAGE="javascript" onclick="wmv()"> 
      <img src="image/rm.gif" align="absmiddle" border="0" style="cursor:hand;" alt="插入RealPlay文件，支持格式为：rm、ra、ram" LANGUAGE="javascript" onclick="rm()"> 
      <img src="image/qt.gif" alt="QuickTime视频文件" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=Cmov()  lANGUAGE="javascript"> 
      <img src="image/csound.gif" alt="插入网上mid背景音乐,文件只能是*.mdi" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick=csound()  lANGUAGE="javascript"> 
      &nbsp; <img src="image/img.gif" alt="插入网上图片，支持格式为：gif、jpg、png、bmp" width="18" height="18" border="0" align="absmiddle" style="cursor:hand;" onclick="pic()" lANGUAGE="javascript">        
      <img src="image/help.gif" align="absmiddle" border="0" style="cursor:hand;" alt="使用帮助" lANGUAGE="javascript" onclick="help()"> 
      -->
      <font color="#339966">&nbsp;&nbsp;
      <table width="90%" height="3" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td id="tdMainRemarkTitle" class="title">注：
      
      
      
<%--        <Input name="button_MainRemark_Open_Context" class="button" type="button" value="录 入" alt="录入" src="/prpall/common/images/butInputCoins.gif"--%>
<%--          onclick="showSpan('span_MainRemark_Context');">--%>
<%--        <span id="span_MainRemark_Context" style='width:520;display:none;position:absolute;background-color:C0C0C0;'>--%>
<%--          <table class=sub>--%>
<%--            <tr>--%>
<%--              <td class=title>--%>
<%--                <textarea name="MainRemark" rows="3" cols="80" maxLength="200" description="备注"><%=pageContent%></textarea>--%>
<%--              </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--              <td colspan=2 align=center>--%>
<%--                <Input class="button" type="button" name="button_LadingNo_Close_Context" alt="确定" value="确 定" src="/prpall/common/images/butOk.gif"--%>
<%--                  onclick="checkLengthRemark();">--%>
<%--              </td>--%>
<%--            </tr>--%>
<%--          </table>--%>
<%--        </span>--%>
      </td>
      <td>
<%--        <textarea name="MainRemark_Show" rows="3" cols="80" maxLength="200" readonly description="编辑备注"><%=pageContent%></textarea>--%>
         编辑列印内容时一定要删除编辑和关闭按钮
      </td>
  </tr>
</table>
    </td>
  </tr>
</table>
<iframe class="HtmlEdit" ID="HtmlEdit" MARGINHEIGHT="1" MARGINWIDTH="1" width="100%" height="100%" > 
</iframe>
	<div><textarea name="ta1" style="display:none"></textarea></div>
</form>
<p>
	<script src="htmlEncode.js"></script>
	<script type="text/javascript">
SEP_PADDING = 5
HANDLE_PADDING = 7

var yToolbars =        new Array();
var YInitialized = false;
var bLoad=false
var pureText=true
var bodyTag="<head><style type=\"text/css\">body {font-size:        9pt}</style><meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"></head><BODY bgcolor=\"#FFFFFF\" MONOSPACE>"
var bTextMode=false

public_description=new Editor


function document.onreadystatechange(){
  if (YInitialized) return;
  YInitialized = true;

  var i, s, curr;

  for (i=0; i<document.body.all.length;        i++)
  {
    curr=document.body.all[i];
    if (curr.className == "yToolbar")
    {
      InitTB(curr);
      yToolbars[yToolbars.length] = curr;
    }
  }

  DoLayout();
  window.onresize = DoLayout;

  HtmlEdit.document.open();
  HtmlEdit.document.write(bodyTag);
  HtmlEdit.document.close();
  HtmlEdit.document.designMode="On";
}

function InitBtn(btn)
{
  btn.onmouseover = BtnMouseOver;
  btn.onmouseout = BtnMouseOut;
  btn.onmousedown = BtnMouseDown;
  btn.onmouseup        = BtnMouseUp;
  btn.ondragstart = YCancelEvent;
  btn.onselectstart = YCancelEvent;
  btn.onselect = YCancelEvent;
  btn.YUSERONCLICK = btn.onclick;
  btn.onclick =        YCancelEvent;
  btn.YINITIALIZED = true;
  return true;
}

function InitTB(y)
{
  y.TBWidth = 0;

  if (!        PopulateTB(y)) return false;

  y.style.posWidth = y.TBWidth;

  return true;
}


function YCancelEvent()
{
  event.returnValue=false;
  event.cancelBubble=true;
  return false;
}

function PopulateTB(y)
{
  var i, elements, element;

  elements = y.children;
  for (i=0; i<elements.length; i++) {
    element = elements[i];
    if (element.tagName        == "SCRIPT" || element.tagName == "!") continue;

    switch (element.className) {
      case "Btn":
        if (element.YINITIALIZED == null)        {
          if (! InitBtn(element))
          return false;
        }
        element.style.posLeft = y.TBWidth;
        y.TBWidth        += element.offsetWidth + 1;
        break;

      case "TBGen":
        element.style.posLeft = y.TBWidth;
        y.TBWidth        += element.offsetWidth + 1;
        break;

      case "TBSep":
        element.style.posLeft = y.TBWidth        + 2;
        y.TBWidth        += SEP_PADDING;
        break;

      case "TBHandle":
        element.style.posLeft = 2;
        y.TBWidth        += element.offsetWidth + HANDLE_PADDING;
        break;

      default:
        return false;
      }
  }

  y.TBWidth += 1;
  return true;
}

function DebugObject(obj)
{
  var msg = "";
  for (var i in        TB) {
    ans=prompt(i+"="+TB[i]+"\n");
    if (! ans) break;
  }
}

function LayoutTBs()
{
  NumTBs = yToolbars.length;

  if (NumTBs ==        0) return;

  var i;
  var ScrWid = (document.body.offsetWidth) - 6;
  var TotalLen = ScrWid;
  for (i = 0 ; i < NumTBs ; i++) {
    TB = yToolbars[i];
    if (TB.TBWidth > TotalLen) TotalLen        = TB.TBWidth;
  }

  var PrevTB;
  var LastStart        = 0;
  var RelTop = 0;
  var LastWid, CurrWid;
  var TB = yToolbars[0];
  TB.style.posTop = 0;
  TB.style.posLeft = 0;

  var Start = TB.TBWidth;
  for (i = 1 ; i < yToolbars.length ; i++) {
    PrevTB = TB;
    TB = yToolbars[i];
    CurrWid = TB.TBWidth;

    if ((Start + CurrWid) > ScrWid) {
      Start = 0;
      LastWid =        TotalLen - LastStart;
    }
    else {
       LastWid =        PrevTB.TBWidth;
       RelTop -=        TB.offsetHeight;
    }

    TB.style.posTop = RelTop;
    TB.style.posLeft = Start;
    PrevTB.style.width = LastWid;

    LastStart =        Start;
    Start += CurrWid;
  }

  TB.style.width = TotalLen - LastStart;

  i--;
  TB = yToolbars[i];
  var TBInd = TB.sourceIndex;
  var A        = TB.document.all;
  var item;
  for (i in A) {
    item = A.item(i);
    if (! item)        continue;
    if (! item.style) continue;
    if (item.sourceIndex <= TBInd) continue;
    if (item.style.position == "absolute") continue;
    item.style.posTop =        RelTop;
  }
}

function DoLayout()
{
  LayoutTBs();
}

function BtnMouseOver()
{
  if (event.srcElement.tagName != "IMG") return        false;
  var image = event.srcElement;
  var element =        image.parentElement;

  if (image.className == "Ico")        element.className = "BtnMouseOverUp";
  else if (image.className == "IcoDown") element.className = "BtnMouseOverDown";

  event.cancelBubble = true;
}

function BtnMouseOut()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =        image.parentElement;
  yRaisedElement = null;

  element.className = "Btn";
  image.className = "Ico";

  event.cancelBubble = true;
}

function BtnMouseDown()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    event.returnValue=false;
    return false;
  }

  var image = event.srcElement;
  var element =        image.parentElement;

  element.className = "BtnMouseOverDown";
  image.className = "IcoDown";

  event.cancelBubble = true;
  event.returnValue=false;
  return false;
}

function BtnMouseUp()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =        image.parentElement;

  if (element.YUSERONCLICK) eval(element.YUSERONCLICK +        "anonymous()");

  element.className = "BtnMouseOverUp";
  image.className = "Ico";

  event.cancelBubble = true;
  return false;
}

function getEl(sTag,start)
{
  while        ((start!=null) && (start.tagName!=sTag)) start = start.parentElement;
  return start;
}

function cleanHtml()
{
  var fonts = HtmlEdit.document.body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML        = curr.innerHTML;
  }
}

function getPureHtml()
{
  var str = "";
  var paras = HtmlEdit.document.body.all.tags("P");
  if (paras.length > 0)        {
    for        (var i=paras.length-1; i >= 0; i--) str        = paras[i].innerHTML + "\n" + str;
  }
  else {
    str        = HtmlEdit.document.body.innerHTML;
  }
  return str;
}


function Editor()
{
  this.put_HtmlMode=setMode;
  this.put_value=putText;
  this.get_value=getText;
}

function getText()
{
  if (bTextMode)
    return HtmlEdit.document.body.innerText;
  else
  {
    cleanHtml();
    cleanHtml();
    return HtmlEdit.document.body.innerHTML;
  }
}

function putText(v)
{
  if (bTextMode)
    c = v;
  else
    HtmlEdit.document.body.innerHTML = v;
}

function UserDialog(what)
{
  if (!validateMode()) return;

  HtmlEdit.document.execCommand(what, true);

  pureText = false;
  HtmlEdit.focus();
}

function validateMode()
{
  if (!        bTextMode) return true;
  alert("请取消“查看HTML源代码”选项，然後再使用系统编辑功能!");
  HtmlEdit.focus();
  return false;
}

function format(what,opt)
{
  if (!validateMode()) return;
  if (opt=="removeFormat")
  {
    what=opt;
    opt=null;
  }

  if (opt==null) HtmlEdit.document.execCommand(what);
  else HtmlEdit.document.execCommand(what,"",opt);

  pureText = false;
  HtmlEdit.focus();
}

function setMode(newMode)
{
  var cont;
  bTextMode = newMode;
  if (bTextMode) {
    cleanHtml();
    cleanHtml();

    cont=HtmlEdit.document.body.innerHTML;
    HtmlEdit.document.body.innerText=cont;
  }
  else {
    cont=HtmlEdit.document.body.innerText;
    HtmlEdit.document.body.innerHTML=cont;
  }
  HtmlEdit.focus();
}

function foreColor()
{
  if (!        validateMode())        return;
  var arr = showModalDialog("selcolor.html", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0");
  if (arr != null) format('forecolor', arr);
  else HtmlEdit.focus();
}


function BackColor()
{
  if (!        validateMode())        return;
  var arr = showModalDialog("selcolor.html", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0");
  if (arr != null) format('BackColor', arr);
  else HtmlEdit.focus();
}

function InsertTable()
{
  if (!        validateMode())        return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("table.html", "", "dialogWidth:300pt;dialogHeight:236pt;help:0;status:0");

  if (arr != null){
        range.pasteHTML(arr);
  }
  HtmlEdit.focus();
}


function InsertImg()
{
  if (!        validateMode())        return;
  HtmlEdit.focus();
  var range = HtmlEdit.document.selection.createRange();
  var arr = showModalDialog("image.asp", "", "dialogWidth:430px; dialogHeight:230px; status:0");
  if (arr != null)
  {
        range.pasteHTML(arr);
        parent.myform.IncludePic.checked=true;
  }
  HtmlEdit.focus();
}

function specialtype(Mark){
  if (!Error()) return;
  var sel,RangeType
  sel = HtmlEdit.document.selection.createRange();
  RangeType = HtmlEdit.document.selection.type;
  if (RangeType == "Text"){
    sel.pasteHTML("<" + Mark + ">" + sel.text + "</" + Mark + ">");
    sel.select();
  }
  HtmlEdit.focus();
}

function help()
{
  var arr = showModalDialog("help.html", "", "dialogWidth:580px; dialogHeight:460px; status:0");
}




function pic()
{
  var arr = showModalDialog("pic.html", "", "dialogWidth:30em; dialogHeight:15em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  f=ss[5];
  g=ss[6];
  h=ss[7];
  i=ss[8];
  
  var str1;
str1="<img src='"+a+"' alt='"+b+"'"
if(d.value!='')str1=str1+"width='"+d+"'"
if(e.value!='')str1=str1+"height='"+e+"' "
str1=str1+" border='"+i+"' align='"+h+"' vspace='"+f+"' hspace='"+g+"'  style='"+c+"'"
str1=str1+">"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function hr()
{
  var arr = showModalDialog("hr.htm", "", "dialogWidth:30em; dialogHeight:12em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  var str1;
str1="<hr"
str1=str1+" color='"+a+"'"
str1=str1+" size="+b+"'"
str1=str1+" "+c+""
str1=str1+" align="+d+""
str1=str1+" width="+e
str1=str1+">"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function FIELDSET()
{
  var arr = showModalDialog("fieldset.htm", "", "dialogWidth:25em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  var str1;
str1="<FIELDSET "
str1=str1+"align="+a+""
str1=str1+" style='"
if(c.value!='')str1=str1+"color:"+c+";"
if(d.value!='')str1=str1+"background-color:"+d+";"
str1=str1+"'><Legend"
str1=str1+" align="+b+""
str1=str1+">标题</Legend>内容</FIELDSET>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function iframe()
{
  var arr = showModalDialog("iframe.htm", "", "dialogWidth:30em; dialogHeight:13em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  a=ss[0];
  b=ss[1];
  c=ss[2];
  d=ss[3];
  e=ss[4];
  f=ss[5];
  g=ss[6];
  var str1;
str1="<iframe src='"+a+"'"
str1+=" scrolling="+b+""
str1+=" frameborder="+c+""
if(d!='')str1+=" marginheight="+d
if(e!='')str1+=" marginwidth="+e
if(f!='')str1+=" width="+f
if(g!='')str1+=" height="+g
str1=str1+"></iframe>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+str1;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function swf()
{
  var arr = showModalDialog("flash.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' width="+row+" height="+col+"><param name=movie value="+path+"><param name=quality value=high><embed src="+path+" pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width="+row+" height="+col+"></embed></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function wmv()
{
  var arr = showModalDialog("media.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' width="+row+" height="+col+"><param name=Filename value="+path+"><param name='BufferingTime' value='5'><param name='AutoSize' value='-1'><param name='AnimationAtStart' value='-1'><param name='AllowChangeDisplaySize' value='-1'><param name='ShowPositionControls' value='0'><param name='TransparentAtStart' value='1'><param name='ShowStatusBar' value='1'></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}


function rm()
{
  var arr = showModalDialog("rm.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
string="<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+row+" height="+col+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+path+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+row+" height=60><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}
function Cmov()
{
  var arr = showModalDialog("Cmov.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  col=ss[2];
  var string;
  //string="[qt="+row+","+col+"]"+path+"[/qt]"
//string="<object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA' width="+row+" height="+col+"><param name='CONTROLS' value='ImageWindow'><param name='CONSOLE' value='Clip1'><param name='AUTOSTART' value='-1'><param name=src value="+path+"></object><br><object classid='clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'  width="+row+" height=60><param name='CONTROLS' value='ControlPanel,StatusBar'><param name='CONSOLE' value='Clip1'></object>"
string="<embed src="+path+" width="+row+" height="+col+" autoplay=true loop=false controller=true playeveryframe=false cache=false scale=TOFIT bgcolor=#000000 kioskmode=false targetcache=false pluginspage=http://www.apple.com/quicktime/>"
 

  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function fly()
{
  var arr = showModalDialog("fly.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  //row=ss[1];
  //col=ss[2];
  var string;
string="<marquee width=90% behavior=alternate scrollamount=3>"+path+"</marquee>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}
function move()
{
  var arr = showModalDialog("move.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  //row=ss[1];
  //col=ss[2];
  var string;
string="<MARQUEE scrollamount=3>"+path+"</marquee>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}


function glow()
{
  var arr = showModalDialog("glow.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  var kk;
  kk=row.split(",")
  row1=kk[0]
  row2=kk[1]
  row3=kk[2]
  var string;
  
//string="[glow="+row+"]"+path+"[/glow]"
string="<table width="+row1+" style='filter:glow(color="+row2+", strength="+row3+")'>"+path+"</table>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function shadow()
{
  var arr = showModalDialog("shadow.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  row=ss[1];
  var kk;
  kk=row.split(",")
  row1=kk[0]
  row2=kk[1]
  row3=kk[2]
  var string;

string="<table width="+row1+" style='filter:shadow(color="+row2+", strength="+row3+")'>"+path+"</table>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function csound()
{
  var arr = showModalDialog("csound.html", "", "dialogWidth:30em; dialogHeight:10em; status:0;help:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  path=ss[0];
  //row=ss[1];
  //col=ss[2];
  var string;
//string="[sound]"+path+"[/sound]"
string="<a href='"+path+"' target=_blank><IMG SRC=image/mid.gif border=0 alt='mid背景音乐'></a><bgsound src='"+path+"' loop='-1'>"
  content=HtmlEdit.document.body.innerHTML;
  content=content+string;
   HtmlEdit.document.body.innerHTML=content;
  }
  else HtmlEdit.focus();
}

function insertsmilie(smilieface){
  content=HtmlEdit.document.body.innerHTML;
  string="<a href='"+smilieface+"' target=_blank><IMG SRC=image/mid.gif border=0 alt='mid背景音乐'></a><bgsound src='"+smilieface+"' loop='-1'>"

  content=content+string;
  
   HtmlEdit.document.body.innerHTML=content;
//        document.frmAnnounce.Content.value+=smilieface;
}

    function WinOpen() {
  var pageWidth=screen.availWidth-10;
  var pageHeight=screen.availHeight-30;
  if (pageWidth<100 )
    pageWidth = 100;
  if (pageHeight<100 )
    pageHeight = 100;
    mesg=	open("about:blank","print",'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
  if (bTextMode){
    mesg.document.write(HtmlEdit.document.body.innerText);
  }else{
    mesg.document.write("<html><head><link rel=stylesheet type=text/css href=/prpall/css/PrintStandard.css><link rel='stylesheet' type='text/css' href='/prpall/css/Standard.css'></head><body>");
    mesg.document.write(HtmlEdit.document.body.innerHTML);
    mesg.document.write("</body></html>");
  }
  mesg.focus(); 
  return mesg;
	}
	
function save()
{
	
  //opener.document.location.reload();
  opener.document.write("<html><head><title><%=riskName%></title></head><body>");
  if (bTextMode){
//编辑器嵌入其他网页时使用下面这一句（请将myform改成相应表单名）
  //parent.myform.Content.value=HtmlEdit.document.body.innerText;
  //parent.myform.Content.value=HtmlEdit.document.body.innerText;
//单独打开编辑器时使用下面这一句（请将form1改成相应表单名）  
//  self.opener.myform.content.value+=HtmlEdit.document.body.innerText;
	opener.document.write(HtmlEdit.document.body.innerText);
  }
  else{
//编辑器嵌入其他网页时使用下面这一句（请将myform改成相应表单名）
//  parent.myform.Content.value=HtmlEdit.document.body.innerHTML;

 opener.document.write(HtmlEdit.document.body.innerHTML);

//单独打开编辑器时使用下面这一句（请将form1改成相应表单名）  
//  self.opener.myform.content.value+=HtmlEdit.document.body.innerHTML;
  }
  //HtmlEdit.focus();
  //window.close();
  //return false;
  <%
   if(showOnly!=null && !"".equals(showOnly)){
   %>
       opener.document.write("</body></html>");
   <%
   }else{
   %>
       opener.document.write("<font color='red' size='10'>该页面仅供查看，列印无效！</font></body></html>");
   <%
    }
   %>
  opener.document.close();
  //opener.document.location.reload();
  save2();
  
}


function save2(){
  if (bTextMode){
	//document.form1.ta1.value = 	htmlEncode(HtmlEdit.document.body.innerText,false,0);
	document.form1.ta1.value = 	"<html><head><title><%=riskName%></title></head><body>" +
															HtmlEdit.document.body.innerText + 
															"/body></html>";
	}else{
	//document.form1.ta1.value = 	htmlEncode(HtmlEdit.document.body.innerHTML,false,0);
	document.form1.ta1.value = 	"<html><head><title><%=riskName%></title></head><body>" +
															HtmlEdit.document.body.innerHTML + 
															"</body></html>";
  }
	document.form1.action="UIEditReportResult.jsp?BizNo=<%=ProposalNo%>&showOnly=yes&PrintType=<%=PrintType%>";
	form1.submit();
	//window.close();
}

function load(){
  var cssUrl = "<table><link rel=\"stylesheet\" type=\"text/css\" href=\"/prpall/css/PrintStandard.css\">" + 
               "<link rel=\"stylesheet\" type=\"text/css\" href=\"/prpall/css/Standard.css\"></table>";
    cleanHtml();
    cleanHtml();

    HtmlEdit.document.body.innerHTML= cssUrl + opener.document.body.innerHTML;
}
	
	
function onload(){
    HtmlEdit.document.body.innerHTML= opener.document.body.innerHTML;
}

// 检验备注长度
function checkLengthRemark(){
  var strValue = form1.MainRemark.value;
  var strDesc = form1.MainRemark.description;
  var intMaxLength = form1.MainRemark.maxLength;
  var intCount = 0;
  var vChar;
  var i;
  if(strDesc==null)
    strDesc = form1.MainRemark.name;
  if(strValue.indexOf("^")>-1||
     strValue.indexOf(FIELD_SEPARATOR)>-1||
     strValue.indexOf(GROUP_SEPARATOR)>-1)
  {
    errorMessage("^为系统保留字符，不允许输入！");
    form1.MainRemark.focus();
    form1.MainRemark.select();
    return false;
  }
  for(i=0;i<strValue.length;i++)
  {
    vChar = escape(strValue.charAt(i));
    if(vChar.substring(0,2)=="%u"&&vChar.length==6)
      intCount = intCount+2;
    else
      intCount = intCount+1;
  }
  if(intCount>intMaxLength)
  {
    errorMessage(strDesc+"输入的内容超长！\n"+strDesc+"的最大长度为"+intMaxLength+"个英文字符！\n请重新输入！");
    form1.MainRemark.focus();
    form1.MainRemark.select();
    return false;
  }
  hideSpan('span_MainRemark_Context');
  form1.MainRemark_Show.value = form1.MainRemark.value;
  form1.button_MainRemark_Open_Context.value = "修 改";
  return true;
}

</script>

</p>
</body>
</html>

