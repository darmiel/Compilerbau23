package compiler.ast;

import java.io.OutputStreamWriter;

/**
 * questionMarkExpr -> value1 if andOrResult equals 1 (true) else value2
 */
public class ASTQuestionMarkNode extends ASTExprNode {
    public ASTExprNode cond, value1, value2;

    public ASTQuestionMarkNode(ASTExprNode cond, ASTExprNode value1, ASTExprNode value2) {
        this.cond = cond;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("?");
        cond.print(outStream, indent + "  ");
        value1.print(outStream, indent + "  ");
        value2.print(outStream, indent + "  ");
    }

    @Override
    public int eval() {
        return this.cond.eval() == 1 ? this.value1.eval() : this.value2.eval();
    }
}