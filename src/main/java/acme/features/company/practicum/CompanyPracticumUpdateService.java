
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum practicum;
		int practicumId;
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId() && practicum.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum practicum;
		int idPracticum;
		idPracticum = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(idPracticum);
		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "title", "overview", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		Collection<String> codes;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			codes = this.repository.findAllCodes();
			codes.remove(object.getCode());
			super.state(!codes.contains(object.getCode()), "code", "company.practicum.form.error.code");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "title", "overview", "goals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}
}
