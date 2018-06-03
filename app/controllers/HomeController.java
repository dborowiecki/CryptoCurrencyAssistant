package controllers;

import models.DiagramModel;
import services.Diagram.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
import services.*;
import views.html.Diagram.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.Date;


public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result index() {
        return diagram();
    }

    public Result diagram(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        Date dateFrom = null;
        Date dateTo = null;
        String d1 = "";
        String d2 = "";
        Integer showTrends = 0;
        DynamicForm period = formFactory.form();
        period.bindFromRequest("dateFrom", "dateTo", "showTrends");
        try {
            dateTo = requestData.get("dateTo") == null || requestData.get("dateTo") == "" ?
                    DateFormater.getCurrentDate()
                    : DateFormater.stringToDate(requestData.get("dateTo"), "yyyy-MM-dd");

            dateFrom   = requestData.get("dateFrom") == null || requestData.get("dateFrom") == "" ?
                          DateFormater.addDays(DateFormater.getCurrentDate(),-7)
                    : DateFormater.stringToDate(requestData.get("dateFrom"), "yyyy-MM-dd");

            showTrends = requestData.get("showTrends")!= null ? 1 : 0;
            d1 = DateFormater.dateToString(dateFrom);
            d2 = DateFormater.dateToString(dateTo);
        } catch (Exception e){
            System.err.println("Data format is invalid");
        }


        DiagramModel d = new DiagramModel("Coin Trends");

        d.addCurrencyInfo("BTCUSD", dateFrom, dateTo, new Color(180, 0, 0));
        d.addCurrencyInfo("ETHUSD", dateFrom, dateTo, new Color(0, 180, 0));
        d.addCurrencyInfo("LTCUSD", dateFrom, dateTo, new Color(0,0,180));

        if(showTrends==1) {
            d.createWeeklyTrendLines(d.currencyLines.get(0));
            d.createWeeklyTrendLines(d.currencyLines.get(1));
            d.createWeeklyTrendLines(d.currencyLines.get(2));
        }
        return ok(diagram.render(d1, d2, showTrends, d, period));
        //return ok("hello");
    }

    public Result update(){
        return TODO;
    }
}
