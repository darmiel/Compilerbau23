package compiler.ast;

import compiler.exceptions.BreakException;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ASTStmtListNode extends ASTStmtNode {

    public List<ASTStmtNode> m_statements;

    public ASTStmtListNode() {
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
    public void execute() throws BreakException {
        for (final ASTStmtNode statement : this.m_statements) {
           if (statement instanceof ASTBreakStmtNode) {
               throw new BreakException();
           }
           statement.execute();
        }
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        for (int i = 0; i != m_statements.size(); i++) {
            m_statements.get(i).codegen(env);
        }
        return null;
    }}
