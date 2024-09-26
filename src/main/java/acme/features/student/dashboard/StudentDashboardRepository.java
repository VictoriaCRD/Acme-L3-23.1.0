
package acme.features.student.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select a from Activity a where a.enrolment.student.id = :id")
	Collection<Activity> findActivitiesByStudentId(int id);

	@Query("select e.course from Enrolment e where e.notPublished = 0 and e.student.id = :id")
	Collection<Course> findEnrolledCoursesByStudentId(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

}
