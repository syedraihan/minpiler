package minpiler.pass;

import java.util.*;
import junit.framework.TestCase;
import minpiler.model.VarArgument;
import minpiler.model.x0.*;

public class PatchTest extends TestCase
{
    Patch sut;

    public void test()
    {
        X86Program program = new X86Program(
            Arrays.asList("x1", "x2"), 
            Arrays.asList(
                new MovInstruction(new VarArgument("x1"), new VarArgument("x2")),
                new AddInstruction(new VarArgument("x1"), new VarArgument("x2")),
                new CallInstruction("read_int")
            )
        );

        sut = new Patch(program);
        X86Program result = sut.getResult();
        String[] lines = result.toString().split("\n");

        // We will test the string representation of the X86Program
        // Although it makes the test a bit brittle, the test become much more readable
        // Asserting complex object model is not usefull.
        assertEquals("X86Program (x1,x2)", lines[0]);
        assertEquals("movq x1, %rax",      lines[1].trim());
        assertEquals("movq %rax, x2",      lines[2].trim());
        assertEquals("movq x1, %rax",      lines[3].trim());
        assertEquals("addq x2, %rax",      lines[4].trim());
        assertEquals("movq %rax, x2",      lines[5].trim());
        assertEquals("callq read_int",     lines[6].trim());
    }   
}
