package de.orat.math.gacalc.util;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Tuple {

    public final double[] values;

    public Tuple(double[] values){
        //this.values = values; //TODO copy array
        this.values = new double[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }
    public Tuple sub(Tuple b){
        double[] newValues = new double[values.length];
        for (int i=0;i<values.length;i++){
             newValues[i] = values[i]-b.values[i];
        }
        return new Tuple(newValues);
    }
    public Tuple normalize(){
        double length = 0d; //Math.sqrt(x*x + y*y + z*z);
        for (int i=0;i<values.length;i++){
            length += values[i]*values[i];
        }
        length = Math.sqrt(length);
        double[] normalizedValues = new double[values.length];
        for (int i=0;i<values.length;i++){
            normalizedValues[i] = values[i]/length;
        }
        //return new Tuple(this.x/length, this.y/length, this.z/length);
        return new Tuple(normalizedValues);
    }
}
