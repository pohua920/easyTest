<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants,java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>岗位模板管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
 
    
    <title>My JSP 'prepareInsertGradeTempl.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table class="fix_table" width="100%" >
         <tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              <s:if test="${editType=='insert' }">岗位模板增加</s:if>
              <s:if test="${editType=='update' }">岗位模板修改</s:if>
              <s:if test="${editType=='view' }">岗位模板查看</s:if>
               <s:if test="${editType=='copy' }">岗位模板复制</s:if>
            </h2>
            </div>	
		</tr>	
<!-------- 增加------------ -->		
      <s:if test="${editType=='insert'}" >
		<tr>
              <td class="bgc_tt short">模板简体中文名称<font color="red">*</font></td>
			<td class="long">
              <s:textfield name="gradeTemplCName" 
					id="gradeTemplCName" cssClass='input_w w_30' maxlength="40" />
          </td>
        	</tr>
        	<tr>
		       <td class="bgc_tt short">模板繁体中文名称</td>
			<td class="long"><input type="text" name="gradeTemplTName" id="gradeTemplTName" class='input_w w_30'></td>
		</tr>
        	<tr>
			

			<td class="bgc_tt short">模板英文名称</td>
			<td class="long"><input type="text" name="gradeTemplEName" id="gradeTemplEName" class='input_w w_30'></td>
        	</tr>
            <!--
        	<tr>
		   <td class="bgc_tt short">继承模板</td>
			<td class="long" >
                        <c:set var="checked" value="0" />
			        <ce:select name="extendTemplID" id="extendTemplID" cssClass="selectui-input-up input_w w_30" value="${checked}" list="gradeTemplMap" />

                     </td>
		</tr>    -->
           <tr>
                  <td class="bgc_tt short">有效状态<font color="red">*</font></td>
                <td>
                 <select name="validStatus" >
                     <option value="0">无效
                     <option value="1" selected="selected">有效
                 </select>   

                </td>

          </tr>
      </s:if>   
<!-------- 修改------------ -->	
   
        <s:elseif test="${editType=='update'}" >
         <s:hidden name="id" id="id" value="${saaGradeTempl.id}"></s:hidden>
		<tr>
              
              <td class="bgc_tt short">模板简体中文名称<font color="red">*</font></td>
			<td class="long"><input type="text" name="gradeTemplCName" id="gradeTemplCName3" value="${saaGradeTempl.gradeTemplCName }" class='input_w w_30' ></td>
		       	</tr>
        	<tr>
            <td class="bgc_tt short">模板繁体中文名称</td>
			<td class="long"><input type="text" name="gradeTemplTName" id="gradeTemplTName3" value="${saaGradeTempl.gradeTemplTName}" class='input_w w_30' ></td>
		</tr>
        	<tr>
			

			<td class="bgc_tt short">模板英文名称</td>
			<td class="long"><input type="text" name="gradeTemplEName" id="gradeTemplEName3" class='input_w w_30' value="${saaGradeTempl.gradeTemplEName}" ></td>
            	</tr>
           <!--
        	<tr>
		   <td class="bgc_tt short">继承模板</td>
			<td class="long" ><input type="text" name="extendTemplID" id="" class='input_w w_30' value="${saaGradeTempl.extendTemplID}"  readonly="readonly">
          </td>
		</tr>  -->
         <tr>
		       <td class="bgc_tt short">创建日期</td>
			<td class="long"><input type="text" name="createTime"   class='input_w w_30' value="${saaGradeTempl.createTime}"  readonly="readonly"></td>
             	</tr>
        	<tr>
			<td class="bgc_tt short">模板创建人员</td>
			<td class="long"><input type="text" name="creatorCode" id="" class='input_w w_30' value="${saaGradeTempl.creatorCode}" readonly="readonly"></td>
           
		
		</tr>
         

       
     </s:elseif>
