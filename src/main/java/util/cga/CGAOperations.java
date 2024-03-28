package util.cga;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOperations {

    public static <IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> IMultivectorSymbolic generalInverse(IMultivectorSymbolic mv) {
        IMultivectorSymbolic conjugate = mv.conjugate();
        IMultivectorSymbolic gradeInversion = mv.gradeInversion();
        IMultivectorSymbolic reversion = mv.reverse();
        IMultivectorSymbolic part1 = conjugate.gp(gradeInversion).gp(reversion);
        IMultivectorSymbolic part2 = mv.gp(part1);
        IMultivectorSymbolic part3 = part2.negate14();
        IMultivectorSymbolic scalar = part2.gp(part3).gradeSelection(0);
        return part1.gp(part3).gp(scalar.scalarInverse());
    }
}
