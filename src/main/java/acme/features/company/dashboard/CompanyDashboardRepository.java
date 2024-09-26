
package acme.features.company.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.TypeNature;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("SELECT avg(TIME_TO_SEC(TIMEDIFF(s.endDate, s.startDate)) / 3600.0)  FROM SessionPracticum s WHERE s.practicum.company.id = :companyId")
	Double avgTimeSessions(int companyId);

	@Query("SELECT count(p) FROM Practicum p WHERE p.company.id = :companyId and p.course.type = :nature")
	Integer numPracticumByType(int companyId, TypeNature nature);

	@Query("SELECT stddev(TIME_TO_SEC(TIMEDIFF(s.endDate, s.startDate)) / 3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId")
	Double stddevTimeSessions(int companyId);

	@Query("SELECT min(TIME_TO_SEC(TIMEDIFF(s.endDate, s.startDate)) / 3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId")
	Double minTimeSessions(int companyId);

	@Query("SELECT max((select sum(TIME_TO_SEC(TIMEDIFF(s.endDate,s.startDate)) /3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId and s.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	Double maxTimePracticum(int companyId);

	@Query("SELECT avg((select sum(TIME_TO_SEC(TIMEDIFF(s.endDate,s.startDate)) /3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId and s.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	Double avgTimePracticum(int companyId);

	@Query("SELECT max(TIME_TO_SEC(TIMEDIFF(s.endDate, s.startDate)) / 3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId")
	Double maxTimeSessions(int companyId);

	@Query("SELECT stddev((select sum(TIME_TO_SEC(TIMEDIFF(s.endDate,s.startDate)) /3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId and s.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	Double stddevTimePracticum(int companyId);

	@Query("SELECT min((select sum(TIME_TO_SEC(TIMEDIFF(s.endDate,s.startDate)) /3600.0) FROM SessionPracticum s WHERE s.practicum.company.id = :companyId and s.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	Double minTimePracticum(int companyId);

}
