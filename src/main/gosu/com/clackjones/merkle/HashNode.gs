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

public class HashNode implements Hashable {
    var _leftChild : Hashable
    var _rightChild : Hashable
    var _hashValue : String
    var _hashFunction : HashFunction

    construct(hashFunction : HashFunction, bloc : Block) {
        _hashFunction = hashFunction
        _leftChild = bloc
        _rightChild = bloc
        _hashValue = null
    }

    construct(hashFunction : HashFunction, leftChild : HashNode, rightChild : HashNode) {
        _hashFunction = hashFunction
        _leftChild = leftChild
        _rightChild = rightChild
        _hashValue = null
    }

    override function createHash() : String {
        if (_hashValue == null) {
            _hashValue = _hashFunction.hashOf(_leftChild.createHash() + _rightChild.createHash())
        }

        return _hashValue
    }
}
