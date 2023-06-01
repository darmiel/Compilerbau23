package compiler.instr;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import compiler.ExecutionEnvIntf;
import compiler.InstrIntf;

public class InstrArgumentList extends InstrIntf {

    private final List<InstrIntf> _arguments;

    public InstrArgumentList() {
        _arguments = new ArrayList<>();
    }

    public void addArgument(InstrIntf arg) {
        _arguments.add(arg);
    }

    @Override
    public void execute(ExecutionEnvIntf env) {
        for(int i = _arguments.size() - 1; i >= 0; --i) {
            env.push(_arguments.get(i).getValue());
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("PUSH ARGS\n");
    }
    
}
