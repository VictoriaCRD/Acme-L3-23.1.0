
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.id = :companyId")
	Practicum findOnePracticumById(int companyId);

	@Query("SELECT s.practicum FROM SessionPracticum s WHERE s.id = :id")
	Practicum findOnePracticumBySessionPracticumId(int id);

	@Query("SELECT s FROM SessionPracticum s WHERE s.id = :id")
	SessionPracticum findOneSessionPracticumById(int id);

	@Query("SELECT s.practicum FROM SessionPracticum s WHERE s.id = :id")
	Practicum findPracticumBySessionPracticumId(int id);

	@Query("SELECT s FROM SessionPracticum s WHERE s.practicum.id = :practicumId")
	Collection<SessionPracticum> findSessionPracticumsByPracticumId(int practicumId);
}
