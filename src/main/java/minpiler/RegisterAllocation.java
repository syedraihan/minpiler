package minpiler;

import java.util.*;

import minpiler.model.x0.*;
import minpiler.pass.*;
import minpiler.utils.*;
public class RegisterAllocation {

    private X86Program _program;
    private HashMap<String, Integer> _result;

    private final int AVAILABLE_REGS = 4;
    private static int REG_START = 13;    

    public RegisterAllocation(X86Program program) {
        _program = program;
    }

    public HashMap<String, Integer> getResult() {
        if (_result == null) {
            _result = new HashMap<String, Integer>();
            allocateRegister();
        }

        return _result;
    }

    // Before implementing the Register allocation logic,
    // Just spill everything!
    private void allocateRegisterSimple() {
        int home = 0;
        for(String name: _program.getVariables()) {
            _result.put(name, --home);
        }
    }

    private void allocateRegister() {
        int[][] graph = new InterfearanceGraph(_program).getResult();
        int[] colors = GraphColoring.assignColors(graph);
        log("Graph Coloring", colors);

        for (int i=0; i<colors.length; i++) {
            if (colors[i] <= (AVAILABLE_REGS-1)) 
                // Coloring starts from 0, but we assign register homes from 1,2,3
                // So, just add one
                colors[i] += REG_START;    
            else 
                // Deduct MAX_REG to calculate the index in memory
                // multiply by -1 to indicate it is memory location
                colors[i] = -1 * (colors[i] - AVAILABLE_REGS + 1);
        }

        int i = 0;
        for(String name: _program.getVariables()) {
            _result.put(name, colors[i++]);
        }
        log("Graph Coloring: After Spilling", colors);
    }

    private void log(String title, int[] colors) {
        System.out.println(title);
        System.out.println("==========================");

        int i = 0;
        for(String name: _program.getVariables()) {
            System.out.println(String.format("%6s: %d", name, colors[i++]));
        }

        System.out.println();
    }
}
