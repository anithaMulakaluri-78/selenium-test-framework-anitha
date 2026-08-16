package com.selenium.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;



public class StreamsTest {

	public static void main(String[] args) {
		
		//filter numbers greater than 20
		// TODO Auto-generated method stub
		List<Integer> numbers =Arrays.asList(10, 5, 20, 15, 30, 25, 40);
		numbers.stream().filter(num->num>20).forEach(System.out::println);
		
		List<String> names =
			    Arrays.asList("selenium", "java", "testng");
		names.stream().map(nam->nam.toUpperCase()).forEach(System.out::println);
		
		//duplicates
		List<Integer> numberss =
			    Arrays.asList(10, 20, 10, 30, 20, 40, 30);
		numberss.stream().distinct().forEach(System.out::println);
		
		//collect
		List<Integer> unique =
			    Arrays.asList(10, 20, 10, 30, 20, 40, 30);
		List<Integer> uniqueNumbers =unique.stream().distinct().collect(Collectors.toList());
		System.out.println(uniqueNumbers);
		
      //sorted
		List<Integer> numberst =
		        Arrays.asList(40, 10, 30, 20, 50);
		List<Integer> asc =numberst.stream().sorted().collect(Collectors.toList());
		System.out.println(asc);
		
		   //sorted
				List<Integer> numberstt =
				        Arrays.asList(40, 10, 30, 20, 50);
				List<Integer> desc =numberstt.stream().sorted(Comparator.reverseOrder())
						.collect(Collectors.toList());
				System.out.println(desc);
				
		//Occurs most of the times
		List<String> namess = Arrays.asList("John", "Anitha", "John","Rahul", "Anitha", "John");
		Map<Object, Long> occur=namess.stream().collect(Collectors.groupingBy(num -> num,Collectors.counting()));
		System.out.println(occur);
		Entry<Object, Long> max =
		        occur.entrySet()
		             .stream()
		             .max(Map.Entry.comparingByValue())
		             .get();

		System.out.println(max.getKey() + " → " + max.getValue());
	}
}
