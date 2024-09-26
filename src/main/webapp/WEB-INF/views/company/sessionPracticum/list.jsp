<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.sessionPracticum.list.label.addendum" path="addendum" width="5%"/>
    <acme:list-column code="company.sessionPracticum.list.label.title" path="title" width="15%"/>
    <acme:list-column code="company.sessionPracticum.list.label.overview" path="overview" width="60%"/>
    <acme:list-column code="company.sessionPracticum.list.label.practicum.title" path="practicum.title" width="20%"/>
</acme:list>
<acme:button test="${showCreate && draftMode}" code="company.sessionPracticum.list.button.create" action="/company/session-practicum/create?practicumId=${practicumId}"/>
<acme:button test="${showCreate && !draftMode}" code="company.sessionPracticum.list.button.createAddendum" action="/company/session-practicum/create-addendum?practicumId=${practicumId}"/>

