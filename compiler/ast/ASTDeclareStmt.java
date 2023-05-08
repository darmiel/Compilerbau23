package compiler.ast;

import compiler.Token;
import compiler.TokenIntf;

public class ASTDeclareStmt extends ASTNode {
    public final String identifier;

    public ASTDeclareStmt(Token identifier) {
        assert identifier.m_type == TokenIntf.Type.IDENT;

        this.identifier = identifier.m_value;
    }

}
