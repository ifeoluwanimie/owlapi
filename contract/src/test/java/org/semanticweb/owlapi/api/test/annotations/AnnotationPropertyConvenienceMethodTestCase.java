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
package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Filters.subAnnotationWithSuper;
import static org.semanticweb.owlapi.search.Searcher.sub;
import static org.semanticweb.owlapi.search.Searcher.sup;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.Filters;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.2.0
 */
class AnnotationPropertyConvenienceMethodTestCase extends TestBase {

    @Test
    void testGetSuperProperties() {
        OWLOntology ont = create("OntA");
        ont.getOWLOntologyManager().addAxiom(ont,
            df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont,
            df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));
        Collection<OWLAxiom> axioms =
            ont.filterAxioms(Filters.subAnnotationWithSub, propP, INCLUDED);
        assertTrue(sup(axioms).contains(propQ));
        assertTrue(sup(axioms).contains(propR));
        axioms = ont.filterAxioms(Filters.subAnnotationWithSub, propP, EXCLUDED);
        assertTrue(sup(axioms).contains(propQ));
        assertTrue(sup(axioms).contains(propR));
    }

    @Test
    void testGetSubProperties() {
        OWLOntology ont = create("OntA");
        ont.getOWLOntologyManager().addAxiom(ont,
            df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont,
            df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));
        assertTrue(sub(ont.filterAxioms(subAnnotationWithSuper, propQ, INCLUDED)).contains(propP));
        assertTrue(sub(ont.filterAxioms(subAnnotationWithSuper, propQ, EXCLUDED)).contains(propP));
        assertTrue(sub(ont.filterAxioms(subAnnotationWithSuper, propR, INCLUDED)).contains(propP));
        assertTrue(sub(ont.filterAxioms(subAnnotationWithSuper, propR, EXCLUDED)).contains(propP));
    }
}
