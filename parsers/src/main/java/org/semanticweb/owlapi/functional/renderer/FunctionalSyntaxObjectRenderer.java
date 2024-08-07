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
package org.semanticweb.owlapi.functional.renderer;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BODY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_DEFINITION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DECLARATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_UNION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DL_SAFE_RULE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HAS_KEY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HEAD;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IMPORT;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_CLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.VARIABLE;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatImpl;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * The Class OWLObjectRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor {

    @Nonnull
    private DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager();
    private PrefixManager prefixManager;
    protected final OWLOntology ont;
    private final Writer writer;
    private boolean writeEntitiesAsURIs = true;
    private OWLObject focusedObject;
    private boolean addMissingDeclarations = true;
    private boolean explicitXsdString = false;
    protected AnnotationValueShortFormProvider labelMaker = null;

    /**
     * @param ontology the ontology
     * @param writer the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nonnull OWLOntology ontology, Writer writer) {
        this(ontology, ontology.getOWLOntologyManager().getOntologyFormat(ontology), writer);
    }

    /**
     * @param ontology the ontology
     * @param ontologyFormat format
     * @param writer the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nonnull OWLOntology ontology,
        OWLDocumentFormat ontologyFormat, Writer writer) {
        ont = ontology;
        this.writer = writer;
        prefixManager = defaultPrefixManager;
        // reuse the setting on the existing format, if there is one
        if (ontologyFormat != null) {
            addMissingDeclarations = ontologyFormat.isAddMissingTypes();
            explicitXsdString = ((Boolean) ontologyFormat
                .getParameter("force xsd:string on literals", Boolean.FALSE)).booleanValue();
        }
        if (ontologyFormat instanceof PrefixDocumentFormat) {
            prefixManager.copyPrefixesFrom((PrefixDocumentFormat) ontologyFormat);
            prefixManager
                .setPrefixComparator(((PrefixDocumentFormat) ontologyFormat).getPrefixComparator());
        }
        if (!ontology.isAnonymous() && prefixManager.getDefaultPrefix() == null) {
            prefixManager.setDefaultPrefix(XMLUtils.iriWithTerminatingHash(
                ontology.getOntologyID().getOntologyIRI().get().toString()));
        }
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        OWLAnnotationProperty labelProp = df.getOWLAnnotationProperty(RDFS_LABEL.getIRI());
        labelMaker = new AnnotationValueShortFormProvider(Collections.singletonList(labelProp),
            prefLangMap, manager, defaultPrefixManager);
        focusedObject = ontology.getOWLOntologyManager().getOWLDataFactory().getOWLThing();
    }

    /**
     * Set the add missing declaration flag.
     * 
     * @param flag new value
     */
    public void setAddMissingDeclarations(boolean flag) {
        addMissingDeclarations = flag;
    }

    /**
     * @param prefixManager the new prefix manager
     */
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
        if (prefixManager instanceof DefaultPrefixManager) {
            defaultPrefixManager = (DefaultPrefixManager) prefixManager;
        }
    }

    /**
     * @param focusedObject the new focused object
     */
    protected void setFocusedObject(OWLObject focusedObject) {
        this.focusedObject = focusedObject;
    }

    protected void writePrefix(@Nonnull String prefix, @Nonnull String namespace) {
        write("Prefix");
        writeOpenBracket();
        write(prefix);
        write("=");
        write("<");
        write(namespace);
        write(">");
        writeCloseBracket();
        writeReturn();
    }

    protected void writePrefixes() {
        for (Map.Entry<String, String> e : prefixManager.getPrefixName2PrefixMap().entrySet()) {
            writePrefix(e.getKey(), e.getValue());
        }
    }

    private void write(@Nonnull OWLXMLVocabulary v) {
        write(v.getShortForm());
    }

    private void write(@Nonnull String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(@Nonnull IRI iri) {
        String qname = prefixManager.getPrefixIRIIgnoreQName(iri);
        if (qname != null) {
            boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
            if (!lastCharIsColon) {
                write(qname);
                return;
            }
        }
        writeFullIRI(iri);
    }

    private void writeFullIRI(@Nonnull IRI iri) {
        write("<");
        write(iri.toString());
        write(">");
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        if (!ontology.isAnonymous()) {
            writeFullIRI(ontology.getOntologyID().getOntologyIRI().get());
            if (ontology.getOntologyID().getVersionIRI().isPresent()) {
                writeReturn();
                writeFullIRI(ontology.getOntologyID().getVersionIRI().get());
            }
            writeReturn();
        }
        ontology.getImportsDeclarations().stream().sorted().forEach(decl -> {
            write(IMPORT);
            writeOpenBracket();
            writeFullIRI(decl.getIRI());
            writeCloseBracket();
            writeReturn();
        });
        for (OWLAnnotation ontologyAnnotation : getSortedAnnotations(ontology)) {
            ontologyAnnotation.accept(this);
            writeReturn();
        }
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Collection<IRI> illegals = OWLDocumentFormatImpl.determineIllegalPunnings(
            addMissingDeclarations, ontology.getSignature(), ont.getPunnedIRIs(INCLUDED));
        for (OWLEntity ent : sortOptionally(ontology.getSignature())) {
            writeDeclarations(ent, writtenAxioms, illegals);
        }
        // writeEntities(sortedSignature, writtenAxioms);
        writeSortedEntities("Annotation Properties", "Annotation Property",
            ontology.getAnnotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Object Properties", "Object Property",
            ontology.getObjectPropertiesInSignature(), writtenAxioms);
        writeSortedEntities("Data Properties", "Data Property",
            ontology.getDataPropertiesInSignature(), writtenAxioms);
        writeSortedEntities("Datatypes", "Datatype", ontology.getDatatypesInSignature(),
            writtenAxioms);
        writeSortedEntities("Classes", "Class", ontology.getClassesInSignature(), writtenAxioms);
        writeSortedEntities("Named Individuals", "Individual", ontology.getIndividualsInSignature(),
            writtenAxioms);
        Set<OWLAxiom> otherAxioms = ontology.getAxioms();
        otherAxioms.removeAll(writtenAxioms);
        for (OWLAxiom ax : sortOptionally(otherAxioms)) {
            ax.accept(this);
            writeReturn();
        }
        writeCloseBracket();
        flush();
    }

    private void writeSortedEntities(String bannerComment, String entityTypeName,
        Set<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        if (!entities.isEmpty()) {
            writeEntities(bannerComment, entityTypeName, sortOptionally(entities), writtenAxioms);
            writeln();
        }
    }

    private void writeln() {
        writeReturn();
    }

    private void writeln(@Nonnull String s) {
        write(s);
        writeReturn();
    }

    private void writeEntities(String comment, String entityTypeName,
        List<? extends OWLEntity> entities, @Nonnull Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            Set<? extends OWLAxiom> axiomsForEntity = getUnsortedAxiomsForEntity(owlEntity);
            for (Iterator<? extends OWLAxiom> iterator = axiomsForEntity.iterator(); iterator
                .hasNext();) {
                OWLAxiom axiom = iterator.next();
                if (writtenAxioms.contains(axiom)) {
                    iterator.remove();
                }
            }
            Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
                ont.getAnnotationAssertionAxioms(owlEntity.getIRI());
            for (Iterator<OWLAnnotationAssertionAxiom> iterator =
                annotationAssertionAxioms.iterator(); iterator.hasNext();) {
                OWLAnnotationAssertionAxiom axiom = iterator.next();
                if (writtenAxioms.contains(axiom)) {
                    iterator.remove();
                }
            }
            if (axiomsForEntity.isEmpty() && annotationAssertionAxioms.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                writeln("############################");
                writeln("#   " + comment);
                writeln("############################");
                writeln();
                haveWrittenBanner = true;
            }
            writeEntity2(owlEntity, entityTypeName, sortAxioms(axiomsForEntity),
                sortOptionally(annotationAssertionAxioms), writtenAxioms);
        }
    }

    @Nonnull
    private static List<OWLAnnotation> getSortedAnnotations(HasAnnotations annotationBearer) {
        return sortOptionally(annotationBearer.getAnnotations());
    }

    /**
     * Writes out the axioms that define the specified entity.
     * 
     * @param entity The entity
     * @return The set of axioms that was written out
     */
    @Nonnull
    protected Set<OWLAxiom> writeEntity(@Nonnull OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        writeEntity(entity, writtenAxioms);
        return writtenAxioms;
    }

    protected void writeEntity(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        writeEntity2(entity, "", sortAxioms(getUnsortedAxiomsForEntity(entity)),
            sortOptionally(ont.getAnnotationAssertionAxioms(entity.getIRI())),
            alreadyWrittenAxioms);
    }

    protected void writeEntity2(@Nonnull OWLEntity entity, String entityTypeName,
        @Nonnull List<? extends OWLAxiom> axiomsForEntity,
        @Nonnull List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        writeln("# " + entityTypeName + ": " + getIRIString(entity) + " (" + getEntityLabel(entity)
            + ")");
        writeln();
        setFocusedObject(entity);
        writeAnnotations2(entity, alreadyWrittenAxioms, annotationAssertionAxioms);
        List<? extends OWLAxiom> axs = axiomsForEntity;
        for (OWLAxiom ax : axs) {
            if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).getClassExpressions().size() > 2) {
                continue;
            }
            ax.accept(this);
            alreadyWrittenAxioms.add(ax);
            writeReturn();
        }
        writeln();
    }

    @Nonnull
    private Set<? extends OWLAxiom> getUnsortedAxiomsForEntity(@Nonnull OWLEntity entity) {
        return entity.accept(new OWLEntityVisitorEx<Set<? extends OWLAxiom>>() {

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLClass cls) {
                return ont.getAxioms(cls, EXCLUDED);
            }

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLObjectProperty property) {
                return ont.getAxioms(property, EXCLUDED);
            }

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLDataProperty property) {
                return ont.getAxioms(property, EXCLUDED);
            }

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLNamedIndividual individual) {
                return ont.getAxioms(individual, EXCLUDED);
            }

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLDatatype datatype) {
                return ont.getAxioms(datatype, EXCLUDED);
            }

            @Nonnull
            @Override
            public Set<? extends OWLAxiom> visit(@Nonnull OWLAnnotationProperty property) {
                return ont.getAxioms(property, EXCLUDED);
            }
        });
    }

    @Nonnull
    protected List<? extends OWLAxiom> sortAxioms(@Nonnull Set<? extends OWLAxiom> axioms) {
        return sortOptionally(axioms);
    }

    @Nonnull
    private String getIRIString(@Nonnull OWLEntity entity) {
        return defaultPrefixManager.getShortForm(entity);
    }

    private String getEntityLabel(@Nonnull OWLEntity entity) {
        return labelMaker.getShortForm(entity).replace("\n", "\n# ");
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     * 
     * @param entity The entity
     * @return The axioms that were written out
     */
    @Nonnull
    protected Set<OWLAxiom> writeDeclarations(@Nonnull OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<>();
        Set<OWLDeclarationAxiom> declarationAxioms = ont.getDeclarationAxioms(entity);
        for (OWLAxiom ax : sortOptionally(declarationAxioms)) {
            ax.accept(this);
            axioms.add(ax);
            writeReturn();
        }
        return axioms;
    }

    private void writeDeclarations(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms, Collection<IRI> illegals) {
        Collection<OWLDeclarationAxiom> axioms = ont.getDeclarationAxioms(entity);
        for (OWLDeclarationAxiom ax : sortOptionally(axioms)) {
            if (!alreadyWrittenAxioms.contains(ax)) {
                ax.accept(this);
                writeReturn();
            }
        }
        // if multiple illegal declarations already exist, they have already
        // been output; the renderer cannot take responsibility for removing
        // them. It should not add declarations for illegally punned entities
        // here, though
        if (addMissingDeclarations && axioms.isEmpty()) {
            // if declarations should be added, check if the IRI is illegally
            // punned
            if (!entity.isBuiltIn() && !illegals.contains(entity.getIRI())
                && !ont.isDeclared(entity, INCLUDED)) {
                OWLDeclarationAxiom declaration =
                    ont.getOWLOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(entity);
                declaration.accept(this);
                writeReturn();
            }
        }
        alreadyWrittenAxioms.addAll(axioms);
    }

    /**
     * Writes of the annotation for the specified entity.
     * 
     * @param entity The entity
     * @param alreadyWrittenAxioms already written axioms, to be updated with the newly written
     *        axioms
     */
    protected void writeAnnotations(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
            sortOptionally(ont.getAnnotationAssertionAxioms(entity.getIRI()));
        writeAnnotations2(entity, alreadyWrittenAxioms, annotationAssertionAxioms);
    }

    protected void writeAnnotations2(@SuppressWarnings("unused") @Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms,
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms) {
        for (OWLAnnotationAxiom ax : annotationAssertionAxioms) {
            ax.accept(this);
            writeReturn();
        }
        alreadyWrittenAxioms.addAll(annotationAssertionAxioms);
    }

    /**
     * Write.
     * 
     * @param v the v
     * @param o the o
     */
    protected void write(@Nonnull OWLXMLVocabulary v, @Nonnull OWLObject o) {
        write(v);
        writeOpenBracket();
        o.accept(this);
        writeCloseBracket();
    }

    private void write(@Nonnull Collection<? extends OWLObject> objects) {
        if (objects.size() > 2) {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
                it.next().accept(this);
                if (it.hasNext()) {
                    writeSpace();
                }
            }
        } else if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject objA = it.next();
            OWLObject objB = it.next();
            OWLObject lhs;
            OWLObject rhs;
            if (objA.equals(focusedObject)) {
                lhs = objA;
                rhs = objB;
            } else {
                lhs = objB;
                rhs = objA;
            }
            lhs.accept(this);
            writeSpace();
            rhs.accept(this);
        } else if (objects.size() == 1) {
            objects.iterator().next().accept(this);
        }
    }

    private void write(@Nonnull List<? extends OWLObject> objects) {
        if (objects.size() > 1) {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
                it.next().accept(this);
                if (it.hasNext()) {
                    writeSpace();
                }
            }
        } else if (objects.size() == 1) {
            objects.iterator().next().accept(this);
        }
    }

    protected void writeOpenBracket() {
        write("(");
    }

    protected void writeCloseBracket() {
        write(")");
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void writeReturn() {
        write("\n");
    }

    protected void writeAnnotations(@Nonnull OWLAxiom ax) {
        for (OWLAnnotation anno : getSortedAnnotations(ax)) {
            anno.accept(this);
            writeSpace();
        }
    }

    protected void writeAxiomStart(@Nonnull OWLXMLVocabulary v, @Nonnull OWLAxiom axiom) {
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
    }

    protected void writeAxiomEnd() {
        writeCloseBracket();
    }

    protected void writePropertyCharacteristic(@Nonnull OWLXMLVocabulary v, @Nonnull OWLAxiom ax,
        @Nonnull OWLPropertyExpression prop) {
        writeAxiomStart(v, ax);
        prop.accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(ASYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        writeAxiomStart(CLASS_ASSERTION, axiom);
        axiom.getClassExpression().accept(this);
        writeSpace();
        axiom.getIndividual().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_DATA_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        writeAxiomStart(DECLARATION, axiom);
        writeEntitiesAsURIs = false;
        axiom.getEntity().accept(this);
        writeEntitiesAsURIs = true;
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        Set<OWLIndividual> individuals = axiom.getIndividuals();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DIFFERENT_INDIVIDUALS, axiom);
        write(sortOptionally(individuals));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_CLASSES, axiom);
        write(sortOptionally(classExpressions));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_DATA_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_OBJECT_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        writeAxiomStart(DISJOINT_UNION, axiom);
        axiom.getOWLClass().accept(this);
        writeSpace();
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        List<OWLClassExpression> expressionList = sortOptionally(classExpressions);
        write(expressionList);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        writeAxiomStart(ANNOTATION_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getValue().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_CLASSES, axiom);
        List<OWLClassExpression> expressionList = sortOptionally(classExpressions);
        write(expressionList);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_DATA_PROPERTIES, axiom);
        List<OWLDataPropertyExpression> expressionList = sortOptionally(properties);
        write(expressionList);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_OBJECT_PROPERTIES, axiom);
        List<OWLObjectPropertyExpression> expressionList = sortOptionally(properties);
        write(expressionList);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_DATA_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        writeAxiomStart(INVERSE_OBJECT_PROPERTIES, axiom);
        axiom.getFirstProperty().accept(this);
        writeSpace();
        axiom.getSecondProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(IRREFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        write(OBJECT_PROPERTY_CHAIN);
        writeOpenBracket();
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it
            .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(REFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        Set<OWLIndividual> individuals = axiom.getIndividuals();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(SAME_INDIVIDUAL, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        writeAxiomStart(SUB_CLASS_OF, axiom);
        axiom.getSubClass().accept(this);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(SYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(TRANSITIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClass ce) {
        if (!writeEntitiesAsURIs) {
            write(CLASS);
            writeOpenBracket();
        }
        ce.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLCardinalityRestriction<F> restriction, @Nonnull OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        p.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLQuantifiedDataRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v, @Nonnull OWLPropertyExpression prop,
        @Nonnull OWLObject filler) {
        write(v);
        writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(DATA_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        writeRestriction(DATA_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        writeRestriction(DATA_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        writeRestriction(DATA_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(DATA_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        writeRestriction(DATA_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(OBJECT_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        write(OBJECT_COMPLEMENT_OF, ce.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        writeRestriction(OBJECT_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        if (ce.getOperands().size() == 1) {
            ce.getOperands().iterator().next().accept(this);
            return;
        }
        write(OBJECT_INTERSECTION_OF);
        writeOpenBracket();
        Set<OWLClassExpression> operands = ce.getOperands();
        List<OWLClassExpression> objects = sortOptionally(operands);
        write(objects);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        writeRestriction(OBJECT_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        writeRestriction(OBJECT_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        write(OBJECT_ONE_OF);
        writeOpenBracket();
        Set<OWLIndividual> individuals = ce.getIndividuals();
        List<OWLIndividual> objects = sortOptionally(individuals);
        write(objects);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        write(OBJECT_HAS_SELF, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(OBJECT_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        if (ce.getOperands().size() == 1) {
            ce.getOperands().iterator().next().accept(this);
            return;
        }
        write(OBJECT_UNION_OF);
        writeOpenBracket();
        Set<OWLClassExpression> operands = ce.getOperands();
        List<OWLClassExpression> objects = sortOptionally(operands);
        write(objects);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        writeRestriction(OBJECT_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        write(DATA_COMPLEMENT_OF, node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        write(DATA_ONE_OF);
        writeOpenBracket();
        Set<OWLLiteral> values = node.getValues();
        write(sortOptionally(values));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        if (!writeEntitiesAsURIs) {
            write(DATATYPE);
            writeOpenBracket();
        }
        node.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        write(DATATYPE_RESTRICTION);
        writeOpenBracket();
        node.getDatatype().accept(this);
        Set<OWLFacetRestriction> facetRestrictions = node.getFacetRestrictions();
        List<OWLFacetRestriction> restrictionList = sortOptionally(facetRestrictions);
        for (OWLFacetRestriction restriction : restrictionList) {
            writeSpace();
            restriction.accept(this);
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        write("\"");
        write(EscapeUtils.escapeString(node.getLiteral()));
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral() && (explicitXsdString
            || !OWL2Datatype.XSD_STRING.getIRI().equals(node.getDatatype().getIRI()))) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        if (!writeEntitiesAsURIs) {
            write(DATA_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        if (!writeEntitiesAsURIs) {
            write(OBJECT_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        write(OBJECT_INVERSE_OF);
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        if (!writeEntitiesAsURIs) {
            write(NAMED_INDIVIDUAL);
            writeOpenBracket();
        }
        individual.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        writeAxiomStart(HAS_KEY, axiom);
        axiom.getClassExpression().accept(this);
        writeSpace();
        writeOpenBracket();
        Set<OWLObjectPropertyExpression> objectPropertyExpressions =
            axiom.getObjectPropertyExpressions();
        List<OWLObjectPropertyExpression> expressions = sortOptionally(objectPropertyExpressions);
        for (Iterator<? extends OWLPropertyExpression> it = expressions.iterator(); it.hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeSpace();
        writeOpenBracket();
        Set<OWLDataPropertyExpression> dataPropertyExpressions = axiom.getDataPropertyExpressions();
        List<OWLDataPropertyExpression> expressionList = sortOptionally(dataPropertyExpressions);
        for (Iterator<? extends OWLPropertyExpression> it = expressionList.iterator(); it
            .hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_ANNOTATION_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        if (node.getOperands().size() == 1) {
            node.getOperands().iterator().next().accept(this);
            return;
        }
        write(DATA_INTERSECTION_OF);
        writeOpenBracket();
        Set<OWLDataRange> operands = node.getOperands();
        List<OWLDataRange> objects = sortOptionally(operands);
        write(objects);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        if (node.getOperands().size() == 1) {
            node.getOperands().iterator().next().accept(this);
            return;
        }
        write(DATA_UNION_OF);
        writeOpenBracket();
        Set<OWLDataRange> operands = node.getOperands();
        List<OWLDataRange> objects = sortOptionally(operands);
        write(objects);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        if (!writeEntitiesAsURIs) {
            write(ANNOTATION_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(@Nonnull IRI iri) {
        write(iri);
    }

    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        write(ANNOTATION);
        writeOpenBracket();
        for (OWLAnnotation anno : getSortedAnnotations(node)) {
            anno.accept(this);
            writeSpace();
        }
        node.getProperty().accept(this);
        writeSpace();
        node.getValue().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        writeAxiomStart(DATATYPE_DEFINITION, axiom);
        axiom.getDatatype().accept(this);
        writeSpace();
        axiom.getDataRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull SWRLRule rule) {
        writeAxiomStart(DL_SAFE_RULE, rule);
        write(BODY);
        writeOpenBracket();
        write(rule.getBody());
        writeCloseBracket();
        write(HEAD);
        writeOpenBracket();
        write(rule.getHead());
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        write(CLASS_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        write(DATA_RANGE_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        write(OBJECT_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        write(DATA_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        write(BUILT_IN_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        node.getIRI().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_INDIVIDUALS_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        write(SAME_INDIVIDUAL_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }
}
