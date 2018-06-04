package com.example.charlotte.okhtttptest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类用来显示图标，使用的是SmartChart中的pie chart
 * @author Charlotte
 */
public class PieChartActivity extends AppCompatActivity {

    private PieChart cpuChart, memChart, fileChart;
    private StateBean stateBean;
    private double usedMemPre;
    private double cpuRatio;
    private double usedFilePre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);


        //接收MainActivity传过来的信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        usedMemPre = bundle.getDouble("usedMemPre");
        cpuRatio = bundle.getDouble("cpuRatio");
        usedFilePre = bundle.getDouble("usedFilePre");

        Logger.i("usedMemPre="+usedMemPre+"   cpuRatio"+cpuRatio+"   usedFilePre"+usedFilePre);


        //修改数据格式，只保留两位小数
        BigDecimal bg = new BigDecimal(usedMemPre);
        double mem = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        bg = new BigDecimal(1-mem);
        double mem1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        bg = new BigDecimal(cpuRatio);
        double cpu = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        bg = new BigDecimal(1-cpu);
        double cpu1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        bg = new BigDecimal(usedFilePre);
        double file = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        bg = new BigDecimal(1-file);
        double file1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        //获取三个piechart
        cpuChart = (PieChart) findViewById(R.id.cpuChart);
        memChart = (PieChart) findViewById(R.id.memChart);
        fileChart = (PieChart) findViewById(R.id.fileChart);


        //第一个PieChart部分 CPU，完全参考开源项目SmartChart
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> cpuYDataList = new ArrayList<>();
        cpuYDataList.add("USED");
        cpuYDataList.add("FREE");

        List<PieData> cpuDatas = new ArrayList<>();

        PieData cpuData1 = new PieData("USED", "%", getResources().getColor(R.color.arc1), cpu);
        PieData cpuData2 = new PieData("FREE", "%", getResources().getColor(R.color.arc2), cpu1);
        cpuDatas.add(cpuData1);
        cpuDatas.add(cpuData2);
        ChartData<PieData> cpuChartData = new ChartData<>("CPU",cpuYDataList,cpuDatas);
        cpuChart.setShowChartName(true);

        //设置标题样式
        FontStyle cpuFontStyle = cpuChart.getChartTitle().getFontStyle();
        cpuFontStyle.setTextColor(res.getColor(R.color.arc3));
        cpuFontStyle.setTextSpSize(this,15);
        cpuChart.getProvider().setOpenMark(true);
        cpuChart.getProvider().setMarkView(new BubbleMarkView(this));
        LegendPoint legendPoint = (LegendPoint)cpuChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.CIRCLE);
        cpuChart.getLegend().setDirection(IComponent.TOP);
        cpuChart.setRotate(true);
        cpuChart.setChartData(cpuChartData);
        cpuChart.startChartAnim(1000);
        cpuChart.setOnClickColumnListener(new OnClickColumnListener<PieData>() {
            @Override
            public void onClickColumn(PieData lineData, int position) {
                Toast.makeText(PieChartActivity.this,lineData.getChartYDataList()+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });


        //设置第二个PieChart MEM
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> memYDataList = new ArrayList<>();
        memYDataList.add("USED");
        memYDataList.add("FREE");

        List<PieData> memDatas = new ArrayList<>();

        PieData memData1 = new PieData("USED", "%", getResources().getColor(R.color.arc1), mem);
        PieData memData2 = new PieData("FREE", "%", getResources().getColor(R.color.arc2), mem1);
        memDatas.add(memData1);
        memDatas.add(memData2);
        ChartData<PieData> memChartData = new ChartData<>("MEMORY",memYDataList,memDatas);
        memChart.setShowChartName(true);

        //设置标题样式
        FontStyle memFontStyle = memChart.getChartTitle().getFontStyle();
        memFontStyle.setTextColor(res.getColor(R.color.arc3));
        memFontStyle.setTextSpSize(this,15);
        memChart.getProvider().setOpenMark(true);
        memChart.getProvider().setMarkView(new BubbleMarkView(this));
//        LegendPoint legendPoint = (LegendPoint)memChart.getLegend().getPoint();
//        PointStyle style = legendPoint.getPointStyle();
//        style.setShape(PointStyle.CIRCLE);
        memChart.getLegend().setDirection(IComponent.TOP);
        memChart.setRotate(true);
        memChart.setChartData(memChartData);
        memChart.startChartAnim(1000);
        memChart.setOnClickColumnListener(new OnClickColumnListener<PieData>() {
            @Override
            public void onClickColumn(PieData lineData, int position) {
                Toast.makeText(PieChartActivity.this,lineData.getChartYDataList()+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });





        //设置第三个PieChart File
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> fileYDataList = new ArrayList<>();
        fileYDataList.add("USED");
        fileYDataList.add("FREE");

        List<PieData> fileDatas = new ArrayList<>();

        PieData fileData1 = new PieData("USED", "%", getResources().getColor(R.color.arc1), mem);
        PieData fileData2 = new PieData("FREE", "%", getResources().getColor(R.color.arc2), mem1);
        fileDatas.add(memData1);
        fileDatas.add(memData2);
        ChartData<PieData> fileChartData = new ChartData<>("MEMORY",fileYDataList,memDatas);
        fileChart.setShowChartName(true);

        //设置标题样式
        FontStyle fileFontStyle = fileChart.getChartTitle().getFontStyle();
        fileFontStyle.setTextColor(res.getColor(R.color.arc3));
        fileFontStyle.setTextSpSize(this,15);
        fileChart.getProvider().setOpenMark(true);
        fileChart.getProvider().setMarkView(new BubbleMarkView(this));
//        LegendPoint fileLegendPoint = (LegendPoint)fileChart.getLegend().getPoint();
//        PointStyle fileStyle = fileLegendPoint.getPointStyle();
        fileChart.getLegend().setDirection(IComponent.TOP);
        fileChart.setRotate(true);
        fileChart.setChartData(memChartData);
        fileChart.startChartAnim(1000);
        fileChart.setOnClickColumnListener(new OnClickColumnListener<PieData>() {
            @Override
            public void onClickColumn(PieData lineData, int position) {
                Toast.makeText(PieChartActivity.this,lineData.getChartYDataList()+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });



    }
}

