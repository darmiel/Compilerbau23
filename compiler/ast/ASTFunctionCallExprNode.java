package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.CompileEnvIntf;
import compiler.FunctionInfo;
import compiler.InstrIntf;
import compiler.instr.InstrCallFunction;
import compiler.instr.InstrPop;

public class ASTFunctionCallExprNode extends ASTExprNode {

    private final FunctionInfo _functionInfo;
    private final ASTArgumentListNode _arguments;

    public ASTFunctionCallExprNode(FunctionInfo functionInfo, ASTArgumentListNode args) {
        _functionInfo = functionInfo;
        _arguments = args;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent+"CALL " + _functionInfo.m_name + "\n");
        indent += "    ";
        _arguments.print(outStream, indent);
    }

    @Override
    public int eval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        InstrIntf argumentsInstr = _arguments.codegen(env);
        env.addInstr(argumentsInstr);  
        InstrIntf callInstr = new InstrCallFunction(_functionInfo);
        env.addInstr(callInstr);
        InstrIntf popInstr = new InstrPop();
        env.addInstr(popInstr);
        return popInstr;  
    }
    
}
