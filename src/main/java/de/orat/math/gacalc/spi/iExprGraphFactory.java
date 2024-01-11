package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
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

	default void init(Callback callback) {

	}

	String getAlgebra();

	String getName();

	MultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity);

	MultivectorSymbolic createMultivectorSymbolic(String name);

	MultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector);

	MultivectorSymbolic createMultivectorSymbolic(String name, int grade);

	/**
	 * Create a numeric multivector. Sparsity is created from zero values.
	 */
	MultivectorNumeric createMultivectorNumeric(double[] nonzeros, SparseDoubleColumnVector sparsity);

	MultivectorNumeric createMultivectorNumeric(double[] values);

	MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

	MultivectorNumeric createRandomMultivectorNumeric();

	FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorSymbolic> parameters,
		List<MultivectorSymbolic> returns);

	double[] createRandomCGAMultivector();
}
