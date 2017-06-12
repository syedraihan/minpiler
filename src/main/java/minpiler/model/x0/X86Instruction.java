package minpiler.model.x0;

import java.util.*;
import minpiler.model.*;

public abstract class X86Instruction {

    private List<Argument> _args;
    private HashSet<String> _liveAfter;

    public X86Instruction() {
        _args = new ArrayList<Argument>();
        _liveAfter = new HashSet<String>();
    }

    public X86Instruction(List<Argument> args) {
        this();
        _args = args;
    }

    public List<Argument> getArgs() {
        return _args;
    }

    public String getReadVar() {
        return "";
    }

    public String getWriteVar() {
        return "";
    }
    
    public HashSet<String> getLiveVarsAfter() {
        return _liveAfter;
    }    
}