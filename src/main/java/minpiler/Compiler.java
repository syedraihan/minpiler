package minpiler;

import java.io.*;
import java.nio.file.*;

import minpiler.model.r1.*;
import minpiler.model.c0.*;
import minpiler.model.x0.*;
import minpiler.pass.*;

public class Compiler {

    private String _sourceCode;

    public Compiler(String sourceCode) {
        _sourceCode = sourceCode;
    }

    public static void main(String[] args) throws Exception {
        Path sourcePath = Paths.get(args[0]);
        String sourceCode = new String(Files.readAllBytes(sourcePath));
        String asmText = new Compiler(sourceCode).getResult();

        String destPath = sourcePath.toString().replace(".r1", ".asm");
        save(destPath, asmText);
    }

    public String getResult() throws IOException {
        R1Expression ast    = new Parser(_sourceCode).getResult();      log(ast,    "Parser");      
        R1Expression pass1  = new Uniquify(ast).getResult();            log(pass1,  "Uniquify");      
        C0Program    pass2  = new Flatten(pass1).getResult();           log(pass2,  "Flatten");      
        X86Program   pass3  = new SelectInstruction(pass2).getResult(); log(pass3,  "SelectInstruction");      
        X86Program   pass41 = new LivenessAnalysis(pass3).getResult();  log(pass41, "LivenessAnalysis");      
        X86Program   pass42 = new AssignHome(pass41).getResult();       log(pass42, "AssignHome");      
        X86Program   pass5  = new Patch(pass42).getResult();            log(pass5,  "Patch");      
        String       pass6  = new Print(pass5).getResult();             log(pass6,  "Print");      

        return pass6;
    }

    private void log(Object result, String title) {
        System.out.println(title);
        System.out.println("==========================");
        System.out.println(result);
    }

    private static void save(String path, String content) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
    }
}
