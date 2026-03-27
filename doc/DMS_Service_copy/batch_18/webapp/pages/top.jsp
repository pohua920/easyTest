<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>

<%
	String userCode=(String)session.getAttribute("UserCode");
	String comCode=(String)session.getAttribute("ComCode");

%>
<style type="text/css">
*{margin:0;padding:0px;}
body {
	background:url("nimg/top_repeatbg.jpg") no-repeat;
	height:61px;
}
#l {
	margin-left:120px;
	width:365px;
	height:61px;
	float:left;
	margin-top:-5px;
}
#l ul {
    list-style:none;
}
#l ul li {
    color:#FFF;
    font-size:12px;
    font-weight:bold;
}

#r {
	float:right;
	width:180px;
	height:61px;
	margin-top:-2px;
	
}
#r ul li {
    float:left;
    margin-right:20px;
    margin-left:0px;
    font-weight:bold;
    font-size:12px;

}
#r ul li a{
    color:#FFF;  
    text-decoration:none; 
    margin-left:6px;
}
#r ul li a:hover{
    color:#FFF;  
    text-decoration:underline; 
}
#png1 {
    height:61px;
    width:38px;
    float:left;
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='nimg/png1.png');
}
#png2 {
    height:61px;
    width:38px;  
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='nimg/png2.png');
}
#png3 {
    height:61px;
    width:38px; 
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='nimg/png3.png');
}
</style>
</head>
<body>
  <div id="l">
  <span id="png1"></span><br />
    <ul>
      <li>当前操作员：小飞侠 （<%=userCode %>）</li>
      <li>归属机构：中国人民保险公司广州分公司 （<%=comCode %>）</li>
    </ul>
  </div>
  <div id="r">
        <ul>
        <li><span id="png2"></span><br /><a href="#" title="资料">资料</a></li>
        <li><span id="png3"></span><br /><a href="#" title="退出">退出</a></li>
        </ul>
  </div>
</body>
</html>
