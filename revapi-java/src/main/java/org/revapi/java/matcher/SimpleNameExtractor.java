/*
 * Copyright 2014-2017 Lukas Krejci
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.revapi.java.matcher;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor8;

import org.revapi.java.spi.JavaAnnotationElement;
import org.revapi.java.spi.JavaModelElement;
import org.revapi.java.spi.Util;

/**
 * @author Lukas Krejci
 */
final class SimpleNameExtractor implements DataExtractor<String> {
    @Override
    public String extract(JavaModelElement element) {
        return element.getDeclaringElement().getSimpleName().toString();
    }

    @Override
    public String extract(JavaAnnotationElement element) {
        return element.getAnnotation().getAnnotationType().asElement().getSimpleName().toString();
    }

    @Override
    public String extract(TypeMirror type) {
        return Util.toHumanReadableString(type);
    }

    @Override
    public String extract(AnnotationAttributeElement element) {
        return element.getAttributeMethod().getSimpleName().toString();
    }

    @Override
    public String extract(TypeParameterElement element) {
        return element.getType().accept(new SimpleTypeVisitor8<String, Void>() {
            @Override
            public String visitTypeVariable(TypeVariable t, Void aVoid) {
                return t.asElement().getSimpleName().toString();
            }

            @Override
            public String visitWildcard(WildcardType t, Void aVoid) {
                return "?";
            }

            @Override
            public String visitDeclared(DeclaredType t, Void aVoid) {
                return t.asElement().getSimpleName().toString();
            }
        }, null);
    }

    @Override
    public String extract(AnnotationValue value) {
        return "";
    }

    @Override
    public Class<String> extractedType() {
        return null;
    }
}
