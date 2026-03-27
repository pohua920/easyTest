<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>登录页面</title>
<%@ include file="/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/pages/logo/style/basic.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/pages/logo/style/layout.css"/>
<script type="text/javascript" src="${ctx}/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/widgets/yui/treeview/treeview-min.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/changestyle.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/date.js"></script>

</head>
<body scroll="no">
  <!--Info-->
    <div id="infoWrapper"> 
      <script type="text/javascript" src="${ctx}/pages/logo/js/info.js" ></script>
      <div id="crash_menu">
        <h2>您现在的位置是：<span>查勘/定损/人伤跟踪/核损/医疗审核任务</span></h2>
      </div>
      <!--弹出小提示-->
      <div id="systom_tip" class="clearfix"><b></b><!--not delete this line-->
        <div class="off" id="marqueebox1">
        系统提示：11111111111111111111111111111<br />
		系统提示：222222222222222222222222222222<br />
		系统提示：333333333333333333333<br />
		系统提示：444444444444444<br />
		系统提示：555555555555555555<br />
		系统提示：6666666666666666666<br />
		系统提示：77777777777777777<br />
        </div>
		<script type="text/javascript" src="${ctx}/pages/logo/js/markee.js" ></script>
        <div class="tip_popup" id="tip_pop"> <em class="clearfix"><img src="${ctx}/pages/logo/image/close.gif" onclick="closetip('tip_pop','marqueebox1');" alt="关闭"/></em>
          <p>系统提示：</p>
        </div>
        <div id="displayMenu"><img src="${ctx}/pages/nimg/switch_menu_hide.jpg" title="隐藏菜单" onclick="showMenu(this)"/></div>      
      </div>
      <!--End-->
</div>
  <!--Info End-->
  
</body>
</html>