package minpiler.pass;

import java.util.*;

import junit.framework.TestCase;
import minpiler.model.r1.*;
import minpiler.model.c0.*;

public class FlattenTest extends TestCase
{
    private Flatten sut;

    public void test()
    {
        //(let ([x 10]) (- (+ (read) x)))
        R1Expression exp = new R1LetExpression(Arrays.asList(
            new R1VarExpression("x"),
            new R1IntExpression("10"),
            new R1NegExpression(Arrays.asList(
                (R1Expression)new R1AddExpression(Arrays.asList(
                    new R1ReadExpression(),
                    new R1VarExpression("x")
                ))
            ))
        ));

        sut = new Flatten(exp);
        C0Program result = sut.getResult();
        String[] lines = result.toString().split("\n");

        // We will test the string representation of the C0Program
        // Although it makes the test a bit brittle, the test become much more readable
        // Asserting complex object model is not usefull.
        assertEquals("C0Program (tmp.1,tmp.2,tmp.3,x)", lines[0]);
        assertEquals("x = $10",             lines[1]);  
        assertEquals("tmp.1 = read",        lines[2]);
        assertEquals("tmp.2 = tmp.1 + x",   lines[3]);
        assertEquals("tmp.3 = - tmp.2",     lines[4]);
        assertEquals("return tmp.3",        lines[5]);
    }      
}
