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
