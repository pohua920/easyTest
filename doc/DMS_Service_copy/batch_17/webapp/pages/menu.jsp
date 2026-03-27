<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>


<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/pages/logo/style/basic.css"/>
<script type="text/javascript" src="${ctx}/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/widgets/yui/treeview/treeview-min.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/changestyle.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/date.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/tree_data.js"></script>
</head>
<body scroll="no">
<div id="container">
<!--Menu-->
<div id="navigation">
    <dl id="menu" class="dl_on">
        <dt class="floder">
            <span id="datePadding"><script type="text/javascript">document.write(y+"年"+m+"月"+d+"日");</script></span>
        </dt>
        <dd class="floder"><span onclick="hidden_menu('treediv0')">理赔管理</span>
            <div class="tree_on" id="treediv0"> </div>
        </dd>
        <dd class="floder"><span onclick="hidden_menu('treediv4')">报表单证管理</span>
            <div class="tree_off" id="treediv4"> </div>
        </dd>
        <dd class="floder">
            <span onclick="hidden_menu('treediv1')">工作流程管理</span>
            <div class="tree_off" id="treediv1"> </div>
        </dd>
        <dd class="floder">
            <span onclick="hidden_menu('treediv2')">密码管理</span>
            <div class="tree_off" id="treediv2"> </div>
        </dd>
        <dd class="floder">
            <span onclick="hidden_menu('treediv3')">权限管理</span>
	    <div class="tree_off" id="treediv3"> </div>
        </dd>
    </dl>
</div>
  <!--Menu End-->
</div>
</body>
</html>