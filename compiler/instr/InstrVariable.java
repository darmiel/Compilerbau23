package compiler.instr;

import compiler.ExecutionEnvIntf;
import compiler.InstrIntf;
import compiler.Symbol;

import java.io.OutputStreamWriter;

public class InstrVariable extends InstrIntf {

    private final String name;

    public InstrVariable(final String name) {
        this.name = name;
    }

    public void execute(final ExecutionEnvIntf environment) {
        this.m_value = environment.getSymbol(this.name).m_number;
    }

    public void trace(final OutputStreamWriter os) throws Exception {
        os.write(String.format("VARIABLE %s\n", this.name));
    }

}
