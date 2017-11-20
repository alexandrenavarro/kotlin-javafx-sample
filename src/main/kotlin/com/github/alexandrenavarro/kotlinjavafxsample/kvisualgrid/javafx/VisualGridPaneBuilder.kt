package com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.javafx

import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.core.*
import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.Node
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import mu.KLogging
import kotlin.reflect.full.*


// TODO parent or map node
class VisualGridPaneBuilder(config: String, parent: Any) {

    companion object : KLogging()

    private val gridPane: GridPane = GridPane()

    init {
        var modifiedConfig = config
        logger.info { "config before $modifiedConfig" }
        val nameToControlMap = HashMap<String, Node>()

        // TODO use 2 constructors

        // Parent
        parent.let {
            parent.javaClass.kotlin.memberProperties.forEach {
                if (it.returnType.isSubtypeOf(Node::class.createType())) {
                    val value = it.get(parent)
                    if (value is Node) {
                        nameToControlMap[it.name] = value
                    }
                    modifiedConfig = modifiedConfig.replace(value.toString(), "$" + it.name)
                }
            }
        }

        // Map object

        logger.info {
            "\n" +
                    "$modifiedConfig"
        }

        //TODO parsing rows / columns
        modifiedConfig.lines().forEach {
            val list = it.trim().split(" +".toRegex())
        }

        // TODO parse with grammar

        val layoutCells = arrayOf(
                LayoutCell(0, 0, 1, 1, arrayOf(ControlConstraint("firstNameLabel"))),
                LayoutCell(1, 0, 1, 1, arrayOf(ControlConstraint("firstNameTextField", width = Width.MAX))),
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
                val node = nameToControlMap[controlConstraint.controlName]
                if (node != null) {
                    setConstraintsOnNode(controlConstraint, node)
                    gridPane.add(node, layoutCell.columnIndex, layoutCell.rowIndex, layoutCell.columnSpan, layoutCell.rowSpan)
                } else {
                    logger.warn { "Impossible to find ${controlConstraint.controlName} in the map" }
                }
            } else if (layoutCell.controlConstraints.size > 1) {
                val flowPane = FlowPane()
                layoutCell.controlConstraints.forEach { controlConstraint ->
                    val node = nameToControlMap[controlConstraint.controlName]
                    if (node != null) {
                        setConstraintsOnNode(controlConstraint, node)
                        flowPane.children.add(node)
                    } else {
                        logger.warn { "Impossible to find ${controlConstraint.controlName} in the map" }
                    }
                }
                gridPane.add(flowPane, layoutCell.columnIndex, layoutCell.rowIndex, layoutCell.columnSpan, layoutCell.rowSpan)
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
        when (controlConstraint.width) {
            Width.MAX -> GridPane.setHgrow(node, Priority.ALWAYS)
            Width.PREF -> GridPane.setHgrow(node, Priority.SOMETIMES)
            Width.MIN -> GridPane.setHgrow(node, Priority.NEVER)
        }
    }

    fun build(): GridPane = this.gridPane
}