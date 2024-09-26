
package acme.features.company.sessionPracticum;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Addendum;
import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumCreateAddendumService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("practicumId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumingRecordId;
		Practicum practicum;
		practicumingRecordId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(practicumingRecordId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int practicumId;
		Practicum practicum;
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		object = new SessionPracticum();
		object.setPracticum(practicum);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;
		super.bind(object, "title", "overview", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;
		boolean confirmation;
		Date date;
		confirmation = object.getPracticum().getDraftMode() ? true : super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "company.sessionPracticum.form.error.confirmation");
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "company.sessionPracticum.form.error.endAfterStart");
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			date = CompanySessionPracticumCreateService.plusOneWeek(MomentHelper.getCurrentMoment());
			super.state(object.getStartDate().equals(date) || object.getStartDate().after(date), "startDate", "company.sessionPracticum.form.error.oneWeekAhead");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			date = CompanySessionPracticumCreateService.plusOneWeek(object.getStartDate());
			super.state(object.getStartDate().equals(date) || object.getEndDate().after(date), "endDate", "company.sessionPracticum.form.error.oneWeekLong");
		}
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		object.setAddendum(Addendum.O);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "overview", "startDate", "endDate", "moreInfo");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}
}
