package compiler.ast;

import java.io.OutputStreamWriter;

public class ASTBlockStmtNode extends ASTStmtNode {
    private ASTStmtListNode m_stmtList;

    public ASTBlockStmtNode(ASTStmtListNode stmtList) {
        m_stmtList = stmtList;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "ANONYMOUS BLOCK\n");
        indent += "    ";
        m_stmtList.print(outStream, indent);
    }

    @Override
    public void execute() {
        this.m_stmtList.execute();
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        // we are in entry block
        compiler.InstrBlock block = env.createBlock("anonymousBlock");
        compiler.InstrBlock blockExit = env.createBlock("anonymousBlockExit");
        compiler.InstrIntf jumpBlock = new compiler.instr.InstrJump(block);
        env.addInstr(jumpBlock);
        env.setCurrentBlock(block);
        m_stmtList.codegen(env);
        compiler.InstrIntf jumpBlockExit = new compiler.instr.InstrJump(blockExit);
        env.addInstr(jumpBlockExit);
        env.setCurrentBlock(blockExit);
        return null;
    }

    @Override
    public boolean semicolAfter()  { return false; }

}
