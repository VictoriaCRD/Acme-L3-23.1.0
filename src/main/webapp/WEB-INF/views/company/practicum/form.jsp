<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
    <acme:input-textbox code="company.practicum.form.label.code" path="code"/>
    <acme:input-textbox  code="company.practicum.form.label.title" path="title"/>
    <acme:input-double  code="company.practicum.form.label.estimatedTime" path="estimatedTime" readonly= "true"/>
    <acme:input-textarea code="company.practicum.form.label.overview" path="overview"/>
    <acme:input-textarea code="company.practicum.form.label.goals" path="goals"/>
    <acme:input-select code ="company.practicum.form.label.course" path="course" choices="${courses}"/>
    <jstl:choose>
    	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
			<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
			<acme:button code="company.practicum.form.button.listSessionPracticum" action="/company/session-practicum/list?practicumId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when> 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="company.practicum.form.button.listSessionPracticum" action="/company/session-practicum/list?practicumId=${id}"/>			
		</jstl:when>
    </jstl:choose>
</acme:form>