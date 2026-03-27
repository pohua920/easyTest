<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<s:if test="hasFieldErrors()">
	<span class="errorMessage"> <b>Errors:</b> <br> <s:iterator
			value="fieldErrors" id="error">
			<li>
				<s:property value="#error" />
			</li>
		</s:iterator> </span>
</s:if>
