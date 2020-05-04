// TODO remove file with 1.0.0
@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")
package ch.tutteli.atrium.api.cc.infix.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.contain
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.order
import ch.tutteli.atrium.api.infix.en_GB.contains
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.builders.migration.asAssert
import ch.tutteli.atrium.domain.builders.migration.asExpect
import org.jetbrains.spek.api.Spek
import ch.tutteli.atrium.api.infix.en_GB.values
import org.jetbrains.spek.api.include
import kotlin.reflect.KFunction2
import ch.tutteli.atrium.api.infix.en_GB.o

//TODO remove with 1.0.0, no need to migrate to Spek 2
class IterableContainsInAnyOrderAtLeast1ValuesAssertionsSpec : Spek({
    include(BuilderSpec)
    include(ShortcutSpec)
}) {
    object BuilderSpec : ch.tutteli.atrium.spec.integration.IterableContainsInAnyOrderAtLeast1ValuesAssertionsSpec(
        AssertionVerbFactory,
        getContainsPair(),
        getContainsNullablePair(),
        "◆ ",
        "[Atrium][Builder] "
    )

    object ShortcutSpec : ch.tutteli.atrium.spec.integration.IterableContainsInAnyOrderAtLeast1ValuesAssertionsSpec(
        AssertionVerbFactory,
        getContainsShortcutPair(),
        getContainsNullableShortcutPair(),
        "◆ ",
        "[Atrium][Shortcut] "
    )

    companion object : IterableContainsSpecBase() {
        fun getContainsPair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderValues" to Companion::containsValues

        private fun containsValues(plant: Assert<Iterable<Double>>, a: Double, aX: Array<out Double>): Assert<Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(o) inAny order atLeast 1 value a
            } else {
                plant.asExpect().contains(o) inAny order atLeast 1 the Values(a, *aX)
            }
        }

        fun getContainsNullablePair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderValues" to Companion::containsNullableValues

        private fun containsNullableValues(plant: Assert<Iterable<Double?>>, a: Double?, aX: Array<out Double?>): Assert<Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(o) inAny order atLeast 1 value a
            } else {
                plant.asExpect().contains(o) inAny order atLeast 1 the Values(a, *aX)
            }
        }


        private val containsFun: KFunction2<Assert<Iterable<Double>>, Values<Double>, Assert<Iterable<Double>>> = Assert<Iterable<Double>>::contains
        fun getContainsShortcutPair() = containsFun.name to Companion::contains

        private fun contains(plant: Assert<Iterable<Double>>, a: Double, aX: Array<out Double>): Assert<Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(a).asAssert()
            } else {
                val values = Values(a, *aX)
                plant.asExpect()
                    .contains(values(values.expected, *values.otherExpected))
                    .asAssert()
            }
        }

        private val containsNullableFun: KFunction2<Assert<Iterable<Double?>>, Values<Double?>, Assert<Iterable<Double?>>> = Assert<Iterable<Double?>>::contains
        fun getContainsNullableShortcutPair() = containsNullableFun.name to Companion::containsNullableValuesShortcut

        private fun containsNullableValuesShortcut(plant: Assert<Iterable<Double?>>, a: Double?, aX: Array<out Double?>): Assert<Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(a).asAssert()
            } else {
                val values = Values(a, *aX)
                plant.asExpect().contains(values(values.expected, *values.otherExpected)).asAssert()
            }
        }
    }
}
