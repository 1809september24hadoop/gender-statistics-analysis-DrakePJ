package com.revature;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.revature.map.MostCurrentGraduationMapper;
import com.revature.reduce.PercentageCheckReducer;

public class QuestionOne {

	public static void main(String[] args) throws Exception{
		if(args.length != 2){
			System.out.printf("Usage: WordCount <input dir> <output dir>\n");
			System.exit(-1);
		}
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Country female graduation rates under 30%");
		
		job.setJarByClass(QuestionOne.class);
		
		job.setJobName("Word Count");
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(MostCurrentGraduationMapper.class);
		job.setReducerClass(PercentageCheckReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		Path outputPath = new Path(args[2]);
		
		FileOutputFormat.setOutputPath(job, outputPath);

		outputPath.getFileSystem(conf);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
