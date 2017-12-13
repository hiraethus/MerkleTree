package com.clackjones.merkle;


public class HashNode implements Hashable {
    private Hashable leftChild;
    private Hashable rightChild;
    private String hashValue;
    private HashFunction hashFunction;

    {
        hashValue = null;
    }

    public HashNode(HashFunction hashFunction, Block block) {
        this.hashFunction = hashFunction;
        leftChild = block;
        rightChild = block;
    }

    public HashNode(HashFunction hashFunction, HashNode leftChild, HashNode rightChild) {
        this.hashFunction = hashFunction;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String createHash() {
        if (this.hashValue == null) {
            this.hashValue = hashFunction.hashOf(leftChild.createHash() + rightChild.createHash());
        }

        return this.hashValue;
    }
}
