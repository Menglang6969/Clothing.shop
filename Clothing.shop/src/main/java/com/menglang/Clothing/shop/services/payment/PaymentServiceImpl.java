package com.menglang.Clothing.shop.services.payment;

import com.menglang.Clothing.shop.dto.payment.PaymentMapper;
import com.menglang.Clothing.shop.dto.payment.PaymentRequest;
import com.menglang.Clothing.shop.dto.payment.PaymentResponse;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.PaymentEntity;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final PaymentRepository paymentRepository;
    @Autowired
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse makePayment(PaymentRequest data) throws BadRequestException {
        OrderEntity order = orderRepository.findOrderByOrderNo(data.orderNo()).orElseThrow(() -> new NotFoundException("Order Not Found"));

        log.info("order no: {}", order.getOrderNo());

        double debt = 0;
        if (data.payKHR() != 0) {
            debt = (data.payKHR() / 4000);
        }
        debt += data.payUSD();

        PaymentEntity paymententity = PaymentEntity.builder()
                .order(order)
                .customer(data.customer())
                .payUSD(data.payUSD())
                .payKHR(data.payKHR())
                .description(data.description())
                .debtUSD(order.getTotalPrice() - debt)
                .build();

       try{
           return paymentMapper.toPaymentDTO(paymentRepository.save(paymententity));
       }catch (Exception e){
           throw new BadRequestException(e.getMessage());
       }
    }

    @Override
    public PaymentResponse deletePayment(PaymentRequest data) throws BadRequestException {
        return null;
    }

    @Override
    public PaymentResponse editPayment(Long id, PaymentRequest data) throws BadRequestException {
        PaymentEntity payment =  paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment Not Found"));
        Boolean isDateExpire=isExpired(payment.getCreatedAt());
        if(isDateExpire) throw new BadRequestException("Your Payment cannot Modify It's Over than 7 Days");

        OrderEntity order=orderRepository.findOrderByOrderNo(data.orderNo()).orElseThrow(()->new NotFoundException("Order Not Found"));

        double debt = 0;
        if (data.payKHR() != 0) {
            debt = (data.payKHR() / 4000);
        }
        debt += data.payUSD();

        payment.setCustomer(data.customer());
        payment.setDescription(data.description());
        payment.setOrder(order);
        payment.setPayUSD(data.payUSD());
        payment.setPayKHR(data.payKHR());
        payment.setDebtUSD(order.getTotalPrice()-debt);

        try{
            return paymentMapper.toPaymentDTO(paymentRepository.save(payment));
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }


    }

    private Boolean isExpired(Date start_date) {
        LocalDate createdAt = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(createdAt, currentDate);
        log.info(" date between: {}",daysBetween);
        return daysBetween<7;
    }
}
