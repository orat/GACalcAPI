package de.orat.math.gacalc.util;

import java.util.function.Supplier;
import de.orat.math.gacalc.spi.IGAFunction;

/**
 * @Deprectated
 *
 */
public class LazyFunction implements Supplier<IGAFunction> {

    private Supplier<IGAFunction> supplier;
    private IGAFunction value;

    private LazyFunction(Supplier<IGAFunction> supplier) {
        this.supplier = supplier;
    }

    @Override
    public IGAFunction get() {
        if (supplier == null) {
            return value;
        } else {
            this.value = supplier.get();
            supplier = null;
            return this.value;
        }
    }
}
