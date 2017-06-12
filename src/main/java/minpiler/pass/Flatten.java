package minpiler.pass;

import java.util.*;
import minpiler.model.*;
import minpiler.model.r1.*;
import minpiler.model.c0.*;

public class Flatten {
    private R1Expression _expr;
    private C0Program _result;
    private int _counter;
    
    public Flatten(R1Expression expr) {
        _expr = expr;
        _counter = 1;
    }

    public C0Program getResult() {
        if (_result == null) {
            _result = flatten(_expr);
        }

        return _result;
    }    

    private C0Program flatten(R1Expression expr) {
        C0Program result = null;

             if (expr instanceof R1ReadExpression) result = flattenRead();
        else if (expr instanceof R1IntExpression)  result = flattenInt(expr);
        else if (expr instanceof R1VarExpression)  result = flattenVar(expr);
        else if (expr instanceof R1NegExpression)  result = flattenNeg(expr);
        else if (expr instanceof R1AddExpression)  result = flattenAdd(expr);
        else if (expr instanceof R1LetExpression)  result = flattenLet(expr);

        return result;
    }

    private C0Program flattenRead() {
        List<String> variables = new ArrayList<String>();
        String x = gensym();
        variables.add(x);
        
        List<C0Statement> statements = new ArrayList<C0Statement>();
        statements.add(new C0Statement(x, new C0ReadExpression()));
        
        return new C0Program(variables, statements, new VarArgument(x));
    }

    private C0Program flattenInt(R1Expression expr) {
        R1IntExpression intExpr = (R1IntExpression)expr;

        return new C0Program(new IntArgument(intExpr.getValue()));
    }

    private C0Program flattenVar(R1Expression expr) {
        R1VarExpression varExpr = (R1VarExpression)expr;

        return new C0Program(new VarArgument(varExpr.getValue()));
    }

    private C0Program flattenNeg(R1Expression expr) {
        R1NegExpression negExpr = (R1NegExpression)expr;

        C0Program right = flatten(negExpr.getRight());

        List<String> variables = new ArrayList<String>();
        String x = gensym();
        variables.addAll(right.getVariables());
        variables.add(x);
        
        List<C0Statement> statements = new ArrayList<C0Statement>();
        statements.addAll(right.getStatements());
        statements.add(new C0Statement(x, new C0NegExpression(right.getReturnArg())));
        
        return new C0Program(variables, statements, new VarArgument(x));
    }

    private C0Program flattenAdd(R1Expression expr) {
        R1AddExpression addExpr = (R1AddExpression)expr;

        C0Program left = flatten(addExpr.getLeft());
        C0Program right = flatten(addExpr.getRight());

        List<String> variables = new ArrayList<String>();
        String x = gensym();
        variables.addAll(left.getVariables());
        variables.addAll(right.getVariables());
        variables.add(x);
        
        List<C0Statement> statements = new ArrayList<C0Statement>();
        statements.addAll(left.getStatements());
        statements.addAll(right.getStatements());
        statements.add(new C0Statement(x, new C0AddExpression(left.getReturnArg(), right.getReturnArg())));
        
        return new C0Program(variables, statements, new VarArgument(x));
    }

    private C0Program flattenLet(R1Expression expr) {
        R1LetExpression letExpr = (R1LetExpression)expr;
        
        C0Program right = flatten(letExpr.getRight());
        C0Program body = flatten(letExpr.getBody());

        List<String> variables = new ArrayList<String>();
        String x = letExpr.getVariable();
        variables.addAll(right.getVariables());
        variables.addAll(body.getVariables());
        variables.add(x);
        
        List<C0Statement> statements = new ArrayList<C0Statement>();
        statements.addAll(right.getStatements());
        statements.add(new C0Statement(x, new C0ArgExpression(right.getReturnArg())));
        statements.addAll(body.getStatements());
        
        return new C0Program(variables, statements, body.getReturnArg());
    }

    private String gensym() {
        return "tmp." + _counter++;
    }
}