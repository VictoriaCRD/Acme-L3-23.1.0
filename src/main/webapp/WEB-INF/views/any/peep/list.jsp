<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.startDate" path="startDate" width="5%"/>
	<acme:list-column code="any.peep.list.title" path="title" width="50%"/>
	<acme:list-column code="any.peep.list.nick" path="nick" width="45%"/>
</acme:list>
<acme:button code="any.peep.button.publish" action="/any/peep/create"/>	