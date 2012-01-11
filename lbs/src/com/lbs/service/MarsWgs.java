package com.lbs.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbs.client.GPSEntity;

public class MarsWgs {
	double[] TableX = new double[660 * 450];
	double[] TableY = new double[660 * 450];
	private Boolean initTable = false;

	private static MarsWgs theInstance = new MarsWgs();
	
	private MarsWgs(){
		
	}
	
	public static MarsWgs instance(){
		return theInstance;
	}
	
	public void init() throws Exception{
		this.ReadText();
	}
	
	public GPSEntity rectify(double x, double y) {
		int i, j, k;
		double x1, y1, x2, y2, x3, y3, x4, y4, xtry, ytry, dx, dy;
		double t, u;
		GPSEntity gps = new GPSEntity();

		if (!initTable)
			return null;

		xtry = x;
		ytry = y;

		for (k = 0; k < 10; ++k) {
			// 只对中国国境内数据转捄1�7
			if (xtry < 72 || xtry > 137.9 || ytry < 10 || ytry > 54.9) {
				gps.setX(x);
				gps.setY(y);
				return gps;
			}

			i = (int) ((xtry - 72.0) * 10.0);
			j = (int) ((ytry - 10.0) * 10.0);

			x1 = TableX[GetID(i, j)];
			y1 = TableY[GetID(i, j)];
			x2 = TableX[GetID(i + 1, j)];
			y2 = TableY[GetID(i + 1, j)];
			x3 = TableX[GetID(i + 1, j + 1)];
			y3 = TableY[GetID(i + 1, j + 1)];
			x4 = TableX[GetID(i, j + 1)];
			y4 = TableY[GetID(i, j + 1)];

			t = (xtry - 72.0 - 0.1 * i) * 10.0;
			u = (ytry - 10.0 - 0.1 * j) * 10.0;

			dx = (1.0 - t) * (1.0 - u) * x1 + t * (1.0 - u) * x2 + t * u * x3
					+ (1.0 - t) * u * x4 - xtry;
			dy = (1.0 - t) * (1.0 - u) * y1 + t * (1.0 - u) * y2 + t * u * y3
					+ (1.0 - t) * u * y4 - ytry;

			xtry = (xtry + x - dx) / 2.0;
			ytry = (ytry + y - dy) / 2.0;
		}

		gps.setX(xtry);
		gps.setY(ytry);

		return gps;
	}

	private int GetID(int I, int J) {
		return I + 660 * J;
	}

	private void ReadText() throws Exception {
		String s = "";

		s = readFile("Mars2Wgs.txt");
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(s);
		int i = 0;
		while (m.find()) {
			String v = m.group();
			if (i % 2 == 0)
				TableX[i / 2] = Double.parseDouble(v) / 100000.0;
			else
				TableY[(i - 1) / 2] = Double.parseDouble(v) / 100000.0;
			i++;
		}
		initTable = true;

	}

	private String readFile(String path) throws Exception {
		File file = null;
		BufferedReader br = null;
		StringBuffer buffer = null;

		file = new File(path);
		buffer = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(
				new FileInputStream(file), "utf-8");
		br = new BufferedReader(isr);
		int s;
		while ((s = br.read()) != -1) {
			buffer.append((char) s);
		}
		String retData = buffer.toString();
		return retData;

	}

}
