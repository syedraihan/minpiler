package minpiler.model.r1;

import java.util.*;

public class R1LetExpression extends R1Expression {

    public R1LetExpression (List<R1Expression> children) {
        super(children);
    }

    public String getVariable() {
        return this.getChildren().get(0).getValue();
    }

    public void setVariable(String value) {
        this.getChildren().get(0).setValue(value);
    }

    public R1Expression getRight() {
        return this.getChildren().get(1);
    }

    public R1Expression getBody() {
        return this.getChildren().get(2);
    }

    @Override
    public int eval() {
        return this.getBody().eval();
    }  
}