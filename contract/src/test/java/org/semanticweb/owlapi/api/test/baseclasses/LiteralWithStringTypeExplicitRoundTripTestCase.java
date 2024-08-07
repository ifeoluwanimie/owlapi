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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.3.0
 */
class LiteralWithStringTypeExplicitRoundTripTestCase extends TestBase {

    OWLAxiom ax = df.getOWLDataPropertyAssertionAxiom(DP, indA,
        df.getOWLLiteral("test url", OWL2Datatype.XSD_STRING.getDatatype(df)));

    @Override
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format) {
        format.setParameter("force xsd:string on literals", Boolean.TRUE);
        String string = saveOntology(ont, format).toString();
        assertTrue(string.contains("test url^^xsd:string")
            || string.contains("test url\"^^xsd:string")
            || string.contains("rdf:datatype=\"&xsd;string\">test url")
            || string.contains("rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">test url")
            || string.contains("datatypeIRI=\"http://www.w3.org/2001/XMLSchema#string\">test url")
            || string.contains("datatypeIRI=\"&xsd;string\">test url"),
            format.getKey() + "\n" + format.getClass().getName() + "\n" + string);
        return super.roundTripOntology(ont, format);
    }

    static Stream<String> dataWithTestUrl() {
        return Stream.of(TestFiles.STRING1, TestFiles.STRING2, TestFiles.STRING3, TestFiles.STRING4,
            TestFiles.STRING5, TestFiles.STRING6, TestFiles.STRING7, TestFiles.STRING8,
            TestFiles.STRING9);
    }

    @ParameterizedTest
    @MethodSource("dataWithTestUrl")
    void shouldParseInputWithoutExplicitString(String s) {
        assertTrue(loadOntologyFromString(s).containsAxiom(ax), s);
    }

    @ParameterizedTest
    @MethodSource("formatsNoRio")
    void testFormat(OWLDocumentFormat format) {
        roundTripOntology(o(ax), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSame() {
        OWLOntology o = o(ax);
        OWLOntology o1 = roundTripOntology(o, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTripOntology(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
        equal(o1, o2);
    }
}
