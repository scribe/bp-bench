/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */

package com.spectralogic.bp.bench.cli

import io.kotlintest.data.forall
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row

class SizeUnits_Test : ShouldSpec({
    should("have a power") {
        forall(
            row("b", 0.0),
            row("B", 0.0),
            row("B ", 0.0),
            row("kb", 3.0),
            row("Mb", 6.0),
            row("  GB  ", 9.0),
            row("TB", 12.0)
        ) { name: String, power: Double ->
            SizeUnits.parse(name).power shouldBe power
        }
    }

    should("provide a list of names") {
        SizeUnits.names().toList().shouldContainExactlyInAnyOrder("B", "KB", "MB", "GB", "TB")
    }
})