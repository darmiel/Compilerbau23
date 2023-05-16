package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTFunctionBodyStmtNode extends ASTStmtNode {

    private ASTStmtNode m_stmtList;

    public ASTFunctionBodyStmtNode(ASTStmtNode stmtList) {
        m_stmtList = stmtList;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        m_stmtList.print(outStream, indent);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
