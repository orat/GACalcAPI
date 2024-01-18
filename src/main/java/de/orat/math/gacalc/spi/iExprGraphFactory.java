package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iExprGraphFactory {

	default void init(Callback callback) {

	}

	String getAlgebra();

	String getName();

	iMultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity);

	iMultivectorSymbolic createMultivectorSymbolic(String name);

	iMultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector);

	iMultivectorSymbolic createMultivectorSymbolic(String name, int grade);

	/**
	 * Create a numeric multivector. Sparsity is created from zero values.
	 */
	iMultivectorNumeric createMultivectorNumeric(double[] nonzeros, SparseDoubleColumnVector sparsity);

	iMultivectorNumeric createMultivectorNumeric(double[] values);

	iMultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

	iMultivectorNumeric createRandomMultivectorNumeric();

	iFunctionSymbolic createFunctionSymbolic(String name, List<iMultivectorSymbolic> parameters,
		List<iMultivectorSymbolic> returns);

	double[] createRandomCGAMultivector();
}
