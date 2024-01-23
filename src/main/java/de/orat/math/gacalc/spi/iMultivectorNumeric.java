package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorNumeric {

    void init(Callback callback);

    @Override
    String toString();

    //TODO
    // ich brauche hier doch auch sparsity
    
    double[] elements();
    
    //TODO
    // vermutlich brauche ich hier alle möglichen Operatoren aus iMultivectorSymbolic
    // um z.B. die Methoden in SparseCGAColumnVectorFactory implementieren zu können 
    
    iMultivectorNumeric op(iMultivectorNumeric mv);
}
