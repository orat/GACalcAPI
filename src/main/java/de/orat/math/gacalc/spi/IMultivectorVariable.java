package de.orat.math.gacalc.spi;

public interface IMultivectorVariable<EXPR extends IMultivectorExpression<EXPR, VAR, VAL>, VAR extends IMultivectorVariable<EXPR, VAR, VAL>, VAL extends IMultivectorValue<EXPR, VAR, VAL>>
    extends IMultivectorExpression<EXPR, VAR, VAL> {

    String getName();

    /**
     * Downcast. Expected to work always. Only the current subtype should be assigned to the generic
     * parameter.
     */
    VAR asVAR();
}
