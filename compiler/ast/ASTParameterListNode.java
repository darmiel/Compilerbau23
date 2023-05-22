package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import compiler.Symbol;

public class ASTParameterListNode extends ASTStmtNode {

    private final List<Symbol> _parameters;
    
    public ASTParameterListNode() {
        _parameters = new ArrayList<>();
    }

    public void addParameter(Symbol s) {
        _parameters.add(s);
    }

    public List<Symbol> getParameters() {
        return _parameters;
    }

    public List<String> getParametersAsStringList() {
        return _parameters
            .stream()
            .map(e -> e.m_name)
            .collect(Collectors.toList());
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
