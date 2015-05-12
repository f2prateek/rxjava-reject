package com.f2prateek.rxjava.reject.sample;

import rx.Observable;

import static com.f2prateek.rxjava.reject.Reject.reject;

public class Sample {
  private Sample() {
    // No instances.
  }

  public static void main(String... args) throws Exception {
    Observable<Integer> sequence = Observable.range(1, 10);

    System.out.println("Reject Evens:");
    sequence.lift(reject(n -> n % 2 == 0)).subscribe(System.out::println);

    System.out.println("Reject Odds:");
    sequence.lift(reject(n -> n % 2 != 0)).subscribe(System.out::println);
  }
}
