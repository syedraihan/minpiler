package minpiler.model.r1;

import java.util.*;

public abstract class R1Expression {

    private String _value; 
    private List<R1Expression> _children;

    public R1Expression() {
        _value = "";
        _children = new ArrayList<R1Expression>();
    }

    public R1Expression(List<R1Expression> children) {
        this();
        _children = children;
    }

    public R1Expression(String value) {
        this();
        _value = value;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    public List<R1Expression> getChildren() {
        return _children;
    }
    
    public abstract int eval();

    @Override
    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        print(this, sbuf, 0);

        return sbuf.toString();
    }

    private void print(R1Expression expr, StringBuffer sbuf, int indent) {
        String name = expr.getClass().getSimpleName().replace("R1", "").replace("Expression", "");

        sbuf.append(String.join("", Collections.nCopies(indent, "  ")));
        sbuf.append(String.format("%s %s\n",  name, expr.getValue()));

        for(R1Expression childExpr : expr.getChildren()) {
            print(childExpr, sbuf, indent + 1);
        }
    }    
}