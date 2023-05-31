package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.Token;
import compiler.TokenIntf;
import compiler.instr.InstrJump;
import compiler.InstrIntf;
import compiler.InstrBlock;

import java.io.OutputStreamWriter;

public class ASTAndOrExprNode extends ASTExprNode {

    private final ASTExprNode lhs;
    private final ASTExprNode rhs;
    private final Token token;

    public ASTAndOrExprNode(final ASTExprNode lhs, final ASTExprNode rhs, final Token token) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.token = token;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + this.token.toString() + "\n");
        indent += "  ";
        this.lhs.print(outStream, indent);
        this.rhs.print(outStream, indent);
    }

    @Override
    public int eval() {
        final int lhsVal = this.lhs.eval();
        final int rhsVal = this.rhs.eval();
        if (this.token.m_type == TokenIntf.Type.AND) {
            return (lhsVal > 0 && rhsVal > 0) ? 1 : 0;
        }
        return (lhsVal > 0 || rhsVal > 0) ? 1 : 0;
    }

    @Override 
    public InstrIntf codegen(CompileEnvIntf env){
        compiler.InstrIntf lhs = this.lhs.codegen(env);
        compiler.InstrIntf rhs = this.rhs.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrAndOrExpr(token.m_type, lhs, rhs);
        env.addInstr(instr);
        return instr;
    }

}
