package compiler.ast;

import compiler.instr.InstrJump;
import compiler.instr.InstrJumpCond;

import java.io.OutputStreamWriter;

public class ASTIfStmt extends ASTStmtNode {

    public final ASTExprNode condition;
    public final ASTStmtNode codeTrue;
    public final ASTStmtNode rightChild;

    public ASTIfStmt(ASTExprNode condition, ASTStmtNode codeTrue, ASTStmtNode rightChild) {
        this.condition = condition;
        this.codeTrue = codeTrue;
        this.rightChild = rightChild;
    }
    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        // Unsure if Indentation is correct
        outStream.write(indent + "IF\n");
        condition.print(outStream, indent);
        outStream.write("\n");
        codeTrue.print(outStream, indent);
        outStream.write("\n");
        rightChild.print(outStream, indent);
    }

    @Override
    public void execute() {
        if (condition.eval() != 0) {
            codeTrue.execute();
        } else if (rightChild != null) {
            rightChild.execute();
        }
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        compiler.InstrBlock conditionBlock = env.createBlock("conditionBlock");
        compiler.InstrBlock trueBlock = env.createBlock("codeTrueBlock");
        compiler.InstrBlock falseBlock = env.createBlock("codeFalseBlock");
        compiler.InstrBlock blockExit = env.createBlock("ifExit");

        compiler.InstrIntf jump = new compiler.instr.InstrJump(conditionBlock);
        env.addInstr(jump);

        env.setCurrentBlock(conditionBlock);
        compiler.InstrIntf conditionInstr = condition.codegen(env);

        env.setCurrentBlock(trueBlock);
        codeTrue.codegen(env);
        env.addInstr(new InstrJump(blockExit));


        if (rightChild == null) {
            env.setCurrentBlock(conditionBlock);
            compiler.InstrIntf jumpCondBlock = new InstrJumpCond(conditionInstr, trueBlock, blockExit);
            env.addInstr(jumpCondBlock);
        } else {
            env.setCurrentBlock(conditionBlock);
            compiler.InstrIntf jumpCondBlock = new InstrJumpCond(conditionInstr, trueBlock, falseBlock);
            env.addInstr(jumpCondBlock);

            env.setCurrentBlock(falseBlock);
            rightChild.codegen(env);
            env.addInstr(new InstrJump(blockExit));
        }

        return null;
    }

//    @Override
//    public boolean semicolAfter() {
//        return false;
//    }
}
