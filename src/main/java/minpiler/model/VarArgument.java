package minpiler.model;

public class VarArgument extends Argument{

    private String _name;
    private int _home;      // > 0 for registers, < 0 for memory

    private static int RAX = 1;
    private static int RDI = 8;
    private static String[] _registers = {"",
        "rax",  "rbx",  "rcx",  "rdx",         
        "rbp",  "rsp",  "rsi",  "rdi",
        "r8",   "r9",   "r10",  "r11", 
        "r12",  "r13",  "r14",  "r15"
    };

    public VarArgument(int home) {
        _name = "";
        _home = home;     
    }

    public VarArgument(String name) {
        _name = name;
        _home = 0;     // unassigned
    }

    public String getName() {
        return _name;
    }

    public int getHome() {
        return _home;
    }

    public void setHome(int value) {
        _home = value;
    }

    public boolean isRegister() {
        return _home > 0;
    }

    public boolean isUnassigned() {
        return _home == 0;
    }

    public static VarArgument rax() {
        return new VarArgument(RAX);
    }

    public static VarArgument rdi() {
        return new VarArgument(RDI);
    }

    @Override
    public String toString() {
        if (_home > 0)
            return "%" + _registers[_home];            
        else if (_home < 0) 
            return String.format("%d(%%rbp)", _home * 8);
        else
            return _name;
    }
}