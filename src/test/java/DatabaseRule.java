import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/college_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteStudentsQuery = "DELETE FROM students *;";
      String deleteStudentsCoursesQuery = "DELETE FROM students_courses *;";
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteStudentsQuery).executeUpdate();
      con.createQuery(deleteStudentsCoursesQuery).executeUpdate();
    }
  }

}
