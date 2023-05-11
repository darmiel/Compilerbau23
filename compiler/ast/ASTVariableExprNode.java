package compiler.ast;

import compiler.SymbolTableIntf;
import compiler.Token;

import java.io.OutputStreamWriter;

public class ASTVariableExprNode extends ASTExprNode {
    private final int value;
    private final Token token;

    public ASTVariableExprNode(Token token, SymbolTableIntf symbolTable) {
        this.token = token;
        this.value = symbolTable.getSymbol(token.m_value).m_number;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + token.m_value + " (" + token.m_type + "): " + this.value + "\n");
    }

    @Override
    public int eval() {
        return this.value;
    }
}