package minpiler.model.x0;

import java.util.*;
import minpiler.model.*;

public abstract class TwoParamInstruction extends X86Instruction {

    private Argument _src;
    private Argument _dest;

    public TwoParamInstruction(Argument src, Argument dest) {
        super(Arrays.asList(src, dest));
        _src = src;
        _dest = dest;
    }

    public Argument getSrc() {
        return _src;
    }

    public Argument getDest() {
        return _dest;
    }


    @Override
    public String getReadVar() {
        Argument arg = this.getSrc();
        if (!(arg instanceof VarArgument))
            return "";

        return ((VarArgument)arg).getName();
    }

    @Override
    public String getWriteVar() {
        Argument arg = this.getDest();
        if (!(arg instanceof VarArgument))
            return "";

        return ((VarArgument)arg).getName();
    }
}