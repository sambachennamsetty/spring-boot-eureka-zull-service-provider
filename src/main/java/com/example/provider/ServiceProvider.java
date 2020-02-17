package com.example.provider;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Cart;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/service-provider")
public class ServiceProvider {

	@Value("${server.port}")
	private String port;

	@GetMapping("/get")
	@HystrixCommand(fallbackMethod = "showFallBack")
	public String getData() {
		if (true) {
			throw new RuntimeException("Exception from service provider");
		}
		return "Hello";
	}

	@GetMapping("/data")
	public Cart getObj() {
		return new Cart(109, "ABC:" + port, 636.36);
	}

	@GetMapping("/list")
	public List<Cart> getBulk() {
		return Arrays.asList(new Cart(101, "A:" + port, 636.36), new Cart(102, "B:" + port, 526.46),
				new Cart(103, "C:" + port, 839.38));
	}

	public String showFallBack() {
		System.out.println("from fall back method");
		return "Server down. Please wait for some time:---->" + port;
	}

}
