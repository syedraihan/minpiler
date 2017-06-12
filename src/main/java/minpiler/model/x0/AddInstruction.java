package minpiler.model.x0;

import minpiler.model.*;

public class AddInstruction extends TwoParamInstruction {

    public AddInstruction(Argument src, Argument dest) {
        super(src, dest);
    }

    @Override
    public String toString() {
        return String.format("addq %s, %s", this.getSrc(), this.getDest());
    }    
}