import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Student_instantiatesCorrectly_true() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    assertEquals(true, myStudent instanceof Student);
  }

  @Test
  public void getName_studentInstantiatesWithName_String() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    assertEquals("Bob Smith", myStudent.getName());
  }

  @Test
  public void getDate_studentInstantiatesWithDate_String() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    assertEquals("1/01/2016", myStudent.getDate());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Student.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Student firstStudent = new Student("Bob Smith", "1/01/2016");
    Student secondStudent = new Student("Bob Smith", "1/01/2016");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    assertTrue(Student.all().get(0).equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }

  @Test
  public void find_findStudentInDatabase_true() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void addCourse_addsCourseToStudent_true() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myStudent.addCourse(myCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void getCourses_returnsAllCourses_List() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myStudent.addCourse(myCourse);
    List savedCourses = myStudent.getCourses();
    assertEquals(1, savedCourses.size());
  }

  @Test
  public void delete_deletesAllCoursesAndStudentsAssociations() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myStudent.addCourse(myCourse);
    myStudent.delete();
    assertEquals(0, myCourse.getStudents().size());
  }
}
