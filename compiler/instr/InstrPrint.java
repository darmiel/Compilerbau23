package compiler.instr;

import java.io.OutputStreamWriter;

import compiler.ExecutionEnvIntf;

public class InstrPrint extends compiler.InstrIntf {
    private compiler.InstrIntf m_expr;

    public InstrPrint(compiler.InstrIntf instrExpr) {
        m_expr = instrExpr;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) {
        System.out.println(m_expr.getValue());        
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("PRINT\n");
    }
    
}
