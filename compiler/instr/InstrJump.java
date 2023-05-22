package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

public class InstrJump extends compiler.InstrIntf {
    private compiler.InstrBlock m_target;

    public InstrJump(compiler.InstrBlock target) {
        m_target = target;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        env.setInstrIter(m_target.getIterator());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("JUMP " + m_target.getName() + "\n");
    }
    
}
