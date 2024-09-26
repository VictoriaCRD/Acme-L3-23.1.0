<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
    <acme:input-textbox code="authenticated.practicum.form.label.code" path="code"/>
    <acme:input-textbox  code="authenticated.practicum.form.label.title" path="title"/>
    <acme:input-textarea code="authenticated.practicum.form.label.overview" path="overview"/>
    <acme:input-textarea code="authenticated.practicum.form.label.goals" path="goals"/>
    <acme:input-textbox code="authenticated.practicum.form.label.company" path="company"/>
</acme:form>