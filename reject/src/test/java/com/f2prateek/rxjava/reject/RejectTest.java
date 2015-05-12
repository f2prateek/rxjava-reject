package com.f2prateek.rxjava.reject;

import org.junit.Test;
import org.mockito.InOrder;
import rx.Observable;
import rx.Observer;
import rx.observers.TestObserver;

import static com.f2prateek.rxjava.reject.Reject.reject;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class RejectTest {
  @Test public void rejectEvens() {
    final Observer<Integer> observer = mock(Observer.class);

    Observable.just(1, 2, 3, 4, 5) //
        .lift(reject(n -> n % 2 == 0)) //
        .subscribe(new TestObserver<>(observer));

    InOrder inOrder = inOrder(observer);
    inOrder.verify(observer, times(1)).onNext(1);
    inOrder.verify(observer, times(1)).onNext(3);
    inOrder.verify(observer, times(1)).onNext(5);
  }
}
