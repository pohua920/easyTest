<%@ page contentType="text/html; charset=GBK" %>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<body class="interface">
<form action="/undwrt/common/processCodeInput.do?actionType=query">
    <span id=cond style="display:none">
        <input type="textarea" name=fieldIndex>
        <input type="textarea" name=fieldValue>
        <input type="textarea" name=codeMethod  >
        <input type="textarea" name=codeType    >
        <input type="textarea" name=codeRelation>
        <input type="textarea" name=isClear   >
        <input type="textarea" name=otherCondition>
        <input type="textarea" name=callBackMethod>
        <input type="textarea" name=getDataMethod>
    </span>
</form>
<script language='javascript'>
    function codeQuery(fieldIndex,fieldValue,codeMethod,codeType,codeRelation,isClear,otherCondition,callBackMethod,getDataMethod){
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
        fm.submit();
    }
</script>
</body>
</html>