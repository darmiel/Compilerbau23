package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;
import compiler.instr.InstrReturnStmt;

public class ASTReturnStmt extends ASTStmtNode {

    private final ASTExprNode _expression;

    public ASTReturnStmt(ASTExprNode expression) {
        this._expression = expression;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "RETURN\n");
        indent += "    ";
        _expression.print(outStream, indent);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) throws Exception {
        InstrIntf expr = _expression.codegen(env);
        InstrIntf returnStmt = new InstrReturnStmt(expr);
        env.addInstr(returnStmt);
        return returnStmt;
    }
    
}
