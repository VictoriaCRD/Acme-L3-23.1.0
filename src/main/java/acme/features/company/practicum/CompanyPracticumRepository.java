
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.id = :idPracticum")
	Practicum findOnePracticumById(int idPracticum);

	@Query("SELECT c FROM Course c WHERE c.id = :idCourse")
	Course findOneCourseById(int idCourse);

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.id = :idPracticum")
	Collection<SessionPracticum> findSessionsByPracticumId(int idPracticum);

	@Query("SELECT c FROM Company c WHERE c.id = :idCompany")
	Company findOneCompanyById(int idCompany);

	@Query("SELECT sum(TIME_TO_SEC(TIMEDIFF(s.endDate, s.startDate))) FROM SessionPracticum s WHERE s.practicum.id= :practicumId")
	Double findEstimatedTimeSessionsPerPracticum(int practicumId);

	@Query("SELECT p.code FROM Practicum p")
	Collection<String> findAllCodes();

	@Query("SELECT p FROM Practicum p WHERE p.company.id= :activeCompanyId")
	Collection<Practicum> findPracticumByCompanyId(int activeCompanyId);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

}
