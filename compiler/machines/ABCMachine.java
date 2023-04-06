package compiler.machines;

public class ABCMachine extends compiler.StateMachine {
  @Override
  public void initStateTable() {
    compiler.State startState = new compiler.State("start", false);
    startState.addTransition('A', "after_a");
    addState(startState);
    compiler.State after_state = new compiler.State("after_a", false);
    after_state.addTransition('B', "after_a");
    after_state.addTransition('C', "end");
    compiler.State endState = new compiler.State("end", true);
    addState(endState);
  }

  @Override
  public String getStartState() {
    return "start";
  }
}
