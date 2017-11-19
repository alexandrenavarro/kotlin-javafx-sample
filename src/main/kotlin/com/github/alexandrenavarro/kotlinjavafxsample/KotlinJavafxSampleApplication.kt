package com.github.alexandrenavarro.kotlinjavafxsample

import javafx.application.Application
import javafx.scene.Node
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import mu.KLogging


//import org.springframework.boot.runApplication

//@SpringBootApplication
class KotlinJavafxSampleApplication : Application() {

    override fun start(primaryStage: Stage?) {

        val firstNameLabel = Label().apply {
            //id = "firstNameLabel"
            text = "First Name"
        }


        val firstNameTextField = TextField().apply {
            //id = "firstNameTextField"
        }

        val lastNameLabel = Label().apply {
            //id = "lastNameLabel"
            text = "Last Name"
        }

        val lastNameTextField = TextField().apply {
            //id = "lastNameTextField"
        }

        val cancelButton = Button().apply {
            text = "Cancel"
        }
        val okButton = Button().apply {
            id = "okButton"
            text = "Ok"
        }

        logger.info { ">$firstNameLabel   <$firstNameTextField" }

        val gridPane = GridPane()
        val test = with(gridPane) {
            add(firstNameLabel, 0, 0)
            add(firstNameTextField, 1, 0)
            add(lastNameLabel, 0, 1)
            add(lastNameTextField, 1, 1)
            add(cancelButton, 0, 2)
            add(okButton, 1, 2)
        }

        val gridPaneBuilder = GridPaneBuilder(arrayOf(firstNameLabel),
                "",
                "");


        //createGridPane(firstNameLabel, firstNameTextField)

        val scene = Scene(gridPane, 800.0, 600.0)
        primaryStage?.setScene(scene)
        primaryStage?.show()
    }

    fun createGridPane(vararg args: Node): GridPane {
        val gridPane = GridPane()
        val parameters = KotlinJavafxSampleApplication::createGridPane.parameters
        logger.info { parameters }
        return gridPane
    }

    companion object : KLogging() {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(KotlinJavafxSampleApplication::class.java)
        }
    }

}


