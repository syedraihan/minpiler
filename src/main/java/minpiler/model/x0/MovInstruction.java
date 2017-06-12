package minpiler.model.x0;

import java.util.*;
import minpiler.model.*;

public class MovInstruction extends TwoParamInstruction {

    public MovInstruction(Argument src, Argument dest) {
        super(src, dest);
    }

    @Override
    public String toString() {
        return String.format("movq %s, %s", this.getSrc(), this.getDest());
    }
}