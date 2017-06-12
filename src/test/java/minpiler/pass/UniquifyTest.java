package minpiler.pass;

import java.util.Arrays;

import junit.framework.TestCase;
import minpiler.model.r1.*;

public class UniquifyTest extends TestCase
{
    Uniquify sut;
    public void test()
    {
        // (let ([x 10]) (+ x (let ([x 20]) x))) 
        R1Expression exp = new R1LetExpression(Arrays.asList(
            new R1VarExpression("x"),
            new R1IntExpression("10"),
            new R1AddExpression(Arrays.asList(
                new R1VarExpression("x"),
                new R1LetExpression(Arrays.asList(
                    new R1VarExpression("x"),
                    new R1IntExpression("20"),
                    new R1VarExpression("x")
                ))
            ))
        ));

        sut = new Uniquify(exp);

        // Result should be: (let ([x1 10]) (+ x1 (let ([x2 20]) x2))) 
        R1LetExpression result = (R1LetExpression)sut.getResult();
        assertEquals("x1", result.getVariable());
        assertEquals("10", result.getRight().getValue());

        R1AddExpression addExp = (R1AddExpression)result.getBody();
        assertEquals("x1", addExp.getLeft().getValue());

        R1LetExpression letExp = (R1LetExpression)addExp.getRight();
        assertEquals("x2", letExp.getVariable());
        assertEquals("20", letExp.getRight().getValue());
        assertEquals("x2", letExp.getBody().getValue());
    }   
}
