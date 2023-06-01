package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

public class InstrCompare extends compiler.InstrIntf {
    private compiler.TokenIntf.Type m_type;
    compiler.InstrIntf m_lhs;
    compiler.InstrIntf m_rhs;

    public InstrCompare(compiler.TokenIntf.Type type, compiler.InstrIntf lhs, compiler.InstrIntf rhs) {
       m_type = type;
       m_lhs = lhs;
       m_rhs = rhs;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        int lhsVal = m_lhs.getValue();
        int rhsVal = m_rhs.getValue();
        if (m_type == compiler.TokenIntf.Type.LESS) {
            m_value = lhsVal < rhsVal ? 1 : 0;
        } else if(m_type == compiler.TokenIntf.Type.GREATER) {
            m_value = lhsVal > rhsVal ? 1 : 0;
        } else {
            m_value = lhsVal == rhsVal ? 1 : 0;
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(m_type.toString() + '\n');        
    }
    
}
