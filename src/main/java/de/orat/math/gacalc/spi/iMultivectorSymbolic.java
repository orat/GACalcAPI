package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import util.CayleyTable;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {

    void init(Callback callback);

    @Override
    String toString();

    String getName();

    ColumnVectorSparsity getSparsity();
    
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
       
    iMultivectorSymbolic gradeInversion();

    iMultivectorSymbolic pseudoscalar();
    iMultivectorSymbolic inversePseudoscalar();

    /**
     * Dual.
     *
     * Generic GA poincare duality operator based on geometric product and inverse pseudoscalar.
     *
     * tested
     * 
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual() {
        //return lc(inversePseudoscalar());
        return gp(inversePseudoscalar());
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
     * and y gives thes orthogonal complement in y of the projection of x onto y.
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
     * Generic GA left contraction based on geometric product, pseudoscalar and inversePseudoscalar.
     * 
     * test failed
     * 
     * @param rhs
     * @return 
     */
    default iMultivectorSymbolic lc_(iMultivectorSymbolic rhs){
        return op(rhs.gp(inversePseudoscalar())).gp(pseudoscalar());
    }
    
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
                int grade = -grades_a[i] + grades_b[j];
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

    default iMultivectorSymbolic ip(iMultivectorSymbolic b){
        //TODO
        // abhängig von den grades a,b lc oder rc rechnen
        // taschen der Seiten berücksichtigen, bzw. wechsel zu rc
        return lc(b);
    }

    /**
     * The regressive product. (JOIN)
     *
     * This should be implemented in a more performant way.
     * 
     * @param a
     * @param b
     * @return a & b
     */
    default iMultivectorSymbolic vee (iMultivectorSymbolic b){
         return dual().op(b.dual()).dual().gp(-1d);
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
     * normalized.
     *
     * Returns a normalized (Euclidean) element.
     */
    iMultivectorSymbolic normalized();
  
    public iMultivectorSymbolic denseEmptyInstance();
    // brauche ich das wirklich?
    //FIXME
    public iMultivectorSymbolic sparseEmptyInstance();
    
    public iMultivectorSymbolic negate14();
    
    // for scalars only
    public iMultivectorSymbolic atan2(iMultivectorSymbolic y);
    
    public iMultivectorSymbolic exp();
    
    public iMultivectorSymbolic meet(iMultivectorSymbolic b);
    public iMultivectorSymbolic join(iMultivectorSymbolic b);
    
}
