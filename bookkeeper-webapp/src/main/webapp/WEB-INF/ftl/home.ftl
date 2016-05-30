<#include "template.ftl" />

<#macro styles>
</#macro>

<#macro content>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
    </section>

    <!-- Main content -->
    <section class="content">

        <div class="row">
            <div class="col-md-6 col-sm-12 col-xs-12">
                <div class="info-box">
                    <span class="info-box-icon bg-blue"><i class="ion ion-social-usd-outline"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">Текущий баланс</span>
                        <span class="info-box-number">${balance} ₽</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>

            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                    <a href="/income">
                        <span class="info-box-icon bg-green"><i class="ion ion-ios-plus-outline"></i></span>
                    </a>

                    <div class="info-box-content">
                        <span class="info-box-text">Доход <br>за месяц</span>
                        <span class="info-box-number">${income} ₽</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                    <a href="minus">
                        <span class="info-box-icon bg-red"><i class="ion ion-ios-minus-outline"></i></span>
                    </a>

                    <div class="info-box-content">
                        <span class="info-box-text">Расход <br>за месяц</span>
                        <span class="info-box-number">760 ₽</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <!-- INCOME CHART -->
                <div class="box box-success">
                    <div class="box-header with-border">
                        <h3 class="box-title">Доходы за текущий месяц</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="chart">
                            <style>
                                .chart-legend ul {
                                    list-style-type: none;
                                }

                                .chart-legend ul li span {
                                    display: inline-block;
                                    width: 12px;
                                    height: 12px;
                                    margin-right: 5px;
                                }
                            </style>
                            <div id="incomeLegend" class="chart-legend"></div>
                            <canvas id="incomeChart" style="height: 250px; width: 475px;" width="950"
                                    height="500"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col (LEFT) -->

            <div class="col-md-6">
                <!-- SPENDING CHART -->
                <div class="box box-danger">
                    <div class="box-header with-border">
                        <h3 class="box-title">Расходы</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="chart">
                        <#--<canvas id="areaChart" style="height: 250px; width: 475px;" width="950"-->
                                    <#--height="500"></canvas>-->
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col (RIGHT) -->
        </div>

    </section>

</div>
</#macro>

<#macro scripts>
<!-- ChartJS 1.0.1 -->
<script src="/static/plugins/chartjs/Chart.min.js"></script>

<script>

    $(function () {
        var incomeChartCtx = $("#incomeChart").get(0).getContext("2d");

        var data = [
        ];

        var options = {
            segmentShowStroke: true,
            animateRotate: true,
            animateScale: false,
            percentageInnerCutout: 50,
            tooltipTemplate: "<%= value %> рублей"
        };

        var incomeChart = new Chart(incomeChartCtx).Doughnut(data, options);
        $("#incomeLegend").html(incomeChart.generateLegend());
    });


</script>
</#macro>