<!-------- 复制------------ -->	
   
        <s:elseif test="${editType=='copy'}" >
         <s:hidden name="id" id="id" value="${id}"></s:hidden>
		<tr>
              <td class="bgc_tt short">模板简体中文名称<font color="red">*</font></td>
			<td class="long">
              <s:textfield name="gradeTemplCName" 
					id="gradeTemplCName2" cssClass='input_w w_30' maxlength="40" />
                </td>
                 	</tr>
        	        <tr>
		       <td class="bgc_tt short">模板繁体中文名称</td>
			<td class="long"><input type="text" name="gradeTemplTName" id="gradeTemplTName2" class='input_w w_30'></td>
		</tr>
        	<tr>
			

			<td class="bgc_tt short">模板英文名称</td>
			<td class="long"><input type="text" name="gradeTemplEName" id="gradeTemplEName2" class='input_w w_30'></td>
               </tr>
             <!--
        	        <tr>
		   <td class="bgc_tt short">继承模板</td>
			<td class="long" >
                    <c:set var="checked" value="0" />
			        <ce:select name="extendTemplID" id="extendTemplID" cssClass="selectui-input-up input_w w_30" value="${checked}" list="gradeTemplMap" />
          </td>
		</tr>    -->
                    <tr>
                  <td class="bgc_tt short">有效状态<font color="red">*</font></td>
                <td>
                 <select name="validStatus" >
                     <option value="0">无效
                     <option value="1" selected="selected">有效
                 </select> </td>
          </tr>
       
     </s:elseif>
<!--------查看------------ -->	
   
        <s:else>
		<tr>
              
              <td class="bgc_tt short">模板简体中文名称</td>
			<td class="long"><input type="text" name="gradeTemplCName" id="saaGradeTempl.gradeTemplCName" value="${saaGradeTempl.gradeTemplCName }" class='input_w w_30' readonly="readonly"></td>
		      </tr>
        	        <tr>
                <td class="bgc_tt short">模板繁体中文名称</td>
			<td class="long"><input type="text" name="gradeTemplTName" id="gradeTemplTName" value="${saaGradeTempl.gradeTemplTName}" class='input_w w_30' readonly="readonly"></td>
		 </tr>
        	<tr>
			

			<td class="bgc_tt short">模板英文名称</td>
			<td class="long"><input type="text" name="gradeTemplEName" id="" class='input_w w_30' value="${saaGradeTempl.gradeTemplEName}" readonly="readonly"></td>
              </tr>
             <!--   
        	    <tr>
		   <td class="bgc_tt short">继承模板</td>
			<td class="long" ><input type="text" name="extendTemplID" id="" class='input_w w_30' value="${saaGradeTempl.extendTemplID}" readonly="readonly"></td>
		</tr>--> 
         <tr> 
		       <td class="bgc_tt short">创建日期</td>
			<td class="long"><input type="text" name="createTime"   class='input_w w_30' value="${saaGradeTempl.createTime}" readonly="readonly"></td>
                    </tr>
        	        <tr>
			<td class="bgc_tt short">模板创建人员</td>
			<td class="long"><input type="text" name="creatorCode" id="" class='input_w w_30' value="${saaGradeTempl.creatorCode}" readonly="readonly"></td>

		
		</tr>
        <tr>
			<td class="bgc_tt short">修改日期</td>
			<td class="long"><input type="text" name="updateTime" id="" class='input_w w_30'  value="${saaGradeTempl.updateTime}" readonly="readonly"></td>
                      </tr>
        	        <tr>
			<td class="bgc_tt short">修改人员</td>
			<td class="long"><input type="text" name="updaterCode" id="" class='input_w w_30' value="${saaGradeTempl.updaterCode}" readonly="readonly"></td>
             
		
		</tr>
        <tr>
                 <td class="bgc_tt short">有效状态</td>
                <td>
              <s:select name="saaGradeTempl.validStatus" 
					list="#@java.util.HashMap@{'1':'有效','0':'无效'}"  disabled="true"/></td>
          </tr>
     </s:else>
			</table>

<br><br>
     	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<c:if test="${editType=='insert' }">
				<td><input type="button" value="保存" class="button_ty"
				onclick="return addMethod()"></td>
            </c:if>
            <c:if test="${editType=='update' }">
                <td><input type="button" value="保存" class="button_ty"
				onclick="return updateMethod()"></td>
            </c:if>
            <c:if test="${editType=='copy' }">	
				<td><input type="button" value="保存" class="button_ty"
				onclick="return copy()"></td>
			</c:if>
			<c:if test="${editType=='view' }">	
				<td><input type="button" value="关闭" class="button_ty"
				onclick="OKButton()"></td>
			</c:if>
		</tr>
	</table>
         		
        	
		
