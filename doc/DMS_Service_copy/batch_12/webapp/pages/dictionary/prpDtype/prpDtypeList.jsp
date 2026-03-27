<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<body>
<s:form name="fm" action="/dictionary/updateprpDtype.do">
	<div id="div"
		style="MARGIN: 2pt; width: 100%; height: 100%;">
	<table class="fix_table">
		<tr>
			<td align="center">
			<h2>代码类型</h2>
			</td>
		</tr>
		<c:set var="index" value="0" />
		 <c:forEach items="${requestScope.prpDtypes}" var="prpDtype">
			<tr>
				<td>
				<div style="display: none">${index+1 }</div>
				<input type=radio name=checkboxSelect
					value="${prpDtype.codeType}"> 
					
					<a
					href="${ctx}/dictionary/prepareQueryPrpDcode.do?prpDtype=${prpDtype.codeType}"
					target="prpDcodeRight">
					${prpDtype.codeType}-${prpDtype.codeTypeDesc}</a>
			</td>
				<s:hidden name="codeType" value="${prpDtype.codeType}" />
			</tr>
			<c:set var="index" value="${index+1 }" />
		 </c:forEach>
	</table>
	</div>
	<table align="center" class="fix_table">
		<tr>
			<td align="right"><input type='button' class="button_ty"
				name=buttonInsert value="增加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDtype.do?editType=insert');"></td>

			<td align="left"><input type='button' class="button_ty"
				name=buttonModify value="修改" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDtype.do?editType=update');"></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type='button' class="button_ty"
				name=buttonInsert value="删除" onclick="return deleteMethod()"></td>

			<td align="left"><input type='button' class="button_ty"
				name=buttonModify value="查看" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDtype.do?editType=view');"></td>
		</tr>
	</table>
</s:form>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script language="javascript">
    function insertMethod(){
        fm.action="${ctx}/dictionary/prepareInsertPrpDtype.do?editType=insert";
        fm.target="companyTreeRight";
        fm.submit();
        return true;
    }
    function copyMethod(){
    	  var count = getElementCount('checkboxSelect');
         if(count==0){
            alert('没有记录!');
            return false;
        }else 
        if(count==1){
            if(fm.checkboxSelect.checked==true){
            	var checkboxSelect = fm.checkboxSelect.value;
                fm.action = '${ctx}/saaGrade/prepareCopyGrade.do?editType=cope&gradeID='+checkboxSelect;
                fm.target="prpDcodeRight";
                fm.submit();
                return true;
            }
            else{
                alert('请选择一条记录');
                return false;
            }
        }else{
        	var n = 0;
        	for(var i=0;i<fm.checkboxSelect.length;i++){
        	    if(fm.checkboxSelect[i].checked==true){
        	        n = n + 1;
        	    }
       	 	}
	        if(n==0){
	            alert("请选择一条记录");
	            return false;
	        }
	        else if(n==1){
	            for(var j=0;j<fm.checkboxSelect.length;j++){
	                if(fm.checkboxSelect[j].checked==true){
	                   var checkboxSelect = fm.checkboxSelect[j].value;
	                   fm.action = 'contextRootPath/saaGrade/prepareCopyGrade.do?editType=copy&gradeID='+checkboxSelect;
	                   fm.target="prpDcodeRight";
	                   fm.submit();
	                   break;
	                 }
	            }
	        }
	        else{
	            alert("只能选择一条记录");
	            return false;
	        }
	        return true;
	    	}
    }
    
    function modifyMethod(){
        var count = getElementCount('checkboxSelect');
         if(count==0){
            alert('没有记录!');
            return false;
        }else 
        if(count==1){
            if(fm.checkboxSelect.checked==true){
            	var checkboxSelect = fm.checkboxSelect.value;
                fm.action = '${ctx}/saaGrade/prepareUpdateGrade.do?editType=update&gradeID='+checkboxSelect;
                fm.target="prpDcodeRight";
                fm.submit();
                return true;
            }
            else{
                alert('请选择一条记录');
                return false;
            }
        }else{
        	var n = 0;
        	for(var i=0;i<fm.checkboxSelect.length;i++){
        	    if(fm.checkboxSelect[i].checked==true){
        	        n = n + 1;
        	    }
       	 	}
	        if(n==0){
	            alert("请选择一条记录");
	            return false;
	        }
	        else if(n==1){
	            for(var j=0;j<fm.checkboxSelect.length;j++){
	                if(fm.checkboxSelect[j].checked==true){
	                   var checkboxSelect = fm.checkboxSelect[j].value;
	                   fm.action = 'contextRootPath/saaGrade/prepareUpdateGrade.do?editType=update&gradeID='+checkboxSelect;
	                   fm.target="prpDcodeRight";
	                   fm.submit();
	                   break;
	                 }
	            }
	        }
	        else{
	            alert("只能选择一条记录");
	            return false;
	        }
	        return true;
	    	}
    }
    function checkMethod(){
        var count = getElementCount('checkboxSelect');
         if(count==0){
            alert('没有记录!');
            return false;
        }else 
        if(count==1){
            if(fm.checkboxSelect.checked==true){
            	var checkboxSelect = fm.checkboxSelect.value;
                fm.action = '${ctx }/dictionary/viewGrade.do?editType=view&gradeID='+checkboxSelect;
                fm.target="prpDcodeRight";
                fm.submit();
                return true;
            }
            else{
                alert('请选择一条记录');
                return false;
            }
        }else{
        	var n = 0;
        	for(var i=0;i<fm.checkboxSelect.length;i++){
        	    if(fm.checkboxSelect[i].checked==true){
        	        n = n + 1;
        	    }
       	 	}
	        if(n==0){
	            alert("请选择一条记录");
	            return false;
	        }
	        else if(n==1){
	            for(var j=0;j<fm.checkboxSelect.length;j++){
	                if(fm.checkboxSelect[j].checked==true){
	                   var checkboxSelect = fm.checkboxSelect[j].value;
	                   fm.action = '${ctx }/saaGrade/viewGrade.do?editType=view&gradeID='+checkboxSelect;
	                   fm.target="prpDcodeRight";
	                   fm.submit();
	                   break;
	                 }
	            }
	        }
	        else{
	            alert("只能选择一条记录");
	            return false;
	        }
	        return true;
	    	}
    }
    function deleteMethod(){
    	var count = getElementCount('checkboxSelect');
         if(count==0){
            alert('没有记录!');
            return false;
        }else{
        	var n = 0;
        	for(var i=0;i<fm.checkboxSelect.length;i++){
        	    if(fm.checkboxSelect[i].checked==true){
        	        n = n + 1;
        	    }
       	 	}if(n==0){
	            alert("请选择一条记录");
	            return false;
	        }else if(n==1){
	        	if(confirm("确定要删除该代码类型？")){
	            	for(var j=0;j<fm.checkboxSelect.length;j++){
	                	if(fm.checkboxSelect[j].checked==true){
	                   		var checkboxSelect = fm.checkboxSelect[j].value;
	                  	 	fm.action = '${ctx }/dictionary/deletePrpDtype.do?chkbox='+checkboxSelect;
	                   		fm.submit();
	                   		break;
	                 	}
	            	}
	            }else{
	            	return false；
	            }
	        }else{
	            alert("只能选择一条记录");
	            return false;
	        }
	        return true;
	    }
    }
 </script>
