package minpiler.model;

public class IntArgument extends Argument {

    private String _value;

    public IntArgument(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return "$" + _value;
    }
}