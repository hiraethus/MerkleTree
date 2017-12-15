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
 * but WITHOUT ANY WARRANTY without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MerkleTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.clackjones.merkle

uses java.util.*
uses java.util.stream.Collectors

class HashTree {
    final var _hashFunction : HashFunction
    final var _totalNumberOfBlocks : int

    var _currentNumberOfBlocks : int
    var _rootNode : HashNode
    var _treeBlocks : List<Block>

    construct(hashFunction : HashFunction, totalNumberOfBlocks : int) {
        _hashFunction = hashFunction
        _totalNumberOfBlocks = totalNumberOfBlocks
    }

    /**
     * Initialize an empty tree
     */
    function init() {
        initWithValues(Collections.emptyList())
    }

    function initWithValues(sequence : List<String>) {
        if (_totalNumberOfBlocks < sequence.size()) {
            throw new RuntimeException("Sequence too long for number of blocks")
        }

        _currentNumberOfBlocks = sequence.size()

        var emptyBlock = new Block(_hashFunction, "")
        var blocks = new ArrayList<Block>(Collections.nCopies(_totalNumberOfBlocks, emptyBlock))

        for (i in 0..|sequence.size()) {
            blocks.set(i, new Block(_hashFunction, sequence.get(i)))
        }

        initImpl(blocks)
    }

    function initImpl(values : List<Block>) {
        _treeBlocks = values
        var treeNodes = values.map(\ bloc -> new HashNode(_hashFunction, bloc))

        var valueQueue = new ArrayDeque<HashNode>(treeNodes)
        while(valueQueue.size() > 1) {
            var parentNode = new HashNode(_hashFunction,
                    valueQueue.poll(), valueQueue.poll())

            valueQueue.offer(parentNode)
        }

        _rootNode = valueQueue.poll()
    }

    function getRootNode() : HashNode {
        return _rootNode
    }

    function getNumberOfUsedBlocks() : int {
        return _currentNumberOfBlocks
    }

    function getTreeBlocks() : List<Block> {
        return _treeBlocks.subList(0, _currentNumberOfBlocks)
    }

    function isFull() : boolean {
        return _currentNumberOfBlocks == _totalNumberOfBlocks
    }

    function addBlock(bloc : Block) {
        throw new UnsupportedOperationException("Not yet implemented")
    }

    static function main(args : String[]) {
        var values = Arrays.asList({"one", "two", "three", "four", "five", "six", "seven", "eight", "ninety"})

        var tree = new HashTree(new Sha1HashFunction(), Math.pow(2, 5) as int)
        tree.initWithValues(values)

        System.out.printf("The Root Hash is %s\n", {tree.getRootNode().createHash()})
        System.out.printf("The tree is currently using %d blocks\n", {tree.getNumberOfUsedBlocks()})

        tree.getTreeBlocks().each(\ bloc -> System.out.println("'" + bloc.Value + "'"))
    }
}
