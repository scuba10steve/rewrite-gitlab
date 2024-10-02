/*
 * Copyright 2024 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.gitlab.core;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.yaml.DeleteKey;

@Value
@EqualsAndHashCode
public class RemoveTemplate extends Recipe {

    @Option(displayName = "Template",
            description = "The name of the template to match.",
            example = "Terraform/Base.gitlab-ci.yml")
    String oldTemplate;

    @Override
    public String getDisplayName() {
        return "Change GitLab template";
    }

    @Override
    public String getDescription() {
        return "Change a GitLab template in use.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new DeleteKey(
                "$.include[?(@.template =~ '" + oldTemplate + "(?:@.+)?')]",
                ".gitlab-ci.yml").getVisitor();
    }
}
