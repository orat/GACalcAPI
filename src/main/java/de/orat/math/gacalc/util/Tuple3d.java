package de.orat.math.gacalc.util;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */

public class Tuple3d {

    public final double x;
    public final double y;
    public final double z;

    public Tuple3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Tuple3d normalize(){
        double length = Math.sqrt(x*x + y*y + z*z);
        return new Tuple3d(this.x/length, this.y/length, this.z/length);
    }
}
