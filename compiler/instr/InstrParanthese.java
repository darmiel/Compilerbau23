package compiler.instr;

import compiler.ExecutionEnvIntf;

import java.io.OutputStreamWriter;

public class InstrParanthese extends compiler.InstrIntf {

    compiler.InstrIntf inner;
    public InstrParanthese(compiler.InstrIntf inner) {
        this.inner = inner;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        m_value = inner.getValue();
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("PAREN\n");
    }
}
