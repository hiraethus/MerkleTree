package com.clackjones.merkle;

public class Block implements Hashable{
    private HashFunction hashFunction;
    private String value;
    private String hashValue;

    public Block(HashFunction hashFunction, String value) {
        this.hashFunction = hashFunction;
        this.value = value;
        this.hashValue = null;
    }

    public String getValue() {
        return value;
    }

    public String createHash() {
        if (hashValue == null) {
            hashValue = hashFunction.hashOf(value);
        }

        return hashValue;
    }
}
