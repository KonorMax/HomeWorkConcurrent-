package com.konor.HomeWorkConcurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Summa extends RecursiveTask<Double> {
    final int thresholdValue = 3000;

    double[] data;
    int start;
    int end;

    public Summa(double[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        double sum = 0;

        if ((end - start) < thresholdValue) {
            for (int i = start; i < end; i++) sum += data[i];
        } else {
            int midlle = (start - end) / 2;

            Summa subTask1 = new Summa(data, start, midlle);
            Summa subTask2 = new Summa(data, midlle, end);

            subTask1.fork();
            subTask2.fork();

            sum = subTask1.join() + subTask2.join();
        }
        return sum;
    }
}

class RecursiveTaskEx {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        double[] nums = new double[700];
        for (int i = 0; i < nums.length; i++)
            nums[i] = ((i % 2) == 0) ? i : -i;

        Summa task = new Summa(nums, 0, nums.length);

        double sumOperation = forkJoinPool.invoke(task);

        System.out.println("Summa " + sumOperation);
    }
}


