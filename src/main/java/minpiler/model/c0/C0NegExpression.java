package minpiler.model.c0;

import minpiler.model.*;

public class C0NegExpression extends C0Expression {

    private Argument _right;

    public C0NegExpression (Argument right) {
        _right = right;
    }

    public Argument getRight() {
        return _right;
    }

    @Override
    public String toString() {
        return "- " + _right;
    }     
}