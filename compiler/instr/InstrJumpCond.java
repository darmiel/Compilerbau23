package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

// sample: jmpcond %2, while_body, while_exit 
public class InstrJumpCond extends compiler.InstrIntf {
    private compiler.InstrIntf m_cond;
    private compiler.InstrBlock m_targetTrue;
    private compiler.InstrBlock m_targetFalse;

    public InstrJumpCond(compiler.InstrIntf cond, compiler.InstrBlock targetTrue, compiler.InstrBlock targetFalse) {
        m_cond = cond;
        m_targetTrue = targetTrue;
        m_targetFalse = targetFalse;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        if (m_cond.getValue() != 0) {
            env.setInstrIter(m_targetTrue.getIterator());
        } else {
            env.setInstrIter(m_targetFalse.getIterator());
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("JUMP COND " + m_targetTrue.getName() + ", " + m_targetFalse.getName() + "\n");
    }
    
}
