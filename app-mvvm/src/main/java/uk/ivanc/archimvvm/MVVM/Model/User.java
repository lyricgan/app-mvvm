package uk.ivanc.archimvvm.MVVM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.ivanc.archimvvm.CacheManager.Core.BaseModel;

public class User extends BaseModel<RealmUser> {

  @SerializedName("login") @Expose public String login;
  @SerializedName("id") @Expose public int id;
  @SerializedName("avatar_url") @Expose public String avatarUrl;
  @SerializedName("gravatar_id") @Expose public String gravatarId;
  @SerializedName("url") @Expose public String url;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("followers_url") @Expose public String followersUrl;
  @SerializedName("following_url") @Expose public String followingUrl;
  @SerializedName("gists_url") @Expose public String gistsUrl;
  @SerializedName("starred_url") @Expose public String starredUrl;
  @SerializedName("subscriptions_url") @Expose public String subscriptionsUrl;
  @SerializedName("organizations_url") @Expose public String organizationsUrl;
  @SerializedName("repos_url") @Expose public String reposUrl;
  @SerializedName("events_url") @Expose public String eventsUrl;
  @SerializedName("received_events_url") @Expose public String receivedEventsUrl;
  @SerializedName("type") @Expose public String type;
  @SerializedName("site_admin") @Expose public boolean siteAdmin;
  @SerializedName("name") @Expose public String name;
  @SerializedName("company") @Expose public String company;
  @SerializedName("blog") @Expose public String blog;
  @SerializedName("location") @Expose public String location;
  @SerializedName("email") @Expose public String email;
  @SerializedName("public_repos") @Expose public int publicRepos;
  @SerializedName("public_gists") @Expose public int publicGists;
  @SerializedName("followers") @Expose public int followers;
  @SerializedName("following") @Expose public int following;
  @SerializedName("created_at") @Expose public String createdAt;
  @SerializedName("updated_at") @Expose public String updatedAt;

  public void setLogin(String login) { this.login = login; }
  public String getLogin() {
    return login;
  }

  public void setId(int id) { this.id = id; }
  public int getId() {
    return id;
  }

  public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setGravatarId(String gravatarId) { this.gravatarId = gravatarId; }
  public String getGravatarId() {
    return gravatarId;
  }

  public void setUrl(String url) { this.url = url; }
  public String getUrl() {
    return url;
  }

  public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setFollowersUrl(String followersUrl) { this.followersUrl = followersUrl; }
  public String getFollowersUrl() {
    return followersUrl;
  }

  public void setFollowingUrl(String followingUrl) { this.followingUrl = followingUrl; }
  public String getFollowingUrl() {
    return followingUrl;
  }

  public void setGistsUrl(String gistsUrl) { this.gistsUrl = gistsUrl; }
  public String getGistsUrl() {
    return gistsUrl;
  }

  public void setStarredUrl(String starredUrl) { this.starredUrl = starredUrl; }
  public String getStarredUrl() {
    return starredUrl;
  }

  public void setSubscriptionsUrl(String subscriptionsUrl) { this.subscriptionsUrl = subscriptionsUrl; }
  public String getSubscriptionsUrl() {
    return subscriptionsUrl;
  }

  public void setOrganizationsUrl(String organizationsUrl) { this.organizationsUrl = organizationsUrl; }
  public String getOrganizationsUrl() {
    return organizationsUrl;
  }

  public void setReposUrl(String reposUrl) { this.reposUrl = reposUrl; }
  public String getReposUrl() {
    return reposUrl;
  }

  public void setEventsUrl(String eventsUrl) { this.eventsUrl = eventsUrl; }
  public String getEventsUrl() {
    return eventsUrl;
  }

  public void setReceivedEventsUrl(String receivedEventsUrl) { this.receivedEventsUrl = receivedEventsUrl; }
  public String getReceivedEventsUrl() {
    return receivedEventsUrl;
  }

  public void setType(String type) { this.type = type; }
  public String getType() {
    return type;
  }

  public void setSiteAdmin(boolean siteAdmin) { this.siteAdmin = siteAdmin; }
  public boolean getSiteAdmin() {
    return siteAdmin;
  }

  public void setName(String name) { this.name = name; }
  public String getName() {
    return name;
  }

  public void setCompany(String company) { this.company = company; }
  public String getCompany() {
    return company;
  }

  public void setBlog(String blog) { this.blog = blog; }
  public String getBlog() {
    return blog;
  }

  public void setLocation(String location) { this.location = location; }
  public String getLocation() {
    return location;
  }

  public void setEmail(String email) { this.email = email; }
  public String getEmail() {
    return email;
  }

  public void setPublicRepos(int publicRepos) { this.publicRepos = publicRepos; }
  public int getPublicRepos() {
    return publicRepos;
  }

  public void setPublicGists(int publicGists) { this.publicGists = publicGists; }
  public int getPublicGists() {
    return publicGists;
  }

  public void setFollowers(int followers) { this.followers = followers; }
  public int getFollowers() {
    return followers;
  }

  public void setFollowing(int following) { this.following = following; }
  public int getFollowing() {
    return following;
  }

  public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
  public String getCreatedAt() {
    return createdAt;
  }