</s:form>

  </body>
</html>
<%@ include file="/common/meta_js.jsp"%>

<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript">
var tabView = new YAHOO.widget.TabView('tabdemo');
var tabFlag = new Array();
var exName = "yes";
var exOName = "yes";
tabFlag.push("taskIframe1");


  function updateMethod(){
	  	if(checkForm()){
	  	    
		    fm.action="${ctx}/saaGradeTempl/updateGradeTempl.do";
		    fm.submit();
		    return true;
		   window.close();
	  	} 
  }
  
  function addMethod(){
	 if(checkForm()){
		
				fm.action="${ctx}/saaGradeTempl/insertGradeTempl.do";
				fm.submit();
				return true;
	  }
  }
    function copy(){
	 if(checkForm()){
				fm.action="${ctx}/saaGradeTempl/copyGradeTempl.do";
				fm.submit();
				return true;
		}
	  
  }
  
	function OKButton(){
		window.close();
	}
 

   function checkForm(){
    var editType=document.getElementById("editType").value;
  	var ENameReg=/^[0-9a-zA-Z _]{0,20}$/;
  	var CNameReg=/^[0-9\u4e00-\u9fa5_ ]{1,20}$/;
  	var TNameReg=/^[0-9\u4e00-\u9fa5_ ]{0,20}$/;
  	if(editType=="copy"){
  		var CName=document.getElementById("gradeTemplCName2").value;
  		var EName=document.getElementById("gradeTemplEName2").value;
  		var TName=document.getElementById("gradeTemplTName2").value;
  		if(CName.replace(/\s+$|^\s+/g,"")==""){
  		      alert("岗位模板中文名称为必填项");
  		      return false;
  		   }
  		else if(CNameReg.test(CName)){
  			if(ENameReg.test(EName)){
  				if(TNameReg.test(TName)){
  					return true;
  				}else{
  					alert("请输入正确的岗位模板繁体名称");
  					return false;
  				}  				
  			}else{
  				alert("请输入正确的岗位模板英文名称");
  				return false;
  			}
  		}else{
  			alert("请输入正确的岗位模板中文名称");
  			return false;
  		}  	
  	}else if(editType=="insert"){
  		var CName=document.getElementById("gradeTemplCName").value;
  		var EName=document.getElementById("gradeTemplEName").value;
  		var TName=document.getElementById("gradeTemplTName").value;
  			if(CName.replace(/\s+$|^\s+/g,"")==""){
  		      alert("岗位模板中文名称为必填项");
  		      return false;
  		   }
  		else if(CNameReg.test(CName)){
  			if(ENameReg.test(EName)){
  				if(TNameReg.test(TName)){
  					return true;
  				}else{
  					alert("请输入正确的岗位模板繁体名称");
  					return false;
  				}
  			}else{
  				alert("请输入正确的岗位模板英文名称");
  				return false;
  			}
  		}else{
   			alert("请输入正确的岗位模板中文名称");
  			return false;
  		}
  	}else if(editType=="update"){
  		var CName=document.getElementById("gradeTemplCName3").value;
  		var EName=document.getElementById("gradeTemplEName3").value;
  		var TName=document.getElementById("gradeTemplTName3").value;
  			if(CName.replace(/\s+$|^\s+/g,"")==""){
  		      alert("岗位模板中文名称为必填项");
  		      return false;
  		   }
  	     else if(CNameReg.test(CName)){
  			if(ENameReg.test(EName)){
  				if(TNameReg.test(TName)){
  					return true;
  				}else{
  					alert("请输入正确的岗位模板繁体名称");
  					return false;
  				}
  			}else{
  				alert("请输入正确的岗位模板英文名称");
  				return false;
  			}
  		}else{
   			alert("请输入正确的岗位模板名称中文名称");
  			return false;
  		}
  	}
  }

  
  
  
</script>
