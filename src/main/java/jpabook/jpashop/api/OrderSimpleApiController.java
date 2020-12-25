package jpabook.jpashop.api;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderStatus;
import jpabook.jpashop.repositories.OrderRepository;
import jpabook.jpashop.repositories.OrderSearch;
import jpabook.jpashop.repositories.SimpleOrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;

	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
		return all;
	}

	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		// ORDER 2개
		// 1 + N -> 1 + 회원 N + 배송 N
		List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
		List<SimpleOrderDto> result = orders.stream().map(v -> new SimpleOrderDto(v))
						.collect(Collectors.toList());
		return result;
	}

	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		return orders.stream()
						.map(v -> new SimpleOrderDto(v))
						.collect(Collectors.toList());
	}

	@GetMapping("/api/v4/simple-orders")
	public List<SimpleOrderQueryDto> ordersV4() {
		return orderRepository.findOrderDtos();
	}

	@Data
	static class SimpleOrderDto {

		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
		}

	}

}
