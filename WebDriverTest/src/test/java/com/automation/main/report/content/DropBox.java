package com.automation.main.report.content;

import java.util.List;

/**
 * Created by Lenovo on 01/06/2017.
 */
public interface DropBox {
    String getBoxName();

    List<String> getListOfContent();

    void setBoxName(String boxName);

    void setListOfContent(List<String> listOfContent);
}
