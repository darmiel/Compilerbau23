package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class ASTSwitchCaseStmtNode extends ASTStmtNode{

    ASTExprNode m_expr;
    ASTCaseListNode m_caseList;

    public ASTSwitchCaseStmtNode (ASTExprNode expr, ASTCaseListNode caseList) {
        m_expr = expr;
        m_caseList = caseList;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "Switch ");
        this.m_expr.print(outStream, indent);
        outStream.write("\n");
        indent += "    ";
        this.m_caseList.print(outStream, indent);
    }

    @Override
    public void execute() {
        for (int i=0; i < m_caseList.m_cases.size(); i++){
            if (m_expr.eval() == Integer.parseInt(m_caseList.m_cases.get(i).m_number.m_value)) {
                m_caseList.m_cases.get(i).execute(m_expr.eval());
            }
        }
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        m_caseList.codegen(env, m_expr);
        return null;
    }
}
