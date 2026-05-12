<#if parameters.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if parameters.align?exists>
    align="${parameters.align?html}"<#t/>
</#if>
<#if parameters.id?exists>
    id="wwctrl_${parameters.id}"<#rt/>
</#if>
><#t/>
<#if parameters.type?exists && parameters.type=="button">
<button type="submit"<#rt/>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#include "/template/custom/scripting-events.ftl"/>
<#include "/template/custom/common-attributes.ftl" />
><#if parameters.body?length gt 0><@s.property value="parameters.body"/><#elseif parameters.label?exists><@s.property value="parameters.label"/><#rt/></#if></button>
<#else>
<#if parameters.type?exists && parameters.type=="image">
<@s.property value="parameters.body"/>
<input type="image"<#rt/>
<#if parameters.label?exists>
 alt="${parameters.label?html}"<#rt/>
</#if>
<#if parameters.src?exists>
 src="${parameters.src?html}"<#rt/>
</#if>
<#else>
<input type="submit"<#rt/>
</#if>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/template/custom/scripting-events.ftl" />
<#include "/template/custom/common-attributes.ftl" />
/>
</#if>
<#if parameters.labelposition?default("top") == 'top'>
</div> <#t/>
<#else>
</span> <#t/>
</#if>
