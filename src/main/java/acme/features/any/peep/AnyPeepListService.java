
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepListService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


	@Override
	public void check() {
		final boolean status = true;
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = true;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Peep> objects;
		objects = this.repository.findAllPublishedPeeps();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "moment", "title", "nick");
		super.getResponse().setData(tuple);
	}

}
