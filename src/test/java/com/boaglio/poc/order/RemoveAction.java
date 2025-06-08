package com.boaglio.poc.order;

import net.jqwik.api.stateful.Action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RemoveAction implements Action<Basket> {

  static long counter=0;

  public Basket run(Basket basket) {
    BigDecimal currentValue = basket.getTotalValue();
    Set<Product> productsInBasket = basket.products();
 
    if(productsInBasket.isEmpty()) {
      return basket;
    }
 
    Product randomProduct = pickRandom(productsInBasket);
    double currentProductQty = basket.quantityOf(randomProduct);
    basket.remove(randomProduct);
 
    BigDecimal basketValueWithoutRandomProduct = currentValue
      .subtract(randomProduct.getPrice()
      .multiply(valueOf(currentProductQty)));
 
    assertThat(basket.getTotalValue())
      .isEqualByComparingTo(basketValueWithoutRandomProduct);

    System.out.println(++counter +" - RemoveAction=>"+basket);

    return basket;
  }

  private Product pickRandom(Set<Product> products) {
    // Convert Set to List for index-based access
    List<Product> productList = new ArrayList<>(products);

    // Generate a random index using ThreadLocalRandom for thread safety
    int randomIndex = ThreadLocalRandom.current().nextInt(productList.size());

    return productList.get(randomIndex);
  }
}