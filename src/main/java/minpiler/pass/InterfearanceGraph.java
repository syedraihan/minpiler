package minpiler.pass;

import java.util.*;

import minpiler.model.x0.*;
import minpiler.model.*;

public class InterfearanceGraph {

    private int[][] _graph;
    private HashMap<String, Integer> _varNumbers;

    public InterfearanceGraph(X86Program program) {
        int index = 0;
        _varNumbers = new HashMap<String, Integer>();
        for (String varName: program.getVariables()) {
            _varNumbers.put(varName, index);
            index++;
        }

        int vertices = program.getVariables().size();
        _graph = new int[vertices][vertices];

        for(X86Instruction inst: program.getInstructions()) 
            processInstruction(inst);
    }
    
    public int[][] getResult() {
        return _graph;
    }

    private void processInstruction(X86Instruction inst) {
        if (!(inst instanceof TwoParamInstruction))
            return;

        TwoParamInstruction twoParamInst = (TwoParamInstruction)inst;
        Argument dest = twoParamInst.getDest();

        if (!(dest instanceof VarArgument))
            return;

        VarArgument destVar = (VarArgument)dest;
        if (destVar.isRegister()) 
            return;

        String destVarName = destVar.getName();
        String srcVarName = "";

        if (twoParamInst instanceof MovInstruction) {
            Argument src  = twoParamInst.getSrc();
            if (src instanceof VarArgument) 
                srcVarName = ((VarArgument)src).getName();
        }

        for(String liveAfterVarName: inst.getLiveVarsAfter()) {
            if (!(liveAfterVarName.equals(srcVarName) || liveAfterVarName.equals(destVarName))) {

                int varFromIndex = _varNumbers.get(liveAfterVarName);
                int varToIndex = _varNumbers.get(destVarName);
                _graph[varFromIndex][varToIndex] = 1;
            }
        }            
    }

    @Override 
    public String toString() {
        StringBuffer out = new StringBuffer();

        out.append(String.format("%6s", ""));
        for (String varName: _varNumbers.keySet()) {
            out.append(String.format("%6s", varName));
        }
        out.append("\n");

        int row = 0;
        for (String varName: _varNumbers.keySet()) {
            out.append(String.format("%6s", varName));
            for (int col=0; col<_graph[0].length; col++) {
                out.append(String.format("%6d", _graph[row][col]));
            }
            row++;
            out.append("\n");
        }       

        return out.toString();
    }
}