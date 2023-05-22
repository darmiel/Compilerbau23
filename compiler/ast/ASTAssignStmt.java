package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;
import compiler.Symbol;

import java.io.OutputStreamWriter;

public class ASTAssignStmt extends ASTStmtNode {
    private final Symbol symbol;
    private final ASTExprNode expression;

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

    public InstrIntf codegen(CompileEnvIntf env) throws Exception {
        InstrIntf expInstr = expression.codegen(env);
        InstrIntf instr = new compiler.instr.InstrAssignStmt(symbol, expInstr);
        env.addInstr(instr);
        return instr;
    }
}
