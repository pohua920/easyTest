<B>關貿第四次明信片排程啟動通知</B><BR/><BR/>

排程起始時間：${result.executeTime} <BR/>

<#if result.resultList?exists>
執行結果摘要：<BR/>
	<#list result.resultList as fileData>
			${fileData.filename} - 總筆數：${fileData.successCount} / ${fileData.dataCount}<BR/>
	</#list>
</#if>
<#if result.msg?exists>
<BR/>
${result.msg}<BR/>
</#if>
<BR/>