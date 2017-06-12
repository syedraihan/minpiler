package minpiler.model.r1;

public class R1IntExpression extends R1Expression {

    public R1IntExpression (String operand) {
        super(operand);
    }


    @Override
    public int eval() {
        return Integer.parseInt(this.getValue());
    }    
}