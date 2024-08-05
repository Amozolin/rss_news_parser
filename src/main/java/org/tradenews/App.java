package org.tradenews;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * JavaFX App
 */
/*
enum CurScene
{
    RSS,
    KEYWORD,
    NEWSVIEW
}
*/
public class App extends Application
{
/*
    CurScene openScene = CurScene.RSS;
    @Override
    public void start(Stage stage)
    {

        HashMap<String, String> rss_links = new HashMap<>();

        //rss_links.put("https://www.cnbc.com/id/10000664/device/rss/rss.html", "");
        //rss_links.put("https://www.globenewswire.com/RssFeed/subjectcode/43-Technical%20Analysis/feedTitle/GlobeNewswire%20-%20Technical%20Analysis", "");

        List<String> key_words = new ArrayList<>();

        key_words.add(" ");
        rss_links.put("https://www.cnbc.com/id/10000664/device/rss/rss.html", "");


        stage.setTitle("Trade News Parser");

        //Menu Buttons
        var button1 = new Button("Add Website");
        button1.setPrefSize(150,30);

        var button2 = new Button("Add Keyword");
        button2.setPrefSize(150,30);

        var button3 = new Button("News View");
        button3.setPrefSize(150,30);

        // Menu Bar
        var menuBar = new HBox(20,button1,button2,button3);
        menuBar.setAlignment(Pos.BASELINE_CENTER);

        //Root Node
        var mainVBox = new VBox(20,menuBar);
        VBox.setMargin(menuBar, new Insets(10, 10, 10, 10));

        // Start Scene
        var scene = new Scene(mainVBox, 640, 480);
        stage.setScene(scene);
        stage.setX(650);
        stage.setY(250);
        stage.show();

        //Scenes Set Up for Rss Link and Keyword

        //Label
        var label = new Label();
        label.setPrefHeight(30);

        //TextField
        var textField = new TextField();
        textField.setPrefSize(400,30);

        // Button for getting text
        var btnGetText = new Button("Enter");
        btnGetText.setPrefHeight(30);
        btnGetText.setPrefWidth(70);

        // TextField Box
        var textFieldBox = new HBox(20, label, textField, btnGetText);
        textFieldBox.setAlignment(Pos.BASELINE_CENTER);

        // Set Up Table for RSS scene
        var rss_table = new TableView();
        TableColumn<RSS_link,String> rss_column1 = new TableColumn<>("RSS Link");
        rss_column1.setCellValueFactory(new PropertyValueFactory<>("link"));
        rss_column1.setPrefWidth(550);

        TableColumn<RSS_link,Button> rss_column2 = new TableColumn<>("");
        rss_column2.setCellValueFactory(new PropertyValueFactory<>("btn"));
        rss_column2.setPrefWidth(60);


        rss_table.getColumns().addAll(rss_column1, rss_column2);
        rss_table.setPrefHeight(1920);

        var rss_stackPane = new StackPane(rss_table);

        //Set Up for Keyword Scene
        var keyword_table = new TableView();
        TableColumn<Keyword_link,String> keyword_column1 = new TableColumn<>("Keyword");
        keyword_column1.setCellValueFactory(new PropertyValueFactory<>("keyword"));
        keyword_column1.setPrefWidth(550);

        TableColumn<Keyword_link,Button> keyword_column2 = new TableColumn<>("");
        keyword_column2.setCellValueFactory(new PropertyValueFactory<>("btn"));
        keyword_column2.setPrefWidth(60);


        keyword_table.getColumns().addAll(keyword_column1, keyword_column2);
        keyword_table.setPrefHeight(1920);

        var keyword_stackPane = new StackPane(keyword_table);

        // Set Up for News View Scene
        var table = new TableView();
        TableColumn<News,String> column1 = new TableColumn<>("Time");
        column1.setCellValueFactory(new PropertyValueFactory<>("time"));
        column1.setPrefWidth(200);

        TableColumn<News,String> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2.setPrefWidth(970);

        TableColumn<News,Button> column3 = new TableColumn<>("Link");
        column3.setCellValueFactory(new PropertyValueFactory<>("btn"));
        column3.setPrefWidth(60);

        table.getColumns().addAll(column1, column2, column3);
        table.setPrefHeight(1920);

        var stackPane = new StackPane(table);

        //Sound
        String musicFile = "Sound\\zapsplat_bell_service_desk_press_x3_18039.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        final Boolean[] added = {new Boolean(false)};

        Thread newsUpdates = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(openScene != CurScene.MAIN)
                {
                    for (String rss_link : rss_links.keySet())
                    {
                        try
                        {
                            CloseableHttpClient client = HttpClientBuilder.create().build();
                            HttpGet request = new HttpGet(rss_link);

                            HttpResponse response = client.execute(request);
                            HttpEntity entity = response.getEntity();

                            //int responseCode = response.getStatusLine().getStatusCode();

                            //System.out.println("Request Url: " + request.getURI());
                            //System.out.println("Response Code: " + responseCode);

                            InputStream is = entity.getContent();

                            SyndFeedInput input = new SyndFeedInput();
                            SyndFeed feed = input.build(new XmlReader(is));

                            List<SyndEntry> list  = feed.getEntries();

                            for (SyndEntry entry : list)
                            {
                                String title = entry.getTitle();
                                SyndContent cont = entry.getDescription();
                                String description = cont.getValue();
                                String url = entry.getLink();
                                String time = entry.getPublishedDate().toString();

                                if(rss_links.get(rss_link).equals(title))
                                {
                                    break;
                                }

                                added[0] = false;

                                for(String keyword : key_words)
                                {
                                    if(title.contains(keyword) || description.contains(keyword))
                                    {
                                        if(!table.getItems().contains(new News(time,title,description,url)))
                                        {
                                            table.getItems().add(new News(time,title,description,url));
                                            added[0] = true;
                                        }
                                    }
                                }
                                if (added[0])
                                {
                                    Media sound = new Media(new File(musicFile).toURI().toString());
                                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                                    mediaPlayer.play();
                                }
                            }
                            if(list.size() > 0)
                            {
                                rss_links.put(rss_link,list.get(0).getTitle());
                            }

                        }
                        catch (ClientProtocolException e) { e.printStackTrace(); }
                        catch (UnsupportedOperationException e) { e.printStackTrace(); }
                        catch (IOException e) { e.printStackTrace(); }
                        catch (FeedException e) { e.printStackTrace(); }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        newsUpdates.start();
        // Main functionality

        button1.setOnAction(e ->
        {
            openScene = CurScene.RSS;
            label.setText("Enter RSS Link: ");
            var rss_scene = new Scene(new VBox(20, menuBar, textFieldBox, rss_stackPane), 640, 480);
            VBox.setMargin(textFieldBox, new Insets(10, 10, 10, 10));
            VBox.setMargin(rss_stackPane, new Insets(10, 10, 10, 10));
            stage.setScene(rss_scene);

        });
        button2.setOnAction(e ->
        {
            openScene = CurScene.KEYWORD;
            label.setText("Enter Keyword: ");
            var keywordScene = new Scene(new VBox(20, menuBar, textFieldBox, keyword_stackPane), 640, 480);
            VBox.setMargin(textFieldBox, new Insets(10, 10, 10, 10));
            VBox.setMargin(keyword_stackPane, new Insets(10, 10, 10, 10));
            stage.setScene(keywordScene);
        });
        button3.setOnAction(event ->
        {
            openScene = CurScene.NEWSVIEW;
            var newsViewScene = new Scene(new VBox(20, menuBar, stackPane), 1240, 480);
            VBox.setMargin(stackPane, new Insets(10, 10, 10, 10));
            stage.setScene(newsViewScene);

        });

        btnGetText.setOnAction(e ->
        {
            var str = textField.getText();
            if (openScene == CurScene.RSS)
            {
                if(!rss_links.containsKey(str))
                {
                    rss_table.getItems().add(new RSS_link(str, rss_links, rss_table));
                    rss_links.put(str, "");
                }
                textField.setText("");
            }
            else if (openScene == CurScene.KEYWORD)
            {
                if(!key_words.contains(str))
                {
                    key_words.add(str);
                    keyword_table.getItems().add(new Keyword_link(str,key_words,keyword_table));
                }

                textField.setText("");
            }
            //System.out.println(str);
        });

        stage.setOnCloseRequest(e ->
        {
            openScene = CurScene.MAIN;
        });

        // Respond to key presses
        stage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) ->
        {
            //System.out.println("Key pressed: " + event.toString());

            switch(event.getCode().getCode())
            {
                case 27 :
                { // 27 = ESC key
                    stage.close();
                    openScene = CurScene.MAIN;
                    break;
                }
                case 10 :
                { // 10 = Return
                    var str = textField.getText();
                    if (openScene == CurScene.RSS)
                    {
                        if(!rss_links.containsKey(str))
                        {
                            rss_table.getItems().add(new RSS_link(str, rss_links, rss_table));
                            rss_links.put(str, "");
                        }
                        textField.setText("");
                    }
                    else if (openScene == CurScene.KEYWORD)
                    {
                        if(!key_words.contains(str))
                        {
                            key_words.add(str);
                            keyword_table.getItems().add(new Keyword_link(str,key_words,keyword_table));
                        }
                        textField.setText("");
                    }
                    //System.out.println(str);
                }
                default:
                {
                    //System.out.println("Unrecognized key");
                }
            }
        });
    }
*/
    boolean isOn = true;
    boolean added = false;

