package ru.medyannikov.mypyatnashka4.data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Record implements Comparable<Record>{
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName()+"    "+ getTime()+" | "+getMovement();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	private long id;
	private String time;
	private int movement;
	private String name;
	public Record() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Record another) {
		// TODO Auto-generated method stub
		int res = 0;
		
		
		SimpleDateFormat  format = new SimpleDateFormat("mm:ss"); 
		try {
			if (format.parse(this.getTime()).compareTo(format.parse(another.getTime())) == 1)
			{
				res = 1;
				
			}
			else
				{	
				    res = -1;
				}
		} catch (ParseException e) {
			
		}
		System.out.print(res);
		return res;
	}
	
}
