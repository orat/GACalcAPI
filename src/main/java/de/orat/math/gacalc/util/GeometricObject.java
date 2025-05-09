package de.orat.math.gacalc.util;

import static de.orat.math.gacalc.util.GeometricObject.GeometricType.CIRCLE;
import static de.orat.math.gacalc.util.GeometricObject.GeometricType.LINE;
import static de.orat.math.gacalc.util.GeometricObject.GeometricType.ORIENTED_POINT;
import static de.orat.math.gacalc.util.GeometricObject.GeometricType.ROUND_POINT;
import static de.orat.math.gacalc.util.GeometricObject.GeometricType.SCREW;
import static de.orat.math.gacalc.util.GeometricObject.GeometricType.SPHERE;
import static de.orat.math.gacalc.util.GeometricObject.Space.IPNS;
import static de.orat.math.gacalc.util.GeometricObject.Space.OPNS;
import static de.orat.math.gacalc.util.GeometricObject.Type.IMAGINARY;
import static de.orat.math.gacalc.util.GeometricObject.Type.REAL;

/**
 * Geomeric objects are visuable and independent of the algebra. But not each object type is 
 * available in every algebra.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class GeometricObject {
    
    public final GeometricType geometricType;
    private final Type type;
    private final Space space;
    public final Tuple3d attitude;
    public final Tuple3d[] location;
    public final double squaredSize;
    public final double squaredWeight;
    
    //TODO besser in einem der Interfaces definieren?
    /**
     * Algebra's Precision.
     */
    public static double eps = 1e-6; // sollte eigentlich e-12 sein

    public GeometricObject(GeometricType type, Space space, Tuple3d attitude, Tuple3d location, 
                    double squaredSize, double squaredWeight){
        this.attitude = attitude;
        this.location = new Tuple3d[]{location};
        this.squaredSize = squaredSize;
        if (squaredSize < 0) {
            this.type = Type.IMAGINARY;
        } else {
            this.type = Type.REAL;
        }
        this.space = space;
        double pitch = -1;
        //TODO pitch vermutlich via squaredSize übergeben, unklar ist aber auch wie der
        // pitch überhaupt bestimmt werden kann
        if (type.equals(SPHERE)){
            if (squaredSize < eps){
                this.geometricType = ROUND_POINT;
            } else {
                this.geometricType = type;
            }
        } else if (type.equals(SCREW)){
            if (squaredSize < eps){
                this.geometricType = LINE;
            } else {
                this.geometricType = type;
            }
        } else if (type.equals(CIRCLE)){
            if (squaredSize < eps){
                this.geometricType = ORIENTED_POINT;
            } else {
                this.geometricType = type;
            }
        } else {
            this.geometricType = type;
        }
        this.squaredWeight = squaredWeight;
    }
    
    public GeometricObject(Space space, Type type, Tuple3d location1, Tuple3d location2){
        this.space = space;
        this.type = type;
        this.location = new Tuple3d[]{location1, location2};
        this.squaredSize = Double.NaN;
        this.geometricType = GeometricType.DIPOLE;
        this.squaredWeight = Double.NaN;
        Tuple3d tuple3d = new Tuple3d(location2.x-location1.x, location2.y-location2.y, location2.z-location1.z);
        this.attitude = tuple3d.normalize();
    }
    
    public enum Space {IPNS, OPNS}
    public enum Type {REAL, IMAGINARY} // ergibt sich vermutlich aus dem Vorzeichen von sizeSquare, d.h. muss nicht in den Konstruktor mit aufgenommen werden
    
    public enum GeometricSubType {FLAT, ROUND}
    
    public boolean isImaginary(){
        return (type.equals(IMAGINARY));
    }
    public boolean isReal(){
        return (type.equals(REAL));
    }
    public boolean isIPNS(){
        return (space.equals(IPNS));
    }
    public boolean isOPNS(){
        return (space.equals(OPNS));
    }
    public boolean isFlat(){
        boolean result = true;
        switch (geometricType){
            case ROUND_POINT:
            case SPHERE:
            case ORIENTED_POINT:
            case CIRCLE:
            case DIPOLE:
               result = false;
        }
        return result;
    }
    public boolean isRound(){
        return !isFlat();
    }
    
    // dipole=point-pair
    // was ist mit ATTITUDE_SCALAR und ATTITUDE_TRIVECTOR
    // SCALAR, PSEUDO_SCALAR
    // ATTITUDE, ATTITUDE_BIVECTOR,
    public enum GeometricType {ROUND_POINT, FLAT_POINT, PLANE, LINE, SCREW, CIRCLE, DIPOLE, SPHERE, 
                      ORIENTED_POINT}
}