package util.cga;

//import util.CayleyTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

    //FIXME
    // sollte das nicht getGrade() heissen und bereits in CayleyTable abstract vorhanden sein?
    // ich brauche das hier aber als statische Methode? aber getGrade() ist nicht statisch verfügbar
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
    public static int getMikovskiBivectorIndex(){
        return 15;
    }
    public static int getPseudoScalarIndex(){
        return 31;
    }
    public static int[] getNonScalarIndizes(){
        return new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        // return getIndizes(new int[]{1,2,3,4,5};
    }
    
    CGACayleyTable(String[][] cgaTable) {
        super(cgaTable, CGABasisBladeNames);
    }

    public static int[] getIndizes(int[] grades){
        List<Integer> result = new ArrayList<>();
        for (int i=0;i<grades.length;i++){
            result.addAll(Arrays.stream(getIndizes(grades[i])).boxed().collect(Collectors.toList()));
        }
        return result.stream().mapToInt(i -> i).toArray();
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
