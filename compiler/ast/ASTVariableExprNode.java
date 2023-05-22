package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;
import compiler.Symbol;
import compiler.instr.InstrVariable;

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

    @Override
    public InstrIntf codegen(final CompileEnvIntf environment) throws Exception {
        final InstrIntf instr = new InstrVariable(this.symbol.m_name);
        environment.addInstr(instr);
        return instr;
    }

}
