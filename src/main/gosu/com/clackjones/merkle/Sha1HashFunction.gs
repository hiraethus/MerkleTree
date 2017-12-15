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

uses java.security.MessageDigest
uses java.security.NoSuchAlgorithmException

class Sha1HashFunction implements HashFunction {

    /**
     * adapted from http://www.sha1-online.com/sha1-java/
     * (accessed: 2017-12-13)
     */
    override function hashOf(value : String) : String {
        var mDigest : MessageDigest = null
        try {
            mDigest = MessageDigest.getInstance("SHA1")
        } catch (e : NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        var result = mDigest.digest(value.Bytes)
        var sb = new StringBuffer()
        for (i in 0..|result.length) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1))
        }

        return sb.toString()
    }
}
