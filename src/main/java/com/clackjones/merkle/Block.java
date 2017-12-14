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
