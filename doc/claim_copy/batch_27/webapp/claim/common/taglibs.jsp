<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="rc" uri="/WEB-INF/tlds/rc-date.tld"%>
<%@ taglib prefix="ad" uri="/WEB-INF/tlds/ad-date.tld"%>
<%@ taglib prefix="sui" uri="/sui-tags" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var contextRootPath = "${ctx}";
  var operateADD = "add";
  var operateUPDATE = "update";
  var operateVIEW = "view";
</script>

