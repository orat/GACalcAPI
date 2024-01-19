package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic;
import de.orat.math.gacalc.api.MultivectorNumeric;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iExprGraphFactory {
    
    public String getAlgebra();
    public String getName();
    
    public MultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity);
    public MultivectorSymbolic createMultivectorSymbolic(String name);
    public MultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector);
    public MultivectorSymbolic createMultivectorSymbolic(String name, int grade);
    
    /**
     * Create a numeric multivector. Sparsity is created from zero values. 
     * 
     * @param values
     * @return 
     */
    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, SparseDoubleColumnVector sparsity);
    public MultivectorNumeric createMultivectorNumeric(double[] values);
    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);
    public MultivectorNumeric createRandomMultivectorNumeric();
    
    public FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorSymbolic> parameters,
                                           List<MultivectorSymbolic> returns);
    
    public double[] createRandomCGAMultivector();
}
