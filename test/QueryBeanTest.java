import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

import models.Building;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class QueryBeanTest extends WithApplication {
  @Test
  public void queryBeans() {
    for (String n : new String[] {"Alpha", "Beta", "Atrium"}) {
      Building b = new Building();
      b.name = n;
      b.save();
    }
    Result result = route(app, new Http.RequestBuilder().uri("/"));
    assertEquals(OK, result.status());
    assertEquals("<ul><li>Alpha</li><li>Atrium</li></ul>", contentAsString(result).trim().replace("\n", ""));
  }
}
