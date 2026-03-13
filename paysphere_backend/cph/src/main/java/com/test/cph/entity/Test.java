package com.test.cph.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Test {

    private int id;
    private String name;
    private String dept;
    private Double salary;

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(11,6,9,2,4,5,8,6,3,11);

        List<Integer> mostTopThreElemt;
        mostTopThreElemt = list.stream().sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Most top three element "+ mostTopThreElemt);

        Optional<Integer> firstNonRepeatedElm = list.stream().filter(i -> Collections.frequency(list, i) != 1)
                .findFirst();
        System.out.println("First non repeated element " + firstNonRepeatedElm);

        List<Test> testList = new ArrayList<>();
        testList.add(new Test(1, "abhi", "HR", 40000.0));
        testList.add(new Test(1, "abhi", "HR", 40000.0));
        testList.add(new Test(2, "rohit", "IT", 45000.0));
        testList.add(new Test(3, "neha", "Finance", 48000.0));
        testList.add(new Test(4, "amit", "HR", 42000.0));
        testList.add(new Test(5, "pooja", "IT", 50000.0));
        testList.add(new Test(6, "sneha", "Finance", 52000.0));
        testList.add(new Test(7, "rahul", "Admin", 39000.0));
        testList.add(new Test(8, "kiran", "HR", 46000.0));
        testList.add(new Test(9, "anita", "IT", 55000.0));
        testList.add(new Test(10, "vikas", "Sales", 47000.0));

        Map<String, List<Test>> map =
                testList.stream()
                        .sorted(
                                Comparator.comparing(Test::getDept)                // dept ASC
                                        .thenComparing(
                                                Comparator.comparing(Test::getSalary).reversed() // salary DESC
                                        )
                        )
                        .collect(
                                Collectors.groupingBy(
                                        Test::getDept,
                                        TreeMap::new,     // keeps department ascending
                                        Collectors.toList()
                                )
                        );
        map.forEach((dept, list1) -> {
            System.out.println(dept);
            list1.forEach(t ->
                    System.out.println("  " + t.getName() + " - " + t.getSalary())
            );
        });

    }
}
