// TODO remove file with 1.0.0
@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")
package ch.tutteli.atrium.api.cc.infix.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.contain
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.order
import ch.tutteli.atrium.api.infix.en_GB.atLeast
import ch.tutteli.atrium.api.infix.en_GB.contains
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.builders.migration.asAssert
import ch.tutteli.atrium.domain.builders.migration.asExpect
import ch.tutteli.atrium.domain.builders.migration.asSubExpect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include
import kotlin.reflect.KFunction2
import ch.tutteli.atrium.api.infix.en_GB.o

//TODO remove with 1.0.0, no need to migrate to Spek 2
class IterableContainsInAnyOrderAtLeast1EntriesAssertionsSpec : Spek({
    include(BuilderSpec)
    include(ShortcutSpec)
}) {
    object BuilderSpec : ch.tutteli.atrium.spec.integration.IterableContainsInAnyOrderAtLeast1EntriesAssertionsSpec(
        AssertionVerbFactory,
        getContainsPair(),
        getContainsNullablePair(),
        "◆ ",
        "[Atrium][Builder] "
    )

    object ShortcutSpec : ch.tutteli.atrium.spec.integration.IterableContainsInAnyOrderAtLeast1EntriesAssertionsSpec(
        AssertionVerbFactory,
        getContainsShortcutPair(),
        getContainsNullableShortcutPair(),
        "◆ ",
        "[Atrium][Shortcut] "
    )

    companion object : IterableContainsSpecBase() {
        fun getContainsPair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderEntries" to Companion::containsInAnyOrderEntries

        private fun containsInAnyOrderEntries(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit, aX: Array<out Assert<Double>.() -> Unit>): Assert<Iterable<Double>> {
            return if (aX.isEmpty()) {
                (plant.asExpect().contains(o) inAny order).atLeast(1) entry a
            } else {
                plant.asExpect().contains(o) inAny order atLeast 1 the Entries(a, *aX)
            }
        }

        fun getContainsNullablePair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderEntries" to Companion::containsNullableEntries

        private fun containsNullableEntries(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?, aX: Array<out (Assert<Double>.() -> Unit)?>): Assert<Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(o) inAny order atLeast 1 entry a
            } else {
                plant.asExpect().contains(o) inAny order atLeast 1 the Entries(a, *aX)
            }
        }


        private val containsShortcutFun : KFunction2<Assert<Iterable<Double>>, Assert<Double>.() -> Unit, Assert<Iterable<Double>>> = Assert<Iterable<Double>>::contains
        fun getContainsShortcutPair() = containsShortcutFun.name to Companion::containsInAnyOrderEntriesShortcut

        private fun containsInAnyOrderEntriesShortcut(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit, aX: Array<out Assert<Double>.() -> Unit>): Assert<Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(asSubExpect(a)).asAssert()
            } else {
                plant.asExpect().contains<Any?, Iterable<Double>>(Entries(a, *aX)).asAssert()
            }
        }

        private val containsEntriesFun: KFunction2<Assert<Iterable<Double?>>, Entries<Double>, Assert<Iterable<Double?>>> = Assert<Iterable<Double?>>::contains
        fun getContainsNullableShortcutPair() = containsEntriesFun.name to Companion::containsNullableEntriesShortcut

        private fun containsNullableEntriesShortcut(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?, aX: Array<out (Assert<Double>.() -> Unit)?>): Assert<Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.asExpect().contains(asSubExpect(a)).asAssert()
            } else {
                plant.asExpect().contains(Entries(a, *aX)).asAssert()
            }
        }
    }
}
