package de.dietrichpaul.clientbase.features;

import java.util.Set;
import java.util.TreeSet;

public class FriendList {

    private Set<String> friends = new TreeSet<>();

    public void add(String name) {
        friends.add(name);
    }

    public void remove(String name) {
        friends.remove(name);
    }

    public Set<String> getFriends() {
        return friends;
    }
}
