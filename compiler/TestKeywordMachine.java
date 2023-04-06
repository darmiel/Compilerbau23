package compiler;

public class TestKeywordMachine {

    public static final String KEYWORD = "Hallo! Ich bin der Luca! Hackl!";

    public static void main(String[] args) throws Exception {
        compiler.StateMachine keyWordMachine = new compiler.machines.KeywordMachine();
        java.io.OutputStreamWriter outWriter = new java.io.OutputStreamWriter(System.out);
        keyWordMachine.process(KEYWORD, outWriter);
    }
}
