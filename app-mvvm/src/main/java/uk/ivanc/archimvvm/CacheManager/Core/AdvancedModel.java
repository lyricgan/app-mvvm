package uk.ivanc.archimvvm.CacheManager.Core;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 *
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 */
public class AdvancedModel<T> {
  public int type;
  public int state;
  public String stamp;
  public T data;

  public AdvancedModel() {

  }

  public AdvancedModel(int type, int state, String stamp, T data) {
    this.type = type;
    this.state = state;
    this.stamp = stamp;
    this.data = data;
  }
}