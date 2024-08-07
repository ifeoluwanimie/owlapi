package org.obolibrary.macro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OntologyAxiomPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** macro expansion gci visitor */
public class MacroExpansionGCIVisitor {

    protected static final Logger LOG =
        LoggerFactory.getLogger(MacroExpansionGCIVisitor.class.getName());
    protected final OWLOntology inputOntology;
    private final OWLOntologyManager outputManager;
    private final OWLOntology outputOntology;
    protected final ManchesterSyntaxTool manchesterSyntaxTool;
    protected final boolean shouldAddExpansionMarker;
    protected boolean preserveAnnotationsWhenExpanding = false;

    /**
     * @param inputOntology input ontology
     * @param outputManager manager for output ontology
     * @param preserveAnnotationsWhenExpanding annotations should be preserved
     */
    public MacroExpansionGCIVisitor(OWLOntology inputOntology, OWLOntologyManager outputManager,
        boolean preserveAnnotationsWhenExpanding) {
        this(outputManager, inputOntology, false);
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }

    /**
     * @param inputOntology input ontology
     * @param outputManager output manager
     * @param shouldAddExpansionMarker expansion marker should be added
     */
    public MacroExpansionGCIVisitor(OWLOntologyManager outputManager, OWLOntology inputOntology,
        boolean shouldAddExpansionMarker) {
        this.inputOntology = inputOntology;
        this.shouldAddExpansionMarker = shouldAddExpansionMarker;
        manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
        this.outputManager = outputManager;
        try {
            outputOntology = outputManager.createOntology(inputOntology.getOntologyID());
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void output(OWLAxiom axiom) {
        if (axiom == null) {
            LOG.error("no axiom");
            return;
        }
        AddAxiom addAx = new AddAxiom(outputOntology, axiom);
        try {
            outputManager.applyChange(addAx);
        } catch (Exception e) {
            LOG.error("COULD NOT TRANSLATE AXIOM", e);
        }
    }

    /**
     * @return ontology for general concept inclusion
     */
    public OWLOntology createGCIOntology() {
        MacroExpansions expansions = new MacroExpansions();
        outputManager.addAxioms(outputOntology, expansions.getNewAxioms());
        outputManager.removeAxioms(outputOntology, expansions.getRmAxioms());
        return outputOntology;
    }

    private class MacroExpansions {

        protected Set<OWLAxiom> newAxioms = new HashSet<>();
        private final Set<OWLAxiom> rmAxioms = new HashSet<>();
        GCIVisitor visitor = new GCIVisitor(inputOntology);

        public MacroExpansions() {
            for (OWLSubClassOfAxiom axiom : inputOntology.getAxioms(AxiomType.SUBCLASS_OF)) {
                // XXX suspected unnecessary variables
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLEquivalentClassesAxiom axiom : inputOntology
                .getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLClassAssertionAxiom axiom : inputOntology
                .getAxioms(AxiomType.CLASS_ASSERTION)) {
                OWLAxiom newAxiom = visitor.visit(axiom);
                // System.out.println("not adding " + newAxiom);
            }
            for (OWLAnnotationAssertionAxiom axiom : inputOntology
                .getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (expand(axiom)) {
                    // System.out.println("not removing " + axiom);
                }
            }
        }

        public Set<OWLAxiom> getNewAxioms() {
            return newAxioms;
        }

        public Set<OWLAxiom> getRmAxioms() {
            return rmAxioms;
        }

        private boolean expand(OWLAnnotationAssertionAxiom ax) {
            OWLAnnotationProperty prop = ax.getProperty();
            boolean didExpansion = false;
            String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
            if (expandTo != null) {
                LOG.info("Template to Expand {}", expandTo);
                expandTo =
                    expandTo.replaceAll("\\?X", manchesterSyntaxTool.getId((IRI) ax.getSubject()));
                expandTo =
                    expandTo.replaceAll("\\?Y", manchesterSyntaxTool.getId((IRI) ax.getValue()));
                LOG.info("Expanding {}", expandTo);
                try {
                    Set<OntologyAxiomPair> setAxp =
                        manchesterSyntaxTool.parseManchesterExpressionFrames(expandTo);
                    for (OntologyAxiomPair axp : setAxp) {
                        OWLAxiom axiom = axp.getAxiom();
                        if (shouldPreserveAnnotationsWhenExpanding()) {
                            Set<OWLAnnotation> annotationsWithOptionalExpansionMarker =
                                visitor.getAnnotationsWithOptionalExpansionMarker(ax);
                            axiom = axiom.getAnnotatedAxiom(annotationsWithOptionalExpansionMarker);
                        }
                        newAxioms.add(axiom);
                        didExpansion = true;
                    }
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            return didExpansion;
        }

        private class GCIVisitor extends AbstractMacroExpansionVisitor {

            final Set<OWLAnnotation> expansionMarkingAnnotations;

            GCIVisitor(OWLOntology inputOntology) {
                super(inputOntology, shouldAddExpansionMarker);
                if (shouldAddExpansionMarker) {
                    expansionMarkingAnnotations = Collections.singleton(expansionMarkerAnnotation);
                } else {
                    expansionMarkingAnnotations = EMPTY_ANNOTATIONS;
                }
            }

            @Override
            protected OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
                OWLObjectPropertyExpression p) {
                OWLClassExpression gciRHS = expandObject(filler, p);
                if (gciRHS != null) {
                    OWLClassExpression gciLHS = dataFactory.getOWLObjectSomeValuesFrom(p, filler);
                    OWLEquivalentClassesAxiom ax = dataFactory.getOWLEquivalentClassesAxiom(gciLHS,
                        gciRHS, expansionMarkingAnnotations);
                    newAxioms.add(ax);
                }
                return gciRHS;
            }

            @Override
            protected OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc,
                OWLIndividual filler, OWLObjectPropertyExpression p) {
                OWLClassExpression gciRHS = expandObject(filler, p);
                if (gciRHS != null) {
                    OWLClassExpression gciLHS = dataFactory.getOWLObjectHasValue(p, filler);
                    OWLEquivalentClassesAxiom ax = dataFactory.getOWLEquivalentClassesAxiom(gciLHS,
                        gciRHS, expansionMarkingAnnotations);
                    newAxioms.add(ax);
                }
                return gciRHS;
            }

            private OWLClassExpression expandObject(Object filler, OWLObjectPropertyExpression p) {
                OWLClassExpression result = null;
                IRI iri = ((OWLObjectProperty) p).getIRI();
                IRI templateVal = null;
                if (expandExpressionMap.containsKey(iri)) {
                    if (filler instanceof OWLObjectOneOf) {
                        Set<OWLIndividual> inds = ((OWLObjectOneOf) filler).getIndividuals();
                        if (inds.size() == 1) {
                            OWLIndividual ind = inds.iterator().next();
                            if (ind instanceof OWLNamedIndividual) {
                                templateVal = ((OWLNamedObject) ind).getIRI();
                            }
                        }
                    }
                    if (filler instanceof OWLNamedObject) {
                        templateVal = ((OWLNamedObject) filler).getIRI();
                    }
                    if (templateVal != null) {
                        String tStr = expandExpressionMap.get(iri);
                        String exStr =
                            tStr.replaceAll("\\?Y", manchesterSyntaxTool.getId(templateVal));
                        try {
                            result = manchesterSyntaxTool.parseManchesterExpression(exStr);
                        } catch (OWLParserException e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }
                }
                return result;
            }
        }
    }

    /**
     * @return true if annotations should be preserved
     */
    public boolean shouldPreserveAnnotationsWhenExpanding() {
        return preserveAnnotationsWhenExpanding;
    }

    /**
     * @param preserveAnnotationsWhenExpanding annotations should be preserved
     */
    public void setPreserveAnnotationsWhenExpanding(boolean preserveAnnotationsWhenExpanding) {
        this.preserveAnnotationsWhenExpanding = preserveAnnotationsWhenExpanding;
    }

    /** Call this method to clear internal references. */
    public void dispose() {
        manchesterSyntaxTool.dispose();
    }
}
