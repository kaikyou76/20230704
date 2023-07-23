<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>

<div id="header" class="container clearfix" style="min-width:300px">
	<div class="dialogTitle">
		<c:if test="${ not empty param.title }"><c:out value="${ param.title }" /></c:if>
	</div>
</div>