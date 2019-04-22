package com.kunminx.rxmagic.bean;

import com.kunminx.rxmagic.bean.RxOperator;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpression {

    /**
     * operator of a operator
     */
    private RxOperator rxOperator;
    /**
     * expression of a operator
     */
    private String expression;

    public RxOperator getRxOperator() {
        return rxOperator;
    }

    public void setRxOperator(RxOperator rxOperator) {
        this.rxOperator = rxOperator;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
