package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTFunctionCallExprNode extends ASTExprNode {

    private final String _functionName;
    private final ASTArgumentListNode _arguments;

    public ASTFunctionCallExprNode(String functionName, ASTArgumentListNode args) {
        _functionName = functionName;
        _arguments = args;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent+"CALL " + _functionName + "\n");
        indent += "    ";
        _arguments.print(outStream, indent);
    }

    @Override
    public int eval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}
