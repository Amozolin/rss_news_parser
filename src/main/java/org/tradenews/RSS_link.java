package org.tradenews;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.HashMap;

public class RSS_link
{
    private String link;
    private Button btn;

    public RSS_link()
    {}

    public RSS_link(String link, HashMap<String, String> rss_links, TableView table)
    {
        this.link = link;
        this.btn = new Button("Delete");

        btn.setOnAction(actionEvent ->
        {
            rss_links.remove(link);
            table.getItems().remove(this);
        });
    }

    public String getLink()
    {
        return link;
    }

    public Button getBtn()
    {
        return btn;
    }
}
