import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;


public class Department {
  private int id;
  private String name;

  public Department(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Department> all() {
    String sql = "SELECT id, name FROM departments;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Department.class);
    }
  }

  @Override
  public boolean equals(Object otherDepartment) {
    if (!(otherDepartment instanceof Department)) {
      return false;
    } else {
      Department newDepartment = (Department) otherDepartment;
      return this.getName().equals(newDepartment.getName()) &&
             this.getId() == newDepartment.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO departments(name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Department find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM departments where id=:id;";
      Department department = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Department.class);
      return department;
    }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        // .addParameter("student_id", student.getId())
        .addParameter("course_id", course.getId())
        .addParameter("department_id", this.getId())
        .executeUpdate();
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        // .addParameter("course_id", course.getId())
        .addParameter("department_id", this.getId())
        .addParameter("student_id", student.getId())
        .executeUpdate();
    }
  }

  public List<Course> getCourses() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT course_id FROM students_courses_departments WHERE department_id = :department_id;";
      List<Integer> courseIds = con.createQuery(joinQuery)
        .addParameter("department_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Course> courses = new ArrayList<Course>();

      for (Integer courseId : courseIds) {
        String courseQuery = "SELECT * FROM courses WHERE id = :courseId;";
        Course course = con.createQuery(courseQuery)
          .addParameter("courseId", courseId)
          .executeAndFetchFirst(Course.class);
        courses.add(course);
      }
      return courses;
    }
  }

  public List<Student> getStudents() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT student_id FROM students_courses_departments WHERE department_id = :department_id;";
      List<Integer> studentIds = con.createQuery(joinQuery)
        .addParameter("department_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
        String studentQuery = "SELECT * FROM students WHERE id = :studentId;";
        Student student = con.createQuery(studentQuery)
          .addParameter("studentId", studentId)
          .executeAndFetchFirst(Student.class);
        students.add(student);
      }
      return students;
    }
  }

  public void update(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE departments SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM departments WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students WHERE department_id = :departmentId";
        con.createQuery(joinDeleteQuery)
          .addParameter("departmentId", this.getId())
          .executeUpdate();
    }
  }
}
