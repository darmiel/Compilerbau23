package compiler.instr;

import compiler.ExecutionEnvIntf;
import compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class InstrMulDiv extends compiler.InstrIntf{
    private compiler.TokenIntf.Type m_type;
    compiler.InstrIntf m_lhs;
    compiler.InstrIntf m_rhs;

    public InstrMulDiv(compiler.TokenIntf.Type type, compiler.InstrIntf lhs, compiler.InstrIntf rhs) {
        m_type = type;
        m_lhs = lhs;
        m_rhs = rhs;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        if (m_type == TokenIntf.Type.MUL) {
            m_value = m_lhs.getValue() * m_rhs.getValue();
        } else {
            m_value = m_lhs.getValue() / m_rhs.getValue();
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(m_type.toString() + '\n');
    }
}
