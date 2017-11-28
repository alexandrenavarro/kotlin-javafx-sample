package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.javafx

import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core.*
import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.dsl.VisualGridLexer
import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.dsl.VisualGridListener
import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.dsl.VisualGridParser
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.VPos
import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.layout.*
import mu.KLogging
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import kotlin.reflect.full.*


// TODO view or map node
class VisualGridPaneBuilder(private val visualGridDsl: String,
                            private val view: Any = Any(),
                            private val controlMap: MutableMap<String, Node> = HashMap<String, Node>()) {

    companion object : KLogging()

    constructor(config: String, componentMap: MutableMap<String, Node>) : this(config, Any(), componentMap) {}

    private fun setConstraintsOnNode(controlConstraint: ControlConstraint, node: Node?) {
        when (controlConstraint.verticalAlign) {
            VerticalAlign.BOTTOM -> GridPane.setValignment(node, VPos.BOTTOM)
            VerticalAlign.CENTER -> GridPane.setValignment(node, VPos.CENTER)
            VerticalAlign.TOP -> GridPane.setValignment(node, VPos.TOP)
        }
        when (controlConstraint.horizontalAlign) {
            HorizontalAlign.LEFT -> GridPane.setHalignment(node, HPos.LEFT)
            HorizontalAlign.CENTER -> GridPane.setHalignment(node, HPos.CENTER)
            HorizontalAlign.RIGHT -> GridPane.setHalignment(node, HPos.RIGHT)
        }
        when (controlConstraint.verticalSize) {
            VerticalSize.MAX -> GridPane.setVgrow(node, Priority.ALWAYS)
            VerticalSize.PREF -> GridPane.setVgrow(node, Priority.SOMETIMES)
            VerticalSize.MIN -> GridPane.setVgrow(node, Priority.NEVER)
        }
        when (controlConstraint.horizontalSize) {
            HorizontalSize.MAX -> GridPane.setHgrow(node, Priority.ALWAYS)
            HorizontalSize.PREF -> GridPane.setHgrow(node, Priority.SOMETIMES)
            HorizontalSize.MIN -> GridPane.setHgrow(node, Priority.NEVER)
        }
    }

    fun build(): GridPane {
        val gridPane = GridPane()

        // Retrieve properties from the view
        view.javaClass.kotlin.memberProperties.forEach {
            if (it.returnType.isSubtypeOf(Node::class.createType())) {
                val value = it.get(view)
                if (value is Node) {
                    controlMap[it.name] = value
                }
            }
        }

        var transformedVisualGridDsl = visualGridDsl
        logger.debug { "visualGridDsl before transformation $transformedVisualGridDsl" }

        // Replace the toString from component with the original String given
        controlMap.entries.forEach {
            transformedVisualGridDsl = transformedVisualGridDsl.replace(it.value.toString(), "$" + it.key)
        }

        // Map object
        logger.debug { "visualGridDsl after transformation $transformedVisualGridDsl" }

        //TODO parsing rows / columns

        transformedVisualGridDsl.lines().forEachIndexed {
            rowIndex, row ->
            row.trim().split(" +".toRegex()).forEachIndexed {
                columnIndex, cell ->

                var visualGridLexer = VisualGridLexer(ANTLRInputStream(cell))
                var visualGridParser = VisualGridParser(CommonTokenStream(visualGridLexer))
                var rowConstraints = visualGridParser.rowConstraints()

            }
        }

        var rowIndex = 0
        var columnIndex = 0
        val layoutCellList = ArrayList<CellConstraint>()
        for (line in transformedVisualGridDsl.lines()) {
            line.trim().split(" +".toRegex()).forEach {
                // TODO Add
                val columnSpan = 1
                val rowSpan = 1
                val controlName = "name-$columnSpan-$rowSpan"
                val verticalAlign: VerticalAlign = VerticalAlign.DEFAULT
                val horizontalAlign: HorizontalAlign = HorizontalAlign.DEFAULT
                val verticalSize: VerticalSize = VerticalSize.DEFAULT
                val horizontalSize: HorizontalSize = HorizontalSize.DEFAULT
                val groupId = 0;
                // TODO Manage RowSpan
                // TODO Manage MultiControl
                columnIndex += columnSpan
                rowIndex += rowSpan

                layoutCellList.add(CellConstraint(columnIndex, rowIndex, columnSpan, rowSpan, ControlConstraint(controlName, verticalAlign, horizontalAlign, verticalSize, horizontalSize, groupId)))
            }
        }

        val layoutCells = arrayOf(
                CellConstraint(0, 0, controlConstraints = ControlConstraint("firstNameLabel")),
                CellConstraint(1, 0, controlConstraints = ControlConstraint("firstNameTextField")),
                CellConstraint(0, 1, controlConstraints = ControlConstraint("lastNameLabel")),
                CellConstraint(1, 1, controlConstraints = ControlConstraint("lastNameTextField")),
                CellConstraint(0, 2, controlConstraints = ControlConstraint("middleNameLabel")),
                CellConstraint(1, 2, controlConstraints = ControlConstraint("table", horizontalSize = HorizontalSize.MAX, verticalSize = VerticalSize.MAX)),
                CellConstraint(1, 3, controlConstraints = *arrayOf(ControlConstraint("cancelButton"), ControlConstraint("okButton"))))


        //ColumnConstraints()
        // (double minWidth, double prefWidth, double maxWidth, Priority hgrow, HPos halignment, boolean fillWidth)
        // pos > grow < fill

        //RowConstraints()
        // (double minHeight, double prefHeight, double maxHeight, Priority vgrow, VPos valignment, boolean fillHeight)
        // pos _ grow never, sometime, always, fill,   >,100,>,grow,fill,  or   >100<

        // TODO manage global layout
        // TODO manage row layout
        // TODO manage column layout

        for (layoutCell in layoutCells) {
            if (layoutCell.controlConstraints.size == 1) {
                val controlConstraint = layoutCell.controlConstraints[0]
                val node = controlMap[controlConstraint.controlName]
                if (node != null) {
                    setConstraintsOnNode(controlConstraint, node)
                    gridPane.add(node, layoutCell.columnIndex, layoutCell.rowIndex, layoutCell.columnSpan, layoutCell.rowSpan)
                } else {
                    logger.error { "Impossible to find ${controlConstraint.controlName} in the map, it is abnormal, do you ?" }
                }
            } else if (layoutCell.controlConstraints.size > 1) {
                val flowPane = FlowPane()
                val buttonBar = ButtonBar()
                layoutCell.controlConstraints.forEach { controlConstraint ->
                    val node = controlMap[controlConstraint.controlName]
                    if (node != null) {
                        setConstraintsOnNode(controlConstraint, node)
                        //flowPane.children.add(node)
                        buttonBar.buttons.add(node)
                    } else {
                        logger.error { "Impossible to find ${controlConstraint.controlName} in the map" }
                    }
                }
                //gridPane.add(flowPane, layoutCell.columnIndex, layoutCell.rowIndex, layoutCell.columnSpan, layoutCell.rowSpan)

                gridPane.add(buttonBar, layoutCell.columnIndex, layoutCell.rowIndex, layoutCell.columnSpan, layoutCell.rowSpan)

            }
        }

        return gridPane
    }
}


