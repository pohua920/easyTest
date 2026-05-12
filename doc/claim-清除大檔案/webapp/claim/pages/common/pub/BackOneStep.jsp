<%--
 *************************************************************************
 * 程序名称: BackOneStep.jsp
 * 程序功能: 回退界面
 * 最近更新人: weishixin
 * 最近更新日期: 2004-08-23
 ****************************************************************************
--%>


<html>
    <head>
        <title>
        </title>
        <STYLE>BODY {
        	BORDER-TOP-WIDTH: 0px; 
        	BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 11pt; 	BORDER-BOTTOM-WIDTH: 0px; 
        	LINE-HEIGHT: 160%; 
        	FONT-FAMILY: "宋体"; 
        	BORDER-RIGHT-WIDTH: 0px;
           
        }
        </STYLE>
        <link href="/claim/css/Standard.css" rel="stylesheet" type="text/css">
        <script language="javascript">
        function windowBackOne() { <%
    		Object compensateFlag = request.getAttribute("strCompensateFlag");
    	if (compensateFlag != null) { %>
    			alert("牌号为：" + "<%= compensateFlag %>" + " 的车已出赔款计算书,不能再收集单证"); <%
    	} %>
    		window.history.back(1);
    	}
        </script>
    </head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="return windowBackOne();">
    </body>
</html>
