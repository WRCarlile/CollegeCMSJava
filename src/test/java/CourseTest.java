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
    Course myCourse = new Course("Biology", "BIO 101");
    assertEquals(true, myCourse instanceof Course);
  }

  @Test
  public void getCourseTitle_courseInstantiatesWithCourseTitle_String() {
    Course myCourse = new Course("Biology", "BIO 101");
    assertEquals("Biology", myCourse.getCourseTitle());
  }

  @Test
  public void getCourseNumber_courseInstantiatesWithCourseNumber_String() {
    Course myCourse = new Course("Biology", "BIO 101");
    assertEquals("BIO 101", myCourse.getCourseNumber());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Course.all().size());
  }

  @Test
  public void equals_returnsTrueIfCourseTitlesAretheSame_true() {
    Course firstCourse = new Course("Biology", "BIO 101");
    Course secondCourse = new Course("Biology", "BIO 101");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertEquals(myCourse.getId(), savedCourse.getId());
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void update_updatesCourseCourseTitle_true() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myCourse.update("History", "HS 101");
    assertEquals("History", Course.find(myCourse.getId()).getCourseTitle());
  }

  @Test
  public void update_updatesCourseCourseNumber_true() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myCourse.update("History", "HS 101");
    assertEquals("HS 101", Course.find(myCourse.getId()).getCourseNumber());
  }

  @Test
  public void delete_deletesCourse_true() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    int myCourseId = myCourse.getId();
    myCourse.delete();
    assertEquals(null, Course.find(myCourseId));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    Department myDepartment = new Department("Sciences");
    myDepartment.save();
    myCourse.addStudent(myStudent, myDepartment);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_List() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    Department myDepartment = new Department("Sciences");
    myDepartment.save();
    myCourse.addStudent(myStudent, myDepartment);
    List savedStudents = myCourse.getStudents();
    assertEquals(1, savedStudents.size());
  }

  @Test
  public void delete_deletesAllCoursesAndStudentsAssociations() {
    Student myStudent = new Student("Bob Smith", "1/01/2016");
    myStudent.save();
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    Department myDepartment = new Department("Sciences");
    myDepartment.save();
    myCourse.addStudent(myStudent, myDepartment);
    myCourse.delete();
    assertEquals(0, myStudent.getCourses().size());
  }

  @Test
  public void complete_isCompleted_false() {
    Course myCourse = new Course("Biology", "BIO 101");
    myCourse.save();
    myCourse.complete();
    assertEquals(true, Course.find(myCourse.getId()).getIsCompleted());
  }

}
