import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Course {
  private int id;
  private String course_title;
  private String course_number;
  private boolean is_completed;


  public Course(String course_title, String course_number) {
    this.course_title = course_title;
    this.course_number = course_number;
  }

  public String getCourseTitle() {
    return course_title;
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
    String sql = "SELECT id, course_title, course_number FROM courses;";
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
      return this.getCourseTitle().equals(newCourse.getCourseTitle()) &&
             this.getId() == newCourse.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses(course_title, course_number) VALUES (:course_title, :course_number);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("course_title", this.course_title)
        .addParameter("course_number", this.course_number)

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

  public void update(String newCourseTitle, String newCourseNumber) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_title = :newCourseTitle, course_number = :newCourseNumber WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("newCourseTitle", newCourseTitle)
        .addParameter("newCourseNumber", newCourseNumber)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


  public void addStudent(Student student, Department department) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("course_id", this.getId())
        .addParameter("department_id", department.getId())
        .executeUpdate();
    }
  }

  public void addDepartment(Department department, Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("course_id", this.getId())
        .addParameter("department_id", department.getId())
        .executeUpdate();
    }
  }


  public List<Student> getStudents(){
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT student_id FROM students_courses_departments WHERE course_id = :course_id;";
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

  public List<Department> getDepartments(){
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT department_id FROM students_courses_departments WHERE course_id = :course_id;";
      List<Integer> departmentIds = con.createQuery(joinQuery)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);
      List<Department> departments = new ArrayList<Department>();
      for (Integer departmentId : departmentIds) {
        String departmentQuery = "Select * FROM departments WHERE id = :departmentId;";
        Department department = con.createQuery(departmentQuery)
          .addParameter("departmentId", departmentId)
          .executeAndFetchFirst(Department.class);
        departments.add(department);
      }
      return departments;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses_departments WHERE course_id = :courseId;";
        con.createQuery(joinDeleteQuery)
          .addParameter("courseId", this.getId())
          .executeUpdate();
    }
  }

}
