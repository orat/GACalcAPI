package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import java.util.List;

/**
 * Function.
 */
public interface iFunctionSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, IMultivectorNumeric extends iMultivectorNumeric<IMultivectorNumeric, IMultivectorSymbolic>> {

    void init(Callback callback);

    String getName();

    int getArity();

    int getResultCount();

    @Override
    String toString();

    List<? extends IMultivectorSymbolic> callSymbolic(List<? extends IMultivectorSymbolic> arguments);

    List<? extends IMultivectorNumeric> callNumeric(List<? extends IMultivectorNumeric> arguments);
}
