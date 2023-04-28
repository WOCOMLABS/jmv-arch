package io.thorib.framework.test

/**
 * Marks functions and properties as part of the ThoribioTest DSL.
 */
@Target(
    AnnotationTarget.CLASS ,
    AnnotationTarget.FUNCTION ,
    AnnotationTarget.PROPERTY
)
@DslMarker
annotation class ThoribioTestDSL