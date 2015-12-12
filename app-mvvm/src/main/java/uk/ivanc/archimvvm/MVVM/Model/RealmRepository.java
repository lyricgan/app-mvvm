package uk.ivanc.archimvvm.MVVM.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by marcel on 09/10/15.
 */
public class RealmRepository extends RealmObject {

  @PrimaryKey
  private long id;
  private String name;
  private String description;
  private int forks;
  private int watchers;
  private int stars;
  private String language;
  private String homepage;
  private RealmUser owner;
  private boolean fork;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getForks() { return forks; }

  public void setForks(int focks) { this.forks = focks; }

  public int getWatchers() { return watchers; }

  public void setWatchers(int watchers) { this.watchers = watchers; }

  public int getStars() { return stars; }

  public void setStars(int stars) { this.stars = stars; }

  public String getLanguage() { return language; }

  public void setLanguage(String language) { this.language = language; }

  public String getHomepage() { return homepage; }

  public void setHomepage(String homepage) { this.homepage = homepage; }

  public RealmUser getOwner() {
    return owner;
  }

  public void setOwner(RealmUser owner) { this.owner = owner; }

  public boolean getFork() { return fork; }

  public void setFork(boolean fock) { this.fork = fock; }
}
