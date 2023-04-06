public class TestDecimalMachine {
    
    public static void main(String[] args) throws Exception {
        compiler.StateMachine decimalMachine = new compiler.machines.DecimalMachine();
        java.io.OutputStreamWriter outWriter = new java.io.OutputStreamWriter(System.out);
        decimalMachine.process("0.0", outWriter); // accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("0", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("01.0", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("1.", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("1.5320", outWriter); // accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("0.5320", outWriter); // accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("01.5320", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("01.532", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("-1.5320", outWriter); // accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("-0.5320", outWriter); // accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("-01.5320", outWriter); //no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("-01.532", outWriter); // no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("-10012323.532", outWriter); // acept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("10012323.532e5", outWriter); // acept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("10012323.532e-5.5", outWriter);  //no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("10012323.532E-5.5", outWriter);  //no
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("10012323.532E-5", outWriter); //accept
        decimalMachine = new compiler.machines.DecimalMachine();
        decimalMachine.process("10012323.532E05", outWriter); // no
    }
}
