package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTFunctionCallStmtNode extends ASTStmtNode {

    private final ASTExprNode _expression;

    public ASTFunctionCallStmtNode(ASTExprNode expression) {
        _expression = expression;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        _expression.print(outStream, indent);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
