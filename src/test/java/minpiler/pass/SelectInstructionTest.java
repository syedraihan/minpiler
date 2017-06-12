package minpiler.pass;

import java.util.*;

import junit.framework.TestCase;
import minpiler.model.c0.*;
import minpiler.model.x0.*;
import minpiler.model.*;

public class SelectInstructionTest extends TestCase
{
    private SelectInstruction sut;

    public void test1()
    {
        C0Program prog = new C0Program(
            Arrays.asList("x", "y", "z", "result"), 
            Arrays.asList(
                new C0Statement("x", new C0ArgExpression(new IntArgument("10"))),                       // x = 10
                new C0Statement("y", new C0ReadExpression()),                                           // y = read
                new C0Statement("z", new C0AddExpression(new VarArgument("x"), new VarArgument("y"))),  // z = x + y
                new C0Statement("result", new C0NegExpression(new VarArgument("z")))                    // result = - z
            ),
            new VarArgument("result")
        );

        sut = new SelectInstruction(prog);
        X86Program result = sut.getResult();
        String[] lines = result.toString().split("\n");

        // We will test the string representation of the X86Program
        // Although it makes the test a bit brittle, the test become much more readable
        // Asserting complex object model is not usefull.
        assertEquals("X86Program (x,y,z,result)", lines[0]);
        assertEquals("movq $10, x",         lines[1].trim());  
        assertEquals("callq read_int",      lines[2].trim());
        assertEquals("movq %rax, y",        lines[3].trim());
        assertEquals("movq x, z",           lines[4].trim());
        assertEquals("addq y, z",           lines[5].trim());
        assertEquals("movq z, result",      lines[6].trim()); 
        assertEquals("negq result",         lines[7].trim());
        assertEquals("movq result, %rax",   lines[8].trim());
        assertEquals("movq %rax, %rdi",     lines[9].trim());
        assertEquals("callq print_int",     lines[10].trim());
    }   
}
