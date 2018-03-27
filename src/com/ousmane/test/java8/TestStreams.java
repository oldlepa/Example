package com.ousmane.test.java8;

import java.util.Arrays;
import java.util.List;

public class TestStreams {

	public static void main(String[] args) {
		
		List<String> list = Arrays.asList("Giraff","Cabrie","Mouton","chien","Chat","Vache");
		
		list.stream()
		.filter(x -> !x.equals("Chat"))
		.forEach(System.out::println);

	}

}
