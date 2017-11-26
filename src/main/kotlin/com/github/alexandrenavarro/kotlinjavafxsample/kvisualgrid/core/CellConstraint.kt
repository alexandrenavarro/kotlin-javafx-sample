package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core

class CellConstraint(
        val columnIndex: Int,
        val rowIndex: Int,
        val columnSpan: Int = 1,
        val rowSpan: Int = 1,
        vararg val controlConstraints: ControlConstraint = emptyArray())