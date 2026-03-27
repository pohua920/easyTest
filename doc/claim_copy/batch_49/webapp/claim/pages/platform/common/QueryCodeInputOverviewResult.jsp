<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <body class="interface" onload="loadPage()" style="BORDER: #3D72D7 1px solid">
        <form action="${ctx}/processClaimPlatFormCodeInput.do?actionType=query" name=fm>
            <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
            <span id=cond style="display: none"> 
                <input type="hidden" name=actionType value="query"> 
                <input type="textarea" name=fieldIndex value='<%=request.getParameter("fieldIndex")%>'>
                <input type="textarea" name=fieldValue value='<%=request.getParameter("fieldValue")%>'> 
                <input type="textarea" name=codeMethod value='<%=request.getParameter("codeMethod")%>'> 
                <input type="textarea" name=codeType value='<%=request.getParameter("codeType")%>'> 
                <input type="textarea" name=codeRelation value='<%=request.getParameter("codeRelation")%>'> 
                <input type="textarea" name=isClear value='<%=request.getParameter("isClear")%>'> 
                <input type="textarea" name=otherCondition value='<%=request.getParameter("otherCondition")%>'> 
                <input type="textarea" name=callBackMethod value='<%=request.getParameter("callBackMethod")%>'> 
                <input type="textarea" name=getDataMethod value='<%=request.getParameter("getDataMethod")%>'> 
            </span>
            <table class="common" cellpadding="2" cellspacing="0" align="center"
                style="display: none" id="resultTab">
                <tr>
                    <td width=50% align="center">
                        <input class="button" type="button" name="SelectIt" value="确 定"
                            alt="确 定" onclick='setFieldValue()'>
                    </td>
                    <td width=50% align="center">
                        <input name="CancelIt" class="button" type="button" value="取 消"
                            alt="取 消" onclick='cancelFieldValue()'>
                    </td>
                </tr>
                <tr>
                    <td colspan=2 align="center">
                        <select name=codeselect class="one" size=8 style="width: 100%"
                            <%
                            if(((String)request.getParameter("codeMethod")).equals("query")){
                                out.println("multiple ");
                            }
                            %>
                            ondblclick=setFieldValue() onkeypress=fieldOnKeyPress()>
                            <%
                                Object[] codeValues = (Object[]) request.getAttribute("codeValues");
                                Object[] codeLabels = (Object[]) request.getAttribute("codeLabels");
                                for (int i = 0; i < codeValues.length; i++) {
                            %>
                            <%-- 只限制核赔所需的审核类型 --%>
                                <option value="<%=codeValues[i]%>"><%=codeLabels[i]%>
                                <%
                                    }
                                %>
                            
                        </select>
                    </td>
                </tr>
            </table>
            <form>
                <script language="Javascript" src="${ctx}/pages/platform/js/Common.js"></script>
                <script language="Javascript" src="${ctx}/pages/platform/js/CodeSelect.js"></script>
                <script language='javascript'>
                    function codeQuery(fieldIndex,fieldValue,codeMethod,codeType,codeRelation,isClear,otherCondition,callBackMethod,getDataMethod){
                        resultTab.style.display="none";
                        fm.fieldIndex.value=fieldIndex;
                        fm.fieldValue.value=fieldValue;
                        fm.codeMethod.value=codeMethod;
                        fm.codeType.value=codeType;
                        fm.codeRelation.value=codeRelation;
                        fm.isClear.value=isClear;
                        fm.otherCondition.value=otherCondition;
                        fm.callBackMethod.value=callBackMethod;
                        fm.getDataMethod.value=getDataMethod;
                        if(fm.isClear.value=="undefined" || fm.isClear.value=="null"){
                            fm.isClear.value="Y";
                        }
                        if(fm.otherCondition.value=="undefined" || fm.otherCondition.value=="null"){
                            fm.otherCondition.value="";
                        }
                        if(fm.callBackMethod.value=="undefined" || fm.callBackMethod.value=="null"){
                            fm.callBackMethod.value="";
                        }
                        if(fm.getDataMethod.value=="undefined" || fm.getDataMethod.value=="null"){
                            fm.getDataMethod.value="";
                        }
                        //alert("这里："+fm.action)
                        fm.submit();
                    }
                <%
                    String codeMethod = request.getParameter("codeMethod");
                    if(codeMethod.equalsIgnoreCase("change")){
                        out.println("setFieldValue()");
                    }
                %>
                    function loadPage(){
                  //  alert("ok");
                        if(fm.getDataMethod.value!=""){
                            fm.codeselect.innerText=eval("parent." + fm.getDataMethod.value);
                        }
                        resultTab.style.display="";
                        if(fm.codeMethod.value=="select"){
                            if(fm.codeselect.options.length>=1){
                                fm.codeselect.selectedIndex=0;
                            }
                        }
                        resultTab.scrollIntoView(true);
                        setFocus(fm.codeselect);
                    }
                </script>
    </body>
</html>