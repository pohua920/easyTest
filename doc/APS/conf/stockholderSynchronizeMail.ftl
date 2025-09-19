<B>金控利害關係人資料同步</B><BR/><BR/>

排程起始時間：${result.executeTime} <BR/>
排程結束時間：${result.finishTime} <BR/>
排程總花費秒數：${result.seconds} <BR/><BR/>

<#if result.resultList?exists>
執行結果摘要：<BR/>
	<#list result.resultList as fileData>
			${fileData.filename} - 總筆數：${fileData.successCount} / ${fileData.dataCount}<BR/>
	</#list>
</#if>
<BR/>