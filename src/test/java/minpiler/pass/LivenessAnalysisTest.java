package minpiler.pass;

import java.util.*;
import junit.framework.TestCase;
import minpiler.model.*;
import minpiler.model.x0.*;

public class LivenessAnalysisTest extends TestCase
{
    private LivenessAnalysis sut;

    public void test()
    {
        X86Program program = new X86Program(
            Arrays.asList("x"), 
            Arrays.asList(
                new MovInstruction(VarArgument.rax(),    new VarArgument("z")),    // {}
                new MovInstruction(VarArgument.rax(),    new VarArgument("y")),    // {z}
                new MovInstruction(new VarArgument("z"), VarArgument.rax()),       // {y, z}
                new AddInstruction(new VarArgument("y"), new VarArgument("x")),    // {y}
                new MovInstruction(new VarArgument("x"), VarArgument.rax()),       // {x} 
                new CallInstruction("print_int")                                   // {}
            )
        );
        sut = new LivenessAnalysis(program);
        X86Program result = sut.getResult();
        List<X86Instruction> instructions = result.getInstructions();

        assertEquals(0, instructions.get(0).getLiveVarsAfter().size());
        assertEquals(1, instructions.get(1).getLiveVarsAfter().size());
        assertEquals(2, instructions.get(2).getLiveVarsAfter().size());
        assertEquals(1, instructions.get(3).getLiveVarsAfter().size());
        assertEquals(1, instructions.get(4).getLiveVarsAfter().size());
        assertEquals(0, instructions.get(5).getLiveVarsAfter().size());

        assertTrue(instructions.get(1).getLiveVarsAfter().contains("z"));
        assertTrue(instructions.get(2).getLiveVarsAfter().contains("y"));
        assertTrue(instructions.get(2).getLiveVarsAfter().contains("z"));
        assertTrue(instructions.get(3).getLiveVarsAfter().contains("y"));
        assertTrue(instructions.get(4).getLiveVarsAfter().contains("x"));
    }   
}
