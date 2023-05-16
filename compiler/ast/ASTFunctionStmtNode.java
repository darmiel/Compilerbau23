package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.FunctionInfo;

public class ASTFunctionStmtNode extends ASTStmtNode {

    private final FunctionInfo _functionInfo;
    
    private final ASTParameterListNode _parameters;
    private final ASTStmtNode _body;
    
    public ASTFunctionStmtNode(FunctionInfo functionInfo, ASTParameterListNode parameters, ASTStmtNode body) {
        this._functionInfo = functionInfo;
        this._parameters = parameters;
        this._body = body;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "FUNCTION " + _functionInfo.m_name + "\n");
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
