package compiler.ast;

import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrJump;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ASTCaseListNode extends ASTCaseNode{
    public List<ASTCaseNode> m_cases;

    public ASTCaseListNode() {
        m_cases = new ArrayList<>();
    }

    public void addStatement(ASTCaseNode caseNode) {
        m_cases.add(caseNode);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        for (int i = 0; i != m_cases.size(); i++) {
            m_cases.get(i).print(outStream, indent);
        }
    }

    public void execute(int value) {
        for (int i = 0; i != m_cases.size(); i++) {
            m_cases.get(i).execute(value);
        }
    }

    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env, ASTExprNode expr) {

        InstrBlock exit = env.createBlock("exit");
        InstrBlock body = env.createBlock("body");

        InstrIntf jmpToBody = new InstrJump(body);
        env.addInstr(jmpToBody);
        env.setCurrentBlock(body);

        InstrIntf exprInstr = expr.codegen(env);

        for (int i = 0; i < m_cases.size(); i++) {
            ASTCaseNode caseNode = m_cases.get(i);
            caseNode.codegen(env, exprInstr, exit);
        }

        InstrIntf jmpToExit = new InstrJump(exit);
        env.addInstr(jmpToExit);
        env.setCurrentBlock(exit);


        return null;
    }
}
