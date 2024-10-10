package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.MatrixSparsity;
import util.CayleyTable;

/**
 * Parent interface. Not intended to be implemented directly.
 */
public interface iMultivector<IMultivector extends iMultivector<IMultivector>> {

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

    public boolean isZero();

    /**
     * Get the Cayley-Table for the geometric product.
     *
     * @return
     */
    CayleyTable getCayleyTable();

    int grade();

    int[] grades();

    iConstantsFactory<IMultivector> constants();

    //======================================================
    // Operators
    //======================================================
    default IMultivector commutatorProduct(IMultivector rhs) {
        return gp(rhs).sub(rhs.gp((IMultivector) this)).gp(constants().half());
    }

    default IMultivector projection(IMultivector rhs) {
        if (grade() == -1) {
            throw new IllegalArgumentException("projection only defined for k-vectors!");
        }
        return lc(rhs.generalInverse()).lc(rhs);
    }

    default IMultivector negate() {
        return gpWithScalar(-1);
    }

    default IMultivector square() {
        return gp((IMultivector) this);
    }

    IMultivector gradeSelection(int grade);

    /**
     * Generic GA reverse implementation based on grade selection.
     *
     * [Dorst] p. 604
     *
     * noch unvollständig
     *
     * @return
     */
    /*public IMultivectorSymbolic reverse_(){
        int[] grades = grades();
        IMultivectorSymbolic result = null; //denseEmptyInstance();
        for (int i=0;i<grades.length;i++){
            System.out.println("reverse:grade()="+String.valueOf(grades[i]));
            SX multiplier = SX.pow(new SX(-1d), new SX((new SX(grades[i])),));
            IMultivectorSymbolic norme2 = gradeSelection(grades[i]).
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
    IMultivector reverse();

    IMultivector gp(IMultivector rhs);

    IMultivector gpWithScalar(double s);

    // involute (Ak) = (-1) hoch k * Ak
    // ungetested
    default IMultivector gradeInversion() {
        int[] grades = grades();
        IMultivector result = null; //denseEmptyInstance();
        for (int i = 0; i < grades.length; i++) {
            IMultivector res = gradeSelection(grades[i]).gpWithScalar(Math.pow(-1, grades[i]));
            //TODO
            // eleganter wäre es die for-Schleifen bei 1 starten zu lassen
            // und den ersten Wert vor dem Vorschleifen in die Variable zu streichen
            // dann könnte ich das if vermeiden.
            if (result == null) {
                result = res;
            } else {
                result = result.add(res);
            }
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
    default IMultivector dual() {
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
    /*default IMultivectorSymbolic undual(){
        // alternativ könnte das auch via GA generic via gradeselection implementiert werden
        // nur nicht hier im Interface, da sonst Methoden zurm symoblischen Rechnen von scalars
        // benötigt würden.
        return gp(pseudoscalar());
    }*/
    //TODO
    // default impl? sollte möglich sein ist dann aber nicht so performant, da das
    // Vorzeichen je nach GA model bestimmt werden muss.
    IMultivector undual();

    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    IMultivector conjugate();

