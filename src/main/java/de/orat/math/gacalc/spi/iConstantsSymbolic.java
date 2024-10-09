package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ConstantsSymbolic;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

public interface iConstantsSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

    default void init(ConstantsSymbolic.Callback callback) {

    }

    iExprGraphFactory<?, ?, ?> fac();

    IMultivectorSymbolic newConstant(String name, SparseDoubleMatrix definition);

    IMultivectorSymbolic newConstant(String name, IMultivectorSymbolic definition);

    IMultivectorSymbolic cached(Supplier<IMultivectorSymbolic> creator);

    default IMultivectorSymbolic getBaseVectorOrigin() {
        return cached(() -> newConstant("ε₀", fac().createBaseVectorOrigin(1d)));
    }

    default IMultivectorSymbolic getBaseVectorInfinity() {
        return cached(() -> newConstant("εᵢ", fac().createBaseVectorInfinity(1d)));
    }

    default IMultivectorSymbolic getBaseVectorX() {
        return cached(() -> newConstant("ε₁", fac().createBaseVectorX(1d)));
    }

    default IMultivectorSymbolic getBaseVectorY() {
        return cached(() -> newConstant("ε₂", fac().createBaseVectorY(1d)));
    }

    default IMultivectorSymbolic getBaseVectorZ() {
        return cached(() -> newConstant("ε₃", fac().createBaseVectorZ(1d)));
    }

    default IMultivectorSymbolic getEpsilonPlus() {
        return cached(() -> newConstant("ε₊", fac().createEpsilonPlus()));
    }

    default IMultivectorSymbolic getEpsilonMinus() {
        return cached(() -> newConstant("ε₋", fac().createEpsilonMinus()));
    }

    default IMultivectorSymbolic getPi() {
        return cached(() -> newConstant("π", fac().createScalar(Math.PI)));
    }

    default IMultivectorSymbolic getBaseVectorInfinityDorst() {
        return cached(() -> newConstant("∞", fac().createBaseVectorInfinityDorst()));
    }

    default IMultivectorSymbolic getBaseVectorOriginDorst() {
        return cached(() -> newConstant("o", fac().createBaseVectorOriginDorst()));
    }

    default IMultivectorSymbolic getBaseVectorInfinityDoran() {
        return cached(() -> newConstant("n", fac().createBaseVectorInfinityDoran()));
    }

    default IMultivectorSymbolic getBaseVectorOriginDoran() {
        return cached(() -> newConstant("ñ", fac().createBaseVectorOriginDoran()));
    }

    default IMultivectorSymbolic getMinkovskyBiVector() {
        return cached(() -> newConstant("E₀", fac().createMinkovskyBiVector()));
    }

    default IMultivectorSymbolic getEuclideanPseudoscalar() {
        return cached(() -> newConstant("E₃", fac().createEuclideanPseudoscalar()));
    }

    default IMultivectorSymbolic getPseudoscalar() {
        return cached(() -> newConstant("E", fac().createPseudoscalar()));
    }

    IMultivectorSymbolic getInversePseudoscalar();

    default IMultivectorSymbolic one() {
        return cached(() -> newConstant("1", fac().createScalar(1d)));
    }

    default IMultivectorSymbolic half() {
        return cached(() -> newConstant("0.5", fac().createScalar(0.5d)));
    }
}
