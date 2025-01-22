package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import java.util.List;

/**
 * Function.
 */
public interface iFunctionSymbolic<IMVSymbolic extends iMultivectorSymbolic<IMVSymbolic>, IMVNumeric extends iMultivectorNumeric<IMVNumeric, IMVSymbolic>> {

    void init(Callback callback);

    String getName();

    int getArity();

    int getResultCount();

    @Override
    String toString();

    List<? extends IMVSymbolic> callSymbolic(List<? extends IMVSymbolic> arguments);

    List<? extends IMVNumeric> callNumeric(List<? extends IMVNumeric> arguments);
}
