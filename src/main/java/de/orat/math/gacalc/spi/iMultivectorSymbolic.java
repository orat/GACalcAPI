package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import util.CayleyTable;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * Methods with prefix "_" are to use only inside this interface.
 */
public interface iMultivectorSymbolic {

    void init(Callback callback);

    @Override
    String toString();

    String getName();

    MatrixSparsity getSparsity();
    
    /**
     * Is structural a scalar.
     * 
     * @return 
     */
    default boolean isScalar(){
        int[] rows = getSparsity().getrow();
        if (rows.length != 1) return false;
        return rows[0] == 0;
    }
    
    public boolean isZero();
    
    /**
     * Get the Cayley-Table for the geometric product.
     * 
     * @return 
     */
    CayleyTable getCayleyTable();
    
    
    // Operators to create an acyclic graph
    
    iMultivectorSymbolic _asCachedSymbolicFunction(String name, 
        List<iMultivectorSymbolic> args, iMultivectorSymbolic res);
    
    int grade();
    int[] grades();

    iFunctionSymbolic getGradeSelectionFunction(int grade);
    default iMultivectorSymbolic gradeSelection(int grade){
         return getGradeSelectionFunction(grade).callSymbolic(Collections.singletonList(
                this)).iterator().next();
    }
    
    
    default iMultivectorSymbolic reverse(){
         return getReverseFunction().callSymbolic(Collections.singletonList(
                this)).iterator().next();
    }
    // das könnte ich default implementieren?
    // schwierig, da ich sonst viele weitere Methoden hier im Interface brauche
    // um scalare Operationen ausführen zu können, daher die ga-allgemeine Implementierung
    // erst einmal in die cga-spezifische Implementierung aufgenommen, soll dann
    // später in eine allg. GA casadi impl verschoben werden
    iFunctionSymbolic getReverseFunction();
    /**
     * Generic GA reverse implementation based on grade selection.
     * 
     * [Dorst] p. 604
     * 
     * noch unvollständig
     * 
     * @return 
     */
    /*public iMultivectorSymbolic reverse_(){
        int[] grades = grades();
        iMultivectorSymbolic result = null; //denseEmptyInstance(); 
        for (int i=0;i<grades.length;i++){
            System.out.println("reverse:grade()="+String.valueOf(grades[i]));
            SX multiplier = SX.pow(new SX(-1d), new SX((new SX(grades[i])),));
            iMultivectorSymbolic norme2 = gradeSelection(grades[i]).
                    gp(new SparseCGASymbolicMultivector(multiplier));
            System.out.println("reverse:norme2 grade()="+String.valueOf(grades[i])+" ="+norme2.toString());
            //TODO
            // eleganter wäre es die for-Schleifen bei 1 starten zu lassen
            // und den ersten Wert vor dem Vorschleifen in die Variable zu streichen
            // dann könnte ich das if vermeiden.
            if (result == null) {
                result = norme2;
            } else {
                result = result.add(norme2);
            }
            System.out.println("reverse:norme2 sparsity="+result.getSparsity().toString());
        }
        return result;
    }*/
    
    
    // gp
    
    //iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    public iFunctionSymbolic getGPFunction();
    default iMultivectorSymbolic gp(iMultivectorSymbolic rhs){
        return getGPFunction().callSymbolic(Arrays.asList(this, rhs)).iterator().next();
    }
    
    //iMultivectorSymbolic gp(double s);
    public iFunctionSymbolic getGPWithScalarFunction(double s);
    default iMultivectorSymbolic gp(double s){
        return getGPWithScalarFunction(s).callSymbolic(Arrays.asList(this)).iterator().next();
    }
  
    // involute (Ak) = (-1) hoch k * Ak
    // ungetested
    default iMultivectorSymbolic gradeInversion(){
        int[] grades = grades();
        iMultivectorSymbolic result = null; //denseEmptyInstance(); 
        for (int i=0;i<grades.length;i++){
            iMultivectorSymbolic res = gradeSelection(grades[i]).
                    //FIXME das geht effizienter, gp kann vermieden werden, bei
                    // geradem grade
                    gp(Math.pow(-1, grades[i]));
            //TODO
            // eleganter wäre es die for-Schleifen bei 1 starten zu lassen
            // und den ersten Wert vor dem Vorschleifen in die Variable zu streichen
            // dann könnte ich das if vermeiden.
            if (result == null) {
                result = res;
            } else {
                result = result.add(res);
            }
            System.out.println("op:res sparsity="+result.getSparsity().toString());
        }
        return result;
    }
   
        
    iMultivectorSymbolic pseudoscalar();
    
