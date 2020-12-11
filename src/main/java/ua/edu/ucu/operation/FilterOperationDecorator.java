package ua.edu.ucu.operation;

import ua.edu.ucu.function.IntPredicate;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FilterOperationDecorator extends BaseOperationDecorator {
    private IntPredicate predicate;
    public FilterOperationDecorator(Operation operation, IntPredicate initIntPred) {
        super(operation);
        predicate = initIntPred;

    }
    public int[] perform(int[] numbers){
        int[] newNumbers = operation.perform(numbers);
        int[] numsCopy = new int[newNumbers.length];
        int newLength = 0;
        for (int number: numbers) {
            if (predicate.test(number)) {
                numsCopy[newLength] = number;
                newLength += 1;
            }
        }
        int[] finalNumbers = new int[newLength];
        System.arraycopy(numsCopy, 0,
                finalNumbers, 0, newLength);
        return finalNumbers;
    }
}
