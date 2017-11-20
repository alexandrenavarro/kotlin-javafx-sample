package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core

class LayoutCell(
        val columnIndex: Int,
        val rowIndex: Int,
        val columnSpan: Int = 1,
        val rowSpan: Int = 1,
        val controlConstraints: Array<ControlConstraint> = emptyArray()) {


}