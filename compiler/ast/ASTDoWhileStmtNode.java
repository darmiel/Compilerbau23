package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrJump;
import compiler.instr.InstrJumpCond;

public class ASTDoWhileStmtNode extends ASTStmtNode{

    private ASTExprNode exprNode;
	private ASTStmtNode blockstmt;

    public ASTDoWhileStmtNode(ASTExprNode exprNode, ASTStmtNode blockstmt) {
		this.exprNode = exprNode;
		this.blockstmt = blockstmt;
	}
    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "DO " + "{" + "\n");
        blockstmt.print(outStream, indent + indent);
        outStream.write(indent + "}" + " WHILE(");
        exprNode.print(outStream, indent);
        outStream.write(indent + ")");
    }

    @Override
    public void execute() {
        do {
			blockstmt.execute();
		} while (exprNode.eval() != 0);
    }
    
   @Override
	public InstrIntf codegen(CompileEnvIntf env){
		InstrBlock do_begin = env.createBlock("do_begin");
		InstrBlock exit = env.createBlock("while_exit");

		InstrIntf jumpToHead = new InstrJump(do_begin);
		env.addInstr(jumpToHead);

		env.setCurrentBlock(do_begin);
		this.blockstmt.codegen(env);

		this.exprNode.codegen(env);
		InstrIntf jumpToBody = new InstrJumpCond(exprNode.codegen(env), do_begin, exit);
		env.addInstr(jumpToBody);

		env.setCurrentBlock(exit);
        return null;
	}
}
