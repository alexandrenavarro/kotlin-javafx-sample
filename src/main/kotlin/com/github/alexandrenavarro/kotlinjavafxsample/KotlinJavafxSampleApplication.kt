package com.github.alexandrenavarro.kotlinjavafxsample

import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.javafx.VisualGridPaneBuilder
import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import mu.KLogging


//import org.springframework.boot.runApplication

//@SpringBootApplication
class KotlinJavafxSampleApplication : Application() {

    val firstNameLabel = Label().apply {
        id = "firstNameLabel"
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

    val middleNameLabel = Label().apply {
        id = "firstNameLabel"
        text = "Middle Name"
    }

    val middleNameTextField = TextField().apply {
        //id = "lastNameTextField"
    }

    val textArea = TextArea().apply {

    }

    val cancelButton = Button().apply {
        text = "Cancel"
    }

    val tableView = TableView<String>().apply {

    }

    val okButton = Button().apply {
        id = "okButton"
        text = "Ok"
    }


    override fun start(primaryStage: Stage?) {


        val gridPane = GridPane()
        val test = with(gridPane) {
            add(firstNameLabel, 0, 0)
            add(firstNameTextField, 1, 0)
            add(lastNameLabel, 0, 1)
            add(lastNameTextField, 1, 1)
            add(cancelButton, 0, 2)
            add(okButton, 1, 2)
        }




        val gridPane1 = VisualGridPaneBuilder(
                """>$firstNameLabel   <$firstNameTextField
                         >$lastNameLabel    <$lastNameTextField
                         >$okButton,$cancelButton                """,
                this
        ).build()


        val scene = Scene(gridPane1, 800.0, 600.0)
        primaryStage?.setScene(scene)
        primaryStage?.show()
    }


    companion object : KLogging() {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(KotlinJavafxSampleApplication::class.java)
        }
    }

}


