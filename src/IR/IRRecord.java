/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author njau_
 */
public class IRRecord {
    private Boolean successOrFail;
    private String time;
    private String date;
    
    public IRRecord(Boolean SuccessOrFail){
        this.successOrFail = SuccessOrFail;
        Date date=new Date();
        this.time=String.format("%tT", date);
        this.date=String.format("%tF", date);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        return  "合格：" + successOrFail + ", 时间：" + time + ", 日期：" + date ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IRRecord other = (IRRecord) obj;
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.successOrFail, other.successOrFail)) {
            return false;
        }
        return true;
    }
}
