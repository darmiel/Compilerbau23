package compiler.machines;

public class StringliteralMachine extends compiler.StateMachine {
  @Override
  public void initStateTable() {
    compiler.State startState = new compiler.State("start", false);
    startState.addTransition('"', "in_string_b");
    addState(startState);

    compiler.State afterStateB = new compiler.State("in_string_b", false);
    afterStateB.addTransition('"', "in_string");
    addState(afterStateB);

    compiler.State afterState = new compiler.State("in_string",false);
    String asciiString = "";
    for (int i = 37; i <= 126; i++) {
        asciiString += (char)i;
    }
    //String asciiCharacters = "!#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    for(int i = 0; i < asciiString.length(); i++){
        char c = asciiString.charAt(i);
        afterState.addTransition(c, "in_string");

    }

    afterState.addTransition('"', "end");
    compiler.State endState = new compiler.State("end", true);
    addState(endState);
  }

  @Override
  public String getStartState() {
    return "start";
  }
}