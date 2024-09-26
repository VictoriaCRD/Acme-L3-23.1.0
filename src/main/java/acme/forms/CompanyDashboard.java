
package acme.forms;

import java.util.Map;

import acme.entities.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		totalNumberOfPracticalGroupedByMoth;

	Double						averagePeriorPracticum;
	Double						deviationPeriorOfPracticum;
	Double						minimumPeriodOfPracticum;
	Double						maximumPeriodOfPracticum;
	Double						averagePeriodOfSessions;
	Double						deviationPeriodOfSessions;
	Double						miniumPeriodOfSessions;
	Double						maximumPeriodOfSessions;
	Statistic					statisticsPract;
	Statistic					statisticsSess;
}
