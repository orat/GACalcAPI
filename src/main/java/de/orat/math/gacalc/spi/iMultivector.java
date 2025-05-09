package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.gacalc.util.CayleyTable;

/**
 * Parent interface. Not intended to be implemented directly.
 */
public interface iMultivector<IMV extends iMultivector<IMV>> {

    //======================================================
    // Other methods
    //======================================================
    @Override
    String toString();

    String getName();

    MatrixSparsity getSparsity();

    /**
     * Is structural a scalar.
     *
     * @return
     */
    default boolean isScalar() {
        int[] rows = getSparsity().getrow();
        if (rows.length != 1) {
            return false;
        }
        return rows[0] == 0;
    }

    /**
     * Is structural euclidian.
     * 
     * @return true, if the multivector contains only {e1, e2, e3} or a subspace, even no elements are allowed.
     */
    default boolean isEuclidian(){
         int[] rows = getSparsity().getrow();
         if (rows.length > 3) return false;
         for (int i=0;i<rows.length;i++){
             if (rows[i]>3 || rows[i] == 0) return false;
         }
         return true;
    }

    /**
     * Get the Cayley-Table for the geometric product.
     *
     * @return
     */
    CayleyTable getCayleyTable();

    int grade();

    int[] grades();

    iConstantsFactory<IMV> constants();

    //======================================================
    // Operators
    //======================================================
    default IMV commutatorProduct(IMV rhs) {
        return gp(rhs).sub(rhs.gp((IMV) this)).gp(constants().half());
    }

    default IMV projection(IMV rhs) {
        if (grade() == -1) {
            throw new IllegalArgumentException("projection only defined for k-vectors!");
        }
        return lc(rhs.generalInverse()).lc(rhs);
    }

    default IMV negate() {
        return gpWithScalar(-1);
    }

    default IMV square() {
        return gp((IMV) this);
    }

    IMV gradeSelection(int grade);

    /**
     * Generic GA reverse implementation based on grade selection.
     *
     * [Dorst] p. 604
     *
     * noch unvollständig
     *
     * @return
     */
    /*public IMVSymbolic reverse_(){
        int[] grades = grades();
        IMVSymbolic result = null; //denseEmptyInstance();
        for (int i=0;i<grades.length;i++){
            System.out.println("reverse:grade()="+String.valueOf(grades[i]));
            SX multiplier = SX.pow(new SX(-1d), new SX((new SX(grades[i])),));
            IMVSymbolic norme2 = gradeSelection(grades[i]).
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
    // das könnte ich default implementieren?
    // schwierig, da ich sonst viele weitere Methoden hier im Interface brauche
    // um scalare Operationen ausführen zu können, daher die ga-allgemeine Implementierung
    // erst einmal in die cga-spezifische Implementierung aufgenommen, soll dann
    // später in eine allg. GA casadi impl verschoben werden
    IMV reverse();

    IMV gp(IMV rhs);

    IMV map(IMV rhs);
    
    IMV gpWithScalar(double s);

    // involute (Ak) = (-1) hoch k * Ak
    // ungetested
    default IMV gradeInversion() {
        int[] grades = grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades.length; i++) {
            IMV res = gradeSelection(grades[i]).gpWithScalar(Math.pow(-1, grades[i]));
            result = result.add(res);
            System.out.println("op:res sparsity=" + result.getSparsity().toString());
        }
        return result;
    }

    /**
     * Dual.
     *
     * Generic GA poincare duality operator based on geometric product and inverse pseudoscalar.<p>
     *
     * This implementation only works for non-degenerate metrics and even for those a more efficient
     * implementation is possible.<p>
     *
     * @param a
     * @return !a
     */
    default IMV dual() {
        // scheint beides zu funktionieren
        return lc(constants().getInversePseudoscalar());
        //return gp(inversePseudoscalar());
    }

    /**
     * Generic GA poincare unduality operator based on geometric product and pseudoscalar.
     *
     * not tested
     *
     * @return
     */
    /*default IMVSymbolic undual(){
        // alternativ könnte das auch via GA generic via gradeselection implementiert werden
        // nur nicht hier im Interface, da sonst Methoden zurm symoblischen Rechnen von scalars
        // benötigt würden.
        return gp(pseudoscalar());
    }*/
    //TODO
    // default impl? sollte möglich sein ist dann aber nicht so performant, da das
    // Vorzeichen je nach GA model bestimmt werden muss.
    IMV undual();

    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    IMV conjugate();

