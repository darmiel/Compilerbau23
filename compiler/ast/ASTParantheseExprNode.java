package compiler.ast;


import compiler.CompileEnvIntf;
import compiler.InstrIntf;
import compiler.TokenIntf;
import org.w3c.dom.xpath.XPathResult;

import java.io.OutputStreamWriter;

public class ASTParantheseExprNode extends ASTExprNode{

    ASTExprNode inner;

    public ASTParantheseExprNode(ASTExprNode inner){
        this.inner = inner;
    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + TokenIntf.Type.LPAREN + " " + TokenIntf.Type.RPAREN + "\n");
        indent = indent + "  ";
        inner.print(outStream, indent);
    }

    @Override
    public int eval() {
        return inner.eval();
    }

    @Override
    public InstrIntf codegen(CompileEnvIntf env) throws Exception {
        compiler.InstrIntf innerInstruction = inner.codegen(env);
        compiler.InstrIntf parantheseInstr = new compiler.instr.InstrParanthese(innerInstruction);
        env.addInstr(parantheseInstr);
        return parantheseInstr;
    }
}
