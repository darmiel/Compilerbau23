import java.io.OutputStreamWriter;

public class ParantheseParserMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");

        lexer.init("(())");
        parseS(lexer);
        if (lexer.lookAhead().m_type == compiler.TokenIntf.Type.EOF) {
            outStream.write("ACCEPTED");
        } else {
            outStream.write("FAILED");
        }
        outStream.flush();
    }

    public static void parseS(compiler.Lexer lexer) throws Exception {
        compiler.Token currentToken = lexer.lookAhead();
        if () {
            // S : (S)
        } else if () {
            // S : epsilon
        } else {
            lexer.throwCompilerException("invalid paranthese expression", "");
        }
    }

}
