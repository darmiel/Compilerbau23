package compiler;
import compiler.TokenIntf.Type;
import compiler.ast.*;

public class Parser {
    private Lexer m_lexer;
    private SymbolTableIntf m_symbolTable;
    
    public Parser(Lexer lexer, SymbolTableIntf symbolTable) {
        m_lexer = lexer;
        m_symbolTable = symbolTable;
    }

    public Parser(Lexer lexer) {
        m_lexer = lexer;
        m_symbolTable = null;
    }
    
    public ASTExprNode parseExpression(String val) throws Exception {
        m_lexer.init(val);
        return getQuestionMarkExpr();
    }

    public ASTStmtNode parseStmt(String val) throws Exception {
        m_lexer.init(val);
        return getStmtList();
    }

    ASTExprNode getParantheseExpr() throws Exception {
        // parantheseExpr: INT | ( LPAREN questionmarkExpr RPAREN )
        ASTExprNode node = null;
        if(m_lexer.lookAhead().m_type == TokenIntf.Type.INTEGER){
            node = new ASTIntegerLiteralNode(m_lexer.lookAhead().m_value);
            m_lexer.advance();
        } else if (this.m_lexer.lookAhead().m_type == TokenIntf.Type.IDENT) {
            node = this.getVariableExpr();
            this.m_lexer.advance();
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
        // return getUnaryExpr();
        // MulDivExpr: UnaryExpr ((MUL|DIV) UnaryExpr)*
        ASTExprNode currentLhs = getUnaryExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.MUL ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.DIV) {
            Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode currentRhs = getUnaryExpr();
            ASTExprNode currentResult = new ASTMulDivExprNode(currentLhs, currentRhs, currentToken);
            currentLhs = currentResult;
        }
        return currentLhs;
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
        ASTExprNode currentLhs = getPlusMinusExpr();
        while (m_lexer.lookAhead().m_type == TokenIntf.Type.BITAND ||
                m_lexer.lookAhead().m_type == TokenIntf.Type.BITOR) {
            Token currentToken = m_lexer.lookAhead();
            m_lexer.advance();
            ASTExprNode currentRhs = getPlusMinusExpr();
            ASTExprNode currentResult = new ASTBitAndOr(currentLhs, currentRhs, currentToken);
            currentLhs = currentResult;
        }
        return currentLhs;
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

    ASTExprNode getVariableExpr() throws Exception {
        final Token token = this.m_lexer.lookAhead();
        final Symbol symbol = this.m_symbolTable.getSymbol(token.m_value);
        if (symbol == null) {
            this.m_lexer.throwCompilerException("variable not defined", "");
        }
        return new ASTVariableExprNode(symbol);
    }

    ASTStmtNode getAssignStmt() throws Exception {
        // assignStmt: IDENTIFIER ASSIGN expr
        // bsp: a = 5 + 2
        Token nextToken = m_lexer.lookAhead();
        m_lexer.expect(TokenIntf.Type.IDENT);

        Symbol symbol = m_symbolTable.getSymbol(nextToken.m_value);
        if (symbol == null) {
            this.m_lexer.throwCompilerException("variable not defined", "");
        }

        m_lexer.expect(TokenIntf.Type.ASSIGN);
        ASTExprNode expression = getQuestionMarkExpr();

        return new ASTAssignStmt(symbol, expression);
    }

    ASTStmtNode getVarDeclareStmt() throws Exception {
        m_lexer.expect(TokenIntf.Type.DECLARE);

        Token identifier = m_lexer.lookAhead();
        if (m_symbolTable.getSymbol(identifier.m_value) != null) {
            m_lexer.throwCompilerException("Identifier already declared previously", "");
        } else {
            m_symbolTable.createSymbol(identifier.m_value);
        }

        m_lexer.advance();
        return new ASTDeclareStmt(identifier);
    }

    ASTStmtNode getPrintStmt() throws Exception{
        m_lexer.expect(TokenIntf.Type.PRINT);
        m_lexer.advance();
        ASTExprNode exprNode = getQuestionMarkExpr();
        if(m_lexer.lookAhead().m_type == TokenIntf.Type.SEMICOLON){
            m_lexer.advance();
        }
        return new ASTPrintStmtNode(exprNode);
        
    }

    ASTStmtNode getStmt() throws Exception {
        // stmt: assignStmt // SELECT = {IDENTIFIER}
        if (m_lexer.lookAhead().m_type == TokenIntf.Type.IDENT) {
            return getAssignStmt();
        //    stmt: varDeclareStmt // SELECT = {DECLARE}
        } else if (m_lexer.lookAhead().m_type == TokenIntf.Type.DECLARE) {
            return getVarDeclareStmt();
        //    stmt: printStmt // SELECT = {PRINT}
        } else if (m_lexer.lookAhead().m_type == TokenIntf.Type.PRINT) {
            return getPrintStmt();
        } else {
            m_lexer.throwCompilerException("Unexpected Statement", "");
        }
        return null;
    }

    ASTStmtNode getStmtList() throws Exception {
        // stmtlist: stmt stmtlist // SELECT = {IDENTIFIER, DECLARE, PRINT}
        // stmtlist: eps // SELECT = {EOF}
        // stmtlist: (stmt)* // TERMINATE on EOF
        ASTBlockStmtNode stmtList = new ASTBlockStmtNode();
        while (m_lexer.lookAhead().m_type != TokenIntf.Type.EOF) {
            ASTStmtNode currentStmt = getStmt();
            stmtList.addStatement(currentStmt);
        }
        return stmtList;
    }
}
