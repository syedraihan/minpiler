package minpiler.model.c0;

import minpiler.model.*;

public class C0ArgExpression extends C0Expression {

    private Argument _value;

    public C0ArgExpression (Argument value) {
        super();
        _value = value;
    }

    public Argument getVaue() {
        return _value;
    } 

    @Override
    public String toString() {
        return _value.toString();
    }      
}