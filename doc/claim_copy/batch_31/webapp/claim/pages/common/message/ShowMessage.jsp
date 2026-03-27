<%--
ÐÅÏ¢Òþ²ØÓò
--%>
<c:if test="${showMessage!=null||showMessage!='' }">
<input type="hidden" name="showMessage" value="${showMessage }">
</<c:if>
<c:if test="${showMessageList!=null }">
<c:forEach items="${showMessageList }" var="showEditMessage">
<input type="hidden" name="showEditMessage" value="${showEditMessage}">
</c:forEach>
</c:if>

