package de.orat.math.gacalc.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import util.CayleyTableGenerator;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TestCayleyTableGenerator {

    private boolean equals(int a, int b) {
        return a == b;
    }

    /**
     * kendallTauRankDistance([1, 2, 3], [1, 3, 2]) = 1
     * <p>
     * kendallTauRankDistance([1, 2, 3], [1, 2, 3]) = 0
     * <p>
     * kendallTauRankDistance([1, 2, 3], [3, 2, 1]) = 3
     * <p>
     */
    @Test
    public void testKendallTauRankDistance() {
        int result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{1, 3, 2});
        assertTrue(equals(result, 1));
        result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assertTrue(equals(result, 0));
        result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{3, 2, 1});
        assertTrue(equals(result, 3));
    }
}
