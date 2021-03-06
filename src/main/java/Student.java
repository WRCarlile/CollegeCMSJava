import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;


public class Student {
  private int id;
  private String name;
  private String date;

  public Student(String name, String date) {
    this.name = name;
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Student> all() {
    String sql = "SELECT id, name, date FROM students;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent) {
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getId() == newStudent.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(name, date) VALUES (:name, :date);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("date", this.date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id;";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void addCourse(Course course, Department department) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("course_id", course.getId())
        .addParameter("department_id", department.getId())
        .executeUpdate();
    }
  }
  public void addDepartment(Department department, Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses_departments (student_id, course_id, department_id) VALUES (:student_id, :course_id, :department_id);";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("course_id", course.getId())
        .addParameter("department_id", department.getId())
        .executeUpdate();
    }
  }
  //get courses with this student id
  public List<Course> getCourses() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT course_id FROM students_courses_departments WHERE student_id = :student_id;";
      List<Integer> courseIds = con.createQuery(joinQuery)
        .addParameter("student_id", this.getId())
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

  public List<Department> getDepartments() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT department_id FROM students_courses_departments WHERE student_id = :student_id;";
      List<Integer> departmentIds = con.createQuery(joinQuery)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);
      List<Department> departments = new ArrayList<Department>();
      for (Integer departmentId : departmentIds) {
        String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId;";
        Department department = con.createQuery(departmentQuery)
          .addParameter("departmentId", departmentId)
          .executeAndFetchFirst(Department.class);
        departments.add(department);
      }
      return departments;
    }
  }

  public void update(String newName, String newDate) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :newName, date = :newDate WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("newDate", newDate)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM students WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses_departments WHERE student_id = :studentId";
        con.createQuery(joinDeleteQuery)
          .addParameter("studentId", this.getId())
          .executeUpdate();
    }
  }
}
