package compiler.instr;

import java.io.OutputStreamWriter;
import java.util.ListIterator;

import compiler.ExecutionEnvIntf;
import compiler.InstrIntf;

public class InstrReturnStmt extends InstrIntf {

    private final InstrIntf _expression;

    public InstrReturnStmt(InstrIntf expr) {
        this._expression = expr;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        ListIterator<InstrIntf> returnAdress = env.popReturnAddr();
        env.setInstrIter(returnAdress);
        env.push(_expression.getValue());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'trace'");
    }
    
}
