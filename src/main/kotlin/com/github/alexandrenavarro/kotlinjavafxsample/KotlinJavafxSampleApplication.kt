package com.github.alexandrenavarro.kotlinjavafxsample

import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.Person
import com.github.alexandrenavarro.kotlinjavafxsample.kvisualgrid.javafx.VisualGridPaneBuilder
import javafx.application.Application
import javafx.geometry.Insets
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import mu.KLogging
import javafx.scene.control.cell.PropertyValueFactory




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

    val table = TableView<Person>().apply {
        val firstNameCol = TableColumn<Person, String>("First Name")
        firstNameCol.cellValueFactory = PropertyValueFactory("firstName")
        val lastNameCol = TableColumn<Person, String>("Last Name")
        lastNameCol.cellValueFactory = PropertyValueFactory("lastName")
        this.columns.setAll(firstNameCol, lastNameCol)
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


        var gridPane = VisualGridPaneBuilder(
                """>$firstNameLabel   <$firstNameTextField
                              >$lastNameLabel    <$lastNameTextField
                              >$okButton,$cancelButton                """,
                this
        ).build()
        gridPane.padding = Insets(4.0, 4.0, 4.0, 4.0)
        for (child in gridPane.children) {
            GridPane.setMargin(child, Insets(4.0, 4.0, 4.0, 4.0))
        }

        val scene = Scene(gridPane, 800.0, 600.0)
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


