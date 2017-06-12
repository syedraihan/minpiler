package minpiler.model.c0;

import java.util.*;
import minpiler.model.*;

public class C0Program {
    private List<String> _variables;
    private List<C0Statement> _statements;
    private Argument _returnArg;

    public C0Program(Argument returnArg) {
        _variables = new ArrayList<String>();
        _statements = new ArrayList<C0Statement>();
        _returnArg = returnArg;
    }

    public C0Program(List<String> variables, List<C0Statement> statements, Argument returnArg) {
        _variables = variables;
        _statements = statements;
        _returnArg = returnArg;
    }

    public List<String> getVariables() {
        return _variables;
    }    

    public List<C0Statement> getStatements() {
        return _statements;
    }

    public Argument getReturnArg() {
        return _returnArg;
    }

    @Override
    public String toString() {
        StringBuffer sbuf = new StringBuffer();

        sbuf.append(String.format("%s (%s)\n", this.getClass().getSimpleName(), String.join(",", _variables)));
        for(C0Statement stmt: _statements) {
            sbuf.append(String.format("%s = %s\n", stmt.getVaribale(), stmt.getValue()));
        }
        sbuf.append(String.format("return %s\n", _returnArg));

        return sbuf.toString();
    }
}