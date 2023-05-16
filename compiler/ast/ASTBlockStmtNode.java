package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ASTBlockStmtNode extends ASTStmtNode {

    public List<ASTStmtNode> m_statements;

    public ASTBlockStmtNode() {
        m_statements = new ArrayList<>();
    }

    public void addStatement(ASTStmtNode stmtNode) {
        m_statements.add(stmtNode);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        for (int i = 0; i != m_statements.size(); i++) {
            m_statements.get(i).print(outStream, indent);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i != m_statements.size(); i++) {
            m_statements.get(i).execute();
        }
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        for (int i = 0; i != m_statements.size(); i++) {
            m_statements.get(i).codegen(env);
        }
        return null;
    }}
