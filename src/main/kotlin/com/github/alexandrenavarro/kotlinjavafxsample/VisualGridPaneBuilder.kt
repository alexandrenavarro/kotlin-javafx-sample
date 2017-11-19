package com.github.alexandrenavarro.kotlinjavafxsample

import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import mu.KLogging
import kotlin.reflect.KType
import kotlin.reflect.full.*


// TODO parent or varargs node
class VisualGridPaneBuilder(parent: Any, config: String) {

    companion object : KLogging()


    init {
        var modifiedConfig = config
        logger.info { "config before $modifiedConfig" }
        parent.javaClass.kotlin.memberProperties.forEach {
            val value = it.get(parent)
            if (it.returnType.isSubtypeOf(Node::class.createType())) {
                logger.info { "memberProperties:${it.toString()}" }
                modifiedConfig = modifiedConfig.replace(value.toString(), "$" + it.name)
            }
        }

        logger.info { "\n" +
                "$modifiedConfig" }

        modifiedConfig.lines().forEach {
            val list = it.trim().split(" +".toRegex())
            logger.info { list.size }
        }

    }

    fun build() : GridPane {
        return GridPane()
    }
}