    @Override
    public void start(Stage stage)
    {
        HashMap<String, String> rss_links = new HashMap<>();
        List<String> key_words = new ArrayList<>();

        key_words.add(" ");
        //rss_links.put("https://www.cnbc.com/id/10000664/device/rss/rss.html", "");
        rss_links.put("https://www.globenewswire.com/RssFeed/subjectcode/43-Technical%20Analysis/feedTitle/GlobeNewswire%20-%20Technical%20Analysis", "");

        SceneManager scene_manager = new SceneManager(stage, rss_links, key_words);

        String musicFile = "Sound\\zapsplat_bell_service_desk_press_x3_18039.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());

        Thread newsUpdates = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(isOn)
                {
                    for (String rss_link : rss_links.keySet())
                    {
                        try
                        {
                            CloseableHttpClient client = HttpClientBuilder.create().build();
                            HttpGet request = new HttpGet(rss_link);

                            HttpResponse response = client.execute(request);
                            HttpEntity entity = response.getEntity();

                            InputStream is = entity.getContent();

                            SyndFeedInput input = new SyndFeedInput();
                            SyndFeed feed = input.build(new XmlReader(is));

                            List<SyndEntry> list  = feed.getEntries();

                            for (SyndEntry entry : list)
                            {
                                String title = entry.getTitle();
                                SyndContent cont = entry.getDescription();
                                String description = cont.getValue();
                                String url = entry.getLink();
                                String time = entry.getPublishedDate().toString();

                                added = false;

                                if(rss_links.get(rss_link).equals(title))
                                {
                                    break;
                                }

                                for(String keyword : key_words)
                                {
                                    if(title.contains(keyword) || description.contains(keyword))
                                    {
                                        if(!scene_manager.getNews_table().getItems().contains(new News(time,title,description,url)))
                                        {
                                            //scene_manager.getNews_table().getItems().add(new News(time,title,description,url));
                                            scene_manager.getNews_table().getItems().add(0,new News(time,title,description,url));
                                            added = true;
                                        }
                                    }
                                }
                            }
                            if (added)
                            {
                                new MediaPlayer(sound).play();
                            }
                            if(list.size() > 0)
                            {
                                rss_links.put(rss_link,list.get(0).getTitle());
                            }

                        }
                        catch (ClientProtocolException e) { e.printStackTrace(); }
                        catch (UnsupportedOperationException e) { e.printStackTrace(); }
                        catch (IOException e) { e.printStackTrace(); }
                        catch (FeedException e) { e.printStackTrace(); }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        newsUpdates.start();
    }

    @Override
    public void stop()
    {
        isOn = false;
    }


    public static void main(String[] args)
    {
        launch();
    }

}