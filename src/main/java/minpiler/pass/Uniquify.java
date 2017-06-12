package minpiler.pass;

import java.util.*;
import minpiler.model.r1.*;

public class Uniquify {

    private R1Expression _program;
    private int _counter;

    public Uniquify(R1Expression program) {
        _program = program;
        _counter = 1;

        HashMap<String, String> varNameMap = new HashMap<String, String>();
        uniquify(_program, varNameMap);
    }

    public R1Expression getResult() {
        return _program;
    }

    private void uniquify(R1Expression expr, HashMap<String, String> varNameMap) {
        if (expr instanceof R1LetExpression) {
            uniquifyLet(expr, varNameMap);
        }
        else if (expr instanceof R1VarExpression) {
            R1VarExpression varExpr = (R1VarExpression)expr;
            String oldName = varExpr.getValue();
            String newName = varNameMap.get(oldName);
            varExpr.setValue(newName);
        }
        else {
            for(R1Expression childExpr: expr.getChildren()) {
                uniquify(childExpr, varNameMap);
            }
        }
    }

    private void uniquifyLet(R1Expression expr, HashMap<String, String> varNameMap) {
        R1LetExpression letExpr = (R1LetExpression)expr;

        // 
        String oldName = letExpr.getVariable();
        String newName = gensym(oldName);
        while (true) {
            if (!varNameMap.containsKey(newName)) 
                break;
            else
                newName = gensym(newName);
        }
        letExpr.setVariable(newName);

        //
        HashMap<String, String> varNameMap2 = copyMap(varNameMap);
        varNameMap2.put(oldName, newName);
        uniquify(letExpr.getBody(), varNameMap2);

        //
        uniquify(letExpr.getRight(), varNameMap);        
    }

    private String gensym(String varName) {
        return varName + _counter++;
    }

    private HashMap<String, String> copyMap(HashMap<String, String> map) {
        HashMap<String, String> retVal = new HashMap<String, String>();
        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            retVal.put(key, map.get(key));
        }

        return retVal;
    }
}