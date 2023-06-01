package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;
import compiler.FunctionInfo;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.Symbol;

public class InstrCallFunction extends InstrIntf {

    private final FunctionInfo _function;

    public InstrCallFunction(FunctionInfo func) {
        _function = func;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        InstrBlock functionBlock = _function.m_body;
        env.pushReturnAddr(env.getInstrIter());
        env.setInstrIter(functionBlock.getIterator());
        for(int i = 0; i < _function.varNames.size(); ++i) {
            Symbol var = env.getSymbol(_function.varNames.get(i));
            var.m_number = env.pop();
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("CALL " + _function.m_name + "\n");
    }
}
