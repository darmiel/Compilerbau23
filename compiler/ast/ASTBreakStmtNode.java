package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTBreakStmtNode extends ASTStmtNode {

    public static ASTBreakStmtNode BREAK_STATEMENT_NODE = new ASTBreakStmtNode();

    public static class BreakException extends RuntimeException {

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "BREAK\n");
    }

    @Override
    public void execute() {
        throw new BreakException();
    }

}
