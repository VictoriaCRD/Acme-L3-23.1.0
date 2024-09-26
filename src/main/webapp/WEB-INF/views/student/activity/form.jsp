<%--
- form.jsp
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

<acme:form> 
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textbox code="student.activity.form.label.textAbstract" path="texAbstract"/>
	<acme:input-select code="student.activity.form.label.typeOfActivity" path="typeOfActivity" choices="${types}"/>
	<acme:input-moment code="student.activity.form.label.initialDate" path="initialDate" />
	<acme:input-moment code="student.activity.form.label.finishDate" path="finishDate"/>
		
	<jstl:if test="${_command == 'show'}">
		<acme:input-double code="student.activity.form.label.period" path="period" readonly="true"/>
	</jstl:if>
		<acme:input-textbox code="student.activity.form.label.link" path="link"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && notPublished == false}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activty/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>