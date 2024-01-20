package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import util.CayleyTable;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {
<<<<<<< fabian

	void init(Callback callback);

	@Override
	String toString();

	String getName();

	ColumnVectorSparsity getSparsity();

	// operators
	int grade();

	iMultivectorSymbolic gradeSelection(int grade);

	iMultivectorSymbolic gp(iMultivectorSymbolic rhs);

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

	/**
	 * Conjugate.
	 *
	 * Clifford Conjugation
	 *
	 * @param a
	 * @return a.Conjugate()
	 */
	iMultivectorSymbolic conjugate();

	// implementation works only for k-vectors
	// was tun, wenn die grade-summe größer als max grade/pseuscalar-grade?
	// max grade so oft abziehen bis < maxgrade erreicht wird, add(grade a, grade b)
	// in Cayleytable hinzufügen?
	default iMultivectorSymbolic op(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(grade() + b.grade());
	}

	/**
	 * left contraction.
	 *
	 * @param a
	 * @param b
	 * @return a | b
	 */
	default iMultivectorSymbolic lc(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(b.grade() - grade());
	}

	default iMultivectorSymbolic rc(iMultivectorSymbolic b) {
		return gp(b).gradeSelection(grade() - b.grade());
	}

	/**
	 * Vee.
	 *
	 * The regressive product. (JOIN)
	 *
	 * @param a
	 * @param b
	 * @return a & b
	 */
	iMultivectorSymbolic vee(iMultivectorSymbolic b);

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
	 * Sub.
	 *
	 * Multivector subtraction
	 *
	 * @param a
	 * @param b
	 * @return a - b
	 */
	iMultivectorSymbolic sub(iMultivectorSymbolic b);

	// macht vermutlich nur Sinn für scalars
	iMultivectorSymbolic mul(iMultivectorSymbolic b);

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
}
=======
    
    public void init(Callback callback);
    public String toString();
     
    public String getName();
    
    public iMultivectorSymbolic zeroInstance();
    
    public ColumnVectorSparsity getSparsity();
    
    public CayleyTable getCayleyTable();
    
    
    // operators
    
    public int grade();
    public int[] grades();
    
    public iMultivectorSymbolic gradeSelection(int grade);
    
    public iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    public iMultivectorSymbolic gp(double s);
    
    public iMultivectorSymbolic reverse();
    
    public iMultivectorSymbolic gradeInversion();

    public iMultivectorSymbolic pseudoscalar();
    public iMultivectorSymbolic inversePseudoscalar();

    /**
     * Poincare duality.
     *
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual(){
        return lc(inversePseudoscalar());
    }


    /**
     * Clifford Conjugation.
     *
     * @param a
     * @return a.Conjugate()
     */
    public iMultivectorSymbolic conjugate();

    default iMultivectorSymbolic op(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance(); // scheint dense zu sein
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+" op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = getCayleyTable().addGrades(grades_a[i], grades_b[j]);
                System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >=0){
                    //FIXME beim Ausführen des Tests lande ich hier gar nicht, es werden
                    // also keinerlei Komponenten hinzugefügt
                    System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    // hier sehe ich dass alle result componenten 00 sind
                    //FIXME
                    // mir scheint gradeSelection() liefert keine oder falsche Komponenten?
                    System.out.println("res="+res.toString());
                    result.add(res);
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
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance();
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = getCayleyTable().addGrades(grades_a[i], -grades_b[j]);
                if (grade >=0){
                    result.add(gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade));
                }
            }
        }
        return result;
    }
    default iMultivectorSymbolic rc (iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance(); // dense
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = getCayleyTable().addGrades(-grades_a[i], grades_b[j]);
                if (grade >=0){
                    result.add(gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade));
                }
            }
        }
        return result;
    }

    /**
     * The regressive product. (JOIN)
     *
     * @param a
     * @param b
     * @return a & b
     */
    default iMultivectorSymbolic vee (iMultivectorSymbolic b){
         return dual().op(b.dual()).dual().gp(-1);
    }


    /**
     * Multivector addition.
     *
     * @param a
     * @param b
     * @return a + b
     */
    public iMultivectorSymbolic add (iMultivectorSymbolic b);

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
    //FIXME
    // brauche ich das wirklich?
    public iMultivectorSymbolic mul(iMultivectorSymbolic b);
    
    /**
     * Calculate the Euclidean norm. (strict positive).
     */
    public iMultivectorSymbolic norm();

    /**
     * Calculate the Ideal norm. (signed)
     */
    default iMultivectorSymbolic inorm() {
        return dual().norm();
    }
    
    /**
     * Returns a normalized (Euclidean) element.
     */
    public iMultivectorSymbolic normalized();
}
>>>>>>> master
