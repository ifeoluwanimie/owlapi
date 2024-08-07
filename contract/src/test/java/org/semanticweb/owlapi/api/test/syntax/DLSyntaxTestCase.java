package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

class DLSyntaxTestCase extends TestBase {

    @Test
    void testCommasOnDisjointThree() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    void testCommasOnDisjointTwo() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    void testCommasOnDisjointFour() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }

    @Test
    void testCommasOnDisjointThreeOntologyHTML() {
        OWLOntology o = create(iri("urn:test:", "onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjointInHTML,
            render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    void testCommasOnDisjointTwoOntologyHTML() {
        OWLOntology o = create(iri("urn:test:", "onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjointTwoInHTML,
            render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    void testCommasOnDisjointFourOntologyHTML() {
        OWLOntology o = create(iri("urn:test:", "onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjoint4InHTML,
            render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    void testCommasOnDisjointThreeOntology() {
        OWLOntology o = create("onto");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    void testCommasOnDisjointTwoOntology() {
        OWLOntology o = create("onto");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    void testCommasOnDisjointFourOntology() {
        OWLOntology o = create("onto");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }
}
