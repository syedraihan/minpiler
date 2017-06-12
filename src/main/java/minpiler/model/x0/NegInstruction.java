package minpiler.model.x0;

import java.util.*;
import minpiler.model.*;

public class NegInstruction extends X86Instruction {

    private Argument _dest;
    public NegInstruction(Argument dest) {
        super(Arrays.asList(dest));
        _dest = dest;
    }

    public Argument getDest() {
        return _dest;
    }

    @Override
    public String toString() {
        return "negq " + _dest;
    }    
}