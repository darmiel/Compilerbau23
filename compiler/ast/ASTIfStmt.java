package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTIfStmt extends ASTStmtNode {

    public final ASTExprNode condition;
    public final ASTStmtNode codeTrue;
    public final ASTStmtNode rightChild;

    public ASTIfStmt(ASTExprNode condition, ASTStmtNode codeTrue, ASTStmtNode rightChild) {
        this.condition = condition;
        this.codeTrue = codeTrue;
        this.rightChild = rightChild;
    }
    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        // Unsure if Indentation is correct
        outStream.write(indent + "IF\n");
        condition.print(outStream, indent);
        outStream.write("\n");
        codeTrue.print(outStream, indent);
        outStream.write("\n");
        rightChild.print(outStream, indent);
    }

    @Override
    public void execute() {
        if (condition.eval() != 0) {
            codeTrue.execute();
        } else if (rightChild != null) {
            rightChild.execute();
        }
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        // TODO
        return null;
    }
}
