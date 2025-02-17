package de.orat.math.gacalc.api;

import java.util.ArrayList;
import java.util.Collection;

public class MultivectorSymbolicArray extends ArrayList<MultivectorSymbolic> {

    public MultivectorSymbolicArray() {
        super();
    }

    public MultivectorSymbolicArray(Collection<? extends MultivectorSymbolic> c) {
        super(c);
    }
}
