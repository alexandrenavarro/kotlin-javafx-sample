package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.javafx

import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core.*
import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.Node
import javafx.scene.control.ButtonBar
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import mu.KLogging
import kotlin.reflect.full.*


// TODO view or map node
class VisualGridPaneBuilder(private val visualGridDsl: String,
                            private val view: Any = Any(),
                            private val controlMap: MutableMap<String, Node> = HashMap<String, Node>()) {

    companion object : KLogging()

    private val gridPane: GridPane = GridPane()

    constructor(config: String, view: Any) : this(config, view, emptyMap<String, Node>() as MutableMap<String, Node>) {}

    constructor(config: String, componentMap: MutableMap<String, Node>) : this(config, Any(), componentMap) {}

    init {
        var transformedVisualGridDsl = visualGridDsl
        logger.debug { "visualGridDsl before transformation $transformedVisualGridDsl" }

        // Retrieve properties from the view
        view.javaClass.kotlin.memberProperties.forEach {
            if (it.returnType.isSubtypeOf(Node::class.createType())) {
                val value = it.get(view)
                if (value is Node) {
                    controlMap[it.name] = value
                }
            }
        }

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

            }
        }

        var rowIndex = 0
        var columnIndex = 0
        val layoutCellList = ArrayList<LayoutCell>()
        for (line in transformedVisualGridDsl.lines()) {
            line.trim().split(" +".toRegex()).forEach {
                // TODO Add
                val columnSpan = 1
                val rowSpan = 1
                val controlName = "name-$columnSpan-$rowSpan"
                val horizontalAlign: HorizontalAlign = HorizontalAlign.DEFAULT
                val verticalAlign: VerticalAlign = VerticalAlign.DEFAULT
                val size: Size = Size.DEFAULT
                // TODO Manage RowSpan
                // TODO Manage MultiControl
                columnIndex += columnSpan
                rowIndex += rowSpan
                layoutCellList.add(LayoutCell(columnIndex, rowIndex, columnSpan, rowSpan, arrayOf(ControlConstraint(controlName, horizontalAlign, verticalAlign, size))))
            }
        }

        val layoutCells = arrayOf(
                LayoutCell(0, 0, 1, 1, arrayOf(ControlConstraint("firstNameLabel"))),
                LayoutCell(1, 0, 1, 1, arrayOf(ControlConstraint("firstNameTextField"))),
                LayoutCell(0, 1, 1, 1, arrayOf(ControlConstraint("lastNameLabel"))),
                LayoutCell(1, 1, 1, 1, arrayOf(ControlConstraint("lastNameTextField"))),
                LayoutCell(0, 2, 1, 1, arrayOf(ControlConstraint("middleNameLabel"))),
                LayoutCell(1, 2, 1, 1, arrayOf(ControlConstraint("textArea"))),
                LayoutCell(1, 3, 1, 1, arrayOf(ControlConstraint("cancelButton"), ControlConstraint("okButton"))))


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
    }

    private fun setConstraintsOnNode(controlConstraint: ControlConstraint, node: Node?) {
        when (controlConstraint.horizontalAlign) {
            HorizontalAlign.LEFT -> GridPane.setHalignment(node, HPos.LEFT)
            HorizontalAlign.CENTER -> GridPane.setHalignment(node, HPos.CENTER)
            HorizontalAlign.RIGHT -> GridPane.setHalignment(node, HPos.RIGHT)
        }
        when (controlConstraint.verticalAlign) {
            VerticalAlign.BOTTOM -> GridPane.setValignment(node, VPos.BOTTOM)
            VerticalAlign.CENTER -> GridPane.setValignment(node, VPos.CENTER)
            VerticalAlign.TOP -> GridPane.setValignment(node, VPos.TOP)
        }
        when (controlConstraint.size) {
            Size.MAX -> GridPane.setHgrow(node, Priority.ALWAYS)
            Size.PREF -> GridPane.setHgrow(node, Priority.SOMETIMES)
            Size.MIN -> GridPane.setHgrow(node, Priority.NEVER)
        }
    }


    fun build(): GridPane = this.gridPane
}