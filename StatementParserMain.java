import java.io.OutputStreamWriter;

public class StatementParserMain {

    public static void main(String[] args) throws Exception {
        String program = compiler.InputReader.fileToString(args[0]);
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser parser = new compiler.Parser(lexer, new compiler.SymbolTable());
        compiler.ast.ASTStmtNode stmt = parser.parseStmt(program);
        stmt.execute();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        stmt.print(outStream, "");
        outStream.flush();
    }

}
