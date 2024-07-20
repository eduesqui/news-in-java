package com.eduardoes.news;

import com.eduardoes.records.Product;

public class Examples {



    public void textBlocks(){
        String text =
                """
                    This is  a "Example"
                    of text block
                    """;
        System.out.println("Text Blocks New:");
        System.out.println(text);
    }

    public void switchExpressions(){
        int score = 8;
        String myResult = switch (score) {
            case 1, 2, 3, 4, 5 -> "Failed Exam";
            case 6, 7, 8, 9, 10 -> {
               String result = "Passed exam";
               if(score>8){
                  result = result.concat(" Great job!");
               }
               yield result;
            }
            default -> throw new IllegalArgumentException("Invalid day");
        };

        System.out.println(myResult);

    }


    public void recordPojo(){
         record Person (String name, int age) {}
         var person = new Person("John Doe", 22);
         System.out.println("Record :" + person.toString());
         System.out.println("Attribute :" + person.age);

    }

    public void recordClone(){
        var product = new Product("GameBoy", 100.0);
        var productClone = product.applyDiscount(product,20);
        System.out.println("Record :" + productClone.toString());
    }
}
