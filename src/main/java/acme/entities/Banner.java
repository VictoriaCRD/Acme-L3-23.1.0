
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				moment;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayEnd;

	@URL
	@Transient
	protected String			picture;

	@URL
	protected String			link;
}
