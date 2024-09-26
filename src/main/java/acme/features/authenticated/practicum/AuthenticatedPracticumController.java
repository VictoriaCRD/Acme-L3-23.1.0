
package acme.features.authenticated.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPracticumController extends AbstractController<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumListService	listService;

	@Autowired
	protected AuthenticatedPracticumShowService	showService;


	@PostConstruct
	protected void initialised() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
