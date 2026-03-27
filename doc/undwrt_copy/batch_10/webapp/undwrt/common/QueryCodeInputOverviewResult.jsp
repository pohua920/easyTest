<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="com.opensymphony.xwork2.util.ValueStack"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	boolean isQuery = false;
	if (request.getParameter("codeMethod").equals("query")) {
		isQuery = true;
	}

	ActionContext actionContext = ServletActionContext.getContext();
	ValueStack valueStack = actionContext.getValueStack();

	ArrayList codeValues = (ArrayList) valueStack
			.findValue("codeValues");
	ArrayList codeLabels = (ArrayList) valueStack
			.findValue("codeLabels");
%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<base target="_self">
<title>Code Select</title>

<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<body style="BORDER: #3D72D7 1px solid">
<s:form name="fm" action="processCodeInputContinue" namespace="/commonCommon">
	<span id="cond" style="display:none">
		<textarea name=fieldIndex><s:property value="#parameters.fieldIndex" /></textarea>
		<textarea name=fieldValue><s:property value="#parameters.fieldValue" /></textarea>
		<textarea name=codeMethod><s:property value="#parameters.codeMethod" /></textarea>
		<textarea name=codeType><s:property value="#parameters.codeType" /></textarea>
		<textarea name=codeRelation><s:property value="#parameters.codeRelation" /></textarea>
		<textarea name=isClear><s:property value="#parameters.isClear" /></textarea>
		<textarea name=otherCondition><s:property value="#parameters.otherCondition" /></textarea>
		<textarea name=typeParam><s:property value="#parameters.typeParam" /></textarea>
		<textarea name=callBackMethod><s:property value="#parameters.callBackMethod" /></textarea>
		<textarea name=getDataMethod><s:property value="#parameters.getDataMethod" /></textarea>
		<textarea name=pageNo><s:property value="#parameters.pageNo" /></textarea>
		<textarea name=pageSize><s:property value="#parameters.pageSize" /></textarea>
		<textarea name=elementOrder><s:property value="#parameters.elementOrder" /></textarea>
		<textarea name=elementLength><s:property value="#parameters.elementLength" /></textarea>
		<textarea name=codeselectText></textarea>
	</span>
	<table cellpadding="2" cellspacing="0" align="center"
		style="display:" id="resultTab" class="fix_table">
		<tr>
			<td width=50% align="center">
				<input class="button_ty" type="button" name="SelectIt" value="´_¶¨"
					onclick='setFieldValue()' >
			</td>
			<td width=50% align="center">
				<input name="CancelIt" class="button_ty" type="button" value="È¡Ïû"
					onclick='cancelFieldValue()'>
			</td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<select name="codeselect" size="20" style="width:100%" class="STYLE1"
					<%= isQuery ? "multiple" : "" %> ondblclick="setFieldValue()"
					onkeydown="fieldOnKeyPress()">
					<%
					for (int i = 0; i < codeValues.size(); i++) {
					%>
					<option value="<%=codeValues.get(i)%>">
						<%=codeLabels.get(i)%>
					</option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div id="content_navigation" class="query"></div>
			</td>
		</tr>
	</table>
</s:form>

<script language="javascript" src="${ctx}/common/js/Common.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/datasource/datasource-beta-min.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/datatable/datatable-beta-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language='javascript'>

/** deleteNode */
function  delLoadingNode(){
  var nodeId = "loading";
  try{
	  var div =document.getElementById(nodeId);
	  if(div !==null){
		  document.body.removeChild(div);
		  div=null;
 	  }
  } catch(e){
  	alert("delete node "+nodeId+" error");
  }
}

YAHOO.namespace("query.container");
function genNavigator(pageNo,pageSize,pageCount,totalRecords)
{
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    if(totalRecords>0){
		content_navigation.innerHTML = markup;
	}else{
		content_navigation.innerHTML = "";
	}
}
function loadPage(){
	genNavigator(<s:property value="pageNo"/>,<s:property value="pageSize"/>,<s:property value="totalPage"/>,<s:property value="totalCount"/>);
	if(<%=isQuery%> && "${codeselect}".length>0 ){
		var arrValue = "${codeselect}".split(",");
		var arrText = "${codeselectText}".split(",");
		for(var i=arrValue.length-1;i>=0; i--){
			var option = document.createElement("option");
			option.value = arrValue[i];
			option.text = arrText[i];
			option.selected = true;
			fm.codeselect.add(option,0);
		}
	}
    if(fm.codeselect.options.length>=1){
    	if(<%=isQuery%>){
    	} else {
			fm.codeselect.selectedIndex=0;
		}
    }
    resultTab.scrollIntoView(false);
    setFocus(fm.codeselect);
}

function executeQuery(pageNo,pageSize){
	fm.pageNo.value = pageNo;
	fm.pageSize.value = pageSize;
	if(<%=isQuery%>){
		var  selectedText = "";
		for(var i=0;i<fm.codeselect.options.length;i++){
			if(fm.codeselect.options[i].selected){
				selectedText += fm.codeselect.options[i].text + ",";
			}
		}
		if(selectedText.length>0){
			selectedText = selectedText.substring(0,selectedText.length-1);
		}
		fm.codeselectText.value = selectedText;
	}
	fm.submit();
}
delLoadingNode();
YAHOO.util.Event.addListener(window,'load',loadPage);
</script>
</body>
</html>
