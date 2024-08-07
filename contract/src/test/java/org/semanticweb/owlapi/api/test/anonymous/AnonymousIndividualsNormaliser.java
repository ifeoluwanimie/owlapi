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
package org.semanticweb.owlapi.api.test.anonymous;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class AnonymousIndividualsNormaliser extends OWLObjectDuplicator {

    private final Map<OWLAnonymousIndividual, OWLAnonymousIndividual> renamingMap = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory.
     * 
     * @param dataFactory The data factory to be used for the duplication.
     */
    public AnonymousIndividualsNormaliser(@Nonnull OWLDataFactory dataFactory) {
        super(dataFactory);
    }

    @Nonnull
    public Set<OWLAxiom> getNormalisedAxioms(@Nonnull Set<OWLAxiom> axioms) {
        List<OWLAxiom> axiomsList = new ArrayList<>(axioms);
        Collections.sort(axiomsList);
        Set<OWLAxiom> normalised = new HashSet<>();
        for (OWLAxiom ax : axiomsList) {
            assert ax != null;
            OWLAxiom dup = duplicateObject(ax);
            normalised.add(dup);
        }
        return normalised;
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        OWLAnonymousIndividual ind = renamingMap.get(individual);
        if (ind == null) {
            ind = AnonymousIndividual("anon-ind-" + counter.incrementAndGet());
            renamingMap.put(individual, ind);
        }
        setLastObject(ind);
    }
}
