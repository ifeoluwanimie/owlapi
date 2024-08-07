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
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSComment;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class TestPlainLiteralTestCase extends TestBase {

    static final String URN_TEST = "urn:test#";
    static final String TEST = "test";
    private static final OWLLiteral OWL_LITERAL =
        df.getOWLLiteral(TEST, OWL2Datatype.RDF_PLAIN_LITERAL);
    static final OWLDataProperty TEST_DP = df.getOWLDataProperty(iri(URN_TEST, "p"));
    static final IRI IRI = IRI(URN_TEST, "ind");

    @Test
    void testPlainLiteral() {
        IRI iri = iri("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "PlainLiteral");
        assertTrue(iri.isPlainLiteral());
        assertNotNull(df.getRDFPlainLiteral());
        assertNotNull(OWL2Datatype.getDatatype(iri));
    }

    @Test
    void shouldParsePlainLiteral() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parsePlainLiteral, new RDFXMLDocumentFormat());
        assertEquals(o.getAnnotationAssertionAxioms(IRI).iterator().next(),
            AnnotationAssertion(RDFSComment(), IRI, Literal(TEST, OWL2Datatype.RDF_PLAIN_LITERAL)));
    }

    @Test
    void testPlainLiteralFromEvren() {
        OWLDatatype node = df.getRDFPlainLiteral();
        assertTrue(node.isBuiltIn());
        assertNotNull(node.getBuiltInDatatype());
    }

    @Test
    void testPlainLiteralSerialization() {
        OWLOntology o = createAnon();
        m.addAxiom(o, df.getOWLDataPropertyAssertionAxiom(TEST_DP, df.getOWLNamedIndividual(IRI),
            OWL_LITERAL));
        StringDocumentTarget out = saveOntology(o);
        String expectedStart = "<test:p";
        String expectedEnd = ">test</test:p>";
        assertTrue(out.toString().contains(expectedStart), out.toString());
        assertTrue(out.toString().contains(expectedEnd), out.toString());
    }

    @Test
    void testPlainLiteralSerializationComments() {
        OWLOntology o = createAnon();
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(df.getRDFSComment(),
            df.getOWLNamedIndividual(IRI).getIRI(), OWL_LITERAL));
        StringDocumentTarget out = saveOntology(o);
        String expectedStart = "<rdfs:comment";
        String expectedEnd = ">test</rdfs:comment>";
        assertTrue(out.toString().contains(expectedStart), out.toString());
        assertTrue(out.toString().contains(expectedEnd), out.toString());
    }

    @Test
    void testPlainLiteralSerializationComments2() {
        OWLOntology o = createAnon();
        OWLAnnotation a = df.getOWLAnnotation(df.getRDFSComment(), OWL_LITERAL);
        m.applyChange(new AddOntologyAnnotation(o, a));
        StringDocumentTarget out = saveOntology(o);
        String expectedStart = "<rdfs:comment";
        String expectedEnd = ">test</rdfs:comment>";
        assertTrue(out.toString().contains(expectedStart), out.toString());
        assertTrue(out.toString().contains(expectedEnd), out.toString());
    }
}
