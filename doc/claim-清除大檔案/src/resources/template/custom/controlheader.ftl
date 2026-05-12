<#include "/template/custom/controlheader-core.ftl">
<#if parameters.labelposition?default("top") == 'top'>
<span <#rt/>
<#else>
<span <#rt/>
</#if>
<#if parameters.id?exists>id="wwctrl_${parameters.id}"<#rt/></#if> class="wwctrl">
    