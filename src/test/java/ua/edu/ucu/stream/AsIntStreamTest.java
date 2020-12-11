package ua.edu.ucu.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AsIntStreamTest {
    private IntStream emptyStream;
    private IntStream nonEmptyStream;
    private IntStream filteredStream;
    private IntStream mappedStream;
    private IntStream flatMappedStream;

    @Before
    public void initStreams() {
        emptyStream = AsIntStream.of();
        nonEmptyStream = AsIntStream.of(1, 0, -1, 2, 3, 4, 10);
        filteredStream = nonEmptyStream.filter(x -> x < 4);
        mappedStream = nonEmptyStream.map(x -> x*x);
        flatMappedStream = nonEmptyStream.flatMap(
                x -> AsIntStream.of(x, 2*x)
        );
    }

    @Test
    public void testOfEmpty() {
        assertEquals(0, emptyStream.count());
    }

    @Test
    public void testOfNonEmpty() {
        assertEquals(7, nonEmptyStream.count());
    }

    @Test
    public void testToArrayEmpty() {
        assertArrayEquals(new int[0], emptyStream.toArray());
    }

    @Test
    public void testToArrayNonEmpty() {
        assertArrayEquals(
                new int[] {1, 0, -1, 2, 3, 4, 10},
                nonEmptyStream.toArray());
    }

    @Test
    public void testFilter() {
        assertArrayEquals(
                new int[] {1, 0, -1, 2, 3},
                filteredStream.toArray());
    }

    @Test
    public void testMap() {
        assertArrayEquals(
                new int[] {1, 0, 1, 4, 9, 16, 100},
                mappedStream.toArray());
    }

    @Test
    public void testFlatMap() {
        assertArrayEquals(
                new int[] {
                        1, 2,
                        0, 0,
                        -1, -2,
                        2, 4,
                        3, 6,
                        4, 8,
                        10, 20},
                flatMappedStream.toArray());
    }


    @Test
    public void testFilterEmpty() {
        assertArrayEquals(
                new int[] {},
                emptyStream.filter(x -> x == 2).toArray());
    }

    @Test
    public void testMapEmpty() {
        assertArrayEquals(
                new int[] {},
                emptyStream.map(x -> x + 1).toArray());
    }

    @Test
    public void testFlatMapEmpty() {
        assertArrayEquals(
                new int[] {},
                emptyStream.flatMap(
                        x -> AsIntStream.of(x, 2*x)
                ).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidSum() {
        emptyStream.sum();
    }

    @Test
    public void TestSum() {
        assertEquals(19, nonEmptyStream.sum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidMin() {
        emptyStream.min();
    }

    @Test
    public void TestMin() {
        assertEquals(-1, nonEmptyStream.min());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidMax() {
        emptyStream.max();
    }

    @Test
    public void TestMax() {
        assertEquals(10, nonEmptyStream.max());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidAverage() {
        emptyStream.average();
    }

    @Test
    public void TestAverage() {
        assertEquals(
                (double)19/7,
                nonEmptyStream.average(),
                0.0001
        );

    }

    @Test
    public void TestForEachNonEmpty() {
        ArrayList<Integer> array = new ArrayList<Integer>();
        nonEmptyStream.forEach(x -> array.add(x));
        assertArrayEquals(
                new Object[] {1, 0, -1, 2, 3, 4, 10},
                array.toArray()
        );
    }

    @Test
    public void TestForEachEmpty() {
        ArrayList<Integer> array = new ArrayList<Integer>();
        emptyStream.forEach(x -> array.add(x));
        assertArrayEquals(
                new Object[] {},
                array.toArray()
        );
    }

    @Test
    public void TestEmptyReduced() {
        assertEquals(
                999,
                emptyStream.reduce(999, (x, y) -> x*y)
        );
    }

    @Test
    public void TestNonEmptyReduced() {
        assertEquals(
                20,
                nonEmptyStream.reduce(1, (x, y) -> x + y)
        );
    }


    @Test
    public void TestDoubleOperation() {
        assertArrayEquals(
                new int[] {4, 3, 2, 5, 6},
                filteredStream.map(x -> x+3).toArray()
        );
    }


}
