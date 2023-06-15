import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class languageMain {

	public static void main(String[] args) throws Exception {
		// create input stream
		CharStream input = CharStreams.fromFileName("language.txt");
		// create lexer
		antlrcompiler.languageLexer lexer = new antlrcompiler.languageLexer(input);
		// create token stream
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// create parser
		antlrcompiler.languageParser parser = new antlrcompiler.languageParser(tokens);
		parser.setBuildParseTree(true);
		// build parse tree
		ParseTree tree = parser.sumExpr();
		// output parse tree
		System.out.println(tree.toStringTree(parser));
    }
}
