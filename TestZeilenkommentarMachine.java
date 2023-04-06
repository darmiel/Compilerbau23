public class TestZeilenkommentarMachine {

    public static void main(String[] args) throws Exception {
        compiler.StateMachine zeilenkkommentarMachine = new compiler.machines.ZeilenkommentarMachine();
        java.io.OutputStreamWriter outWriter = new java.io.OutputStreamWriter(System.out);
        zeilenkkommentarMachine.process("// Test test test \n", outWriter);
    }
}
