<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
	var $limitObject = $("body");
	var $hisPayObject = $("body");
	var $checkObject = $("body");
</script>
<c:forEach items="${requestScope.KindLimitMap}" var="map">
	<script language="javascript">jQuery.data($limitObject , "${map.key}" ,${map.value});</script>
</c:forEach>
<c:forEach items="${requestScope.KindHisPayMap}" var="map">
	<script language="javascript">jQuery.data($hisPayObject , "${map.key}" ,${map.value});</script>
</c:forEach>
<script language="javascript" src="${ctx}/pages/common/compensate/compensateLimit.js"></script>