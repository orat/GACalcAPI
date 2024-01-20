package util.cga;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * MultivectorNumeric kann leicht aus SparseCGAColumnVector mit der 
 * iExprGraphFactory erzeugt werden.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparseCGAColumnVectorFactory {
    
    private static final CGACayleyTable cgaCayleyTable = CGACayleyTableGeometricProduct.instance();
    
    // aus Arguments class de.dhbw.rahmlab.geomalgelang.api;
    //TODO
    private static CGAMultivectorSparsity euclideanVectorSparsity = 
            new CGAMultivectorSparsity(new int[]{
             cgaCayleyTable.getBasisBladeRow("e1"),
             cgaCayleyTable.getBasisBladeRow("e2"),
             cgaCayleyTable.getBasisBladeRow("e3"),
    });
    public static SparseCGAColumnVector euclidean_vector(Tuple3d tuple3d) {
        // createEx(v.x).add(createEy(v.y)).add(createEz(v.z)
        return new SparseCGAColumnVector(euclideanVectorSparsity, 
                new double[]{tuple3d.x, tuple3d.y, tuple3d.z});
    }

    public static SparseCGAColumnVector euclidean_bivector(Vector3d v1, Vector3d v2) {
        //var mvec = new CGAEuclideanBivector(v1, v2);
        //ColumnVectorSparsity sparsity
        //CGACayleyTable.
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector scalar_opns(double scalar) {
        //var mvec = new CGAScalarOPNS(scalar);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector scalar_ipns(double scalar) {
        //var mvec = new CGAScalarIPNS(scalar);
        CGAMultivectorSparsity sparsity = new CGAMultivectorSparsity(new int[]{0});
        return new SparseCGAColumnVector(sparsity, new double[]{scalar});
    }

    public static SparseCGAColumnVector bool(boolean bool) {
        //var mvec = new CGABoolean(bool);
        throw new RuntimeException("not yet implemented!");
    }
    private static CGAMultivectorSparsity roundPointIPNSSparsity = 
            new CGAMultivectorSparsity(new int[]{
             cgaCayleyTable.getBasisBladeRow("e1"),
             cgaCayleyTable.getBasisBladeRow("e2"),
             cgaCayleyTable.getBasisBladeRow("e3"),
             cgaCayleyTable.getBasisBladeRow("e4"),
             cgaCayleyTable.getBasisBladeRow("e5"),
    });
    public static SparseCGAColumnVector createRoundPointIPNS(Point3d point){
        CGAMultivectorSparsity sparsity = roundPointIPNSSparsity;
        /*CGAMultivector result = (o
                .add(createEx(p.x))
                .add(createEy(p.y))
                .add(createEz(p.z))
                .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)))).gp(weight);
        return result;*/
        throw new RuntimeException("not yet implemented!");
    }
    public static SparseCGAColumnVector round_point_opns(Point3d point) {
        //var mvec = new CGARoundPointOPNS(point);
        throw new RuntimeException("not yet implemented!");
    }

    // point-pair
    public static SparseCGAColumnVector pointpair_opns(Point3d point1, double weight1,
        Point3d point2, double weight2) {
        //var mvec = new CGAPointPairOPNS(point1, weight1, point2, weight2);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector pointpair_opns(Point3d point1, Point3d point2) {
        //var mvec = new CGAPointPairOPNS(new CGARoundPointIPNS(point1), new CGARoundPointIPNS(point2));
        throw new RuntimeException("not yet implemented!");
    }

    // direction from point-2 to point-1
    public static SparseCGAColumnVector pointpair_ipns/*2*/(Point3d location, Vector3d normal, double radius) {
        //var mvec = new CGAPointPairIPNS(location, normal, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // via opns dual
    public static SparseCGAColumnVector pointpair_ipns(Point3d point1, Point3d point2) {
        //var mvec = new CGAPointPairOPNS(new CGARoundPointIPNS(point1),
        //	new CGARoundPointIPNS(point2)).dual();
        throw new RuntimeException("not yet implemented!");
    }

    // line
    public static SparseCGAColumnVector line_opns(Point3d point1, double weight1, Point3d point2, double weight2) {
        //var mvec = new CGALineOPNS(point1, weight1, point2, weight2);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector line_opns(Point3d point1, Point3d point2) {
        //var mvec = new CGALineOPNS(point1, point2);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector line_ipns(Point3d location, Vector3d normal) {
        //var mvec = new CGALineIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // sphere
    public static SparseCGAColumnVector sphere_ipns(Point3d center, double radius, double weight) {
        //var mvec = new CGASphereIPNS(center, radius, weight);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector sphere_opns(Point3d center, double radius) {
        //var mvec = new CGASphereOPNS(center, radius);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector sphere_ipns(Point3d center, double radius) {
        //var mvec = new CGASphereIPNS(center, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // plane
    public static SparseCGAColumnVector plane_ipns(Vector3d normal, double dist, double weight) {
        //var mvec = new CGAPlaneIPNS(normal, dist, weight);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector plane_ipns(Vector3d normal, double dist) {
        //return this.plane_ipns(argName, normal, dist, 1.0);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector plane_ipns(Point3d location, Vector3d normal) {
        //var mvec = new CGAPlaneIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector plane_opns(Point3d location, Vector3d normal) {
        //var mvec = new CGAPlaneIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // circle
    public static SparseCGAColumnVector circle_opns(Point3d point1, double weight1,
            Point3d point2, double weight2, Point3d point3, double weight3) {
        //var mvec = new CGACircleOPNS(point1, weight1, point2, weight2, point3, weight3);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector circle_opns(Point3d point1, Point3d point2, Point3d point3) {
        //var mvec = new CGACircleOPNS(point1, point2, point3);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector circle_ipns(Point3d location, Vector3d normal, double radius) {
        //var mvec = new CGACircleIPNS(location, normal, radius);
        throw new RuntimeException("not yet implemented!");
    }

    // oriented points
    public static SparseCGAColumnVector oriented_point_ipns(Point3d location, Vector3d normal) {
        //var mvec = new CGAOrientedPointIPNS(location, normal);
        throw new RuntimeException("not yet implemented!");
    }

    // flat points
    public static SparseCGAColumnVector flat_point_ipns(Point3d location) {
        //var mvec = new CGAFlatPointIPNS(location);
        throw new RuntimeException("not yet implemented!");
    }

    // tangent
    public static SparseCGAColumnVector tangent_opns(Point3d location, Vector3d direction) {
        //var mvec = new CGATangentVectorOPNS(location, direction);
        throw new RuntimeException("not yet implemented!");
    }

    // attitude
    public static SparseCGAColumnVector attitude_ipns(Vector3d t) {
        //var mvec = new CGAAttitudeVectorIPNS(t);
        throw new RuntimeException("not yet implemented!");
    }

    public static SparseCGAColumnVector attitude_opns(Vector3d t) {
        //var mvec = new CGAAttitudeVectorOPNS(t);
        throw new RuntimeException("not yet implemented!");
    }

    // translation
    public static SparseCGAColumnVector translator(Vector3d point) {
        //var mvec = new CGATranslator(point);
        throw new RuntimeException("not yet implemented!");
    }
}
