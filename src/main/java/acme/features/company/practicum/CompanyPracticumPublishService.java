
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

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
		Company company;
		int practicumId;
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(company);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int idPracticum;
		idPracticum = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(idPracticum);
		super.getBuffer().setData(object);
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
		final Collection<SessionPracticum> sessions = this.repository.findSessionsByPracticumId(object.getId());
		super.state(!sessions.isEmpty(), "*", "company.practicum.emptyPracticum");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Collection<Course> courses;
		SelectChoices sChoices;
		Tuple tuple;
		courses = this.repository.findAllCourses();
		sChoices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "title", "overview", "goals");
		tuple.put("course", sChoices.getSelected().getKey());
		tuple.put("courses", sChoices);
		super.getResponse().setData(tuple);
	}
}
