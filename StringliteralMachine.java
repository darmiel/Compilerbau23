public class StringliteralMachine {

  public static void main(String[] args) throws Exception {
    System.out.println("HelloWorld");
    compiler.StateMachine abMachine = new compiler.machines.StringliteralMachine();
    java.io.OutputStreamWriter outWriter = new java.io.OutputStreamWriter(System.out);
    abMachine.process("hello there", outWriter);
  }
}
