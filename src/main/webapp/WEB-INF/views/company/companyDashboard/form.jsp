<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="company.dashboard.title"/>
</h2>
<h3>
	<acme:message code="company.dashboard.practicum"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimePracticum.average"/>
		</th>
		<td>
			<acme:print value="${StatisticsPract.average}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimePracticum.minimum"/>
		</th>
		<td>
			<acme:print value="${StatisticsPract.minimum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimePracticum.maximum"/>
		</th>
		<td>
			<acme:print value="${StatisticsPract.maximum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimePracticum.deviation"/>
		</th>
		<td>
			<acme:print value="${StatisticsPract.deviation}"/>
		</td>
	</tr>	
</table>
<h3>
	<acme:message code="company.dashboard.sessions"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimeSession.average"/>
		</th>
		<td>
			<acme:print value="${StatisticsSess.average}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimeSession.minimum"/>
		</th>
		<td>
			<acme:print value="${StatisticsSess.minimum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimeSession.maximum"/>
		</th>
		<td>
			<acme:print value="${StatisticsSess.maximum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.label.statisticsTimeSession.deviation"/>
		</th>
		<td>
			<acme:print value="${StatisticsSess.deviation}"/>
		</td>
	</tr>	
</table>
<acme:return/>