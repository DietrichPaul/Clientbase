package de.dietrichpaul.clientbase.feature;

import java.util.Set;
import java.util.TreeSet;

public class FriendList {
    private final Set<String> friends = new TreeSet<>();

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