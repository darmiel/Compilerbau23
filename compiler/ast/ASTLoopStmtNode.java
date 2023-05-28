package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.Parser;
import compiler.exceptions.BreakException;
import compiler.instr.InstrJump;

import java.io.OutputStreamWriter;

public class ASTLoopStmtNode extends ASTStmtNode {

    private final ASTBlockStmtNode block;

    public ASTLoopStmtNode(final ASTBlockStmtNode block) {
        this.block = block;
    }

    @Override
    public void print(final OutputStreamWriter out, final String indent) throws Exception {
        out.write(indent + "LOOP {\n");
        this.block.print(out, indent + "  ");
        out.write(indent + "}\n");
    }

    @Override
    public void execute() {
        int i = 0;
        while (true) {
            try {
                this.block.execute();
            } catch (final BreakException ignored) {
                System.out.println("Breaking! :)");
                break;
            }
        }
    }

    @Override
    public InstrIntf codegen(final CompileEnvIntf env) {
        final InstrBlock loopBody = env.createBlock("loop_body");
        final InstrBlock loopExit = env.createBlock("loop_exit");

        env.addInstr(new InstrJump(loopBody));
        env.setCurrentBlock(loopBody);
        this.block.codegen(env);

        env.addInstr(new InstrJump(loopExit));
        env.setCurrentBlock(loopExit);
        return null;
    }

    @Override
    public boolean semicolAfter() {
        return false;
    }
}
