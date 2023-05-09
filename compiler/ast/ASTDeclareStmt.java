package compiler.ast;

import compiler.SymbolTable;
import compiler.Token;
import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class ASTDeclareStmt extends ASTStmtNode {
    public final String identifier;

    public ASTDeclareStmt(Token identifier) {
        assert identifier.m_type == TokenIntf.Type.IDENT;

        this.identifier = identifier.m_value;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "Declare " + identifier);
    }

    @Override
    public void execute() {
        return;
    }
}
