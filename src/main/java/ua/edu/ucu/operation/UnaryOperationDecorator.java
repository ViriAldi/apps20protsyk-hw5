package ua.edu.ucu.operation;

import ua.edu.ucu.function.IntUnaryOperator;

public class UnaryOperationDecorator extends BaseOperationDecorator{
    IntUnaryOperator unaryOperator;
    public UnaryOperationDecorator(Operation operation, IntUnaryOperator initUnaryOperator) {
        super(operation);
        unaryOperator = initUnaryOperator;

    }
    public int[] perform(int[] numbers){
        int[] newNumbers = operation.perform(numbers);
        int[] numsCopy = new int[newNumbers.length];
        for (int i = 0; i < newNumbers.length; i++) {
            numsCopy[i] = unaryOperator.apply(newNumbers[i]);
        }
        return numsCopy;
    }
}
