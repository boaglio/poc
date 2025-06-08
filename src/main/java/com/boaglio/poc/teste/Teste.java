package com.boaglio.poc.teste;

public class Teste {

    public static void main(String[] args) {

        Teste1 t1 = new Teste1();
        System.out.println(t1.a);
        System.out.println(Teste1.a);
        System.out.println("---");
        t1.a++;

        Teste1 t2 = new Teste1();
        System.out.println(t2.a);
        System.out.println(Teste1.a);
        System.out.println("---");
        t2.a++;
        System.out.println(t2.a);
        System.out.println(Teste1.a);
        System.out.println("---");

    }
}
