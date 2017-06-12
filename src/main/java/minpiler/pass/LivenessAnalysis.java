package minpiler.pass;

import java.util.*;

import minpiler.model.x0.*;

public class LivenessAnalysis {

    private X86Program _program;

    public LivenessAnalysis(X86Program program) {
        _program = program;

        analyze();
    }

    public X86Program getResult() {
        return _program;
    }
    
    private void analyze() {
        List<X86Instruction> instructions = _program.getInstructions();
        ListIterator<X86Instruction> li = instructions.listIterator(instructions.size());

        X86Instruction nextInstruction = null;

        while(li.hasPrevious()) {
            X86Instruction instruction = li.previous();
            HashSet<String> liveVars = instruction.getLiveVarsAfter();

            liveVars.clear();
            if (nextInstruction != null) {
                for(String var: nextInstruction.getLiveVarsAfter()) 
                    liveVars.add(var);

                liveVars.remove(instruction.getWriteVar());

                String readVar = instruction.getReadVar();
                if (readVar.length() > 0) 
                    liveVars.add(readVar);
            }

            nextInstruction = instruction;
        }        
    }
}