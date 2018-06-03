package models.Diagram;

public class DiagramPoint{
    public String x;
    public Number y;

    public DiagramPoint(String x, Number y){
        this.x = x;
        this.y = y;
    }

    public DiagramPoint getPoint(){
        return this;
    }
}
