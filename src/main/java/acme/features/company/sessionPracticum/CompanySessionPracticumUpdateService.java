
package acme.features.company.sessionPracticum;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int idPracticum;
		Practicum practicum;
		idPracticum = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(idPracticum);
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int idSessionPracticum;
		idSessionPracticum = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionPracticumById(idSessionPracticum);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;
		super.bind(object, "title", "overview", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void validate(final SessionPracticum object) {
		Date date;
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "company.sessionPracticum.form.error.endAfterStart");
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			date = CompanySessionPracticumCreateService.plusOneWeek(MomentHelper.getCurrentMoment());
			super.state(object.getStartDate().after(date) || object.getStartDate().equals(date), "startDate", "company.sessionPracticum.form.error.oneWeekAhead");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			date = CompanySessionPracticumCreateService.plusOneWeek(object.getStartDate());
			super.state(object.getEndDate().after(date) || object.getStartDate().equals(date), "endDate", "company.sessionPracticum.form.error.oneWeekLong");
		}
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "overview", "startDate", "endDate", "moreInfo");
		super.getResponse().setData(tuple);
	}
}
