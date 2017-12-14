/*
 * This file is part of MerkleTree by Mike Clack Jones
 * (http://mike.clackjones.com/)
 * 
 * MerkleTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MerkleTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MerkleTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.clackjones.merkle;

import java.util.*;
import java.util.stream.Collectors;

public class HashTree {
    private final HashFunction hashFunction;
    private final int totalNumberOfBlocks;

    private int currentNumberOfBlocks;
    private HashNode rootNode;
    private List<Block> treeBlocks;

    public HashTree(HashFunction hashFunction, int totalNumberOfBlocks) {
        this.hashFunction = hashFunction;
        this.totalNumberOfBlocks = totalNumberOfBlocks;
    }

    /**
     * Initialize an empty tree
     */
    public void init() {
        initWithValues(Collections.emptyList());
    }

    public void initWithValues(List<String> sequence) {
        if (totalNumberOfBlocks < sequence.size()) {
            throw new RuntimeException("Sequence too long for number of blocks");
        }

        currentNumberOfBlocks = sequence.size();

        Block emptyBlock = new Block(hashFunction, "");
        List<Block> blocks = new ArrayList<>(Collections.nCopies(totalNumberOfBlocks, emptyBlock));

        for (int i = 0; i < sequence.size(); ++i) {
            blocks.set(i, new Block(hashFunction, sequence.get(i)));
        }

        initImpl(blocks);
    }

    void initImpl(List<Block> values) {
        treeBlocks = values;
        List<HashNode> treeNodes = values.stream()
                .map(block -> new HashNode(hashFunction, block))
                .collect(Collectors.toList());

        Queue<HashNode> valueQueue = new ArrayDeque<>(treeNodes);
        while(valueQueue.size() > 1) {
            HashNode parentNode = new HashNode(hashFunction,
                    valueQueue.poll(), valueQueue.poll());

            valueQueue.offer(parentNode);
        }

        rootNode = valueQueue.poll();
    }

    public HashNode getRootNode() {
        return rootNode;
    }

    public int getNumberOfUsedBlocks() {
        return currentNumberOfBlocks;
    }

    public List<Block> getTreeBlocks() {
        return treeBlocks.subList(0, currentNumberOfBlocks);
    }

    public boolean isFull() {
        return currentNumberOfBlocks == totalNumberOfBlocks;
    }

    public void addBlock(Block block) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void main(String[] args) {
        final HashFunction sha1Hash = new Sha1HashFunction();

        List<String> values = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "ninety");

        HashTree tree = new HashTree(new Sha1HashFunction(), (int) Math.pow(2, 5));
        tree.initWithValues(values);

        System.out.printf("The Root Hash is %s\n", tree.getRootNode().createHash());
        System.out.printf("The tree is currently using %d blocks\n", tree.getNumberOfUsedBlocks());

        tree.getTreeBlocks().stream().forEach(block -> System.out.println("'" + block.getValue() + "'"));
    }
}
