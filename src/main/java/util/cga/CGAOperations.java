package util.cga;

import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOperations {
    
    /*public static MultivectorSymbolic generalInverse(MultivectorSymbolic mv) {
        MultivectorSymbolic conjugate = mv.cliffordConjugate();
        MultivectorSymbolic gradeInversion = mv.gradeInversion();
        MultivectorSymbolic reversion = mv.reverse();
        MultivectorSymbolic part1 = conjugate.geometricProduct(gradeInversion).geometricProduct(reversion); 
        MultivectorSymbolic part2 = mv.geometricProduct(part1); 
        MultivectorSymbolic part3 = part2.negate14();
        double scalar = part2.geometricProduct(part3).scalarPart(); 
        return part1.geometricProduct(part3).geometricProduct(1d/scalar);
    }*/
    
    public static iMultivectorSymbolic generalInverse(iMultivectorSymbolic mv) {
        iMultivectorSymbolic conjugate = mv.conjugate();
        iMultivectorSymbolic gradeInversion = mv.gradeInversion();
        iMultivectorSymbolic reversion = mv.reverse();
        iMultivectorSymbolic part1 = conjugate.gp(gradeInversion).gp(reversion); 
        iMultivectorSymbolic part2 = mv.gp(part1); 
        iMultivectorSymbolic part3 = part2.negate14();
        double scalar = part2.gp(part3).scalarPart(); 
        return part1.gp(part3).gp(1d/scalar);
    }
}
