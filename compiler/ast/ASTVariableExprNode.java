package compiler.ast;

import compiler.Symbol;

import java.io.OutputStreamWriter;

public class ASTVariableExprNode extends ASTExprNode {

    private final String name;
    private final int number;

    public ASTVariableExprNode(final Symbol symbol) {
        this.name = symbol.m_name;
        this.number = symbol.m_number;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("IDENTIFIER ");
        outStream.write(this.name);
        outStream.write("\n");
    }

    @Override
    public int eval() {
        return this.number;
    }

}
