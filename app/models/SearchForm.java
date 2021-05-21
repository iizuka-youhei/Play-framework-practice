package models;

import play.data.validation.Constraints.Required;

public class SearchForm {

    @Required(message="検索キーワードを入力してください")
    protected String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}