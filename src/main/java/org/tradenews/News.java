package org.tradenews;

import javafx.scene.control.Button;

import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class News
{
    private String time;
    private String title;
    private String description;
    private String url;
    private Button btn;

    public News()
    {}

    public News(String time, String title, String description, String url)
    {
        this.time = time;
        this.title = title;
        this.description = description;
        this.url = url;
        this.btn = new Button("Link");

        btn.setOnAction(actionEvent ->
        {
            try {
                Desktop.getDesktop().browse(new URL(getUrl()).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public String getTime()
    {
        return time;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUrl()
    {
        return url;
    }

    public Button getBtn() { return btn;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return time.equals(news.time) && title.equals(news.title) && description.equals(news.description) && url.equals(news.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, title, description, url);
    }
}
