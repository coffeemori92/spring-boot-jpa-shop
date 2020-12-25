package jpabook.jpashop.repositories;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderStatus;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {
	private Long orderId;
	private String name;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;

	public SimpleOrderQueryDto(
					Long orderId, String name, LocalDateTime orderDate,
					OrderStatus orderStatus, Address address
	) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
