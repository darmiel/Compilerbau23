public class ExpressionEvaluatorMain {

    public static void main(String[] args) throws Exception {
        compiler.Lexer lexer = new compiler.Lexer();
        compiler.ExpressionEvaluator exprEvaluator = new compiler.ExpressionEvaluator(lexer);
        int result = exprEvaluator.eval("45 + 13 - 7 + (4 < 5 ? 2 : 3 +4)");
        System.out.println(result);
    }

}
