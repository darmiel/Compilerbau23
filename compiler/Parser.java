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
        // shiftExpr: bitAndOr ((<<|>>) bitAndOr)*
        ASTExprNode currentLhs = getBitAndOrExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTLEFT ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.SHIFTRIGHT) {
            Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode currentRhs = getBitAndOrExpr();
            ASTExprNode currentResult = new ASTShiftExprNode(currentLhs, currentRhs, currentToken);
            currentLhs = currentResult;
        }
        return currentLhs;
    }

    ASTExprNode getCompareExpr() throws Exception {
        // plusMinusExpr: mulDivExpr ((PLUS|MINUS) mulDivExpr)*
        ASTExprNode currentLhs = getShiftExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.LESS ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.GREATER ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.EQUAL
                ) {
            Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode currentRhs = getShiftExpr();
            ASTExprNode currentResult = new ASTCompareExprNode(currentLhs, currentRhs, currentToken);
            currentLhs = currentResult;
        }
        return currentLhs;
        return getShiftExpr();
    }

    ASTExprNode getAndOrExpr() throws Exception {
        ASTExprNode currentLhs = getCompareExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.AND || m_lexer.lookAhead().m_type == TokenIntf.Type.OR) {
            final Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            final ASTExprNode currentRhs = getCompareExpr();
            currentLhs = new ASTAndOrExprNode(currentLhs, currentRhs, currentToken);
        }
        return currentLhs;
    }

    ASTExprNode getQuestionMarkExpr() throws Exception {
        ASTExprNode andOrResult = getAndOrExpr();
        if (m_lexer.lookAhead().m_type == TokenIntf.Type.QUESTIONMARK) {
            m_lexer.expect(TokenIntf.Type.QUESTIONMARK);
            ASTExprNode value1 = getAndOrExpr();
            m_lexer.expect(TokenIntf.Type.DOUBLECOLON);
            ASTExprNode value2 = getAndOrExpr();
            return new ASTQuestionMarkNode(andOrResult, value1, value2);
        } else {
            return andOrResult;
        }
    }
}
