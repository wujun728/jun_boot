package com.youmeek.ssm.pojo;

import java.util.List;

public class Spcolumn {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spcolumn.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spcolumn.spcolumn_en
     *
     * @mbggenerated
     */
    private String spcolumnEn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spcolumn.spcolumn_cn
     *
     * @mbggenerated
     */
    private String spcolumnCn;

    /**
     * 关联的二级标题
     */
    private List<Childcol> childcolList;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spcolumn.id
     *
     * @return the value of spcolumn.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spcolumn.id
     *
     * @param id the value for spcolumn.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spcolumn.spcolumn_en
     *
     * @return the value of spcolumn.spcolumn_en
     *
     * @mbggenerated
     */
    public String getSpcolumnEn() {
        return spcolumnEn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spcolumn.spcolumn_en
     *
     * @param spcolumnEn the value for spcolumn.spcolumn_en
     *
     * @mbggenerated
     */
    public void setSpcolumnEn(String spcolumnEn) {
        this.spcolumnEn = spcolumnEn == null ? null : spcolumnEn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spcolumn.spcolumn_cn
     *
     * @return the value of spcolumn.spcolumn_cn
     *
     * @mbggenerated
     */
    public String getSpcolumnCn() {
        return spcolumnCn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spcolumn.spcolumn_cn
     *
     * @param spcolumnCn the value for spcolumn.spcolumn_cn
     *
     * @mbggenerated
     */
    public void setSpcolumnCn(String spcolumnCn) {
        this.spcolumnCn = spcolumnCn == null ? null : spcolumnCn.trim();
    }

    public List<Childcol> getChildcolList() {
        return childcolList;
    }

    public void setChildcolList(List<Childcol> childcolList) {
        this.childcolList = childcolList;
    }
}