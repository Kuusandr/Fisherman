package com.kuusandr.santamaus;

public class Random {
    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    public static int rnd(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        int val;
        //Random r = new Random();
        do
        {
            //val =  r.nextInt((max - min) + 1) + min;
            val = min + (int)(Math.random() * ((max - min) + 1));
        }while((val<min) || (val>max));
        return val;
    }
}


