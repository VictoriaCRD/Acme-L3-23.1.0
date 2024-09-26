
package acme.features.student.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.entities.EnumType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("enrolmentId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		Enrolment enrolment;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		object = new Activity();
		object.setEnrolment(enrolment);

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

		if (!(super.getBuffer().getErrors().hasErrors("initialDate") || super.getBuffer().getErrors().hasErrors("finishDate"))) {
			final boolean endDateIsAfter = object.getFinishDate().after(object.getInitialDate());
			final Date currentDate = MomentHelper.getCurrentMoment();
			final Long durationSinceCurrentDate = MomentHelper.computeDuration(currentDate, object.getInitialDate()).toDays();
			final boolean startDateIsOneDayAhead = durationSinceCurrentDate.doubleValue() >= 1.;
			super.state(endDateIsAfter, "finishDate", "student.activity.form.error.finishDate");
			super.state(startDateIsOneDayAhead, "initialDate", "student.activity.form.error.initialDate");
			super.state(object.getDurationInHours() >= 1. && //
				object.getDurationInHours() <= 5., "finishDate", "student.activity.form.error.period");

		}

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(EnumType.class, object.getTypeOfActivity());

		tuple = super.unbind(object, "title", "textAbstract", "typeOfActivity", "initialDate", "finishDate", "link");
		tuple.put("enrolmentId", super.getRequest().getData("enrolmentId", int.class));
		tuple.put("types", choices);
		tuple.put("notPublished", object.getEnrolment().getNotPublished());

		super.getResponse().setData(tuple);
	}

}
