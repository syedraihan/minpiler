package minpiler.model.x0;

public class CallInstruction extends X86Instruction {

    private String _label;
    public CallInstruction(String label) {
        _label = label; 
    }

    @Override
    public String toString() {
        return "callq " + _label;
    }    
}