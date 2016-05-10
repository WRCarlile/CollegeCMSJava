import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Register here!");
  }
//
//   @Test
//   public void studentIsCreatedTest() {
//     goTo("http://localhost:4567/");
//     click("a", withText("Students"));
//     fill("#name").with("Household chores");
//     submit(".btn");
//     assertThat(pageSource()).contains("Household chores");
//   }
//
//   @Test
//   public void courseIsCreatedTest() {
//     goTo("http://localhost:4567/");
//     click("a", withText("Courses"));
//     fill("#course_title").with("Mow the lawn");
//     submit(".btn");
//     assertThat(pageSource()).contains("Mow the lawn");
//   }
//
//   @Test
//   public void studentShowPageDisplaysName() {
//     Student testStudent = new Student("Household chores");
//     testStudent.save();
//     String url = String.format("http://localhost:4567/students/%d", testStudent.getId());
//     goTo(url);
//     assertThat(pageSource()).contains("Household chores");
//   }
//
//   @Test
//   public void courseShowPageDisplaysCourseTitle() {
//     Course testCourse = new Course("Mow the lawn");
//     testCourse.save();
//     String url = String.format("http://localhost:4567/courses/%d", testCourse.getId());
//     goTo(url);
//     assertThat(pageSource()).contains("Mow the lawn");
//   }
//
//   @Test
//   public void courseIsAddedToStudent() {
//     Student testStudent = new Student("Household chores");
//     testStudent.save();
//     Course testCourse = new Course("Mow the lawn");
//     testCourse.save();
//     String url = String.format("http://localhost:4567/students/%d", testStudent.getId());
//     goTo(url);
//     fillSelect("#course_id").withText("Mow the lawn");
//     submit(".btn");
//     assertThat(pageSource()).contains("<li>");
//     assertThat(pageSource()).contains("Mow the lawn");
//   }
//
//   @Test
//   public void studentIsAddedToCourse() {
//     Student testStudent = new Student("Household chores");
//     testStudent.save();
//     Course testCourse = new Course("Mow the lawn");
//     testCourse.save();
//     String url = String.format("http://localhost:4567/courses/%d", testCourse.getId());
//     goTo(url);
//     fillSelect("#student_id").withText("Household chores");
//     submit(".btn");
//     assertThat(pageSource()).contains("<li>");
//     assertThat(pageSource()).contains("Household chores");
//   }
//
}
