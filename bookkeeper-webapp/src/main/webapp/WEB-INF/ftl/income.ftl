<#include "template.ftl" />

<#macro styles>
<!-- bootstrap datepicker -->
<link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
<!-- DataTables -->
<link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
</#macro>

<#macro content>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Доходы
        </h1>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <!-- general form elements -->
                <div class="box box-success">
                    <div class="box-header with-border">
                        <h3 class="box-title">Добавить</h3>
                    </div>
                    <!-- /.box-header -->
                    <!-- form start -->
                    <form id="income" action="/income" method="post">
                        <div class="box-body">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">Сумма</span>
                                    <input name="price" type="text" class="form-control" id="price"
                                           placeholder="0 RUB">
                                </div>
                            </div>

                            <div class="form-group">
                                <select name="category" id="category" class="form-control">
                                    <#list categories as category>
                                        <option value="${category.id}">${category.name}</option>
                                    </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <textarea name="description" id="description" class="form-control" rows="3"
                                          maxlength="256" placeholder="Описание"></textarea>
                            </div>

                            <div class="form-group">
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input name="date" id="date" type="text" class="form-control pull-right">
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->

                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div>
                <!-- /.box -->
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">Список доходов</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table id="incomes" class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>Категория</th>
                                <th>Сумма</th>
                                <th>Дата</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list incomes as income>
                                <tr>
                                    <td>${income.category.name}</td>
                                    <td>${income.price}</td>
                                    <td>${income.creationDate}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>

</div>
</#macro>

<#macro scripts>
<!-- jQuery Validation Plugin -->
<script src="/static/plugins/jquery-validation/jquery.validate.min.js"></script>
<!-- bootstrap datepicker -->
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- DataTables -->
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>

<script>
    var now = new Date();
    $('#date').val(now.getFullYear() + '-' + ('0' + (now.getMonth() + 1)).slice(-2) + '-' + ('0' + now.getDate()).slice(-2));
    $('#date').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true
    });

    $('#income').validate({
        rules: {
            price: {
                required: true,
                min: 1,
                digit: true
            },
            date: {
                required: true
            }
        },
        messages: {
            price: {
                required: 'Поле не должно быть пустым',
                min: 'Сумма должна быть не менее 1 рубля'
            },
            date: {
                required: 'Пожалуйста, укажите дату'
            }
        },
        errorElement: 'label',
        errorPlacement: function (error, element) {
            error.insertBefore(element.parent());
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parent().addClass('has-error');
            return false;
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parent().removeClass('has-error');
            return false;
        }

    });

    $("#incomes").DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.11/i18n/Russian.json"
        },
        "order": [[ 2, 'desc' ]]
    });
</script>

</#macro>
