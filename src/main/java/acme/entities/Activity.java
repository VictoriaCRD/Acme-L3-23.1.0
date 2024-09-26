
package acme.entities;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			textAbstract;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishDate;

	@URL
	protected String			link;

	@NotNull
	protected EnumType			typeOfActivity;
	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getDurationInHours() {
		final Duration duration = MomentHelper.computeDuration(this.getInitialDate(), this.getFinishDate());
		final Long seconds = duration.getSeconds();
		return seconds.doubleValue() / 3600.;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Enrolment enrolment;

}