    // outer product
    // funktioniert noch nicht - da bekommen ich lauter 00-Elemente
    // vermutlich gilt die entsprechende Formel nur für blades und ich muss
    // den multivector in blades zerlegen und dann über die blades iterieren
    //TODO
    /*default IMultivectorSymbolic op__(IMultivectorSymbolic b){
        return gp(b).add(b.gradeInversion().gp((IMultivectorSymbolic) this)).gp(0.5d);
    }*/
    default IMultivector op(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null; //denseEmptyInstance();
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                //System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+
                //        " op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = grades_a[i] + grades_b[j];
                //System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    //System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    IMultivector res = gradeSelection(grades_a[i]).
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

    /**
     * Generic GA left contraction based on grade selection.
     *
     * The geometric meaning is usually formulated as the dot product between x and y gives the orthogonal
     * complement in y of the projection of x onto y.
     *
     * ganja:
     * <p>
     *
     * LDot(b,r) { r=r||new (IMultivectorSymbolic) this.constructor(); for (var i=0,x,gsx;
     * gsx=grade_start[i],x=(IMultivectorSymbolic) this[i],i<(IMultivectorSymbolic) this.length; i++) if (x)
     * for (var * j=0,y,gsy;gsy=grade_start[j],y=b[j],j<b.length; j++) if (y) for (var a=0; a<x.length; a++)
     * if (x[a]) for (var bb=0; bb<y.length; bb++) if (y[bb]) { if (i==j && a==bb) { r[0] = r[0]||[0]; r[0][0]
     * += x[a]*y[bb]*metric[i][a]; } else { var rn=simplify_bits(basis_bits[gsx+a],basis_bits[gsy+bb]),
     * g=bc(rn[1]), e=bits_basis[rn[1]]-grade_start[g]; if (g == j-i) { if (!r[g])r[g]=[]; r[g][e] =
     * (r[g][e]||0) + rn[0]*x[a]*y[bb]; } } } return r; }
     *
     * @param a
     * @param b
     * @return a | b
     */
    default IMultivector lc(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null;
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = grades_b[j] - grades_a[i];
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMultivector res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null) {
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
     * test failed TODO direkter Vergleich mit lc()
     *
     * @param rhs
     * @return
     */
    default IMultivector lc_(IMultivector rhs) {
        return op(rhs.gp(constants().getInversePseudoscalar())).gp(constants().getPseudoscalar());
    }

    //TODO
    // könnte auch via reversion implementiert werden auf Basis von lc
    default IMultivector rc(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null;
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = grades_a[i] - grades_b[j];
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMultivector res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null) {
                        result = res;
                    } else {
                        result = result.add(res);
                    }
                }
            }
        }
        return result;
    }

    default IMultivector rc2(IMultivector b) {
        return reverse().lc(b.reverse()).reverse();
    }

    default IMultivector scp(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null;
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                if (grades_a[i] == grades_b[j]) {
                    IMultivector res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(0);
                    if (result == null) {
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
     * Dot product - different to inner product: 0-grade products are not excluded from the summation.
     *
     * This fits better to left/right contraction in combination with a scalar product.<p>
     *
     * @param b
     * @return
     */
    default IMultivector dot(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null;
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade >= 0 && grade <= getCayleyTable().getPseudoscalarGrade()) {
                    IMultivector res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null) {
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
     * Original/classical inner product definition which excludes 0-grades from the summation. Better use the
     * dot-product instead.
     *
     * Symmetric contraction:
     * <p>
     *
     * Dot(b,r) { r=r||new (IMultivectorSymbolic) this.constructor(); for (var i=0,x,gsx;
     * gsx=grade_start[i],x=(IMultivectorSymbolic) this[i],i<(IMultivectorSymbolic) this.length; * i++) if (x)
     * for (var j=0,y,gsy;gsy=grade_start[j],y=b[j],j<b.length; j++) if (y) for (var a=0; a<x.length; a++) if
     * (x[a]) for (var bb=0; bb<y.length; bb++) if (y[bb]) { if (i==j && a==bb) { r[0] = r[0]||[0]; r[0][0] +=
     * x[a]*y[bb]*metric[i][a]; } else { var rn=simplify_bits(basis_bits[gsx+a],basis_bits[gsy+bb]),
     * g=bc(rn[1]), e=bits_basis[rn[1]]-grade_start[g]; if (g == Math.abs(j-i)) { if (!r[g])r[g]=[]; r[g][e] =
     * (r[g][e]||0) + rn[0]*x[a]*y[bb]; } } } return r; }
     *
     * @param b
     * @return
     */
    default IMultivector ip(IMultivector b) {
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        IMultivector result = null;
        for (int i = 0; i < grades_a.length; i++) {
            for (int j = 0; j < grades_b.length; j++) {
                int grade = Math.abs(grades_b[j] - grades_a[i]);
                if (grade > 0) {
                    IMultivector res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    if (result == null) {
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
    default IMultivector vee(IMultivector b) {
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
    IMultivector add(IMultivector b);

    /**
     * Multivector subtraction.
     *
     * @param a
     * @param b
     * @return a - b
     */
    default IMultivector sub(IMultivector b) {
        return add(b.gpWithScalar(-1d));
    }

    IMultivector negate14();

    IMultivector scalarAbs();

    IMultivector scalarAtan2(IMultivector y);

    IMultivector scalarSqrt();

    // non linear operators/functions
    // [8] M Roelfs and S De Keninck. 2021.
    // Graded Symmetry Groups: Plane and Simple. arXiv:2107.03771 [math-ph]
    // generische Implementierung for multivectors
    IMultivector exp();

    IMultivector sqrt();

    IMultivector log();

    IMultivector meet(IMultivector b);

    IMultivector join(IMultivector b);

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
    default IMultivector norm() {
        return scp(reverse()).scalarAbs().scalarSqrt();
    }

    /**
     * Ideal norm.
     *
     * Calculate the Ideal norm. (signed)
     */
    IMultivector inorm();

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
    default IMultivector normalizeByReverseNorm() {
        // ist gp(scalar) wirklich das gleiche wie muls? ja
        //TODO sollte ich besser mit Hilfe von reverse/euclidean norm implementieren
        return gp(gp(reverse()).gradeSelection(0).scalarAbs().scalarSqrt().scalarInverse());
    }

    //return division(norm());
    IMultivector normalizeBySquaredNorm(); // oder idealNorm?

    IMultivector normalizeEvenElement();

    /**
     * Das liesse sich in ga-generic implementieren durch Invertieren der gp-Matrix. Dies ist allerdings nicht
     * so performant wie die spezfische cga impl von generalInverse und gp.
     *
     * TODO da fehlt mir noch ein test
     *
     * @param rhs
     * @return
     */
    default IMultivector div(IMultivector rhs) {
        return gp(rhs.generalInverse());
    }

    IMultivector generalInverse();

    IMultivector scalarInverse();

    /**
     * Inversion of versors is more efficient than inversion of a generic multivector.
     *
     * TODO Untersuchen, ob das besser wieder abgeschafft werden kann und die Implementierung dann intern das
     * argument darauf testet ob ein versor vorliegt und dann die passende Implementierung aufruft.
     *
     * @return inverse of the multivector if the multivector is a versor
     * @throws IllegalArgumentException if the scalarproduct with the rerverse of (IMultivectorSymbolic) this
     * multivector is no * scalar
     */
    default IMultivector versorInverse() {
        //IMultivectorSymbolic rev = reverse();
        // return rev.gp(gp(rev).scalarInverse());
        // wo kommt diese Implementierung her?
        // scheint falsch zu sein
        // vergleich mit generalInverse liefert Vorzeichenfehler

        IMultivector R = reverse();
        IMultivector s = scp(R);
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
