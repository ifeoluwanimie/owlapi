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
package org.semanticweb.owlapi.debugging;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLDebugger {

    /**
     * @return the {@code OWLOntology} that is being debugged.
     * @throws OWLException if there is any problem
     */
    @Nonnull
    OWLOntology getOWLOntology() throws OWLException;

    /**
     * Gets the sOS for inconsistent class.
     * 
     * @param cls The class which is inconsistent
     * @return the first set of supporting (SOS) axioms that are responsible for the specified class
     *         being inconsistent.
     * @throws OWLException if there is any problem
     */
    @Nonnull
    Set<OWLAxiom> getSOSForInconsistentClass(@Nonnull OWLClassExpression cls) throws OWLException;

    /**
     * Gets the all sos for inconsistent class.
     * 
     * @param cls the inconsistent class
     * @return all sets of supporting axioms that are responsible for the specified class being
     *         inconsistent
     * @throws OWLException if there is any problem
     */
    @Nonnull
    Set<Set<OWLAxiom>> getAllSOSForInconsistentClass(@Nonnull OWLClassExpression cls)
        throws OWLException;

    /** dispose of resources locked such as reasoners. */
    void dispose();
}
