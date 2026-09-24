package org.firstinspires.ftc.teamcode.Devices;

public class Vector2 {
    private double x,y;
    public Vector2(double x, double y){
        this.x = x;
        this.y =y;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getMag(){
        return Math.sqrt((x*x) + (y*y));
    }
    public double getMagSquared(){
        return getMag() * getMag();
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y=y;
    }
    public double getHeading(){
        return Math.atan2(y,x);
    }

    //operations
    public Vector2 add(Vector2 v2){
        return new Vector2(x + v2.getX(), y + v2.getY());
    }
    public Vector2 subtract(Vector2 other){
        return new Vector2(x- other.getX(), y-other.getY());
    }
    public Vector2 scalarMultiply(double scalar){
        return new Vector2(x*scalar, y*scalar);
    }
    public Vector2 normalize(){
        return new Vector2(x/getMag(), y/getMag());
    }
    public double[] toArray(){
        return new double[]{x,y};
    }

    @Override
    public String toString(){
        return "x: " + x + "\ny: " + y;
    }
}
