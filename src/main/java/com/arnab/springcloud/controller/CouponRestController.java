package com.arnab.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arnab.springcloud.model.Coupon;
import com.arnab.springcloud.repository.CouponRepository;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {
	
	@Autowired
	private CouponRepository couponRepo;
	
	@RequestMapping(value = "/coupons", method=RequestMethod.POST)
	public Coupon saveCoupon(@RequestBody Coupon coupon) {	
		return couponRepo.save(coupon);
	}
	@RequestMapping(value = "/coupons/{couponCode}")
	public Coupon getCoupon(@PathVariable("couponCode") String couponCode) {
		
		return couponRepo.findByCode(couponCode);
	}

}
