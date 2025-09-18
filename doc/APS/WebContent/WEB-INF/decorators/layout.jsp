<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="com.tlg.util.*,com.tlg.util.encoders.*"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<% 
String path = request.getContextPath();
InetAddress addr = InetAddress.getLocalHost();
String host = addr.getHostName();
String port = String.valueOf(request.getLocalPort());
String basePath = request.getScheme()+"://"+host+":"+port+path;
String css = path + "/styles/";
String js = path + "/js/";
session.setAttribute("basePath",basePath);
%>
<html>
<head>
	<meta http-equiv="x-ua-compatible" content="IE=edge">
	<!--  
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	-->
	<!-- mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 -->
	<title><decorator:title default="中國信託產險"/></title>
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${pageContext.request.contextPath}/styles/datePicker.css" rel="stylesheet" type="text/css" media="all"/>
	
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/decimal.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-3.1.0.min.js" type="text/javascript"></script>
	  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.12.1.custom.js" type="text/javascript"></script>
	<!--  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.datepick.js" type="text/javascript"></script>
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.datepick.tw.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI-2.70.0.js" type="text/javascript"></script>
		<!--  -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <!-- 
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.datePicker-2.1.2.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
    
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.floatinglayer.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.floatinglayer.pack.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.floatinglayer.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/utils.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cascade.ext.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cascade.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.templating.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectboxes.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.readonly.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/twzipcode-1.3.js" ></script>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/jquery.readonly.css">
     
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/jquery-ui-1.12.1.custom.css"/>
	
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/redmond.datepick.css"/>
	<!-- 
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/bootstrap.min.css">
 	-->
    <decorator:head/>
    <script>
    	var verifyPageNum = "";
		var verifyPageUser = "";
		var pdfRptFixParams = "";
		
	    $(document).keydown(function(e) {
	        var nodeName = e.target.nodeName.toLowerCase();
	        if (e.which === 8) {
	            if ((nodeName === 'input' && (e.target.type === 'text' || e.target.type === 'password')) 
	    	            || nodeName === 'textarea') {
	                // do nothing
	            } else {
	                e.preventDefault();
	            }
	        }
	    });
	    $(function(){
			<%
		    	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	    		if(userInfo != null){
	    			String str = BasicCode.encode(userInfo.getUserId(), userInfo.getLogId());
	    	%>	
	    		verifyPageNum = "<%=str%>";
				verifyPageUser = "<%=userInfo.getUserId()%>";

				$("iframe").each(function() {
					var osrc = $(this).attr('src');
					if(osrc != "#")
						return;
					
					var src = $(this).attr('longdesc');
					if(src.indexOf("javascript") != -1)
		    			return;
			    	if(src == "" || src == null)
				    	return;
			    	if(src.indexOf("#") != -1)
						return;
			    	if(src.indexOf("verifyPageNum") != -1)
						return;
			    	if(src.indexOf("?") == -1)
			    		src = src + "?";
			    	if(src.indexOf("&") != -1)
			    		src = src + "&";
			    	if(src.split("?")[1] != "")
			    		src = src + "&";

			    	src = src + "verifyPageNum=" + verifyPageNum + "&verifyPageUser=" + verifyPageUser + "&check=1&reset=2&anumber=<%=Math.round(Math.random() * 100000)%>";
			        $(this).attr('src',src);
				});
		    	$("a").click(function(e) {

		    		if(this.href.indexOf("javascript") != -1)
		    			return;
			    	if(this.href == "" || this.href == null)
				    	return;
			    	if(this.href.indexOf("#") != -1)
						return;
			    	if(this.href.indexOf("default.html") != -1)
						return;
			    	if(this.href.indexOf("verifyPageNum") != -1)
						return;
			    	if(this.href.indexOf("?") == -1)
			    		this.href = this.href + "?";
			    	if(this.href.indexOf("&") != -1)
			    		this.href = this.href + "&";
			    	if(this.href.split("?")[1] != "")
			    		this.href = this.href + "&";

			    	this.href = this.href + "verifyPageNum=" + verifyPageNum + "&verifyPageUser=" + verifyPageUser + "&reset=3";
		    	});

		    	$('form').submit(function() {

		    		$('<input>').attr({
			    	    type: 'hidden',
		    	    	id: 'verifyPageNum',
		    	    	name: 'verifyPageNum',
		    	    	value: verifyPageNum
		    		}).appendTo(this);
		    		$('<input>').attr({
			    	    type: 'hidden',
		    	    	id: 'verifyPageUser',
		    	    	name: 'verifyPageUser',
		    	    	value: verifyPageUser
		    		}).appendTo(this);
		    		$('<input>').attr({
			    	    type: 'hidden',
		    	    	id: 'reset',
		    	    	name: 'reset',
		    	    	value: '3'
		    		}).appendTo(this);
		    	});
				pdfRptFixParams = "&verifyPageNum=" + verifyPageNum + "&verifyPageUser=" + verifyPageUser + "&reset=3";
    		<%
		    	}else{
		    		//不檢查的程式
		    		if(request.getRequestURI().indexOf("FMN/fmn/103") == -1 
		    				&& request.getRequestURI().indexOf("/FMN/pages/OPW/OPW000/OPW000V0.jsp") == -1){
		    			session.invalidate();	
		    		}
		    	}
	    	%>
	    });

	    var originalcolor;
	    var originalfontcolor;
	    function GridOver(mobj)
	    {
	       originalcolor=mobj.style.backgroundColor;
	       mobj.style.backgroundColor="#eaf2ff";
	       originalfontcolor=mobj.style.color;
	       mobj.style.color="Red";
	    }
	    function GridOut(mobj)
	    {
	       mobj.style.backgroundColor=originalcolor;
	       mobj.style.color=originalfontcolor;
	    }
    </script>
</head>
<body style="text-align: left" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" topmargin="0" bottommargin="0">
<decorator:body />
</body>
</html>
