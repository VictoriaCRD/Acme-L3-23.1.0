
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Peep object;
		String nick;
		UserAccount userAccount;
		if (super.getRequest().getPrincipal().isAnonymous())
			nick = "";
		else {
			userAccount = this.repository.findOneUserAccountById(super.getRequest().getPrincipal().getAccountId());
			nick = userAccount.getIdentity().getName() + " " + userAccount.getIdentity().getSurname();
		}
		object = new Peep();
		object.setNick(nick);
		object.setStartDate(MomentHelper.getCurrentMoment());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;
		super.bind(object, "startDate", "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "startDate", "title", "nick", "message", "email", "link");
		super.getResponse().setData(tuple);
	}
}
