package minpiler.pass;

import java.util.*;
import minpiler.model.*;
import minpiler.model.c0.*;
import minpiler.model.x0.*;

public class SelectInstruction {

    private C0Program _program;
    private X86Program _result;
    private List<X86Instruction> _instructions;

    public SelectInstruction(C0Program program) {
        _program = program;
        _instructions = new ArrayList<X86Instruction>();
    }

    public X86Program getResult() {
        if (_result == null) {
            for (C0Statement stmt: _program.getStatements()) {
                C0Expression rhs = stmt.getValue();
                     if (rhs instanceof C0ArgExpression)  selectArg(stmt); 
                else if (rhs instanceof C0NegExpression)  selectNeg(stmt); 
                else if (rhs instanceof C0AddExpression)  selectAdd(stmt); 
                else if (rhs instanceof C0ReadExpression) selectRead(stmt); 
            }
            selectRet();
            _result = new X86Program(_program.getVariables(), _instructions);
        }

        return _result;
    }

    private void selectArg(C0Statement stmt) {
        Argument x = new VarArgument(stmt.getVaribale());
        Argument arg = ((C0ArgExpression)stmt.getValue()).getVaue();

        _instructions.add(new MovInstruction(arg, x));
    }

    private void selectNeg(C0Statement stmt) {
        Argument x = new VarArgument(stmt.getVaribale());
        Argument arg = ((C0NegExpression)stmt.getValue()).getRight();

        _instructions.add(new MovInstruction(arg, x));
        _instructions.add(new NegInstruction(x));
    }

    private void selectAdd(C0Statement stmt) {
        Argument x = new VarArgument(stmt.getVaribale());
        Argument lhs = ((C0AddExpression)stmt.getValue()).getLeft();
        Argument rhs = ((C0AddExpression)stmt.getValue()).getRight();

        _instructions.add(new MovInstruction(lhs, x));
        _instructions.add(new AddInstruction(rhs, x));
    }

    private void selectRead(C0Statement stmt) {
        Argument x = new VarArgument(stmt.getVaribale());

        _instructions.add(new CallInstruction("read_int"));
        _instructions.add(new MovInstruction(VarArgument.rax(), x));
    }

    private void selectRet() {
        Argument rax = VarArgument.rax();
        Argument rdi = VarArgument.rdi();

        _instructions.add(new MovInstruction(_program.getReturnArg(), rax));
        _instructions.add(new MovInstruction(rax, rdi));
        _instructions.add(new CallInstruction("print_int"));
    }
}