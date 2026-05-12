<link rel="stylesheet" href="<@s.url value='/struts/custom/styles.css' includeParams='none' encode='false' />" type="text/css"/>
<script language="JavaScript" type="text/javascript">
    // Dojo configuration
    djConfig = {
        baseRelativePath: "<@s.url includeParams='none' value='/struts/dojo' includeParams="none" encode='false'/>",
        isDebug: ${parameters.debug?default(false)},
        bindEncoding: "${parameters.encoding}",
        debugAtAllCosts: true // not needed, but allows the Venkman debugger to work with the includes
    };
</script>
<script language="JavaScript" type="text/javascript"
        src="<@s.url includeParams='none' value='/struts/dojo/dojo.js' includeParams="none" encode='false'/>"></script>
<script language="JavaScript" type="text/javascript"
        src="<@s.url includeParams='none' value='/struts/custom/dojoRequire.js' includeParams="none" encode='false'/>"></script>

