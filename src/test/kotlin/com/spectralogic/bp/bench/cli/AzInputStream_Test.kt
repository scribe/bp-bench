/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */

package com.spectralogic.bp.bench.cli

import io.kotlintest.data.suspend.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row

class AzInputStream_Test : ShouldSpec({
    should("look through the alphabet") {
        forall(
            row(0, 'a'),
            row(1, 'b'),
            row(51, 'z')
        ) { iterations, character ->
            val az = AZInputStream()
            var char = '?'
            for (i in 0..iterations) {
                char = az.read().toChar()
            }
            char shouldBe character
        }
    }
})