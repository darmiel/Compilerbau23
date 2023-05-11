package compiler.ast;

public class ASTNode {
    public compiler.InstrIntf codegen(compiler.CompileEnvIntf env) {
        return new compiler.instr.InstrIntegerLiteral(0);
    }
}
