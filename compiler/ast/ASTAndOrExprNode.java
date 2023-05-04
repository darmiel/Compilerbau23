package compiler.ast;

import compiler.Token;
import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTAndOrExprNode extends ASTExprNode {
    private final ASTExprNode m_lhs;
    private final ASTExprNode m_rhs;
    private final Token m_token;

    public ASTAndOrExprNode(final ASTExprNode lhs, final ASTExprNode rhs, final Token token) {
        m_lhs = lhs;
        m_rhs = rhs;
        m_token = token;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + m_token.toString() + "\n");
        String newIndent = indent + "  ";
        m_lhs.print(outStream, newIndent);
        m_rhs.print(outStream, newIndent);
    }

    @Override
    public int eval() {
        final int lhsVal = m_lhs.eval();
        final int rhsVal = m_rhs.eval();
        if (m_token.m_type == TokenIntf.Type.AND) {
            return (lhsVal > 0 && rhsVal > 0) ? 1 : 0;
        }
        return (lhsVal > 0 || rhsVal > 0) ? 1 : 0;
    }

}
