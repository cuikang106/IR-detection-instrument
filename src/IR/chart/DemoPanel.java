/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR.chart;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;

public class DemoPanel extends JPanel
{

	List charts;

	public DemoPanel(java.awt.LayoutManager layoutmanager)
	{
		super(layoutmanager);
		charts = new ArrayList();
	}

	public void addChart(JFreeChart jfreechart)
	{
		charts.add(jfreechart);
	}

	public JFreeChart[] getCharts()
	{
		int i = charts.size();
		JFreeChart ajfreechart[] = new JFreeChart[i];
		for (int j = 0; j < i; j++)
			ajfreechart[j] = (JFreeChart)charts.get(j);

		return ajfreechart;
	}
}