    //TODO default impl ist leicht 
    iMultivectorSymbolic inversePseudoscalar();

    /**
     * Dual.
     *
     * Generic GA poincare duality operator based on geometric product and 
     * inverse pseudoscalar.<p>
     *
     * This implementation only works for non-degenerate metrics and even for
     * those a more efficient implementation is possible.<p>
     *
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual() {
        return _asCachedSymbolicFunction("dual", Arrays.asList(this), 
                _lc_InversePseudoscalar());
        // scheint beides zu funktionieren
        //return lc(inversePseudoscalar());
        //return gp(inversePseudoscalar());
    }
    /**
     * Generic GA poincare unduality operator based on geometric product and pseudoscalar.
     * 
     * not tested
     * 
     * @return 
     */
    /*default iMultivectorSymbolic undual(){
        // alternativ könnte das auch via GA generic via gradeselection implementiert werden
        // nur nicht hier im Interface, da sonst Methoden zurm symoblischen Rechnen von scalars
        // benötigt würden.
        return gp(pseudoscalar());
    }*/
    default iMultivectorSymbolic undual(){
        return getUndualFunction().callSymbolic(Arrays.asList(this)).iterator().next();
    }
    //TODO
    // default impl? sollte möglich sein ist dann aber nicht so performant, da das
    // Vorzeichen je nach GA model bestimmt werden muss, 
    public iFunctionSymbolic getUndualFunction();
    
    
    
    iMultivectorSymbolic scalarInverse();
    

    // conjugate
    
    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    //iMultivectorSymbolic conjugate();
    default iMultivectorSymbolic conjugate(){
         return getConjugateFunction().callSymbolic(Collections.singletonList(
                this)).iterator().next();
    }
    iFunctionSymbolic getConjugateFunction();
    
    
    // outer product
    
    // funktioniert noch nicht - da bekommen ich lauter 00-Elemente
    // vermutlich gilt die entsprechende Formel nur für blades und ich muss
    // den multivector in blades zerlegen und dann über die blades iterieren
    //TODO
    /*default iMultivectorSymbolic op__(iMultivectorSymbolic b){
        return gp(b).add(b.gradeInversion().gp(this)).gp(0.5d);
    }*/
    
    default iMultivectorSymbolic op(iMultivectorSymbolic b){
        String funName =  _createBipedFuncName("op", grades(), b.grades());
        return _asCachedSymbolicFunction(funName, Arrays.asList(this, b), _op(b));
    }

