package ua.edu.ucu.operation;

public class BaseOperationDecorator implements Operation {
    protected Operation operation;

    public BaseOperationDecorator(Operation initOperation){
        operation = initOperation;
    }

    public int[] perform(int[] numbers) {
        return operation.perform(numbers);
    }
}
