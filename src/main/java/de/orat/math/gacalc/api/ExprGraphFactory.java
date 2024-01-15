package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;
import util.cga.SparseCGAColumnVectorFactory;

public final class ExprGraphFactory {

	//======================================================
	// SPI boilerplate code
	//======================================================
	protected final iExprGraphFactory impl;

	protected static ExprGraphFactory get(iExprGraphFactory impl) {
		ExprGraphFactory result = new ExprGraphFactory(impl);
		Callback callback = new Callback(result);
		impl.init(callback);
		return result;
	}

	private ExprGraphFactory(iExprGraphFactory impl) {
		this.impl = impl;
	}

	public static final class Callback {

		private final ExprGraphFactory api;

		private Callback(ExprGraphFactory api) {
			this.api = api;
		}

		//TODO
		// add methods needed by the spi implementation
		public iExprGraphFactory getImpl() {
			return api.impl;
		}
	}

	//======================================================
	// Payload methods
	//======================================================
	public String getAlgebra() {
		return impl.getAlgebra();
	}

	public String getName() {
		return impl.getName();
	}

	public MultivectorSymbolic createMultivectorSymbolic(String name) {
		return impl.createMultivectorSymbolic(name);
	}

	public MultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity) {
		return impl.createMultivectorSymbolic(name, sparsity);
	}

	public MultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector) {
		return impl.createMultivectorSymbolic(name, sparseVector);
	}

	public MultivectorSymbolic createMultivectorSymbolic(String name, int grade) {
		return impl.createMultivectorSymbolic(name, grade);
	}

	public MultivectorNumeric createMultivectorNumeric(double[] values) {
		return impl.createMultivectorNumeric(values);
	}

	/**
	 * Create a numeric multivector. Sparsity is created from zero values.
	 */
	public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, SparseDoubleColumnVector sparsity) {
		return impl.createMultivectorNumeric(nonzeros, sparsity);
	}

	public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows) {
		return impl.createMultivectorNumeric(nonzeros, rows);
	}

	public MultivectorNumeric createRandomMultivectorNumeric() {
		return impl.createRandomMultivectorNumeric();
	}

	public double[] createRandomCGAMultivector() {
		return impl.createRandomCGAMultivector();
	}

	public FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorSymbolic> parameters,
		List<MultivectorSymbolic> returns) {
		return impl.createFunctionSymbolic(name, parameters, returns);
	}

	//======================================================
	// Symbolic scalar
	//======================================================
	public MultivectorSymbolic createScalarLiteral(String name, double scalar) {
		return this.createMultivectorSymbolic(name, SparseCGAColumnVectorFactory.scalar_ipns(scalar));
	}

	//======================================================
	// Symbolic constants
	//======================================================
	public final MultivectorSymbolic baseVectorOrigin = createBaseVectorOrigin();

	protected MultivectorSymbolic createBaseVectorOrigin() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorInfinity = createBaseVectorInfinity();

	protected MultivectorSymbolic createBaseVectorInfinity() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorX = createBaseVectorX();

	protected MultivectorSymbolic createBaseVectorX() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorY = createBaseVectorY();

	protected MultivectorSymbolic createBaseVectorY() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorZ = createBaseVectorZ();

	protected MultivectorSymbolic createBaseVectorZ() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic epsilonPlus = createEpsilonPlus();

	protected MultivectorSymbolic createEpsilonPlus() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic epsilonMinus = createEpsilonMinus();

	protected MultivectorSymbolic createEpsilonMinus() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorInfinityDorst = createBaseVectorInfinityDorst();

	protected MultivectorSymbolic createBaseVectorInfinityDorst() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorOriginDorst = createBaseVectorOriginDorst();

	protected MultivectorSymbolic createBaseVectorOriginDorst() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorInfinityDoran = createBaseVectorInfinityDoran();

	protected MultivectorSymbolic createBaseVectorInfinityDoran() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic baseVectorOriginDoran = createBaseVectorOriginDoran();

	protected MultivectorSymbolic createBaseVectorOriginDoran() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic pi = createPi();

	protected MultivectorSymbolic createPi() {
		return this.createScalarLiteral("Pi", Math.PI);
	}

	public final MultivectorSymbolic minkovskyBiVector = createMinkovskyBiVector();

	protected MultivectorSymbolic createMinkovskyBiVector() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic euclideanPseudoscalar = createEuclideanPseudoscalar();

	protected MultivectorSymbolic createEuclideanPseudoscalar() {
		throw new UnsupportedOperationException();
	}

	public final MultivectorSymbolic pseudoscalar = createPseudoscalar();

	protected MultivectorSymbolic createPseudoscalar() {
		throw new UnsupportedOperationException();
	}
}
