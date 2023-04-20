
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.EnumType;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionShowService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

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
		int tutorialId;
		TutorialSession tutorialSession;
		final Tutorial tutorial;
		Assistant assistant;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorialSession = this.repository.findOneTutorialSessionById(tutorialId);
		tutorial = tutorialSession == null ? null : tutorialSession.getTutorial();
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorialSession != null && //
			tutorial != null && tutorial.getAssistant().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		Tutorial tutorial;
		boolean notPublished;
		final Double period;

		tutorial = object.getTutorial();
		notPublished = tutorial.getNotPublished();
		choices = SelectChoices.from(EnumType.class, object.getSessionType());
		period = object.getDurationInHours();

		tuple = super.unbind(object, "title", "abstractm", "sessionType", "startDate", "endDate", "link");
		tuple.put("period", period);
		tuple.put("types", choices);
		tuple.put("notPublished", notPublished);

		super.getResponse().setData(tuple);
	}

}