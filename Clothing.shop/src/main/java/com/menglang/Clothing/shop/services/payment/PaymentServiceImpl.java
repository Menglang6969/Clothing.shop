package com.menglang.Clothing.shop.services.payment;

import com.menglang.Clothing.shop.dto.payment.PaymentMapper;
import com.menglang.Clothing.shop.dto.payment.PaymentRequest;
import com.menglang.Clothing.shop.dto.payment.PaymentResponse;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.PaymentEntity;
import com.menglang.Clothing.shop.entity.enums.PaymentStatus;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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
    @Transactional
    public PaymentResponse makePayment(PaymentRequest data) throws BadRequestException {
        OrderEntity order = orderRepository.findOrderByOrderNo(data.orderNo()).orElseThrow(() -> new NotFoundException("Order Not Found"));

        //validate order is Success
        if(order.getStatus().equals(PaymentStatus.SUCCESS)) throw new BadRequestException(" This Order "+order.getOrderNo()+" Already Payment");

        PaymentEntity previousPayment=getPreviousPayment(order);
        double debt = 0;
        if (data.payKHR() != 0) {
            debt = (data.payKHR() / 4000);
        }
        debt += data.payUSD();
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrder(order);
        paymentEntity.setDescription(data.description());
        paymentEntity.setPayUSD(data.payUSD());
        paymentEntity.setPayKHR(data.payKHR());
        paymentEntity.setCustomer(data.customer());

        if (previousPayment!= null) {
            log.info("next time create payment.....{} debt:{} ",previousPayment.getDebt(),debt);
            paymentEntity.setDebtUSD(calculateDebt(previousPayment.getDebt()-debt));
            paymentEntity.setDebt(calculateDebt(previousPayment.getDebt()-debt));
            updateOrderStatus(previousPayment.getDebt()-debt,order,false);
            //reset previous debtUsd to 0 ez to query
            previousPayment.setDebt(0.0);
            try {
                paymentRepository.save(previousPayment);
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        } else {
            log.info("first create payment");
            paymentEntity.setDebtUSD(calculateDebt(order.getTotalPrice()-debt));
            paymentEntity.setDebt(calculateDebt(order.getTotalPrice() - debt));
        }
       try{
           return paymentMapper.toPaymentDTO(paymentRepository.save(paymentEntity));
       }catch (Exception e){
           throw new BadRequestException(e.getMessage());
       }
    }

    @Override
    public PaymentResponse deletePayment(Long id) throws BadRequestException {
        PaymentEntity payment=paymentRepository.findById(id).orElseThrow(()->new NotFoundException("Payment Not Found"));
        try{
            paymentRepository.delete(payment);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        return paymentMapper.toPaymentDTO(payment);
    }

    @Override
    public PaymentResponse editPayment(Long id, PaymentRequest data) throws BadRequestException {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment Not Found"));
        Boolean isDateExpire = isExpired(payment.getCreatedAt());
        if (isDateExpire) throw new BadRequestException("Your Payment cannot Modify It's Over than 7 Days");

        OrderEntity order = orderRepository.findOrderByOrderNo(data.orderNo()).orElseThrow(() -> new NotFoundException("Order Not Found"));

        //calculate paymentUSD + PaymentKHR
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
        payment.setDebtUSD(order.getTotalPrice() - debt);
        payment.setDebt(order.getTotalPrice()-debt);
        updateOrderStatus(order.getTotalPrice()-debt,order,true);
        try {
            return paymentMapper.toPaymentDTO(paymentRepository.save(payment));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }


    }

    private Boolean isExpired(Date start_date) {
        LocalDate createdAt = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(createdAt, currentDate);
        log.info(" date between: {}", daysBetween);
        return daysBetween < 7;
    }

    private PaymentEntity getPreviousPayment(OrderEntity order){
        try{
            List<PaymentEntity> paymentPrevious = paymentRepository.findPreviousPayment(order, PageRequest.of(0,1));
            return paymentPrevious.isEmpty() ? null : paymentPrevious.get(0);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private void updateOrderStatus(double debt,OrderEntity order,Boolean update){
        if (debt<=0){
            order.setStatus(PaymentStatus.SUCCESS);
            try{
                orderRepository.save(order);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }else{
            if(update){
                order.setStatus(PaymentStatus.DEBT);
                orderRepository.save(order);
            }
        }
    }
    private double calculateDebt(double debt){
        return debt>=0?debt:0;
    }
}
