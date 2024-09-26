
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepShowService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int idPeep;
		final Peep peep;
		idPeep = super.getRequest().getData("id", int.class);
		peep = this.repository.findOnePeepById(idPeep);
		status = peep != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Peep object;
		int idPeep;
		idPeep = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePeepById(idPeep);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "nick", "message", "email", "link");
		super.getResponse().setData(tuple);
	}
}
