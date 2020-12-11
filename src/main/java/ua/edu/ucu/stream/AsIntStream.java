package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.operation.*;


public class AsIntStream implements IntStream {
    private int[] stream;
    private int length;
    private Operation operations;


    private AsIntStream(Operation initOperations, int... values) {
        length = values.length;
        stream = new int[length];
        operations = initOperations;
        System.arraycopy(values, 0, stream, 0, length);
    }

    public static IntStream of(int... values) {
        return new AsIntStream(new EmptyOperation(), values);
    }

    private void performOperations() {
        stream = operations.perform(stream);
        length = stream.length;
        operations = new EmptyOperation();
    }


    public double average() {
        double sum = sum();
        return sum/length;

    }

    public int findMaxKeyFunction(IntUnaryOperator mapper) {
        performOperations();
        checkEmpty();
        int max = mapper.apply(stream[0]);
        for (int i = 1; i < length; i++) {
            if (mapper.apply(stream[i]) > max) {
                max = stream[i];
            }
        }
        return max;
    }

    public void checkEmpty() {
        if (length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public int max() {
        return findMaxKeyFunction(x -> x);
    }

    public int min() {
        return findMaxKeyFunction(x -> -x);
    }

    public long count() {
        performOperations();
        return length;
    }

    public int sum() {
        performOperations();
        checkEmpty();
        int sum = 0;
        for (int num: stream) {
            sum += num;
        }
        return sum;
    }

    public IntStream filter(IntPredicate predicate) {
        return new AsIntStream(
                new FilterOperationDecorator(
                        operations, predicate
                ),
                stream);

    }

    public void forEach(IntConsumer action) {
        performOperations();
        for (int i = 0; i < length; i++) {
            action.accept(stream[i]);
        }

    }

    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(
                new UnaryOperationDecorator(
                        operations, mapper
                ),
                stream);
    }


    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(
                new ToStreamOperationDecorator(
                        operations, func
                ),
                stream);
    }

    public int reduce(int identity, IntBinaryOperator op) {
        performOperations();
        int reducedNum = identity;
        for (int num: stream) {
            reducedNum = op.apply(reducedNum, num);
        }
        return reducedNum;

    }

    public int[] toArray() {
        performOperations();
        int[] values = new int[length];
        System.arraycopy(stream, 0, values, 0, length);
        return values;
    }
}
