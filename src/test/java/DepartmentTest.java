import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class DepartmentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Department_instantiatesCorrectly_true() {
    Department myDepartment = new Department("Math");
    assertEquals(true, myDepartment instanceof Department);
  }

  // @Test
  // public void getName_studentInstantiatesWithName_String() {
  //   Department myDepartment = new Department("Math");
  //   assertEquals("Math", myDepartment.getName());
  // }
  //
  // @Test
  // public void all_emptyAtFirst_0() {
  //   assertEquals(0, Department.all().size());
  // }
  //
  // @Test
  // public void equals_returnsTrueIfNamesAretheSame_true() {
  //   Department firstDepartment = new Department("Math");
  //   Department secondDepartment = new Department("Math");
  //   assertTrue(firstDepartment.equals(secondDepartment));
  // }
  //
  // @Test
  // public void save_savesObjectIntoDatabase_true() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   assertTrue(Department.all().get(0).equals(myDepartment));
  // }
  //
  // @Test
  // public void save_assignsIdToObject_int() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Department savedDepartment = Department.all().get(0);
  //   assertEquals(myDepartment.getId(), savedDepartment.getId());
  // }
  //
  // @Test
  // public void find_findDepartmentInDatabase_true() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Department savedDepartment = Department.find(myDepartment.getId());
  //   assertTrue(myDepartment.equals(savedDepartment));
  // }
  //
  // @Test
  // public void addCourse_addsCourseToDepartment_true() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Course myCourse = new Course("Biology", "BIO 101");
  //   myCourse.save();
  //   myDepartment.addCourse(myCourse);
  //   Course savedCourse = myDepartment.getCourses().get(0);
  //   assertTrue(myCourse.equals(savedCourse));
  // }
  //
  // @Test
  // public void addStudent_addsStudentToDepartment_true() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Student myStudent = new Student("Bob Smith", "1/01/2016");
  //   myStudent.save();
  //   myDepartment.addStudent(myStudent);
  //   Student savedStudent = myDepartment.getStudents().get(0);
  //   assertTrue(myStudent.equals(savedStudent));
  // }
  //
  // @Test
  // public void getCourses_returnsAllCourses_List() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Course myCourse = new Course("Biology", "BIO 101");
  //   myCourse.save();
  //   myDepartment.addCourse(myCourse);
  //   List savedCourses = myDepartment.getCourses();
  //   assertEquals(1, savedCourses.size());
  // }
  //
  // @Test
  // public void getStudents_returnsAllStudents_List() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Student myStudent = new Student("Bob Smith", "1/01/2016");
  //   myStudent.save();
  //   myDepartment.addStudent(myStudent);
  //   List savedStudents = myDepartment.getStudents();
  //   assertEquals(1, savedStudents.size());
  // }
  //
  // @Test
  // public void delete_deletesAllCoursesAndDepartmentsAssociations() {
  //   Department myDepartment = new Department("Math");
  //   myDepartment.save();
  //   Course myCourse = new Course("Biology", "BIO 101");
  //   myCourse.save();
  //   myDepartment.addCourse(myCourse);
  //   myDepartment.delete();
  //   assertEquals(0, myCourse.getDepartments().size());
  // }
}
