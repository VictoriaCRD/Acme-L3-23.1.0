/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Tutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialController extends AbstractController<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialListService		listService;

	@Autowired
	protected AssistantTutorialShowService		showService;

	@Autowired
	protected AssistantTutorialUpdateService	updateService;

	@Autowired
	protected AssistantTutorialCreateService	createService;

	@Autowired
	protected AssistantTutorialDeleteService	deleteService;

	@Autowired
	protected AssistantTutorialPublishService	publishService;
	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}