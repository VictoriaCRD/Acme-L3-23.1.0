<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="company.practicum.list.label.code" path="code" width="25%"/>
    <acme:list-column code="company.practicum.list.label.title" path="title" width="50%"/>
    <acme:list-column code="company.practicum.list.label.courseCode" path="courseCode" width="25%"/>
</acme:list>
<acme:button code="company.practicum.list.button.create" action="/company/practicum/create"/>