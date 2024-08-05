package org.tradenews;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.List;

public class Keyword_link
{
    private String keyword;
    private Button btn;

    public Keyword_link()
    {}

    public Keyword_link(String keyword, List<String> key_words, TableView table)
    {
        this.keyword = keyword;
        this.btn = new Button("Delete");

        btn.setOnAction(actionEvent ->
        {
            key_words.remove(keyword);
            table.getItems().remove(this);
        });
    }

    public String getKeyword()
    {
        return keyword;
    }

    public Button getBtn()
    {
        return btn;
    }
}
