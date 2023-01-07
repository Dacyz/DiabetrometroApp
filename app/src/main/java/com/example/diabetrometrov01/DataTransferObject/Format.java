package com.example.diabetrometrov01.DataTransferObject;

import android.annotation.SuppressLint;
import android.icu.text.UFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class Format {
    
    /*
        To Make Format at Date:  https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        To Make Format at Decimal: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
    */
    private final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.UK);

    private final SimpleDateFormat FormatDate  = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat FormatTime  = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat FormatTimeS  = new SimpleDateFormat("HH:mm");
    private final DateTimeFormatter FormatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter FormatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DecimalFormat FormatDecimals = new DecimalFormat("#.##",otherSymbols);
    private final DecimalFormat FormatMoney = new DecimalFormat("S/ 00.00");

    public String ApplyFormat(LocalDate LOCALDATE){
        return FormatLocalDate.format(LOCALDATE);
    }
    
    public String ApplyFormat(LocalDateTime LOCALDATETIME){
        return FormatDateTime.format(LOCALDATETIME);
    }

    public String ApplyFormat(Time TIME){
        return FormatTime.format(TIME);
    }

    public String ApplyFormatS(Time TIME){
        return FormatTimeS.format(TIME);
    }
    
    public String ApplyFormat(float FLOAT){
        return FormatDecimals.format(FLOAT);
    }
    
    public String ApplyFormat(int INT){
        return FormatDecimals.format(INT);
    }
    
    public String ApplyFormat(double DOUBLE){
        return FormatMoney.format(DOUBLE);
    }
    
    public String ApplyFormat(Date DATE){
        return FormatDate.format(DATE);
    }
    
    public int getRandomNumber(int MAX){
        return new Random().nextInt(MAX + 1) + 1;
    }
    
}
