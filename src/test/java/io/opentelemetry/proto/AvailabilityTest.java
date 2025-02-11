/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.proto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

/** A placeholder test which verifies that the generated classes compile and can load. */
public class AvailabilityTest {

  @Test
  void available() {

    isValidClass("io.opentelemetry.proto.trace.v1.Span");
    isValidClass("io.opentelemetry.proto.metrics.v1.Metric");
    isValidClass("io.opentelemetry.proto.logs.v1.LogRecord");
  }

  private static void isValidClass(String fqcn) {
    Class<?> clazz = null;
    try {
      clazz = Class.forName(fqcn);
    } catch (ClassNotFoundException e) {
      fail(e.getMessage());
    }
    Field[] declaredFields = clazz.getDeclaredFields();
    Class<?>[] declaredClasses = clazz.getDeclaredClasses();
    boolean hasFieldsOrInnerClasses =
        (declaredFields != null && declaredFields.length > 0)
            || (declaredClasses != null && declaredClasses.length > 0);
    assertThat(hasFieldsOrInnerClasses).withFailMessage(() -> fqcn + " is empty").isTrue();
  }
}