    default iMultivectorSymbolic _op(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null; //denseEmptyInstance(); 
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                //System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+
                //        " op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = grades_a[i] + grades_b[j];
                //System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    //System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).
                            gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    //System.out.println("op:res grade(a)="+String.valueOf(grades_a[i])+
                    //        ", grade(b)="+String.valueOf(grades_b[j])+", grade(result)="+
                    //        String.valueOf(grade)+" ="+res.toString());
                    //TODO
                    // eleganter wäre es die for-Schleifen bei 1 starten zu lassen
                    // und den ersten Wert vor dem Vorschleifen in die Variable zu streichen
                    // dann könnte ich das if vermeiden.
                    if (result == null) {
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                    //System.out.println("op:res sparsity="+result.getSparsity().toString());
                }
            }
        }
        return result;
    }
    
    /*default iMultivectorSymbolic op(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null; //denseEmptyInstance(); 
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+
                        " op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = grades_a[i] + grades_b[j];
                System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).
                            gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    System.out.println("op:res grade(a)="+String.valueOf(grades_a[i])+
                            ", grade(b)="+String.valueOf(grades_b[j])+", grade(result)="+
                            String.valueOf(grade)+" ="+res.toString());
                    //TODO
                    // eleganter wäre es die for-Schleifen bei 1 starten zu lassen
                    // und den ersten Wert vor dem Vorschleifen in die Variable zu streichen
                    // dann könnte ich das if vermeiden.
                    if (result == null) {
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                    System.out.println("op:res sparsity="+result.getSparsity().toString());
                }
            }
        }
        return result;
    }*/

    /**
     * Generic GA left contraction based on grade selection.
     *
     * The geometric meaning is usually formulated as the dot product between x
     * and y gives the orthogonal complement in y of the projection of x onto y.
     * 
     * ganja:<p>
     * 
     * LDot(b,r) {
     *   r=r||new this.constructor();
     *   for (var i=0,x,gsx; gsx=grade_start[i],x=this[i],i<this.length; i++) if (x) for (var j=0,y,gsy;gsy=grade_start[j],y=b[j],j<b.length; j++) if (y) for (var a=0; a<x.length; a++) if (x[a]) for (var bb=0; bb<y.length; bb++) if (y[bb]) {
     *     if (i==j && a==bb) { r[0] = r[0]||[0]; r[0][0] += x[a]*y[bb]*metric[i][a]; }
     *     else {
     *        var rn=simplify_bits(basis_bits[gsx+a],basis_bits[gsy+bb]), g=bc(rn[1]), e=bits_basis[rn[1]]-grade_start[g];
     *        if (g == j-i) { if (!r[g])r[g]=[]; r[g][e] = (r[g][e]||0) + rn[0]*x[a]*y[bb]; }
     *     }
     *   }
     *   return r;
     * }
     * 
     * @param a
     * @param b
     * @return a | b
     */
    default iMultivectorSymbolic lc(iMultivectorSymbolic b){
        String funName =  _createBipedFuncName("lc", grades(), b.grades());
        return _asCachedSymbolicFunction(funName, Arrays.asList(this, b), _lc(b));
    }
    //TODO umbenennen in getDualFunction()
    default iMultivectorSymbolic _lc_InversePseudoscalar(){
        String funName =  _createBipedFuncName("lc", grades(),"PseudoScalar");
        return _asCachedSymbolicFunction(funName, Arrays.asList(this), __lc_InversePseudoscalar());
    }
    default iMultivectorSymbolic __lc_InversePseudoscalar (){
        iMultivectorSymbolic b = inversePseudoscalar();
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = grades_b[j] - grades_a[i];
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
    default iMultivectorSymbolic _lc (iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = grades_b[j] - grades_a[i];
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
    /*default iMultivectorSymbolic lc (iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = grades_b[j] - grades_a[i];
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }*/
    
    /**
     * Generic GA left contraction based on geometric product, pseudoscalar and 
     * inversePseudoscalar.
     * 
     * test failed
     * TODO direkter Vergleich mit lc()
     * 
     * @param rhs
     * @return 
     */
    default iMultivectorSymbolic lc_(iMultivectorSymbolic rhs){
        return op(rhs.gp(inversePseudoscalar())).gp(pseudoscalar());
    }
    
    //TODO
    // könnte auch via reversion implementiert werden auf Basis von lc
    default iMultivectorSymbolic rc (iMultivectorSymbolic b){
        // Das ist so keine vollständig symbolische Implementierung, was dazu führt,
        // dass beim Aufbau des Expression Graphs die grades der Argumente
        // bestimmt werden und als fest für die Laufzeit angenommen werden
        //TODO
        // damit mit jedem Aufruf bei gleicher Sparsity der Argumente nicht neuer
        // Code im Expression Graph erzeugt wird, sollte eine Kapselung in function-Objekten
        // erfolgen. Unklar, ob dies hier im Interface als defaut-Implementierung
        // möglich ist oder ob dies dann in der Casadi-Implementierung erfolgen muss.
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = grades_a[i] - grades_b[j];
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
    
    default iMultivectorSymbolic rc2(iMultivectorSymbolic b){
        return reverse().lc(b.reverse()).reverse();
    }

    // special characters like ._-[]{} are not allowed as function names
    default String _createBipedFuncName(String name, int[] arg1Grades, int[] arg2Grades){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("a");
        for (int i=0;i<arg1Grades.length-1;i++){
            sb.append(String.valueOf(arg1Grades[i]));
            //sb.append("-");
        }
        sb.append(String.valueOf(arg1Grades[arg1Grades.length-1]));
        sb.append("b");
        for (int i=0;i<arg2Grades.length-1;i++){
            sb.append(String.valueOf(arg2Grades[i]));
            //sb.append("-");
        }
        sb.append(String.valueOf(arg2Grades[arg2Grades.length-1]));
        //sb.append("_");
        return sb.toString();
    }
    default String _createBipedFuncName(String name, int[] arg1Grades, String constName){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("a");
        for (int i=0;i<arg1Grades.length-1;i++){
            sb.append(String.valueOf(arg1Grades[i]));
            //sb.append("-");
        }
        sb.append(String.valueOf(arg1Grades[arg1Grades.length-1]));
        sb.append("b");
        sb.append(constName);
        //sb.append("_");
        return sb.toString();
    }
    
    default iMultivectorSymbolic scp(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        String funName =  _createBipedFuncName("scp", grades_a, grades_b);
        return _asCachedSymbolicFunction(funName, Arrays.asList(this, b), _scp(b));
    }
    default iMultivectorSymbolic _scp(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                if (grades_a[i] == grades_b[j]){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(0);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
        
    /*default iMultivectorSymbolic scp(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                if (grades_a[i] == grades_b[j]){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(0);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }*/
    
    
    /**
     * Dot product - different to inner product: 0-grade products are not excluded
     * from the summation. 
     * 
     * This fits better to left/right contraction in combination with a scalar product.<p>
     * 
     * @param b
     * @return 
     */
    default iMultivectorSymbolic dot(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        String funName =  _createBipedFuncName("dot", grades_a, grades_b);
        return _asCachedSymbolicFunction(funName, Arrays.asList(this, b), _dot(b));
    }
    default iMultivectorSymbolic _dot(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
    /*default iMultivectorSymbolic dot(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade >=0 && grade <= getCayleyTable().getPseudoscalarGrade()){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }*/
    
    /**
     * Original/classical inner product definition which excludes 0-grades from
     * the summation. Better use the dot-product instead.
     * 
     * Symmetric contraction:<p>
     * 
     * Dot(b,r) {
     *   r=r||new this.constructor();
     *   for (var i=0,x,gsx; gsx=grade_start[i],x=this[i],i<this.length; i++) if (x) for (var j=0,y,gsy;gsy=grade_start[j],y=b[j],j<b.length; j++) if (y) for (var a=0; a<x.length; a++) if (x[a]) for (var bb=0; bb<y.length; bb++) if (y[bb]) {
     *     if (i==j && a==bb) { r[0] = r[0]||[0]; r[0][0] += x[a]*y[bb]*metric[i][a]; }
     *     else {
     *        var rn=simplify_bits(basis_bits[gsx+a],basis_bits[gsy+bb]), g=bc(rn[1]), e=bits_basis[rn[1]]-grade_start[g];
     *        if (g == Math.abs(j-i)) { if (!r[g])r[g]=[]; r[g][e] = (r[g][e]||0) + rn[0]*x[a]*y[bb]; }
     *     }
     *   }
     *   return r;
     * }
     * 
     * @param b
     * @return 
     */
    default iMultivectorSymbolic ip(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade > 0){
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null){
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }
    

    /**
     * The regressive or vee product. (JOIN)
     *
     * This should be implemented in a more performant way.
     * 
     * @param a first multivector
     * @param b second multivector
     * @return a & b
     */
    default iMultivectorSymbolic vee (iMultivectorSymbolic b){
        //FIXME das erste dual() hier liefert keinen symbolic expr.
        //TODO wieder auf cached function umstellen
        //return _asCachedSymbolicFunction("vee", Arrays.asList(this, b), 
        //        dual().op(b.dual()).dual());
        return dual().op(b.dual()).dual();
    }

    /**
     * Add.
     *
     * Multivector addition
     *
     * @param a
     * @param b
     * @return a + b
     */
    iMultivectorSymbolic add(iMultivectorSymbolic b);

    /**
     * Multivector subtraction.
     *
     * @param a
     * @param b
     * @return a - b
     */
    default iMultivectorSymbolic sub (iMultivectorSymbolic b){
        return _asCachedSymbolicFunction("sub", Arrays.asList(this, b), 
                add(b.gp(-1d)));
        //return add(b.gp(-1d));
    }

    public iMultivectorSymbolic negate14();
    
    iMultivectorSymbolic scalarAbs();
    
    iMultivectorSymbolic scalarAtan2(iMultivectorSymbolic y);
    
    iMultivectorSymbolic scalarSqrt();
    
    
    public iMultivectorSymbolic denseEmptyInstance();
    // brauche ich das wirklich?
    //FIXME
    public iMultivectorSymbolic sparseEmptyInstance();
    
    
    // non linear operators/functions
    
    // [8] M Roelfs and S De Keninck. 2021. 
    // Graded Symmetry Groups: Plane and Simple. arXiv:2107.03771 [math-ph]
    // generische Implementierung for multivectors
    public iMultivectorSymbolic exp();
    public iMultivectorSymbolic sqrt();
    public iMultivectorSymbolic log();
    
    public iMultivectorSymbolic meet(iMultivectorSymbolic b);
    public iMultivectorSymbolic join(iMultivectorSymbolic b);
    
    /**
     * Euclidean/reverse norm.
     *
     * Calculate the euclidean/reverse norm. (strict positive).
     * 
     * TODO
     * vielleicht besser umbenennen in euclideanNorm oder reverseNorm?
     * wird in der impl überschrieben
     * 
     * aber was ist mit conjugate based norm?
     * https://math.stackexchange.com/questions/1128844/about-the-definition-of-norm-in-clifford-algebra?rq=1
     * 
     */
    default iMultivectorSymbolic norm(){
        return scp(reverse()).scalarAbs().scalarSqrt();
    }
    
    /**
     * Normalize a multivector (unit under reverse).
     * 
     * Grade-selection is needed if n>3 only, else reverse() is always a scalar.<p>
     * 
     * n=p+q+r, R41 corresponds to n=5<p>
     * 
     * Reverse norm is different to standard normalization.<p>
     * 
     * https://math.stackexchange.com/questions/1128844/about-the-definition-of-norm-in-clifford-algebra?rq=1
     * --> Difference between reverse based and conjugate based norm
     * 
     * [Kleppe
     * normed = {
     *  _P(1)/(sqrt(abs(_P(1)*_P(1)~)))
     * }
     * 
     * Returns a normalized (Euclidean) element.
     * 
     * TODO
     * da fehlt mir noch ein test
     */
    default iMultivectorSymbolic normalizeByReverseNorm(){
        //return _asCachedSymbolicFunction("normed", Collections.singletonList(this),
        //        gp(gp(reverse().gradeSelection(0)).scalarAbs().scalarSqrt().scalarInverse()));
        // ist gp(scalar) wirklich das gleiche wie muls? ja
        //TODO sollte ich besser mit Hilfe von reverse/euclidean norm implementieren
        return gp(gp(reverse()).gradeSelection(0).scalarAbs().scalarSqrt().scalarInverse());
    }
    
    /**
     * Ideal norm.
     *
     * Calculate the Ideal norm. (signed)
     */
    iMultivectorSymbolic inorm();
     
    iMultivectorSymbolic normalizeBySquaredNorm(); // oder idealNorm?
   
    iMultivectorSymbolic normalizeEvenElement();
    
    
    iMultivectorSymbolic generalInverse();
    
    /**
     * Das liesse sich in ga-generic implementieren durch Invertieren der gp-Matrix.
     * Dies ist allerdings nicht so performant wie die spezfische cga impl von generalInverse und gp.
     * 
     * TODO
     * da fehlt mir noch ein test
     * 
     * @param rhs
     * @return 
     */
    default iMultivectorSymbolic div(iMultivectorSymbolic rhs){
        return gp(rhs.generalInverse());
    }
    
    /**
     * Inversion of versors is more efficient than inversion of a generic multivector.
     * 
     * TODO
     * Untersuchen, ob das besser wieder abgeschafft werden kann und die Implementierung
     * dann intern das argument darauf testet ob ein versor vorliegt und dann die
     * passende Implementierung aufruft.
     * 
     * @return inverse of the multivector if the multivector is a versor
     * @throws IllegalArgumentException if the scalarproduct with the rerverse of this multivector is no scalar
     */
    default iMultivectorSymbolic versorInverse(){
        //iMultivectorSymbolic rev = reverse();
        // return rev.gp(gp(rev).scalarInverse());
        // wo kommt diese Implementierung her?
        // scheint falsch zu sein
        // vergleich mit generalInverse liefert Vorzeichenfehler
        
        iMultivectorSymbolic R = reverse();
        iMultivectorSymbolic s = scp(R);
        if (!s.isScalar()) throw new IllegalArgumentException("Multiplication with reverse must be a scalar!");
        //if (s == 0.0) throw new java.lang.ArithmeticException("non-invertible multivector");
        return R.gp(s.scalarInverse());
        // Achtung: es wird nicht gp(double) sondern gp(mv) aufgerufen. Vielleicht ist das ja der Fehler?
        
        // scheint mir jetzt den gleichen Vorzeichenfehler zu liefern
        // statt gp taucht im test muls(scalar) auf - elementwise Multiplication mit einem scalar
    }
}
