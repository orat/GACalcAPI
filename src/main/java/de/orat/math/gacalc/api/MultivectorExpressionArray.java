package de.orat.math.gacalc.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MultivectorExpressionArray extends ArrayList<MultivectorExpression> {

    public MultivectorExpressionArray() {
        super();
    }

    public MultivectorExpressionArray(Collection<? extends MultivectorExpression> c) {
        super(c);
    }

    public void ensureSize(int newSize, MultivectorExpression nullElement) {
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
