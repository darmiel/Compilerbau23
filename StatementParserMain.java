import compiler.CompileEnv;

import java.io.OutputStreamWriter;

public class StatementParserMain {

    public static final boolean RUN_EXEC = true;
    public static final boolean RUN_CODE_GEN = true;

    public static void main(String[] args) throws Exception {
        String program = compiler.InputReader.fileToString("Loop.txt");
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser parser = new compiler.Parser(lexer, new compiler.SymbolTable());
        compiler.ast.ASTStmtNode stmt = parser.parseStmt(program);

        final OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        if (RUN_EXEC) {
            System.out.println("===== EXEC =====");
            stmt.execute();
            stmt.print(outStream, "");
            outStream.flush();
        }

        if (RUN_CODE_GEN) {
            System.out.println("===== CGEN =====");
            final CompileEnv env = new CompileEnv(program, false);
            env.compile();
            env.dump(System.out);
            env.execute(outStream);
            outStream.flush();
        }
    }

}
