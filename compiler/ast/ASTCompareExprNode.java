package compiler.ast;

import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTCompareExprNode extends ASTExprNode {
    ASTExprNode m_lhs;
    ASTExprNode m_rhs;
    compiler.Token m_token;

    public ASTCompareExprNode(ASTExprNode lhs, ASTExprNode rhs, compiler.Token token) {
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
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        compiler.InstrIntf lhs = m_lhs.codegen(env);
        compiler.InstrIntf rhs = m_rhs.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrCompare(m_token.m_type, lhs, rhs);
        env.addInstr(instr);
        return instr;
    }

    @Override
    public int eval() {
        int lhsVal =m_lhs.eval();
        int rhsVal = m_rhs.eval();
        int result = 0;
        if (m_token.m_type == compiler.TokenIntf.Type.LESS) {
            result = lhsVal < rhsVal ? 1 : 0; //🔥
        } else if(m_token.m_type == TokenIntf.Type.GREATER) {
            result = lhsVal > rhsVal ? 1 : 0; //🔥
        } else {
            result = lhsVal == rhsVal ? 1 : 0; //yas queen 💅
        }
        return result;
    }
}
