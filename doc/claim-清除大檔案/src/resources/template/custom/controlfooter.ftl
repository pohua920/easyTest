${parameters.after?if_exists}<#t/>
    <#lt/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
 <span class="required">*</span><#t/>
</#if>    
<#if parameters.labelposition?default("top") == 'top'>
</span><#rt/>
<#else>
</span><#rt/>
</#if>
</span>
