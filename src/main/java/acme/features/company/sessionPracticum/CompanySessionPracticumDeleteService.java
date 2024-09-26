
package acme.features.company.sessionPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumDeleteService extends AbstractService<Company, SessionPracticum> {

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
		int sessionPracticumId;
		Practicum practicum;
		sessionPracticumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final SessionPracticum object;
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
		assert object != null;

	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "overview", "startDate", "endDate", "moreInfo");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());
		super.getResponse().setData(tuple);
	}
}
