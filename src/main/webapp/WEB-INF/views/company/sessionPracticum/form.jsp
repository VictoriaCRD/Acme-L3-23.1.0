<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
    <acme:input-textbox code="company.sessionPracticum.form.label.title" path="title"/>
    <acme:input-textbox code="company.sessionPracticum.form.label.overview" path="overview"/>
    <acme:input-moment code="company.sessionPracticum.form.label.startDate" path="startDate"/>
    <acme:input-moment code="company.sessionPracticum.form.label.endDate" path="endDate"/>
    <acme:input-url code="company.sessionPracticum.form.label.moreInfo" path="moreInfo"/>
    <jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.sessionPracticum.form.button.update" action="/company/session-practicum/update"/>
			<acme:submit code="company.sessionPracticum.form.button.delete" action="/company/session-practicum/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="company.sessionPracticum.form.button.create" action="/company/session-practicum/create?practicumId=${practicumId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum' && draftMode == false}">
			<acme:input-checkbox code="company.sessionPracticum.form.button.confirmation" path="confirmation"/>
			<acme:submit code="company.sessionPracticum.form.button.createAddendum" action="/company/session-practicum/create-addendum?practicumId=${practicumId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>