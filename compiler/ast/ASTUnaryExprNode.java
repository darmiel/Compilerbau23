package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTUnaryExprNode extends ASTExprNode {
    ASTExprNode m_rhs;
    compiler.Token m_token;

    public ASTUnaryExprNode(ASTExprNode rhs, compiler.Token token){
        m_rhs = rhs;
        m_token = token;
    }

    @Override 
    public void print(OutputStreamWriter outStream, String indent) throws Exception{
        outStream.write(indent + m_token.toString() + "\n");
        String newIndent = indent + "  ";
        m_rhs.print(outStream, newIndent);
    }

    @Override
    public int eval(){
        int rhsVal = m_rhs.eval();
        int result = 0;
        if(m_token.m_type == compiler.TokenIntf.Type.MINUS){
            result = - rhsVal;
        } else {
            result = rhsVal == 0 ? 1 : 0;
        }
        return result;
    }

    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) throws Exception {
        compiler.InstrIntf rhs = m_rhs.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrUnaryExpr(m_token.m_type, rhs);
        env.addInstr(instr);
        return instr;
    }
}