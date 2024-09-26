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

package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.student.id = :id")
	Collection<Enrolment> findManyEnrolmentsByStudentId(int id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findOneStudentById(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT c FROM Course c WHERE c.notPublished = false")
	Collection<Course> findAllCourses();

	@Query("SELECT s FROM Activity s WHERE s.enrolment.id = :id")
	Collection<Activity> findManyActivitiesByEnrolmentId(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

}
