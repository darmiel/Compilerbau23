package compiler.ast;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrJump;

import java.io.OutputStreamWriter;

public class ASTBreakStmtNode extends ASTStmtNode {

    public static final ASTBreakStmtNode STATEMENT_NODE = new ASTBreakStmtNode();

    public static class BreakException extends RuntimeException {

    }

    @Override
    public void print(final OutputStreamWriter out, final String indent) throws Exception {
        out.write(indent + "BREAK\n");
    }

    @Override
    public void execute() {
        throw new BreakException();
    }

    @Override
    public InstrIntf codegen(final CompileEnvIntf env) {
        final InstrBlock loopExit = env.peekLoopStack();
        env.addInstr(new InstrJump(loopExit));
        return null;
    }

}
