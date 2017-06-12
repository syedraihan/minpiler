package minpiler.model.r1;

import java.util.*;

public class R1AddExpression extends R1Expression {

    public R1AddExpression (List<R1Expression> children) {
        super(children);
    }

    public R1Expression getLeft() {
        return this.getChildren().get(0);
    }

    public R1Expression getRight() {
        return this.getChildren().get(1);
    }

    @Override
    public int eval() {
        return this.getLeft().eval() + this.getRight().eval();
    }
}