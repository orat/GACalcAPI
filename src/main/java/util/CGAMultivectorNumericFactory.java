package util;

import de.orat.math.gacalc.api.MultivectorNumeric;
import org.jogamp.vecmath.Point3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMultivectorNumericFactory {
    
    // aus Arguments class de.dhbw.rahmlab.geomalgelang.api;
    //TODO
    
    public static MultivectorNumeric createRoundPointIPNS(Point3d point){
        throw new RuntimeException("not yet implemented!");
        /*CGAMultivector result = (o
                .add(createEx(p.x))
                .add(createEy(p.y))
                .add(createEz(p.z))
                .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)))).gp(weight);
        return result;*/
    }
}
