package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

/**
 * Parent interface. Not intended to be implemented directly.
 */
public interface iConstantsFactory<IMultivector extends iMultivector<IMultivector>> {

    iExprGraphFactory<?, ?, ?> fac();

    IMultivector newConstant(String name, SparseDoubleMatrix definition);

    IMultivector newConstant(String name, IMultivector definition);

    IMultivector cached(Supplier<IMultivector> creator);

    default IMultivector getBaseVectorOrigin() {
        return cached(() -> newConstant("ε₀", fac().createBaseVectorOrigin(1d)));
    }

    default IMultivector getBaseVectorInfinity() {
        return cached(() -> newConstant("εᵢ", fac().createBaseVectorInfinity(1d)));
    }

    default IMultivector getBaseVectorX() {
        return cached(() -> newConstant("ε₁", fac().createBaseVectorX(1d)));
    }

    default IMultivector getBaseVectorY() {
        return cached(() -> newConstant("ε₂", fac().createBaseVectorY(1d)));
    }

    default IMultivector getBaseVectorZ() {
        return cached(() -> newConstant("ε₃", fac().createBaseVectorZ(1d)));
    }

    default IMultivector getEpsilonPlus() {
        return cached(() -> newConstant("ε₊", fac().createEpsilonPlus()));
    }

    default IMultivector getEpsilonMinus() {
        return cached(() -> newConstant("ε₋", fac().createEpsilonMinus()));
    }

    default IMultivector getPi() {
        return cached(() -> newConstant("π", fac().createScalar(Math.PI)));
    }

    default IMultivector getBaseVectorInfinityDorst() {
        return cached(() -> newConstant("∞", fac().createBaseVectorInfinityDorst()));
    }

    default IMultivector getBaseVectorOriginDorst() {
        return cached(() -> newConstant("o", fac().createBaseVectorOriginDorst()));
    }

    default IMultivector getBaseVectorInfinityDoran() {
        return cached(() -> newConstant("n", fac().createBaseVectorInfinityDoran()));
    }

    default IMultivector getBaseVectorOriginDoran() {
        return cached(() -> newConstant("ñ", fac().createBaseVectorOriginDoran()));
    }

    default IMultivector getMinkovskyBiVector() {
        return cached(() -> newConstant("E₀", fac().createMinkovskyBiVector()));
    }

    default IMultivector getEuclideanPseudoscalar() {
        return cached(() -> newConstant("E₃", fac().createEuclideanPseudoscalar()));
    }

    default IMultivector getPseudoscalar() {
        return cached(() -> newConstant("E", fac().createPseudoscalar()));
    }

    IMultivector getInversePseudoscalar();

    default IMultivector one() {
        return cached(() -> newConstant("1", fac().createScalar(1d)));
    }

    default IMultivector half() {
        return cached(() -> newConstant("0.5", fac().createScalar(0.5d)));
    }
}
