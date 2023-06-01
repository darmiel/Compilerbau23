package compiler.ast;

import compiler.*;
import compiler.instr.*;

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
        Symbol result = env.createUniqueSymbol("result", 0);

        InstrBlock init = env.createBlock("init");
        InstrBlock compare = env.createBlock("compare");
        InstrBlock exit = env.createBlock("exit");

        env.addInstr(new InstrJump(init));
        env.setCurrentBlock(init);

        InstrIntf lhsVal = new InstrIntegerLiteral(1);
        env.addInstr(lhsVal);
        lhs.codegen(env);
        InstrIntf rhsVal = new InstrIntegerLiteral(rhs.eval());
        env.addInstr(rhsVal);
        rhs.codegen(env);

        if(token.m_type == Token.Type.AND){
            env.addInstr(new InstrAssignStmt(result, lhsVal));
            InstrIntf condition = new InstrJumpCond(lhsVal, compare, exit);
            env.addInstr(condition);
            //compare
            env.setCurrentBlock(compare);
            InstrIntf unaryExp = new InstrAndOrExpr(TokenIntf.Type.AND,lhsVal,rhsVal);
            env.addInstr(unaryExp);
            env.addInstr(new InstrAssignStmt(result, unaryExp));
            env.addInstr(new InstrJump(exit));
        }else if(token.m_type == Token.Type.OR){
            env.addInstr(new InstrAssignStmt(result, lhsVal));
            InstrIntf condition = new InstrJumpCond(lhsVal, exit, compare);
            env.addInstr(condition);
            //compare
            env.setCurrentBlock(compare);
            InstrIntf unaryExp = new InstrAndOrExpr(TokenIntf.Type.OR,lhsVal,rhsVal);
            env.addInstr(unaryExp);
            env.addInstr(new InstrAssignStmt(result, unaryExp));
            env.addInstr(new InstrJump(exit));
        }

        //exit
        env.setCurrentBlock(exit);
        return new InstrIntegerLiteral(result.m_number);
    }

}
