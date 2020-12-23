package jpabook.jpashop.services;

import jpabook.jpashop.domains.Delivery;
import jpabook.jpashop.domains.Item.Item;
import jpabook.jpashop.domains.Member;
import jpabook.jpashop.domains.Order;
import jpabook.jpashop.domains.OrderedItem;
import jpabook.jpashop.repositories.ItemRepository;
import jpabook.jpashop.repositories.MemberRepository;
import jpabook.jpashop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	/**
	 * 주문
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		// 엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		// 배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		// 주문상품 생성
		OrderedItem orderedItem = OrderedItem.createOrderedItem(item, item.getPrice(), count);

		// 주문생성
		Order order = Order.createOrder(member, delivery, orderedItem);

		// 주문 저장
		orderRepository.save(order);

		return order.getId();
	}

	/**
	 * 취소
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		// 주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		// 주문 취소
		order.cancel();
	}

	/**
	 * 검색
	 */
//	public List<Order> findOrders(OrderSearch orderSearch) {
//
//	}
	
}
