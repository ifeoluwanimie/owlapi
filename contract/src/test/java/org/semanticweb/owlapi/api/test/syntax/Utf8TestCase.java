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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParser;

class Utf8TestCase extends TestBase {

    @Test
    void testUTF8roundTrip() {
        saveOntology(loadOntologyFromString(TestFiles.roundtripUTF8String,
            new FunctionalSyntaxDocumentFormat()));
    }

    @Test
    void testInvalidUTF8roundTripOWLXML() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should fail parsing but is read
        // in as an owl/xml file
        ByteArrayInputStream in =
            new ByteArrayInputStream(TestFiles.INVALID_UTF8.getBytes(StandardCharsets.ISO_8859_1));
        OWLXMLParser parser = new OWLXMLParser();
        assertThrows(Exception.class,
            () -> parser.parse(new StreamDocumentSource(in), createAnon(), config));
        // expected to fail, but actual exception depends on the parsers in
        // the classpath
    }

    @Test
    void testInvalidUTF8roundTripWithInputStream() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        ByteArrayInputStream in =
            new ByteArrayInputStream(TestFiles.INVALID_UTF8.getBytes(StandardCharsets.ISO_8859_1));
        loadOntologyFrom(in);
    }

    @Test
    void testInvalidUTF8roundTripFromReader() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        assertThrows(OWLRuntimeException.class,
            () -> loadOntologyFromString(TestFiles.INVALID_UTF8, new RDFXMLDocumentFormat()));
    }

    @Test
    void testPositiveUTF8roundTrip() {
        String ns = "http://protege.org/UTF8.owl";
        OWLOntology ontology = create(iri(ns, ""));
        OWLClass a = Class(iri(ns + "#", "A"));
        m.addAxiom(ontology, df.getOWLDeclarationAxiom(a));
        OWLAnnotation ann = df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("Chinese=處方"));
        OWLAxiom axiom = df.getOWLAnnotationAssertionAxiom(a.getIRI(), ann);
        m.addAxiom(ontology, axiom);
        ontology = roundTrip(ontology, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    void testRoundTrip() {
        String ns = "http://protege.org/ontologies/UTF8RoundTrip.owl";
        OWLClass cl = Class(iri(ns + "#", "C"));
        /*
         * The two unicode characters entered here are valid and can be found in the code chart
         * http://www.unicode.org/charts/PDF/U4E00.pdf. It has been said that they are chinese and
         * they do look the part. In UTF-8 these characters are encoded as \u8655 --> \350\231\225
         * \u65b9 --> \346\226\271 where the right hand side is in octal. (I chose octal because
         * this is how emacs represents it with find-file-literally).
         */
        String chinese = "Rx\u8655\u65b9";
        System.setProperty("file.encoding", "UTF-8");
        OWLOntology ontology = createOriginalOntology(ns, cl, chinese);
        checkOntology(ontology, cl, chinese);
        OWLOntology newOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        checkOntology(newOntology, cl, chinese);
    }

    @Nonnull
    private OWLOntology createOriginalOntology(@Nonnull String ns, @Nonnull OWLClass cl,
        @Nonnull String chinese) {
        OWLOntology ontology = create(iri(ns, ""));
        OWLAxiom annotationAxiom = AnnotationAssertion(RDFSLabel(), cl.getIRI(), Literal(chinese));
        m.addAxiom(ontology, annotationAxiom);
        return ontology;
    }

    private static boolean checkOntology(@Nonnull OWLOntology ontology, @Nonnull OWLClass cl,
        @Nonnull String chinese) {
        return getAnnotationObjects(cl, ontology).stream().map(Utf8TestCase::literalValue)
            .anyMatch(chinese::equals);
    }

    protected static String literalValue(OWLAnnotation a) {
        return ((OWLLiteral) a.getValue()).getLiteral();
    }
}
