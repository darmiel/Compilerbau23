package compiler.ast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;
import compiler.instr.InstrArgumentList;

public class ASTArgumentListNode extends ASTStmtNode {

    private final List<ASTExprNode> _arguments;

    public ASTArgumentListNode() {
        _arguments = new ArrayList<>();
    }

    public void addArgument(ASTExprNode expr) {
        _arguments.add(expr);
    }

    public List<ASTExprNode> getArguments() {
        return _arguments;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "ARGS\n");
        indent += "    ";
        for(ASTExprNode expr : _arguments) {
            expr.print(outStream, indent);
        }        
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) throws Exception {
        InstrArgumentList args = new InstrArgumentList();
        for(int i = 0; i < _arguments.size(); ++i) {
            args.addArgument(_arguments.get(i).codegen(env));
        }
        return args;
    }
    
}
