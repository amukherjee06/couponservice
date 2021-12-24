package com.arnab.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arnab.springcloud.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Coupon findByCode(String couponCode);

}
