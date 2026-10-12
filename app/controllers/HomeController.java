package controllers;

import java.util.List;
import models.Building;
import models.query.QBuilding;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {
  public Result index() {
    List<Building> buildings = new QBuilding().name.startsWith("A").findList();
    return ok(views.html.index.render(buildings));
  }
}
