package com.clackjones.merkle;

public class Block implements Hashable{
    private HashFunction hashFunction;
    private int sequenceId;
    private String value;
    private String hashValue;

    public Block(HashFunction hashFunction, int sequenceId, String value) {
        this.hashFunction = hashFunction;
        this.sequenceId = sequenceId;
        this.value = value;
        this.hashValue = null;
    }

    public int getSequenceId() {
        return sequenceId;
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
