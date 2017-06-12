package minpiler.pass;

import minpiler.model.x0.*;

public class Print {
    private X86Program _program;
    private StringBuffer _result;
    private int _stackSize;

    public Print(X86Program program) {
        _program = program;
        _stackSize = _program.getVariables().size();
        _stackSize += (_stackSize % 2);
        _stackSize *= 8;
    }

    public String getResult() {
        if (_result == null) {
            print();
        }

        return _result.toString();
    }    

    private void print() {
        _result = new StringBuffer();

        write(".global main");
        write(".text");
        write("main:");
        setup();
        for(X86Instruction inst: _program.getInstructions()) {
            write(inst.toString());
        }
        restore();
    }

    private void setup() {
        write("pushq %rbp");
        write("movq %rsi, %rbp");
        write(String.format("subq $%d, %%rsi", _stackSize));        
    }

    private void restore() {
        write(String.format("addq $%d, %%rsi", _stackSize));
        write("popq %rbp");
        write("retq");
    }

    private void write(String line) {
        _result.append(line);
        _result.append("\n");
    }
}