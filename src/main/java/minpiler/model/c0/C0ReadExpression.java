package minpiler.model.c0;

import minpiler.model.*;

public class C0ReadExpression extends C0Expression {

    private Argument _right;

    public C0ReadExpression () {
    }

    @Override
    public String toString() {
        return "read";
    }
}