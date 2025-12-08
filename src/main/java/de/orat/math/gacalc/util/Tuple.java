package de.orat.math.gacalc.util;

import static de.orat.math.gacalc.util.GeometricObject.eps;
import java.util.Arrays;
import org.apache.commons.math3.util.Precision;

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
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<values.length-1;i++){
             sb.append(String.valueOf(values[i]));
             sb.append(", ");
        }
        sb.append(String.valueOf(values[values.length-1]));
        return sb.toString();
    }
    
    public Tuple sub(Tuple b){
        double[] newValues = new double[values.length];
        for (int i=0;i<values.length;i++){
             newValues[i] = values[i]-b.values[i];
        }
        return new Tuple(newValues);
    }
    public Tuple add(Tuple b){
        double[] newValues = new double[values.length];
        for (int i=0;i<values.length;i++){
             newValues[i] = values[i]+b.values[i];
        }
        return new Tuple(newValues);
    }
    
    public Tuple muls(double scalar){
        double[] newValues = new double[values.length];
        for (int i=0;i<values.length;i++){
             newValues[i] = values[i]*scalar;
        }
        return new Tuple(newValues);
    }
    public double squaredDist(Tuple b){
        Tuple v = b.sub(this);
        return v.squaredNorm();
    }
    
    public double squaredNorm(){
        double result = 0d; 
        for (int i=0;i<values.length;i++){
            result += values[i]*values[i];
        }
        return result;
    }
    public Tuple normalize(){
        double length = Math.sqrt(squaredNorm());
        double[] normalizedValues = new double[values.length];
        for (int i=0;i<values.length;i++){
            normalizedValues[i] = values[i]/length;
        }
        //return new Tuple(this.x/length, this.y/length, this.z/length);
        return new Tuple(normalizedValues);
    }
    public double ip(Tuple b){
        double result = 0;
        for (int i=0;i<values.length;i++){
             result += values[i]*b.values[i];
        }
        return result;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Arrays.hashCode(this.values);
        return hash;
    }
    
    public boolean isNull(){
        if (values.length > 0){
            for (int i=0;i<values.length;i++){
                if (!Precision.equals(values[i],0d, eps)) return false;
            }
        }
        return true;
    }
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Tuple otherObj = (Tuple) obj;
        
        if (values.length > 0){
            for (int i=0;i<values.length;i++){
                if (!Precision.equals(values[i], otherObj.values[i], eps)) return false;
            }
        }
        return true;
    }
}