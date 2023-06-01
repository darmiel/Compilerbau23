package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;
import compiler.InstrIntf;

public class InstrPop extends InstrIntf {

    @Override
    public void execute(ExecutionEnvIntf env) {
        this.m_value = env.pop();
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("POP\n");
    }
    
}
