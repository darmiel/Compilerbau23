package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.stream.Collectors;

import compiler.CompileEnvIntf;
import compiler.FunctionInfo;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrIntegerLiteral;
import compiler.instr.InstrReturnStmt;

public class ASTFunctionStmtNode extends ASTStmtNode {

    private final FunctionInfo _functionInfo;
    
    private final ASTParameterListNode _parameters;
    private final ASTStmtNode _body;

    private static int _functionCounter = 0;
    
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

    @Override
    public InstrIntf codegen(CompileEnvIntf env) throws Exception {
        InstrBlock currentBlock = env.getCurrentBlock();
        InstrBlock funcBlock = env.createBlock("func-" + _functionCounter);
        _functionCounter++;
        _functionInfo.m_body = funcBlock;
        _functionInfo.varNames = _parameters.getParameters()
            .stream()
            .map(e -> e.m_name)
            .collect(Collectors.toList());

        env.setCurrentBlock(funcBlock);
        _body.codegen(env);
        // RETURN 0 if there is no return before (make sure to jump back to the caller)
        env.addInstr(new InstrReturnStmt(new InstrIntegerLiteral(0)));
        env.setCurrentBlock(currentBlock);
        return null;
    }
}
