package com.sky.quickindex;


public class ContactInfo implements Comparable<ContactInfo> {
    private String firstLetter;
    private String name;

    public ContactInfo (String name) {
        this.firstLetter = PinyinUtil.chineseWordToPinyin(name).substring(0, 1);
        this.name = name;
    }

    public String getFirstLetter () {
        return firstLetter;
    }

    public void setFirstLetter (String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "ContactInfo{" +
                "firstLetter='" + firstLetter + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo (ContactInfo another) {
        return this.firstLetter.compareTo(another.firstLetter);
    }
}
