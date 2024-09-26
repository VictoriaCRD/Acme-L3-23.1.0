/*
 * AuthenticatedConsumerRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	Activity findOneActivityById(int id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findOneStudentById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	Collection<Activity> findManyActivitiesByEnrolmentId(int id);

}
