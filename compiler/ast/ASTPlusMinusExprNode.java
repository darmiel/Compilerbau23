package compiler.ast;

import compiler.info.ConstInfo;
import compiler.instr.InstrIntegerLiteral;

import java.io.OutputStreamWriter;

public class ASTPlusMinusExprNode extends ASTExprNode {
    ASTExprNode m_lhs;
    ASTExprNode m_rhs;
    compiler.Token m_token;

    public ASTPlusMinusExprNode(ASTExprNode lhs, ASTExprNode rhs, compiler.Token token) {
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
        int lhsVal = m_lhs.eval();
        int rhsVal = m_rhs.eval();
        int result = 0;
        if (m_token.m_type == compiler.TokenIntf.Type.PLUS) {
            result = lhsVal + rhsVal;
        } else {
            result = lhsVal - rhsVal;
        }
        return result;
    }

    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        final ConstInfo constInfo = this.constFold();
        if (constInfo.isConst()) {
            final InstrIntegerLiteral res = new InstrIntegerLiteral(constInfo.getValue());
            env.addInstr(res);
            return res;
        }
        compiler.InstrIntf lhs = m_lhs.codegen(env);
        compiler.InstrIntf rhs = m_rhs.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrPlusMinus(m_token.m_type, lhs, rhs);
        env.addInstr(instr);
        return instr;
    }

    @Override
    public ConstInfo constFold() {
        final ConstInfo lhsConstInfo = this.m_lhs.constFold();
        final ConstInfo rhsConstInfo = this.m_rhs.constFold();
        if (lhsConstInfo.isConst() && rhsConstInfo.isConst()) {
            final int value = this.eval();
            return new ConstInfo(true, value);
        }
        return new ConstInfo(false, 0);
    }
}
