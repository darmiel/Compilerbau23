import java.io.OutputStreamWriter;

public class FunctionCallMain {
    

    public static void main(String[] args) throws Exception {
        String program = compiler.InputReader.fileToString("StatementsFunction.txt");
        compiler.CompileEnv env = new compiler.CompileEnv(program, true);
        env.compile();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        System.out.println("=================Execute=================");
        env.execute(outStream);
        outStream.flush();
        System.out.println("=================DUMP=================");
        env.dump(System.out);
    }
}
