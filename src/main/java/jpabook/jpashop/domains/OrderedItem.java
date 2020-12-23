package jpabook.jpashop.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpabook.jpashop.domains.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedItem {

  @Id @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice; // 주문가격
  private int count; // 주문수량

  // 생성 메서드
  public static OrderedItem createOrderedItem(Item item, int orderPrice, int count) {
    OrderedItem orderedItem = new OrderedItem();
    orderedItem.setItem(item);
    orderedItem.setOrderPrice(orderPrice);
    orderedItem.setCount(count);

    item.removeStock(count);

    return orderedItem;
  }

  // 비즈니스 로직
	public void cancel() {
	  getItem().addStock(count);
	}

	// 조회 로직
  public int getTotalPrice() {
    return getOrderPrice() * getCount();
  }

}
