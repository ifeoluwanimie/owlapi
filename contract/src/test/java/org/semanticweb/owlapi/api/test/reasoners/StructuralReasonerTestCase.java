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
package org.semanticweb.owlapi.api.test.reasoners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
class StructuralReasonerTestCase extends TestBase {

    private static void testClassHierarchy(@Nonnull StructuralReasoner reasoner) {
        NodeSet<OWLClass> subsOfA = reasoner.getSubClasses(A, true);
        assertEquals(1, subsOfA.getNodes().size());
        assertTrue(subsOfA.containsEntity(B));
        NodeSet<OWLClass> subsOfAp = reasoner.getSubClasses(D, true);
        assertEquals(1, subsOfAp.getNodes().size());
        assertTrue(subsOfAp.containsEntity(B));
        Node<OWLClass> topNode = reasoner.getTopClassNode();
        NodeSet<OWLClass> subsOfTop =
            reasoner.getSubClasses(topNode.getRepresentativeElement(), true);
        assertEquals(1, subsOfTop.getNodes().size());
        assertTrue(subsOfTop.containsEntity(A));
        NodeSet<OWLClass> descOfTop =
            reasoner.getSubClasses(topNode.getRepresentativeElement(), false);
        assertEquals(3, descOfTop.getNodes().size());
        assertTrue(descOfTop.containsEntity(A));
        assertTrue(descOfTop.containsEntity(B));
        assertTrue(descOfTop.containsEntity(OWLNothing()));
        NodeSet<OWLClass> supersOfTop = reasoner.getSuperClasses(OWLThing(), false);
        assertTrue(supersOfTop.isEmpty());
        NodeSet<OWLClass> supersOfA = reasoner.getSuperClasses(A, false);
        assertTrue(supersOfA.isTopSingleton());
        assertEquals(1, supersOfA.getNodes().size());
        assertTrue(supersOfA.containsEntity(OWLThing()));
        Node<OWLClass> equivsOfTop = reasoner.getEquivalentClasses(OWLThing());
        assertEquals(2, equivsOfTop.getEntities().size());
        assertTrue(equivsOfTop.getEntities().contains(C));
    }

    @Test
    void testClassHierarchy() {
        OWLOntology ont = create("ont");
        OWLOntologyManager man = ont.getOWLOntologyManager();
        man.addAxiom(ont, EquivalentClasses(OWLThing(), C));
        man.addAxiom(ont, SubClassOf(B, A));
        man.addAxiom(ont, EquivalentClasses(A, D));
        StructuralReasoner reasoner =
            new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
        testClassHierarchy(reasoner);
        man.addAxiom(ont, SubClassOf(A, OWLThing()));
        testClassHierarchy(reasoner);
        man.removeAxiom(ont, SubClassOf(A, OWLThing()));
        testClassHierarchy(reasoner);
    }
}
