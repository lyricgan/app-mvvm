package uk.ivanc.archimvvm.Utils;

import java.util.HashMap;
import java.util.Random;

import rx.Observable;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 *
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public class KeyGenerator {
  public static String get() {
    long time = System.currentTimeMillis();
    long random = new Random().nextLong();
    return String.valueOf(time) + String.valueOf(random);
  }
}
