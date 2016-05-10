import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("students", Student.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String course_title = request.queryParams("course_title");
      String course_number = request.queryParams("course_number");

      Course newCourse = new Course(course_title, course_number);
      newCourse.save();
      response.redirect("/courses");
      return null;
    });

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String date = request.queryParams("date");
      Student newStudent = new Student(name, date);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

    get("/courses/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
      model.put("course", course);
      model.put("allStudents", Student.all());
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      model.put("student", student);
      model.put("allCourses", Course.all());
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_courses", (request, response) -> {
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int departmentId = Integer.parseInt(request.queryParams("department_id"));
      Student student = Student.find(studentId);
      Course course = Course.find(courseId);
      Department department = Department.find(departmentId);
      student.addCourse(course, department);
      response.redirect("/students/" + studentId);
      return null;
    });

    post("/add_students", (request, response) -> {
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int departmentId = Integer.parseInt(request.queryParams("department_id"));
      Student student = Student.find(studentId);
      Course course = Course.find(courseId);
      Department department = Department.find(departmentId);
      course.addStudent(student, department);
      response.redirect("/courses/" + courseId);
      return null;
    });
    post("/courses/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
      String course_title = request.queryParams("course_title");
      String course_number = request.queryParams("course_number");
      // Student student = Student.find(course.getStudentId());
      course.update(course_title, course_number);
      String url = String.format("/courses/%d", course.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
      course.delete();

      response.redirect("/courses");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses/:id/complete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
      course.complete();
      String url = String.format("/courses/%d", course.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      String studentName = request.queryParams("studentName");
      String studentDate = request.queryParams("studentDate");
      // Student student = Student.find(student.getStudentId());
      student.update(studentName, studentDate);
      String url = String.format("/students/%d", student.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params("id")));
      student.delete();

      response.redirect("/students");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
