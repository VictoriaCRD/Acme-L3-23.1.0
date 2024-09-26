
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.course.id = :courseId AND p.draftMode = false")
	Collection<Practicum> findFinishedPracticumsByCourse(int courseId);

	@Query("SELECT p FROM Practicum p WHERE p.id = :idPracticum")
	Practicum findOnePracticumById(int idPracticum);
}
