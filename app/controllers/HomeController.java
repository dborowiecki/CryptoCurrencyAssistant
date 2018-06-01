package controllers;

import models.CurrencyLine;
import models.DiagramModel;
import models.DiagramPoint;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import services.DataFetcher;
import views.html.Diagram.*;

import javax.inject.Inject;


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
    @Inject
    FormFactory formFactory;
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result diagram(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String firstname = requestData.get("dateFrom");
        String lastname = requestData.get("dateTo");
        System.out.print(firstname+" "+lastname);
        String dateFrom = firstname;//"2015-11-10";
        String dateTo = lastname;//"2018-03-15";

        DynamicForm period = formFactory.form();
        period.bindFromRequest("dateFrom", "dateTo");

        DiagramModel d = new DiagramModel("Coint Trends");
        CurrencyLine newLine;

        newLine = CurrencyLine.createNewLine("BTCUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(255,0,0);

        newLine = CurrencyLine.createNewLine("ETHUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(0,255,0);

        newLine = CurrencyLine.createNewLine("LTCUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(0,0, 255);

        return ok(diagram.render(firstname, lastname, d, period));
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

    public Result formTest(){
        DynamicForm period = formFactory.form();
        period.bindFromRequest("dateFrom", "dateTo");
        return ok(formTest.render(period));

    }
}
