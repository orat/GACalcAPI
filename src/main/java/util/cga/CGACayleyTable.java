package util.cga;

//import util.CayleyTable;
import java.util.ArrayList;
import java.util.List;
import util.CayleyTable;

/**
 * https://discourse.bivector.net/t/matrix-representation/232/4
 *
 * Cayley-Table for CGA generated with ganja.js codegenerator.<p>
 *
 * make GEN_LANG="java" cga<p>
 *
 * The brackets are substituted with the replace-functionality of the editor.<p>
 *
 * Closure implementation of ga multivectors, maybe helpful:<br>
 * https://gitlab.com/jordibc/geometric-algebra/-/tree/main?ref_type=heads
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public abstract class CGACayleyTable extends CayleyTable {

    private final static String[] CGABasisBladeNames = new String[]{
        "1", "e1", "e2", "e3",
        "e4", "e5", "e12", "e13",
        "e14", "e15", "e23", "e24",
        "e25", "e34", "e35", "e45",
        "e123", "e124", "e125", "e134",
        "e135", "e145", "e234", "e235",
        "e245", "e345", "e1234", "e1235",
        "e1245", "e1345", "e2345", "e12345"};

    public static int getCGAGrade(int index) {
        return CGABasisBladeNames[index].length() - 1;
    }

    // indizes of a general rotor
    public static int[] getEvenIndizes() {
        return new int[]{0, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 26, 27, 28, 29, 30};
    }
    public static int[] getEvenGrades(){
        return new int[]{0,2,4};
    }
    // B=B0​e12​+B1​e13​+B2​e14​+B3​e15​+B4​e23​+B5​e24​+B6​e25​+B7​e34​+B8​e35​+B9​e45​
    public static int[] getBivectorIndizes(){
        return new int[]{6,7,8,9,10,11,12,13,14,15};
    }
    
    CGACayleyTable(String[][] cgaTable) {
        super(cgaTable, CGABasisBladeNames);
    }

    public static int[] getIndizes(int grade) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < CGABasisBladeNames.length; i++) {
            if (CGABasisBladeNames[i].length() - 1 == grade) {
                result.add(i);
            }
        }
        return result.stream().mapToInt(d -> d).toArray();
    }
}
