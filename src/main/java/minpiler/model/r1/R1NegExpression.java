package minpiler.model.r1;

import java.util.*;

public class R1NegExpression extends R1Expression {

    public R1NegExpression (List<R1Expression> children) {
        super(children);
    }

    public R1Expression getRight() {
        return this.getChildren().get(0);
    }

    @Override
    public int eval() {
        return -1 * this.getRight().eval();
    }    
}