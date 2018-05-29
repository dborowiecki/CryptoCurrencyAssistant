package models;

import java.util.LinkedList;
import java.util.Set;

public class DiagramModel {
    public LinkedList<Integer> values;

    private static LinkedList<Point> diagramPoints;

    public DiagramModel(){
        values = new LinkedList<>();
    }

    public void addValue(int value){
        values.add(value);
    }

    public DiagramModel getModel(){
        return this;
    }

    static {
        diagramPoints = new LinkedList<>();
        diagramPoints.add(new Point(1,1));
        diagramPoints.add(new Point(2,1));
        diagramPoints.add(new Point(3,1));
        diagramPoints.add(new Point(4,1));
    }

    public static LinkedList<Point> getAllPoints(){
        return diagramPoints;
    }
    public static void addPoint(Point p){
        diagramPoints.add(p);
    }

}
