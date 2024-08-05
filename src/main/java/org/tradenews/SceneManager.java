package org.tradenews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.List;


public class SceneManager
{
    private Stage stage;

    private Scene rss_scene;
    private Scene keyword_scene;
    private Scene news_view_scene;


    private TableView news_table;

    public SceneManager(Stage stage, HashMap<String, String> rss_links, List<String> key_words)
    {
        this.stage = stage;
        stage.setTitle("Trade News Parser");
        stage.addEventHandler(KeyEvent.KEY_PRESSED,  e ->
        {
            if (e.getCode().getCode() == 27)
            {
                stage.close();
            }
        });
        create_rss_scene(rss_links);
        create_keyword_scene(key_words);
        create_news_view_scene();

        stage.setScene(rss_scene);
        stage.setX(650);
        stage.setY(250);
        stage.show();
    }


    private void create_rss_scene(HashMap<String, String> rss_links)
    {
        //Label
        var label = new Label("Enter RSS Link: ");
        label.setPrefHeight(30);

        //TextField
        var textField = new TextField();
        textField.setPrefSize(400,30);

        // Set Up Table for RSS scene
        var rss_table = new TableView();
        TableColumn<RSS_link,String> rss_column1 = new TableColumn<>("RSS Link");
        rss_column1.setCellValueFactory(new PropertyValueFactory<>("link"));
        rss_column1.setPrefWidth(550);

        TableColumn<RSS_link,Button> rss_column2 = new TableColumn<>("");
        rss_column2.setCellValueFactory(new PropertyValueFactory<>("btn"));
        rss_column2.setPrefWidth(60);
        rss_column2.setMinWidth(60);
        rss_column2.setMaxWidth(60);


        rss_table.getColumns().addAll(rss_column1, rss_column2);
        rss_table.setPrefHeight(1920);

        rss_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        var rss_stackPane = new StackPane(rss_table);

        // Button for getting text
        var btnGetText = new Button("Enter");
        btnGetText.setPrefHeight(30);
        btnGetText.setPrefWidth(70);
        btnGetText.setOnAction(e ->
        {
            var str = textField.getText();

            if(!rss_links.containsKey(str))
            {
                rss_links.put(str, "");
                rss_table.getItems().add(new RSS_link(str, rss_links, rss_table));
            }
            textField.setText("");
        });

        // TextField Box
        var textFieldBox = new HBox(20, label, textField, btnGetText);
        textFieldBox.setAlignment(Pos.BASELINE_CENTER);

        // Root Node
        var root_node = new VBox(20, create_menu_bar(), textFieldBox, rss_stackPane);

        var rss_scene = new Scene(root_node, 640, 480);
        VBox.setMargin(textFieldBox, new Insets(10, 10, 10, 10));
        VBox.setMargin(rss_stackPane, new Insets(10, 10, 10, 10));

        rss_scene.setOnKeyPressed(e ->
        {
            if (e.getCode().getCode() == 10)
            {
                btnGetText.fire();
            }
        });

        this.rss_scene = rss_scene;

    }

    private void create_keyword_scene(List<String> key_words)
    {
        //Label
        var label = new Label("Enter RSS Link: ");
        label.setPrefHeight(30);

        //TextField
        var textField = new TextField();
        textField.setPrefSize(400,30);

        //Table
        var keyword_table = new TableView();
        TableColumn<Keyword_link,String> keyword_column1 = new TableColumn<>("Keyword");
        keyword_column1.setCellValueFactory(new PropertyValueFactory<>("keyword"));
        keyword_column1.setPrefWidth(550);

        TableColumn<Keyword_link,Button> keyword_column2 = new TableColumn<>("");
        keyword_column2.setCellValueFactory(new PropertyValueFactory<>("btn"));
        keyword_column2.setPrefWidth(60);
        keyword_column2.setMaxWidth(60);
        keyword_column2.setMinWidth(60);

        keyword_table.getColumns().addAll(keyword_column1, keyword_column2);
        keyword_table.setPrefHeight(1920);

        keyword_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        var keyword_stackPane = new StackPane(keyword_table);

        var btnGetText = new Button("Enter");
        btnGetText.setPrefHeight(30);
        btnGetText.setPrefWidth(70);
        btnGetText.setOnAction(e ->
        {
            var str = textField.getText();

            if(!key_words.contains(str))
            {
                key_words.add(str);
                keyword_table.getItems().add(new Keyword_link(str,key_words,keyword_table));
            }
            textField.setText("");
        });

        // TextField Box
        var textFieldBox = new HBox(20, label, textField, btnGetText);
        textFieldBox.setAlignment(Pos.BASELINE_CENTER);


        // Root Node
        var root_node = new VBox(20, create_menu_bar(), textFieldBox, keyword_stackPane);

        var keyword_scene = new Scene(root_node, 640, 480);
        VBox.setMargin(textFieldBox, new Insets(10, 10, 10, 10));
        VBox.setMargin(keyword_stackPane, new Insets(10, 10, 10, 10));

        keyword_scene.setOnKeyPressed(e ->
        {
            if (e.getCode().getCode() == 10)
            {
                btnGetText.fire();
            }
        });

        this.keyword_scene = keyword_scene;

    }

    private void create_news_view_scene()
    {
        var table = new TableView();
        TableColumn<News,String> column1 = new TableColumn<>("Time");
        column1.setCellValueFactory(new PropertyValueFactory<>("time"));
        column1.setPrefWidth(200);
        column1.setMinWidth(200);
        column1.setMaxWidth(200);

        TableColumn<News,String> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2.setPrefWidth(950);


        TableColumn<News,Button> column3 = new TableColumn<>("Link");
        column3.setCellValueFactory(new PropertyValueFactory<>("btn"));
        column3.setPrefWidth(50);
        column3.setMinWidth(50);
        column3.setMaxWidth(50);

        table.getColumns().addAll(column1, column2, column3);
        table.setPrefHeight(1920);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        var stackPane = new StackPane(table);

        var root_node = new VBox(20, create_menu_bar(), stackPane);
        var news_view_scene = new Scene(root_node, 1240, 480);
        VBox.setMargin(stackPane, new Insets(10, 10, 10, 10));

        this.news_view_scene = news_view_scene;
        this.news_table = table;
    }

    private HBox create_menu_bar()
    {
        Button button1 = new Button("Add Website");
        button1.setPrefSize(150,30);
        button1.setOnAction(e -> stage.setScene(rss_scene));

        Button button2 = new Button("Add Keyword");
        button2.setPrefSize(150,30);
        button2.setOnAction(e -> stage.setScene(keyword_scene));

        Button button3 = new Button("News View");
        button3.setPrefSize(150,30);
        button3.setOnAction(e -> stage.setScene(news_view_scene));

        HBox menu_bar = new HBox(20,button1,button2,button3);
        menu_bar.setAlignment(Pos.BASELINE_CENTER);
        VBox.setMargin(menu_bar, new Insets(10, 10, 10, 10));

        return menu_bar;
    }

    public TableView getNews_table()
    {
        return news_table;
    }

}
