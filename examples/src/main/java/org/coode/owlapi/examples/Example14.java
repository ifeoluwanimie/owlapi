package org.coode.owlapi.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.*;
import org.semanticweb.reasonerfactory.pellet.PelletReasonerFactory;

import java.net.URI;
import java.util.Collections;
/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 15-Oct-2008<br><br>
 */
public class Example14 {

    public static void main(String[] args) {
        try {
            // We will load the pizza ontology and query it using a reasoner
            URI physicalURI = URI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont = man.loadOntologyFromPhysicalURI(physicalURI);

            // For this particular ontology, we know that all class, properties names etc. have
            // URIs that is made up of the ontology URI plus # plus the local name
            String namespace = ont.getURI() + "#";

            // Create a reasoner.  We will use Pellet in this case.  Make sure that the latest
            // version of the Pellet libraries are on the runtime class path
            OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createReasoner(man);
            // We only have our one ontology to load into the reasoner
            reasoner.loadOntologies(Collections.singleton(ont));

            // Now we can query the reasoner, suppose we want to determine the properties that
            // instances of Marghertia pizza must have
            OWLClass margheritaPizza = man.getOWLDataFactory().getOWLClass(URI.create(namespace + "Margherita"));
            printProperties(man, ont, reasoner, margheritaPizza);

            // Let's do the same for JalapenoPepperTopping
            OWLClass vegTopping = man.getOWLDataFactory().getOWLClass(URI.create(namespace + "JalapenoPepperTopping"));
            printProperties(man, ont, reasoner, vegTopping);

            // We can also ask if the instances of a class must have a property
            OWLClass mozzarellaTopping = man.getOWLDataFactory().getOWLClass(URI.create(namespace + "MozzarellaTopping"));
            OWLObjectProperty hasOrigin = man.getOWLDataFactory().getOWLObjectProperty(URI.create(namespace + "hasCountryOfOrigin"));
            if (hasProperty(man, reasoner, mozzarellaTopping, hasOrigin)) {
                System.out.println("Instances of " + mozzarellaTopping + " have a country of origin");
            }
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Problem loading ontology: " + e.getMessage());
        } catch (OWLReasonerException e) {
            System.out.println("Problem with reasoner: " + e.getMessage());
        }
    }

    /**
     * Prints out the properties that instances of a class description must have
     *
     * @param man      The manager
     * @param ont      The ontology
     * @param reasoner The reasoner
     * @param cls      The class description
     * @throws OWLReasonerException If there was a problem during reasoning
     */
    private static void printProperties(OWLOntologyManager man, OWLOntology ont, OWLReasoner reasoner, OWLClass cls) throws OWLReasonerException {
        if (!ont.containsClassReference(cls.getURI())) {
            throw new RuntimeException("Class not in signature of the ontology");
        }
        // Note that the following code could be optimised... if we find that instances of the specified class
        // do not have a property, then we don't need to check the sub properties of this property
        System.out.println("----------------------------------------------------------");
        System.out.println("Properties of " + cls);
        System.out.println("----------------------------------------------------------");
        for (OWLObjectPropertyExpression prop : ont.getReferencedObjectProperties()) {
            boolean sat = hasProperty(man, reasoner, cls, prop);
            if (sat) {
                System.out.println("Instances of " + cls + " necessarily have the property " + prop);
            }
        }
    }

    private static boolean hasProperty(OWLOntologyManager man, OWLReasoner reasoner, OWLClass cls, OWLObjectPropertyExpression prop) throws OWLReasonerException {
        // To test whether the instances of a class must have a property we create a some values
        // from restriction and then ask for the satisfiability of the class interesected with the complement
        // of this some values from restriction.  If the intersection is satisfiable then the instances of
        // the class don't have to have the property, otherwise, they do.
        OWLDataFactory dataFactory = man.getOWLDataFactory();
        OWLClassExpression restriction = dataFactory.getOWLObjectSomeValuesFrom(prop, dataFactory.getOWLThing());
        // Now we see if the intersection of the class and the complement of this restriction is satisfiable
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(restriction);
        OWLClassExpression intersection = dataFactory.getOWLObjectIntersectionOf(cls, complement);
        return !reasoner.isSatisfiable(intersection);
    }
}
