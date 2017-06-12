package minpiler.model.x0;

import java.util.*;

public class X86Program {
    private List<String> _variables;
    private List<X86Instruction> _instructions;

    public X86Program(List<String> variables, List<X86Instruction> instructions) {
        _variables = variables;
        _instructions = instructions;
    }

    public List<X86Instruction> getInstructions() {
        return _instructions;
    }

    public List<String> getVariables() {
        return _variables;
    }    

    @Override 
    public String toString() {
        StringBuffer out = new StringBuffer();

        String name = this.getClass().getSimpleName();
        out.append(String.format("%s (%s)\n", name, String.join(",", _variables))); 
        for(X86Instruction inst: _instructions) {
            HashSet<String> liveVars = inst.getLiveVarsAfter();
            String liveVarsOut = liveVars.size() > 0 ? String.format("{%s}", String.join(",", liveVars)) : ""; 
            out.append(String.format("%-30s %s\n", inst, liveVarsOut));
        }

        return out.toString();
    }      
}