package minpiler.model.r1;

import java.util.*;

public class R1ReadExpression extends R1Expression {

    public R1ReadExpression () {
        super();
    }

    @Override
    public int eval() {
        Scanner in = new Scanner(System.in);
        int val = in.nextInt();
        in.close();

        return val;
    }     
}