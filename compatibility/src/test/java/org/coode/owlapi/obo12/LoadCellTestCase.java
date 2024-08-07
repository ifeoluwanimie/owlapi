package org.coode.owlapi.obo12;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.coode.owlapi.obo12.parser.OBO12DocumentFormat;
import org.coode.owlapi.obo12.parser.OBO12ParserFactory;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class LoadCellTestCase {

    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(19, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/celltype.obo"),
                OWLOntologyDocumentSourceBase.getNextDocumentIRI("obo"), new OBO12DocumentFormat(),
                null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    public void shouldParseOBO12() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(19, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"),
                OWLOntologyDocumentSourceBase.getNextDocumentIRI("obo"), new OBO12DocumentFormat(),
                null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    public void shouldParseGenericOBO() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(19, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"));
        m.loadOntologyFromOntologyDocument(source);
    }
}
