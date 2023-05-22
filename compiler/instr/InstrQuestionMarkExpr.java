package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

public class InstrQuestionMarkExpr extends compiler.InstrIntf {
    private compiler.InstrIntf m_instrCond;
    private compiler.InstrIntf m_instrValue1;
    private compiler.InstrIntf m_instrValue2;

    public InstrQuestionMarkExpr(compiler.InstrIntf instrCond, compiler.InstrIntf instrValue1, compiler.InstrIntf instrValue2) {
        m_instrCond = instrCond;
        m_instrValue1 = instrValue1;
        m_instrValue2 = instrValue2;
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        if (m_instrCond.getValue() !=  0) {
            m_value = m_instrValue1.getValue();
        } else {
            m_value = m_instrValue2.getValue();
        }       
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("QUESTIONMARK\n");
    }
    
}
