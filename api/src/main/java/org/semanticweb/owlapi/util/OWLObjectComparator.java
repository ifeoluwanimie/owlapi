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
package org.semanticweb.owlapi.util;

import java.util.Comparator;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * A ShortFormProvider based comparator for {@code OWLObject} instances. OWLEntity instances are
 * sorted according to their short forms.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public class OWLObjectComparator implements Comparator<OWLObject> {

    @Nonnull
    private final OWLEntityComparator entityComparator;

    /**
     * @param shortFormProvider short form provider to use
     */
    public OWLObjectComparator(@Nonnull ShortFormProvider shortFormProvider) {
        entityComparator = new OWLEntityComparator(shortFormProvider);
    }

    @Override
    public int compare(OWLObject o1, OWLObject o2) {
        if (o1 == o2) {
            return 0;
        }
        // if both objects are entities, compare their short forms
        if (o1 instanceof OWLEntity && o2 instanceof OWLEntity) {
            return entityComparator.compare((OWLEntity) o1, (OWLEntity) o2);
        }
        return o1.compareTo(o2);
    }
}
