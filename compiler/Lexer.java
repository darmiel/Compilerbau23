package compiler;

import compiler.machines.*;

import java.io.OutputStreamWriter;
import java.util.Vector;

public class Lexer implements LexerIntf {

    static class MachineInfo {

        public StateMachineIntf m_machine;
        public int m_acceptPos;

        public MachineInfo(StateMachineIntf machine) {
            m_machine = machine;
            m_acceptPos = 0;
        }

        public void init(String input) {
            m_acceptPos = 0;
            m_machine.init(input);
        }
    }

    protected Vector<MachineInfo> m_machineList;
    protected MultiLineInputReader m_input;
    protected Token m_currentToken;

    public Lexer() {
        m_machineList = new Vector<MachineInfo>();
        addLexerMachines();
    }

    private void addLexerMachines() {
        addMachine(new IntegerMachine());
        addMachine(new DecimalMachine());
        //addMachine(new StringLiteralMachine());
        addMachine(new CharacterliteralMachine());
        addKeywordMachine("*", compiler.TokenIntf.Type.MUL);
        addKeywordMachine("/", compiler.TokenIntf.Type.DIV);
        addKeywordMachine("+", compiler.TokenIntf.Type.PLUS);
        addKeywordMachine("-", compiler.TokenIntf.Type.MINUS);
        addKeywordMachine("&", compiler.TokenIntf.Type.BITAND);
        addKeywordMachine("|", compiler.TokenIntf.Type.BITOR);
        addKeywordMachine("<<", compiler.TokenIntf.Type.SHIFTLEFT);
        addKeywordMachine(">>", compiler.TokenIntf.Type.SHIFTRIGHT);
        addKeywordMachine("==", compiler.TokenIntf.Type.EQUAL);
        addKeywordMachine("<", compiler.TokenIntf.Type.LESS);
        addKeywordMachine(">", compiler.TokenIntf.Type.GREATER);
        addKeywordMachine("!", compiler.TokenIntf.Type.NOT);
        addKeywordMachine("&&", compiler.TokenIntf.Type.AND);
        addKeywordMachine("||", compiler.TokenIntf.Type.OR);
        addKeywordMachine("?", compiler.TokenIntf.Type.QUESTIONMARK);
        addKeywordMachine(":", compiler.TokenIntf.Type.DOUBLECOLON);
        addKeywordMachine("(", compiler.TokenIntf.Type.LPAREN);
        addKeywordMachine(")", compiler.TokenIntf.Type.RPAREN);
        addKeywordMachine("{", compiler.TokenIntf.Type.LBRACE);
        addKeywordMachine("}", compiler.TokenIntf.Type.RBRACE);
        addMachine(new ZeilenkommentarMachine());
        addMachine(new WhitespaceMachine());
        addKeywordMachine(";", compiler.TokenIntf.Type.SEMICOLON);
        addKeywordMachine(",", compiler.TokenIntf.Type.COMMA);
        addKeywordMachine("=", compiler.TokenIntf.Type.ASSIGN);

        addKeywordMachine("DECLARE", compiler.TokenIntf.Type.DECLARE);
        addKeywordMachine("PRINT", compiler.TokenIntf.Type.PRINT);
        addKeywordMachine("IF", compiler.TokenIntf.Type.IF);
        addKeywordMachine("ELSE", compiler.TokenIntf.Type.ELSE);
        addKeywordMachine("WHILE", compiler.TokenIntf.Type.WHILE);
        addKeywordMachine("DO", compiler.TokenIntf.Type.DO);
        addKeywordMachine("FOR", compiler.TokenIntf.Type.FOR);
        addKeywordMachine("LOOP", compiler.TokenIntf.Type.LOOP);
        addKeywordMachine("BREAK", compiler.TokenIntf.Type.BREAK);
        addKeywordMachine("SWITCH", compiler.TokenIntf.Type.SWITCH);
        addKeywordMachine("CASE", compiler.TokenIntf.Type.CASE);
        addKeywordMachine("EXECUTE", compiler.TokenIntf.Type.EXECUTE);
        addKeywordMachine("TIMES", compiler.TokenIntf.Type.TIMES);
        addKeywordMachine("FUNCTION", compiler.TokenIntf.Type.FUNCTION);
        addKeywordMachine("CALL", compiler.TokenIntf.Type.CALL);
        addKeywordMachine("RETURN", compiler.TokenIntf.Type.RETURN);
        addKeywordMachine("BLOCK", compiler.TokenIntf.Type.BLOCK);
        addKeywordMachine("DEFAULT", compiler.TokenIntf.Type.DEFAULT);

        // addMachine(new IdentifierMachine());
    }

    public void addMachine(StateMachineIntf machine) {
        m_machineList.add(new MachineInfo(machine));
    }

    public void addKeywordMachine(String keyword, TokenIntf.Type tokenType) {
        m_machineList.add(new MachineInfo(new KeywordMachine(keyword, tokenType)));
    }

    public void initMachines(String input) {
        for (MachineInfo machine : m_machineList) {
            machine.init(input);
        }
    }

    public Token nextWord() throws Exception {
        // check end of file
        if (m_input.isEmpty()) {
            Token token = new Token();
            token.m_type = Token.Type.EOF;
            token.m_value = new String();
            return token;
        }

        // initialize machines
        initMachines(m_input.getRemaining());

        // while some machine are in process
        int counter = 0;
        while (this.m_machineList.stream()
                .map(i -> i.m_machine)
                .anyMatch(m -> !m.isFinished())) {

            // increase counter
            counter++;

            // for all remaining machines
            for (final MachineInfo info : this.m_machineList) {
                final StateMachineIntf machine = info.m_machine;
                if (machine.isFinished()) {
                    continue;
                }
                // read next character
                machine.step();
                // if machine would accept
                // update last accept position
                if (machine.isFinalState()) {
                    info.m_acceptPos = counter;
                }
            }
        }
        // end while some machines are in process

        // select match for all machines
        // look for maximum accept position
        MachineInfo max = null;
        for (final MachineInfo info : m_machineList) {
            if (max == null || info.m_acceptPos > max.m_acceptPos) {
                max = info;
            }
        }
        // throw in case of error (best match has length 0)
        if (max == null || max.m_acceptPos == 0) {
            System.out.println("remaining: " + m_input.getRemaining() + ", acceptpos: " + max.m_acceptPos);
            throw new Exception("cannot find matching machine for input");
        }

        // set next word [start pos, final pos)
        final Token token = new Token();
        token.m_type = max.m_machine.getType();
        token.m_value = m_input.advanceAndGet(max.m_acceptPos);
        token.m_firstCol = m_currentToken != null ? m_currentToken.m_lastCol + 1 : 0;
        token.m_lastCol = token.m_firstCol + max.m_acceptPos;

        // update current token
        m_currentToken = token;

        return token;
    }

    public void processInput(String input, OutputStreamWriter outStream) throws Exception {
        m_input = new MultiLineInputReader(input);
        // while input available
        while (!m_input.isEmpty()) {
            // get next word
            Token curWord = nextWord();
            // break on failure
            if (curWord.m_type == Token.Type.EOF) {
                outStream.write("ERROR\n");
                outStream.flush();
                break;
            } else if (curWord.m_type == Token.Type.WHITESPACE) {
                continue;
            } else {
                // print word
                outStream.write(curWord.toString());
                outStream.write("\n");
                outStream.flush();
            }
        }
    }
}
