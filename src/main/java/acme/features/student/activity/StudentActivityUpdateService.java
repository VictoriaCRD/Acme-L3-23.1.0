/*
 * AuthenticatedConsumerUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

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
		Activity activity;
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
		Enrolment enrolment;

		choices = SelectChoices.from(EnumType.class, object.getTypeOfActivity());
		enrolment = object.getEnrolment();

		tuple = super.unbind(object, "title", "textAbstract", "typeOfActivity", "initialDate", "finishDate", "link");
		tuple.put("enrolmentId", enrolment.getId());
		tuple.put("notPublished", enrolment.getNotPublished());
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

}
