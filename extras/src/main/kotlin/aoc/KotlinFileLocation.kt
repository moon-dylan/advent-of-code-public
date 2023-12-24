package aoc

import collections.NonEmptyList
import java.io.File
import kotlin.reflect.KClass

fun <T : Any> getKotlinClassDeclarationLocation(klass: KClass<T>): File =
    klass.java.protectionDomain.codeSource.location.file
        .replaceFirst("/build/classes/kotlin/", "/src/")
        .let { File(it) }
        .let { File(it, "kotlin") }
        .let { File(it, klass.java.packageName) }

fun main() {
    println(getKotlinClassDeclarationLocation(NonEmptyList::class))
}
