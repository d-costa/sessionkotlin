/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.david.sessionkotlin_processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid

class ProjectProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    private val packageName = "org.david.sessionkotlin_lib.annotation"
    val filename = "GeneratedSources"
    val annotationName = "Project"

    override fun process(resolver: Resolver): List<KSAnnotated> {


        val symbols = resolver
            .getSymbolsWithAnnotation(
                listOf(packageName, annotationName).joinToString("."),
                inDepth = true
            )
            .filterIsInstance<KSClassDeclaration>()

        // Exit from the processor in case nothing is annotated
        if (!symbols.iterator().hasNext()) return emptyList()

        symbols.forEach { it.accept(Visitor(), Unit) }


//        val fileSpecBuilder = FileSpec.builder(
//            packageName = packageName,
//            fileName = filename
//        )
//        fileSpecBuilder.addComment("This is a generated file. Do not change it.")
//        fileSpecBuilder.build().writeTo(codeGenerator, Dependencies(false)) // TODO

        return emptyList()
    }

    inner class Visitor() : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.classKind != ClassKind.CLASS) {
                logger.error("Only classes can be annotated with @$annotationName", classDeclaration)
                return
            }

            // Getting the list of member properties of the annotated interface.
            val properties = classDeclaration
                .superTypes

            properties.forEach { prop ->
                logger.error(prop.toString())
            }
        }
    }
}