import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Course {
  private int id;
  private String course_name;
  private String course_number;
  private boolean is_completed;


  public Course(String course_name, String course_number) {
    this.course_name = course_name;
    this.course_number = course_number;
  }

  public String getCourseName() {
    return course_name;
  }

  public String getCourseNumber() {
    return course_number;
  }

  public int getId() {
    return id;
  }

  public boolean getIsCompleted(){
    return is_completed;
  }

  public void complete(){
    if (this.getIsCompleted() ){
      this.is_completed = false;
    } else {
      this.is_completed = true ;
    }

    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET is_completed = :is_completed WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("is_completed", this.is_completed)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Course> all() {
    String sql = "SELECT id, course_name, course_number FROM courses;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getCourseName().equals(newCourse.getCourseName()) &&
             this.getId() == newCourse.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses(course_name, course_number, is_completed) VALUES (:course_name, :course_number; :is_completed);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("course_name", this.course_name)
        .addParameter("course_number", this.course_number)
        .addParameter("is_completed", this.is_completed)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id;";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void update(String newCourseName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_name = :newCourseName, course_number = :newCourseNumber WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("newCourseName", newCourseName)
        .addParameter("newCourseNumber", newCourseNumber)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (:student_id, :course_id);";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("course_id", this.getId())
        .executeUpdate();
    }
  }


  public List<Student> getStudents(){
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT student_id FROM students_courses WHERE course_id = :course_id;";
      List<Integer> studentIds = con.createQuery(joinQuery)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);


      List<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
        String studentQuery = "Select * FROM students WHERE id = :studentId;";
        Student student = con.createQuery(studentQuery)
          .addParameter("studentId", studentId)
          .executeAndFetchFirst(Student.class);
        students.add(student);
      }

      return students;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses WHERE course_id = :courseId;";
        con.createQuery(joinDeleteQuery)
          .addParameter("courseId", this.getId())
          .executeUpdate();
    }
  }

}
