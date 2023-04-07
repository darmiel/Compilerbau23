package compiler.machines;

public class ZeilenkommentarMachine extends compiler.StateMachine{
    @Override
    public void initStateTable() {
        compiler.State startState = new compiler.State("start", false);
        startState.addTransition('/',"oneSlash");
        addState(startState);

        compiler.State oneSlashState = new compiler.State("oneSlash", false);
        oneSlashState.addTransition('/', "twoSlash");
        addState(oneSlashState);

        compiler.State twoSlashState = new compiler.State("twoSlash", false);
        twoSlashState.addTransition('\\', "backSlash");
        twoSlashState.addTransition(' ', "twoSlash");
        twoSlashState.addTransitionRange('0','9', "twoSlash");
        twoSlashState.addTransitionRange('a','z', "twoSlash");
        twoSlashState.addTransitionRange('A','Z', "twoSlash");
        addState(twoSlashState);

        compiler.State backSlashState = new compiler.State("backSlash", false);
        backSlashState.addTransition('n', "end");
        backSlashState.addTransition(' ', "twoSlash");
        backSlashState.addTransitionRange('0','9', "twoSlash");
        backSlashState.addTransitionRange('a','m', "twoSlash");
        backSlashState.addTransitionRange('o','z', "twoSlash");
        backSlashState.addTransitionRange('A','Z', "twoSlash");
        addState(backSlashState);

        compiler.State endState = new compiler.State("end", true);
        addState(endState);

    }

    @Override
    public String getStartState() {
        return "start";
    }
}
