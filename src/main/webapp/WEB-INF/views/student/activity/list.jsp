<%--
- list.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" width="25%"/>
	<acme:list-column code="student.activity.list.label.initialDate" path="initialDate" width="25%"/>
	<acme:list-column code="student.activity.list.label.typeOfActivity" path="typeOfActivity" width="25%"/>
	<acme:list-column code="student.activity.list.label.finishDate" path="finishDate" width="25%"/>
</acme:list>


<acme:button test="${showCreate}" code="typeOfActivity.list.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
<acme:button test="${showUpdate}" code="typeOfActivity.list.button.update" action="/student/activty/update"/>
<acme:button test="${showDelete}" code="typeOfActivity.list.button.delete" action="/student/activty/delete"/>

