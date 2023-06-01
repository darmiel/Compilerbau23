package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrCompare;
import compiler.instr.InstrIntegerLiteral;
import compiler.instr.InstrJump;
import compiler.instr.InstrJumpCond;

import java.io.OutputStreamWriter;

public class ASTCaseNode extends ASTNode {

    public ASTIntegerLiteralNode m_number;
    public ASTStmtNode m_stmtList;

    public ASTCaseNode (ASTIntegerLiteralNode number, ASTStmtNode stmtList) {
        m_number = number;
        m_stmtList = stmtList;
    }

    public ASTCaseNode() {
    }
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "Case " + m_number.m_value + " :\n");
        indent += "    ";
        this.m_stmtList.print(outStream, indent);
    };

    public void execute(int value) {
        if (value == Integer.parseInt(m_number.m_value)) {
            m_stmtList.execute();
        }
    };

    public InstrIntf codegen(CompileEnvIntf env, InstrIntf cond, InstrBlock exit_switch) {

        InstrBlock execute = env.createBlock("execute_" + m_number.m_value);
        InstrBlock check = env.createBlock("check_" + m_number.m_value);
        InstrBlock exit = env.createBlock("exit_"+ m_number.m_value);

        InstrIntf jmpToCheck = new InstrJump(check);
        env.addInstr(jmpToCheck);
        env.setCurrentBlock(check);

        InstrIntf numberLiteral = m_number.codegen(env);

        InstrCompare compareCondLiteral = new InstrCompare(compiler.TokenIntf.Type.EQUAL, cond, numberLiteral);
        env.addInstr(compareCondLiteral);

        InstrIntf jmpToExecute = new InstrJumpCond(compareCondLiteral, execute, exit);
        env.addInstr(jmpToExecute);
        env.setCurrentBlock(execute);

        m_stmtList.codegen(env);
        InstrIntf jmpToExit = new InstrJump(exit_switch);
        env.addInstr(jmpToExit);
        env.setCurrentBlock(exit);

        return null;
    }
}
