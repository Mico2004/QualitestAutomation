package com.automation.main.report.content;

import java.util.List;

/**
 * Created by Lenovo on 01/06/2017.
 */
public class DropBoxImpl implements DropBox {

    private String boxName;
    private List<String>listOfContent;

    @Override
    public String getBoxName() {
        return boxName;
    }

    @Override
    public List<String> getListOfContent() {
        return listOfContent;
    }

    @Override
    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    @Override
    public void setListOfContent(List<String> listOfContent) {
        this.listOfContent = listOfContent;
    }
}
