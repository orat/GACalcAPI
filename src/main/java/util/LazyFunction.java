package util;
 
import de.orat.math.gacalc.spi.iFunctionSymbolic;
import java.util.function.Supplier;

/**
 * @Deprectated
 * 
 */
public class LazyFunction implements Supplier<iFunctionSymbolic> {

    private Supplier<iFunctionSymbolic> supplier;
    private iFunctionSymbolic value;

    private LazyFunction(Supplier<iFunctionSymbolic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public iFunctionSymbolic get() {
        if (supplier == null) {
            return value;
        } else {
            this.value = supplier.get();
            supplier = null;
            return this.value;
        }
    }
}
    