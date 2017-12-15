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

class Block implements Hashable{
    var _hashFunction : HashFunction
    var _value : String as readonly Value
    var _hashValue : String

    construct(hashFunction : HashFunction, value : String) {
        _hashFunction = hashFunction
        _value        = value
        _hashValue    = null
    }

    override function createHash() : String {
        if (_hashValue == null) {
            _hashValue = _hashFunction.hashOf(_value)
        }

        return _hashValue
    }
}
