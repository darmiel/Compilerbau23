package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class ASTForStmtNode extends ASTStmtNode {

    private final ASTStmtNode iterator;
    private final ASTExprNode condition;
    private final ASTStmtNode iteratorOperation;
    private final ASTStmtNode body;

    public ASTForStmtNode(ASTStmtNode iterator, ASTExprNode condition, ASTStmtNode iteratorOperation, ASTStmtNode body) {
        this.iterator = iterator;
        this.condition = condition;
        this.iteratorOperation = iteratorOperation;
        this.body = body;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "FOR\n");
        iterator.print(outStream, indent);
        condition.print(outStream, indent);
        iteratorOperation.print(outStream, indent);
        outStream.write(indent + "{\n");
        body.print(outStream, indent);
        outStream.write(indent + "}\n");
    }

    @Override
    public void execute() {
        // for (iterator condition iteratorOperation) body
        for (iterator.execute(); condition.eval() > 0; iteratorOperation.execute()) {
            body.execute();
        }
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        return super.codegen(env);
    }
}
