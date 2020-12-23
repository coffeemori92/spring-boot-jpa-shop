package jpabook.jpashop.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  
  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderedItem> orderedItems = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate; // 주문시간

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // 주문상태 [ORDER, CANCEL]

  // 연관관계 메서드
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderedItem(OrderedItem orderedItem) {
    orderedItems.add(orderedItem);
    orderedItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  // 생성 메서드
  public static Order createOrder(Member member, Delivery delivery, OrderedItem... orderedItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for(OrderedItem orderedItem : orderedItems) {
      order.addOrderedItem(orderedItem);
    }
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  // 비즈니스 로직
  /**
   * 주문 취소
   */
  public void cancel() {
    if(delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
    }
    this.setStatus(OrderStatus.CANCEl);
    for(OrderedItem orderedItem : orderedItems) {
      orderedItem.cancel();
    }
  }
  
  // 조회 로직
  /**
   * 전체 주문 가격 조회
   */
  public int getTotalPrice() {
    int totalPrice = 0;
    for(OrderedItem orderedItem : orderedItems) {
      totalPrice += orderedItem.getTotalPrice();
    }
    return totalPrice;
  }

}
