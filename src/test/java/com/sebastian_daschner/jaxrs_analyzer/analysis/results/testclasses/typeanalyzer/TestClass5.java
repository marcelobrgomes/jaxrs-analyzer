/*
 * Copyright (C) 2015 Sebastian Daschner, sebastian-daschner.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sebastian_daschner.jaxrs_analyzer.analysis.results.testclasses.typeanalyzer;

import com.sebastian_daschner.jaxrs_analyzer.analysis.results.TypeUtils;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.TypeIdentifier;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.TypeRepresentation;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestClass5 {

    private List<String> first;
    private transient String third;
    @XmlTransient
    private String fourth;

    public List<String> getIgnored() {
        return first;
    }

    @XmlElement
    public Set<String> getSecond() {
        return null;
    }

    public static Set<TypeRepresentation> expectedTypeRepresentations() {
        final Map<String, TypeIdentifier> properties = new HashMap<>();

        final TypeIdentifier listIdentifier = TypeIdentifier.ofType("Ljava/util/List<Ljava/lang/String;>;");
        final TypeIdentifier setIdentifier = TypeIdentifier.ofType("Ljava/util/Set<Ljava/lang/String;>;");

        properties.put("first", listIdentifier);
        properties.put("second", setIdentifier);

        final TypeRepresentation testClass5 = TypeRepresentation.ofConcrete(expectedIdentifier(), properties);
        final TypeRepresentation string = TypeRepresentation.ofConcrete(TypeUtils.STRING_IDENTIFIER);
        final TypeRepresentation listString = TypeRepresentation.ofCollection(listIdentifier, string);
        final TypeRepresentation setString = TypeRepresentation.ofCollection(setIdentifier, string);

        return new HashSet<>(Arrays.asList(testClass5, listString, setString));
    }

    public static TypeIdentifier expectedIdentifier() {
        return TypeIdentifier.ofType("Lcom/sebastian_daschner/jaxrs_analyzer/analysis/results/testclasses/typeanalyzer/TestClass5;");
    }

}
