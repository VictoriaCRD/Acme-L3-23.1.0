
package acme.entities;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Statistic extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	int							counter;
	Double						average;
	Double						deviation;
	Double						minimum;
	Double						maximum;
}
