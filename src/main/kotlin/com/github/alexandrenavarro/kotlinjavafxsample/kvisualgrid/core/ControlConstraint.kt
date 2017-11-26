package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core

class ControlConstraint (
        val controlName: String,
        val verticalAlign: VerticalAlign = VerticalAlign.DEFAULT,
        val horizontalAlign: HorizontalAlign = HorizontalAlign.DEFAULT,
        val verticalSize: VerticalSize = VerticalSize.DEFAULT,
        val horizontalSize: HorizontalSize = HorizontalSize.DEFAULT,
        val groupButtonId: Int = 0)