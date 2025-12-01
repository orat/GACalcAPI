package experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Experiment {

    // Contains squares of simpleBase vectors.
    // Scalar not included.
    final double[] simpleBaseMetric = {1, 1, 1, 1, -1}; //3d cga

    // Indizes of simpleBase vectors.
    public int[] simpleBaseIndizes = IntStream.range(0, simpleBaseMetric.length).toArray();

    // ei^2, if i==j
    // 0 otherwise
    // Precondition: ei is orthogonal to all.
    public double simpleBaseScalarProduct(int i, int j) {
        if (i != j) {
            return 0;
        }
        return simpleBaseMetric[i];
    }

    // Precondition: iArr and jArr are ordered.
    // sum of ei^2, if iArr is same as jArr.
    // 0 otherwise.
    // Schneller, wenn iArr und jArr gecachedte HashCodes haben würden.
    public double simpleBaseScalarProduct(int[] iArr, int[] jArr) {
        if (iArr.length != jArr.length) {
            return 0;
        }
        final int len = iArr.length;
        double sum = 0;
        for (int i = 0; i < len; ++i) {
            if (iArr[i] != jArr[i]) {
                return 0;
            }
            sum += simpleBaseMetric[i];
        }
        return sum;
    }

    // Postcondition: sorted, no duplicates
    public ICompound simpleBaseOuterProduct(int i, int j) {
        if (i == j) {
            return zeroCompound;
        }
        if (i < j) {
            return new CompoundOne(new int[]{i, j});
        } else {
            return new CompoundMinusOne(new int[]{j, i});
        }
    }

    public static interface ICompound {

        double factor();

        int[] iArr();
    }

    public record Compound(double factor, int[] iArr) implements ICompound {

    }

    public record CompoundOne(int[] iArr) implements ICompound {

        @Override
        public double factor() {
            return 1;
        }
    }

    public record CompoundMinusOne(int[] iArr) implements ICompound {

        @Override
        public double factor() {
            return -1;
        }
    }

    // Precondition: iArr and jArr are outerProducts of simple base vectors.
    // Precondition: iArr and jArr are sorted.
    // Precondition: iArr and jArr don't contain duplicates.
    // Precondition: iArr and jArr contain both at least 1 element.
    public int simpleBaseInversionCount(int[] iArr, int[] jArr) {
        final int iLen = iArr.length;
        final int jLen = jArr.length;
        int inversions = 0;
        int ii = 0;
        int jj = 0;
        int i = iArr[0];
        int j = jArr[0];
        // Idea: Try to insert consecutively each j into iArr.
        for (;;) {
            if (i > j) {
                inversions += iLen - ii;
                ++jj;
                if (jj >= jLen) {
                    break;
                }
                j = jArr[jj];
            } else {
                ++ii;
                if (ii >= iLen) {
                    break;
                }
                i = iArr[ii];
            }
        }
        return inversions;
    }

    private static final int[] zeroArr = new int[0];
    private static final Compound zeroCompound = new Compound(0, zeroArr);

    // Damit kann man beliebige Cayley-Table Einträge ausrechnen.
    // Lineare Laufzeit.
    // Precondition: iArr and jArr are geometricProducts of simple base vectors.
    // Precondition: iArr and jArr are sorted.
    // Precondition: iArr and jArr don't contain duplicates.
    // Precondition: iArr and jArr can share elements.
    // Precondition: iArr and jArr contain both at least 1 element.
    // Postcondition: sorted, no duplicates
    public Compound simpleBaseGeometricProduct(int[] iArr, int[] jArr) {
        final int iLen = iArr.length;
        final int jLen = jArr.length;
        int inversions = 0;
        double factor = 1;
        int ii = 0;
        int jj = 0;
        List<Integer> mergeList = new ArrayList<>();
        {
            int i = iArr[0];
            int j = jArr[0];
            final int minLen = Math.min(iLen, jLen);
            for (;;) {
                if (i == j) {
                    inversions += iLen - ii;
                    factor *= this.simpleBaseMetric[i];
                    ++ii;
                    ++jj;
                    if (ii >= minLen || jj >= minLen) {
                        break;
                    }
                    i = iArr[ii];
                    j = jArr[jj];
                } else if (i > j) {
                    inversions += iLen - ii;
                    mergeList.add(j);
                    ++jj;
                    if (jj >= minLen) {
                        break;
                    }
                    j = jArr[jj];
                } else { // if (i < j) {
                    mergeList.add(i);
                    ++ii;
                    if (ii >= minLen) {
                        break;
                    }
                    i = iArr[ii];
                }
            }
        }

        for (; ii < iLen; ++ii) {
            mergeList.add(iArr[ii]);
        }

        for (; jj < jLen; ++jj) {
            mergeList.add(jArr[jj]);
        }

        final int sgn = -1 + 2 * ((inversions + 1) & 0x1); // (-1)^inversions
        factor *= sgn;

        return new Compound(factor, mergeList.stream().mapToInt(Integer::intValue).toArray());
    }

    public static void main(String[] args) {
        Experiment e = new Experiment();

        int[] a = new int[]{0, 1, 2}; //e123
        int[] b = new int[]{0, 1}; //e12

        // -e3
        // Korrekt. e3 hat Index 2.
        var ret = e.simpleBaseGeometricProduct(a, b);
        System.out.println(ret.factor);
        System.out.println(Arrays.toString(ret.iArr()));
    }
}
