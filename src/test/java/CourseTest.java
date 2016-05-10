import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Course_instantiatesCorrectly_true() {
    Course myCourse = new Course("Mow the lawn");
    assertEquals(true, myCourse instanceof Course);
  }

  @Test
  public void getCourseName_courseInstantiatesWithCourseName_String() {
    Course myCourse = new Course("Mow the lawn");
    assertEquals("Mow the lawn", myCourse.getCourseName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Course.all().size());
  }

  @Test
  public void equals_returnsTrueIfCourseNamesAretheSame_true() {
    Course firstCourse = new Course("Mow the lawn");
    Course secondCourse = new Course("Mow the lawn");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertEquals(myCourse.getId(), savedCourse.getId());
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void update_updatesCourseCourseName_true() {
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    myCourse.update("Take a nap");
    assertEquals("Take a nap", Course.find(myCourse.getId()).getCourseName());
  }

  @Test
  public void delete_deletesCourse_true() {
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    int myCourseId = myCourse.getId();
    myCourse.delete();
    assertEquals(null, Course.find(myCourseId));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Student myStudent = new Student("Household chores");
    myStudent.save();
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    myCourse.addStudent(myStudent);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_List() {
    Student myStudent = new Student("Household chores");
    myStudent.save();
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents();
    assertEquals(1, savedStudents.size());
  }

  @Test
  public void delete_deletesAllCoursesAndStudentsAssociations() {
    Student myStudent = new Student("Household chores");
    myStudent.save();
    Course myCourse = new Course("Mow the lawn");
    myCourse.save();
    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(0, myStudent.getCourses().size());
  }


}
