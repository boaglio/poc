package com.boaglio.poc.order;

import net.jqwik.api.*;
import net.jqwik.api.stateful.ActionSequence;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

  private final Basket basket = new Basket();
 
  @Test
  public void testAddProductsTest() {
    basket.add(new Product("TV", valueOf(10)), 2);
    basket.add(new Product("Playstation", valueOf(100)), 1);
 
    assertThat(basket.getTotalValue())
        .isEqualByComparingTo(valueOf(10*2 + 100));
  }
 
  @Test
  public void addSameProductTwiceTest() {
    Product p = new Product("TV", valueOf(10));
    basket.add(p, 2);
    basket.add(p, 3);
 
    assertThat(basket.getTotalValue())
        .isEqualByComparingTo(valueOf(10*5));
  }
 
  @Test
  public void removeProductsTest() {

    basket.add(new Product("TV", valueOf(100)), 1);
 
    Product p = new Product("PlayStation", valueOf(10));

    basket.add(p, 2);
    basket.remove(p);
 
    assertThat(basket
            .getTotalValue())
        .isEqualByComparingTo(valueOf(100));
  }

  private Arbitrary<AddAction> addAction() {
    Arbitrary<Product> products = Arbitraries.oneOf(
            randomProducts
                    .stream()
                    .map(product -> Arbitraries.of(product))
                    .collect(Collectors.toList()));
    Arbitrary<Integer> qtys =
            Arbitraries.integers().between(1, 100);

    return Combinators
            .combine(products, qtys)
            .as((product, qty) -> new AddAction(product, qty));
  }

  static List<Product> randomProducts = new ArrayList<>() {{
    add(new Product("TV", new BigDecimal("100")));
    add(new Product("PlayStation", new BigDecimal("150.3")));
    add(new Product("Refrigerator", new BigDecimal("180.27")));
    add(new Product("Soda", new BigDecimal("2.69")));
  }};

  private Arbitrary<RemoveAction> removeAction() {
    return Arbitraries.of(new RemoveAction());
  }

  @Provide
  Arbitrary<ActionSequence<Basket>> addsAndRemoves() {
    return Arbitraries.sequences(Arbitraries.oneOf(
            addAction(),
            removeAction()));
  }

  @Property
  void sequenceOfAddsAndRemoves(
          @ForAll("addsAndRemoves")
          ActionSequence<Basket> actions) {
    actions.run(new Basket());
  }
}