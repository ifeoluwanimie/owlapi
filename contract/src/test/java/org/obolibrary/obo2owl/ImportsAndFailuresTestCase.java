package org.obolibrary.obo2owl;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class ImportsAndFailuresTestCase extends TestBase {

    private static final IRI TEST_IMPORT =
        iri("http://purl.obolibrary.org/obo/uberon/", "test_import.owl");
    private static final IRI UBERON = IRI.create("urn:test:uberon");

    protected StringDocumentSource inputSource(String input) {
        return new StringDocumentSource(input, UBERON, new OBODocumentFormat(), null);
    }

    static final String NO_INPUT =
        "ontology: uberon\n" + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";

    static final String UBERON_CORE = "format-version: 1.2\n" + "ontology: uberon/core\n\n"
        + "[Term]\nid: UBERON:0004138\n" + "name: somitomeric trunk muscle\n"
        + "property_value: seeAlso \"string\"\n" + "\n" + "[Typedef]\n" + "id: seeAlso\n"
        + "name: seeAlso\n" + "is_metadata_tag: true\n" + "is_class_level: true";

    static final String UBERON_PATO =
        "format-version: 1.2\n" + "import: http://purl.obolibrary.org/obo/uberon/pato_import.owl\n"
            + "ontology: uberon/core\n" + "\n" + "[Term]\n" + "id: UBERON:0004138\n"
            + "name: somitomeric trunk muscle\n" + "property_value: seeAlso \"string\"\n" + "\n"
            + "[Typedef]\n" + "id: seeAlso\n" + "name: seeAlso\n" + "is_metadata_tag: true\n"
            + "is_class_level: true";

    static final String EMPTY_IMPORT =
        "ontology: uberon\n" + "import: http://purl.obolibrary.org/obo/uberon/test_import.owl\n\n"
            + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";

    @Test
    void shouldNotFailOnEmptyImport() throws OWLOntologyCreationException {
        m1.createOntology(TEST_IMPORT);
        OWLOntology o = loadOntologyFromSource(inputSource(EMPTY_IMPORT), m1);
        saveOntology(o, new OBODocumentFormat());
        create(TEST_IMPORT);
        OWLOntology o1 = loadOntologyFromSource(inputSource(EMPTY_IMPORT), m);
        equal(o, o1);
    }

    @Test
    void shouldNotFailOnNoImport() {
        OWLOntology o = loadOntologyFromString(inputSource(NO_INPUT));
        roundTrip(o, new OBODocumentFormat());
    }

    @Test
    void shouldNotFailOnPatoImport() {
        loadOntology("pato_import.owl", m1);
        OWLOntology o = loadOntologyFromSource(inputSource(UBERON_PATO), m1);
        loadOntology("pato_import.owl", m);
        saveOntology(o, new OBODocumentFormat());
        OWLOntology o1 = loadOntologyFromSource(inputSource(UBERON_PATO), m);
        equal(o, o1);
    }

    @Test
    void shouldNotFailOnNoPatoImport() {
        OWLOntology o = loadOntologyFromString(inputSource(UBERON_CORE));
        roundTrip(o, new OBODocumentFormat());
    }
}
