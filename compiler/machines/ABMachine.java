package compiler.machines;

public class ABMachine extends compiler.StateMachine {
    
    public void initStateTable() {
        compiler.State startState = new compiler.State("start");
        startState.addTransition('A', "start");
        startState.addTransition('B', "expectB");
        m_stateMap.put("start", startState);
        compiler.State expectBState = new compiler.State("expectB");
        expectBState.addTransition('B', "expectB");
        m_stateMap.put("expectB", expectBState);
    }

    @Override
    public String getStartState() {
        return "start";
    }

    @Override
    public boolean isFinalState() {       
        return m_state == "expectB";
    }
    
}
