<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SAA</title>
<%@ page contentType="text/html;charset=utf-8" buffer="50kb"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp" %>
<link href="${ctx}/pages/style/mian_demo.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript">
		function doExit(){	
			if(confirm("确定要重新登录?")){
				parent.window.location.href="${ctx}/logout";
				parent.window.opener = null;
				//parent.window.close();
			}else{
				return false;
			}
		}
		
		function setWinSize(url)
		{			
			window.open(url, "newwindow", "height=300, width=500, top=300, left=500, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no") ;
			
		}
		
		function switchMenu(field){
			if(field.value=="Open"){
				field.src="image/btnopenmenu.gif";		
				top.frames["menuFrame"].cols="0,*";
				field.value="Close";
			}else{
				field.src="image/btnclosemenu.gif";
				top.frames["menuFrame"].cols="246,*";
				field.value="Open";
			}
		}

	</script>  
 <style>
 a:link {font-size:12px; text-decoration: none; font-family: "宋体";color:#046678}
 a:visited {font-size: 12px; text-decoration: none; font-family: "宋体";color:#046678 }
 a:hover {font-size: 12px; color: #000000; font-family: "宋体"; position: relative;  }
</style>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="image/top_bg1.jpg"> 
<tr>
    <td width="522"><img src="image/top_02.jpg" align="absmiddle" /></td>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="date" > 
                <img src="image/imgname.gif" align="absmiddle"  />  ${UserName}(${UserCode})&nbsp;&nbsp;&nbsp;&nbsp;
                <img src="image/imgname.gif" align="absmiddle"  /> ${ComCName}(${ComCode})
		</td>
	  </tr>
      <tr>
	    <td align="right" style="padding-right:50px;"> 
           <a href="#" onclick="doExit()"><img src="image/icon_04.png" align="absmiddle" border="0"/></a>&nbsp;
           <!-- modify by duanfa20110806 a href="mailto:reagan006@gmail.com"  -->
						 <a href="mailto:xiaon@ccic-net.com.cn" ><img src="image/icon_03.png" align="absmiddle" border="0"/></a>&nbsp;
						 <a href="#" onclick="setWinSize('${ctx}/pages/Download.jsp')" ><img src="image/icon_02.png" align="absmiddle" border="0"/></a>&nbsp;
		</td>
      </tr>
      </table>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="15%" align="center" valign="bottom" class="place">
	  <input name="menuSwitch" type="image" onclick="switchMenu(this);" value="Open" src="image/btnclosemenu.gif" ></td>
        <td class="place" width="85%"><img src="image/icon_06.gif" align="absmiddle"  border="0"/>&nbsp;&nbsp;您当前的位置：<span align="left" id="CurrentPositionSpan">首页  </span></td>
    </tr>
</table>

</body>
</html>
