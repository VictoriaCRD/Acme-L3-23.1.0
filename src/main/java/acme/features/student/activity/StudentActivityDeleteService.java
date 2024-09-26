
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.entities.EnumType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		final Activity activity;
		final Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("id", int.class);
		activity = this.repository.findOneActivityById(enrolmentId);
		enrolment = activity == null ? null : activity.getEnrolment();
		student = enrolment == null ? null : enrolment.getStudent();
		status = activity != null && //
			enrolment != null && enrolment.getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "textAbstract", "typeOfActivity", "initialDate", "finishDate", "link");

	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		Enrolment enrolment;

		choices = SelectChoices.from(EnumType.class, object.getTypeOfActivity());
		enrolment = object.getEnrolment();

		tuple = super.unbind(object, "title", "textAbstract", "typeOfActivity", "initialDate", "finishDate", "link");
		tuple.put("types", choices.getSelected().getKey());
		tuple.put("notPublished", enrolment.getNotPublished());
		tuple.put("enrolmentId", enrolment.getId());

		super.getResponse().setData(tuple);
	}

}
