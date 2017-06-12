package minpiler.pass;

import java.util.*;

import minpiler.*;
import minpiler.model.*;
import minpiler.model.x0.*;

public class AssignHome {

    private X86Program _program;

    public AssignHome(X86Program program) {
        _program = program;

        HashMap<String, Integer> homes = new RegisterAllocation(_program).getResult();
        assign(homes);
    }

    public X86Program getResult() {
        return _program;
    }
    
    private void assign(HashMap<String, Integer> homes) {

        for(X86Instruction inst: _program.getInstructions()) {
            for(Argument arg: inst.getArgs()) {
                if (arg instanceof VarArgument) {
                    VarArgument varArg = (VarArgument)arg;
                    if (varArg.isUnassigned()) {
                        int home = homes.get(varArg.getName());
                        varArg.setHome(home);
                    }
                }
            }
            inst.getLiveVarsAfter().clear();
        }
    }
}