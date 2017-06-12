package minpiler;

import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import minpiler.antlr4.*;
import minpiler.antlr4.R1Parser.IntegerContext;
import minpiler.antlr4.R1Parser.VariableContext;

import minpiler.model.r1.*;
import minpiler.pass.*;

public class Parser {

    private String _sourceCode;
    private R1Parser _parser;
    private List<String> _ruleNames;
    private R1Expression _result;

    public Parser(String sourceCode) {
        _sourceCode = sourceCode;
    }

    public R1Expression getResult() {
        if (_result == null) {
            _parser = new R1Parser(new CommonTokenStream(new R1Lexer(new ANTLRInputStream(_sourceCode))));
            _ruleNames = Arrays.asList(R1Parser.ruleNames);
            ParseTree root = _parser.r1().getChild(1);
            _result = parse(root);
        }

        return _result;
    }

    private R1Expression parse(ParseTree node)
    {
        Object payload = node.getPayload();
        if (!(payload instanceof RuleContext)) 
            return null;

        RuleContext rc = (RuleContext)payload;
        String ruleName = _ruleNames.get(rc.getRuleIndex());
        R1Expression expr = null;

        if (ruleName.equals("exp")) {
            expr = parse(node.getChild(0));
        } 

        // '(' 'read' ')'
        else if (ruleName.equals("read_exp")) {
            expr = new R1ReadExpression();   
        }

        // '(' '-' exp ')'
        else if (ruleName.equals("neg_exp")) {
            expr = new R1NegExpression(Arrays.asList(
                parse(node.getChild(2))     // RHS exp
            ));
        }

        // '(' '+' exp exp ')'
        else if (ruleName.equals("add_exp")) {
            expr = new R1AddExpression(Arrays.asList(
                parse(node.getChild(2)),    // LHS exp
                parse(node.getChild(3))     // RHS exp
            ));
        }

        // '(' 'let' '(' '[' variable exp ']' ')' exp ')'
        else if (ruleName.equals("let_exp")) {
            expr = new R1LetExpression(Arrays.asList(
                parse(node.getChild(4)),    // varibale name
                parse(node.getChild(5)),    // let RHS
                parse(node.getChild(8))     // let body
            ));
        }

        else if (ruleName.equals("variable")) {
            VariableContext vc = (VariableContext)rc;
            String varName = vc.VAR().getSymbol().getText();
            expr = new R1VarExpression(varName);
        }
        
        else if (ruleName.equals("integer")) {
            IntegerContext ic = (IntegerContext)rc;
            String intOp = ic.INT().getSymbol().getText();
            expr = new R1IntExpression(intOp);
        } 

        return expr;
    }
}
