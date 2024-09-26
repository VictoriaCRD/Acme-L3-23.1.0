
package acme.features.administrator.systemConfiguration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorSystemConfigurationController extends AbstractController<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationShowService	showService;

	@Autowired
	protected AdministratorSystemConfigurationUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);

	}
}
