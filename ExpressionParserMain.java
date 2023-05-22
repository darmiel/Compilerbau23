import java.io.OutputStreamWriter;

public class ExpressionParserMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.Parser exprParser = new compiler.Parser(lexer);
        compiler.ast.ASTExprNode expr = exprParser.parseExpression("INTEGER index;" +
                "INTEGER sum;" +
                "index = 10 - 20;" +
                "sum = 0;" +
                "PRINT sum;");
        System.out.print("Ergebnis: ");
        System.out.println(expr.eval());
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        outStream.write("\nAST: \n");
        expr.print(outStream, "");
        outStream.flush();
    }

}
