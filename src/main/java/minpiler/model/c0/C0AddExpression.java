package minpiler.model.c0;

import minpiler.model.*;

public class C0AddExpression extends C0Expression {

    private Argument _left;
    private Argument _right;

    public C0AddExpression (Argument left, Argument right) {
        _left = left;
        _right = right;
    }

    public Argument getLeft() {
        return _left;
    }

    public Argument getRight() {
        return _right;
    }

    @Override
    public String toString() {
        return _left + " + " + _right;
    }    
}