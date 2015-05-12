package com.f2prateek.rxjava.reject;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;

public final class Reject {
  private Reject() {
    throw new AssertionError("no instances.");
  }

  /** Filters an Observable by rejecting any items it emits that do meet some test. */
  public static <T> Observable.Operator<T, T> reject(Func1<? super T, Boolean> predicate) {
    return new OperatorReject<>(predicate);
  }

  private static final class OperatorReject<T> implements Observable.Operator<T, T> {
    private final Func1<? super T, Boolean> predicate;

    OperatorReject(Func1<? super T, Boolean> predicate) {
      this.predicate = predicate;
    }

    @Override public Subscriber<? super T> call(final Subscriber<? super T> child) {
      return new Subscriber<T>(child) {

        @Override public void onCompleted() {
          child.onCompleted();
        }

        @Override public void onError(Throwable e) {
          child.onError(e);
        }

        @Override public void onNext(T t) {
          try {
            if (predicate.call(t)) {
              // TODO consider a more complicated version that batches these
              request(1);
            } else {
              child.onNext(t);
            }
          } catch (Throwable e) {
            child.onError(OnErrorThrowable.addValueAsLastCause(e, t));
          }
        }
      };
    }
  }
}
