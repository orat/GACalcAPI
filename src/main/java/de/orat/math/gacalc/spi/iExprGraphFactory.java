package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic;
import de.orat.math.gacalc.api.MultivectorNumeric;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iExprGraphFactory {
    
    public MultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity);
    public MultivectorSymbolic createMultivectorSymbolic(String name);
    
    /**
     * Create a numeric multivector. Sparsity is created from zero values. 
     * 
     * @param values
     * @return 
     */
    public MultivectorNumeric createMultivectorNumeric(double[] values);
    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);
    public MultivectorNumeric createRandomMultivectorNumeric();
    
    public FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorSymbolic> parameters,
                                           List<MultivectorSymbolic> returns);
}
