package jpabook.jpashop.repositories;

import jpabook.jpashop.domains.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

	private String memberName;
	private OrderStatus orderStatus;

}
