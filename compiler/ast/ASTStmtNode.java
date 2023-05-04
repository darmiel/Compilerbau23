package compiler.ast;

import java.io.OutputStreamWriter;

public abstract class ASTStmtNode extends ASTNode {
    public abstract void print(OutputStreamWriter outStream, String indent) throws Exception;
    public abstract void execute();
}
