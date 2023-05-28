package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.exceptions.BreakException;
import compiler.instr.InstrJump;
import compiler.instr.InstrJumpCond;

import java.io.OutputStreamWriter;

public class ASTIfStmtNode extends ASTStmtNode {

    private final ASTExprNode expr;
    private final ASTBlockStmtNode block;

    public ASTIfStmtNode(final ASTExprNode expr, final ASTBlockStmtNode block) {
        this.expr = expr;
        this.block = block;
    }

    @Override
    public void print(final OutputStreamWriter out, final String indent) throws Exception {
        out.write(indent + "IF (\n");
        this.expr.print(out, "  " + indent);
        out.write(indent + ") THEN {\n");
        this.block.print(out, indent + "  ");
        out.write(indent + "}\n");
    }

    @Override
    public void execute() {
        if (this.expr.eval() != 0) {
            this.block.execute();
        }
    }

    @Override
    public InstrIntf codegen(final CompileEnvIntf env) {
        final InstrBlock ifHead = env.createBlock("if_head");
        final InstrBlock ifBody = env.createBlock("if_body");
        final InstrBlock ifExit = env.createBlock("if_exit");

        env.addInstr(new InstrJump(ifHead));

        env.setCurrentBlock(ifHead);
        this.expr.codegen(env);
        env.addInstr(new InstrJumpCond(this.expr.codegen(env), ifBody, ifExit));

        env.setCurrentBlock(ifBody);
        this.block.codegen(env);

        env.setCurrentBlock(ifExit);
        return null;
    }

    @Override
    public boolean semicolAfter() {
        return false;
    }
}
