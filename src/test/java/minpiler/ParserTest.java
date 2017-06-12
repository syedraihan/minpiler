package minpiler;

import junit.framework.TestCase;
import minpiler.model.r1.*;

public class ParserTest extends TestCase
{
    private Parser sut;

    public void testRead()
    {
        sut = new Parser("program (read)");
        assertTrue(sut.getResult() instanceof R1ReadExpression);
    }          

    public void testNeg()
    {
        sut = new Parser("program (- 2)");
        R1NegExpression exp = (R1NegExpression)sut.getResult();
        assertEquals("2", exp.getRight().getValue());

        sut = new Parser("program (- x)");
        exp = (R1NegExpression)sut.getResult();
        assertEquals("x", exp.getRight().getValue());
    }
    
    public void testAdd()
    {
        sut = new Parser("program (+ 2 x)");
        R1AddExpression exp = (R1AddExpression)sut.getResult();
        assertEquals("2", exp.getLeft().getValue());
        assertEquals("x", exp.getRight().getValue());
    }   

    public void testLet()
    {
        sut = new Parser("program (let ([x 10]) x)");
        R1LetExpression exp = (R1LetExpression)sut.getResult();

        assertEquals("x", exp.getVariable());
        assertEquals("10", exp.getRight().getValue());
        assertEquals("x", exp.getBody().getValue());
    } 

    public void testNestedExpression()
    {
        sut = new Parser("program (+ (- 1) (+ 2 3))");
        R1AddExpression exp = (R1AddExpression)sut.getResult();
        assertTrue(exp.getLeft() instanceof R1NegExpression);
        assertTrue(exp.getRight() instanceof R1AddExpression);

        R1NegExpression lhs = (R1NegExpression)exp.getLeft();
        R1AddExpression rhs = (R1AddExpression)exp.getRight();

        assertEquals("1", lhs.getRight().getValue());
        assertEquals("2", rhs.getLeft().getValue());
        assertEquals("3", rhs.getRight().getValue());
    }   
}
