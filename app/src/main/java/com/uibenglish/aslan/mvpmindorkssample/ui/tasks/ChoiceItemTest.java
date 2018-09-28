package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

public class ChoiceItemTest {

    private int id;
    private String title;
    private int logo;
    private String subTitle;
    private int section;

    public ChoiceItemTest(int id, String title, int logo, String subTitle, int section) {
        this.id = id;
        this.title = title;
        this.logo = logo;
        this.subTitle = subTitle;
        this.section = section;

    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
