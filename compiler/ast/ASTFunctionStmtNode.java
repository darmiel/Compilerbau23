package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.Symbol;

public class ASTFunctionStmtNode extends ASTStmtNode {

    private final Symbol _functionName;
    
    private final ASTParameterListNode _parameters;
    private final ASTStmtNode _body;
    
    public ASTFunctionStmtNode(Symbol functionName, ASTParameterListNode parameters, ASTStmtNode body) {
        this._functionName = functionName;
        this._parameters = parameters;
        this._body = body;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "FUNCTION " + _functionName.m_name + "\n");
        indent += "    ";
        _parameters.print(outStream, indent);
        _body.print(outStream, indent);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
