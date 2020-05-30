package com.real_estate.lead_grow.fragments_bottom;

class leaddata {
    String heads;

    public leaddata(){
    }

    public leaddata(String heads) {
        this.heads = heads;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads.split(".")[0];
    }
}
