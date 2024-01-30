package util.cga;

import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOperations {
    
    public static iMultivectorSymbolic generalInverse(iMultivectorSymbolic mv) {
        iMultivectorSymbolic conjugate = mv.conjugate();
        iMultivectorSymbolic gradeInversion = mv.gradeInversion();
        iMultivectorSymbolic reversion = mv.reverse();
        iMultivectorSymbolic part1 = conjugate.gp(gradeInversion).gp(reversion); 
        iMultivectorSymbolic part2 = mv.gp(part1); 
        iMultivectorSymbolic part3 = part2.negate14();
        iMultivectorSymbolic scalar = part2.gp(part3).gradeSelection(0); 
        return part1.gp(part3).gp(scalar.scalorInverse());
    }
}
