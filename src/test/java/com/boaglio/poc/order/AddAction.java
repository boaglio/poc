package com.boaglio.poc.order;

import net.jqwik.api.stateful.Action;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddAction
  implements Action<Basket> {

  static long counter=0;
  private final Product product;
  private final int qty;
 
  public AddAction(Product product, int qty) {
    this.product = product;
    this.qty = qty;
  }

  @Override
  public Basket run(Basket basket) {
 
    BigDecimal currentValue = basket.getTotalValue();
 
    basket.add(product, qty);
 
    BigDecimal newProductValue = product.getPrice()
      .multiply(valueOf(qty));
    BigDecimal newValue = currentValue.add(newProductValue);
 
    assertThat(basket.getTotalValue())
      .isEqualByComparingTo(newValue);

    System.out.println(++counter +" - AddAction=>"+basket);

    return basket;
  }
}