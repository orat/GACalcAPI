package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {

	void init(Callback callback);

	@Override
	String toString();

	String getName();

	ColumnVectorSparsity getSparsity();

	// operators
	int grade();

	iMultivectorSymbolic gradeSelection(int grade);

	iMultivectorSymbolic gp(iMultivectorSymbolic rhs);

	iMultivectorSymbolic reverse();

	iMultivectorSymbolic gradeInversion();

	iMultivectorSymbolic pseudoscalar();

	iMultivectorSymbolic inversePseudoscalar();

	/**
	 * Dual.
	 *
	 * Poincare duality operator.
	 *
	 * @param a
	 * @return !a
	 */
	default iMultivectorSymbolic dual() {
		return lc(inversePseudoscalar());
	}

	/**
	 * Conjugate.
	 *
	 * Clifford Conjugation
	 *
	 * @param a
	 * @return a.Conjugate()
	 */
	iMultivectorSymbolic conjugate();

	// implementation works only for k-vectors
	// was tun, wenn die grade-summe größer als max grade/pseuscalar-grade?
	// max grade so oft abziehen bis < maxgrade erreicht wird, add(grade a, grade b)
	// in Cayleytable hinzufügen?
	default iMultivectorSymbolic op(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(grade() + b.grade());
	}

	/**
	 * left contraction.
	 *
	 * @param a
	 * @param b
	 * @return a | b
	 */
	default iMultivectorSymbolic lc(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(b.grade() - grade());
	}

	default iMultivectorSymbolic rc(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(grade() - b.grade());
	}

	/**
	 * Vee.
	 *
	 * The regressive product. (JOIN)
	 *
	 * @param a
	 * @param b
	 * @return a & b
	 */
	iMultivectorSymbolic vee(iMultivectorSymbolic b);

	/**
	 * Add.
	 *
	 * Multivector addition
	 *
	 * @param a
	 * @param b
	 * @return a + b
	 */
	iMultivectorSymbolic add(iMultivectorSymbolic b);

	/**
	 * Sub.
	 *
	 * Multivector subtraction
	 *
	 * @param a
	 * @param b
	 * @return a - b
	 */
	iMultivectorSymbolic sub(iMultivectorSymbolic b);

	// macht vermutlich nur Sinn für scalars
	iMultivectorSymbolic mul(iMultivectorSymbolic b);

	/**
	 * norm.
	 *
	 * Calculate the Euclidean norm. (strict positive).
	 */
	iMultivectorSymbolic norm();

	/**
	 * inorm.
	 *
	 * Calculate the Ideal norm. (signed)
	 */
	iMultivectorSymbolic inorm();

	/**
	 * normalized.
	 *
	 * Returns a normalized (Euclidean) element.
	 */
	iMultivectorSymbolic normalized();
}
