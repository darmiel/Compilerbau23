package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTBreakStmtNode extends ASTStmtNode {

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "BREAK\n");
    }

    @Override
    public void execute() {

    }

}
