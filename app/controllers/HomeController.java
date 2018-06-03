package controllers;

import models.Diagram.CurrencyLine;
import models.Diagram.DiagramModel;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
import services.DateFormater;
import views.html.Diagram.*;

import javax.inject.Inject;


public class HomeController extends Controller {

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
            dateTo = requestData.get("dateFrom") == null || requestData.get("dateFrom") == "" ?
                    DateFormater.dateToString(DateFormater.getCurrentDate(), "yyyy-MM-dd")
                    : requestData.get("dateTo");

            dateFrom   = requestData.get("dateTo") == null || requestData.get("dateTo") == "" ?
                    DateFormater.dateToString(
                          DateFormater.addDays(DateFormater.getCurrentDate(),-9), "yyyy-MM-dd"
                    )
                    : requestData.get("dateFrom");

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
