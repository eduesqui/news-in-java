package com.eduardoes.news;

import com.eduardoes.records.Product;


import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

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

        int score = 6;
        //int score = 9;
        //score +1
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

    public void patterMatchingInstansOf(){
        //Object obj = 12;
        Object obj = "My text";


        if (obj instanceof String str) {
            System.out.println("Es una cadena: " + str);
        } else if (obj instanceof Integer num) {
            System.out.println("Es un entero: " + num);
        } else {
            System.out.println("Tipo desconocido");
        }

    }

    public void sealedExample(){
        TextMsg text = new TextMsg();
        text.sendMessage("hello Sealed");
    }

    public  void patternMatchingSwitch(SealedExample msg) {
        switch (msg) {
            case TextMsg text -> System.out.println(" Text Class");
            case VoiceMessage voice -> System.out.println("Voice Class");

            default -> throw new IllegalStateException("Unexpected value: " + msg);
        }
    }

    /*
    Xoroshiro group
         Xoroshiro128PlusPlus
    Xoshiro group
         Xoshiro256PlusPlus
    LXM group
        L128X1024MixRandom
        L128X128MixRandom
        L128X256MixRandom
        L32X64MixRandom
        L64X1024MixRandom
        L64X128MixRandom
        L64X128StarStarRandom
        L64X256MixRandom
     */
    public void randomGeneratorExample(){
        System.out.println("\n\nRandom Basic:");
        RandomGenerator randomGeneratorInt = RandomGenerator.of("Random");
        randomPrinter(randomGeneratorInt);


        RandomGenerator.SplittableGenerator splittableGenerator = RandomGeneratorFactory
                .<RandomGenerator.SplittableGenerator>of("L32X64MixRandom")
                .create();
        System.out.println("\n\nSplittable Generator:");

       randomPrinter(splittableGenerator);
       RandomGenerator.SplittableGenerator newGenerator1 = splittableGenerator.split();
       RandomGenerator.SplittableGenerator newGenerator2 = splittableGenerator.split();
        System.out.println("\n\nSplittable Generator split 1:");
        randomPrinter(newGenerator1);
        System.out.println("\n\nSplittable Generator split 2:");
        randomPrinter(newGenerator2);

        RandomGenerator.StreamableGenerator streamableGenerator =
                RandomGeneratorFactory.<RandomGenerator.StreamableGenerator>of("L32X64MixRandom")
                .create();

        IntStream intStream = streamableGenerator.ints(10, 0, 20);
        System.out.println("\n\nStream de enteros aleatorios:");
        intStream.forEach(System.out::println);
    }

    private  void randomPrinter(RandomGenerator generator) {
        System.out.println("Enteros Aleatorios:");
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.nextInt(10)); // Generar un entero aleatorio entre 0 y 99
        }

        System.out.println("Doubles Aleatorios:");
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.nextDouble(10.0)); // Generar un double aleatorio entre 0.0 y 1.0
        }
    }




    /*

    public void foreingMemoryAccessAPI() {


        try (ResourceScope scope = ResourceScope.newConfinedScope()) {
            int value = 10;
            // Crear un segmento de memoria con suficiente espacio para un entero (4 bytes) y una cadena (hasta 16 bytes)
           var  segment = MemorySegment.allocateNative(20, scope);
            var memoryAddress =segment.address();
            VarHandle varHandle = MemoryHandles.varHandle(long.class, ByteOrder.nativeOrder());
            varHandle.set(memoryAddress, value);
           System.out.println(varHandle.get(memoryAddress));
        }

    }

     */

    public void hiddenClassExample() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            // define the hidden class using the byte array of Util class
            // Using NESTMATE option so that hidden class has access to
            // private members of classes in same nest
            Class<?> hiddenClass = lookup.defineHiddenClass(getByteArray(),
                    true, MethodHandles.Lookup.ClassOption.NESTMATE).lookupClass();

            // get the hidden class object
            Object hiddenClassObj = hiddenClass.getConstructor().newInstance();

            // get the hidden class method
            Method method = hiddenClassObj.getClass().getDeclaredMethod("square", Integer.class);

            // call the method and get result
            Object result = method.invoke(hiddenClassObj, 3);

            // print the result
            System.out.println(result);

            // as hidden class is not visible to jvm, it will print hidden
            System.out.println(hiddenClass.isHidden());

            // canonical name is null thus this class cannot be instantiated using reflection
            System.out.println(hiddenClass.getCanonicalName());
        }catch(IOException | InvocationTargetException | IllegalAccessException |InstantiationException | NoSuchMethodException e) {
           System.out.println(e.getMessage());
        }

    }
    public static byte[] getByteArray() throws IOException {
        InputStream stream = Util.class.getClassLoader().getResourceAsStream("com/eduardoes/news/Util.class");
        return stream != null ? stream.readAllBytes() : new byte[0];
    }

}
