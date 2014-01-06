/*
 * Copyright 2014 Lukas Krejci
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
 * limitations under the License
 */

package org.revapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukas Krejci
 * @since 1.0
 */
public final class MatchReport {
    public static final class Problem {
        public static final class Builder {
            private final MatchReport.Builder reportBuilder;
            private String code;
            private String name;
            private String description;
            private Map<CompatibilityType, MismatchSeverity> classification = new HashMap<>();

            private Builder(MatchReport.Builder reportBuilder) {
                this.reportBuilder = reportBuilder;
            }

            public Builder withCode(String code) {
                this.code = code;
                return this;
            }

            public Builder withName(String name) {
                this.name = name;
                return this;
            }

            public Builder withDescription(String description) {
                this.description = description;
                return this;
            }

            public Builder addClassification(CompatibilityType compat, MismatchSeverity severity) {
                classification.put(compat, severity);
                return this;
            }

            public MatchReport.Builder done() {
                Problem p = build();
                reportBuilder.problems.add(p);
                return reportBuilder;
            }

            public Problem build() {
                return new Problem(code, name, description, classification);
            }
        }

        public static Builder create() {
            return new Builder(null);
        }

        /**
         * API analyzer dependent unique identification of the reported problem
         */
        public final String code;

        /**
         * Human readable name of the problem
         */
        public final String name;

        /**
         * Detailed description of the problem
         */
        public final String description;
        public final Map<CompatibilityType, MismatchSeverity> classification;

        public Problem(String code, String name, String description, CompatibilityType compatibility,
            MismatchSeverity severity) {
            this.code = code;
            this.name = name;
            this.description = description;
            classification = Collections.singletonMap(compatibility, severity);
        }

        public Problem(String code, String name, String description,
            Map<CompatibilityType, MismatchSeverity> classification) {
            this.code = code;
            this.name = name;
            this.description = description;
            HashMap<CompatibilityType, MismatchSeverity> tmp = new HashMap<>(classification);
            this.classification = Collections.unmodifiableMap(tmp);
        }
    }

    public static final class Builder {
        private Element oldElement;
        private Element newElement;
        private ArrayList<Problem> problems = new ArrayList<>();

        private Builder withOld(Element element) {
            oldElement = element;
            return this;
        }

        public Builder withNew(Element element) {
            newElement = element;
            return this;
        }

        public Problem.Builder addProblem() {
            return new Problem.Builder(this);
        }

        public MatchReport build() {
            return new MatchReport(problems, oldElement, newElement);
        }
    }

    public static Builder create() {
        return new Builder();
    }

    private final List<Problem> problems;
    private final Element oldElement;
    private final Element newElement;

    public MatchReport(Iterable<Problem> problems, Element oldElement, Element newElement) {
        ArrayList<Problem> tmp = new ArrayList<>();
        for (Problem p : problems) {
            tmp.add(p);
        }

        this.problems = Collections.unmodifiableList(tmp);
        this.oldElement = oldElement;
        this.newElement = newElement;
    }

    public Element getNewElement() {
        return newElement;
    }

    public Element getOldElement() {
        return oldElement;
    }

    public List<Problem> getProblems() {
        return problems;
    }
}
