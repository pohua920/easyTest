<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/pages/common/js/RiskTree.js"></script>
</head>
<body id="all_title" >
<form id="fm" name="fm">
<input type="hidden" name="allSelectFlag" id="allSelectFlag" value="0">
<div id="content" align="left">
${treeContent }
</div>
<input value="选 择" onclick="showSelectValue()" type="button" class="button_ty" />
</form>
</body>
</html>
<%@ include file="/common/i18njs.jsp"%>

