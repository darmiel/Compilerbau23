import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class StatementParserMain {

    public static void main(String[] args) throws Exception {
        String program = compiler.InputReader.fileToString("InterpreterTestInput.txt");
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser parser = new compiler.Parser(lexer, new compiler.SymbolTable());
        compiler.ast.ASTStmtNode stmt = parser.parseStmt(program);
        stmt.execute();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        stmt.print(outStream, "");
        outStream.flush();
    }

}
