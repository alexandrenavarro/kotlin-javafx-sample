package com.github.alexandrenavarro.kotlinjavafxsample

import javafx.scene.Node
import javafx.scene.layout.GridPane


class GridPaneBuilder(nodes: Array<Node>, vararg rows: String) {

    fun add(row : String) : GridPaneBuilder {
        return this
    }

    operator fun plusAssign(row : String) {
    }


    fun build() : GridPane {
        return GridPane()
    }
}