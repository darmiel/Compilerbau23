package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;
import compiler.TokenIntf.Type;

public class InstrShiftOperation extends compiler.InstrIntf {

    private final compiler.TokenIntf.Type m_type;
    private final compiler.InstrIntf m_lhs;
    private final compiler.InstrIntf m_rhs;

    public InstrShiftOperation(compiler.TokenIntf.Type type, compiler.InstrIntf lhs, compiler.InstrIntf rhs) {
        m_type = type;
        m_lhs = lhs;
        m_rhs = rhs;
     }

    @Override
    public void execute(ExecutionEnvIntf env) {
        if(m_type == Type.SHIFTLEFT) {
            this.m_value = m_lhs.getValue() << m_rhs.getValue();
        }else {
            this.m_value = m_lhs.getValue() >> m_rhs.getValue();
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(m_type.toString() + '\n');        
    }
    
}
