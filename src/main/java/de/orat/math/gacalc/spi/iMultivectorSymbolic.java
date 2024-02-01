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
  
    iMultivectorSymbolic reverse();

    iMultivectorSymbolic gradeInversion();

    iMultivectorSymbolic pseudoscalar();
    iMultivectorSymbolic inversePseudoscalar();

    /**
     * Dual.
     *
     * Poincare duality operator.
     *
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual() {
        return lc(inversePseudoscalar());
    }
    
    // da könnte ich eine default impl zur Verfügung stellen die abhängig von der
    // basis/metric das richtigen Vorzeichen liefert
    iMultivectorSymbolic undual();

    iMultivectorSymbolic generalInverse();
    iMultivectorSymbolic scalarInverse();
    
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
     * Left contraction.
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
                int grade = grades_b[i] - grades_a[j];
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

    default iMultivectorSymbolic ip(iMultivectorSymbolic b){
        //TODO
        // abhängig von den grades a,b lc oder rc rechnen
        return lc(b);
    }

    /**
     * The regressive product. (JOIN)
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
