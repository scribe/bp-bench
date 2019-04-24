/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */

package com.spectralogic.bp.bench.cli

enum class SizeUnits(val power: Double) {
    B(0.0),
    KB(3.0),
    MB(6.0),
    GB(9.0),
    TB(12.0);

    companion object {
        fun parse(name: String) = SizeUnits.valueOf(name.trim().toUpperCase())
        fun names() = SizeUnits.values().map { it.name }.toTypedArray()
    }
}
