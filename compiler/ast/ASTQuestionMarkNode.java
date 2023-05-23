package compiler.ast;

import java.io.OutputStreamWriter;

/**
 * questionMarkExpr -> value1 if andOrResult equals 1 (true) else value2
 */
public class ASTQuestionMarkNode extends ASTExprNode {
    public ASTExprNode cond, value1, value2;

    public ASTQuestionMarkNode(ASTExprNode cond, ASTExprNode value1, ASTExprNode value2) {
        this.cond = cond;
        this.value1 = value1;
        this.value2 = value2;
    }

//    public ConstInfo constFold() {
//        final ConstInfo cond = this.cond.constFold();
//        final ConstInfo cond = this.cond.constFold();
//        final ConstInfo cond = this.cond.constFold();
//    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent);
        outStream.write("?\n");
        cond.print(outStream, indent + "  ");
        value1.print(outStream, indent + "  ");
        value2.print(outStream, indent + "  ");
    }

    @Override
    public int eval() {
        return this.cond.eval() == 1 ? this.value1.eval() : this.value2.eval();
    }

    @Override
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        compiler.InstrIntf instrCond = this.cond.codegen(env);
        compiler.InstrIntf instrValue1 = this.value1.codegen(env);
        compiler.InstrIntf instrValue2 = this.value2.codegen(env);
        compiler.InstrIntf instr = new compiler.instr.InstrQuestionMarkExpr(instrCond, instrValue1, instrValue2);
        env.addInstr(instr);
        return instr;
    }

}