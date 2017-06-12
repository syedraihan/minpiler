package minpiler.pass;

import java.util.*;

import minpiler.model.*;
import minpiler.model.x0.*;

public class Patch {

    private X86Program _program;
    private X86Program _result;
    private List<X86Instruction> _instructions;

    public Patch(X86Program program) {
        _program = program;
    }

    public X86Program getResult() {
        if (_result != null)
            return _result;

        _instructions = new ArrayList<X86Instruction>();
        for(X86Instruction inst: _program.getInstructions()) {
            patch(inst);
        }
        
        _result = new X86Program(_program.getVariables(), _instructions);

        return _result;
    }

    private void patch(X86Instruction inst) {
        if (inst instanceof TwoParamInstruction) {
            TwoParamInstruction movInst = (TwoParamInstruction)inst; 
            Argument src  = movInst.getSrc();
            Argument dest = movInst.getDest();

            if ( src instanceof VarArgument && !((VarArgument)src).isRegister() &&
                dest instanceof VarArgument && !((VarArgument)dest).isRegister()) {

                Argument rax = VarArgument.rax();
                _instructions.add(new MovInstruction(src, rax));                
                if (inst instanceof AddInstruction)
                    _instructions.add(new AddInstruction(dest, rax));
                _instructions.add(new MovInstruction(rax, dest));
            } 
            else
                _instructions.add(inst);
        }
        else
            _instructions.add(inst);
    }
}