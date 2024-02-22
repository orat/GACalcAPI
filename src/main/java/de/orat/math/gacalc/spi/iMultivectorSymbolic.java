package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.MatrixSparsity;
import util.CayleyTable;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {

    void init(Callback callback);

    @Override
    String toString();

    String getName();

    MatrixSparsity/*ColumnVectorSparsity*/ getSparsity();
    
    default boolean isScalar(){
        int[] rows = getSparsity().getrow();
        if (rows.length != 1) return false;
        return rows[0] == 0;
    }

    /**
     * Get the Cayley-Table for the geometric product.
     * 
     * @return 
     */
    CayleyTable getCayleyTable();
    
    
    // Operators to create an acyclic graph
    
    int grade();
    int[] grades();

    iMultivectorSymbolic gradeSelection(int grade);

    iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    iMultivectorSymbolic gp(double s);
  
    // das könnte ich default implementieren?
    // schwierig, da ich sonst viele weitere Methoden hier im Interface brauche
    // um scalare Operationen ausführen zu können, daher die ga-allgemeine Implementierung
    // erst einmal in die cga-spezifische Implementierung aufgenommen, soll dann
    // später in eine allg. GA casadi impl verschoben werden
    iMultivectorSymbolic reverse();
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
        // scheint beides zu funktionieren
        return lc(inversePseudoscalar());
        //return gp(inversePseudoscalar());
    }
    /**
     * Generic GA poincare unduality operator based on geometric product and pseudoscalar.
     * 
     * not tested
     * 
     * @return 
     */
    default iMultivectorSymbolic undual(){
        // alternativ könnte das auch via GA generic via gradeselection implementiert werden
        // nur nicht hier im Interface, da sonst Methoden zurm symoblischen Rechnen von scalars
        // benötigt würden.
        return gp(pseudoscalar());
    }
    
    iMultivectorSymbolic generalInverse();
    iMultivectorSymbolic scalarInverse();
    
    /**
     * Das liesse sich in ga-generic implementieren durch Invertieren der gp-Matrix.
     * Dies ist allerdings nicht so performant wie die spezfische cga impl von generalInverse und gp.
     * 
     * @param rhs
     * @return 
     */
    default iMultivectorSymbolic div(iMultivectorSymbolic rhs){
        return gp(rhs.generalInverse());
    }
    
    /**
     * Invertion of versors is more efficient than invertion of a generic multivector.
     * 
     * TODO
     * Untersuchen, ob das besser wieder abgeschafft werden kann und die Implementierung
     * dann intern das argument darauf testet ob ein versor vorliegt und dann die
     * passende Implementierung aufruft.
     * 
     * @return 
     */
    default iMultivectorSymbolic versorInverse(){
        iMultivectorSymbolic rev = reverse();
        return rev.gp(gp(rev).scalarInverse());
    }
    
    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    iMultivectorSymbolic conjugate();

    // funktioniert noch nicht - da bekommen ich lauter 00-Elemente
    // vermutlich gilt die entsprechende Formel nur für blades und ich muss
    // den multivector in blades zerlegen und dann über die blades iterieren
    //TODO
    default iMultivectorSymbolic op__(iMultivectorSymbolic b){
        return gp(b).add(b.gradeInversion().gp(this)).gp(0.5d);
    }
    
    default iMultivectorSymbolic op(iMultivectorSymbolic b){
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
        iMultivectorSymbolic result = null; //denseEmptyInstance(); 
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+
                        " op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = grades_a[i] + grades_b[j];
                System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >=0){
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
    }

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
    default iMultivectorSymbolic lc (iMultivectorSymbolic b){
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
                int grade = grades_b[j] - grades_a[i];
                if (grade >=0){
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
     * Generic GA left contraction based on geometric product, pseudoscalar and 
     * inversePseudoscalar.
     * 
     * test failed
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
                if (grade >=0){
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

    
    // ungetested
    // a*reverse(a) == norm-square of a
    default iMultivectorSymbolic scp(iMultivectorSymbolic b){
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
    
    /**
     * Dot product - different to inner product: 0-grade products are not excluded
     * from the summation. This fits better to left/right contraction in combination
     * with a scalar product.
     * 
     * Not yet tested.
     * 
     * @param b
     * @return 
     */
    default iMultivectorSymbolic dot(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = null;
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade >=0){
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
     * @param a
     * @param b
     * @return a & b
     */
    default iMultivectorSymbolic vee (iMultivectorSymbolic b){
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
        return add(b.gp(-1d));
    }

    // macht vermutlich nur Sinn für scalars
    //iMultivectorSymbolic mul(iMultivectorSymbolic b);

    /**
     * norm.
     *
     * Calculate the Euclidean norm. (strict positive).
     */
    iMultivectorSymbolic norm();

    /**
     * inorm.
     *
     * Calculate the Ideal norm. (signed)
     */
    iMultivectorSymbolic inorm();

    /**
     * Normalize a multivector.
     * 
     * Grade-selection is needed if n>3 only, else this.reverse() is always a scalar.<p>
     * 
     * n=p+q+r, R41 corresponds to n=5<p>
     * 
     * TODO<br>
     * https://enki.ws/ganja.js/examples/coffeeshop.html#NSELGA<br>
     * Hier wird eine komplizierte normalize()-Methode beschrieben die auch für R41
     * funktioniert. Diese könnte dann eine spezifische Implementierung dieses
     * Interfaces zur Verfügung stellen und die default impl hier überschreiben.<p>
     * 
     * Returns a normalized (Euclidean) element.
     */
    default iMultivectorSymbolic normalize(){
        return gp(gp(this.reverse().gradeSelection(0)).scalarAbs().scalarSqrt().scalarInverse());
    }
  
    iMultivectorSymbolic scalarAbs();
    
    iMultivectorSymbolic scalarSqrt();
    
    public iMultivectorSymbolic denseEmptyInstance();
    // brauche ich das wirklich?
    //FIXME
    public iMultivectorSymbolic sparseEmptyInstance();
    
    public iMultivectorSymbolic negate14();
    
    // for scalars only
    public iMultivectorSymbolic atan2(iMultivectorSymbolic y);
    
    // [8] M Roelfs and S De Keninck. 2021. 
    // Graded Symmetry Groups: Plane and Simple. arXiv:2107.03771 [math-ph]
    // generische Implementierung for multivectors
    public iMultivectorSymbolic exp();
    public iMultivectorSymbolic sqrt();
    public iMultivectorSymbolic log();
    
    public iMultivectorSymbolic meet(iMultivectorSymbolic b);
    public iMultivectorSymbolic join(iMultivectorSymbolic b);
    
}
