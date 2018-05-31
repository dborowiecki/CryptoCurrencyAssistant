package controllers;

import models.CurrencyLine;
import models.DiagramModel;
import models.DiagramPoint;
import play.mvc.*;
import services.DataFetcher;
import views.html.Diagram.*;

import java.io.IOException;
import java.util.Currency;
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
        DiagramModel d = new DiagramModel("Cryptocurrency");
        d.createCurrencyLine("BTCUSD");
        d.createCurrencyLine("ETHUSD");
        d.createCurrencyLine("LTCUSD");
        return ok(diagram.render(dateFrom, dateTo, d.getLines()));
        //return ok("hello");
    }

    public Result update(){
        return TODO;
    }

    public Result fetchTest(){
        DiagramModel d = new DiagramModel("Cryptocurrency");
        d.createCurrencyLine("BTCUSD");
        d.createCurrencyLine("ETHUSD");
        d.createCurrencyLine("LTCUSD");
        CurrencyLine test = d.getLines().get(0);//new CurrencyLine("BTCUSD");

        return ok(fetcher.render(d.getLines()));

    }
}
