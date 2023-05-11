package compiler.ast;

import compiler.Symbol;

import java.io.OutputStreamWriter;

public class ASTVariableExprNode extends ASTExprNode {

    private final Symbol symbol;

    public ASTVariableExprNode(final Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("IDENTIFIER ");
        outStream.write(this.symbol.m_name);
        outStream.write("\n");
    }

    @Override
    public int eval() {
        return this.symbol.m_number;
    }

}
