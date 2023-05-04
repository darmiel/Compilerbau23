package compiler;
import compiler.ast.*;

public class Parser {
    private Lexer m_lexer;
    
    public Parser(Lexer lexer) {
        m_lexer = lexer;
    }
    
    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }
    
    ASTExprNode getParantheseExpr() throws Exception {
        // parantheseExpr: INT | ( LPAREN questionmarkExpr RPAREN )
        ASTExprNode node = null;
        if(m_lexer.lookAhead().m_type == TokenIntf.Type.INTEGER){
            node = new ASTIntegerLiteralNode(m_lexer.lookAhead().m_value);
            m_lexer.advance();
        }else if(m_lexer.lookAhead().m_type == TokenIntf.Type.LPAREN) {
            m_lexer.expect(TokenIntf.Type.LPAREN);
            ASTExprNode innerNode = getQuestionMarkExpr();
            node = new ASTParantheseExprNode(innerNode);
            m_lexer.expect(TokenIntf.Type.RPAREN);
        }else{
            m_lexer.throwCompilerException("Unexpected Token", "LPAREN or INTEGER");
        }
        return node;
    }
    
    ASTExprNode getUnaryExpr() throws Exception {
        return getParantheseExpr();
    }
    
    ASTExprNode getMulDivExpr() throws Exception {
        return getUnaryExpr();
    }
    
    ASTExprNode getPlusMinusExpr() throws Exception {
        // plusMinusExpr: mulDivExpr ((PLUS|MINUS) mulDivExpr)*
        ASTExprNode currentLhs = getMulDivExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.PLUS ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.MINUS) {
            Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode currentRhs = getMulDivExpr();
            ASTExprNode currentResult = new ASTPlusMinusExprNode(currentLhs, currentRhs, currentToken);
            currentLhs = currentResult;
        }
        return currentLhs;
    }

    ASTExprNode getBitAndOrExpr() throws Exception {
        return getPlusMinusExpr();
    }

    ASTExprNode getShiftExpr() throws Exception {
        return getBitAndOrExpr();
    }

    ASTExprNode getCompareExpr() throws Exception {
        return getShiftExpr();
    }

    ASTExprNode getAndOrExpr() throws Exception {
        return getCompareExpr();
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {
        return getAndOrExpr();
    }
}
