package compiler.ast;

import compiler.Token;
import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTParantheseExprNode extends ASTExprNode{

    ASTExprNode inner;

    public ASTParantheseExprNode(ASTExprNode inner){
        this.inner = inner;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + TokenIntf.Type.LPAREN + " " + TokenIntf.Type.RPAREN + "\n");
        indent = indent + "  ";
        inner.print(outStream, indent);
    }

    @Override
    public int eval() {
        return inner.eval();
    }
}
