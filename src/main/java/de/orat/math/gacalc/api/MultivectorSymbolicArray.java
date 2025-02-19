package de.orat.math.gacalc.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MultivectorSymbolicArray extends ArrayList<MultivectorSymbolic> {

    public MultivectorSymbolicArray() {
        super();
    }

    public MultivectorSymbolicArray(Collection<? extends MultivectorSymbolic> c) {
        super(c);
    }

    public void ensureSize(int newSize, MultivectorSymbolic nullElement) {
        final int oldSize = super.size();
        if (newSize <= oldSize) {
            return;
        }
        // newSize > oldSize
        // diff > 0
        final int diff = newSize - oldSize;
        super.ensureCapacity(newSize);
        var nullList = Collections.nCopies(diff, nullElement);
        super.addAll(nullList);
    }
}
