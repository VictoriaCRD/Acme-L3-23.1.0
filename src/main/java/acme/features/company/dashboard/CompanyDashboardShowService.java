
package acme.features.company.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Statistic;
import acme.forms.CompanyDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int companyId;
		CompanyDashboard dashboard;
		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		final Statistic statisticsSess;
		final Statistic statisticsPract;

		statisticsSess = new Statistic();

		statisticsSess.setAverage(this.repository.avgTimeSessions(companyId));
		statisticsSess.setMinimum(this.repository.minTimeSessions(companyId));
		statisticsSess.setMaximum(this.repository.maxTimeSessions(companyId));
		statisticsSess.setDeviation(this.repository.stddevTimeSessions(companyId));

		statisticsPract = new Statistic();

		statisticsPract.setAverage(this.repository.avgTimePracticum(companyId));
		statisticsPract.setMinimum(this.repository.minTimePracticum(companyId));
		statisticsPract.setMaximum(this.repository.maxTimePracticum(companyId));
		statisticsPract.setDeviation(this.repository.stddevTimePracticum(companyId));

		dashboard = new CompanyDashboard();
		dashboard.setStatisticsSess(statisticsSess);
		dashboard.setStatisticsPract(statisticsPract);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "StatisticsSess", "StatisticsPract");

		super.getResponse().setData(tuple);
	}
}
