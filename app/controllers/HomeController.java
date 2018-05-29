package controllers;

import models.DiagramModel;
import play.mvc.*;
import models.Point;
import views.html.Diagram.*;

import java.util.LinkedList;
import java.util.Set;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result diagram(String dateFrom, String dateTo){
        DiagramModel.addPoint(new Point(1, 4));
        LinkedList<Point> points = DiagramModel.getAllPoints() ;
        return ok(diagram.render(dateFrom, dateTo, points));
    }

    public Result update(){
        return TODO;
    }
}
