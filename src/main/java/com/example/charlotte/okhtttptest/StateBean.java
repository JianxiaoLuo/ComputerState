package com.example.charlotte.okhtttptest;

import java.io.Serializable;
import java.text.NumberFormat;

public class StateBean implements Serializable{
    static NumberFormat numberFormat = NumberFormat.getInstance();

    public StateBean(){

    }

    /** 可使用内存. */
    private long totalMemory;

    /** 已使用内存. */
    private long usedMemory;

    /** 内存使用率. */
    private double usedMemPre;

    /** cpu使用率. */
    private double cpuRatio;

    /** 磁盘总大小. */
    private long totalFile;

    /** 磁盘使用量. */
    private long usedFile;

    /** 磁盘使用率. */

    private double usedFilePre;

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;

    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
        System.out.println("--------------USED CPU:"+cpuRatio);
    }

    public double getTotalFile() {
        return totalFile;
    }

    public void setTotalFile(long totalFile) {
        this.totalFile = totalFile;

    }

    public double getUsedFile() {
        return usedFile;

    }

    public void setUsedFile(long usedFile) {
        this.usedFile = usedFile;
        this.usedFilePre = (double)this.usedFile / (double)this.totalFile;
        System.out.println("--------------USED FILE:"+usedFilePre);
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
        this.usedMemPre = (double)this.usedMemory / (double)this.totalMemory;
        System.out.println("--------------USED MEMORY:"+usedMemPre);
    }

    public double getUsedFilePre() {
        return usedFilePre;
    }

    public double getUsedMemPre() {

        return usedMemPre;
    }

    public static NumberFormat getNumberFormat() {

        return numberFormat;
    }

    @Override
    public String toString() {
        return "State [usedMemPre=" + usedMemPre + ", cpuRatio=" + cpuRatio + ", usedFilePre=" + usedFilePre + "]";
    }


}
