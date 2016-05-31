<#include "template.ftl" />

<#macro styles>
<!-- DataTables -->
<link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
</#macro>

<#macro content>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Баланс</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-12">
                                <p class="text-center">
                                </p>

                                <div class="chart">
                                    <!-- Sales Chart Canvas -->
                                    <canvas id="salesChart" style="height: 180px; width: 656px;" width="1312" height="360"></canvas>
                                </div>
                                <!-- /.chart-responsive -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- ./box-body -->
                    <div class="box-footer">
                        <div class="row">
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block border-right">
                                    <span class="description-percentage text-green"><i class="fa fa-plus-circle"></i></span>
                                    <h5 class="description-header">${income}</h5>
                                    <span class="description-text">Общий доход</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block border-right">
                                    <span class="description-percentage text-red"><i class="fa fa-minus-circle"></i></span>
                                    <h5 class="description-header">${expenditure}</h5>
                                    <span class="description-text">Общий расход</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block border-right">
                                    <span class="description-percentage text-green"><i class="fa fa-caret-up"></i></span>
                                    <h5 class="description-header">${averageIncome}</h5>
                                    <span class="description-text">Средни доход</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block">
                                    <span class="description-percentage text-red"><i class="fa fa-caret-down"></i></span>
                                    <h5 class="description-header">${averageExpenditure}</h5>
                                    <span class="description-text">Средний расход</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.box-footer -->
                </div>
                <!-- /.box -->
            </div>
            <!-- /.col -->
        </div>
    </section>

</div>
</#macro>

<#macro scripts>
<!-- ChartJS 1.0.1 -->
<script src="/static/plugins/chartjs/Chart.min.js"></script>

<script>
    ctx = document.getElementById('salesChart');

    var data = {
        labels: [
            <#list data as coordinate>
            "${coordinate.x}",
            </#list>
        ],
        datasets: [
            {
                label: "Баланс",
                fill: false,
                lineTension: 0.1,
                backgroundColor: "rgba(75,192,192,0.4)",
                borderColor: "rgba(75,192,192,1)",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "rgba(75,192,192,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(75,192,192,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                pointRadius: 3,
                pointHitRadius: 10,
                data: [
                    <#list data as coordinate>
                    "${coordinate.y?c}",
                    </#list>
                ],
            }
        ]
    };

    new Chart(ctx, {
        type: 'line',
        data: data
    });
</script>

</#macro>