package controllers;

import models.CurrencyLine;
import models.DiagramModel;
import models.DiagramPoint;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import services.DataFetcher;
import services.DateFormater;
import views.html.Diagram.*;

import javax.inject.Inject;
import java.util.Objects;


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
        return diagram();
    }

    public Result diagram(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String dateFrom = "";
        String dateTo = "";
        Integer showTrends = 0;
        DynamicForm period = formFactory.form();
        period.bindFromRequest("dateFrom", "dateTo", "showTrends");
        try {
            dateTo = requestData.get("dateFrom") == null ? DateFormater.dateToString(DateFormater.getCurrentDate(), "yyyy-MM-dd") :
             requestData.get("dateTo");
            dateFrom   = requestData.get("dateTo") == null ? DateFormater.dateToString(
                    DateFormater.addDays(DateFormater.getCurrentDate(),-9), "yyyy-MM-dd") :
            requestData.get("dateFrom");
            showTrends = requestData.get("showTrends")!= null ? 1 : 0;
        } catch (Exception e){
            System.err.println("Data format is invalid");
        }
        DiagramModel d = new DiagramModel("Coin Trends");
        CurrencyLine newLine;

        newLine = CurrencyLine.createNewLine("BTCUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(220,0,0);

        newLine = CurrencyLine.createNewLine("ETHUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(0,220,0);

        newLine = CurrencyLine.createNewLine("LTCUSD", dateFrom, dateTo);
        d.addCurrencyLine(newLine);
        newLine.setColor(0,0, 220);
        if(showTrends==1) {
            d.createWeeklyTrendLines(d.currencyLines.get(0));
            d.createWeeklyTrendLines(d.currencyLines.get(1));
            d.createWeeklyTrendLines(d.currencyLines.get(2));
        }

        return ok(diagram.render(dateFrom, dateTo, showTrends, d, period));
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
        period.bindFromRequest("dateFrom", "dateTo", "showTrends");
        return ok(formTest.render(period));

    }
}
