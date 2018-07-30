/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR.chart;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author njau_
 */
public class CombinedCategoryPlot extends JFrame{
    static class MyDemoPanel extends DemoPanel implements ChangeListener{
        
        JScrollBar scroller;
        SlidingCategoryDataset dataset1;
        SlidingCategoryDataset dataset2;
        DefaultCategoryDataset defaultcategorydataset1;
        DefaultCategoryDataset defaultcategorydataset2;
        Integer sum=0;//总的数据个数
        Integer range=0;//数据最大值
        
        //将数据库所有内容一次性读入
        private void createDataset(){
            defaultcategorydataset1 = new DefaultCategoryDataset();
            defaultcategorydataset2 = new DefaultCategoryDataset();
            try{
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection conn=DriverManager.getConnection("jdbc:derby:db/TestDB");
                System.out.println("建立连接");
                Statement statement = conn.createStatement();
                String sql="select * from sum_table";
                ResultSet resultset = statement.executeQuery(sql);
                while(resultset.next()){
                    int sum_success=resultset.getInt("sum_success");
                    int sum_fail=resultset.getInt("sum_fail");
                    if(range<sum_success)
                        range=sum_success;
                    if(range<sum_fail)
                        range=sum_fail;
                    defaultcategorydataset1.addValue(sum_success, "success", resultset.getString("date"));
                    defaultcategorydataset1.addValue(sum_fail, "fail", resultset.getString("date"));
                    defaultcategorydataset2.addValue(((double)sum_success/(sum_success+sum_fail)), "rate", resultset.getString("date"));
                    sum++; 
                }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            } 
        }
        
        @Override
        public void stateChanged(ChangeEvent changeevent){
            dataset1.setFirstCategoryIndex(scroller.getValue());
            dataset2.setFirstCategoryIndex(scroller.getValue());
        }
        
        public MyDemoPanel(){
            super(new BorderLayout());
            createDataset();
            dataset1 = new SlidingCategoryDataset(defaultcategorydataset1, sum-10, sum);
            dataset1.setMaximumCategoryCount(10);
            dataset2 = new SlidingCategoryDataset(defaultcategorydataset2, sum-10, sum);
            dataset2.setMaximumCategoryCount(10);
                    
            NumberAxis rangeAxis1 = new NumberAxis("件数");
            rangeAxis1.setRange(0, range);
            rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            BarRenderer renderer1 = new BarRenderer();
            renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
            CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
            subplot1.setDomainGridlinesVisible(true);
            
            NumberAxis rangeAxis2 = new NumberAxis("合格率");
            DecimalFormat df = new DecimalFormat("0.00%"); 
            rangeAxis2.setNumberFormatOverride(df);
            rangeAxis2.setRange(0, 1);
            rangeAxis2.setStandardTickUnits(NumberAxis.createStandardTickUnits());
            LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
            renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
            CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
            subplot2.setDomainGridlinesVisible(true);
            
            CategoryAxis domainAxis = new CategoryAxis("Category");
            CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
            plot.add(subplot1, 1);
            plot.add(subplot2, 1);
            
            JFreeChart jfreechart = new JFreeChart(
                "统计图",
                new Font("SansSerif", Font.BOLD, 12),
                plot,
                true
                );
            
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(1200, 800));
            scroller = new JScrollBar(JScrollBar.HORIZONTAL, sum-10, 10, 0, sum);//参数分别为:方向，初始位置，最小值，最大值(需要修改）
                add(chartpanel);
                scroller.getModel().addChangeListener(this);
                JPanel jpanel = new JPanel(new BorderLayout());
                jpanel.add(scroller);
                jpanel.setBorder(BorderFactory.createEmptyBorder(66, 2, 2, 2));
                jpanel.setBackground(Color.white);
                add(jpanel, "South");
        }
    }
    
    public CombinedCategoryPlot(String string){
        super(string);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(createDemoPanel());
    }
    
    
    public JPanel createDemoPanel(){ 
        return new MyDemoPanel();
    }
    
    public static void createCombinedCategoryPlot(String string){
        CombinedCategoryPlot demo = new CombinedCategoryPlot(string);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}   