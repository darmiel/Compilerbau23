public class CodegenMain {

    public static void main(String[] args) throws Exception {
        String program = compiler.InputReader.fileToString(args[0]);
        compiler.CompileEnv env = new compiler.CompileEnv(program, false);
        env.compile();
        env.dump(System.out);
        env.execute(System.out);
    }
}
