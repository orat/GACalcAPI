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
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.math3.util.Precision;

/**
 * Geomeric objects are visuable and independent of the algebra. But not each object type is 
 * available in every algebra.
 * 
 * TODO
 * - add Trasformations (Quaterinions, Matrix4x4 etc.), eventuell in eigener GeometricTrafo class, brauche ich
 *   vermutlich nicht für die Visualizer?
 * - eventuell an in der impl auch eine Methoden bauen, die aus GeometricObject ein multivektor zusammenbaut
 *   könnte nützlich sein, wenn im Visualisierer durchgeführte Änderungen ins Programm rückgespiegelt werden
 *   sollen.
 * - Umstellung auf das Builder-Pattern da die Konstruktoren in der bisherigen Impl. zu kompliziert geworden
 *   sind...
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class GeometricObject {
    
    public final GeometricType geometricType;
    public final Type type;
    public final Space space;
    public final Tuple attitude;
    public final Tuple[] location; // length==2 für dipole else typically 1
    public final double squaredSize; // > 0, if obj imaginary this is shown by tye
    public final double squaredWeight; // > 0, if weight is negative, this is shown by signOfWeight
    public final Sign signOfWeight;
    public final int grade;
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(geometricType.name());
        if (isIPNS()){
            sb.append("_IPNS");
        } else {
            sb.append("_OPNS");
        }
        sb.append("{");
        sb.append(type.name());
        
        sb.append(", ");
        if (attitude != null){
            sb.append("attitude=(");
            sb.append(attitude.toString());
            sb.append("), ");
        }
        sb.append("location=(");
        sb.append(location[0].toString());
        sb.append("), ");
        if (location.length == 2){
            sb.append("location2=(");
            sb.append(location[1].toString());
            sb.append("), ");
        }
        sb.append("squaredSize=");
        sb.append(String.valueOf(squaredSize));
        sb.append(", squaredWeight=");
        sb.append(String.valueOf(squaredWeight));
        sb.append(",  grade=");
        sb.append(String.valueOf(grade));
        sb.append("}");
        return sb.toString();
    }
    
    public int grade(){
        return grade;
    }
    
    //TODO besser in einem der Interfaces definieren?
    /**
     * Algebra's Precision.
     */
    public static double eps = 1e-6; // sollte eigentlich e-12 sein

    // with given type but not squaredSize
    public GeometricObject(GeometricType geometricType, boolean isIPNS, Tuple attitude, Tuple location, 
                   boolean isReal, double squaredWeight, Sign signOfWeight, int grade){
        this(geometricType, getType(isReal),getSpace(isIPNS), attitude, location, 
             Double.NaN, squaredWeight, signOfWeight, grade);
    }
    
    // Type determined from squaredSize, ipns given as boolean 
    public GeometricObject(GeometricType geometricType, boolean isIPNS, Tuple attitude, Tuple location, 
                   double squaredSize, double squaredWeight, Sign signOfWeight, int grade){
         this(geometricType, getSpace(isIPNS), attitude, location, squaredSize, squaredWeight, signOfWeight, grade);
    }
    // Type determined from squaredSize, ipns given as getSpace
    public GeometricObject(GeometricType geometricType, Space space, Tuple attitude, Tuple location, 
                    double squaredSize, double squaredWeight, Sign signOfWeight, int grade){
        this(geometricType, getTypeFromSquaredSize(squaredSize), space, attitude, location, 
                    squaredSize, squaredWeight, signOfWeight, grade);
    }
    // kann squaredSize überhaupt negativ werden?
    //TODO
    private static Type getTypeFromSquaredSize(double squaredSize){
        if (Double.isNaN(squaredSize)) //throw 
            //new IllegalArgumentException("Type can not be determined from squaredSize==NaN!");
            return Type.REAL; // flat point: squaredSize==NaN
        if (squaredSize < 0d) return Type.IMAGINARY;
        return Type.REAL;
    }
    private static Type getTypeFromRadius(double radius){
        if (Double.isNaN(radius)) throw 
            new IllegalArgumentException("Type can not be determined from squaredSize==NaN!");
        if (radius < 0d) return Type.IMAGINARY;
        return Type.REAL;
    }
    /**
     * Problem: mit squaredWeight geht das Vorzeichen verloren, wenn ich z.B. ein roundPoint erzeuge, dann 
     * brauche ich aber weight mit Vorzeichen. Hier bekomme ich aber nur squaredWeight und wenn ich die 
     * Wurzel ziehe, fehlt mir das Vorzeichen.
     * 
     * @param geometricType
     * @param type
     * @param space
     * @param attitude
     * @param location
     * @param squaredSize
     * @param squaredWeight 
     */
    private GeometricObject(GeometricType geometricType, Type type, Space space, Tuple attitude, Tuple location, 
                    double squaredSize, double squaredWeight, Sign signOfWeight, int grade){
        this.attitude = attitude;
        this.location = new Tuple[]{location};
        this.squaredSize = squaredSize;
        this.signOfWeight = signOfWeight;
        this.grade = grade;
        if (!Double.isNaN(squaredSize)){
            if (squaredSize < 0) {
                this.type = Type.IMAGINARY;
                if (!type.equals(Type.IMAGINARY)){
                    throw new IllegalArgumentException("The given type does not match the negative sign of the squaredSize argument!");
                }
            } else {
                this.type = Type.REAL;
                if (type.equals(Type.IMAGINARY)){
                    throw new IllegalArgumentException("The given type does not match the positive sign/or 0 of the squaredSize argument!");
                }
            }
        } else {
           this.type = type;
        }
        this.space = space;
        double pitch = -1;
        //TODO pitch vermutlich via squaredSize übergeben, unklar ist aber auch wie der
        // pitch überhaupt bestimmt werden kann
        switch (geometricType) {
            case SPHERE:
                if (Math.abs(squaredSize) < eps){
                    this.geometricType = ROUND_POINT;
                } else {
                    this.geometricType = geometricType;
                }   
                break;
            case SCREW:
                if (Math.abs(squaredSize) < eps){
                    this.geometricType = LINE;
                } else {
                    this.geometricType = geometricType;
                }   
                break;
            case CIRCLE:
                if (Math.abs(squaredSize) < eps){
                    this.geometricType = ORIENTED_POINT;
                } else {
                    this.geometricType = geometricType;
                }   break;
            default:
                this.geometricType = geometricType;
                break;
        }
        this.squaredWeight = squaredWeight;
    }
    
    // create DIPOLE
    public GeometricObject(boolean isIPNS, Type type, Tuple location1, Tuple location2, 
        double squaredWeight, Sign signOfWeight, int grade){
        this(getSpace(isIPNS), type, location1, location2, squaredWeight, signOfWeight, grade);
    }
    public GeometricObject(Space space, Type type, Tuple location1, Tuple location2,
        double squaredWeight, Sign signOfWeight, int grade){
        this.space = space;
        this.type = type;
        this.grade = grade;
        this.signOfWeight = signOfWeight;
        this.location = new Tuple[]{location1, location2};
        this.squaredSize = Math.sqrt(location1.sub(location2).squaredNorm())/4d;
        this.geometricType = GeometricType.DIPOLE;
        this.squaredWeight = squaredWeight;
        this.attitude = location2.sub(location1).normalize();
    }
    
    private static Space getSpace(boolean isIPNS){
        if (isIPNS) return Space.IPNS;
        return Space.OPNS;
    }
    private static Type getType(boolean isReal){
        if (isReal) return Type.REAL;
        return Type.IMAGINARY;
    }
    
    public double getSignedWeight(){
        double result = Math.sqrt(squaredWeight);
        if (signOfWeight.equals(Sign.NEGATIVE)){
            result = -result;
        } else if (signOfWeight.equals(Sign.UNKNOWN)){
            throw new IllegalArgumentException("getWeight() failed because sign of squaredWeight is unknown!");
        }
        return result;
    }
    public double getSquaredWeight(){
        return squaredWeight;
    }
    public boolean isWeightNegative(){
        return signOfWeight.equals(Sign.NEGATIVE);
    }
    
    public double getSignedSquaredSize(){
        double result = squaredSize;
        if (type.equals(Type.IMAGINARY)){
            result = -result;
        }
        return result;
    }
    
    public enum Space {IPNS, OPNS}
    public enum Type {REAL, IMAGINARY} // ergibt sich vermutlich aus dem Vorzeichen von sizeSquare, d.h. muss nicht in den Konstruktor mit aufgenommen werden
    
    public enum Sign {POSITIVE, NEGATIVE, UNKNOWN}
    
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
            case POINT:
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
    public enum GeometricType {POINT, ROUND_POINT, FLAT_POINT, PLANE, LINE, SCREW, CIRCLE, DIPOLE, SPHERE, 
                      ORIENTED_POINT}
    
                  
    // compose geometric objects
    
    // round
    public static GeometricObject createRoundPoint(double[] position,  boolean isIPNS, double squaredWeight, 
                                                   Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.ROUND_POINT, Type.REAL, 
                        getSpace(isIPNS), null, 
                       new Tuple(position), 0d, squaredWeight, signOfWeight, grade);
    }
    public static GeometricObject createPoint(double[] position,  boolean isIPNS, double squaredWeight, 
                                                   Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.POINT, Type.REAL, 
                        getSpace(isIPNS), null, 
                       new Tuple(position), 0d, squaredWeight, signOfWeight, grade);
    }
    // round
    public static GeometricObject createSphere(double[] position,  boolean isIPNS, double radius, 
                                               double squaredWeight, Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.SPHERE, getTypeFromRadius(radius),
                        getSpace(isIPNS), null, 
                       new Tuple(position), radius*radius, squaredWeight, signOfWeight, grade);
    }
    
    // flat
    public static GeometricObject createPlane(boolean isIPNS, double[] direction, double[] position, 
                                              double squaredWeight, Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.PLANE, Type.REAL, 
                      getSpace(isIPNS), new Tuple(direction), 
                       new Tuple(position), Double.NaN, squaredWeight, signOfWeight, grade);
    }
    
    // flat
    public static GeometricObject createLine(double[] position, double[] direction, boolean isIPNS,
        double squaredWeight, Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.LINE, Type.REAL, 
                      getSpace(isIPNS), new Tuple(direction), 
                       new Tuple(position), Double.NaN, squaredWeight, signOfWeight, grade);
    }
    // flat
    public static GeometricObject createScrewAxis(double[] position, double[] direction, double pitch, 
        boolean isIPNS, double squaredWeight, int grade){
        //TODO
        throw new RuntimeException("not yet implemented!");
    }
      
    //round
    public static GeometricObject createDipole(boolean isIPNS, boolean isReal, double[] position1, double[] position2, 
                                         double squaredWeight, Sign signOfWeight, int grade){
        Tuple p1 = new Tuple(position1);
        Tuple p2 = new Tuple(position2);
        return new GeometricObject(getSpace(isIPNS), getType(isReal), 
                       p1, p2,  squaredWeight,  signOfWeight, grade);
    }
    // flat
    public static GeometricObject createFlatPoint(boolean isIPNS, double[] position, double[] direction, 
                                         double squaredWeight, Sign signOfWeight, int grade){
        return new GeometricObject(GeometricType.FLAT_POINT, Type.REAL, getSpace(isIPNS), 
                    new Tuple(direction), new Tuple(position), Double.NaN, squaredWeight, signOfWeight, grade);
    }
    
    // round
    public static GeometricObject createCircle(boolean isIPNS, boolean isReal, double[] position, double[] direction, 
                                        double squaredRadius, double squaredWeight, Sign signOfWeight, int grade){
         return new GeometricObject(GeometricType.CIRCLE, getType(isReal), getSpace(isIPNS), 
                    new Tuple(direction), new Tuple(position), squaredRadius, squaredWeight, 
                                         signOfWeight, grade);
    }
    // round?
    public static GeometricObject createOrientedPoint(boolean isIPNS, boolean isReal, double[] position, 
                                        double[] direction, double squaredWeight, Sign signOfWeight, int grade){
         return new GeometricObject(GeometricType.ORIENTED_POINT, getType(isReal),
                                    getSpace(isIPNS), 
                    new Tuple(direction), new Tuple(position), 0d, squaredWeight, signOfWeight, grade);
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.geometricType);
        hash = 11 * hash + Objects.hashCode(this.type);
        hash = 11 * hash + Objects.hashCode(this.space);
        hash = 11 * hash + Objects.hashCode(this.attitude);
        hash = 11 * hash + Arrays.deepHashCode(this.location);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.squaredSize) ^ (Double.doubleToLongBits(this.squaredSize) >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.squaredWeight) ^ (Double.doubleToLongBits(this.squaredWeight) >>> 32));
        hash = 11 * hash + Objects.hashCode(this.signOfWeight);
        hash = 11 * hash + this.grade;
        return hash;
    }
    
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        GeometricObject otherObj = (GeometricObject) obj;
        
        
        if (!Double.isNaN(squaredSize) && !Double.isNaN(otherObj.squaredSize) &&
            !Precision.equals(squaredSize, otherObj.squaredSize, eps)) return false;
        
        // typicall is 1, only for dipoles length==2
        if (location.length != ((GeometricObject) obj).location.length) return false;
        
        Tuple diffLocation = location[0].sub(((GeometricObject) obj).location[0]);
        // test attitude and location
        switch (geometricType){
            case GeometricType.LINE:
            case GeometricType.SCREW:
                // test direction vector between the two locations should have the same direction as the attitude
                if (!diffLocation.isNull() && !diffLocation.normalize().
                    equals(attitude.normalize())) return false;
                break;
            case GeometricType.PLANE:
                if (!diffLocation.isNull() && !Precision.equals(diffLocation.
                    ip(attitude), 0d, eps)) {
                        System.out.println("plane: diffLocation!=null & equals attitude!");
                        return false;
                }
                break;
            // all of the objects which have 1 or 2 fix locations
            default:
                // length==1 or 2 (dipole)
                for (int i=0;i<location.length;i++){
                    if (!location[i].equals(((GeometricObject) obj).location[i])) return false;
                }
        }
        
        return new EqualsBuilder().append(geometricType, otherObj.geometricType).
            append(space, otherObj.space).
            append(type, otherObj.type).
            append(attitude, otherObj.attitude).
            // location is not included because plane, line can decompose different locations as used for creation
            //append(location, otherObj.location).
            append(squaredSize, otherObj.squaredSize).
            //FIXME squaredWeight ist nur raus, da das sowieso noch nicht funktioniert... und für den view
            // gar nicht gebraucht wird, aber z.B. für Massenpunkte etc. macht das einen Unterschied
            //append(squaredWeight, otherObj.squaredWeight).
            //append(signOfWeight, otherObj.signOfWeight).
            append(grade, otherObj.grade).isEquals();
    }
}