package compiler.ast;

import compiler.Symbol;

import java.io.OutputStreamWriter;

public class ASTAssignStmt extends ASTStmtNode {
    public final Symbol symbol;
    public final ASTExprNode expression;

    public ASTAssignStmt(Symbol symbol, ASTExprNode expression) {
        this.symbol = symbol;
        this.expression = expression;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "Assign " + this.symbol.m_name + "\n");
        indent += "    ";
        this.expression.print(outStream, indent);       
    }

    @Override
    public void execute() {
        this.symbol.m_number = this.expression.eval();
    }
}
