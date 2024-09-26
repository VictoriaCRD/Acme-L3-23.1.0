
package acme.features.company.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Practicum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPraticumController extends AbstractController<Company, Practicum> {

	@Autowired
	protected CompanyPracticumListService		listService;

	@Autowired
	protected CompanyPracticumShowService		showService;

	@Autowired
	protected CompanyPracticumCreateService		createService;

	@Autowired
	protected CompanyPracticumUpdateService		updateService;

	@Autowired
	protected CompanyPracticumPublishService	publishService;

	@Autowired
	protected CompanyPracticumDeleteService		deleteService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
