package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import compiler.Symbol;

public class ASTParameterListNode extends ASTStmtNode {

    private final List<Symbol> _parameters;
    
    public ASTParameterListNode() {
        _parameters = new ArrayList<>();
    }

    public void addParameter(Symbol s) {
        _parameters.add(s);
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "PARAMS ");
        if(_parameters.isEmpty()) {
            outStream.write("\n");
        }
        outStream.write(_parameters.get(0).m_name);
        for(int i = 1; i < _parameters.size(); ++i) {
            outStream.write(", " + _parameters.get(i).m_name);
        }
        outStream.write("\n");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
