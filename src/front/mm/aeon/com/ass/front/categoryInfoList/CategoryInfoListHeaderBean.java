/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.categoryInfoList;

import java.io.Serializable;

public class CategoryInfoListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2082775657522996710L;

    private Integer categoryId;
    private String categoryEng;
    private String categoryMyn;
    private String description;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryEng() {
        return categoryEng;
    }

    public void setCategoryEng(String categoryEng) {
        this.categoryEng = categoryEng;
    }

    public String getCategoryMyn() {
        return categoryMyn;
    }

    public void setCategoryMyn(String categoryMyn) {
        this.categoryMyn = categoryMyn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
