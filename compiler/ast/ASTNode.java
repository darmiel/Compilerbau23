package compiler.ast;

public class ASTNode {
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) throws Exception {
        return new compiler.instr.InstrIntegerLiteral(0);
    }
}
