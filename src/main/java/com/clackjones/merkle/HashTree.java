package com.clackjones.merkle;

import java.util.*;
import java.util.stream.Collectors;

public class HashTree {
    private HashFunction hashFunction;

    public HashTree(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public HashNode build(List<String> sequence) {
        List<Block> blocks = new ArrayList<>(sequence.size());
        int count = 0;
        for (String value: sequence) {
            blocks.add(new Block(hashFunction, count++, value));
        }

        return buildImpl(blocks);
    }

    HashNode buildImpl(List<Block> values) {
        // duplicate so can build tree
        List<HashNode> treeNodes = values.stream()
                .map(block -> new HashNode(hashFunction, block))
                .collect(Collectors.toList());

        Queue<HashNode> valueQueue = new ArrayDeque<>(treeNodes);
        while(valueQueue.size() > 1) {
            HashNode parentNode = new HashNode(hashFunction,
                    valueQueue.poll(), valueQueue.poll());

            valueQueue.offer(parentNode);
        }

        return valueQueue.poll();
    }

    public static void main( String[] args ) {
        final HashFunction sha1Hash = new Sha1HashFunction();

        List<String> values = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eighty");

        HashTree tree = new HashTree(new Sha1HashFunction());
        HashNode rootNode = tree.build(values);

        System.out.printf("The Root Hash is %s\n", rootNode.createHash());
    }
}
