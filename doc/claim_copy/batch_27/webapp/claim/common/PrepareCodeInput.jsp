<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<base target="_self">
<body class="interface" onload="submitCodeSelectForm()"  style="display:none">
<s:form name="fm" action="processCodeInput" namespace="/common">
  <span id="cond">
  	<input type="hidden" name=actionType value="query"/>
    <textarea name=fieldIndex></textarea>
    <textarea name=fieldValue></textarea>
    <textarea name=codeMethod></textarea>
    <textarea name=codeType></textarea>
    <textarea name=codeRelation></textarea>
    <textarea name=isClear></textarea>
    <textarea name=otherCondition></textarea>
    <textarea name=typeParam></textarea>
    <textarea name=callBackMethod></textarea>
    <textarea name=getDataMethod></textarea>
    <textarea name=pageNo></textarea>
    <textarea name=pageSize></textarea>
    <textarea name=elementOrder></textarea>
    <textarea name=elementLength></textarea>
  </span> 
</s:form>
<script language='javascript'>
    function submitCodeSelectForm(){
        var obj = window.dialogArguments.prototype;  
		fm.fieldIndex.value=obj.fieldIndex;
        fm.fieldValue.value=obj.fieldValue;
        fm.codeMethod.value=obj.codeMethod;
        fm.codeType.value=obj.codeType;
        fm.codeRelation.value=obj.codeRelation;
        fm.isClear.value=obj.isClear;
        fm.otherCondition.value=obj.otherCondition;
        fm.typeParam.value=obj.typeParam;
        fm.callBackMethod.value=obj.callBackMethod;
        fm.getDataMethod.value=obj.getDataMethod;
        fm.pageNo.value=obj.pageNo;
        fm.pageSize.value=obj.pageSize;
        fm.elementOrder.value=obj.elementOrder;
        fm.elementLength.value=obj.elementLength;

        if(fm.isClear.value==undefined || fm.isClear.value=="null"){
            fm.isClear.value="Y";
        }
        if(fm.otherCondition.value==undefined || fm.otherCondition.value=="null"){
            fm.otherCondition.value="";
        }
        if(fm.typeParam.value==undefined || fm.typeParam.value=="null"){
            fm.typeParam.value="";
        }
        if(fm.callBackMethod.value==undefined || fm.callBackMethod.value=="null"){
            fm.callBackMethod.value="";
        }
        if(fm.getDataMethod.value==undefined || fm.getDataMethod.value=="null"){
            fm.getDataMethod.value="";
        }
        if(fm.pageNo.value==undefined || fm.pageNo.value=="null"){
            fm.pageNo.value="1";
        }
        if(fm.pageSize.value==undefined || fm.pageSize.value=="null"){
            fm.pageSize.value="50";
        } 
        if(fm.elementOrder.value==undefined || fm.elementOrder.value=="null"){
            fm.elementOrder.value=0;
        } 
        if(fm.elementLength.value==undefined || fm.elementLength.value=="null"){
            fm.elementLength.value=1;
        } 
        document.forms[0].submit(); 
    }
</script>
</body>
</html>
