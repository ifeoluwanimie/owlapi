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
package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.util.QNameShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * A renderer that provides an HTML version of the ontology.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management Group
 * @since 2.0.0
 */
public class OWLTutorialSyntaxObjectRenderer extends OWLObjectVisitorAdapter {

    private static final boolean TABLES = true;
    private static final int TABLE_COLUMNS = 3;
    private final @Nonnull ShortFormProvider shortForms;
    private final Writer writer;
    int lastNewLinePos;
    private int pos;

    public OWLTutorialSyntaxObjectRenderer(Writer writer) {
        this.writer = writer;
        shortForms = new QNameShortFormProvider();
    }

    @Nonnull
    public String labelFor(@Nonnull OWLEntity entity) {
        return shortForms.getShortForm(entity);
    }

    private OWLTutorialSyntaxObjectRenderer write(@Nonnull String s) {
        try {
            int newLineIndex = s.indexOf('\n');
            if (newLineIndex != -1) {
                lastNewLinePos = pos + newLineIndex;
            }
            pos += s.length();
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(int i) {
        try {
            String s = " " + i + ' ';
            pos += s.length();
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(@Nonnull IRI iri) {
        write("<").write(iri.toQuotedString()).write(">");
        return this;
    }

    public void header() {
        write(
            "<html>\n<head>\n<style>\nbody { font-family: sans-serif; }\n.key { color: grey; font-size: 75%; }\n.op { color: grey; }\n.cl { color: #800; }\n.pr { color: #080; }\n.in { color: #008; }\n.box { border: solid 1px grey; padding: 10px; margin: 10px; }\ntable { width: 100%; }\ntd { padding-left: 10px; padding-right: 10px; width: "
                + 100 / TABLE_COLUMNS + "%;}\n</style>\n<body>\n");
    }

    public void footer() {
        write("</body>\n</html>\n");
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeCollection(
        @Nonnull Collection<T> objects) {
        if (TABLES) {
            return writeTable(objects);
        }
        return writeList(objects);
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeTable(
        @Nonnull Collection<T> objects) {
        writeTableStart();
        int count = 0;
        for (Iterator<T> it = objects.iterator(); it.hasNext();) {
            if (count % TABLE_COLUMNS == 0) {
                if (count > 0) {
                    writeTableRowEnd();
                }
                writeTableRowStart();
            }
            writeTableCellStart();
            it.next().accept(this);
            writeTableCellEnd();
            count++;
        }
        writeTableRowEnd();
        writeTableEnd();
        return this;
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeList(
        @Nonnull Collection<T> objects) {
        writeListStart();
        for (Iterator<T> it = objects.iterator(); it.hasNext();) {
            writeListItemStart();
            it.next().accept(this);
            writeListItemEnd();
        }
        writeListEnd();
        return this;
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        header();
        write("<h1>");
        write(ontology.getOntologyID().toString());
        write("</h1>\n");
        write("<div>");
        write("<div class='box'>\n");
        ontology.getImportsDeclarations().stream().sorted().forEach(decl -> {
            write("Imports: ");
            write(decl.getIRI().toString());
            write("\n");
        });
        write("<h2>Classes</h2>\n");
        writeCollection(ontology.getClassesInSignature());
        write("</div>\n");
        write("<div class='box'>\n");
        write("<h2>Properties</h2>\n");
        writeCollection(ontology.getObjectPropertiesInSignature());
        writeCollection(ontology.getDataPropertiesInSignature());
        write("</div>\n");
        write("<div class='box'>\n");
        write("<h2>Individuals</h2>\n");
        writeCollection(ontology.getIndividualsInSignature());
        write("</div>");
        write("<div>");
        write("<div class='box'>");
        write("<h2>Axioms</h2>\n");
        writeListStart();
        for (OWLAxiom ax : ontology.getAxioms()) {
            writeListItemStart();
            ax.accept(this);
            writeListEnd();
        }
        writeListEnd();
        write("</div>");
        footer();
    }

    public OWLTutorialSyntaxObjectRenderer write(@Nonnull String str, @Nonnull OWLObject o) {
        write(str).write("(");
        o.accept(this);
        write(")");
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(@Nonnull Collection<? extends OWLObject> objects,
        @Nonnull String separator) {
        for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(separator);
                writeSpace();
            }
        }
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(
        @Nonnull Collection<? extends OWLObject> objects) {
        return write(objects, "");
    }

    public OWLTutorialSyntaxObjectRenderer writeOpenBracket() {
        return write("(");
    }

    public OWLTutorialSyntaxObjectRenderer writeCloseBracket() {
        return write(")");
    }

    public OWLTutorialSyntaxObjectRenderer writeSpace() {
        return write(" ");
    }

    public OWLTutorialSyntaxObjectRenderer writeAnnotations(
        @SuppressWarnings("unused") OWLAxiom ax) {
        return this;
    }

    public OWLTutorialSyntaxObjectRenderer writeListStart() {
        return write("<ul>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListEnd() {
        return write("</ul>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableStart() {
        return write("<table>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableEnd() {
        return write("</table>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableRowStart() {
        return write("<tr>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableRowEnd() {
        return write("</tr>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableCellStart() {
        return write("<td>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableCellEnd() {
        return write("</td>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListItemStart() {
        return write("<li>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListItemEnd() {
        return write("</li>\n");
    }

    @SuppressWarnings("unused")
    public OWLTutorialSyntaxObjectRenderer writePropertyCharacteristic(String str, OWLAxiom ax,
        @Nonnull OWLPropertyExpression prop) {
        write(keyword(str));
        writeSpace();
        prop.accept(this);
        return this;
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("asymmetric", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        write(keyword(":")).writeSpace();
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("domain"));
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("range"));
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(keyword("subProperty"));
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {}

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        write(axiom.getIndividuals(), keyword("!="));
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        write(axiom.getClassExpressions(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        write(keyword("disjoint")).write(axiom.getProperties(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        writeSpace().write(keyword("==")).writeSpace().write(axiom.getClassExpressions(),
            keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        write(axiom.getClassExpressions(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom, axiom.getProperty());
    }

    public void visit(@Nonnull OWLImportsDeclaration axiom) {
        write(keyword("imports")).write(axiom.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("inversefunctional", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        writeSpace().write(keyword("inverse")).writeSpace();
        axiom.getSecondProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("Irreflexive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace().write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace().write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        write("chain").writeOpenBracket().write(axiom.getPropertyChain()).writeCloseBracket();
        writeSpace().write(keyword("subProperty")).writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace().write(keyword("domain")).writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace().write(keyword("range")).writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace().write(keyword("subProperty")).writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("reflexive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        write(axiom.getIndividuals(), keyword("="));
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        writeSpace().write(keyword("subClass")).writeSpace();
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("symmetric", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("transitive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        write("<span class='cl'>" + labelFor(ce) + "</span>");
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(@Nonnull String str,
        @Nonnull OWLCardinalityRestriction<?> restriction,
        @Nonnull OWLPropertyExpression property) {
        write(str).writeOpenBracket().write(restriction.getCardinality()).writeSpace();
        property.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(@Nonnull String str,
        @Nonnull OWLQuantifiedDataRestriction restriction) {
        return writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(@Nonnull String str,
        @Nonnull OWLQuantifiedObjectRestriction restriction) {
        return writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(@Nonnull String str,
        @Nonnull OWLPropertyExpression prop, @Nonnull OWLObject filler) {
        write(str).writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
        return this;
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        writeRestriction("has-value", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        write(operator("not"), ce.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        writeOpenBracket().write(ce.getOperands(), keyword("and")).writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        write(operator("one-of")).writeOpenBracket().write(ce.getIndividuals()).writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        write("self", ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        writeOpenBracket().write(ce.getOperands(), " or ").writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        writeRestriction("hasValue", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        write(operator("not"), node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        write(operator("one-of")).write("(").write(node.getValues()).write(")");
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        write("Datatype").writeOpenBracket().write(node.getIRI()).writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        write("DatatypeRestriction").writeOpenBracket();
        node.getDatatype().accept(this);
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            writeSpace();
            restriction.accept(this);
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        write(node.getFacet().getIRI()).writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        write("\"").write(node.getLiteral()).write("\"");
        if (node.hasLang()) {
            write("@").write(node.getLang());
        } else {
            write("^^").write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        write("<span class='pr'>" + labelFor(property) + "</span>");
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write("<span class='pr'>" + labelFor(property) + "</span>");
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        write("inv").writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write("<span class='in'>" + labelFor(individual) + "</span>");
    }

    @Override
    public void visit(SWRLRule rule) {}

    @Nonnull
    public String keyword(String str) {
        return "<span class='key'>" + str + "</span>";
    }

    @Nonnull
    public String operator(String str) {
        return "<span class='op'>" + str + "</span>";
    }
}