  public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
  public String getUpdatedAt() {
    return updatedAt;
  }

  public boolean hasEmail() { return email == null; }

  public boolean hasLocation() { return location == null; }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("GitUser{");
    sb.append("login='").append(login).append('\'');
    sb.append(", id=").append(id);
    sb.append(", avatarUrl='").append(avatarUrl).append('\'');
    sb.append(", gravatarId='").append(gravatarId).append('\'');
    sb.append(", url='").append(url).append('\'');
    sb.append(", htmlUrl='").append(htmlUrl).append('\'');
    sb.append(", followersUrl='").append(followersUrl).append('\'');
    sb.append(", followingUrl='").append(followingUrl).append('\'');
    sb.append(", gistsUrl='").append(gistsUrl).append('\'');
    sb.append(", starredUrl='").append(starredUrl).append('\'');
    sb.append(", subscriptionsUrl='").append(subscriptionsUrl).append('\'');
    sb.append(", organizationsUrl='").append(organizationsUrl).append('\'');
    sb.append(", reposUrl='").append(reposUrl).append('\'');
    sb.append(", eventsUrl='").append(eventsUrl).append('\'');
    sb.append(", receivedEventsUrl='").append(receivedEventsUrl).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", siteAdmin=").append(siteAdmin);
    sb.append(", name='").append(name).append('\'');
    sb.append(", company=").append(company);
    sb.append(", blog='").append(blog).append('\'');
    sb.append(", location='").append(location).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", publicRepos=").append(publicRepos);
    sb.append(", publicGists=").append(publicGists);
    sb.append(", followers=").append(followers);
    sb.append(", following=").append(following);
    sb.append(", createdAt='").append(createdAt).append('\'');
    sb.append(", updatedAt='").append(updatedAt).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override public RealmUser transformToRealm() {
    RealmUser realmGitUser = new RealmUser();
    realmGitUser.setLogin(login);
    realmGitUser.setAvatarUrl(avatarUrl);
    realmGitUser.setGravatarId(gravatarId);
    realmGitUser.setUrl(url);
    realmGitUser.setHtmlUrl(htmlUrl);
    realmGitUser.setFollowersUrl(followersUrl);
    realmGitUser.setFollowingUrl(followingUrl);
    realmGitUser.setGistsUrl(gistsUrl);
    realmGitUser.setStarredUrl(starredUrl);
    realmGitUser.setSubscriptionsUrl(subscriptionsUrl);
    realmGitUser.setOrganizationsUrl(organizationsUrl);
    realmGitUser.setReposUrl(reposUrl);
    realmGitUser.setEventsUrl(eventsUrl);
    realmGitUser.setReceivedEventsUrl(receivedEventsUrl);
    realmGitUser.setType(type);
    realmGitUser.setSiteAdmin(siteAdmin);
    realmGitUser.setName(name);
    realmGitUser.setCompany(company);
    realmGitUser.setBlog(blog);
    realmGitUser.setLocation(location);
    realmGitUser.setEmail(email);
    realmGitUser.setPublicRepos(publicRepos);
    realmGitUser.setPublicGists(publicGists);
    realmGitUser.setFollowers(followers);
    realmGitUser.setFollowing(following);
    realmGitUser.setCreatedAt(createdAt);
    realmGitUser.setUpdatedAt(updatedAt);
    return realmGitUser;
  }

  @Override public BaseModel transformFromRealm(RealmUser realm) {
    User gitUser = new User();
    gitUser.setLogin(realm.getLogin());
    gitUser.setAvatarUrl(realm.getAvatarUrl());
    gitUser.setGravatarId(realm.getGravatarId());
    gitUser.setUrl(realm.getUrl());
    gitUser.setHtmlUrl(realm.getHtmlUrl());
    gitUser.setFollowersUrl(realm.getFollowersUrl());
    gitUser.setFollowingUrl(realm.getFollowingUrl());
    gitUser.setGistsUrl(realm.getGistsUrl());
    gitUser.setStarredUrl(realm.getStarredUrl());
    gitUser.setSubscriptionsUrl(realm.getSubscriptionsUrl());
    gitUser.setOrganizationsUrl(realm.getOrganizationsUrl());
    gitUser.setReposUrl(realm.getReposUrl());
    gitUser.setEventsUrl(realm.getEventsUrl());
    gitUser.setReceivedEventsUrl(realm.getReceivedEventsUrl());
    gitUser.setType(realm.getType());
    gitUser.setSiteAdmin(realm.getSiteAdmin());
    gitUser.setName(realm.getName());
    gitUser.setCompany(realm.getCompany());
    gitUser.setBlog(realm.getBlog());
    gitUser.setLocation(realm.getLocation());
    gitUser.setEmail(realm.getEmail());
    gitUser.setPublicRepos(realm.getPublicRepos());
    gitUser.setPublicGists(realm.getPublicGists());
    gitUser.setFollowers(realm.getFollowers());
    gitUser.setFollowing(realm.getFollowing());
    gitUser.setCreatedAt(realm.getCreatedAt());
    gitUser.setUpdatedAt(realm.getUpdatedAt());
    return gitUser;
  }

  @Override public Class getRealmClass() {
    return RealmUser.class;
  }
}