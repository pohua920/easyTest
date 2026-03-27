<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<div id="loading"  style="z-index:9000;position:absolute;background:white url(${ctx}/widgets/loading/images/block-bg.gif) repeat-x;border:3px solid #6699cc;color:#215b06;width:180px;height:50px;left:45%;top:40%;padding:20px;font:bold 14px verdana,tahoma,helvetica; text-align:center;">
	<div style="font-size:8pt;background-image:url(${ctx}/widgets/loading/images/loading.gif);background-repeat: no-repeat;padding-left:20px;	height:18px;	text-align:left;">
		<%="\u52A0\u8F7D\u4E2D"%>...
	</div>
</div>
<script type="text/javascript">
/** deleteNode when page load*/
if (window.attachEvent) {   
   window.attachEvent("onload", delNode);   
} else if (window.addEventListener) {   
   window.addEventListener("load", delNode, false);    
}
/** deleteNode */
function  delNode(){   
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
</script>

