package com.revature.map;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MostCurrentGraduationMapper extends Mapper<Object, Text, Text, DoubleWritable> {
	
	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String record = value.toString();
		String[] parts = record.split("\",\"");
				
		if (parts[2].equals("Gross Graduation ratio, tertiary, female")) {
			String country = parts[0];
			country = country.substring(1, country.length());
			Double gradPercentage = -1.0;
			for (int i = parts.length; i > 2; i--) {
				try {
					gradPercentage = Double.parseDouble(parts[i]);
				} catch(NumberFormatException e){
					continue;
				}
			}
			context.write(new Text(country), new DoubleWritable(gradPercentage));
		}
	}
}
