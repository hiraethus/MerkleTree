package com.clackjones.merkle;

import java.util.*;
import java.util.stream.Collectors;

public class HashTree {
    private final HashFunction hashFunction;
    private final int numberOfBlocks;

    public HashTree(HashFunction hashFunction, int numberOfBlocks) {
        this.hashFunction = hashFunction;
        this.numberOfBlocks = numberOfBlocks;
    }

    public HashNode build(List<String> sequence) {
        if (numberOfBlocks < sequence.size()) {
            throw new RuntimeException("Sequence too long for number of blocks");
        }

        Block[] blocks = new Block[numberOfBlocks];
        Block emptyBlock = new Block(hashFunction, "");
        Arrays.fill(blocks, emptyBlock);

        for (int i = 0; i < sequence.size(); ++i) {
            blocks[i] = new Block(hashFunction, sequence.get(i));
        }

        return buildImpl(Arrays.asList(blocks));
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

        List<String> values = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "ninety");

        HashTree tree = new HashTree(new Sha1HashFunction(), (int)Math.pow(2,5));
        HashNode rootNode = tree.build(values);

        System.out.printf("The Root Hash is %s\n", rootNode.createHash());
    }
}
