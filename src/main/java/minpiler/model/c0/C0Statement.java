package minpiler.model.c0;

public class C0Statement {
    private String _variable;
    private C0Expression _value;

    public C0Statement(String variable, C0Expression value) {
        _variable = variable;
        _value = value;
    }

    public String getVaribale() {
        return _variable;
    }

    public C0Expression getValue() {
        return _value;
    }    
}