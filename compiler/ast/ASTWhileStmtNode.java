package compiler.ast;

import java.io.OutputStreamWriter;

import compiler.CompileEnvIntf;
import compiler.InstrBlock;
import compiler.InstrIntf;
import compiler.instr.InstrJump;
import compiler.instr.InstrJumpCond;

public class ASTWhileStmtNode extends ASTStmtNode{

    private ASTExprNode exprNode;
	private ASTStmtNode blockstmt;

	public ASTWhileStmtNode(ASTExprNode exprNode, ASTStmtNode blockstmt) {
		this.exprNode = exprNode;
		this.blockstmt = blockstmt;
	}

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "WHILE" + "(");
        exprNode.print(outStream, indent);
        outStream.write(  "){" + "\n");
        blockstmt.print(outStream, indent + indent);
        outStream.write(indent + "}");
    }

    @Override
    public void execute() {
        while (exprNode.eval() != 0) {
			blockstmt.execute();
		}
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) {
        InstrBlock while_head = env.createBlock("while_head");
		InstrBlock while_body = env.createBlock("while_body");
		InstrBlock exit = env.createBlock("while_exit");

		InstrIntf jumpToHead = new InstrJump(while_head);
		env.addInstr(jumpToHead);

		env.setCurrentBlock(while_head);
		this.exprNode.codegen(env);
		InstrIntf jumpToBody = new InstrJumpCond(exprNode.codegen(env), while_body, exit);
		env.addInstr(jumpToBody);

		env.setCurrentBlock(while_body);
		this.blockstmt.codegen(env);
		env.addInstr(jumpToHead);

		env.setCurrentBlock(exit);
        return null;
    }

    
}
