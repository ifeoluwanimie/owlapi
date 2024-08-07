/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.XMLUtils;

/**
 * Represents the Node ID for anonymous individuals
 * 
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public final class NodeID implements Comparable<NodeID>, Serializable {

    private static final long serialVersionUID = 40000L;
    private static final AtomicLong COUNTER = new AtomicLong(Integer.MAX_VALUE);
    private static final String NODE_ID_PREFIX = "genid";
    private static final String SHARED_NODE_ID_PREFIX = "genid-nodeid-";
    private static final String PREFIX = "_:";
    private static final String PREFIX_NODE = PREFIX + NODE_ID_PREFIX;
    private static final String PREFIX_SHARED_NODE = PREFIX + SHARED_NODE_ID_PREFIX;

    /**
     * @param id the node id
     * @return string version of id
     */
    @Nonnull
    public static String nodeString(int id) {
        return PREFIX_NODE + Integer.toString(id);
    }

    /**
     * @param id id
     * @return IRI with full node id
     */
    @Nonnull
    public static IRI nodeId(Integer id) {
        return IRI.create(PREFIX_NODE + id);
    }

    /**
     * @return IRI with fresh node id
     */
    @Nonnull
    public static IRI nextFreshNodeId() {
        return IRI.create(PREFIX_NODE + COUNTER.incrementAndGet());
    }

    /**
     * Returns an absolute IRI from a nodeID attribute.
     * 
     * @param nodeID the node id
     * @return absolute IRI
     */
    @Nonnull
    public static String getIRIFromNodeID(String nodeID) {
        if (nodeID.startsWith(PREFIX_SHARED_NODE)) {
            return nodeID;
        }
        return PREFIX_SHARED_NODE + nodeID.replace(NODE_ID_PREFIX, "");
    }

    /**
     * Generates next anonymous IRI.
     * 
     * @return absolute IRI
     */
    @Nonnull
    public static String nextAnonymousIRI() {
        return PREFIX_NODE + COUNTER.incrementAndGet();
    }

    /**
     * Tests whether supplied IRI was generated by this parser in order to label an anonymous node.
     * 
     * @param iri the IRI
     * @return {@code true} if the IRI was generated by this parser to label an anonymous node
     */
    public static boolean isAnonymousNodeIRI(String iri) {
        return iri != null && iri.startsWith(PREFIX) && iri.contains(NODE_ID_PREFIX);
    }

    /**
     * Tests whether supplied IRI was generated by this parser in order to label an anonymous node.
     * 
     * @param iri the IRI
     * @return {@code true} if the IRI was generated by this parser to label an anonymous node
     */
    public static boolean isAnonymousNodeIRI(IRI iri) {
        return iri != null && iri.getNamespace().startsWith(PREFIX)
            && iri.getNamespace().contains(NODE_ID_PREFIX);
    }

    /**
     * @param iri the iri or node id
     * @return true if the iri is an anonymous label
     */
    public static boolean isAnonymousNodeID(String iri) {
        return iri != null && iri.contains(PREFIX_SHARED_NODE);
    }

    /**
     * Gets a NodeID with a specific identifier string
     * 
     * @param id The String that identifies the node. If the String doesn't start with "_:" then
     *        this will be concatenated to the front of the specified id String; if the string is
     *        empty or null, an autogenerated id will be used.
     * @return A NodeID
     */
    @Nonnull
    public static NodeID getNodeID(String id) {
        String nonBlankId = id == null || id.isEmpty() ? nextAnonymousIRI() : id;
        return new NodeID(nonBlankId);
    }

    /**
     * @return node id with fresh id value
     */
    @Nonnull
    public static NodeID getNodeID() {
        return getNodeID(nextAnonymousIRI());
    }

    @Nonnull
    private final String id;

    private NodeID(String id) {
        if (id.startsWith(PREFIX)) {
            this.id = id;
        } else {
            this.id = PREFIX + id;
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return id;
    }

    @Override
    public int compareTo(NodeID o) {
        return id.compareTo(o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NodeID)) {
            return false;
        }
        NodeID other = (NodeID) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Gets the string representation of the node ID. This will begin with _:
     * 
     * @return The string representation of the node ID.
     */
    @Nonnull
    public String getID() {
        return id;
    }

    /**
     * @param resource nodeid IRI to strip of prefixes
     * @return id string compatible with RDF/XML
     */
    public static String stripArtifacts(CharSequence resource) {
        StringBuilder b = new StringBuilder(resource);
        int toDelete = b.indexOf(SHARED_NODE_ID_PREFIX);
        if (toDelete > -1) {
            b.delete(toDelete, toDelete + SHARED_NODE_ID_PREFIX.length());
        }
        toDelete = b.indexOf(NODE_ID_PREFIX);
        if (toDelete > -1) {
            b.delete(toDelete, toDelete + NODE_ID_PREFIX.length());
        }
        toDelete = b.indexOf(PREFIX);
        if (toDelete > -1) {
            b.delete(toDelete, toDelete + PREFIX.length());
        }
        for (int i = b.length() - 1; i > -1; i--) {
            if (!XMLUtils.isNCNameChar(b.charAt(i))) {
                b.deleteCharAt(i);
            }
        }
        if (!XMLUtils.isNCNameStartChar(b.charAt(0))) {
            // if the first character is not suitable as the start of an NCName, prefix it with the
            // mildest of the prefixes
            b.insert(0, NODE_ID_PREFIX);
        }
        return b.toString();
    }
}
