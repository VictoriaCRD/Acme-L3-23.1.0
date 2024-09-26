/*
 * AuthenticatedAnnouncementListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

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
		Collection<Activity> objects;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		objects = this.repository.findManyActivitiesByEnrolmentId(enrolmentId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "typeOfActivity", "initialDate", "finishDate");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {

		assert objects != null;

		int enrolmentId;
		Enrolment enrolment;
		final boolean showCreate;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		showCreate = !enrolment.getNotPublished() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setGlobal("enrolmentId", enrolmentId);
		super.getResponse().setGlobal("showCreate", showCreate);

	}

}
