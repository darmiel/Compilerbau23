package compiler.machines;

public class StringliteralMachine extends compiler.StateMachine {
  @Override
  public void initStateTable() {
    compiler.State startState = new compiler.State("start", false);
    startState.addTransition('"', "in_string");
    addState(startState);
    compiler.State afterState = new compiler.State("in_string", false);
    String asciiCharacters = "!#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    for(int i = 0; i < asciiCharacters.length(); i++){
        char c = asciiCharacters.charAt(i);
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