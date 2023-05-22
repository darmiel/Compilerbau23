package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.CompileEnvIntf;
import compiler.CompilerException;
import compiler.FunctionInfo;
import compiler.InstrIntf;
import compiler.instr.InstrCallFunction;
import compiler.instr.InstrJump;

public class ASTFunctionCallExprNode extends ASTExprNode {

    private final String _functionName;
    private final ASTArgumentListNode _arguments;

    public ASTFunctionCallExprNode(String functionName, ASTArgumentListNode args) {
        _functionName = functionName;
        _arguments = args;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent+"CALL " + _functionName + "\n");
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
        FunctionInfo func = env.getFunctionTable().getFunction(_functionName);
        if(_arguments.getArguments().size() != func.varNames.size()) {
            throw new CompilerException("Invalid amount of arguments for function " + func.m_name + ". Given amount: " + _arguments.getArguments().size(), 0, _functionName, "Function " + func.m_name + "only accepts " + func.varNames.size() + " arguments.");
        }
        InstrIntf argumentsInstr = _arguments.codegen(env);
        env.addInstr(argumentsInstr);  
        InstrIntf callInstr = new InstrCallFunction(func);
        env.addInstr(callInstr);
        return callInstr;  
    }
    
}
