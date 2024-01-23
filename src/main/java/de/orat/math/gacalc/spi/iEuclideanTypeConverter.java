package de.orat.math.gacalc.spi;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;
import util.cga.SparseCGAColumnVector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iEuclideanTypeConverter {
    
    public SparseCGAColumnVector euclidean_vector(Tuple3d tuple3d);

    public SparseCGAColumnVector euclidean_bivector(Vector3d v1, Vector3d v2);

    public SparseCGAColumnVector scalar_opns(double scalar);

    public SparseCGAColumnVector scalar_ipns(double scalar);

    public SparseCGAColumnVector bool(boolean bool);
    
    public SparseCGAColumnVector createRoundPointIPNS(Point3d point);
    public SparseCGAColumnVector round_point_opns(Point3d point);

    // point-pair
    public SparseCGAColumnVector pointpair_opns(Point3d point1, double weight1,
        Point3d point2, double weight2);

    public SparseCGAColumnVector pointpair_opns(Point3d point1, Point3d point2);

    // direction from point-2 to point-1
    public SparseCGAColumnVector pointpair_ipns/*2*/(Point3d location, Vector3d normal, double radius);
    // via opns dual
    public SparseCGAColumnVector pointpair_ipns(Point3d point1, Point3d point2);

    // line
    public SparseCGAColumnVector line_opns(Point3d point1, double weight1, 
            Point3d point2, double weight2);

    public SparseCGAColumnVector line_opns(Point3d point1, Point3d point2);

    public SparseCGAColumnVector line_ipns(Point3d location, Vector3d normal);

    // sphere
    public SparseCGAColumnVector sphere_ipns(Point3d center, double radius, double weight);

    public SparseCGAColumnVector sphere_opns(Point3d center, double radius);

    public SparseCGAColumnVector sphere_ipns(Point3d center, double radius);

    // plane
    public SparseCGAColumnVector plane_ipns(Vector3d normal, double dist, double weight);

    public SparseCGAColumnVector plane_ipns(Vector3d normal, double dist);

    public SparseCGAColumnVector plane_ipns(Point3d location, Vector3d normal);

    public SparseCGAColumnVector plane_opns(Point3d location, Vector3d normal);

    // circle
    public SparseCGAColumnVector circle_opns(Point3d point1, double weight1,
            Point3d point2, double weight2, Point3d point3, double weight3);

    public SparseCGAColumnVector circle_opns(Point3d point1, Point3d point2, Point3d point3);

    public SparseCGAColumnVector circle_ipns(Point3d location, Vector3d normal, double radius);

    // oriented points
    public SparseCGAColumnVector oriented_point_ipns(Point3d location, Vector3d normal);

    // flat points
    public SparseCGAColumnVector flat_point_ipns(Point3d location);

    // tangent
    public SparseCGAColumnVector tangent_opns(Point3d location, Vector3d direction);

    // attitude
    public SparseCGAColumnVector attitude_ipns(Vector3d t);

    public SparseCGAColumnVector attitude_opns(Vector3d t);

    // translation
    public SparseCGAColumnVector translator(Vector3d point);
}
