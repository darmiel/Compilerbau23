package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ASTArgumentListNode extends ASTStmtNode {

    private final List<ASTExprNode> _arguments;

    public ASTArgumentListNode() {
        _arguments = new ArrayList<>();
    }

    public void addArgument(ASTExprNode expr) {
        _arguments.add(expr);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "ARGS\n");
        indent += "    ";
        for(ASTExprNode expr : _arguments) {
            expr.print(outStream, indent);
        }        
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
