package com.app.warehouse.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderMethodUtil {

	private static final Logger log = LoggerFactory.getLogger(OrderMethodUtil.class);

	// 1. Generate Pie Chart
	public void generateChartForOrderMethodMode(String path, List<Object[]> data) {
		log.info("Inside generateChartForOrderMethodMode():");

		// 1. prepare Data Source
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object[] ob : data) {
			// key(String)-val(Double)
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}
		// 2. create JFreeChart object
		// Input => title, dataset
		JFreeChart chart = ChartFactory.createPieChart("Order Method Mode", dataset);

		// 3. save as Image
		try {
			ChartUtils.saveChartAsJPEG(new File(path + "/OrderMethodMode_A.jpg"), // file location + name
					chart, // JFreeChart object
					300, // width
					300); // height
		} catch (IOException e) {
			log.error("Exception Inside generateFreeChart() :" + e);
			e.printStackTrace();
		}

	}

	// 2. Generate Bar Chart
	public void generateBarChartForOrderMethodMode(String path, List<Object[]> data) {
		log.info("Inside generateBarChartForOrderMethodMode()");
		// 1. prepare Data Source
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] ob : data) {
			// val(Double) -- key(String)
			dataset.setValue(Double.valueOf(ob[1].toString()), ob[0].toString(), ""// display label
			);
		}

		// 2. create JFreeChart object
		// Input => title, x-axis label, y-axis-label, dataset
		JFreeChart chart = ChartFactory.createBarChart("Order Method Mode", "Mode", "Counts", dataset);

		// 3. save as Image
		try {
			ChartUtils.saveChartAsJPEG(new File(path + "/OrderMethodMode_B.jpg"), // file location + name
					chart, // JFreeChart object
					450, // width
					400); // height
		} catch (IOException e) {
			log.error("Exception inside generateBarChartForOrderMethodMode():" + e);
			e.printStackTrace();
		}

	}
}
