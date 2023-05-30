package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTBlockStmtNode extends ASTStmtNode {
    private ASTStmtNode m_stmtList;

    public ASTBlockStmtNode(ASTStmtNode stmtList) {
        m_stmtList = stmtList;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "ANONYMOUS BLOCK\n");
        indent += "    ";
        m_stmtList.print(outStream, indent);
    }

    @Override
    public void execute() {
        this.m_stmtList.execute();
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        m_stmtList.codegen(env);
        return null;
    }

    @Override
    public boolean semicolAfter()  { return false; }


    
}
