package ua.edu.ucu.operation;

import ua.edu.ucu.function.IntToIntStreamFunction;

public class ToStreamOperationDecorator extends BaseOperationDecorator {
    private IntToIntStreamFunction toStreamOperation;
    public ToStreamOperationDecorator(
            Operation operation,
            IntToIntStreamFunction initToStreamOperation) {
        super(operation);
        toStreamOperation = initToStreamOperation;

    }
    public int[] perform(int[] numbers) {
        int[] newNumbers = operation.perform(numbers);
        int[][] newArrays = new int[newNumbers.length][];
        int newLength = 0;
        for (int i = 0; i < newNumbers.length; i++) {
            newArrays[i] = toStreamOperation
                    .applyAsIntStream(newNumbers[i]).toArray();
            newLength += newArrays[i].length;
        }

        int[] streamsConnected = new int[newLength];
        int i = 0;
        for (int[] arr: newArrays) {
            for (int num: arr) {
                streamsConnected[i] = num;
                i += 1;
            }
        }

        return streamsConnected;
    }
}
