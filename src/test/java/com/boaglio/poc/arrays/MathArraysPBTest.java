package com.boaglio.poc.arrays;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;

import java.util.List;

import static java.util.Collections.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class MathArraysPBTest {

  static int counter = 0;

  @Property
  void unique(
   @ForAll
   @Size(value = 100)
   List<@IntRange(min = 1, max = 20) Integer>   numbers) {

     System.out.println(++counter+ " - numbers="+numbers);

    int[] doubles = convertListToArray(numbers);
    int[] result = MathArrays.unique(doubles);
 
    assertThat(result)
        .contains(doubles)
        .doesNotHaveDuplicates()
        .isSortedAccordingTo(reverseOrder());
  }
 
  private int[] convertListToArray(List<Integer> numbers) {

      return numbers
        .stream()
        .mapToInt(x -> x)
        .toArray();
  }
}