package util.cga;

import util.cga.CGAMultivectorSparsity;
import util.cga.SparseCGAColumnVector;
import de.orat.math.gacalc.api.MultivectorNumeric;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * MultivectorNumeric kann leicht aus SparseCGAColumnVector mt der iExprGraphFactory erzeugt werden
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMultivectorNumericFactory {
    
    // aus Arguments class de.dhbw.rahmlab.geomalgelang.api;
    //TODO
    
    public static SparseCGAColumnVector euclidean_vector(String argName, Tuple3d tuple3d) {
        // createEx(v.x).add(createEy(v.y)).add(createEz(v.z)
        CGAMultivectorSparsity sparsity = new CGAMultivectorSparsity(new int[]{1,2,3});
        return new SparseCGAColumnVector(sparsity, new double[]{tuple3d.x, tuple3d.y, tuple3d.z});
    }

    public static MultivectorNumeric euclidean_bivector(String argName, Vector3d v1, Vector3d v2) {
        //var mvec = new CGAEuclideanBivector(v1, v2);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric scalar_opns(String argName, double scalar) {
        //var mvec = new CGAScalarOPNS(scalar);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric scalar_ipns(String argName, double scalar) {
        //var mvec = new CGAScalarIPNS(scalar);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric bool(String argName, boolean bool) {
        //var mvec = new CGABoolean(bool);
        throw new RuntimeException("not yet implemented!");
    }
    public static MultivectorNumeric createRoundPointIPNS(Point3d point){
        throw new RuntimeException("not yet implemented!");
        /*CGAMultivector result = (o
                .add(createEx(p.x))
                .add(createEy(p.y))
                .add(createEz(p.z))
                .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)))).gp(weight);
        return result;*/
    }
    public static MultivectorNumeric round_point_opns(String argName, Point3d point) {
        //var mvec = new CGARoundPointOPNS(point);
        throw new RuntimeException("not yet implemented!");
    }

    // point-pair
    public static MultivectorNumeric pointpair_opns(String argName, Point3d point1, double weight1,
        Point3d point2, double weight2) {
        //var mvec = new CGAPointPairOPNS(point1, weight1, point2, weight2);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric pointpair_opns(String argName, Point3d point1, Point3d point2) {
        //var mvec = new CGAPointPairOPNS(new CGARoundPointIPNS(point1), new CGARoundPointIPNS(point2));
        throw new RuntimeException("not yet implemented!");
    }

    // direction from point-2 to point-1
    public static MultivectorNumeric pointpair_ipns/*2*/(String argName, Point3d location, Vector3d normal, double radius) {
        //var mvec = new CGAPointPairIPNS(location, normal, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // via opns dual
    public static MultivectorNumeric pointpair_ipns(String argName, Point3d point1, Point3d point2) {
        //var mvec = new CGAPointPairOPNS(new CGARoundPointIPNS(point1),
        //	new CGARoundPointIPNS(point2)).dual();
        throw new RuntimeException("not yet implemented!");
    }

    // line
    public static MultivectorNumeric line_opns(String argName, Point3d point1, double weight1, Point3d point2, double weight2) {
        //var mvec = new CGALineOPNS(point1, weight1, point2, weight2);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric line_opns(String argName, Point3d point1, Point3d point2) {
        //var mvec = new CGALineOPNS(point1, point2);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric line_ipns(String argName, Point3d location, Vector3d normal) {
        //var mvec = new CGALineIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // sphere
    public static MultivectorNumeric sphere_ipns(String argName, Point3d center, double radius, double weight) {
        //var mvec = new CGASphereIPNS(center, radius, weight);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric sphere_opns(String argName, Point3d center, double radius) {
        //var mvec = new CGASphereOPNS(center, radius);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric sphere_ipns(String argName, Point3d center, double radius) {
        //var mvec = new CGASphereIPNS(center, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // plane
    public static MultivectorNumeric plane_ipns(String argName, Vector3d normal, double dist, double weight) {
        //var mvec = new CGAPlaneIPNS(normal, dist, weight);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric plane_ipns(String argName, Vector3d normal, double dist) {
        //return this.plane_ipns(argName, normal, dist, 1.0);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric plane_ipns(String argName, Point3d location, Vector3d normal) {
        //var mvec = new CGAPlaneIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric plane_opns(String argName, Point3d location, Vector3d normal) {
        //var mvec = new CGAPlaneIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // circle
    public static MultivectorNumeric circle_opns(String argName, Point3d point1, double weight1,
            Point3d point2, double weight2, Point3d point3, double weight3) {
        //var mvec = new CGACircleOPNS(point1, weight1, point2, weight2, point3, weight3);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric circle_opns(String argName, Point3d point1, Point3d point2, Point3d point3) {
        //var mvec = new CGACircleOPNS(point1, point2, point3);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric circle_ipns(String argName, Point3d location, Vector3d normal, double radius) {
        //var mvec = new CGACircleIPNS(location, normal, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // oriented points
    public static MultivectorNumeric oriented_point_ipns(String argName, Point3d location, Vector3d normal) {
        //var mvec = new CGAOrientedPointIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // flat points
    public static MultivectorNumeric flat_point_ipns(String argName, Point3d location) {
        //var mvec = new CGAFlatPointIPNS(location);
        throw new RuntimeException("not yet implemented!");
    }

    // tangent
    public static MultivectorNumeric tangent_opns(String argName, Point3d location, Vector3d direction) {
        //var mvec = new CGATangentVectorOPNS(location, direction);
        throw new RuntimeException("not yet implemented!");
    }

    // attitude
    public static MultivectorNumeric attitude_ipns(String argName, Vector3d t) {
        //var mvec = new CGAAttitudeVectorIPNS(t);
        throw new RuntimeException("not yet implemented!");
    }

    public static MultivectorNumeric attitude_opns(String argName, Vector3d t) {
        //var mvec = new CGAAttitudeVectorOPNS(t);
        throw new RuntimeException("not yet implemented!");
    }

    // translation
    public static MultivectorNumeric translator(String argName, Vector3d point) {
        //var mvec = new CGATranslator(point);
        throw new RuntimeException("not yet implemented!");
    }
}
