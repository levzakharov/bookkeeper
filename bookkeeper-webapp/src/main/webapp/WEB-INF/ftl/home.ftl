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
                        <span class="info-box-text">Доход в этом<br>месяце</span>
                        <span class="info-box-number">${income} ₽</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                    <a href="/expenditure">
                        <span class="info-box-icon bg-red"><i class="ion ion-ios-minus-outline"></i></span>
                    </a>

                    <div class="info-box-content">
                        <span class="info-box-text">Расход в этом<br>месяце</span>
                        <span class="info-box-number">${expenditure} ₽</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
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
                            <canvas id="incomeChart" width="400" height="150"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col (LEFT) -->

            <div class="col-md-12">
                <!-- SPENDING CHART -->
                <div class="box box-danger">
                    <div class="box-header with-border">
                        <h3 class="box-title">Расходы за текущий месяц</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                    class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="chart">
                            <canvas id="expenditureChart" width="400" height="150"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col (RIGHT) -->
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header">
                        <h3 class="box-title">Записи</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table id="records" class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>Категория</th>
                                <th>Сумма</th>
                                <th>Дата</th>
                                <th>Тип</th>
                                <th>Действие</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list records as record>
                                <tr>
                                    <td>${record.category.name}</td>
                                    <td>${record.amount}</td>
                                    <td>${record.creationDate}</td>
                                    <td>
                                        <#if record.type = 'INCOME'>
                                            <span class="label label-success">Доход</span>
                                        <#else>
                                            <span class="label label-danger">Расход</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <form action="/record/delete" method="post">
                                            <input hidden="hidden" name="id" value="${record.id}">
                                            <button type="submit" class="btn btn-xs btn-danger"><i
                                                    class="fa fa-trash-o"></i>Удалить
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div
        </div>

    </section>

</div>
</#macro>

<#macro scripts>
<!-- ChartJS 1.0.1 -->
<script src="/static/plugins/chartjs/Chart.min.js"></script>
<!-- DataTables -->
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>

<script>
    $("#records").DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.11/i18n/Russian.json"
        },
        "order": [[2, 'desc']]
    });

    var ctxIncomeChart = document.getElementById("incomeChart");
    var incomeChart = new Chart(ctxIncomeChart, {
        type: 'bar',
        data: {
            labels: [
                <#list incomeData?keys as label>
                    "${label}",
                </#list>
            ],
            datasets: [{
                label: 'Сумма',
                data: [
                    <#list incomeData?keys as label>
                        ${incomeData[label]?c},
                    </#list>
                ],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

    var ctxExpenditureChart = document.getElementById("expenditureChart");
    var expenditureChart = new Chart(ctxExpenditureChart, {
        type: 'bar',
        data: {
            labels: [
                <#list expenditureData?keys as label>
                    "${label}",
                </#list>
            ],
            datasets: [{
                label: 'Сумма',
                data: [
                    <#list expenditureData?keys as label>
                    ${expenditureData[label]?c},
                    </#list>
                ],
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

</script>
</#macro>