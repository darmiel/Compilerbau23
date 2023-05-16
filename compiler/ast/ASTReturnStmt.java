package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTReturnStmt extends ASTStmtNode {

    private final ASTExprNode _expression;

    public ASTReturnStmt(ASTExprNode expression) {
        this._expression = expression;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "RETURN\n");
        indent += "    ";
        _expression.print(outStream, indent);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
