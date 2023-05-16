package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTShiftExprNode extends ASTExprNode {
    
    ASTExprNode m_lhs;
    ASTExprNode m_rhs;
    compiler.Token m_token;

    public ASTShiftExprNode(ASTExprNode lhs, ASTExprNode rhs, compiler.Token token) {
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
        if (m_token.m_type == compiler.TokenIntf.Type.SHIFTLEFT) {
            result = lhsVal << rhsVal;
        } else {
            result = lhsVal >> rhsVal;
        }
        return result;
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        compiler.InstrIntf lhs = m_lhs.codegen(env);
        compiler.InstrIntf rhs = m_rhs.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrShiftOperation(m_token.m_type, lhs, rhs);
        env.addInstr(instr);
        return instr;
    }
}
