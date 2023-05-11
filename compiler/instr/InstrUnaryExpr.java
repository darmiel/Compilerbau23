package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

public class InstrUnaryExpr extends compiler.InstrIntf {
    private compiler.TokenIntf.Type m_type;
    compiler.InstrIntf m_rhs;

    public InstrUnaryExpr(compiler.TokenIntf.Type type, compiler.InstrIntf rhs) {
       m_type = type;
       m_rhs = rhs;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        if (m_type == compiler.TokenIntf.Type.NOT) {
            m_value = - m_rhs.getValue();
        } else {
            m_value = m_rhs.getValue() == 0 ? 1 : 0;
        }        
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(m_type.toString() + '\n');        
    }
    
}
