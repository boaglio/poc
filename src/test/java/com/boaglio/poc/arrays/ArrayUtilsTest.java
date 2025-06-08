package com.boaglio.poc.arrays;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class ArrayUtilsTest {

  @ParameterizedTest
  @MethodSource("testCases")
  void testIndexOf(int[] array, int valueToFind, int startIndex,
     int expectedResult) {
    int result = ArrayUtils.indexOf(array, valueToFind, startIndex);
    assertThat(result).isEqualTo(expectedResult);
  }
 
  static Stream<Arguments> testCases() {
    int[] array = new int[] { 1, 2, 3, 4, 5, 4, 6, 7 };
 
    return Stream.of(
      of(null, 1, 1, -1),
 
      of(new int[] { 1 }, 1, 0, 0),
      of(new int[] { 1 }, 2, 0, -1),
 
      of(array, 1, 10, -1),
      of(array, 2, -1, 1),
      of(array, 4, 6, -1),
      of(array, 4, 1, 3),
      of(array, 4, 3, 3),
      of(array, 4, 1, 3),
      of(array, 4, 4, 5),
      of(array, 8, 0, -1)
    );
  }

  @Test
  public void testIndexOfIntWithStartIndex() {

    int[] array = null;
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 2));

    array = new int[]{0, 1, 2, 3, 0};
    assertEquals(4, ArrayUtils.indexOf(array, 0, 2));

    assertEquals(-1, ArrayUtils.indexOf(array, 1, 2));

    assertEquals(2, ArrayUtils.indexOf(array, 2, 2));

    assertEquals(3, ArrayUtils.indexOf(array, 3, 2));

    assertEquals(3, ArrayUtils.indexOf(array, 3, -1));

    assertEquals(-1, ArrayUtils.indexOf(array, 99, 0));
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 6));
  }

  static int counter = 0;

  @Property
  void indexOf(
          @ForAll
          @Size(value = 100) List<@IntRange(min = -1000, max = 1000)Integer> numbers,
          @ForAll
          @IntRange(min = 1001, max = 2000) int value,
          @ForAll
          @IntRange(max = 99) int indexToAddElement,
          @ForAll
          @IntRange(max = 99) int startIndex) {

    System.out.println(++counter+ "- value="+value+" indexToAddElement="+indexToAddElement+" startIndex="+startIndex+" - numbers="+numbers);

    numbers.add(indexToAddElement, value);

    int[] array = convertListToArray(numbers);

    int expectedIndex = indexToAddElement >= startIndex ?
            indexToAddElement : -1;

    assertThat(ArrayUtils.indexOf(array, value, startIndex))
            .isEqualTo(expectedIndex);
  }

  private int[] convertListToArray(List<Integer> numbers) {
    int[] array = numbers.stream().mapToInt(x -> x).toArray();
    return array;
  }

}