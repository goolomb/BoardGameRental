/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Goolomb
 */
class ComboModel extends DefaultComboBoxModel  
{  
    DateFormat df;  
    Calendar calendar;  
    //int size;  
    int days;
   
    public ComboModel(int days, int day, int month, int year)  
    {  
        this.days = days;
        df = new SimpleDateFormat("dd.MM.yyyy");  
        calendar = Calendar.getInstance();  
        init(day, month, year);  
    }  
   
    private void init(int day, int month, int year)  
    {  
        calendar.set(year, month, day);  
        String s;  
    //    size = 0;  
        for(int i = 0; i < days; i++)  
        {  
            s = df.format(calendar.getTime());  
            calendar.add(Calendar.DAY_OF_MONTH, 1);  
            addElement(s);  
     //       size++;  
        }  
    }  
   
    /*public int getSize()  
    {  
        return size;  
    }  
   
    public void reset(int day, int month, int year)  
    {  
        removeAllElements();  
        init(day, month, year);  
        fireContentsChanged(this, 0, getSize() - 1);  
    } */ 
}  