    // outer product
    // funktioniert noch nicht - da bekommen ich lauter 00-Elemente
    // vermutlich gilt die entsprechende Formel nur für blades und ich muss
    // den multivector in blades zerlegen und dann über die blades iterieren
    //TODO
    /*default IMVSymbolic op__(IMVSymbolic b){
        return gp(b).add(b.gradeInversion().gp((IMVSymbolic) this)).gp(0.5d);
    }*/
    default IMV op(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                //System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+
                //        " op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = grades_a[i] + grades_b[j];
                //System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    //System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    IMV res = gradeSelection(grades_a[i]).
                        gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    //System.out.println("op:res grade(a)="+String.valueOf(grades_a[i])+
                    //        ", grade(b)="+String.valueOf(grades_b[j])+", grade(result)="+
                    //        String.valueOf(grade)+" ="+res.toString());
                    result = result.add(res);
                    //System.out.println("op:res sparsity="+result.getSparsity().toString());
                }
            }
        }
        return result;
    }

    /**
     * Generic GA left contraction based on grade selection.
     *
     * The geometric meaning is usually formulated as the dot product between x and y gives the orthogonal
     * complement in y of the projection of x onto y.<p>
     *
     * see ganja<p>
     *
     * @param a
     * @param b
     * @return a | b
     */
    default IMV lc(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = grades_b[j] - grades_a[i];
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMV res = gradeSelection(grades_a[i])
                        .gp(b.gradeSelection(grades_b[j]))
                        .gradeSelection(grade);
                    result = result.add(res);
                }
            }
        }
        return result;
    }

    /**
     * Generic GA left contraction based on geometric product, pseudoscalar and inversePseudoscalar.
     *
     * test failed TODO direkter Vergleich mit lc()
     *
     * @param rhs
     * @return
     */
    default IMV lc_(IMV rhs) {
        return op(rhs.gp(constants().getInversePseudoscalar())).gp(constants().getPseudoscalar());
    }

    //TODO
    // könnte auch via reversion implementiert werden auf Basis von lc
    default IMV rc(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = grades_a[i] - grades_b[j];
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMV res = gradeSelection(grades_a[i])
                        .gp(b.gradeSelection(grades_b[j]))
                        .gradeSelection(grade);
                    result = result.add(res);
                }
            }
        }
        return result;
    }

    default IMV rc_(IMV b) {
        return reverse().lc(b.reverse()).reverse();
    }

    default IMV scp(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                if (grades_a[i] == grades_b[j]) {
                    IMV res = gradeSelection(grades_a[i])
                        .gp(b.gradeSelection(grades_b[j]))
                        .gradeSelection(0);
                    result = result.add(res);
                }
            }
        }
        return result;
    }

    /**
     * Dot product - different to inner product: 0-grade products are not excluded from the summation.
     *
     * This fits better to left/right contraction in combination with a scalar product.<p>
     *
     * @param b
     * @return
     */
    default IMV dot(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMV res = gradeSelection(grades_a[i])
                        .gp(b.gradeSelection(grades_b[j]))
                        .gradeSelection(grade);
                    result = result.add(res);
                }
            }
        }
        return result;
    }

    /**
     * Original/classical inner product (symmetric contraction) definition which excludes 0-grades from the 
     * summation. 
     * 
     * Better use the dot-product instead.<p>
     *
     * <p>
     * see ganja Dot(b,r)<p>
     *
     * @param b
     * @return
     */
    default IMV ip(IMV b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMV result = constants().getSparseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade > 0) {
                    IMV res = gradeSelection(grades_a[i])
                        .gp(b.gradeSelection(grades_b[j]))
                        .gradeSelection(grade);
                    result = result.add(res);
                }
            }
        }
        return result;
    }

    IMV up();
    IMV down();
    
    /**
     * The regressive or vee product. (JOIN)
     *
     * This should be implemented in a more performant way.
     *
     * @param a first multivector
     * @param b second multivector
     * @return a & b
     */
    default IMV vee(IMV b) {
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
    IMV add(IMV b);

    /**
     * Multivector subtraction.
     *
     * @param a
     * @param b
     * @return a - b
     */
    default IMV sub(IMV b) {
        return add(b.gpWithScalar(-1d));
    }

    IMV negate14();

    IMV scalarAbs();
    IMV scalarAtan2(IMV y);
    IMV scalarSqrt();

    // neu
    IMV scalarSign();
    IMV scalarSin();
    IMV scalarCos();
    IMV scalarTan();
    IMV scalarAtan();
    IMV scalarAsin();
    IMV scalarAcos();
    
    
    // generische/default Implementierung for multivectors
    // TODO
    IMV exp();

    /**
     * https://enki.ws/ganja.js/examples/coffeeshop.html#NSELGA
     * 
     * implementation for R41 is normalize(1+R) for a rotor/bivector only
     * 
     * @return 
     */
    IMV sqrt();

    // non linear operators/functions
    // [8] M Roelfs and S De Keninck. 2021.
    // Graded Symmetry Groups: Plane and Simple. arXiv:2107.03771 [math-ph]
    // https://arxiv.org/pdf/2107.03771
    // generische/default Implementierung for multivectors
    IMV log();

    
    IMV meet(IMV b);

    IMV join(IMV b);

    /**
     * Euclidean/reverse norm.
     *
     * Calculate the euclidean/reverse norm. (strict positive).
     *
     * TODO vielleicht besser umbenennen in euclideanNorm oder reverseNorm? wird in der impl überschrieben
     *
     * aber was ist mit conjugate based norm?
     * https://math.stackexchange.com/questions/1128844/about-the-definition-of-norm-in-clifford-algebra?rq=1
     *
     */
    default IMV norm() {
        return scp(reverse()).scalarAbs().scalarSqrt();
    }

    /**
     * Ideal norm.
     *
     * Calculate the Ideal norm. (signed)
     */
    IMV inorm();

    /**
     * Normalize a multivector (unit under reverse).
     *
     * Grade-selection is needed if n>3 only, else reverse() is always a scalar.<p>
     *
     * n=p+q+r, R41 corresponds to n=5
     * <p>
     *
     * Reverse norm is different to standard normalization.<p>
     *
     * https://math.stackexchange.com/questions/1128844/about-the-definition-of-norm-in-clifford-algebra?rq=1
     * --> Difference between reverse based and conjugate based norm
     *
     * [Kleppe normed = { _P(1)/(sqrt(abs(_P(1)*_P(1)~))) }
     *
     * Returns a normalized (Euclidean) element.
     *
     * TODO da fehlt mir noch ein test
     */
    default IMV normalizeByReverseNorm() {
        // ist gp(scalar) wirklich das gleiche wie muls? ja
        //TODO sollte ich besser mit Hilfe von reverse/euclidean norm implementieren
        return gp(gp(reverse()).gradeSelection(0).scalarAbs().scalarSqrt().scalarInverse());
    }

    //return division(norm());
    IMV normalizeBySquaredNorm(); // oder idealNorm?

    IMV normalizeRotor();

    /**
     * Das liesse sich in ga-generic implementieren durch Invertieren der gp-Matrix. Dies ist allerdings nicht
     * so performant wie die spezfische cga impl von generalInverse und gp.
     *
     * TODO da fehlt mir noch ein test
     *
     * @param rhs
     * @return
     */
    default IMV div(IMV rhs) {
        return gp(rhs.generalInverse());
    }

    IMV generalInverse();

    IMV scalarInverse();

    /**
     * Inversion of versors is more efficient than inversion of a generic multivector.
     *
     * TODO Untersuchen, ob das besser wieder abgeschafft werden kann und die Implementierung dann intern das
     * argument darauf testet ob ein versor vorliegt und dann die passende Implementierung aufruft.
     *
     * @return inverse of the multivector if the multivector is a versor
     * @throws IllegalArgumentException if the scalarproduct with the rerverse of (IMVSymbolic) this
     * multivector is no * scalar
     */
    default IMV versorInverse() {
        //IMVSymbolic rev = reverse();
        // return rev.gp(gp(rev).scalarInverse());
        // wo kommt diese Implementierung her?
        // scheint falsch zu sein
        // vergleich mit generalInverse liefert Vorzeichenfehler

        IMV R = reverse();
        IMV s = scp(R);
        if (!s.isScalar()) {
            throw new IllegalArgumentException("Multiplication with reverse must be a scalar!");
        }
        //if (s == 0.0) throw new java.lang.ArithmeticException("non-invertible multivector");
        return R.gp(s.scalarInverse());
        // Achtung: es wird nicht gp(double) sondern gp(mv) aufgerufen. Vielleicht ist das ja der Fehler?

        // scheint mir jetzt den gleichen Vorzeichenfehler zu liefern
        // statt gp taucht im test muls(scalar) auf - elementwise Multiplication mit einem scalar
    }
}
